import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Salesman {
	private static ArrayList upcomingEvents = new ArrayList<AtEvent>();
	private static ArrayList pastEvents = new ArrayList<AtEvent>();
	public myEventHandler mEH= new myEventHandler();
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();
	public final Timestamp Startzeit = new Timestamp(System.currentTimeMillis());
	long startzeit= System.currentTimeMillis();
	
	public synchronized void addListener(myListener lis)
	{
		listenerList.add(lis);
	}
	public synchronized void removeListener(myListener lis)
	 {
		 listenerList.remove(lis);
	 }
	 
	public void CreateAtCityEvent(String type, int ID, Date Time)
	 {
		 AtEvent ae= new AtEvent(this,type,ID,Time);
		 
	 }
	 
	 public void CreateAtStepCityEvent(String type, int ID, Date Time)
	 {
		AtEvent ae= new AtEvent(this, type, ID, Time);
	
	 }
	
	public void checkForEvents()
	{	Date jetzt= new Date(0);
		if(jetzt.getTime()>((AtEvent) upcomingEvents.get(0)).getEventTime().getTime())
		{
			
		}
	}
}
