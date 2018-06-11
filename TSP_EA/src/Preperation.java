import java.util.Date;

public class Preperation {
	static Distanzmatrix dis;
	static int anzahlstaedte;
	static int popSize;
	static int iterationen;
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
	static All_Cities allCities; // Dieses All_Citie soll geupdated werden wenn stadt erreicht				
	
	public Preperation(int a, int b, int c) {
		this.anzahlstaedte=a;
		this.popSize=b;
		this.iterationen=c;
	}
	
	public boolean getox2C()
	{
		return ox2C;
	}
	public boolean getordC()
	{
		return ordC;
	}
	public boolean getpmxC()
	{
		return pmxC;
	}
	public boolean getcycC()
	{
		return cycC;
	}
	public boolean getdisM()
	{
		return disM;
	}
	public boolean getinsM()
	{
		return insM;
	}
	public boolean getinvM()
	{
		return invM;
	}
	public boolean getexcM()
	{
		return excM;
	}
	public boolean getmexM()
	{
		return mexM;
	}
	public boolean getelitism()
	{
		return elitism;
	}
	public boolean getfileLesen()
	{
		return fileLesen;
	}
	public int getanzahlstaedte()
	{
		return anzahlstaedte;
	}
	
	public void setanzahlstaedte( int a) {
		anzahlstaedte=a;
	}
	public int getiterationen()
	{
		return iterationen;
	}
	
	public void setiterationen( int a) {
		iterationen=a;
	}
	public int getpopSize()
	{
		return popSize;
	}
	
	public void setpopSize( int a) {
		popSize=a;
	}

	public void Formalitäten(){
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
		int h=1;
		int i=3;
		int j=2;
		int k=1;
		System.out.println("Bitte warten.....");
		System.out.println();
		System.out.println();
		switch(i){
			case 1: ox2C=true;break;
			case 2: ordC=true;break;
			case 3: pmxC=true;break;
			case 4: cycC=true; break;
		}
		switch(j){
			case 1: disM=true;break;
			case 2: insM=true;break;
			case 3: invM=true;break;
			case 4: excM=true;break;
			case 5: mexM=true;break;
		}
		if(k==1){
			elitism=true;
		}
		
		if(h==1){
			fileLesen=true;
		}
		dis= new Distanzmatrix(anzahlstaedte,fileLesen);
		dis.erzeugeStaedteliste();
		anzahlstaedte=dis.getAnzahlstädte();
		allCities=dis.getAllCities();
		dis.erzeugeDistanzmatrix();
		dis.erzeugeAlleDistanzmatrizen();
}
}
