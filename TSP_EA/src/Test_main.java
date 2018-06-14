import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

public class Test_main {
    static double round(double wert){
		double erg=Math.round(wert*Math.pow(10,6))/Math.pow(10, 6);;
		return erg;
	}
	public static void main(String[]args) {
	
		
		
		All_Cities abc= new All_Cities();
		City city= new City(1, null);
		abc.addCity(city);
		All_Cities def= new All_Cities();
		System.out.print(def.getCity(0).getId());
		
		
		
		
		
		
		
		
		
		
		/*double[] duration= new double[] {501,312,514,652,23,817,800,21,45,112,193};
		GammaVerteilung Gamma= new GammaVerteilung();
		Zeitfaktoren faktoren= new Zeitfaktoren();
		double[] gammaDuration= new double[duration.length];												//Array for Gamma influenced values
		double sumD=0;
		double sumGd=0;
		for(int i=0; i<duration.length;i++) {													//Loop through duration values and use gamma function
			gammaDuration[i]=Gamma.goGamma(duration[i], 0.5, 0.5, 0.5);	
			sumD+=duration[i];
			sumGd+=gammaDuration[i];
		}
		
		TimeElement now= new TimeElement();
		double durationSumZFGA=0;																//Sum of gamma and time factor influenced values looped
		double[] ZF_GA_duration= new double[gammaDuration.length];										//Array for final values for Simulation, gamma and time factor influenced
		int hour= now.getHour();																//actual hour
		double ttnh=now.getTimeToNextHour();													// Duration to next hour in seconds
		System.out.println(ttnh);	
		for(int j=0; j<gammaDuration.length;j++) {											//Loop through Gamma 
				
				if(durationSumZFGA+gammaDuration[j]*faktoren.getFaktor(hour)>ttnh) {			//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durationSumZFGA		;									//calculate the time from sum to next hour
					double ratio= tohour/gammaDuration[j]*faktoren.getFaktor(hour);				// Calculate ratio of driven way in this section
					
					
					

					ZF_GA_duration[j]=ratio*gammaDuration[j]*faktoren.getFaktor(hour)+(1-ratio)*gammaDuration[j]*faktoren.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
					durationSumZFGA+=ZF_GA_duration[j];											//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					System.out.println("WECHSEL");													//Update time factor
					
				}
				else {																			// If actual step is within the same hour
					ZF_GA_duration[j]=gammaDuration[j]*faktoren.getFaktor(hour);				//multiply value with time factor
					durationSumZFGA+=ZF_GA_duration[j];		
					System.out.println("Kein WECHSEL"+ ZF_GA_duration[j]);	//Update Sum
				}
			}	
		
		for(int f=0;f<duration.length;f++) {
			System.out.print(duration[f]+"    ");
			System.out.print(gammaDuration[f]+"    ");
			System.out.print(ZF_GA_duration[f]+"    ");
			System.out.println();
			
			
		}
		
		System.out.println(sumD);
		System.out.println(sumGd);
		System.out.println(durationSumZFGA);*/
	}
}





/*Thread t = new Thread()
{
  @Override public void run()
  {
    try
    {
      while ( true ) System.out.println( "I Like To Move It." );
    }
    catch ( ThreadDeath td )
    {
      System.out.println( "Das Leben ist nicht totzukriegen." );
      throw td;
    }
  }
};
t.start();
try { Thread.sleep( 5000 ); } catch ( Exception e ) { }
t.stop();
System.out.println();
System.out.println("ficken");*/