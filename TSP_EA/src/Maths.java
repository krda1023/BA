import java.util.Calendar;
import java.util.Random;

public class Maths {
	
	static double []Faktoren= {(1/1.2),(1/1.3),(1/1.2),(1/1.1),(1/1),(1/0.9),(1/0.8),(1/0.7),(1/0.8),(1/0.85),(1/0.9),(1/0.95),(1/1),(1/0.9),(1/0.8),(1/0.7),(1/0.75),(1/0.8),(1/0.9),(1/0.95),(1/1),(1/1.05),(1/1.1),(1/1.15)};
	
	public static double getFaktor(int a) {
		return Faktoren[a];
	}
   
	public  static double round(double wert, double stellen){
		double erg=Math.round(wert*Math.pow(10,stellen))/Math.pow(10, stellen);;
		return erg;
	}
  
	public static double goGamma(double expectedTime, double k, double theta, double shiftDistance) {
		boolean accept = false;
		 Random rng = new Random(Calendar.getInstance().getTimeInMillis() + Thread.currentThread().getId());
	    if (k < 1) {
	 
	    	// Weibull algorithm
	
	    	double c = (1 / k);
	    	double d = ((1 - k) * Math.pow(k, (k / (1 - k))));
	    	double u, v, z, e, x;
	    	
	    	do {
	    		u = rng.nextDouble();
	    		v = rng.nextDouble();
	    		z = -Math.log(u);
	    		e = -Math.log(v);
	    		x = Math.pow(z, c);
	    		
	    		if ((z + e) >= (d + x)) {
	    			accept = true;
	    			}
	    		} while (!accept);
	    	
	    	double gammaValue =  ((x * theta) + shiftDistance);
	    	double result = expectedTime * gammaValue;
	    	return result;
	    	
	    } 
	    
	    else {
	    	
		 // Cheng's algorithm
		    	
		 double b = (k - Math.log(4));
		 double c = (k + Math.sqrt(2 * k - 1));
		 double lam = Math.sqrt(2 * k - 1);
		 double cheng = (1 + Math.log(4.5));
		 double u, v, x, y, z, r;
		 
		 do {
			 u = rng.nextDouble();
			 v = rng.nextDouble();
			 y = ((1 / lam) * Math.log(v / (1 - v)));
			 x = (k * Math.exp(y));
			 z = (u * v * v);
			 r = (b + (c * y) - x);
			 
			 if ((r >= ((4.5 * z) - cheng)) || (r >= Math.log(z))) {
				 accept = true;
				 }
			 
			 } while (!accept);
		 
		 double gammaValue = ((x * theta) + shiftDistance);
		 double result = expectedTime * gammaValue;
		 return result;
	 
	    }  
	}
}
