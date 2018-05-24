import java.util.Calendar;
import java.util.Date;

public class TimeElement{
	Calendar start;
	long startInMilli;
	
	public TimeElement(){
		 this.start=Calendar.getInstance();
		this.startInMilli= start.getTimeInMillis();
	}
	
	public TimeElement(Calendar d) {
		this.start=d;
		this.startInMilli=start.getTimeInMillis();
	}
	
	public Calendar gibZeit(){
		return start;
	}
	public long gibMillis(){
		return startInMilli;
	}
	
	public long getExistTime(){
		long erg= System.currentTimeMillis()-startInMilli;
		return erg;
	}
	
	@SuppressWarnings("deprecation")
	public int getHour(){
		return start.getTime().getHours();
	}
	public double getTimeToNextHour() {
		int year=start.getTime().getYear();
		int month=start.getTime().getMonth();
		int day= start.getTime().getDate();
		int hour=start.getTime().getHours()+1;
		int minute=0;
		int second=0;
		Calendar nextHour= Calendar.getInstance();
		nextHour.set(year,month,day,hour,minute,second);
		double timeToNextHour= nextHour.getTimeInMillis()-startInMilli;
		return timeToNextHour;						
	}
	
}
