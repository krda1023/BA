import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList<City> tour= new ArrayList<City>();
    private double fitness = 0;
     double totalduration = 0;  //time?
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
        int startcitypos= tour.indexOf(Distanzmatrix.startCity);
      
        tour.set(startcitypos, tour.get(0));
        tour.set(0, Distanzmatrix.startCity);
       
    }
   
    public int tourSize() {
        return tour.size();
    }
    
    // Gets a city from the tour
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }
    
    public boolean containsCity(City city){
        return tour.contains(city);
    }
    
    public int positionofCity(City city) {    
    	int position =tour.indexOf(city);
    	return position;
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
        totalduration=0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
        	fitness = 1/(double)getDuration();
        }
        return fitness;
    }
     
    public double getDuration() {
    	totalduration=0;
    	if(Run.runs==true) {
	    	int index;
	    	double totalDuration =GA.toDrivetoCity+GA.toDrivetoIntersection;
	    	int hour=GA.lastEventTime.getHour();
	    	double ttnh=GA.lastEventTime.getTimeToNextHour();
	    	//Asyym
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
	    	
	    	if(this.getCity(1).getType()=="Intersection") {
	    		index=2;
	    	}
	    	else {
	    		index=1;
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
				// totalduration+=matrix[a][b];
					}
	    	}
	    	return totalDuration;
    	}
    	else {
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
   			 
   			
   			 totalduration+=matrix[a][b];
   		
   			
   		 }
   	totalduration= Maths.round(totalduration,5);
   		//	System.out.println("Round finished");    //Runden
   	return totalduration;  	
    	}
    }

    // Get number of cities on our tour

    // Check if the tour contains a city
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }
   
}