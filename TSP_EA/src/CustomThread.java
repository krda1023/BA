import java.sql.Date;
import java.sql.Timestamp;

public class CustomThread extends Thread {
	final static long startzeit=System.currentTimeMillis();
	static long Fahrtzeit;
	public static void getFahrzeit()
	{	
		 Fahrtzeit=System.currentTimeMillis()-startzeit;
		
	}
	public void run()
	{System.out.println(startzeit);
		try { Thread.sleep(100);
				getFahrzeit();}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println(Fahrtzeit);
		
		System.out.println(Fahrtzeit);

	}

}
