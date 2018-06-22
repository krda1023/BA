

public class City {
	double[] position;																	//aray with longiude[0] and latitude[1]
	public String type;
	public int id;				//String für Node ID?															//City's ID

	public City(int id,String type, double...position) {														// Constructs a city
		super();
		this.id = id;
		this.type=type;
		this.position = position;
	}
	public void setID(int i) {
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
	
	 public int getId() {																			 //Gets city's id
			return id;
		}
	 public String getType() {
		 return type;
	 }
	 public double expectedTravTime(City to,TimeElement startTime) {								//Calculates the expcted travel time from one city to another, regarding daytime and velocity factor
		 double expTime=0;
		 int hour= startTime.getHour();																// hour as int of point of time of city we're travelling from
		 double restTimeOfIntervall=startTime.getTimeToNextHour()/1000;					 			// unit: seconds   
		 if(Distanzmatrix.allMatrix.get(hour)[to.getId()][id]<=restTimeOfIntervall) {
			 expTime=Distanzmatrix.allMatrix.get(hour)[to.getId()][id];
		 }
		 else {
			 double restOf2Intervall=Distanzmatrix.allMatrix.get(hour+1)[to.getId()][id]*(1-(restTimeOfIntervall/Distanzmatrix.allMatrix.get(hour)[to.getId()][id]));
			 expTime+=restTimeOfIntervall+restOf2Intervall;		 		 
		 }
		 return expTime; 
	 }
	    
	 public double distanceTo(City city){															// Gets the distance to given city
	        double xDistance = Math.abs(getLongitude() - city.getLongitude());
	        double yDistance = Math.abs(getLatitude() - city.getLatitude());
	        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );			//Formular:
	        
	        return distance;
	    } 
	    
	    @Override
	 public String toString(){																		//Print longitude and latitude
	        return getLongitude()+", "+getLatitude();
	    }
}