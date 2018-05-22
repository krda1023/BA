import java.util.ArrayList;

public class All_WP {
	private static ArrayList Waypoints = new ArrayList<City>();

    // Adds a destination city
public static void addCity(City city) {
        Waypoints.add(city);
    }
    
public static void deleteCity(City city)
{
	Waypoints.remove(city);
}
  public boolean containsCity(City city){
        return Waypoints.contains(city);
    }
    // Get a city
public static City getCity(int index){
        return (City)Waypoints.get(index);
    }
    
    // Get the number of destination cities
public static int numberOfCities(){
        return Waypoints.size();
    }
}

