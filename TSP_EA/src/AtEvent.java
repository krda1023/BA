import java.util.EventObject;

public class AtEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String status;
	long EventTime;
	City location;
	
	
	public AtEvent(Salesman salesman, City loc, long Time) {
		super(salesman);
		this.location=loc;
		this.EventTime=Time;
	
		
	}
	
	public long getEventTime(){
		return EventTime;
	}
	public String getEventType(){
		return location.type;
	}
	public double getLongitude() {
		return location.position[0];
	}
	public double getLatitude() {
		return location.position[1];
	}
}
