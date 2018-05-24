import java.util.ArrayList;

import javax.swing.event.EventListenerList;

public class myEventHandler implements myListener {
	

	@Override
	public void arrivedAtCity(AtEvent e) {
		if(e.getEventType()=="City"&&e.getEventTime()<Salesman.getFahrtzeit())   //Ist das eine Dynamische abfrage oder hängt sich das auf
		{
			Tour BestTour=Run.getPop().getFittest();
			City nextCity=BestTour.getCity(1); // welche Stelle?
			// Hier muss Population repariert werden		 
		}
		
	}

	@Override
	public void arrivedAtWP(AtEvent e) {
		if(e.getEventType()=="WP"&&e.getEventTime()<Salesman.getFahrtzeit())
		{
			
		}
		
	}

	
}
