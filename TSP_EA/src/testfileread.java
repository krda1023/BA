

public class testfileread {

	public static void main(String[] args) {
		
		String s= "C:\\Users\\BADai\\git\\BA\\TSP_EA\\src\\gr96.tsp";
		readFile rf= new readFile(s);
		rf.readingFile();
		
		int numCity=rf.getNumberofCities();
		double[][] abc = new double[numCity][3];
		abc=rf.getMatrix();
		for(int a=0;a<numCity;a++)
		{
		System.out.print(abc[a][0]+" ");
		System.out.print(abc[a][1]+" ");
		System.out.print(abc[a][2]+" ");
		System.out.println();
		}

	}

}
