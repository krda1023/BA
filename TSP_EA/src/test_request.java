

public class test_request {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		double[][] coordinates= {{48.477473,9.207916},{49.153868,9.970093}};
		Send_Request abc= new Send_Request(coordinates);
		
		double[][] ergebnis= abc.call_me();
		
		
		for (int i=0; i<ergebnis.length;i++)
		{
			for (int j=0; j<ergebnis[i].length;j++)
			{
				System.out.print(" "+ergebnis[i][j]);
			}
			System.out.println("");
		}
	}

}
