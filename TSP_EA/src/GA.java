import java.util.ArrayList;



public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.2;
    private static final int tournamentSize = 2;
    private static boolean elitism;
    private static boolean ox2Crossover;
    private static boolean orderedCrossover;
    private static boolean PMXCrossover;
    private static boolean CycleCrossover;
    private static boolean displacementM;
    private static boolean insertionM;
    private static boolean inversionM;
    private static boolean exchangeM;
    private static boolean multiexchangeM;
    
    public GA(boolean ox2C, boolean ordC, boolean pmxC, boolean cycC, boolean disM, boolean insM, boolean invM, boolean excM,boolean mexM, boolean elitism)
    {	this.elitism=elitism;
    	this.ox2Crossover=ox2C;
    	this.orderedCrossover=ordC;
    	this.PMXCrossover=pmxC;
    	this.CycleCrossover=cycC;
    	this.displacementM=disM;
    	this.insertionM=insM;
    	this.inversionM=invM;
    	this.exchangeM=excM;
    	this.multiexchangeM=mexM;
    }
    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
    
        Population newPopulation = new Population(pop.populationSize(), false);
      
        // Keep our best individual if elitism is enabled
        
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        //Crossover Population
       if(ox2Crossover)
       {
        for (int z = 0; z < newPopulation.populationSize(); z++) 
        {
        	
        	if ((z+1)<newPopulation.populationSize())
        	{
        		
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
          
           Tour childs[]= Ox2Crossover(parent1,parent2);
            Tour child1=childs[0];
           Tour child2=childs[1];            
            newPopulation.saveTour(z, child1);        
            newPopulation.saveTour((z+1),child2);          
            z=z+1;
        	}
        	
        	else 
        	{
        		Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
               
                Tour child= OrderCrossover(parent1,parent2);
                newPopulation.saveTour(z, child);
               
        	
        	}
        	
        }
       }
       if(CycleCrossover)
       {
        for (int z = 0; z < newPopulation.populationSize(); z++) 
        {
        	
        	if ((z+1)<newPopulation.populationSize())
        	{
        		
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
          
            Tour childs[]= CycleC(parent1,parent2);
            Tour child1=childs[0];
            Tour child2=childs[1];            
            newPopulation.saveTour(z, child1);        
            newPopulation.saveTour((z+1),child2);          
            z=z+1;
        	}
        	
        	else 
        	{
        		Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
               
                Tour child= OrderCrossover(parent1,parent2);
                newPopulation.saveTour(z, child);
               
        	
        	}
        	
        }
       }
       if(PMXCrossover)
       {
        for (int z = 0; z < newPopulation.populationSize(); z++) 
        {
        	
        	if ((z+1)<newPopulation.populationSize())
        	{
        		
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
          
            Tour childs[]= PMX(parent1,parent2);
            Tour child1=childs[0];
            Tour child2=childs[1];            
            newPopulation.saveTour(z, child1);        
            newPopulation.saveTour((z+1),child2);          
            z=z+1;
        	}
        	
        	else 
        	{
        		Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
               
                Tour child= OrderCrossover(parent1,parent2);
                newPopulation.saveTour(z, child);
               
        	
        	}
        	
        }
       }
       if(orderedCrossover)
       {
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
               // Select parents
               Tour parent1 = RWS(pop);
               Tour parent2 = RWS(pop);
               // Crossover parents
               Tour child = OrderCrossover(parent1, parent2);
               // Add child to new population
               newPopulation.saveTour(i, child);
           }
       }
       
       
        // Mutate the new population a bit to add some new genetic material 
       if(displacementM)
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++)
           {
        	   DisplacementMutation(newPopulation.getTour(i));


           }
       if(multiexchangeM)
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++)
           {
        	   MultipleExchangeMutation(newPopulation.getTour(i));


           }
       if(exchangeM)
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++)
           {
           	ExchangeMutation(newPopulation.getTour(i));


           }
       if(insertionM)
       for (int i = elitismOffset; i < newPopulation.populationSize(); i++)
       {
       	InsertionMutation(newPopulation.getTour(i));


       }
       if(inversionM)
       {
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++)
        {
        	InversionMutation(newPopulation.getTour(i));


        }
       }

    
        return newPopulation;
    }
    

   
    public static Tour OrderCrossover(Tour parent1, Tour parent2) {
      
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
   
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2)	//FUNKTIONIERT
    {	
    	Tour child1=new Tour();
    	Tour child2=new Tour();
    	Tour[] kids= new Tour[2];
    	int number1 = (int) (Math.random() * Run.getNumberofCities());
    	int number2 = (int) (Math.random() * Run.getNumberofCities());
    	for(int i=0; i<100;i++)
    	{	
    		if(number1==number2)
    		{
    			number1 = (int) (Math.random() * parent1.tourSize());
    			number2 = (int) (Math.random() * parent1.tourSize()); 
    			continue;
			}
			else
			{
				break;
			}
    	}
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);
    	
    	for(int j=0;j<parent1.tourSize();j++)
    	{
    		if(j >= startPos && j <= endPos)
    		{
    			City cityP1=parent1.getCity(j);
    			City cityP2=parent2.getCity(j);
    			child1.setCity(j, cityP2);
    			child2.setCity(j, cityP1);
    		}
    	}
    	
    	for(int k=0;k<parent1.tourSize();k++)
    	{
    		if (!child1.containsCity(parent1.getCity(k)))
    		{
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child1.tourSize(); ii++) 
                {
                    // Spare position found, add city
                    if (child1.getCity(ii) == null)
                    {
                    	City city1 = parent1.getCity(k);
                        child1.setCity(ii, city1);
                        break;
                    }
                }
            }
    	}
    	for(int k=0;k<parent2.tourSize();k++)
    	{
    		if (!child2.containsCity(parent2.getCity(k)))
    		{
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child2.tourSize(); ii++) 
                {
                    // Spare position found, add city
                    if (child2.getCity(ii) == null)
                    {
                    	City city2 = parent2.getCity(k);
                        child2.setCity(ii, city2);
                        break;
                    }
                }
            }
    	}
    	
    	kids[0]=child1;
    	kids[1]=child2;
    	return kids;
    }
    
 public static Tour[] PMX (Tour parent1, Tour parent2) //Muss noch gemacht werden
 {	
	int cut1 =(int) (Math.random() *parent1.tourSize());
	int cut2 = (int) (Math.random() *parent1.tourSize());
	Tour kids[]=new Tour[2];
	Tour child1=new Tour();
	Tour child2= new Tour();
	ArrayList<City> conflicts1= new ArrayList<City>();
	ArrayList<City> conflicts2= new ArrayList<City>();
	while (cut1 == cut2)
	{
		cut1 =(int) (Math.random() *parent1.tourSize());
		cut2 = (int) (Math.random() *parent1.tourSize());
	}
	
	if (cut1 > cut2) {
		int swap = cut1;
		cut1 = cut2;
		cut2 = swap;
	}
	System.out.println(cut1);
	System.out.println(cut2);
	for(int i=0;i<parent1.tourSize();i++)
	{
		City c1= parent1.getCity(i);
		City c2= parent2.getCity(i);
		child1.setCity(i, c1);
		child2.setCity(i, c2);
	}
	for(int j=cut1;j<=cut2;j++)
	{
		City c1= parent1.getCity(j);
		City c2= parent2.getCity(j);
		if(child1.containsCity(c2))
		{
			conflicts1.add(c2);
		}
		if(child2.containsCity(c1))
		{
			conflicts2.add(c1);
		}
		child1.setCity(j, c2);
		child2.setCity(j, c1);
	}
	System.out.println("p1: "+parent1);
	System.out.println("p2: "+parent2);
	System.out.println("c1: "+child1);
	System.out.println("c2: "+child2);
	Tour p2Copy=new Tour();
	for(int x=0; x<parent2.tourSize();x++)
	{
		City abc=parent2.getCity(x);
		p2Copy.setCity(x,abc);
	}
	while(conflicts1.isEmpty()==false)
	{	City inter1;
		int pos1;
		int start1;
		do
		{
			System.out.println();
			pos1= parent2.positionofCity(conflicts1.get(0));
			start1= parent2.positionofCity(conflicts1.get(0));
			inter1=parent1.getCity(pos1);
			pos1=parent2.positionofCity(inter1);
		}
		while(!(inter1.equals(parent2.getCity(pos1))));
		parent2.setCity(pos1, conflicts1.get(0));
		parent2.setCity(start1, inter1);
		child1.setCity(pos1, conflicts1.get(0));
		child1.setCity(start1, inter1);
		conflicts1.remove(0);
	}
	System.out.println("p2: "+parent2);
	System.out.println("c1: "+child1);
	while(conflicts2.isEmpty()==false)
	{	City inter2;
		int pos2;
		int start2;
		do
		{
			pos2= parent1.positionofCity(conflicts2.get(0));
			start2= parent1.positionofCity(conflicts2.get(0));
			inter2=p2Copy.getCity(pos2);
			pos2=parent1.positionofCity(inter2);
		}
		while(!(inter2.equals(parent1.getCity(pos2))));
		parent1.setCity(pos2, conflicts2.get(0));
		parent1.setCity(start2, inter2);
		child2.setCity(pos2, conflicts2.get(0));
		child2.setCity(start2, inter2);
		conflicts2.remove(0);
	}
	System.out.println("p1: "+parent1);
	System.out.println("c2: "+child2);
	System.out.println();
	System.out.println();
	kids[0]=child1;
	kids[1]=child2;
	 return kids;
 }

 
 public static Tour[] CycleC(Tour parent1, Tour parent2)
 {
	Tour child1=new Tour();
 	Tour child2=new Tour();
 	City city1;
 	City city2;
 	Tour[] kids= new Tour[2];
 	int rundenzähler=0;
 	int position=0;
 	int start=0;
 	ArrayList<City> notvisited= new ArrayList<City>();
 	 for(int a=0; a<parent1.tourSize();a++)
	 {
		 City ci= parent1.getCity(a);
		 notvisited.add(ci);
	 }
 	//System.out.println(parent1);
		//System.out.println(parent2);
 	
 	while(notvisited.isEmpty()==false)
 	{	
 		if(rundenzähler%2==1)
		{//System.out.println("Test2");
 			for(int a=0; a<parent1.tourSize();a++)
			{
				if(notvisited.contains(parent1.getCity(a)))
				{
					start=a;
					position=start;
					break;
				}
			
			}
 			do
 			{	
 				
 				city1=parent1.getCity(position);
 				child2.setCity(position, city1);
 				 city2=parent2.getCity(position);
 				 child1.setCity(position, city2);
 				 position=parent1.positionofCity(city2);
 				 
 				 if(notvisited.contains(city2))
 				 { 
 					 notvisited.remove(city2);
 				 }
 				 if(notvisited.contains(city1))
 				 {
 					 notvisited.remove(city1);
 				 }
 			//	System.out.println(child1);
 	 			//System.out.println(child2);	 
 	 		//	System.out.println("next Position: " +position);
 			}
 			while(parent1.getCity(start)!=city2);
 			rundenzähler++;
 			continue;
		}
 		
 		if(rundenzähler==0)
 		{//System.out.println("Test1");
 			do
 			{
 				city1=parent1.getCity(position);
 				child1.setCity(position, city1);
 				 city2=parent2.getCity(position);
 				 child2.setCity(position, city2);
 				 position=parent1.positionofCity(city2);
 				 
 				 if(notvisited.contains(city2))
 				 { 
 					 notvisited.remove(city2);
 				 }
 				 if(notvisited.contains(city1))
 				 {
 					 notvisited.remove(city1);
 				 }
 			//	System.out.println(child1);
 	 			//System.out.println(child2);		
 	 			//System.out.println("next position: "+position);
 			}
 			while(parent1.getCity(start)!=city2);
 			rundenzähler++;
 			continue;
 			
 		}
 		
 		if(rundenzähler%2==0&&rundenzähler!=0)
 		{//System.out.println("Test3");
 			for(int a=0; a<parent1.tourSize();a++)
 			{
 				if(notvisited.contains(parent1.getCity(a)))
 				{
 					start=a;
 					position=start;
 					break;
 				}
 			
 			}
 			
 			do
 			{
 				city1=parent1.getCity(position);
 				child1.setCity(position, city1);
 				 city2=parent2.getCity(position);
 				 child2.setCity(position, city2);
 				 position=parent1.positionofCity(city2);
 				 
 				 if(notvisited.contains(city2))
 				 { 
 					 notvisited.remove(city2);
 				 }
 				 if(notvisited.contains(city1))
 				 {
 					 notvisited.remove(city1);
 				 }
 				//System.out.println(child1);
 	 			//System.out.println(child2);	
 			}
 			while(parent1.getCity(start)!=city2);
 			rundenzähler++;
 			
 			continue;
 		}
 		
 	}
 	kids[0]=child1;
 	kids[1]=child2;
 	//System.out.println("Ende");
	//System.out.println();
	return kids;
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
    	
    	int insertPos=(int) (Math.random() * (Run.getNumberofCities()-(endPos-startPos)));
    	
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
 // Selects candidate tour for crossover
    private static Tour RWS(Population pop)
    {	Tour chosen=new Tour();
    	Tour inter=new Tour();
    	double slotsize1=0;
    	double slotsize2=0;
    	for(int i=0;i<pop.populationSize();i++)
    	{	inter=pop.getTour(i);
    		slotsize1+=inter.getFitness();
    	}
    	
    	double select=Math.random()*slotsize1;
    	for(int j=0; j<pop.populationSize();j++)
    	{
    		inter=pop.getTour(j);
    		slotsize2+=inter.getFitness();
    		if(select<=slotsize2)
    		{
    			chosen=inter;
    			break;
    		}
    	}
    	
    	return chosen;
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




