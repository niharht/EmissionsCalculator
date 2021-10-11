//Nihar Tamhankar
package restservice;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import restservice.EmissionsCalculator.App; //need to import App class
import restservice.EmissionsCalculator.Flight; //need to import Flight class
import restservice.EmissionsCalculator.Itinerary; //need to import Itinerary class
import restservice.EmissionsCalculator.Output;


//example API call: http://localhost:8090/emissionscalculator?travelClass=B&itineraries=San%20Francisco-Chicago%20O%27Hare-AmericanAirlines-B738_Chicago%20O%27Hare-NewYork%20JFK-AmericanAirlines-B738,San%20Francisco-NewYork%20JFK-AmericanAirlines-A321

@CrossOrigin("*")
@RestController
public class OutputController {

	private App application;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	
	@GetMapping("/emissionscalculator")
	public ArrayList<Output> output(
		@RequestParam(value = "travelClass", defaultValue = "E") String travelClass,
		@RequestParam(value = "itineraries", defaultValue = "San%20Francisco-Chicago%20O'Hare-AmericanAirlines-B738_Chicago%20O'Hare-NewYork%20JFK-AmericanAirlines-B738,San%20Francisco-NewYork%20JFK-AmericanAirlines-A321") ArrayList<String> itineraries) {

		
		setupApplication();
		
		for(int i = 0; i < itineraries.size(); i++){
			ArrayList<Flight> flightsInItinerary = new ArrayList<Flight>();
			String [] flights = itineraries.get(i).split("_");

			for(int j = 0; j < flights.length; j++){
				String [] params = flights[j].split("-");
				String origin = params[0].replaceAll("\\s", "%20");
				String destination = params[1].replaceAll("\\s", "%20");
				String airline = params[2];
				String aircraft = params[3];

				Flight flight = new Flight(airline, aircraft, origin, destination);
				flightsInItinerary.add(flight);
			}

			boolean add = application.addItineraries(flightsInItinerary, travelClass);
			
		}
		

		ArrayList<Itinerary> processed_itineraries = application.sortItineraries();

		ArrayList<Output> sortedItineraries = new ArrayList<Output>();

		String carrier = "";
		String aircraft = "";
		double emissions = 0.0;
		for(int i = 0; i < processed_itineraries.size(); i++){
			Itinerary lowest = processed_itineraries.get(i);
			emissions = lowest.getEmissions();
			//System.out.println(lowest.returnInfo());
			Output out = new Output(lowest, emissions);
			sortedItineraries.add(out);

		}

		///end of actual code

		//[{"itinerary":[{"airline":"AmericanAirlines","aircraft":"B738","origin":"San%20Francisco","destination":"Chicago%20O'Hare","emissions":350.4394471349001},{"airline":"AmericanAirlines","aircraft":"B738","origin":"Chicago%20O'Hare","destination":"NewYork%20JFK","emissions":182.6662467705769}],"emissions":533.105693905477},{"itinerary":[{"airline":"AmericanAirlines","aircraft":"A321","origin":"San%20Francisco","destination":"NewYork%20JFK","emissions":902.6123629974309}],"emissions":902.6123629974309}]

		/*
		//start of sample code
		ArrayList<Output> sortedItineraries = new ArrayList<Output>();
		Flight flight1 = new Flight("AmericanAirlines", "B738", "San%20Francisco", "Chicago%20O'Hare");
		flight1.setEmissions(350.4394471349001);
		Itinerary lowest = new Itinerary();
		lowest.addFlight(flight1);

		Flight flight2 = new Flight("AmericanAirlines", "B738", "Chicago%20O'Hare", "NewYork%20JFK");
		flight2.setEmissions(182.3443);
		lowest.addFlight(flight2);

		Output out = new Output(lowest, 533.105693905477);

		sortedItineraries.add(out);

		//"airline":"AmericanAirlines","aircraft":"A321","origin":"San%20Francisco","destination":"NewYork%20JFK","e
		Itinerary lowest2 = new Itinerary();
		Flight flight3 = new Flight("AmericanAirlines", "A321", "San%20Francisco", "NewYork%20JFK");
		flight3.setEmissions(902.6123629974309);
		lowest2.addFlight(flight3);

		out = new Output(lowest2, 902.6123629974309);
		
		sortedItineraries.add(out);
		//end of sample code
		*/
		

		return sortedItineraries;
	}


	private void setupApplication(){
		application = new App();
	}
}