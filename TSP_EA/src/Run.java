import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Run {
		static Distanzmatrix dis;
		static int anzahlstädte;
		static int populationsgröße;
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
		static boolean  fileLesen=false;
		
		
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
		
		
	public static void main(String[] args) throws IOException
	{
		int anzahlstädte1=180;
		int interationen=10;
		int populationsgröße=100;
		
	/*	
		System.out.println("Soll von einer File eingelesen werden, 1 für Ja");
		Scanner sc1 = new Scanner(System.in);
		int h=sc1.nextInt();
		if(h==1)
		{
			fileLesen=true;
		}
		if(fileLesen==false)
		{
			System.out.println("Wieviele Städte sollen erzeugt werden? Maximum ist 100, Minimum 3");
			
			int g =sc1.nextInt();
			anzahlstädte1=g;
		}
		System.out.println("Wieviele Iterationen sollen durchgeführt werden?");
		int e1 =sc1.nextInt();
		interationen=e1;
		System.out.println("Wie groß soll eine Population sein?");
		int e2 =sc1.nextInt();
		populationsgröße=e2;*/
		
		
	/*	System.out.println("Welchen Crossover-Operator möchtest du verwenden?\n Wähle 1 für Ox2 Crossover\n Wähle 2 für OrderCrossover\n Wähle 3 für PMX-Crossover\n Wähle 4 für Cycle-Crossover");
		int i = sc1.nextInt();
		
		System.out.println("Welchen Mutation-Operator möchtest du verwenden?\n Wähle 1 für Displacement-Mutation\n Wähle 2 für Insertion-Mutation\n Wähle 3 für Inversion-Mutation\n Wähle 4 für Exchange Mutation\n Wähle 5 für MultipleExchange-Mutation");
		
		int j = sc1.nextInt();
		
		System.out.println("Elitism aktivieren??\n Wähle 1 für Ja\n Wähle 2 Nein");
		
		
		int k = sc1.nextInt();
		sc1.close();
		*/
		int h=0;
		int i=2;
		int j=1;
		int k=1;
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
		
	
		
		dis= new Distanzmatrix(anzahlstädte1,fileLesen);
		dis.erzeugeStaedteliste();
		anzahlstädte=dis.getAnzahlstädte();
		dis.erzeugeDistanzmatrix();
		//dis.spucksaus();
		
		 

        

        // Initialize population
        Population pop = new Population(populationsgröße, true);
        if(fileLesen==true)
        {
        	System.out.println("Initial distance: " + pop.getFittest().getDistance());
        }
        if(fileLesen==false)
        {
        	System.out.println("Initial duration: " + pop.getFittest().getDistance());
        }
        

        // Evolve population for xx generations
        GA Algorithmus= new GA( ox2C, ordC,  pmxC, cycC,  disM, insM,  invM,  excM, mexM,  elitism);
        pop = GA.evolvePopulation(pop);
        
     //   Logger log = new Logger();
       for (int z = 0; z < interationen; z++) 
       {
           pop = Algorithmus.evolvePopulation(pop);
       //    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           
       //    log.print(new Date().toString()+ " "+ timestamp+ " "+pop.getFittest());
           
         
      }
     /*  Tour oldFittest=new Tour();
        Tour newFittest= new Tour();
        int counter=0;
        do {
        	oldFittest=pop.getFittest();
            pop = Algorithmus.evolvePopulation(pop);
            newFittest=pop.getFittest();
            if(newFittest.getDistance()==oldFittest.getDistance())
            {
            	counter++;
            }
            else
            {
            	counter=0;
            }
        }
        while(counter<2);
*/
        // Print final results
      	System.out.println("Finished");
      	if(fileLesen==true)
      		System.out.println("Final distance: " + pop.getFittest().getDistance());
      	if(fileLesen==false)
      		System.out.println("Final duration: " + pop.getFittest().getDistance());
       System.out.println("Anzahl Städte: "+All_Cities.numberOfCities());
       System.out.println("Solution:");
       System.out.println(pop.getFittest()); 
		

       //log.exit(); 
    }
    
}
		