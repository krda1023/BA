import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Run {
		
	static int count=0;
	static boolean runs=false;
	static Tour lastbest=null;
	static Tour best;
	public static void main(String[] args) throws Exception{
		
	
		GA Optimierer= new GA();								
	//Optimierer.gui_start();
		Optimierer.Formalit�ten();
		

	    
	/*	Salesman Guy= new Salesman();
		Guy.addListener(Optimierer);
		Optimierer.addRouteServiceListener(Guy);
		*/
	
        
       
     Optimierer.evolvePopulation(true);
     double d = GA.pop.getFittest().getDuration();
		
		//BERECHNE ERSTE L�SUNG
     /*MyLogger log= new MyLogger();
     log.setLogger();
      for (int z = 0; z < GA.iterationen; z++) {
           Optimierer.evolvePopulation(false);
     
           
       log.writeInfo(Optimierer.best.getDuration(),Optimierer.best);
    
       }*/
    //  Optimierer.start();
      int counter12 =0;
      int rundenz�hler=0;
       do {
    	   Optimierer.evolvePopulation(false);
    	   best=GA.pop.getFittest();
    	   rundenz�hler++;
    	  // System.out.println("Runde: "+rundenz�hler+" Beste Duration "+best.getDuration());
    
    	   
    	   if(rundenz�hler >2) {
    	   if(best.getFitness()==lastbest.getFitness()) {
    		   counter12++;
    		   if(lastbest.getCity(1)!=best.getCity(1)) {
      		 	 System.out.println(" Wechsel der ZielCity in Runde obwohl gleiche Fitness: "+rundenz�hler);
      		 	 //System.out.println("last best : fitness"+ lastbest.getDuration()+" "+lastbest);
      		 	 //System.out.println("best: fitness"+best.getDuration()+" "+ best);
      	   }	}
    	   }
    	   else{
    		   counter12=0;
    		   System.out.println("Change at round "+rundenz�hler+ " new duration: "+ best.getDuration());
    		   //System.out.println("New best: "+ best.toString());
    	   }
    	   
    	   lastbest=best;
    	   
       }
       while (counter12<1000);       
        int counter=0;
        
        /*do {
        	
            
            Optimierer.evolvePopulation(false);
            //     Guy.checkForEvents();
          
        }
        while(runs==true);
*/
        // Print final results
        
        
    for(int ff=0; ff<GA.pop.populationSize();ff++) {
    	System.out.println(GA.pop.getTour(ff));
    }
        System.out.println(("Initial duration : "+d));
        System.out.println("Final duration: " + GA.pop.getFittest().getDuration());
      	
       System.out.println("Anzahl St�dte: "+All_Cities.numberOfCities());
       System.out.println("Runden: "+ rundenz�hler);;
       System.out.println("Anzahlanfragen"+Send_Request.anfragencounter);
       System.out.println("Solution:");
       System.out.println(GA.pop.getFittest()); 
       Date ende= new Date();
       System.out.print(ende);		
       //log.exit(); 
    }    
}
		