
public class Run {
		static Distanzmatrix dis;
		static int anzahlstädte;
		static int populationsgröße;
		static int interationen;
		public static int getNumberofCities()
		{
			return anzahlstädte;	
		}
		
		public static Distanzmatrix getFirstMatrix()
		{
			return dis;
		}
		public static double[][] getArrayMatrix()
		{
			return dis.getDistanzmatrix();
		}
	public static void main(String[] args) {
		anzahlstädte= 5;
		interationen = 10;
		populationsgröße= 1000;
		
		dis= new Distanzmatrix(anzahlstädte);
		dis.erzeugeStadt();
		dis.erzeugeMatrix();
		//dis.spucksaus();
		//double[][]ergebnis=dis.getDistanzmatrix();      Das hab ich irgendwo rausgeklaut, KP wo
		 

        

        // Initialize population
        Population pop = new Population(populationsgröße, true);
      
      // System.out.println("Initial distance: " + pop.getFittest().getDistance());
        
        

        // Evolve population for xx generations
        pop = GA.evolvePopulation(pop);
        
	for (int i = 0; i < interationen; i++) {
           pop = GA.evolvePopulation(pop);
         
        }

        // Print final results
      	System.out.println("Finished");
       System.out.println("Final distance: " + pop.getFittest().getDistance());
       System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
       System.out.println("Solution:");
       System.out.println(pop.getFittest());  
        
    }
    
}
		