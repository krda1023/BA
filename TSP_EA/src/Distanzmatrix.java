import java.util.ArrayList;
import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen St�dten
	// Erzeuge Send_Request und �bergebe alle St�dte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zur�ck greifen l�sst
	//um sp�ter in getdistance() von Klasse Tour zugreifen zu k�nnen
public class Distanzmatrix {
	static All_Cities liste = new All_Cities();
	double[][] matrix;
	int anzst�dte;
	
	public Distanzmatrix(int numberst�dte){
		this.anzst�dte= numberst�dte;
	}
	static double round(double wert)
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
	
	public static All_Cities getAllCities()
	{
		return liste;
	}
	
	public void erzeugeStadt ()
	{	
		for (int i=0;i<anzst�dte;i++)                                         //HIER ANZAHL ST�DTE BESTIMMEN
		{	  double x_2=(double)(Math.random()*0.59+13.14);
			  double y_2=(double)(Math.random()*0.24+52.41);
			  double x= round(x_2);
			  double y= round(y_2);
			  City neueStaedte = new City(i,x,y);
			  liste.addCity(neueStaedte);
			  //System.out.println(x +" "+y);
		}
	}
	//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public void erzeugeMatrix()
	{	/*for(int i=0;i<liste.numberOfCities()/50;i++)
		{	All_Cities interliste= new All_Cities();
			for(int j =i*50;j<i*50+49;i++)
			{
				interliste.addCity(liste.getCity(j));
			}
		}
		*/
		Send_Request anfrage= new Send_Request(liste);
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
}