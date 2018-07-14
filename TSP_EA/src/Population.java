// class that holds all of out individuals/tours
public class Population {

//VARIABLES:
	//Array that holds all of our tours
    Tour[] tours;														
    
//CONSTRUCTOR:
    // Initializes Tour Array
    // Initialize==true : Initialize a new population and randomly generate individuals
    public Population(int populationSize, boolean initialize) {
        tours = new Tour[populationSize];         
        if (initialize) {											
        	for (int i = 0; i < populationSize; i++) {				  
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);									     
            }
        }
    }
    
//METHODS:
    //Save a tour at certain position in tours
    public void saveTour(int index, Tour tour) {
        tours[index] = tour;
    }
    
    public Tour getTour(int index) {
        return tours[index];
    }
    
    //Returns the fittest and best individual 
    public Tour getFittest() {
        Tour fittest = tours[0];
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    public int populationSize() {
    	return tours.length;
    }

}
