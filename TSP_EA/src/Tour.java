import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList<City> tour= new ArrayList<City>();
    // Cache
    private double fitness = 0;
    private double distance = 0;  //time?
    static final City startCity=Distanzmatrix.startCity;
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < All_Cities.numberOfCities(); i++) {
            tour.add(null);
        }
    }
    
    public Tour(ArrayList<City> tour){
        this.tour = tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < All_Cities.numberOfCities(); cityIndex++) {
          setCity(cityIndex, All_Cities.getCity(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
        int startcitypos= tour.indexOf(startCity);
      
        tour.set(startcitypos, tour.get(0));
        tour.set(0, startCity);
       
    }

    // Gets a city from the tour
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }

    public void deleteCity(int pos) {
    	tour.remove(pos);
    }
    public void addatPosition(int pos, City city) {
    	tour.add(pos,city );
    }
    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
        //    fitness = 1/(double)getDistance();
        	fitness = 1/(double)getDistance();
        }
        return fitness;
    }
    
    public double getDuration123Test() {
    	double duration=GA.toDrivetoIntersection;
    	double[][] matrix =Distanzmatrix.getDistanzmatrix();
    	int hour=GA.lastEventTime.getHour();
    	double ttnh=GA.lastEventTime.getTimeToNextHour();
    	 for (int cityIndex=1; cityIndex < tourSize(); cityIndex++) { 		
			 City fromCity = getCity(cityIndex);
			 City destinationCity;	  							// Check we're not on our tour's last city, if we are set our 
			 													// tour's final destination city to our starting city
			 if(cityIndex+1 < tourSize()){
                
				 destinationCity = getCity(cityIndex+1);
             }
		 
			 
             else{    	 
                 destinationCity = getCity(0);
             }
			 
			
    	 }
    	double durationInSec= (double)(duration/1000);
    	return durationInSec;
    }
    
    public double getDuration() {
    	int index=1;
    	double totalDuration =GA.toDrivetoCity+GA.toDrivetoIntersection;
    	int hour=GA.lastEventTime.getHour();
    	double ttnh=GA.lastEventTime.getTimeToNextHour();
    	if(this.getCity(1).getType()=="Intersection"&&GA.OP_Stop==false) {
    		int a=Integer.parseInt(this.getCity(2).getId());
    		int h_next;
			if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
			}
			 if(totalDuration+Distanzmatrix.allMatrix.get(hour)[GA.numOfCities][a]>ttnh) {
					double tohour=ttnh-totalDuration+Distanzmatrix.allMatrix.get(hour)[GA.numOfCities][a];		;									//calculate the time from sum to next hour
					double hourratio= tohour/totalDuration+Distanzmatrix.allMatrix.get(hour)[GA.numOfCities][a];									// Calculate ratio of driven way in this section
					totalDuration+=hourratio*totalDuration+Distanzmatrix.allMatrix.get(hour)[GA.numOfCities][a]+(1-hourratio)*totalDuration+Distanzmatrix.allMatrix.get(h_next)[GA.numOfCities][a];		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;	
					hour+=1;
					if(hour==24) {
						hour=0;
					}
				
				}
				else {
					totalDuration+=totalDuration+Distanzmatrix.allMatrix.get(hour)[GA.numOfCities][a];
			
				}
    		index=2;
    	}
    	
    	
    	//Bedingung noch für letzte Runden, das stimmt noch nicht ganz und auch tour.size nochmal überdenken
    	
    	for (int cityIndex=index; cityIndex < tourSize(); cityIndex++) { 		//city index noch falsch
			 City fromCity = getCity(cityIndex);
			 City destinationCity;	  							// Check we're not on our tour's last city, if we are set our 
			 													// tour's final destination city to our starting city
			 if(cityIndex+1 < tourSize()){
               
				 destinationCity = getCity(cityIndex+1);
            }
		 
			 
            else{    	 
                destinationCity = Distanzmatrix.startCity;
              //  System.out.println(destinationCity.getId());
            }
			 
			int a = Integer.parseInt(fromCity.getId());
			int b = Integer.parseInt(destinationCity.getId());
			int h_next;
			if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
			}
			 
			 if(totalDuration+Distanzmatrix.allMatrix.get(hour)[a][b]>ttnh) {
					double tohour=ttnh-Distanzmatrix.allMatrix.get(hour)[a][b];		;									//calculate the time from sum to next hour
					double hourratio= tohour/Distanzmatrix.allMatrix.get(hour)[a][b];									// Calculate ratio of driven way in this section
					totalDuration+=hourratio*Distanzmatrix.allMatrix.get(hour)[a][b]+(1-hourratio)*Distanzmatrix.allMatrix.get(h_next)[a][b];		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;	
					hour+=1;
					if(hour==24) {
						hour=0;
					}
				
				}
				else {
					totalDuration+=Distanzmatrix.allMatrix.get(hour)[a][b];
			// tourdistance+=matrix[a][b];
				}
    	}
    	return totalDuration;
    }
    
    
    
    // Gets the total distance of the tour
    public double getDistance(){
      //  System.out.println(this);
    	if (distance ==0) {
    		double tourdistance =0;
    		
			double[][] matrix =Distanzmatrix.getDistanzmatrix();				//Distanzmatrix aus DIstanzmatrixKlasse holen
    		 for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) { 		
    			 City fromCity = getCity(cityIndex);
    			 City destinationCity;	  							// Check we're not on our tour's last city, if we are set our 
    			 													// tour's final destination city to our starting city
    			 if(cityIndex+1 < tourSize()){
                    
    				 destinationCity = getCity(cityIndex+1);
                 }
    		 
    			 
                 else{    	 
                     destinationCity = Distanzmatrix.startCity;
                   //  System.out.println(destinationCity.getId());
                 }
    			 
    			 int a = Integer.parseInt(fromCity.getId());
    			 
    			 int b = Integer.parseInt(destinationCity.getId());
    			 
    			
    			 tourdistance+=matrix[a][b];
    		
    			
    		 }
    		 distance=tourdistance;	  
    		
    		 
    	}
    	distance= Maths.round(distance,5);
    		//	System.out.println("Round finished");    //Runden
    	return distance;  		
    }
    	
    	/*if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're travelling from
                City fromCity = getCity(cityIndex);
                // City we're travelling to
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our 
                // tour's final destination city to our starting city
                if(cityIndex+1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;*/
    	
		 
    

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }
    
    // Check if the tour contains a city
    public boolean containsCity(City city){
        return tour.contains(city);
    }
    public int positionofCity(City city) {    
    	int position =tour.indexOf(city);
    	return position;
    }
    public boolean isEmpty(){
    	if(tour.isEmpty()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }
   
}