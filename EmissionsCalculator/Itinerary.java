//Nihar Tamhankar
package restservice.EmissionsCalculator;

import java.util.ArrayList;

public class Itinerary implements Comparable<Itinerary>{

	private ArrayList<Flight> flightsInItinerary;
	private Double totalEmissions;

	public Itinerary(){
		this.flightsInItinerary = new ArrayList<Flight>(0);
		this.totalEmissions = 0.0;
	}

	public void addFlight(Flight flight){
		this.flightsInItinerary.add(flight);
		this.totalEmissions += flight.getEmissions();
	}

	public Double getEmissions(){
		return totalEmissions;
	}

	public int compareTo(Itinerary that){
		Double diff = this.totalEmissions - that.totalEmissions;

		if(diff < 0){return -1;}
		else if(diff == 0){return 0;}
		else{return 1;}
	}

	public ArrayList<Flight> getFlights(){
		return this.flightsInItinerary;
	}

	public String toString(){
		String  value = "The total emissions for this itinerary are " + this.totalEmissions + "\n";
		for(int i = 0; i < flightsInItinerary.size(); i++){
			value += flightsInItinerary.get(i) + "\n";
		}
		return value;
	}



	

}