public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.2;
    private static final int tournamentSize = 2;
    private static final boolean elitism = false;
    

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
    
        Population newPopulation = new Population(pop.populationSize(), false);
       //System.out.println(pop.populationSize());
   //   System.out.println(newPopulation.populationSize());
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
        	
        	/*if ((z+1)<newPopulation.populationSize())
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
            System.out.println("Zähler: "+z);
        }
        	
        	else 
        	{*/
        		Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
               // Tour child = Ox2Crossover(parent1, parent2);
                Tour child= OrderCrossover(parent1,parent2);
                newPopulation.saveTour(z, child);
                
               
        	
        }
        // Mutate the new population a bit to add some new genetic material 
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
        	DisplacementMutation(newPopulation.getTour(i));


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
    
 public static Tour PMX (Tour parent1, Tour parent2)
 {	Tour child=null;
	 return child;
 }
   
    private static void ExchangeMutation(Tour tour) //FUNKTIONIERT
    {	//Create two positions that should be swapped
    	int tourPos1 = (int) (tour.tourSize() * Math.random());
        int tourPos2 = (int) (tour.tourSize() * Math.random());
        for(int i=0; i<100;i++)
        {	//If same position, do again
        	if(tourPos1==tourPos2)
        	{
                tourPos1 = (int) (tour.tourSize() * Math.random());
                tourPos2 = (int) (tour.tourSize() * Math.random());
                continue;
            }
        	else
            {
        		City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);
               	tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
                break;
            }              
        }
    }
    
    private static void InversionMutation(Tour tour)   //FUNKTIONIERT
    {	
    	Tour child= new Tour();
    	int number1 = (int) (Math.random() * tour.tourSize());
		int number2 = (int) (Math.random() * tour.tourSize());
    	for(int i=0; i<100;i++)
    	{	
    		if(number1==number2)
    		{
    			number1 = (int) (Math.random() * tour.tourSize());
    			number2 = (int) (Math.random() * tour.tourSize()); 
    			continue;
			}
			else
			{
				break;
			}
    	}
    	
    	int startPos= Math.min(number1, number2);
		int endPos= Math.max(number1, number2);
    	int b= endPos;
    	int d= startPos;
    	for(int a=startPos; a<b;a++)
    	{
    		City city1= tour.getCity(b);
    		child.setCity(a,city1);
    		b=b-1;
    	}
    	
    	for(int c=endPos;c>d;c--)
    	{	City city1=tour.getCity(d);
    		child.setCity(c,city1);
    		d=d+1;
    	}
    	
    	for (int i = 0; i < tour.tourSize(); i++)
    	{
    		 if (!child.containsCity(tour.getCity(i)))
    		 {
    			 City city1 =tour.getCity(i);
    			 child.setCity(i, city1);
    		 }
    	}
    	
    	tour=child;
    		
    }
    
    private static void DisplacementMutation(Tour tour)     //FUNKTIONIERT
    {	Tour child = new Tour();
    	System.out.println(tour);
    	int number1 = (int) (Math.random() * Run.getNumberofCities());
    	int number2 = (int) (Math.random() * Run.getNumberofCities());
    	for(int i=0; i<100;i++)
    	{	
    		if(number1==number2)
    		{
    			number1 = (int) (Math.random() * tour.tourSize());
    			number2 = (int) (Math.random() * tour.tourSize()); 
    			continue;
			}
			else
			{
				break;
			}
    	}
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);
    	System.out.println(startPos);
    	System.out.println(endPos);
    	int insertPos=(int) (Math.random() * (Run.getNumberofCities()-(endPos-startPos)));
    	System.out.println(insertPos);
    	int zähler=0;
    	for(int i=0; i<tour.tourSize();i++)
    	{
    		if(i >= startPos && i <= endPos)
    		{
    			City city1= tour.getCity(i);
    			child.setCity(insertPos+zähler, city1);
    			zähler+=1;
    		}
    	}
    	zähler=0;
    	for(int j=0;j<tour.tourSize();j++)
    	{
    		if (!child.containsCity(tour.getCity(j))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                    	City city1 = tour.getCity(j);
                        child.setCity(ii, city1);
                        break;
                    }
                }
            }
    	}
    	tour=child;
    	System.out.println(tour);
    	System.out.println();
    }
    
    private static void InsertionMutation(Tour tour)   //FUNKTIONIERT
    {	
    	//Randomly select a city and randomly create insertion position
    	int oldPos = (int) (Math.random() * Run.getNumberofCities());
    	int newPos = (int) (Math.random() * Run.getNumberofCities());
    	//If Position is the same, do again
    	for(int i=0; i<100;i++)
        {
    		if(oldPos==newPos)
    		{
    			oldPos = (int) (tour.tourSize() * Math.random());
    			newPos = (int) (tour.tourSize() * Math.random());
    			continue;
    		}
    		else
    		{
        	 break;
    		}
        }
    	City citytaken=tour.getCity(oldPos);
    	if(oldPos<newPos)
    	{
    		for(int a=(oldPos+1); a<=newPos;a++)
    		{
    			City city1=tour.getCity(a);
    			tour.setCity((a-1), city1);
    		}
    	}
    	if(oldPos>newPos)
    	{
    		for(int b=(oldPos-1);b>=newPos;b--)
    		{
    			City city1=tour.getCity(b);
    			tour.setCity((b+1), city1);
    		}
    	}
    	tour.setCity(newPos, citytaken);
    	System.out.println(tour);
    	System.out.println();
    }

    private static void MultipleExchangeMutation(Tour tour) {  //FUNKTIONIERT
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










//Applies One-Point crossover to a set of parents and creates 2 offsprings

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
/*public static Tour[] Ox2Crossover(Tour parent1, Tour parent2)
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
		
	System.out.println(parent1.tourSize());
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
      }
      
    childs[0]=child1;
    childs[1]=child2;
    System.out.println(child1);
	System.out.println(child2);
	return childs;
}



// Mutate a tour using Insert MUTATION
private static void MultipleExchangeMutation(Tour tour) {
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
*/