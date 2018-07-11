import java.util.ArrayList;

public class All_Cities {
	
	    // Holds our cities
	static ArrayList<City> destinationCities = new ArrayList<City>();
	static final City startCity=Distanzmatrix.startCity;

	
	    // Adds a destination city
	public static void addCity(City city) {
	        destinationCities.add(city);
	}
	    
	public static void deleteCity(City city)
	{
		destinationCities.remove(city);
		
		
	}
	
	
	public static boolean containsCity(City city){
	    return destinationCities.contains(city);
	}
	public static City getCity(int index){
	        return (City)destinationCities.get(index);
	}
	    
	    // Get the number of destination cities
	public static int numberOfCities(){
	        return destinationCities.size();
	}
	
	public static int checkForCities() {
		int checkcounter=0;
		for(int check=0; check<All_Cities.numberOfCities();check++) {	//Überprüfe wieviel "Citys" noch in All_Cities sind
			if(All_Cities.getCity(check).type=="City") {
				checkcounter++;
			}		
		}
		return checkcounter;
	}
	public static int checkForIntersection() {
		int checkcounter=0;
		for(int check=All_Cities.numberOfCities()-2; check<All_Cities.numberOfCities();check++) {	//Überprüfe wieviel "Citys" noch in All_Cities sind
			if(All_Cities.getCity(check).type=="Intersection") {
				checkcounter++;
			}		
		}
		return checkcounter;
	}
}

