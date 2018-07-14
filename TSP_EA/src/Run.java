// Main class with main method and main loop
//Initializes EA object "Optimierer" and Simulator object "Guy" and starts the dynamic algorithm process and simulation
public class Run {

//VARIABLES:
	static int count=0;
	//status of dynamic process
	static boolean runs=false;
	static Tour lastbest=null;
	static Tour best;

//MAIN METHOD:
	public static void main(String[] args) throws Exception{
		
	//Create new EA class object and create new Simulator class Object Guy
	//start the preperation process: Matrix request, set Selection, Recombination and Mutation Operators
	//add MyListener to object "Optimierer" , add RouteServiceListener to object "Guy"
		EA Optimierer= new EA();	
		Simulator Guy= new Simulator();
		//Optimierer.gui_start();
		Optimierer.Formalitäten();
		Guy.addListener(Optimierer);
		Optimierer.addRouteServiceListener(Guy);
		
		//Initialize population, do first iteration and save initial duration
		Optimierer.evolvePopulation(true);
		double d = EA.pop.getFittest().getDuration();
		
		//Set up Logger for LogFiles
		//Calculate the first solution by number of iteration (iterations1) or by number of iterations without improved solution (iterations2)
		MyLogger log= new MyLogger();
     	log.setLogger();
     	
     	if(EA.iterations1!=0) {
     		for (int z = 0; z < EA.iterations1; z++) {
          		Optimierer.evolvePopulation(false);
           		log.writeInfo(Optimierer.best.getDuration(),Optimierer.best);
           	}
     	}
     	
     	else {
     	   int counter =0;
           int rundenzähler=0;
           do {
        	   Optimierer.evolvePopulation(false);
         	   best=EA.pop.getFittest();
         	   rundenzähler++;
         	   // System.out.println("Runde: "+rundenzähler+" Beste Duration "+best.getDuration()); 
         	   if(rundenzähler >2) {
         		   if(best.getFitness()==lastbest.getFitness()) {
         			   counter++;
         			   if(lastbest.getCity(1)!=best.getCity(1)) {
         				   //System.out.println(" Wechsel der ZielCity in Runde obwohl gleiche Fitness: "+rundenzähler);
         				   //System.out.println("last best : fitness"+ lastbest.getDuration()+" "+lastbest);
         				   //System.out.println("best: fitness"+best.getDuration()+" "+ best);
         			   }
         		   }
         	   }
         	   else{
         		   counter=0;
         		   // System.out.println("Change at round "+rundenzähler+ " new duration: "+ best.getDuration());
         		   //System.out.println("New best: "+ best.toString());
         	   }
         	   lastbest=best;
            }
            while (counter<EA.iterations2); 
     	}
		
         
       
        //Start dynamic algorithm process
        Optimierer.start();
     	
     	//Let Algorithm and Simulation run while runs==true
        do {
            TimeElement start = new TimeElement();
            System.out.print(start.start);
            Optimierer.evolvePopulation(false);
            Guy.checkForEvents();
          
        }
        while(runs==true);
   	
        // Print final results
     	for(int ff=0; ff<EA.pop.populationSize();ff++) {
     		System.out.println(EA.pop.getTour(ff));
     	}
        System.out.println(("Initial duration : "+d));
        System.out.println("Final duration: " + EA.pop.getFittest().getDuration());	
        System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
        System.out.println("Anzahlanfragen"+Send_Request.anfragencounter);
        System.out.println("Solution:");
        System.out.println(EA.pop.getFittest()); 
        TimeElement ende = new TimeElement();
        System.out.print(ende.start);
    }    
}
		