import java.util.ArrayList;
import java.util.Collections;
// Class representing an individual or solution
//Holds City objects in ArrayList
//Calculates its own fitness and total duration
public class Tour{

//VARIABLES:
    // Holds our tour of cities
    ArrayList<City> tour= new ArrayList<City>();
    double fitness = 0;
    double totalduration = 0;
 
//CONSTRUCTOR:
    //Constructs blank tour
    public Tour(){
        for (int i = 0; i < All_Cities.numberOfCities(); i++) {
            tour.add(null);
        }
    }
    //Constructs Tour with City objects of argument ArrayList
    public Tour(ArrayList<City> tour){
        this.tour = tour;
    }

//METHODS:
    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < All_Cities.numberOfCities(); cityIndex++) {
          setCity(cityIndex, All_Cities.getCity(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
        int startcitypos= tour.indexOf(Distanzmatrix.startCity);
        tour.set(startcitypos, tour.get(0));
        tour.set(0, Distanzmatrix.startCity);
    }
   
    //Get size of the tour
    public int tourSize() {
        return tour.size();
    }
   
    //Gets the City at a position
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }
    
    //Returns true if the tour contains a certain city
    public boolean containsCity(City city){
        return tour.contains(city);
    }
    
    //Get the position of a certain
    public int positionofCity(City city) {    
    	int position =tour.indexOf(city);
    	return position;
    }
    
    //Delete the city at certain position
    public void deleteCity(int pos) {
    	tour.remove(pos);
    }
   
    // Add a city at a certain position within the tour
    public void addatPosition(int pos, City city) {
    	tour.add(pos,city );
    }
    
    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        fitness = 0;
        totalduration=0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
        	fitness = 1/(double)getDuration();
        }
        return fitness;
    }
     
    //Calcuations the total duration of the tour depending on the status of the algorithm and procoess 
    public double getDuration() {
    	totalduration=0;
    	//Calculation of duration in dynamic environment
    	if(Run.runs==true) {
	    	int index;
	    	double totalDuration =EA.toDrivetoCity+EA.toDrivetoIntersection;
	    	int hour=EA.lastEventTime.getHour();
	    	double ttnh=EA.lastEventTime.getTimeToNextHour();
	    	// If situation requires a value of the "Intersection" matrix range
	    	if(this.getCity(1).getType()=="Intersection"&&EA.OP_Stop==false) {
	    		//Get ID for selecting correct value in intersection matrix range
	    		int a=Integer.parseInt(this.getCity(2).getId());
	    		//toDriveto Calculation
	    		int h_next;
				if(hour==23) {
					h_next=0;
				}
				else {
					h_next=hour+1;
				}
				//check for hour overlaps and calculate duration by ratios
				 if(totalDuration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]>ttnh) {
						double tohour=ttnh-totalDuration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a];		;									//calculate the time from sum to next hour
						double hourratio= tohour/totalDuration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a];									// Calculate ratio of driven way in this section
						totalDuration+=hourratio*totalDuration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]+(1-hourratio)*totalDuration+Distanzmatrix.allMatrix.get(h_next)[EA.numOfCities][a];		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
						ttnh+=3600;	
						hour+=1;
						if(hour==24) {
							hour=0;
						}
					
					}
				 //add hour value to totalDuration
					else {
						totalDuration+=totalDuration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a];	
					}
	    	}
	    	
	    	//Set index for calculation of duration of remaining cities
	    	if(this.getCity(1).getType()=="Intersection") {
	    		index=2;
	    	}
	    	else {
	    		index=1;
	    	}
	    	
	    	//Calculation hour depending duration of remaining cities
	    	// and back to city we've started from
	    	for (int cityIndex=index; cityIndex < tourSize(); cityIndex++) { 		//city index noch falsch
				 City fromCity = getCity(cityIndex);
				 City destinationCity;	
				 if(cityIndex+1 < tourSize()){   
					 destinationCity = getCity(cityIndex+1);
	            } 
	            else{    	 
	                destinationCity = Distanzmatrix.startCity;
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
				// hour-depending calculation of duration
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
					}
	    	}
	    	return totalDuration;
    	}
    	
    	//Calculation of duration in static environment, just city objects of type "City"
    	else {
    		double[][] matrix =Distanzmatrix.matrix;
   		 for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) { 		
   			 City fromCity = getCity(cityIndex);
   			 City destinationCity;	  	
   			 if(cityIndex+1 < tourSize()){
                   
   				 destinationCity = getCity(cityIndex+1);
   			 }
                else{    	 
                    destinationCity = Distanzmatrix.startCity;
                } 
   			 int a = Integer.parseInt(fromCity.getId());
   			 int b = Integer.parseInt(destinationCity.getId());
   			 totalduration+=matrix[a][b];	
   		 }
   	totalduration= Maths.round(totalduration,5);
   	return totalduration;  	
    	}
    }

    //ToString method
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }
   
}