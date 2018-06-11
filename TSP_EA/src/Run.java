import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Run {
		
		static Population pop;
		
		public static Population getPop() {
			return pop;
		}
		
	public static void main(String[] args) throws Exception{
		
	
		GA Optimierer= new GA();
		Optimierer.gui_start();
		
		Optimierer.Formalitäten();
		
		pop = new Population(Optimierer.anzahlstaedte, true);
        if(Optimierer.getfileLesen()==true){
        	System.out.println("Initial distance: " + pop.getFittest().getDistance());
        }
	    if(Optimierer.getfileLesen()==false){
	       	System.out.println("Initial duration: " + pop.getFittest().getDistance());
	    }
	    
		Salesman Guy= new Salesman();
		Guy.addListener(Optimierer);
		
	
           

       
        pop = Optimierer.evolvePopulation(pop);
  
		
		//BERECHNE ERSTE LÖSUNG
     MyLogger log= new MyLogger();
     log.setLogger();
       for (int z = 0; z < Optimierer.iterationen; z++) {
           pop = Optimierer.evolvePopulation(pop);
    
           
        log.writeInfo(pop.getFittest().getDistance(),pop.getFittest());
    
         
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
      		System.out.println("Final distance: " + pop.getFittest().getDistance());
      	}
      	if(Optimierer.getfileLesen()==false) {
      		System.out.println("Final duration: " + pop.getFittest().getDistance());
      	}
       System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
       System.out.println("Anzahlanfragen"+Send_Request.getAnfragencounter());
       System.out.println("Solution:");
       System.out.println(pop.getFittest()); 
       Date ende= new Date();
       System.out.print(ende);		
       //log.exit(); 
    }    
}
		