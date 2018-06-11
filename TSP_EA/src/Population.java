public class Population {

    
    Tour[] tours;														// Holds population of tours
    static Distanzmatrix dismat;

    // Construct a population
    public Population(int populationSize, boolean initialise) {			//Constructor of population
    	{
        tours = new Tour[populationSize];
        this.dismat=GA.getMatrixObject();
    	}            
        if (initialise) {												// If we need to initialise a population of tours do so     
            for (int i = 0; i < populationSize(); i++) {				  // Loop and create individuals
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);									// save individual
            }
        }
    }
    
    
    public void saveTour(int index, Tour tour) {						// Saves a tour
        tours[index] = tour; 
    }
    
  /*  public static double[][] getDistanzmatrix(){
    	return dismat.getDistanzmatrix();
    }
    public static Distanzmatrix getDistanzmatrixalsKlasse(){
    	return dismat;
    } */
    
    
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
    static double round(double wert)									//General method to round values
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
    
    
    }
