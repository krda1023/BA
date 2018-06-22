import java.util.ArrayList;
import java.util.Random;

import javax.lang.model.type.IntersectionType;

import org.json.JSONObject;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	
	All_Cities liste = new All_Cities();
	static double[][] matrix;
	 static int CreatingnumOfCities;
	 static City startCity;
		
	static ArrayList<double[][]> allMatrix= new ArrayList<double[][]>();	
	static boolean vonFileeinlesen=false;
	


	
	public static int getCreatedAnzahlstädte() {
		return CreatingnumOfCities;
	}
	
	
	
	
	public static void erzeugeStaedteliste (){		
		if (vonFileeinlesen==true) {
			double[][] zwischenmatrix;
			String s= "C:\\Users\\BADai\\git\\BA\\TSP_EA\\src\\gr97.tsp";
			readFile rf= new readFile(s);
			rf.readingFile();
			CreatingnumOfCities=rf.getNumberofCities();		
			zwischenmatrix=rf.getMatrix();
			for(int a=0;a<CreatingnumOfCities;a++) {			
				City neueStaedte = new City(a,"City",zwischenmatrix[a][2],zwischenmatrix[a][1]);			//Hier werden IDs vergeben
				All_Cities.addCity(neueStaedte);
			}
			startCity=All_Cities.getCity(0);
		}
		if(vonFileeinlesen==false) {		
			for (int i=0;i<CreatingnumOfCities;i++)  {                                      //HIER ANZAHL STÄDTE BESTIMMEN
				double x_2=(double)(Math.random()*0.59+13.14);
				double y_2=(double)(Math.random()*0.24+52.41);
				double longitude= Maths.round(x_2,6);
				double latitude= Maths.round(y_2,6);
				City neueStaedte = new City(i,"City",longitude,latitude);
				All_Cities.addCity(neueStaedte);
				startCity=All_Cities.getCity(0);
				//System.out.println(x +" "+y);
			}
		}
	}
																				//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public static void erzeugeDistanzmatrix() {
		
		if (vonFileeinlesen==true) {	
			matrix= new double[CreatingnumOfCities+1][CreatingnumOfCities+1]; 						// +1 erzeugt Zeile und SPalte für zwischensteps
			for(int i=0;i<CreatingnumOfCities;i++) {		
				for(int j=0;j<CreatingnumOfCities;j++) {
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
		        
		        matrix=Send_Request.createsmallMatrix();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void erzeugeAlleDistanzmatrizen(){
		
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
	
	
	public static void updateAllMatrix() throws Exception {
		
		double[]IntersectionMatrix=Send_Request.IntersectionMatrix(All_Cities.getCity(All_Cities.numberOfCities()-1));
		for(int i=0;i<matrix.length-1;i++) {
			matrix[matrix.length-1][i]=IntersectionMatrix[i];
		}
		for(int i=0; i<24;i++) {
			double faktor= Maths.getFaktor(i);
			for(int j=0;j<matrix.length-1;j++) {				//Matrix wird mit +1 Stellen extra erzeugt für Zwischenwerte
				
					allMatrix.get(i)[matrix.length-1][j]=IntersectionMatrix[j]*faktor;
				}
			
			}
			
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