import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Salesman {
	private static ArrayList upcomingEvents = new ArrayList<AtEvent>();
	private static ArrayList pastEvents = new ArrayList<AtEvent>();
	public myEventHandler mEH= new myEventHandler();
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();
	public final Timestamp Startzeit = new Timestamp(System.currentTimeMillis());
	static long startzeit= System.currentTimeMillis();
	
	public long getStartzeit()
	{
		return startzeit;
	}
	
	public static long getFahrtzeit()
	{
		long jetzt=System.currentTimeMillis();
		long erg= jetzt-startzeit;
		return erg;
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
	
	public void checkForEvents()
	{	long jetzt= System.currentTimeMillis();
		if(getFahrtzeit()>((AtEvent) upcomingEvents.get(0)).getEventTime())
		{	//fire
			for(myListener lis: listenerList)
			{	AtEvent ae= (AtEvent)upcomingEvents.get(0);
				lis.arrivedAtCity(ae);
				lis.arrivedAtWP(ae);
				// übergib event an listener der daruaf reagiert sobald Fahrtzeit größer eventzeit
			}
		}
	}
}
