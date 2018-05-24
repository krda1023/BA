import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Test_main {
		
	
	public static void main(String[] args) { 
	/*Blubb b = new Blubb();
	
	CustomThread t1 = new CustomThread("erster Fred", b, true);
	CustomThread t2 = new CustomThread("zweiter Fred", b, false);
	
	t1.start();
	t2.start();

	try {
		t1.join();
		t2.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Ende des Programms");*/
		Calendar d= Calendar.getInstance();
		
		TimeElement t= new TimeElement(d);
		
		//int a = t.getHour();
		System.out.println(d.getTime().getDay());
		System.out.println(d.getTime().getDate());
}

}
