import java.util.Date;

public class TimeElement
{
	Date start;
	long startInMilli;
	
	public TimeElement()
	{
		 this.start= new Date();
		this.startInMilli= start.getTime();
	}
	
	public TimeElement(Date d, long t)
	{
		this.start=d;
		this.startInMilli=t;
	}
	
	public Date gibZeit()
	{
		return start;
	}
	public long gibMillis()
	{
		return startInMilli;
	}
	
	public long getExistTime()
	{
		long erg= System.currentTimeMillis()-startInMilli;
		return erg;
	}
}
