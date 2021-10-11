//Nihar Tamhankar
package restservice.EmissionsCalculator;

import java.net.URI;
import java.net.http.*;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Math;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Hashtable;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class GreaterCircle {

	private Hashtable<String, Location> pointsLookupTable;
	private Hashtable<String, Double> routesLookupTable;

	private String originData = "{\"data\":[{\"latitude\":37.5572,\"longitude\":-122.2809}]}";
	private String destinationData;
	private static JsonParser PARSER;

	private static final String BASE_URL = "http://api.positionstack.com/v1/";

	private static final String apiKey = "get your key at positionstack.com";

	public GreaterCircle(){

		pointsLookupTable = new Hashtable<String, Location>();
		routesLookupTable = new Hashtable<String, Double>();
	}

	private String airportFormatter(String name){
		return (name + "%20airport");
	}

	private void sendRequest(Location place){
		String urlBuilder = "";
		HttpRequest request;

		String unique_keys = "forward" + "?access_key=" + apiKey + "&query=" + place.getLocation() +"&limit=1";
		urlBuilder = BASE_URL + unique_keys;

		try{
			request = HttpRequest.newBuilder()
				.uri(URI.create(urlBuilder))
				.build();


			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			place.setData(response.body().toString());
			System.out.println(place.getData());
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}

	}

	private Location getCoordinates(String point){
		Location place;

		if(pointsLookupTable.containsKey(airportFormatter(point))){ //we already have coordinates for place
			System.out.println("Seeing this place again " + point);
			return pointsLookupTable.get(airportFormatter(point));
		}
		else{
			place  = new Location(airportFormatter(point));
		}
		
		sendRequest(place);

		Gson gson = new Gson();
		JsonElement jsonelement = PARSER.parseString(place.getData());
		JsonObject jsonobject = jsonelement.getAsJsonObject();


		JsonArray array = (JsonArray) jsonobject.get("data");

		JsonObject embeddedData = (JsonObject) array.get(0);

		Double latitude = embeddedData.get("latitude").getAsDouble();
		Double longitude = embeddedData.get("longitude").getAsDouble();

		place.setLatitude(latitude);
		place.setLongitude(longitude);

		pointsLookupTable.put(place.getLocation(), place); //add to hashtable
		System.out.println("latitude: " + latitude + " longitude: " + longitude);

		return place;

	}

	public Double greaterCircleDistance(String from, String to){

		if(routesLookupTable.containsKey(airportFormatter(from) + airportFormatter(to))){
			System.out.println("Seeing this route again " + from + " " + to);
			return routesLookupTable.get(airportFormatter(from) + airportFormatter(to));
		}

		Location origin = getCoordinates(from);
		Location destination = getCoordinates(to);
		



		double x1 = Math.toRadians(origin.getLatitude());
		double y1 = Math.toRadians(origin.getLongitude());
		double x2 = Math.toRadians(destination.getLatitude());
		double y2 = Math.toRadians(destination.getLongitude());

		//Use Haversine formula
		double a = Math.pow(Math.sin((x2-x1)/2), 2)
                 + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2-y1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;

        System.out.println(distance2 * 1.852 + " km");

        routesLookupTable.put(airportFormatter(from) + airportFormatter(to), distance2 * 1.852);

        return (distance2 * 1.852); //convert to km

	}


	public static void main(String[] args){
		GreaterCircle connector = new GreaterCircle();
		System.out.println(connector.greaterCircleDistance("SFO", "LAX"));
		System.out.println(connector.greaterCircleDistance("LAX", "BOS"));
		System.out.println(connector.greaterCircleDistance("LAX", "BOS"));
	}
}