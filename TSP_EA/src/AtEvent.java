import java.sql.Date;
import java.util.EventObject;

public class AtEvent extends EventObject {
	String EventType;
	int EventID;
	Date EventTime;
	
	public AtEvent(Salesman Guy, String type, int ID, Date Time) 
	{
		super(Guy);
		this.EventType=type;
		this.EventID=ID;
		this.EventTime=Time;
	}
	
	public Date getEventTime()
	{
		return EventTime;
	}
}
