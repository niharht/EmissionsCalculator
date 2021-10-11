//Nihar Tamhankar
package restservice.EmissionsCalculator;

public class Location{
	private String location;
	private String jsonData;
	private Double latitude;
	private Double longitude;

	public Location(String location){
		this.location = location;
	}

	public String getLocation(){
		return this.location;
	}

	public String getData(){
		return this.jsonData;
	}

	public Double getLatitude(){
		return this.latitude;
	}

	public Double getLongitude(){
		return this.longitude;
	}

	public void setData(String data){
		this.jsonData = data;
	}

	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}

}