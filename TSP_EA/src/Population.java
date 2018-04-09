public class Population {

    // Holds population of tours
    Tour[] tours;
    static Distanzmatrix dismat;

    // Construct a population
    public Population(int populationSize, int numberCity, boolean initialise) {
        tours = new Tour[populationSize];
        dismat= new Distanzmatrix(numberCity);
        dismat.erzeugeStadt();
        dismat.erzeugeMatrix();
        
        
        // If we need to initialise a population of tours do so
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++) {
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
        }
    }
    
    // Saves a tour
    public void saveTour(int index, Tour tour) {
        tours[index] = tour; 
    }
    
    public static double[][] getDistanzmatrix(){
    	return dismat.getDistanzmatrix();
    }
    public static Distanzmatrix getDistanzmatrixalsKlasse(){
    	return dismat;
    }
    
    // Gets a tour from population
    public Tour getTour(int index) {
        return tours[index];
    }

    
    
    // Gets the best tour in the population
    public Tour getFittest() {
        Tour fittest = tours[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return tours.length;
    }
    static double round(double wert)
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
    }