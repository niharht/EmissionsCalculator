//Nihar Tamhankar
package restservice.EmissionsCalculator;

import java.util.ArrayList;
public class Output {

	private final Itinerary itinerary;
	private final double emissions;
	

	public Output(Itinerary itinerary, double emissions) {
		this.itinerary = itinerary;
		this.emissions = emissions;
	}

	public ArrayList<Flight> getItinerary() {
		return this.itinerary.getFlights();
	}

	public double getEmissions() {
		return this.emissions;
	}
}