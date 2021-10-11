Emissions Calculator

This is an application that calculates CO2 emissions for as many flight itineraries as you would like!

The emissions are based of greater circle distance for each flight, aircraft, airline (which takes into account seat configuration), and class of travel.

In order to determine the greater circle data, I use the positionstack API to determine the coordinates of the origin and destination.
Using these coordinates, I apply the Haversine formula to determine the greater circle distance.


Using the formula from myClimate.org, which is:
emissions = (fuelBurn * distance) /(aircraft capacity * average Load factor).
Then a class factor (based on First, Business, or Economy) is also factored in.

This is calculated for each flight in the itinerary and the total emissions for the itinerary is the sum of these emissions.
The output of the API is a sorted JSON based on lowest emissions per itinerary.

In order to reduce API calls, locations that have already been looked up with the positionstack API are stored in a hashtable.
The same is done with origin, destination pairs to reduce computation time of greater circle distance.


At startup time, the data in the data/ folder is read in.

The AirlineFleet class contains a HashTable of HashTables which stores the aircraft type and capacity by airline name.
The EmissionsLookUp class contains a HashTable of fuel burns by aircraft type. Different fuel burns depending on length of journey are used. This is used to reduce file I/O.
Depending on length of your flight, the correct fuel burn is used.

These two classes are used as lookups for determining the emissions per flight.


Utilizing a Gradle Wrapper and Spring Initializr, these files can be used to create and API that can be leveraged by different applications.



Contents of repo:

README.md: this file

DemoApplication.java: contains SpringBootApplication annotation needed by SpringBoot
OutputController.java: class for RestController and parser for API call to split arguments and pass to different classes and generate sorted JSON output.


data/
	Emissions.txt: contains fuel burn for different aircraft
	AirlineData.txt: sample of data format for different airlines, aircraft and seating capacity


EmissionsCalculator/
	App.java: Command Line Application that can be used for debugging and serves as a wrapper class also used by OutputController class for passing itineraries
	Aircraft.java: class for aircraft, including type, capacity and fuel burn
	AirlineFleet.java: class that hashes aircraft by airline as described above.
	Calculator.java: class that does actual emissions calculation per flight per itinerary.
	EmissionsLookUp.java: class that hashes fuel burns by aircraft.
	Flight.java: class for flights
	GreaterCircle.java: class that makes positionstack api call and calculates greater circle distance for each route.
	Itinerary.java: wrapper class for flights in an itinerary.
	Location.java: class used for origins and destinations.
	Output.java: class used by RestController to format output JSON.




