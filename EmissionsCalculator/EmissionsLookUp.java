//Nihar Tamhankar
package restservice.EmissionsCalculator;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;


public class EmissionsLookUp{

	Hashtable<String, LinkedList<DistanceBurn>> lookUpTable;
	private String relativePath = "path to data/Emissions.txt";

	private class DistanceBurn implements Comparable<DistanceBurn>{
		private Double distance;
		private Double fuelburn;

		public DistanceBurn(Double distance, Double fuelburn){
			this.distance = distance;
			this.fuelburn = fuelburn;
		}

		public Double getDistance(){
			return this.distance;
		}

		public Double getFuelBurn(){
			return this.fuelburn;
		}

		public int compareTo(DistanceBurn that){
			Double diff = this.distance - that.distance;
			if(diff < 0){return -1;}
			else if(diff == 0){return 0;}
			else{return 1;}
		}
	}
	

	public EmissionsLookUp(){
		lookUpTable = new Hashtable<String, LinkedList<DistanceBurn>>();
		//initiate Lookup Table
		setUpLookUp();

	}


	//initiate Lookup Table

	private void setUpLookUp(){

		BufferedReader in;
		String line;
		try{
			in = new BufferedReader(new FileReader(relativePath));
			line = in.readLine(); //first line we can discard
			System.out.println(line);
			while((line = in.readLine()) != null){
				//
				System.out.println(line);
				if(!line.isEmpty())
					infoParser(line);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}


	}

	private void infoParser(String data){
		String[] tokens = data.split(",");
		String type = tokens[0];
		Double distance = Double.parseDouble(tokens[1]);
		Double fuelburn = Double.parseDouble(tokens[2]);

		DistanceBurn value = new DistanceBurn(distance, fuelburn);

		LinkedList<DistanceBurn> distancelist = this.lookUpTable.get(type);
		if(distancelist == null){
			LinkedList<DistanceBurn> newdistancelist = new LinkedList<DistanceBurn>();
			newdistancelist.add(value);
			this.lookUpTable.put(type, newdistancelist);

		}else{
			distancelist.add(value);
		}

	}

	private DistanceBurn search(LinkedList<DistanceBurn> values, Double distance){
		DistanceBurn candidate = values.get(0);
		Double diff = Math.abs(distance - candidate.getDistance());

		for(int i = 1; i < values.size(); i++){
			DistanceBurn
			 temp = values.get(i);
			Double tempdiff = Math.abs(distance - temp.getDistance());

			if(tempdiff < diff){
				diff = tempdiff;
				candidate = temp;
			}
		}

		return candidate;
	}

	public Double getFuelBurn(String aircraft, Double distance){
		LinkedList<DistanceBurn> values = lookUpTable.get(aircraft);
		Collections.sort(values);

		DistanceBurn fuelburn = search(values, distance);

		return fuelburn.getFuelBurn();

	}

	public static void main(String[] args){
		EmissionsLookUp table = new EmissionsLookUp();
		System.out.println("Info for A388:");
		System.out.println(table.getFuelBurn("A388", 10000.0));

	}


}
