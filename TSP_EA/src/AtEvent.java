import java.sql.Date;
import java.util.EventObject;



public class AtEvent extends EventObject {
	String EventType;
	int EventID;
	long EventTime;
	
	public AtEvent(Salesman salesman, String type, int ID, long Time) 
	{
		super(salesman);
		this.EventType=type;
		this.EventID=ID;
		this.EventTime=salesman.getStartzeit()+salesman.getFahrtzeit()+Time;
	}
	
	public long getEventTime()
	{
		return EventTime;
	}
	public String getEventType()
	{
		return EventType;
	}
}
