
public class Run {
	public static void main(String[] args) {

        // Create and add our cities  ....macht später der Simulator
		for(int i=0; i<(int)Math.ceil(Math.random()*101);i++)
		{
		double[] pos = {(int)Math.ceil(Math.random()*200),(int)Math.ceil(Math.random()*200)};
        City city = new City(i, pos);
        All_Cities.addCity(city);
        }
        //Bastle URL aus Koordinaten, SEND REQUEST AN API .... bearbeite....erhalte Distanzmatrix
        

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