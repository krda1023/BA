import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	All_Cities liste = new All_Cities();
	double[][] matrix;
	
	static double round(double wert)
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
	public void erzeugeStadt ()
	{	double max = 180.00;
		double min = -180.00;
		Random generator = new Random();
		for (int i=0;i<10;i++)                                         //HIER ANZAHL STÄDTE BESTIMMEN
		{	  double x_2=(double)(Math.random()*0.59+13.14);
			  double y_2=(double)(Math.random()*0.24+52.41);
				//double x_2 = min + (generator.nextDouble() * (max - min));
			  //double y_2 = min + (generator.nextDouble() * (max - min));
			  double x= round(x_2);
			  double y= round(y_2);
			  City neueStaedte = new City(i,x,y);
			  liste.addCity(neueStaedte);
			  //System.out.println(x +" "+y);
		}
	}
	//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public void erzeugeMatrix()
	{	Send_Request anfrage= new Send_Request(liste);
	try 
		{
        anfrage.call_me();
        matrix=anfrage.getergebnis();
		} 
	catch (Exception e) 
		{ 
		e.printStackTrace();
		}
	}
	
	public double [][] getDistanzmatrix()
	{
		return matrix;
	}
	
	
	public void spucksaus()
	{
		for(int i=0; i<liste.numberOfCities();i++)
		{
			for(int j=0;j<liste.numberOfCities();j++)
			{
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println("");
		}
	}
			
			
			
	
	
	
	/*public static void main (String[]args)
	{
	
	Distanzmatrix dis= new Distanzmatrix();
	dis.erzeugeStadt();
	dis.erzeugeMatrix();
	dis.spucksaus();
	

}*/
}