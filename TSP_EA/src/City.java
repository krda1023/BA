

public class City {
	double[] position;																	//array with longiude[0] and latitude[1]
	public String type;
	public String id;				//String für Node ID?															//City's ID

	public City(String id,String type, double...position) {														// Constructs a city
		super();
		this.id = id;
		this.type=type;
		this.position = position;
	}
	public void setID(String i) {
		id=i;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public double[] getPosition() {																	//Get Position array
		return position;}
	    
	  
	 public double getLongitude(){																	 // Gets city's longitude coordinate
	        return position[0];
	    }
	    
	   
	 public double getLatitude(){																	 // Gets city's latitude coordinate
	        return position[1];
	    }
	
	 public String getId() {																			 //Gets city's id
			return id;
		}
	 public String getType() {
		 return type;
	 }
	
	  public String toString() {
		  String str;
		  str="ID:"+this.id+" "+this.getLongitude()+" "+this.getLatitude();
		  return str;
	  }

}