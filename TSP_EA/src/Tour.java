import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList<City> tour= new ArrayList<City>();
    // Cache
    private double fitness = 0;
    private double distance = 0;  //time?
    
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < GA.numOfCities; i++) {
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
    }

    // Gets a city from the tour
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
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
    
  /*  public double getDuration(double toIntersection,) {
    	long duration=0;
    	
    	for(int i=1; i<tour.size();i++) {
    		City destinationcity;
    		if(i+1<tour.size()) {
    			destinationcity=tour.get(i+1);
    		}
    		else {
    			destinationcity=tour.get(0);					//Nur wenn erste Stadt auch noch an erster Stelle steht, sprich anfang der reise, oder Serste stadt für immer an erster stelle lasssen und abfragen umdesignen
    		}
	    		TimeElement traveltime=Salesman.getTimeE();		
	    		traveltime.addTimeInMillis(Salesman.getTimeE().getExistTime()+duration);
	    		duration+=(long)1000*tour.get(i).expectedTravTime(destinationcity, traveltime);
    		
    	}
    	double durationInSec= (double)(duration/1000);
    	return durationInSec;
    }*/
    
    
    // Gets the total distance of the tour
    public double getDistance(){
        
    	if (distance ==0) {
    		double tourdistance =0;
    		double[][] matrix =GA.getMatrixObject().getDistanzmatrix();				//Distanzmatrix aus DIstanzmatrixKlasse holen
    		 for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) { 		
    			 City fromCity = getCity(cityIndex);
    			 City destinationCity;	  							// Check we're not on our tour's last city, if we are set our 
    			 													// tour's final destination city to our starting city
    			 if(cityIndex+1 < tourSize()){
                    
    				 destinationCity = getCity(cityIndex+1);
                 }
    		 
    			 
                 else{    	 
                     destinationCity = getCity(0);
                 }
    			 
    			 int a = fromCity.getId();
    			 int b = destinationCity.getId();
    			 
    			 tourdistance+=matrix[a][b];
    		 }
    		 distance=tourdistance;	        	 
    	}
    	distance= Maths.round(distance,5);    //Runden
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