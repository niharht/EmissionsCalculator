//Nihar Tamhankar
package restservice.EmissionsCalculator;
import java.util.Scanner;
import java.util.ArrayList;

public class App{
	public Calculator emissionsCalculator;

	public App(){
		emissionsCalculator = new Calculator();
	}

	public ArrayList<Itinerary> sortItineraries(){
		return this.emissionsCalculator.sortItineraries();
	}

	public boolean addItineraries(ArrayList<Flight> flights , String travelClass){
		return this.emissionsCalculator.addItinerary(flights, travelClass);
	}

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

		System.out.println("Welcome to the Emissions Calculator App");

		

		App application = new App();

		System.out.println("How many itineraries would you like to compare?");
		Integer numItineraries = Integer.parseInt(scanner.next());

		for(int i = 0; i < numItineraries; i++){
			ArrayList<Flight> flights = new ArrayList<Flight>();
			System.out.println("Please enter if you are flying Economy (E), Business (B), First (F) for this itinerary:");
			String travelClass = scanner.next();
			System.out.println("How many flights are in the itinerary?");
			Integer numFlights = Integer.parseInt(scanner.next());
			for(int j = 0; j < numFlights; j++){
				System.out.println("Please enter the airline you will be flying:");
				String airline = scanner.next();

				System.out.println("Please enter the origin:");
				String origin = scanner.next();

				System.out.println("Please enter the destination:");
				String destination = scanner.next();

				System.out.println("Please enter the aircraft you will be flying such as B77W, B789, B77L");
				String aircraft = scanner.next();

				Flight flight  = new Flight(airline, aircraft, origin, destination);

				flights.add(flight);
			}
			boolean add = application.addItineraries(flights, travelClass);
		}

		
		
		ArrayList<Itinerary> itineraries = application.sortItineraries();


		System.out.println("Based on our calculation, here is your emissions per itinerary to :");

		for(int i = 0; i < itineraries.size(); i++){
			Itinerary nextLowest = itineraries.get(i);
			System.out.println(nextLowest);
		}

	}

}