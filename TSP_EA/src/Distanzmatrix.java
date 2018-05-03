import java.util.ArrayList;
import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	static All_Cities liste = new All_Cities();
	double[][] matrix;
	int anzstädte;
	boolean vonFileeinlesen=false;
	
	public Distanzmatrix(int numberstädte, boolean filelesen){
		if(filelesen==false)
		{
			this.anzstädte= numberstädte;

		}
		if(filelesen)
		{
			this.vonFileeinlesen=true;
		}
	}
	static double round(double wert)
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
	
	public int getAnzahlstädte()
	{
		return anzstädte;
	}
	
	public static All_Cities getAllCities()
	{
		return liste;
	}
	
	public void erzeugeStaedteliste ()
	{	
		if (vonFileeinlesen==true)
		{	double[][] zwischenmatrix;
			String s= "C:\\Users\\BADai\\git\\BA\\TSP_EA\\src\\gr96.tsp";
			readFile rf= new readFile(s);
			rf.readingFile();
			
			anzstädte=rf.getNumberofCities();
			
			zwischenmatrix=rf.getMatrix();
			for(int a=0;a<anzstädte;a++)
			{
				City neueStaedte = new City(a,zwischenmatrix[a][1],zwischenmatrix[a][2]);
				liste.addCity(neueStaedte);
			}

		}
		if(vonFileeinlesen==false)
		{
			for (int i=0;i<anzstädte;i++)                                         //HIER ANZAHL STÄDTE BESTIMMEN
			{	double x_2=(double)(Math.random()*0.59+13.14);
				double y_2=(double)(Math.random()*0.24+52.41);
				double x= round(x_2);
				double y= round(y_2);
				City neueStaedte = new City(i,x,y);
				liste.addCity(neueStaedte);
				//System.out.println(x +" "+y);
			}
		}
	}
	//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public void erzeugeDistanzmatrix()
	{	/*for(int i=0;i<liste.numberOfCities()/50;i++)
		{	All_Cities interliste= new All_Cities();
			for(int j =i*50;j<i*50+49;i++)
			{
				interliste.addCity(liste.getCity(j));
			}
		}
		*/
		if (vonFileeinlesen==true)
		{
			// DIstanzmatrixberechnen

		}

		
		if(vonFileeinlesen==false)
		{
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