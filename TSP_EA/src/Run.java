import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Run {
		
	static int count=0;
	static boolean runs=true;
		
	public static void main(String[] args) throws Exception{
		
	
		GA Optimierer= new GA();								
	//	Optimierer.gui_start();
		Optimierer.Formalitäten();
		

	    
	/*	Salesman Guy= new Salesman();
		Guy.addListener(Optimierer);
		Optimierer.addRouteServiceListener(Guy);
		*/
	
        
       
     Optimierer.evolvePopulation(true);
     double d = GA.pop.getFittest().getDistance();
		
		//BERECHNE ERSTE LÖSUNG
     MyLogger log= new MyLogger();
     log.setLogger();
      for (int z = 0; z < GA.iterationen; z++) {
           Optimierer.evolvePopulation(false);
     
           
       log.writeInfo(Optimierer.best.getDistance(),Optimierer.best);
    
       }
    //  Optimierer.start();
  
       

        int counter=0;
    /*    do {
        	
            
            Optimierer.evolvePopulation(false);
            //     Guy.checkForEvents();
          
        }
        while(runs==true);
*/
        // Print final results
        System.out.println(("Initial duration : "+d));
        System.out.println("Final duration: " + GA.pop.getFittest().getDistance());
      	
       System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
       System.out.println("Anzahlanfragen"+Send_Request.anfragencounter);
       System.out.println("Solution:");
       System.out.println(GA.pop.getFittest()); 
       Date ende= new Date();
       System.out.print(ende);		
       //log.exit(); 
    }    
}
		