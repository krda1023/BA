

public class City {
	double[] position;								//array with longiude[0] and latitude[1]
	public String type;
	public String id;												//City's ID

	public City(String id,String type, double...position) {				// Constructs a city
		super();
		this.id = id;
		this.type=type;
		this.position = position;
	}
	
	public String getId() {																			 //Gets city's id
			return id;
		}
	 
	public void setID(String i) {
		id=i;
	}
	
	public double[] getPosition() {																	//Get Position array
		return position;}
	    
	public double getLongitude(){																	 // Gets city's longitude coordinate
	        return position[0];
	    }
	    
	public double getLatitude(){																	 // Gets city's latitude coordinate
	        return position[1];
	    }
	 
	public void setCoordinates(double lon, double lat) {
		 position[0]=lon;
		 position[1]=lat;
	 }
	
	public String getType() {
		 return type;
	 }
	
	public void setType(String type) {
			this.type=type;
		}
		
	public String toString() {
		  String str;
		  str="ID:"+this.id+" "+this.getLongitude()+" "+this.getLatitude();
		  return str;
	  }

}