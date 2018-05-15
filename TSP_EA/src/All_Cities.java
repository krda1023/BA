import java.util.ArrayList;

public class All_Cities {
	
	    // Holds our cities
	private static ArrayList destinationCities = new ArrayList<City>();

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
	    // Get a city
	public static City getCity(int index){
	        return (City)destinationCities.get(index);
	    }
	    
	    // Get the number of destination cities
	public static int numberOfCities(){
	        return destinationCities.size();
	    }
	}

