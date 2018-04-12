public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = false;
    

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
    
        Population newPopulation = new Population(pop.populationSize(), false);
        System.out.println(pop.populationSize());
        System.out.println(newPopulation.populationSize());
       /* for (int a=0; a<pop.populationSize();a++)
        {
        	System.out.println(pop.getTour(a));
        }*/
        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population  ORDERED CROSSOVER
        // Loop over the new population's size and create individuals from
        // Current population
        for (int z = 0; z < newPopulation.populationSize(); z++) {
        	if ((z+1)<newPopulation.populationSize())
        	{
        		
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            // Crossover parents
            Tour child = OrderCrossover(parent1, parent2);
            //Ox1 Crossover
           Tour childs[]= Ox2Crossover(parent1,parent2);
            Tour child1=childs[0];
           Tour child2=childs[1];
            //Add child to new population
            newPopulation.saveTour(z, child1);
           // System.out.println(child1);
            newPopulation.saveTour((z+1),child2);
          //  System.out.println(child2);
            //System.out.println("");System.out.println("");
            
            z=z+1;
            System.out.println("Z�hler: "+z);
        }
        	
        	else 
        	{
        		Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
               // Tour child = Ox2Crossover(parent1, parent2);
                Tour child= OrderCrossover(parent1,parent2);
                newPopulation.saveTour(z, child);
                System.out.println("Z�hler: "+z);
               
        	
        }}
        // Mutate the new population a bit to add some new genetic material 
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            InsertMutation(newPopulation.getTour(i));
        }
     /*   for (int a=0; a<pop.populationSize();a++)
        {
        	System.out.println(newPopulation.getTour(a));
        }*/
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
    
   /* public static Tour[] Ox1Crossover(Tour parent1, Tour parent2)
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
    }*/
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2)
    {	Tour childs[]= new Tour[2];
    	Tour interparent1= parent1;
    	Tour interparent2= parent2;
    	System.out.println(interparent1);
    	System.out.println(interparent2);
    	Tour child1 = new Tour();
    	Tour child2 = new Tour();
    	int number1 = (int) (Math.random() * parent1.tourSize());
    	int number2 = (int) (Math.random() * parent1.tourSize());
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);
    	for(int p=0;p<100;p++)
    	{
    		if(startPos==endPos)
    		{
    		number1 = (int) (Math.random() * parent1.tourSize());
        	number2 = (int) (Math.random() * parent1.tourSize());
        	startPos= Math.min(number1, number2);
        	endPos= Math.max(number1, number2);	
    		}
    		else
    		{
    			break;
    		}
    	}
    		
    	//System.out.println(parent1);
    //	System.out.println(parent2);
    	for (int i = 0; i < parent1.tourSize(); i++) {
    		if (i >= startPos && i <= endPos) {
            child1.setCity(i, parent2.getCity(i));
            child2.setCity(i, parent1.getCity(i));
         // System.out.print(child1.getCity(i));
        	//System.out.print(child2.getCity(i));
        } 
       }
    	//System.out.println(startPos);
    	//System.out.println(endPos);
    	
    	
    	//Ox2 Crossover
    	for(int j=0;j<parent1.tourSize();j++)
    		{
    		if (child1.containsCity(interparent2.getCity(j)))
			{ interparent2.setCity(j,null);}
    		if (child2.containsCity(interparent1.getCity(j)))
    		{ interparent1.setCity(j,null);}
    		}
    	System.out.println(interparent1);
    	System.out.println(interparent2);
    	for (int k = 0; k < parent2.tourSize(); k++) {
            // If child doesn't have the city add it
            if (!child1.containsCity(parent2.getCity(k))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child1.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child1.getCity(ii) == null) {
                        	child1.setCity(ii, parent2.getCity(k));
                        break;
                        }
                    }
                }
            }
    	
          for (int l = 0; l < parent1.tourSize(); l++) {
                            // If child doesn't have the city add it
            if (!child2.containsCity(parent1.getCity(l))) {
                                // Loop to find a spare position in the child's tour
            	for (int ii = 0; ii < child1.tourSize(); ii++) {
                                    // Spare position found, add city
            		if (child2.getCity(ii) == null) {
            				child2.setCity(ii, parent1.getCity(l));
                      break;
            			}
            		}
            	}
            }
        /*  double zufall= Math.random();
          if(zufall<0.5)
          {
          child=child1;
          System.out.println("Ich bin child1:"+child);}
          else {
          child=child2; System.out.println("Ich bin child2:"+child);
          }*/
          
        childs[0]=child1;
        childs[1]=child2;
        System.out.println(child1);
    	System.out.println(child2);
    	return childs;
    }
    
    
    private static void CutMutation(Tour tour) {
    	int number1 = (int) (Math.random() * Run.getNumberofCities());
    	int number2 = (int) (Math.random() * Run.getNumberofCities());
    	
    	Tour intertour= tour;
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);
    	int insertposition= (int)(Math.random()*(Run.getNumberofCities()-1-(endPos-startPos)));
    	for (int i = 0; i < tour.tourSize(); i++) {
    		if (i >= startPos && i <= endPos) {
            intertour.setCity(insertposition, tour.getCity(i));
            insertposition=insertposition+1;
            }}
    	for(int j=0;j<tour.tourSize();j++)
    	{
    		
    	}
            
    	
    	
    }

    // Mutate a tour using Insert MUTATION
    private static void InsertMutation(Tour tour) {
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