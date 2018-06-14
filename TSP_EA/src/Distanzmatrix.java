import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	
	All_Cities liste = new All_Cities();
	static double[][] matrix;
	static int numOfCities;
	Send_Request anfrage= new Send_Request(liste);		
	static ArrayList<double[][]> allMatrix= new ArrayList<double[][]>();	
	boolean vonFileeinlesen=false;
	
	public Distanzmatrix(int numberstädte, boolean filelesen){
		if(filelesen==false) {	
			Distanzmatrix.numOfCities= numberstädte;
		}
		if(filelesen) {		
			this.vonFileeinlesen=true;
		}
	}
	static double round(double wert) {
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
	
	public static int getAnzahlstädte() {
		return numOfCities;
	}
	
	public void setMatrixwithIntermediateCity(double[][] asymMatrix) {
	
		
	}
	
	
	
	public void erzeugeStaedteliste (){		
		if (vonFileeinlesen==true) {
			double[][] zwischenmatrix;
			String s= "C:\\Users\\BADai\\git\\BA\\TSP_EA\\src\\gr97.tsp";
			readFile rf= new readFile(s);
			rf.readingFile();
			numOfCities=rf.getNumberofCities();		
			zwischenmatrix=rf.getMatrix();
			for(int a=0;a<numOfCities;a++) {			
				City neueStaedte = new City(a,zwischenmatrix[a][2],zwischenmatrix[a][1]);
				All_Cities.addCity(neueStaedte);
			}
		}
		if(vonFileeinlesen==false) {		
			for (int i=0;i<numOfCities;i++)  {                                      //HIER ANZAHL STÄDTE BESTIMMEN
				double x_2=(double)(Math.random()*0.59+13.14);
				double y_2=(double)(Math.random()*0.24+52.41);
				double longitude= round(x_2);
				double latitude= round(y_2);
				City neueStaedte = new City(i,longitude,latitude);
				All_Cities.addCity(neueStaedte);
				//System.out.println(x +" "+y);
			}
		}
	}
																				//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public void erzeugeDistanzmatrix() {
		
		if (vonFileeinlesen==true) {	
			matrix= new double[numOfCities+1][numOfCities+1]; 						// +1 erzeugt Zeile und SPalte für zwischensteps
			for(int i=0;i<numOfCities;i++) {		
				for(int j=0;j<numOfCities;j++) {
					double long1=All_Cities.getCity(i).getLongitude();
					double lat1=All_Cities.getCity(i).getLatitude();
					double long2=All_Cities.getCity(j).getLongitude();
					double lat2=All_Cities.getCity(j).getLatitude();
					double distance=distanceInKm(long1,lat1,long2,lat2);
				
					matrix[i][j]=distance;
					
					//System.out.print(matrix[i][j]+" ");
				}
				//System.out.println();
			}		
		}	
		if(vonFileeinlesen==false) {		
			try {		
		        anfrage.createsmallMatrix();
		        matrix=anfrage.getergebnis();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void erzeugeAlleDistanzmatrizen(){
		
		for(int i=0; i<24;i++) {
			double faktor= Maths.getFaktor(i);
			double newMatrix[][] = new double[matrix.length][matrix.length];
			for(int j=0;j<matrix.length-1;j++) {				//Matrix wird mit +1 Stellen extra erzeugt für Zwischenwerte
				for(int k=0; k<matrix.length-1;k++) {
					newMatrix[j][k]=matrix[j][k]*faktor;
				}
			}
			allMatrix.add(newMatrix);
		}
		allMatrix.add(matrix);
	}
	
	
	
	
	
	public static double [][] getDistanzmatrix(){
		return matrix;
	}
	public static ArrayList<double [][]> getAllMatrix(){
		return allMatrix;
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

	
	
	
}