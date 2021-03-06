import com.opencsv.CSVWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;



// Main class with main method and main loop
//Initializes EA object "Optimierer" and Simulator object "Salesman" and starts the dynamic algorithm process and simulation
public class Run {

//VARIABLES:
	static int count=0;
	//status of dynamic process
	static boolean runs=false;
	static Tour lastbest;
	static Tour best;

//MAIN METHOD:
	public static void main(String[] args) throws Exception{
	TimeElement el= new TimeElement();
	String nameCSV="./"+el.toString()+".csv";
	ArrayList<String[]> CSVdata= new ArrayList<String[]>();
	String[] header= new String[] {"Iteration","totalduration","Fitness","Time","best Tour"};
	CSVdata.add(header);
	//Create new EA class object and create new Simulator class Object Salesman
	//start the preperation process: Matrix request, set Selection, Recombination and Mutation Operators
	//add MyListener to object "Optimierer" , add RouteServiceListener to object "Salesman"
		 PrintWriter pw = new PrintWriter(new File(nameCSV));
		 pw.close();
		
		EA Optimierer= new EA();	
		Simulator Salesman= new Simulator();
		//Optimierer.gui_start();
		Optimierer.Formalit�ten();
		
		Salesman.addListener(Optimierer);
		Optimierer.addRouteServiceListener(Salesman);
		
		//Initialize population, do first iteration and save initial duration
		Optimierer.evolvePopulation(true);
		best=EA.pop.getFittest();
		
		double d=best.getDuration();
	/*	for(int a=0;a<EA.pop.populationSize();a++) {
			log.writeConfig("Tour "+String.valueOf(a)+" : "+EA.pop.getTour(a).toString());
		}
		log.writeConfig("Population Fittest: "+EA.pop.getFittest().toString());*/	
			
		
		
		//Set up Logger for LogFiles
		//Calculate the first solution by number of iteration (iterations1) or by number of iterations without improved solution (iterations2)
	
     
//		log.writeInfo("time factor with gamma function: "+Maths.GammaFaktoren[0]+" "+Maths.GammaFaktoren[1]+" "+Maths.GammaFaktoren[2]+" "+)......;
		 long now1 = System.currentTimeMillis();
     if(EA.timeStop!=0) {
    	 TimeElement now= new TimeElement();
    	 long stop = now.startInMilli+EA.timeStop;
    	 do {
    		 
    		  Optimierer.evolvePopulation(false);
    		
        	   best=EA.pop.getFittest();
        	   
    	 }
    	 while(System.currentTimeMillis()<=stop);
     }
    
     else if(EA.iterations1!=0) {
     		for (int z = 0; z < EA.iterations1; z++) {
          		if(z>1) {
     			lastbest= new Tour(best);
          		}
          		/*for(int a=0; a<EA.pop.populationSize();a++) {
    	    	//	log.writeInfo("Nummer: "+String.valueOf(a)+ " "+EA.pop.getTour(a).toString());
    	    	}
          		*/
     			Optimierer.evolvePopulation(false);
     			best=EA.best;
     			long last=now1;
     			 now1 = System.currentTimeMillis();
     			
     			String[] dataset= new String[] {String.valueOf(z),String.valueOf(Maths.round(best.getDuration(),0)),String.valueOf(best.getFitness()),String.valueOf(now1-last),best.toString()};
     			CSVdata.add(dataset);
     		/*	for(int a=0; a<EA.pop.populationSize();a++) {
    	    	//	log.writeInfo("Nummer: "+String.valueOf(a)+ " "+EA.pop.getTour(a).toString());
    	    	}*/
     			//best=new Tour(EA.pop.getFittest());
          		/*if(z>1) {
     			//log.writeInfo("Iteration: "+String.valueOf(z)+" Best duration: "+String.valueOf(best.getDuration())+"  Lastbest duration: "+String.valueOf(lastbest.getDuration()));
          		//log.writeFinest("Lastbest: "+lastbest.toString());
          		//log.writeFinest("Best: "+best.toString());
          		}*/
     	
     		}
     	}
    
     else {
     	int counter =0;
        int rundenz�hler=0;
       do {
    	   lastbest= new Tour (best);
      	   Optimierer.evolvePopulation(false);
       	   best=EA.best;
         	   
       	   rundenz�hler++;
       	 
       	   if(rundenz�hler >2) {
       		;	
          	 
        	   if(best.getFitness()>lastbest.getFitness()) {
       			   counter=0;	
       			   System.out.println(best.getDuration()+" "+best);
       			   
//       			log.writeInfo("VERBESSERT!! : Iteration: "+String.valueOf(rundenz�hler)+" Best duration: "+String.valueOf(best.getDuration())+"  Lastbest duration: "+String.valueOf(lastbest.getDuration()));
//    			 log.writeFinest("Lastbest: "+lastbest.toString());
//           	log.writeFinest("Best: "+best.toString());
             		 
         			 
         		   }
         		   else if (best.getFitness()<lastbest.getFitness()) {////////WAS IST MIT DIESEM VERGLEICH
//         			  log.writeWarning("MISTAKE?!?!?: Iteration: "+String.valueOf(rundenz�hler)+" Best duration: "+String.valueOf(best.getDuration())+"  Lastbest duration: "+String.valueOf(lastbest.getDuration()));
//                 		log.writeFinest("Lastbest: "+lastbest.toString());
//                 		log.writeFinest("Best: "+best.toString());
         		   }
         	   else{
         		  counter++;  
         		  if(counter%1000==0) {
         			System.out.println(counter);  
         		  }
         	   }
         	//	 log.writeInfo("GLEICH: Iteration: "+String.valueOf(rundenz�hler)+" Best duration: "+String.valueOf(best.getDuration())+"  Lastbest duration: "+String.valueOf(lastbest.getDuration()));
           		//log.writeFinest("Lastbest: "+lastbest.toString());
           		//log.writeFinest("Best: "+best.toString());
           	  if(best.checkforOrderDiffrence(lastbest)) {
//     			  log.writeWarning("change in Order obwohl gleiche duration");
    		   }
         		   
         	   }
         	  
            }
         	  
         	
         	  
           
            while (counter<EA.iterations2); 
         
             
     	}
/*
 	  Optimierer.start();
         
       
        //Start dynamic algorithm process
      
     	
     	//Let Algorithm and Simulation run while runs==true
    	do {
            Optimierer.evolvePopulation(false);
            Salesman.checkForEvents();
           
          
		}
       while(runs==true);
   	*/
        // Print final results
        
 	
 	for(int a=0;a<EA.pop.populationSize();a++) {
		System.out.println("Tour "+String.valueOf(a)+" : "+EA.pop.getTour(a).toString());
	}
        System.out.println(("Initial duration : "+d));
        //System.out.println("Final duration: " + EA.pop.getFittest().getDuration());	
     
    
        System.out.println("Solution:");
        System.out.println(EA.pop.getFittest()); 
        System.out.println(EA.pop.getFittest().getDuration());
        TimeElement ende = new TimeElement();
        System.out.print(ende);
        try (
        		Writer writer = Files.newBufferedWriter(Paths.get(nameCSV));
        	     CSVWriter csvWriter = new CSVWriter(writer,
        	                    CSVWriter.DEFAULT_SEPARATOR,
        	                    CSVWriter.NO_QUOTE_CHARACTER,
        	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        	                    CSVWriter.DEFAULT_LINE_END);
        	        ) 
        	{
        	          for(String[] s: CSVdata) {

        	            csvWriter.writeNext(s);
        	            
        	       
        	        }
        	}
    }    
}






		