import java.util.ArrayList;
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
		ArrayList<String> ab= new ArrayList<String>();
		ab.add("Position 0");
		ab.add("Position 1");
		ab.add("Position 2");
		ab.add("Position 3");
		ab.remove(0);
		System.out.println(ab.get(0)+" aber eigentlich: "+ab.indexOf(ab.get(0)));
		System.out.println(ab.get(1)+" aber eigentlich: "+ab.indexOf(ab.get(1)));
		System.out.println(ab.get(2)+" aber eigentlich: "+ab.indexOf(ab.get(2)));
}

}
