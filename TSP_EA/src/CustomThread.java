import java.sql.Date;
import java.sql.Timestamp;

public class CustomThread extends Thread {
	static TimeElement t;
	String type;
	Blubb blubb;
	boolean flag;

	CustomThread(String s, Blubb b, boolean c) {
		this.type = s;
		blubb = b;
		flag = c;
		
	}

	public void run() {
		System.out.println("Starte Thread " + type);
		for(int i = 0; i < 5; i++)
		{
			if(flag)
			{
				blubb.first();
			}
			else
			{
				blubb.second();
			}
		}
		System.out.println("Beende Thread " + type);
	}
}


