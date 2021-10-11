//Nihar Tamhankar
package restservice.EmissionsCalculator;

import java.util.Hashtable;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class AirlineFleet{

	private Hashtable<String, Hashtable<String,Aircraft>> lookupTable;

	private String relativePath = "path to data/AirlineData.txt";

	public AirlineFleet(){
		lookupTable = new Hashtable<String, Hashtable<String,Aircraft>>();
		readAirlineInfo(relativePath);

	}

	private void readAirlineInfo(String filename){
		BufferedReader in;
		String line;

		try{
			in = new BufferedReader(new FileReader(relativePath));
			while((line = in.readLine()) != null){
				System.out.println(line);
				if(!line.isEmpty())
					aircraftParser(line);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void aircraftParser(String line){
		String[] tokens = line.split(",");
		String airlineName = getFormattedName(tokens[0]);
		String type = tokens[1];
		Integer capacity = Integer.parseInt(tokens[2]);

		Aircraft craft = new Aircraft(type, capacity);

		if(this.lookupTable.get(airlineName) == null){ //airline not in database yet
			Hashtable<String, Aircraft> newTable = new Hashtable<String, Aircraft>();
			newTable.put(type,craft);
			this.lookupTable.put(airlineName, newTable);
		}
		else{
			this.lookupTable.get(airlineName).put(type, craft);
		}
	}

	private String getFormattedName(String name){
		String airlineName = name.toUpperCase();
		String formattedName = airlineName.replaceAll("\\s", "");
		return formattedName;
	}

	public Aircraft getAircraftInfo(String airline, String aircraft){
		return lookupTable.get(getFormattedName(airline)).get(aircraft);
	}

	public static void main(String[] args){
		AirlineFleet database = new AirlineFleet();

		System.out.println(database.getAircraftInfo("Alaska Airlines", "B739"));
		System.out.println(database.getAircraftInfo("american airlines", "B738"));



	}
}