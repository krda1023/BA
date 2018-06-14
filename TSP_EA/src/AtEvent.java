import java.sql.Date;
import java.util.EventObject;



public class AtEvent extends EventObject {
	String EventType;
	
	long EventTime;
	double longitude;
	double latitude;
	
	public AtEvent(Salesman salesman, String type, long Time, double lon, double lat) {
		super(salesman);
		this.EventType=type;
		this.EventTime=Time;
		this.longitude=lon;
		this.latitude=lat;
		
	}
	
	public long getEventTime(){
		return EventTime;
	}
	public String getEventType(){
		return EventType;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
}
