import java.util.ArrayList;

public class All_Cities {
	
	    // Holds our cities
	private static ArrayList<City> destinationCities = new ArrayList<City>();
	static final City startCity=Distanzmatrix.startCity;
	    // Adds a destination city
	public static void addCity(City city) {
	        destinationCities.add(city);
	}
	    
	public static void deleteCity(City city)
	{
		destinationCities.remove(city);
	}
	public boolean containsCity(City city){
	    return destinationCities.contains(city);
	}
	public static City getCity(int index){
	        return (City)destinationCities.get(index);
	}
	    
	    // Get the number of destination cities
	public static int numberOfCities(){
	        return destinationCities.size();
	}
}

