//Nihar Tamhankar
//Flight.java
package restservice.EmissionsCalculator;

public class Flight implements Comparable<Flight>{
	private String flightNumber;
	private String airline;
	private String aircraft;
	private String origin;
	private String destination;
	private Double emissions;

	public Flight(String airline, String aircraft, String origin, String destination){
		this.airline = airline;
		this.aircraft = aircraft;
		this.origin = origin;
		this.destination = destination;

	}

	public String getAirline(){
		return this.airline;
	}

	public String getAircraft(){
		return this.aircraft;
	}

	public String getOrigin(){
		return this.origin;
	}

	public String getDestination(){
		return this.destination;
	}

	public void setEmissions(Double emissions){
		this.emissions = emissions;
	}

	public Double getEmissions(){
		return this.emissions;
	}

	public int compareTo(Flight that){
		Double diff = this.emissions - that.emissions;

		if(diff < 0){return -1;}
		else if(diff == 0){return 0;}
		else{return 1;}
	}

	public String toString(){
		String output = "Airline: ";
		output += this.airline;
		output += " from ";
		output += this.origin;
		output += " to ";
		output += this.destination;
		output += " Aircraft: ";
		output += this.aircraft;
		output += " Emissions: ";
		output += this.emissions;
		output += " kgs.";

		return output;
	}



}