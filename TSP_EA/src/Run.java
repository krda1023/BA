import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class Run {
		static Distanzmatrix dis;
		static int anzahlst�dte;
		static int populationsgr��e;
		static int interationen;
		public static int getNumberofCities()
		{
			return anzahlst�dte;	
		}
		
		public static Distanzmatrix getFirstMatrix()
		{
			return dis;
		}
		public static double[][] getArrayMatrix()
		{
			return dis.getDistanzmatrix();
		}
	public static void main(String[] args) throws IOException {
		anzahlst�dte= 10;
		interationen = 50;
		populationsgr��e= 20;
		
		dis= new Distanzmatrix(anzahlst�dte);
		dis.erzeugeStadt();
		dis.erzeugeMatrix();
		//dis.spucksaus();
		//double[][]ergebnis=dis.getDistanzmatrix();      Das hab ich irgendwo rausgeklaut, KP wo
		 

        

        // Initialize population
        Population pop = new Population(populationsgr��e, true);
      
      // System.out.println("Initial distance: " + pop.getFittest().getDistance());
        
        

        // Evolve population for xx generations
        pop = GA.evolvePopulation(pop);
        Logger log = new Logger();
	for (int i = 0; i < interationen; i++) {
           pop = GA.evolvePopulation(pop);
           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           
           log.print(new Date().toString()+ " "+ timestamp+ " "+pop.getFittest());
           
         
        }
	
        // Print final results
      	System.out.println("Finished");
       System.out.println("Final distance: " + pop.getFittest().getDistance());
       System.out.println("Anzahl St�dte: "+All_Cities.numberOfCities());
       System.out.println("Solution:");
       System.out.println(pop.getFittest());  
       log.exit(); 
    }
    
}
		