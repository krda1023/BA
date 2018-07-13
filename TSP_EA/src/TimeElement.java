import java.util.Calendar;

public class TimeElement{
	Calendar start;
	long startInMilli;
	
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
	
	@SuppressWarnings("deprecation")
	public int getHour(){
		return start.getTime().getHours();
	}
	
	public double getTimeToNextHour() { 	
		int year=start.getTime().getYear()+1900;
		int month=start.getTime().getMonth();
		int day= start.getTime().getDate();
		int hour=start.getTime().getHours()+1;
		int minute=0;
		int second=0;
		Calendar nextHour= start;
		nextHour.set(year,month,day,hour,minute,second);
		System.out.println(nextHour.getTime());//??????
		System.out.println(nextHour.getTimeInMillis());
		System.out.println(startInMilli);
								//??????????????????????????????
		double timeToNextHour= (nextHour.getTimeInMillis()-startInMilli)/1000;   //In Seconds
		return timeToNextHour;						
	}
	
	public String toString() {
		 String s="";
		 s=start.getTime()+"";
		 return s;
	 }
}
