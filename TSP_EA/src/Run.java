import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Run {
		static Distanzmatrix dis;
		static int anzahlst�dte;
		static int populationsgr��e;
		static int interationen;
		static boolean ox2C=false;
		static boolean ordC=false;
		static boolean pmxC=false;
		static boolean cycC=false;
		static boolean disM=false;
		static boolean insM=false;
		static boolean invM=false;
		static boolean excM=false;
		static boolean mexM=false;
		static boolean elitism=false;
		
		
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
		
		
	public static void main(String[] args) throws IOException
	{
		anzahlst�dte= 100;
		interationen = 100;
		populationsgr��e= 100;
		
		
		System.out.println("Welchen Crossover-Operator m�chtest du verwenden?\n W�hle 1 f�r Ox2 Crossover\n W�hle 2 f�r OrderCrossover\n W�hle 3 f�r PMX-Crossover\n W�hle 4 f�r Cycle-Crossover");
		Scanner sc1 = new Scanner(System.in);
		int i = sc1.nextInt();
		
		System.out.println("Welchen Mutation-Operator m�chtest du verwenden?\n W�hle 1 f�r Displacement-Mutation\n W�hle 2 f�r Insertion-Mutation\n W�hle 3 f�r Inversion-Mutation\n W�hle 4 f�r Exchange Mutation\n W�hle 5 f�r MultipleExchange-Mutation");
		
		int j = sc1.nextInt();
		
		System.out.println("Elitism aktivieren??\n W�hle 1 f�r Ja\n W�hle 2 Nein");
		
		
		int k = sc1.nextInt();
		sc1.close();
		System.out.println("Bitte warten.....");
		System.out.println();
		System.out.println();
		switch(i)
		{
		case 1: ox2C=true;break;
		case 2: ordC=true;break;
		case 3: pmxC=true;break;
		case 4: cycC=true; break;
		}
		switch(j)
		{
		case 1: disM=true;break;
		case 2: insM=true;break;
		case 3: invM=true;break;
		case 4: excM=true;break;
		case 5: mexM=true;break;
		}
		if(k==1)
			{elitism=true;}
		
		dis= new Distanzmatrix(anzahlst�dte);
		dis.erzeugeStadt();
		dis.erzeugeMatrix();
		//dis.spucksaus();
		
		 

        

        // Initialize population
        Population pop = new Population(populationsgr��e, true);
      
      System.out.println("Initial distance: " + pop.getFittest().getDistance());
        
        

        // Evolve population for xx generations
        GA Algorithmus= new GA( ox2C, ordC,  pmxC, cycC,  disM, insM,  invM,  excM, mexM,  elitism);
        pop = GA.evolvePopulation(pop);
        Logger log = new Logger();
	for (int z = 0; z < interationen; z++) {
           pop = Algorithmus.evolvePopulation(pop);
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
		