import java.util.ArrayList;
import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen St�dten
	// Erzeuge Send_Request und �bergebe alle St�dte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zur�ck greifen l�sst
	//um sp�ter in getdistance() von Klasse Tour zugreifen zu k�nnen
public class Distanzmatrix {
	static All_Cities liste = new All_Cities();
	static double[][] matrix;
	int anzst�dte;
	boolean vonFileeinlesen=false;
	Send_Request anfrage= new Send_Request(liste);
	
	public Distanzmatrix(int numberst�dte, boolean filelesen){
		if(filelesen==false)
		{
			this.anzst�dte= numberst�dte;

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
	
	public int getAnzahlst�dte()
	{
		return anzst�dte;
	}
	
	public void setMatrixwithIntermediateCity(double[][] asymMatrix)
	{
		
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
			
			anzst�dte=rf.getNumberofCities();
			
			zwischenmatrix=rf.getMatrix();
			for(int a=0;a<anzst�dte;a++)
			{
				City neueStaedte = new City(a,zwischenmatrix[a][2],zwischenmatrix[a][1]);
				liste.addCity(neueStaedte);
			}

		}
		if(vonFileeinlesen==false)
		{
			for (int i=0;i<anzst�dte;i++)                                         //HIER ANZAHL ST�DTE BESTIMMEN
			{	double x_2=(double)(Math.random()*0.59+13.14);
				double y_2=(double)(Math.random()*0.24+52.41);
				double longitude= round(x_2);
				double latitude= round(y_2);
				City neueStaedte = new City(i,longitude,latitude);
				liste.addCity(neueStaedte);
				//System.out.println(x +" "+y);
			}
		}
	}
	//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public void erzeugeDistanzmatrix()
	{	
		if (vonFileeinlesen==true)
		{
			matrix= new double[anzst�dte+1][anzst�dte+1]; 	// +1 erzeugt Zeile und SPalte f�r zwischensteps
			for(int i=0;i<anzst�dte;i++)
			{
				for(int j=0;j<anzst�dte;j++)
				{	double long1=liste.getCity(i).getLongitude();
					double lat1=liste.getCity(i).getLatitude();
					double long2=liste.getCity(j).getLongitude();
					double lat2=liste.getCity(j).getLatitude();
					double distance=distanceInKm(long1,lat1,long2,lat2);
					matrix[i][j]=distance;
					//System.out.print(matrix[i][j]+" ");
				}
				//System.out.println();
			}
			
		}

		
		if(vonFileeinlesen==false)
		{
		
			try 
			{
				
		        anfrage.createBasicMatrix();
		        matrix=anfrage.getergebnis();
		      /* for(int a=0;a<=anzst�dte;a++)
		        {
		        	for(int b=0;b<=anzst�dte;b++)
		        	{
		        		System.out.print(matrix[a][b]+" ");
		        	}
		        	System.out.println();
		        }*/
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
			}
		}
	}
	
	public static double [][] getDistanzmatrix()
	{
		return matrix;
	}
	
	public static double distanceInKm( double lon1, double lat1, double lon2, double lat2) {   //Haversine-Formel
		    int radius = 6371;
		    double lat = Math.toRadians(lat2 - lat1);
		    double lon = Math.toRadians(lon2- lon1);
		    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double d = radius * c;
		    
		    return Math.abs(d);
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