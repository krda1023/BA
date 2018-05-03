
public class City {
	private final double[] position;
	private final int id;
	
	    
	    // Constructs a city
	public City(int id, double... position) {
		super();
		this.id = id;
		this.position = position;
	}
		//Get Position array
	public double[] getPosition() {
		return position;}
	    
	    // Gets city's x coordinate
	 public double getLongitude(){
	        return position[0];
	    }
	    
	    // Gets city's y coordinate
	 public double getLatitude(){
	        return position[1];
	    }
	 //Gets city's id
	 public int getId() {
			return id;
		}
	    
	    // Gets the distance to given city
	 public double distanceTo(City city){
	        double xDistance = Math.abs(getLongitude() - city.getLongitude());
	        double yDistance = Math.abs(getLatitude() - city.getLatitude());
	        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
	        
	        return distance;
	    } 
	    
	    @Override
	 public String toString(){
	        return getLongitude()+", "+getLatitude();
	    }
}
