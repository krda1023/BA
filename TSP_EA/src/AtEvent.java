import java.sql.Date;
import java.util.EventObject;

public class AtEvent extends EventObject {
	String EventType;
	int EventID;
	long EventTime;
	
	public AtEvent(Salesman Guy, String type, int ID, long Time) 
	{
		super(Guy);
		this.EventType=type;
		this.EventID=ID;
		this.EventTime=Guy.getStartzeit()+Guy.getFahrtzeit()+Time;
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
