public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population  ORDERED CROSSOVER
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            // Crossover parents
            Tour child = OrderCrossover(parent1, parent2);
            //Ox1 Crossover
          //  Tour childs[]= Ox1Crossover(parent1,parent2);
            //Tour child1=childs[0];
           // Tour child2=childs[1];
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material 
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            swapmutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    // Applies crossover to a set of parents and creates offspring  ORDERED CROSSOVER
    public static Tour OrderCrossover(Tour parent1, Tour parent2) {
        // Create new child tour
        Tour child = new Tour();
        int number1 = (int) (Math.random() * parent1.tourSize());
        int number2 = (int) (Math.random() * parent1.tourSize());
        int startPos= Math.min(number1, number2);
        int endPos= Math.max(number1, number2);
        

        
        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            if (i >= startPos && i <= endPos) {
                child.setCity(i, parent1.getCity(i));
            } 
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }
    
 // Applies One-Point crossover to a set of parents and creates 2 offsprings
    
    public static Tour[] Ox1Crossover(Tour parent1, Tour parent2)
    {
    	Tour[] children = new Tour[2];
    	Tour interparent1= new Tour();
    	Tour interparent2= new Tour();
    	Tour child1 = new Tour();
    	Tour child2 = new Tour();
        int number1 = (int) (Math.random() * parent1.tourSize());
        int number2 = (int) (Math.random() * parent1.tourSize());
        int startPos= Math.min(number1, number2);
        int endPos= Math.max(number1, number2);
        
        for (int i = 0; i < child1.tourSize(); i++) {
            if (i >= startPos && i <= endPos) {
                child1.setCity(i, parent2.getCity(i));
                child2.setCity(i, parent1.getCity(i));
            } 
           }
            
        //Take not cut part and move to head of intermediate parent
        for(int j=0; j<child1.tourSize();j++)
        {
  
        	if(j>endPos)
        	{
        		interparent1.setCity((endPos+1-j), parent1.getCity(j));
        		interparent2.setCity((endPos+1-j), parent2.getCity(j));
        	}
        }
        int l=(endPos-startPos);
        for( int k=0;k<child1.tourSize();k++ )
        	
        {	
        	if(k>l)
        	interparent1.setCity(k,parent1.getCity(k-l-1));
        	interparent2.setCity(k,parent2.getCity(k-l-1));
        }
        
        for (int m=0; m<child1.tourSize();m++)
        {
        	if (child1.containsCity(interparent2.getCity(m)))
        			{ interparent2.setCity(m,null);}
        	if (child2.containsCity(interparent1.getCity(m)))
			{ interparent1.setCity(m,null);}
        }
        
        for(int n=endPos+1;n<child1.tourSize();n++)
        {
        	for( int o=0; o<child1.tourSize();o++)
        	{
        		if(interparent1.getCity(o)!=null)
        		child2.setCity(n,interparent1.getCity(o));
        		interparent1.setCity(o,null);
        		break;
        	}
        	for( int p=0; p<child1.tourSize();p++)
        	{
        		if(interparent2.getCity(p)!=null)
        		child1.setCity(n,interparent2.getCity(p));
        		interparent2.setCity(p,null);
        		break;
        	}
        	
        }
        
        children[0]= child1;
        children[1]=child2;
    	return children;
    }
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2)
    {	Tour []childs= new Tour[2];
    	Tour interparent1= parent1;
    	Tour interparent2= parent2;
    	Tour child1 = new Tour();
    	Tour child2 = new Tour();
    	int number1 = (int) (Math.random() * parent1.tourSize());
    	int number2 = (int) (Math.random() * parent1.tourSize());
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);
    
    	for (int i = 0; i < child1.tourSize(); i++) {
    		if (i >= startPos && i <= endPos) {
            child1.setCity(i, parent2.getCity(i));
            child2.setCity(i, parent1.getCity(i));
        } 
       }
    	
    	//Ox2 Crossover
    	for(int j=0;j<child1.tourSize();j++)
    		{
    			
    		}
    
    	return childs;
    }
    

    // Mutate a tour using SWAP MUTATION
    private static void swapmutate(Tour tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                // Get the cities at target position in tour
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }

    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }
}