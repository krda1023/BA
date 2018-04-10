
public class Run {
		static Distanzmatrix dis;
		static int anzahlstädte;
		
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
		anzahlstädte= 50;
		int interationen = 1000;
		dis= new Distanzmatrix(anzahlstädte);
		dis.erzeugeStadt();
		dis.erzeugeMatrix();
		dis.spucksaus();
		//double[][]ergebnis=dis.getDistanzmatrix();      Das hab ich irgendwo rausgeklaut, KP wo
		 

        

        // Initialize population
        Population pop = new Population(100, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());
        
        

        // Evolve population for 100 generations
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
		