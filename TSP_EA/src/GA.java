import java.util.ArrayList;



public class GA {

    /* GA parameters - Which operators are selected*/
    private static final double mutationRate = 0.2;   //Mutationrate for Multiple Exchange Mutation
    private static final int tournamentSize = 2;	//Tournament size for Tournament Selection
    private static boolean elitism;					//Elitism enable/unenabled
    private static boolean ox2Crossover;			// "    "
    private static boolean orderedCrossover;		//"    "
    private static boolean PMXCrossover;
    private static boolean CycleCrossover;
    private static boolean displacementM;
    private static boolean insertionM;
    private static boolean inversionM;
    private static boolean exchangeM;
    private static boolean multiexchangeM;
    
    //Constructor for enabling required operators
    public GA(boolean ox2C, boolean ordC, boolean pmxC, boolean cycC, boolean disM, boolean insM, boolean invM, boolean excM,boolean mexM, boolean elitism){
    	this.elitism=elitism;
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
    
    public static Population evolvePopulation(Population pop) {						// Evolves Population( Usage of all selected operators)
    
        Population newPopulation = new Population(pop.populationSize(), false);     //Create new population, no initialisation      
        int elitismOffset = 0;
        if (elitism) {																//Keep best tour elitism=true
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;							
        }

       
       if(ox2Crossover){
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {   //Loop through every tour of the population
    		   if ((z+1)<newPopulation.populationSize()) {							//If more than 2 tours are left, use Ox2-Crossover    		
    			   Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	        		Tour parent2 = tournamentSelection(pop);       					// Choose second parent chromosome with tournament selection
	        		Tour childs[]= Ox2Crossover(parent1,parent2);					//Receive offsprings in an Tour array
	        		Tour child1=childs[0];
	        		Tour child2=childs[1];            
	        		newPopulation.saveTour(z, child1);    							//Save first offspring
	        		newPopulation.saveTour((z+1),child2);    						//Save second offspring
	        		z=z+1;						
    		   }        	
        	else {																	// If one tour is left, use order crossover
          		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
                Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
                Tour child= OrderCrossover(parent1,parent2);						//Receive offspring
                newPopulation.saveTour(z, child);                    				// save offspring in new Population
        	}      	
    	  }
       }
       if(CycleCrossover){
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {   	 // Loop through all tours of population
    		   if ((z+1)<newPopulation.populationSize()) {       	       				//If more than 2 tours are left, use Cycle-Crossover  
    			   Tour parent1 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection         
		           Tour childs[]= CycleC(parent1,parent2);								//Receive offsprings in an Tour array with offsprings         
		           newPopulation.saveTour(z, childs[0]);        						//Save first offspring in new population
		           newPopulation.saveTour((z+1),childs[1]);          					//Save second offspring in new population
		           z=z+1;
		       	}
        	
    		   else {																	// If one tour is left, use order crossover
             		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
                   Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
                   Tour child= OrderCrossover(parent1,parent2);							//Receive offspring
                   newPopulation.saveTour(z, child);                    				// save offspring in new Population
           		}      	
    	   }
       }
       if(PMXCrossover) {      
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {		// Loop through all tours of population
    		   if ((z+1)<newPopulation.populationSize()) {        						//If more than 2 tours are left, use PMX-Crossover  
    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chomosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);	          					//Choose second parent chromosome with tournament selection
    			   Tour childs[]= PMX(parent1,parent2);									//Receive Tour array with offsprings            
    			   newPopulation.saveTour(z, childs[0]);        						//Save first offspring in new population
    			   newPopulation.saveTour((z+1),childs[1]);          					//Save second offspring in new population
    			   z=z+1;																
    		   }       	
    		   else {																	//If one tour is left use order crossover
    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chromosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);     						//Choose second parent chromosome with tournament selection         
    			   Tour child= OrderCrossover(parent1,parent2);							//receive offspring
    			   newPopulation.saveTour(z, child);                       				//save offspring
    		   }
    	   }
       }
       if(orderedCrossover) {       
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of population
               Tour parent1 = tournamentSelection(pop);									//Choose first parent chromosome with tournament selection
               Tour parent2 = tournamentSelection(pop);									//Choose second parent chromosome with tournament selection
               Tour child = OrderCrossover(parent1, parent2);							//receive offspring
               newPopulation.saveTour(i, child);										//Save offspring
           }
       }
       
       
       
       if(displacementM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {        //Loop through all tours of new population and use displacement mutation
        	   DisplacementMutation(newPopulation.getTour(i));
           }
       }
       if(multiexchangeM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use multiple exchange mutation
        	   MultipleExchangeMutation(newPopulation.getTour(i));
           }
       }
       if(exchangeM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use exchange mutation
           		ExchangeMutation(newPopulation.getTour(i));
           }
       }
       if(insertionM) {
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use insertion mutation
    		   InsertionMutation(newPopulation.getTour(i));
    	   }
       }
       if(inversionM) {				
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use inversion mutation
        	InversionMutation(newPopulation.getTour(i));
    	   }
       }
       return newPopulation;			
    }
    
   
    public static Tour OrderCrossover(Tour parent1, Tour parent2) {
      
        Tour child = new Tour();										//Create new tour
        int number1 = (int) (Math.random() * parent1.tourSize());		//create first random number
        int number2 = (int) (Math.random() * parent1.tourSize());		//
       	while(number1==number2)	{										//Falls Cuttingpoints gleich, wiederhole die Ziehung	
    		number1 = (int) (Math.random() * parent1.tourSize());
    		number2 = (int) (Math.random() * parent1.tourSize()); 
    		continue;
    	}
        int startPos= Math.min(number1, number2);
        int endPos= Math.max(number1, number2);        
        for (int i = 0; i < child.tourSize(); i++) {
            if (i >= startPos && i <= endPos) {						//Loop durch Substring und vertausche Substrings in den Childs
                child.setCity(i, parent1.getCity(i));
            } 
        }
        for (int i = 0; i < parent2.tourSize(); i++) {
            if (!child.containsCity(parent2.getCity(i))) {
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }
   
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2) {	//FUNKTIONIERT
    	Tour child1=new Tour();
    	Tour child2=new Tour();
    	Tour[] kids= new Tour[2];
    	int number1 = (int) (Math.random() * Run.getNumberofCities());	//First Cuttingpoint
    	int number2 = (int) (Math.random() * Run.getNumberofCities());	//Second Cuttingpoint
    	while(number1==number2)	{										//Falls Cuttingpoints gleich, wiederhole die Ziehung	
    		number1 = (int) (Math.random() * parent1.tourSize());
    		number2 = (int) (Math.random() * parent1.tourSize()); 
    		continue;
    	}
    	int startPos= Math.min(number1, number2);						//Minimum von number1,number2 ist der Beginn des Schnittpunkts
    	int endPos= Math.max(number1, number2);							//Maximum von number1,number2 ist der Ende des Schnittpunkts	
    	for(int j=0;j<parent1.tourSize();j++) {   	
    		if(j >= startPos && j <= endPos) {							//Loop durch Substring und vertausche Substrings in den Childs
    			City cityP1=parent1.getCity(j);
    			City cityP2=parent2.getCity(j);
    			child1.setCity(j, cityP2);
    			child2.setCity(j, cityP1);
    		}
    	}    	
    	for(int k=0;k<parent1.tourSize();k++) {    	
    		if (!child1.containsCity(parent1.getCity(k))) {   		
                for (int ii = 0; ii < child1.tourSize(); ii++)  {	// Loop um freie Position in ChildTour zu finden                  
                    if (child1.getCity(ii) == null)	{		 // Leere Stelle gefunden, füge Stadt hinzu                    
                    	City city1 = parent1.getCity(k);
                        child1.setCity(ii, city1);
                        break;
                    }
                }
            }
    	}
    	for(int k=0;k<parent2.tourSize();k++) {	
    		if (!child2.containsCity(parent2.getCity(k))) {	
                for (int ii = 0; ii < child2.tourSize(); ii++) {                             
                    if (child2.getCity(ii) == null) {      
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
    
    public static Tour[] PMX (Tour parent1, Tour parent2) { //Muss noch gemacht werden	
		int cut1 =(int) (Math.random() *parent1.tourSize());
		int cut2 = (int) (Math.random() *parent1.tourSize());
		Tour kids[]=new Tour[2];
		Tour child1=new Tour();
		Tour child2= new Tour();
		ArrayList<City> conflicts1= new ArrayList<City>();
		ArrayList<City> conflicts2= new ArrayList<City>();
		while (cut1 == cut2) {
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
		for(int i=0;i<parent1.tourSize();i++) {
			City c1= parent1.getCity(i);
			City c2= parent2.getCity(i);
			child1.setCity(i, c1);
			child2.setCity(i, c2);
		}
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			if(child1.containsCity(c2)) {	
				conflicts1.add(c2);
			}
			if(child2.containsCity(c1)) {		
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
		for(int x=0; x<parent2.tourSize();x++) {		
			City abc=parent2.getCity(x);
			p2Copy.setCity(x,abc);
		}
		while(conflicts1.isEmpty()==false) {
			City inter1;
			int pos1;
			int start1;
			do {			
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
		while(conflicts2.isEmpty()==false) {
			City inter2;
			int pos2;
			int start2;
			do {			
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

 
	 public static Tour[] CycleC(Tour parent1, Tour parent2) {
		Tour child1=new Tour();
	 	Tour child2=new Tour();
	 	City city1;
	 	City city2;
	 	Tour[] kids= new Tour[2];
	 	int rundenzähler=0;
	 	int position=0;
	 	int start=0;
	 	ArrayList<City> notvisited= new ArrayList<City>();
	 	for(int a=0; a<parent1.tourSize();a++) {		 
			 City ci= parent1.getCity(a);
			 notvisited.add(ci);
		 }
	 	//System.out.println(parent1);
	 	//System.out.println(parent2);	 	
	 	while(notvisited.isEmpty()==false) { 		
	 		if(rundenzähler%2==1) {
	 			for(int a=0; a<parent1.tourSize();a++) {				
					if(notvisited.contains(parent1.getCity(a))) {				
						start=a;
						position=start;
						break;
					}
				
				}
	 			do {	 					 				
	 				city1=parent1.getCity(position);
	 				child2.setCity(position, city1);
	 				 city2=parent2.getCity(position);
	 				 child1.setCity(position, city2);
	 				 position=parent1.positionofCity(city2);	 				 
	 				 if(notvisited.contains(city2)) {	 				  
	 					 notvisited.remove(city2);
	 				 }
	 				 if(notvisited.contains(city1)) { 				 
	 					 notvisited.remove(city1);
	 				 }
	 			}
	 			while(parent1.getCity(start)!=city2);
	 			rundenzähler++;
	 			continue;
			}
	 		
	 		if(rundenzähler==0) {
	 			do {			
	 				city1=parent1.getCity(position);
	 				child1.setCity(position, city1);
	 				city2=parent2.getCity(position);
	 				child2.setCity(position, city2);
	 				position=parent1.positionofCity(city2);	 				 
	 				if(notvisited.contains(city2)) {  				
	 					notvisited.remove(city2);
	 				}
	 				if(notvisited.contains(city1)) {			 
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
	 		if(rundenzähler%2==0&&rundenzähler!=0) {
	 			for(int a=0; a<parent1.tourSize();a++) { 			
	 				if(notvisited.contains(parent1.getCity(a))) {			
	 					start=a;
	 					position=start;
	 					break;
	 				}
	 			
	 			}
	 			do {	 			
	 				city1=parent1.getCity(position);
	 				child1.setCity(position, city1);
	 				 city2=parent2.getCity(position);
	 				 child2.setCity(position, city2);
	 				 position=parent1.positionofCity(city2);				 
	 				 if(notvisited.contains(city2)) {		  
	 					 notvisited.remove(city2);
	 				 }
	 				 if(notvisited.contains(city1)){
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
 
    private static void ExchangeMutation(Tour tour) {   	
    	int tourPos1 = (int) (tour.tourSize() * Math.random()); 	//Create two positions that should be swapped
        int tourPos2 = (int) (tour.tourSize() * Math.random());
        for(int i=0; i<100;i++) {      															//If same position, do again
        	if(tourPos1==tourPos2) {	
                tourPos1 = (int) (tour.tourSize() * Math.random());
                tourPos2 = (int) (tour.tourSize() * Math.random());
                continue;
            }
        	else {          
        		City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);
               	tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
                break;
            }              
        }
    }
    
    private static void InversionMutation(Tour tour) {  //FUNKTIONIERT	
    	Tour child= new Tour();
    	int number1 = (int) (Math.random() * tour.tourSize());
		int number2 = (int) (Math.random() * tour.tourSize());
    	for(int i=0; i<100;i++) { 		
    		if(number1==number2) {	
    			number1 = (int) (Math.random() * tour.tourSize());
    			number2 = (int) (Math.random() * tour.tourSize()); 
    			continue;
			}
			else {		
				break;
			}
    	}    	
    	int startPos= Math.min(number1, number2);
		int endPos= Math.max(number1, number2);
    	int b= endPos;
    	int d= startPos;
    	for(int a=startPos; a<b;a++) {	
    		City city1= tour.getCity(b);
    		child.setCity(a,city1);
    		b=b-1;
    	}
    	
    	for(int c=endPos;c>d;c--) {
    		City city1=tour.getCity(d);
    		child.setCity(c,city1);
    		d=d+1;
    	}
    	
    	for (int i = 0; i < tour.tourSize(); i++) {	
    		 if (!child.containsCity(tour.getCity(i))) {	 
    			 City city1 =tour.getCity(i);
    			 child.setCity(i, city1);
    		 }
    	}  	
    	tour=child;    		
    }
    private static void DisplacementMutation(Tour tour) {     //FUNKTIONIERT
    	Tour child = new Tour();    	
    	int number1 = (int) (Math.random() * Run.getNumberofCities());
    	int number2 = (int) (Math.random() * Run.getNumberofCities());
    	for(int i=0; i<100;i++) {   		
    		if(number1==number2) {  		
    			number1 = (int) (Math.random() * tour.tourSize());
    			number2 = (int) (Math.random() * tour.tourSize()); 
    			continue;
			}
			else {		
				break;
			}
    	}
    	int startPos= Math.min(number1, number2);
    	int endPos= Math.max(number1, number2);    	
    	int insertPos=(int) (Math.random() * (Run.getNumberofCities()-(endPos-startPos))); 	
    	int zähler=0;
    	for(int i=0; i<tour.tourSize();i++) { 	
    		if(i >= startPos && i <= endPos) { 		
    			City city1= tour.getCity(i);
    			child.setCity(insertPos+zähler, city1);
    			zähler+=1;
    		}
    	}
    	zähler=0;
    	for(int j=0;j<tour.tourSize();j++) {   	
    		if (!child.containsCity(tour.getCity(j))) {
                for (int ii = 0; ii < child.tourSize(); ii++) {
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
    
    private static void InsertionMutation(Tour tour)    {  	  	
    	int oldPos = (int) (Math.random() * Run.getNumberofCities());	//Randomly select a city and randomly create insertion position
    	int newPos = (int) (Math.random() * Run.getNumberofCities());   	
    	for(int i=0; i<100;i++)	 {									//If Position is the same, do again   
    		if(oldPos==newPos) {		
    			oldPos = (int) (tour.tourSize() * Math.random());
    			newPos = (int) (tour.tourSize() * Math.random());
    			continue;
    		}
    		else {   		
        	 break;
    		}
        }
    	City citytaken=tour.getCity(oldPos);
    	if(oldPos<newPos) { 	
    		for(int a=(oldPos+1); a<=newPos;a++) {  		
    			City city1=tour.getCity(a);
    			tour.setCity((a-1), city1);
    		}
    	}
    	if(oldPos>newPos) { 	
    		for(int b=(oldPos-1);b>=newPos;b--) {		
    			City city1=tour.getCity(b);
    			tour.setCity((b+1), city1);
    		}
    	}
    	tour.setCity(newPos, citytaken);
    }

    private static void MultipleExchangeMutation(Tour tour) {  
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            if(Math.random() < mutationRate){               
                int tourPos2 = (int) (tour.tourSize() * Math.random()) ;     // Get a second random position in the tour
                City city1 = tour.getCity(tourPos1); 						// Get the cities at target position in tour
                City city2 = tour.getCity(tourPos2);             
                tour.setCity(tourPos2, city1);								 // Swap them around
                tour.setCity(tourPos1, city2);
            }
        }
    }
 
    
   
    private static Tour tournamentSelection(Population pop) {				 // Selects candidate tour for crossover   
        Population tournament = new Population(tournamentSize, false);		 // Create a tournament population
        for (int i = 0; i < tournamentSize; i++) { 							// For each place in the tournament get a random candidate tour and add it
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest();								// Get the fittest tour
        return fittest;
    }
																		 
    private static Tour RWS(Population pop)									// Selects candidate tour for crossover
    {	Tour chosen=new Tour();
    	Tour inter=new Tour();
    	double slotsize1=0;
    	double slotsize2=0;
    	for(int i=0;i<pop.populationSize();i++) {
    		inter=pop.getTour(i);
    		slotsize1+=inter.getFitness();
    	} 	
    	double select=Math.random()*slotsize1;
    	for(int j=0; j<pop.populationSize();j++) {  	
    		inter=pop.getTour(j);
    		slotsize2+=inter.getFitness();
    		if(select<=slotsize2) {	
    			chosen=inter;
    			break;
    		}
    	}
    	
    	return chosen;
    }
    
}
