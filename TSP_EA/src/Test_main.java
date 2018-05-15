
public class Test_main {
		
	public static long getFahrzeit()
	{	
		long Fahrtzeit=System.currentTimeMillis();
		System.out.println(Fahrtzeit);
		return Fahrtzeit;
	}
	
	public static void main(String[] args) 
	{	long startzeit=System.currentTimeMillis();
		System.out.print("Los gehts: ");
		long endzeit= startzeit+1500;
		 while(endzeit>getFahrzeit())
		 {
			
				
		 }
		 System.out.print("Fertig: ");
	
		
	}
			

	}


