import java.util.Random;

// hol dir ein Array oder Liste aus All Cities mit allen Städten
	// Erzeuge Send_Request und Übergebe alle Städte damit URL gebildet werden kann
	//Klassenvariable Ergebnis[][] = request.callme()
	// Methode die mich auf jeden Eintrag im Ergebnis genau zurück greifen lässt
	//um später in getdistance() von Klasse Tour zugreifen zu können
public class Distanzmatrix {
	All_Cities liste = new All_Cities();
	static double round(double wert)
	{
		double erg=Math.round(wert*Math.pow(10,5))/Math.pow(10, 5);;
		return erg;
	}
	public void erzeugeStadt ()
	{	double max = 180.00;
		double min = -180.00;
		Random generator = new Random();
		for (int i=0;i<50;i++)
		{	  double x_2 = min + (generator.nextDouble() * (max - min));
			  double y_2 = min + (generator.nextDouble() * (max - min));
			  double x= round(x_2);
			  double y= round(y_2);
			  City neueStadt = new City(i,x,y);
			  liste.addCity(neueStadt);
			  //System.out.println(x +" "+y);
		}
	}
	
	/*public static void main (String[]args)
	{
	
	Distanzmatrix dis= new Distanzmatrix();
	dis.erzeugeStadt();

}*/
}