import java.util.ArrayList;

import javax.swing.event.EventListenerList;

public class myEventHandler implements myListener {
	

	@Override
	public void arrivedAtCity(AtEvent e) {
		if(e.getEventType()=="City"&&e.getEventTime()<Salesman.getFahrtzeit())   //Ist das eine Dynamische abfrage oder hängt sich das auf
		{
			
		}
		
	}

	@Override
	public void arrivedAtWP(AtEvent e) {
		if(e.getEventType()=="WP"&&e.getEventTime()<Salesman.getFahrtzeit())
		{
			
		}
		
	}

	
}
