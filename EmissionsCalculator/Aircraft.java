//Nihar Tamhankar
package restservice.EmissionsCalculator;
import java.util.LinkedList;

public class Aircraft {

	private String type;
	private Integer capacity;

	private Double fuelburn;

	public Aircraft(String type, Integer capacity){
		this.type = type;
		this.capacity = capacity;
	}


	public String getType(){
		return this.type;
	}

	public Integer getCapacity(){
		return this.capacity;
	}

	public String toString(){
		return "The type is " + this.type + " and the capacity is " + this.capacity;
	}


}