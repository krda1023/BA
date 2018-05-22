import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Salesman {
	private static ArrayList<AtEvent> upcomingEvents = new ArrayList<AtEvent>();
	private static ArrayList<AtEvent> pastEvents = new ArrayList<AtEvent>();
	public myEventHandler mEH= new myEventHandler();
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();
	TimeElement t = new TimeElement();
	
	public class myEventHandler implements myListener {
		

		@Override
		public void arrivedAtCity(AtEvent e) {
			if(e.getEventType()=="City"&&e.getEventTime()<getFahrtzeit())   //Ist das eine Dynamische abfrage oder hängt sich das auf
			{
				Tour BestTour=Run.getPop().getFittest();
				City nextCity=BestTour.getCity(1); // welche Stelle?
				// Hier muss Population repariert werden		 
			}
			
		}

		@Override
		public void arrivedAtWP(AtEvent e) {
			if(e.getEventType()=="WP"&&e.getEventTime()<getFahrtzeit())
			{
				
			}
			
		}
	
	
	}
	
	public long getStartzeit()
	{
		return t.gibMillis();
	}
	
	public long getFahrtzeit()
	{
		return t.getExistTime();
	}
	
	public synchronized void addListener(myListener lis)
	{
		listenerList.add(lis);
	}
	public synchronized void removeListener(myListener lis)
	 {
		 listenerList.remove(lis);
	 }
	 
	public void CreateAtCityEvent(String type, int ID, long Time)
	 {
		 AtEvent ae= new AtEvent(this,type,ID,Time);
		 upcomingEvents.add(ae);
		 
	 }
	 
	 public void CreateAtStepCityEvent(String type, int ID, long Time)
	 {
		AtEvent ae= new AtEvent(this, type, ID, Time);
		upcomingEvents.add(ae);
	 }
	
	public AtEvent checkForEvents()
	{	AtEvent currentEvent = null;
		long jetzt= System.currentTimeMillis();
		
		for(int i=0; i<upcomingEvents.size();i++)
		{
			if(jetzt>( upcomingEvents.get(i)).getEventTime())
			{
				pastEvents.add(upcomingEvents.get(i));
				currentEvent=upcomingEvents.get(i);
				break;

			}
			else
			{}
		}
		upcomingEvents.remove(currentEvent);
		return currentEvent;
		
	/*	if(getFahrtzeit()>((AtEvent) upcomingEvents.get(0)).getEventTime())
		{	//fire
			for(myListener lis: listenerList)
			{	AtEvent ae= (AtEvent)upcomingEvents.get(0);
				lis.arrivedAtCity(ae);
				lis.arrivedAtWP(ae);
				// übergib event an listener der daruaf reagiert sobald Fahrtzeit größer eventzeit
			}
		} */
	}
}
	
