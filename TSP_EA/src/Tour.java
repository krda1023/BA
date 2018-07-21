import java.util.ArrayList;
import java.util.Collections;
// Class representing an individual or solution
//Holds City objects in ArrayList
//Calculates its own fitness and total duration
public class Tour {

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
    
    public Tour(Tour t) {
    	for( int tt=0; tt<t.tourSize();tt++) {
    		City cn= new City(t.getCity(tt).getId(), t.getCity(tt).getType(),t.getCity(tt).getPosition());
    		tour.add(tt, cn);
    	}
        
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
        int startcitypos= tour.indexOf(All_Cities.getCity(0));
        tour.set(startcitypos, tour.get(0));
        tour.set(0, All_Cities.getCity(0));
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

    public boolean checkforOrderDiffrence(Tour last) {
    	boolean change=false;
    	for(int a=0;a<this.tourSize();a++) {
    		
    		if(!this.getCity(a).getId().equals(last.getCity(a).getId())) {
    			change=true;
    			break;
    		}
    	}
    	return change;
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
    		int hour= EA.lastEventTime.getHour();
        	long nexthour=EA.lastEventTime.getMilliatNextHour();
        	long sumMilli=EA.lastEventTime.startInMilli;
        	int h_next;
    		int index=1;
	    	
	    	totalduration =EA.toDrivetoCity+EA.toDrivetoIntersection;
	    	//System.out.println("totaldur at beginning:");
	    	sumMilli+=totalduration*1000;
	    	if(sumMilli>nexthour) { //Annahme nich gr��er als 60 min;
	    		hour++;
	    		nexthour+=3600000;	
	    	}	
	    	if(hour==24) {
				hour=0;
			}
	    	if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
			}
	    	/*System.out.println(this);
	    	System.out.print("tDtC: "+EA.toDrivetoCity+" ");
	    	System.out.print("tdtI: "+EA.toDrivetoIntersection+" ");*/
	    //	System.out.print("  ttnh: "+ttnh+ " hour : "+ hour);
	    	
	    	// If situation requires a value of the "Intersection" matrix range
	    	if(EA.OP_Stop==false&&this.getCity(1).getType()=="Intersection") {
	    		//Get ID for selecting correct value in intersection matrix range
	    		int a=Integer.parseInt(this.getCity(2).getId());
	    		//toDriveto Calculation
	   
				//check for hour overlaps and calculate duration by ratios
				 if(sumMilli+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000>nexthour) {
 						long houroverlaps=(long)(sumMilli+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000-nexthour);						//	System.out.print(" tohour: "+tohour);
 						double houroverlapsratio= Maths.round(houroverlaps/Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000,5);									// Calculate ratio of driven way in this sectio						//System.out.print(" hourratio: "+hourratio);
    					totalduration+=(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]+(houroverlapsratio)*Distanzmatrix.allMatrix.get(h_next)[EA.numOfCities][a];		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
    					sumMilli+=(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000+(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(h_next)[EA.numOfCities][a];
    					nexthour+=3600000;
						hour+=1;
						if(hour==24) {
							hour=0;
						}
						if(hour==23) {
							h_next=0;
						}
						else {
							h_next=hour+1;
						}
					
					}
				 //add hour value to totalduration
					else {
						totalduration+=totalduration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a];	
						sumMilli+=totalduration+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000;	
					}
				/*	System.out.print(" IM Valueohne Faktor: "+Distanzmatrix.matrix[EA.numOfCities][a]);
			    	System.out.print(" IMValue: "+(totalduration-EA.toDrivetoCity-EA.toDrivetoIntersection));
			    	System.out.println();*/
	    	}
	    	if(EA.lastCityvisited==false) {
	    	//Set index for calculation of duration of remaining cities
	    	if(this.getCity(1).getType()=="Intersection") {
	    		index=2;
	    	}
	    	else if(this.getCity(1).getType()=="City") {
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
				
			
				// hour-depending calculation of duration
				if(sumMilli+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000>nexthour) {
						long houroverlaps=(long)(sumMilli+Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000-nexthour);						//	System.out.print(" tohour: "+tohour);
						double houroverlapsratio= Maths.round(houroverlaps/Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000,5);									// Calculate ratio of driven way in this sectio						//System.out.print(" hourratio: "+hourratio);
					totalduration+=(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]+(houroverlapsratio)*Distanzmatrix.allMatrix.get(h_next)[EA.numOfCities][a];		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					sumMilli+=(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(hour)[EA.numOfCities][a]*1000+(1-houroverlapsratio)*Distanzmatrix.allMatrix.get(h_next)[EA.numOfCities][a];
					nexthour+=3600000;
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					if(hour==23) {
						h_next=0;
					}
					else {
						h_next=hour+1;
					}
				
				}
	    	}
	    	}
	    	totalduration=Maths.round(totalduration, 3);
	    	return totalduration;
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
   	totalduration= Maths.round(totalduration,3);
   	return totalduration;  	
    	}
    }

    //ToString method
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += "ID:"+getCity(i).getId()+" "+getCity(i).getLatitude()+" "+getCity(i).getLongitude()+"|";
        }
        return geneString;
    }
   
}