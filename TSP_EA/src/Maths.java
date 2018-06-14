
public class Maths {
static double []Faktoren= {(1/1.2),(1/1.3),(1/1.2),(1/1.1),(1/1),(1/0.9),(1/0.8),(1/0.7),(1/0.8),(1/0.85),(1/0.9),(1/0.95),(1/1),(1/0.9),(1/0.8),(1/0.7),(1/0.75),(1/0.8),(1/0.9),(1/0.95),(1/1),(1/1.05),(1/1.1),(1/1.15)};
	
	public static double getFaktor(int a) {
		return Faktoren[a];
	}
   public  static double round(double wert, double stellen){
		double erg=Math.round(wert*Math.pow(10,stellen))/Math.pow(10, stellen);;
		return erg;
	}
}
