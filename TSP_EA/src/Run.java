
public class Run {
	public static void main(String[] args) {

		Distanzmatrix dis= new Distanzmatrix();
		dis.erzeugeStadt();
		dis.erzeugeMatrix();
		dis.spucksaus();

        

        // Initialize population
        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
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