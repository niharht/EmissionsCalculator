//Nihar Tamhankar
package restservice.EmissionsCalculator;
import java.util.Collections;
import java.util.ArrayList;


public class Calculator{

	ArrayList<Itinerary> itineraries;
	GreaterCircle greaterCircle;

	EmissionsLookUp emissionsTable;
	AirlineFleet airlineTable;


	public Calculator(){
		greaterCircle = new GreaterCircle();

		itineraries = new ArrayList<Itinerary>(0);

		this.emissionsTable = new EmissionsLookUp();
		this.airlineTable = new AirlineFleet();
		
	}


	private Double calculateEmissions(String airline, String aircraft, String origin, String destination, String travelClass){
		Double emissions;

		Double distance = greaterCircle.greaterCircleDistance(origin, destination);

		Aircraft jet = airlineTable.getAircraftInfo(airline, aircraft);

		Double fuelBurn = emissionsTable.getFuelBurn(jet.getType(), distance);
		

		//Formula based on research done by myclimate.org
		Double classFactor;
		emissions = (fuelBurn * distance) / (jet.getCapacity() * 0.82);  //total fuel burned for the distance divided by capacity*load factor
		
		if(distance < 2500.0){//short haul
			switch(travelClass){
				case "E":
					classFactor = 0.96;
					break;
				case "B":
					classFactor = 1.26;
					break;
				case "F":
					classFactor = 2.40;
					break;
				default:
					classFactor = 0.96;
			}
		}else{ //longhaul
			switch(travelClass){
				case "E":
					classFactor = 0.80;
					break;
				case "B":
					classFactor = 1.54;
					break;
				case "F":
					classFactor = 2.40;
					break;
				default:
					classFactor = 0.80;
			}
		}
		emissions = emissions * classFactor; //class factor
		emissions = emissions * (2.0 * 3.15); //emissions factor * multiplier

		return emissions;
	}

	private void calculateFlightEmissions(Flight flight, String travelClass){
		Double emissions = calculateEmissions(flight.getAirline(), flight.getAircraft(), flight.getOrigin(), flight.getDestination(), travelClass);
		flight.setEmissions(emissions);
	}

	public boolean addItinerary(ArrayList<Flight> flights, String travelClass){
		Itinerary routing = new Itinerary();
		for(int i = 0; i < flights.size(); i++){
			Flight flight = flights.get(i);
			calculateFlightEmissions(flight, travelClass);
			routing.addFlight(flight);
		}

		return this.itineraries.add(routing);

	}

	public ArrayList<Itinerary> sortItineraries(){
		Collections.sort(this.itineraries);
		return this.itineraries;
	}


}