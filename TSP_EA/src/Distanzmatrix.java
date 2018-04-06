import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	
	public void erzeugeStadt ()
	{	double max = 180.00;
		double min = -180.00;
		Random generator = new Random();
		for (int i=0;i<50;i++)
		{	  double x = min + (generator.nextDouble() * (max - min));
			  double y = min + (generator.nextDouble() * (max - min));
			  City neueStadt = new City(i,x,y);
			  liste.addCity(neueStadt);
		}
	}
	
	
	All_Cities liste = new All_Cities();
	

}
