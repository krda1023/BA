public class Population {

    
    Tour[] tours;														// Holds population of tours
    Distanzmatrix dismat;

    // Construct a population
    public Population(int populationSize, boolean initialise) {			//Constructor of population
    	{
        tours = new Tour[populationSize];
        this.dismat=GA.getMatrixObject();
    	}            
        if (initialise) {											// If we need to initialise a population of tours do so     
           
        	System.out.println(populationSize);
        	for (int i = 0; i < populationSize; i++) {				  // Loop and create individuals
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);									// save individual
          
            }
        }
    }
    
    
    public void saveTour(int index, Tour tour) {						// Saves a tour
        tours[index] = tour; 
    }

        
    public Tour getTour(int index) {									// Gets a tour from population
        return tours[index];
    }
    
    public Tour getFittest() {											// Gets the best tour in the population
        Tour fittest = tours[0];
        for (int i = 1; i < populationSize(); i++) {					// Loop through individuals to find fittest
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }


    public int populationSize() {									   // Gets population size
    	return tours.length;
    }
   
    
    
    }
