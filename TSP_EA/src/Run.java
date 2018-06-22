import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Run {
		
	
		
	public static void main(String[] args) throws Exception{
		
	
		GA Optimierer= new GA();								
	//	Optimierer.gui_start();
		Optimierer.Formalitäten();
		

	    
	/*	Salesman Guy= new Salesman();
		Guy.addListener(Optimierer);
		Optimierer.addRouteServiceListener(Guy);
		*/
	
           
System.out.print(GA.numOfCities);
       
     Optimierer.evolvePopulation(true);
  
		
		//BERECHNE ERSTE LÖSUNG
     MyLogger log= new MyLogger();
     log.setLogger();
       for (int z = 0; z < GA.iterationen; z++) {
           Optimierer.evolvePopulation(false);
      //     Guy.checkForEvents();
           
      //  log.writeInfo(Optimierer.best.getDistance(),Optimierer.best);
    
         
       }
       
       // 1 Directions Abfrage
          
      // Route route= new Route();
       //route.WayFromTo();
       
       
      
		
		
      /* Tour oldFittest=new Tour();
        Tour newFittest= new Tour();
        int counter=0;
        do {
        	oldFittest=pop.getFittest();
            pop = Algorithmus.evolvePopulation(pop);
            newFittest=pop.getFittest();
            if(newFittest.getDistance()==oldFittest.getDistance())
            {
            	counter++;
            }
            else
            {
            	counter=0;
            }
        }
        while(counter<2);*/

        // Print final results
      	System.out.println("Finished");
      	if(Optimierer.getfileLesen()==true) {
      		System.out.println("Final distance: " + Optimierer.pop.getFittest().getDistance());
      	}
      	if(Optimierer.getfileLesen()==false) {
      		System.out.println("Final duration: " + Optimierer.pop.getFittest().getDistance());
      	}
       System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
       System.out.println("Anzahlanfragen"+Send_Request.anfragencounter);
       System.out.println("Solution:");
       System.out.println(Optimierer.pop.getFittest()); 
       Date ende= new Date();
       System.out.print(ende);		
       //log.exit(); 
    }    
}
		