import java.util.Calendar;
// Class for for saving and managing time 
public class TimeElement{

//VARIABLES:
	Calendar start;
	long startInMilli;
	
//CONSTRUCTOR:
	public TimeElement(){
		 this.start=Calendar.getInstance();
		this.startInMilli= start.getTimeInMillis();
	}
	
	public TimeElement(long timeinMilli) {
		 Calendar cal =Calendar.getInstance();
		 cal.setTimeInMillis(timeinMilli);
		 
		 this.start=cal;
		this.startInMilli=start.getTimeInMillis();
	}
	
//METHODS:
	//Get hour of initialized time
	@SuppressWarnings("deprecation")
	public int getHour(){
		return start.getTime().getHours();
		
	}
	public long getMilliatNextHour() { 	
		int year=start.getTime().getYear()+1900;
		int month=start.getTime().getMonth();
		int day= start.getTime().getDate();
		int hour=start.getTime().getHours()+1;
		int minute=0;
		int second=0;
		Calendar nextHour= Calendar.getInstance();
		nextHour.set(year,month,day,hour,minute,second);			
		long timeAtNextHour= nextHour.getTimeInMillis();  
		return timeAtNextHour;						
	}
	//Get seconds to fill up time of "start" to match the next full hour
	public double getTimeToNextHour() { 	
		int year=start.getTime().getYear()+1900;
		int month=start.getTime().getMonth();
		int day= start.getTime().getDate();
		int hour=start.getTime().getHours()+1;
		int minute=0;
		int second=0;
		Calendar nextHour= Calendar.getInstance();
		nextHour.set(year,month,day,hour,minute,second);			
		double timeToNextHour= Maths.round((nextHour.getTimeInMillis()-startInMilli)/1000,3);   //In Seconds
		return timeToNextHour;						
	}
	
	public String toString() {
		 String s="";
		 s=start.getTime()+"";
		 return s;
	 }
	
}
