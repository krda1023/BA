import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;





public class GA implements myListener {

    /* GA parameters - Which operators are selected*/
    private static double mutationRate = 0.2;   //Mutationrate for Multiple Exchange Mutation
    private static int tournamentSize = 2;	//Tournament size for Tournament Selection
    static Distanzmatrix dis;			//Finale Distanzmatrix 
   
    static int numOfCities=50;   //FINAL
	static int popSize=50;			//FINAL
	static int iterationen=100;		//FINAL
	static boolean ox2C=true;
	static boolean ordC=false;
	static boolean pmxC=true;
	static boolean cycC=false;
	static boolean disM=true;
	static boolean insM=false;
	static boolean invM=false;
	static boolean excM=false;
	static boolean mexM=false;
	static boolean elitism=true;
	static boolean  fileLesen=true;
	static double c=1;
	static double theta=1;
	static double shiftDistance=0;
	static int EventCounter=0;
	GUI_Start form;
	static int blockedCities=2;
	Tour best;
	Population pop;
	Population currrentPop;
	static City lastCity;
	
	ArrayList<City> Nodes;
	ArrayList<City> Intersections;
	double[] durations;
	static double toDrivetoIntersection;
	static TimeElement lastEventTime;
	private ArrayList<RouteServiceListener> listenerList= new ArrayList<RouteServiceListener>();
	
	int h;
	int i;
	int j;
	int k;
	All_Cities allCities;   	//ALL_CITIES welches das aktuelle sein soll --- Hier einf�gen und Streichen
   
	public void gui_start() {
		
		Thread t = new Thread()
		{
		  @Override public void run()
		  {
		    try
		    {
		    	form= new GUI_Start(); ;
		    }
		    catch ( ThreadDeath td )
		    {
		      System.out.println( "Das Leben ist nicht totzukriegen." );
		      throw td;
		    }
		  }
		};
		t.start();
		try { Thread.sleep( 8000 ); } catch ( Exception e ) { }
		t.stop();
	}
	
	class GUI_Start extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JRadioButton Jox2C;
		JRadioButton JordC;
		JRadioButton JcycC;
		JRadioButton JpmxC;
		JRadioButton JinsM;
		JRadioButton JinvM;
		JRadioButton JdisM;
		JRadioButton JexcM;
		JRadioButton JmexM;
		JButton close;
		JSlider city;
		JSlider iteration;
		JSlider population;
		JRadioButton FileJa;
		JRadioButton FileNo;
		JRadioButton EliJa;
		JRadioButton EliNo;
		JLabel CrossText;
		JLabel MutText;
		JLabel vonFileText;
		JLabel numCityText;
		JLabel iterText;
		JLabel popText;
		JLabel ElitismText;

		

		class cityListener implements ChangeListener{


			@Override
			public void stateChanged(ChangeEvent e) {
				numOfCities=city.getValue();
				
				
			}
			
		}
		class iterListener implements ChangeListener{

			@Override
			public void stateChanged(ChangeEvent e) {
				iterationen=iteration.getValue();
				
			}
			
		}
		class popListener implements ChangeListener{

			@Override
			public void stateChanged(ChangeEvent e) {
				popSize=population.getValue();
				
			}
			
		}
		class eliListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==EliJa){
					k=1;
				}
				if(e.getSource()==EliNo){
					k=2;
				}
			}
			
		}


		class closeListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
				
			}
			
		}

		class MutListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==JinsM) {
					j=2;
				}
				if(e.getSource()==JinvM) {
					j=3;
				}
				if(e.getSource()==JdisM) {
					j=1;
				}
				if(e.getSource()==JexcM) {
					j=4;
					System.out.println("EXCHA");
				}
				if(e.getSource()==JmexM) {
					j=5;
					System.out.print("Fick mich Displacement");
				}
			}
			
		}
		class CroListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==Jox2C) {
					i=1;
				}
				if(e.getSource()==JordC) {
					i=2;
				}
				if(e.getSource()==JcycC) {
					i=3;
				}
				if(e.getSource()==JpmxC) {
					i=4;
					
				}
			
			}
			
		}
		class FileListener implements ActionListener{

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==FileNo) {
					h=2;
					city.setEnabled(true);
					
					
					
					
				}
				if(e.getSource()==FileJa) {
					h=1;
					city.setEnabled(false);
					
				}
				
			}
			
		}

		public GUI_Start() {
			super("Bitte treffen Sie die Einstellungen");
			setLayout(null);
			
			getContentPane().setBackground(Color.lightGray);
			setSize(1000,750);
			setVisible(true);
			CrossText= new JLabel("Bitte w�hlen Sie den Crossover-Operator");
			CrossText.setBounds(0,0, 500, 20);
			Jox2C= new JRadioButton("Ox2 Crossover");
			Jox2C.setBounds(0,20,200,50);
			Jox2C.setBackground(Color.lightGray);
			Jox2C.addActionListener(new CroListener());
			JordC= new JRadioButton("Order Crossover");
			JordC.addActionListener(new CroListener());
			JordC.setBounds(250,20,200,50);
			JordC.setSelected(true);
			JordC.setBackground(Color.lightGray);
			
			JcycC= new JRadioButton("Cycle Crossover");
			JcycC.addActionListener(new CroListener());
			JcycC.setBounds(500,20,200,50);
			JcycC.setBackground(Color.lightGray);

			JpmxC= new JRadioButton("PMX Crossover");
			JpmxC.addActionListener(new CroListener());
			JpmxC.setBounds(750,20,200,50);
			JpmxC.setBackground(Color.lightGray);
			MutText= new JLabel("Bitte w�hlen sie den Mutations-Operator");
			MutText.setBounds(0,90,500,20);
			JinsM= new JRadioButton("Insertion Mutation");
			JinsM.addActionListener(new MutListener());
			JinsM.setBackground(Color.lightGray);
			JinsM.setBounds(0,110,200,50);
			JinsM.setSelected(true);
			JinvM= new JRadioButton("Inversion Mutation");
			JinvM.addActionListener(new MutListener());
			JinvM.setBackground(Color.lightGray);
			JinvM.setBounds(200,110,200,50);

			JdisM= new JRadioButton("Displacement Mutation");
			JdisM.addActionListener(new MutListener());
			JdisM.setBackground(Color.lightGray);
			JdisM.setBounds(400,110,200,50);

			JexcM= new JRadioButton("Exchange Mutation");
			JexcM.addActionListener(new MutListener());
			JexcM.setBackground(Color.lightGray);
			JexcM.setBounds(600,110,200,50);

			JmexM= new JRadioButton("Mult. Exchange Mutation");
			JmexM.addActionListener(new MutListener());
			JmexM.setBackground(Color.lightGray);
			JmexM.setBounds(800,110,200,50);
			ElitismText= new JLabel("Soll Elitism aktiviert werden?");
			ElitismText.setBounds(0,170,500,20);
			EliJa= new JRadioButton("Ja");
			EliJa.setBackground(Color.lightGray);
			EliJa.setBounds(0, 190, 200,50);
			EliJa.setSelected(true);
			EliJa.addActionListener(new eliListener());
			EliNo= new JRadioButton("Nein");
			EliNo.setBackground(Color.lightGray);
			EliNo.setBounds(200, 180, 200, 50);
			EliNo.addActionListener(new eliListener());
			vonFileText= new JLabel("Soll von einer TSP-File eingelesen werden?");
			vonFileText.setBounds(0,240,500,20);
			FileJa= new JRadioButton("Ja");
			FileJa.setBackground(Color.lightGray);
			FileJa.setBounds(0, 260, 200,50);
			FileJa.setSelected(true);
			FileJa.addActionListener(new FileListener());
			FileNo= new JRadioButton("Nein");
			FileNo.setBackground(Color.lightGray);
			FileNo.setBounds(200, 260, 200, 50);
			FileNo.addActionListener(new FileListener());
			numCityText= new JLabel("Falls Sie nicht von einer Tsp-File lesen: Wieviel St�dte sollen erzeugt werden?");
			numCityText.setBounds(0,320, 1000, 20);
			int cityMayor=20;
			int cityMinor=5;
			 city= new JSlider(0,200,50);
			 
			 city.setBounds(0, 340, 500, 50);
			 city.setBackground(Color.lightGray);
			 city.setForeground(Color.YELLOW);
			 city.setPaintTicks(true);
			 city.setPaintLabels(true);
			 city.setMajorTickSpacing(cityMayor);
			 city.setMinorTickSpacing(cityMinor);
			 city.addChangeListener(new cityListener());
			 city.setEnabled(false);
			 iterText= new JLabel("Wieviel Iterationen sollen durchgef�hrt werden?");
			 iterText.setBounds(0, 410, 1000, 20);
			 int iterMayor=1000;
			 int iterMinor=100;
			 iteration= new JSlider(0,10000,100);
			 iteration.setBounds(0,430,500,50);
			 iteration.setBackground(Color.lightGray);
			 iteration.setForeground(Color.YELLOW);
			 iteration.setPaintTicks(true);
			 iteration.setPaintLabels(true);
			 iteration.setMajorTickSpacing(iterMayor);
			 iteration.setMinorTickSpacing(iterMinor);
			 iteration.addChangeListener(new iterListener());
			 
			 popText= new JLabel("Wie gro� soll die Population sein?");
			 popText.setBounds(0, 510, 1000, 20);
			 int popMayor= 20;
			 int popMinor=5;
			 population= new JSlider(0,200,50);
			 population.setBounds(0,530,500,50);
			 population.setPaintTicks(true);
			 population.setPaintLabels(true);
			 population.setBackground(Color.lightGray);
			 population.setForeground(Color.YELLOW);
			 population.setMajorTickSpacing(popMayor);
			 population.setMinorTickSpacing(popMinor);
			 population.addChangeListener(new popListener());
			 
			 close=new JButton("Run Algorithm!");
			 close.setBounds(500,620,120,50);
			 close.setBackground(Color.gray);
			 
			 close.addActionListener(new closeListener());
			 ButtonGroup crossover= new ButtonGroup();
			 ButtonGroup mutation= new ButtonGroup();
			 ButtonGroup elitism= new ButtonGroup();
			 ButtonGroup File= new ButtonGroup();
			 File.add(FileJa);
			 File.add(FileNo);
			 elitism.add(EliNo);
			 elitism.add(EliJa);
			 crossover.add(Jox2C);
			 crossover.add(JordC);
			 crossover.add(JcycC);
			 crossover.add(JpmxC);
			 mutation.add(JinvM);
			 mutation.add(JinsM);
			 mutation.add(JdisM);
			 mutation.add(JexcM);
			 mutation.add(JmexM);
			 add(city);
			 add(iteration);
			 add(population);
			 add(Jox2C);
			 add(JordC);
			 add(JcycC);
			 add(JpmxC);
			 add(JinsM);
			 add(JinvM);
			 add(JdisM);
			 add(JexcM);
			 add(JmexM);
			 add(MutText);
			 add(CrossText);
			 add(EliJa);
			 add(EliNo);
			 add(ElitismText);
			 add(vonFileText);
			 add(FileJa);
			 add(FileNo);
			 add(numCityText);
			 add(popText);
			 add(iterText);
			 add(close);
			 
		}
		}

	
	public void Formalit�ten(){
	   
		
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
		switch(k) {
		case 1: elitism=true;
		case 2: elitism=false;
		}
		
		switch(h){
		case 1: fileLesen=true;
		case 2:fileLesen=false;
		}
		
		Distanzmatrix.CreatingnumOfCities=numOfCities;
		Distanzmatrix.vonFileeinlesen=fileLesen;
		Distanzmatrix.erzeugeStaedteliste();
		numOfCities=Distanzmatrix.getCreatedAnzahlst�dte();
		
		Distanzmatrix.erzeugeDistanzmatrix();
		Distanzmatrix.erzeugeAlleDistanzmatrizen();
	}
	
	
	public boolean getfileLesen()
	{
		return fileLesen;
	}
	public static  int getnumOfCities()
	{
		return numOfCities;
	}
	
	public void addRouteServiceListener(RouteServiceListener toAdd) {
		listenerList.add(toAdd);
	}
	
	public void fireEvent(RouteServiceEvent e)
	{
		listenerList.get(0).GAdidRequest(e);
	}
	
	public static  Distanzmatrix getMatrixObject(){					//ZENTRALES DISTANZMATRIX Object
		return dis;
	}
	
    public void evolvePopulation(boolean initilize) {						// Evolves Population( Usage of all selected operators)
    	if(initilize) {
    		pop = new Population(popSize, true);
    	}
        Population newPopulation = new Population(popSize, false);     //Create new population, no initialisation      
        int elitismOffset = 0;
        if (elitism) {																//Keep best tour elitism=true
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;							
        }

       
       if(ox2C){
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {   //Loop through every tour of the population
    		   if ((z+1)<newPopulation.populationSize()) {							//If more than 2 tours are left, use Ox2-Crossover    		
    			   Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	        		Tour parent2 = tournamentSelection(pop);       					// Choose second parent chromosome with tournament selection
	        		Tour childs[]= Ox2Crossover(parent1,parent2);					//Receive offsprings in an Tour array
	        		Tour child1=childs[0];
	        		Tour child2=childs[1];            
	        		newPopulation.saveTour(z, child1);    							//Save first offspring
	        		newPopulation.saveTour((z+1),child2);    						//Save second offspring
	        		z=z+1;						
    		   }        	
        	else {																	// If one tour is left, use order crossover
          		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
                Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
                Tour child= OrderCrossover(parent1,parent2);						//Receive offspring
                newPopulation.saveTour(z, child);                    				// save offspring in new Population
        	}      	
    		 
    	   }
    	   
       }
       if(cycC){
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {   	 // Loop through all tours of population
    		   if ((z+1)<newPopulation.populationSize()) {       	       				//If more than 2 tours are left, use Cycle-Crossover  
    			   Tour parent1 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection         
		           Tour childs[]= CycleC(parent1,parent2);								//Receive offsprings in an Tour array with offsprings         
		           newPopulation.saveTour(z, childs[0]);        						//Save first offspring in new population
		           newPopulation.saveTour((z+1),childs[1]);          					//Save second offspring in new population
		           z=z+1;
		       	}
        	
    		   else {																	// If one tour is left, use order crossover
             		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
                   Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
                   Tour child= OrderCrossover(parent1,parent2);							//Receive offspring
                   newPopulation.saveTour(z, child);                    				// save offspring in new Population
           		}
    		
    	   }
       }
       if(pmxC) {      
    	   for (int z = elitismOffset; z < newPopulation.populationSize(); z++) {		// Loop through all tours of population
    		   if ((z+1)<newPopulation.populationSize()) {        						//If more than 2 tours are left, use PMX-Crossover  
    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chomosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);	          					//Choose second parent chromosome with tournament selection
    			   Tour childs[]= PMX(parent1,parent2);									//Receive Tour array with offsprings            
    			   newPopulation.saveTour(z, childs[0]);        						//Save first offspring in new population
    			   newPopulation.saveTour((z+1),childs[1]);          					//Save second offspring in new population
    			   z=z+1;																
    		   }       	
    		   else {																	//If one tour is left use order crossover
    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chromosome with tournament selection
    			   Tour parent2 = tournamentSelection(pop);     						//Choose second parent chromosome with tournament selection         
    			   Tour child= OrderCrossover(parent1,parent2);							//receive offspring
    			   newPopulation.saveTour(z, child);                       				//save offspring
    		   }
    	   }
       }
       if(ordC) {       
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of population
               Tour parent1 = tournamentSelection(pop);									//Choose first parent chromosome with tournament selection
               Tour parent2 = tournamentSelection(pop);									//Choose second parent chromosome with tournament selection
               Tour child = OrderCrossover(parent1, parent2);							//receive offspring
               newPopulation.saveTour(i, child);										//Save offspring
             
    	   }
       }
       
       
       
       if(disM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {        //Loop through all tours of new population and use displacement mutation
        	   DisplacementMutation(newPopulation.getTour(i));
        	  
           }
       }
       if(mexM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use multiple exchange mutation
        	   MultipleExchangeMutation(newPopulation.getTour(i));
           }
       }
       if(excM) {
           for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use exchange mutation
           		ExchangeMutation(newPopulation.getTour(i));
           }
       }
       if(insM) {
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use insertion mutation
    		   InsertionMutation(newPopulation.getTour(i));
    		  
    	   }
       }
       if(invM) {				
    	   for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {		//Loop through all tours of new population and use inversion mutation
        	InversionMutation(newPopulation.getTour(i));
    	   }
       }
       
       best=newPopulation.getFittest();
       currrentPop= newPopulation;
       			
    }
    
    //parent1.tour-size-2 damit startpos endpos selbe l�nge hat wie im array zu ver�ndern, position aber um 2 verschoben
   //bei startpos und endpos wieder draufschlagen dann ver�nderst die richtigen Positionen
    public static Tour OrderCrossover(Tour parent1, Tour parent2) {
    	
    	if(GA.EventCounter==0) {
    		blockedCities=1;
    	}
        Tour child = new Tour();										
        int number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		//create first random number
        int number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		//create second random number
       
        while(number1==number2)	{										//If random numbers are equal, do it again
    		number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		
    		number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities)); 
    		continue;
    	}
        int startPos= Math.min(number1, number2)+blockedCities;						//startposition is minimum of the two random numbers
        int endPos= Math.max(number1, number2)+blockedCities;        					// endposition is the maximum of the two random numbers
        for(int bl=0;bl<blockedCities;bl++) {
        	child.setCity(bl, parent1.getCity(bl));
        }
        for (int i =blockedCities; i < parent1.tourSize(); i++) {					// Loop through parent 1
            if (i >= startPos && i <= endPos) {							//if i is in the selected substring
                child.setCity(i, parent1.getCity(i));					// set city in offspring at position i
            
            } 
        }
        for (int i = blockedCities; i < parent2.tourSize(); i++) {					// Loop through parent 2
            if (!child.containsCity(parent2.getCity(i))) {				//If offspring does not cointain actual city of parent2 add to offspring
                for (int ii = blockedCities; ii < child.tourSize(); ii++) {			//Loop through offspring
                    if (child.getCity(ii) == null) {					//Find spare position
                        child.setCity(ii, parent2.getCity(i));			//save city in offspring
                        break;
                    }
                }
            }
        }
    /*    System.out.println(parent1);
        System.out.println(parent2);
        System.out.println(child);
        System.out.println();
        System.out.println();*/
        return child;
    }
   
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2) {	
    	if(GA.EventCounter==0) {
    		blockedCities=1;
    	}
    	Tour child1=new Tour();
    	Tour child2=new Tour();
    	Tour[] kids= new Tour[2];										// Tour array for returning
    	int number1 = (int) (Math.random() *( parent1.tourSize()-blockedCities));	//First random number
    	int number2 = (int) (Math.random() * ( parent1.tourSize()-blockedCities));	//Second random number
    	while(number1==number2)	{										//If random numbers are equal, do it again	
    		number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));
    		number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities)); 
    		continue;
    	}
    	   for(int bl=0;bl<blockedCities;bl++) {
           	child1.setCity(bl, parent1.getCity(bl));
           	child2.setCity(bl,parent2.getCity(bl));
           }
    	int startPos= Math.min(number1, number2)+blockedCities;						//startposition is minimum of the two random numbers
    	int endPos= Math.max(number1, number2)+blockedCities;							// endposition is the maximum of the two random numbers
    	for(int j=blockedCities;j<parent1.tourSize();j++) {   						//Loop through parent1
    		if(j >= startPos && j <= endPos) {							//if j is in the selected substring
    			City cityP1=parent1.getCity(j);							
    			City cityP2=parent2.getCity(j);
    			child1.setCity(j, cityP2);								//set city of parent2 in first offsrping at position j
    			child2.setCity(j, cityP1);								//set city of parent1 in second offsrping at position j
    		}
    	}    	
    	for(int k=blockedCities;k<parent1.tourSize();k++) {    						//Loop through parent1
    		if (!child1.containsCity(parent1.getCity(k))) {   			//If first offspring does not cointain actual city of parent1 add to offspring
                for (int ii = blockedCities; ii < child1.tourSize(); ii++)  {		//Loop through offsrping       
                    if (child1.getCity(ii) == null)	{		 			//Find spare position                
                    	City city1 = parent1.getCity(k);
                        child1.setCity(ii, city1);						//Save city of parent1 in offspring
                        break;
                    }
                }
            }
    	}
    	for(int k=blockedCities;k<parent2.tourSize();k++) {							//Loop through parent2
    		if (!child2.containsCity(parent2.getCity(k))) {				//If second offsrping does not cointain actual city of parent 2 add to offspring
                for (int ii = 0; ii < child2.tourSize(); ii++) {       	//Loop through second offspring                      
                    if (child2.getCity(ii) == null) {      				//Find spare position
                    	City city2 = parent2.getCity(k);
                        child2.setCity(ii, city2);						//Save city of parent2 in offspring
                        break;
                    }
                }
            }
    	}    	
    	kids[0]=child1;													// Save offsprings in Array
    	kids[1]=child2;
    	return kids;
    }
    
    
    public static Tour[] PMX2 (Tour parent1, Tour parent2) { //Muss noch gemacht werden	
    	if(GA.EventCounter==0) {
    		blockedCities=1;
    	}
    	int number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
		int number2 = (int) (Math.random() *(parent1.tourSize()-blockedCities));
		Tour kids[]=new Tour[2];
		Tour child1=new Tour();
		Tour child2= new Tour();
		ArrayList<City> conflicts1= new ArrayList<City>();
		ArrayList<City> conflicts2= new ArrayList<City>();
		while (number1 == number2) {
			number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
			number2= (int) (Math.random() *(parent1.tourSize()-blockedCities));
		}
		int cut1= Math.min(number1, number2)+blockedCities;						//startposition is minimum of the two random numbers
    	int cut2= Math.max(number1, number2)+blockedCities;
		for(int bl=0;bl<blockedCities;bl++) {
	       	child1.setCity(bl, parent1.getCity(bl));
	       	child2.setCity(bl,parent2.getCity(bl));
	    }
		for(int i=blockedCities;i<parent1.tourSize();i++) {
			City c1= parent1.getCity(i);
			City c2= parent2.getCity(i);
			child1.setCity(i, c1);
			child2.setCity(i, c2);
	
			
		}
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			child1.setCity(j, c2);
			child2.setCity(j, c1);
			System.out.println("cut1: "+cut1);
			System.out.println("cut2: "+cut2);
			System.out.println("parent1: "+parent1);
			System.out.println("child1 "+child1);
			System.out.println("parent2: "+parent2);
			System.out.println("child2 "+child2);
		} 
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			for(int jj=blockedCities;jj<parent1.tourSize();jj++)
			{
				if(jj<cut1||jj>cut2) {
					if(c2==parent1.getCity(jj)) {
						conflicts1.add(c2);
					}
				}
				if(jj<cut1||jj>cut2) {
					if(c1==parent2.getCity(jj)) {
					conflicts2.add(c1);
					}
				}
			}
		}
		 
		
    }
    public static Tour[] PMX (Tour parent1, Tour parent2) { //Muss noch gemacht werden	
    	if(GA.EventCounter==0) {
    		blockedCities=1;
    	}
    	int number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
		int number2 = (int) (Math.random() *(parent1.tourSize()-blockedCities));
		Tour kids[]=new Tour[2];
		Tour child1=new Tour();
		Tour child2= new Tour();
		ArrayList<City> conflicts1= new ArrayList<City>();
		ArrayList<City> conflicts2= new ArrayList<City>();
		while (number1 == number2) {
			number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
			number2= (int) (Math.random() *(parent1.tourSize()-blockedCities));
		}
		int cut1= Math.min(number1, number2)+blockedCities;						//startposition is minimum of the two random numbers
    	int cut2= Math.max(number1, number2)+blockedCities;
		for(int bl=0;bl<blockedCities;bl++) {
	       	child1.setCity(bl, parent1.getCity(bl));
	       	child2.setCity(bl,parent2.getCity(bl));
	    }
		for(int i=blockedCities;i<parent1.tourSize();i++) {
			City c1= parent1.getCity(i);
			City c2= parent2.getCity(i);
			child1.setCity(i, c1);
			child2.setCity(i, c2);
	
			
		}
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			child1.setCity(j, c2);
			child2.setCity(j, c1);
			System.out.println("cut1: "+cut1);
			System.out.println("cut2: "+cut2);
			System.out.println("parent1: "+parent1);
			System.out.println("child1 "+child1);
			System.out.println("parent2: "+parent2);
			System.out.println("child2 "+child2);
		} 
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			if(child1.containsCity(c2)) {	
				conflicts1.add(c2);
				
			}
			if(child2.containsCity(c1)) {		
				conflicts2.add(c1);
			}
			child1.setCity(j, c2);
			child2.setCity(j, c1);
		}
		System.out.println("p1: "+parent1);
		System.out.println("p2: "+parent2);
		System.out.println("c1: "+child1);
		System.out.println("c2: "+child2);
		
	
		while(conflicts1.isEmpty()==false) {
			City inter1;
			int pos1;
			int start1;
			do {			
				
				pos1= parent2.positionofCity(conflicts1.get(0));
				start1= parent2.positionofCity(conflicts1.get(0));
				inter1=parent1.getCity(pos1);
				pos1=parent2.positionofCity(inter1);
			}
			while(!(inter1.equals(parent2.getCity(pos1))));
			parent2.setCity(pos1, conflicts1.get(0));
			parent2.setCity(start1, inter1);
			child1.setCity(pos1, conflicts1.get(0));
			child1.setCity(start1, inter1);
			conflicts1.remove(0);
		}
	
		while(conflicts2.isEmpty()==false) {
			City inter2;
			int pos2;
			int start2;
			do {			
				pos2= parent1.positionofCity(conflicts2.get(0));
				start2= parent1.positionofCity(conflicts2.get(0));
				inter2=p2Copy.getCity(pos2);
				pos2=parent1.positionofCity(inter2);
			}
			while(!(inter2.equals(parent1.getCity(pos2))));
			parent1.setCity(pos2, conflicts2.get(0));
			parent1.setCity(start2, inter2);
			child2.setCity(pos2, conflicts2.get(0));
			child2.setCity(start2, inter2);
			conflicts2.remove(0);
		}
		
		System.out.println("done DONE done DONE");
		kids[0]=child1;
		kids[1]=child2;
		return kids;
	 }

 
	 public static Tour[] CycleC(Tour parent1, Tour parent2) {
		Tour child1=new Tour();
	 	Tour child2=new Tour();
	 	City city1;
	 	City city2;
	 	Tour[] kids= new Tour[2];											// returning array
	 	int rundenzaehler=0;													// counts cycles
	 	int position=2;														//actual position of cycle process
	 	int start=2;														//starting position of cycle process
	 	ArrayList<City> notvisited= new ArrayList<City>();					//ArrayList to check which cities have not been visited
	    for(int bl=0;bl<blockedCities;bl++) {
           	child1.setCity(bl, parent1.getCity(bl));
           	child2.setCity(bl,parent2.getCity(bl));
        }
	 	for(int a=blockedCities; a<parent1.tourSize();a++) {		 					//Loop through parent1
			 City ci= parent1.getCity(a);
			 notvisited.add(ci);											//add all cities to ArrayList
		}
	 	//System.out.println(parent1);
	 	//System.out.println(parent2);	 	
	 	while(notvisited.isEmpty()==false) { 								//while ArrayList contains a not visited city
	 		if(rundenzaehler%2==1) {											//if we have an odd number of cycles
	 			for(int a=blockedCities; a<parent1.tourSize();a++) {					//Loop through parent1
					if(notvisited.contains(parent1.getCity(a))) {			//find fist not visited city in parent1
						start=a;											//Update starting point	
						position=start;										//update position
						break;
					}
				
				}
	 			do {	 					 				
	 				city1=parent1.getCity(position);
	 				child2.setCity(position, city1);						//Add city of parent1 to second offspring
	 				city2=parent2.getCity(position);
	 				child1.setCity(position, city2);						//Add city of parent2 to first offspring
	 				position=parent1.positionofCity(city2);	 				//update current position in parent1
	 				 if(notvisited.contains(city2)) {	 				  	// remove visited city
	 					 notvisited.remove(city2);
	 				 }
	 				 if(notvisited.contains(city1)) { 						// remove visited city
	 					 notvisited.remove(city1);
	 				 }
	 			}
	 			while(parent1.getCity(start)!=city2);						//While cycle is not closed
	 			rundenzaehler++;												//increase cycle counter
	 			continue;
			}
	 		
	 		if(rundenzaehler==0) {											//first cycle
	 			do {			
	 				city1=parent1.getCity(position);						//Add city of parent1 to fist offspring
	 				child1.setCity(position, city1);
	 				city2=parent2.getCity(position);						//Add city of parent2 to second offspring
	 				child2.setCity(position, city2);
	 				position=parent1.positionofCity(city2);	 				//Update current position in parent1
	 				if(notvisited.contains(city2)) {  				
	 					notvisited.remove(city2);							//remove visited city from ArrayList
	 				}
	 				if(notvisited.contains(city1)) {			 			//remove visited city from ArrayList
	 					notvisited.remove(city1);
	 				 }
	 			//	System.out.println(child1);
	 	 			//System.out.println(child2);		
	 	 			//System.out.println("next position: "+position);
	 			}
	 			while(parent1.getCity(start)!=city2);						//While cycle is not closed
	 			rundenzaehler++;												//increase cycle counter
	 			continue;			
	 		}	
	 		if(rundenzaehler%2==0&&rundenzaehler!=0) {						//even number of cycles
	 			for(int a=0; a<parent1.tourSize();a++) { 					// Loop through parent1
	 				if(notvisited.contains(parent1.getCity(a))) {			//find first not visited city
	 					start=a;											//update starting point
	 					position=start;										//update position
	 					break;
	 				}
	 			
	 			}
	 			do {	 			
	 				city1=parent1.getCity(position);						//Add city of parent1 to fist offspring
	 				child1.setCity(position, city1);
	 				 city2=parent2.getCity(position);						//Add city of parent2 to second offspring
	 				 child2.setCity(position, city2);
	 				 position=parent1.positionofCity(city2);				//Update current position in parent1
	 				 if(notvisited.contains(city2)) {		  				
	 					 notvisited.remove(city2);							//remove visited city from ArrayList
	 				 }
	 				 if(notvisited.contains(city1)){						//remove visited city from ArrayList
	 					 notvisited.remove(city1);
	 				 }
	 				//System.out.println(child1);
	 	 			//System.out.println(child2);	
	 			}
	 			while(parent1.getCity(start)!=city2);						//While cycle is not closed
	 			rundenzaehler++;											//Increase cycle counter
	 			
	 			continue;
	 		}	 		
	 	}
	 	kids[0]=child1;														//Copy offspring in array
	 	kids[1]=child2;
	 	//System.out.println("Ende");
		//System.out.println();
		return kids;
	 }
 
    private static void ExchangeMutation(Tour tour) {   	
    	int tourPos1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities; 			//Create two random positions that should be swapped
        int tourPos2 = (int) ((tour.tourSize() -blockedCities)* Math.random())+blockedCities;
        while(tourPos1==tourPos2){											//If positions are equal, do it again
             tourPos1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;			
             tourPos2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;    
        }       
        City city1 = tour.getCity(tourPos1);								//Swap the selected cities
        City city2 = tour.getCity(tourPos2);
        tour.setCity(tourPos2, city1);
        tour.setCity(tourPos1, city2);
        
                          
       
    }
    
    private static void InversionMutation(Tour tour) {  
    	Tour child= new Tour();
    	int number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;				//Create two random positions that should be swapped
		int number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
    	while(number1==number2) { 		  									//If positions are equal, do it again
    		number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
    		number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities; 
		}   	
    	int startPos= Math.min(number1, number2);							//startingpoint is the minimum of both random numbers
		int endPos= Math.max(number1, number2);								//endposition is the maximum of the two random numbers
    	int b= endPos;														//copy of end position
    	int d= startPos;													//copy of start position
    	for(int a=startPos; a<b;a++) {										//Loop through tour from start point to the half of the substring(With decreasing b)
    		City city1= tour.getCity(b);
    		child.setCity(a,city1);											//save city at current position
    		b=b-1;															//decrease end position copy
    	}
    	
    	for(int c=endPos;c>d;c--) {											//Loop through tour from endpoint to half of the substring(with increasing d)
    		City city1=tour.getCity(d);										
    		child.setCity(c,city1);											//save city at current position
    		d=d+1;															//increase start position copy
    	}		
    	
    	for (int i = blockedCities; i < tour.tourSize(); i++) {							//Loop through tour
    		 if (!child.containsCity(tour.getCity(i))) {	 				//If offspring does not contain city, add to offspring
    			 City city1 =tour.getCity(i);
    			 child.setCity(i, city1);									//save city 
    		 }
    	}  	
    	tour=child;    		
    }
    private static void DisplacementMutation(Tour tour) {    
    	Tour child = new Tour();    	
    	int number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;		//Create first random number
    	int number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;		//Create second random number
    	while(number1==number2) { 		  									//If positions are equal, do it again
    		number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
    		number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;  
    	}
    	int startPos= Math.min(number1, number2);							//startingpoint is the minimum of both random numbers
		int endPos= Math.max(number1, number2);								//endposition is the maximum of the two random numbers
    	int insertPos=(int) (Math.random() * (tour.tourSize()-(endPos-startPos)-blockedCities))+blockedCities; 	//create random position for insertion
    	int zaehler=0;														//counter
    	for(int i=blockedCities; i<tour.tourSize();i++) { 								//Loop through tour
    		if(i >= startPos && i <= endPos) { 								//if we are within the substring 
    			City city1= tour.getCity(i);
    			child.setCity(insertPos+zaehler, city1);						//save in offspring at insertposition + counter
    			zaehler+=1;													//increase counter
    		}
    	}
    	zaehler=0;															//reset counter
    	for(int j=blockedCities;j<tour.tourSize();j++) {   								//Loop through tour
    		if (!child.containsCity(tour.getCity(j))) {						//If offspring does not contain city
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    if (child.getCity(ii) == null) {						//find spare position in offspring
                    	City city1 = tour.getCity(j);
                        child.setCity(ii, city1);							//save city at spare position
                        break;
                    }
                }
            }
    	}
    	tour=child;
    
    }
    
    private static void InsertionMutation(Tour tour)    {  	  	
    	int oldPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;;	//Select a random city
    	int newPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;   //Select a new random position									
    	while(oldPos==newPos) {		
    		oldPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;			//If Position is the same, do again   
    		newPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
        }
    	City citytaken=tour.getCity(oldPos);							//save selected city
    	if(oldPos<newPos) { 											//shift all cities with a smaller position than newPos one position backwards
    		for(int a=(oldPos+1); a<=newPos;a++) {  		
    			City city1=tour.getCity(a);
    			tour.setCity((a-1), city1);
    		}
    	}
    	if(oldPos>newPos) { 											//shift all cities with a higher position than newPos one position forward
    		for(int b=(oldPos-1);b>=newPos;b--) {		
    			City city1=tour.getCity(b);
    			tour.setCity((b+1), city1);
    		}
    	}
    	tour.setCity(newPos, citytaken);								// set selected city at new position
    }

    private static void MultipleExchangeMutation(Tour tour) {  
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){		//Loop through tour
            if(Math.random() < mutationRate){               				//If a random number is smaller than our selected mutationrate do the mutation
                int tourPos2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;;    // Get a random position in the tour
                City city1 = tour.getCity(tourPos1); 						// Get the cities at target position in tour
                City city2 = tour.getCity(tourPos2);             
                tour.setCity(tourPos2, city1);								// Swap them
                tour.setCity(tourPos1, city2);
            }
        }
    }
    
   
    private static Tour tournamentSelection(Population pop) {				 // Selects candidate tour for crossover   
        Population tournament = new Population(tournamentSize, false);		 // Create a tournament population
        for (int i = 0; i < tournamentSize; i++) { 							// For each place in the tournament get a random candidate tour and add it
            int randomId = (int) (Math.random() * pop.populationSize());	//Save choosen tour in intermediate population
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest();								// Get the fittest tour of the selected tours
        return fittest;
    }
																		 
    private static Tour RWS(Population pop)									// Selects candidate tour for crossover
    {	Tour chosen=new Tour();
    	Tour inter=new Tour();
    	double Totalslotsize=0;
    	double slotsize2=0;
    	for(int i=0;i<pop.populationSize();i++) {							//Loop through population
    		inter=pop.getTour(i);
    		Totalslotsize+=inter.getFitness();								// add Fitness of current tour to total slotsize
    	} 	
    	double select=Math.random()*Totalslotsize;							//Randomly select a fitnesslevel within the slotsize
    	for(int j=0; j<pop.populationSize();j++) {  						//Loop through poulation
    		inter=pop.getTour(j);
    		slotsize2+=inter.getFitness();									//add fitness of current tour to slotsize2
    		if(select<=slotsize2) {											// if our sum of fitness of all checked tours is higher than our selected fitnesslevel, select the current tour
    			chosen=inter;
    			break;
    		}
    	}
    	
    	return chosen;
    }

	@Override
	public void atCity(AtEvent e) throws Exception{
		EventCounter++;
		Route route= new Route();
		lastEventTime= new TimeElement();
		route.WayFromTo(best);
		durations=route.Duration;
		Nodes=route.Allnodes;
		Intersections=route.intersections;
		RouteServiceEvent event= new RouteServiceEvent(this, Nodes,Intersections, durations);
		fireEvent(event);
		
	}

	@Override
	public void atIntersection(AtEvent e) {
		EventCounter++;
		All_Cities.deleteCity(lastCity);
		City Intersection= new City(numOfCities,"Intersection",e.longitude,e.latitude);
		All_Cities.addCity(Intersection);
		lastCity=Intersection;
		lastEventTime= new TimeElement();
		toDrivetoIntersection=0; //
	}

	@Override
	public void GPS_Signal(AtEvent e)  {
		EventCounter++;
		All_Cities.deleteCity(lastCity);
		City GPS= new City(numOfCities,"GPS",e.longitude,e.latitude);
		All_Cities.addCity(GPS);
		lastCity=GPS;
		lastEventTime= new TimeElement();
		for( int i=0; i<Nodes.size()-1;i++) {								//Find nodes I am in between now
			double maxLat= Math.max(Nodes.get(i).getLatitude(),Nodes.get(i+1).getLatitude());
			double minLat= Math.min(Nodes.get(i).getLatitude(),Nodes.get(i+1).getLatitude());
			double maxLon= Math.max(Nodes.get(i).getLongitude(),Nodes.get(i+1).getLongitude());
			double minLon= Math.min(Nodes.get(i).getLongitude(),Nodes.get(i+1).getLongitude());
			
			if(e.getLatitude()<=maxLat&&e.getLatitude()>=minLat&&e.getLongitude()<=maxLon&&e.getLongitude()>=minLon) {
				
				double latratio= (Nodes.get(i+1).getLatitude()-e.getLatitude())/(Nodes.get(i+1).getLatitude()-Nodes.get(i).getLatitude());
				double lonratio=(Nodes.get(i+1).getLongitude()-e.getLongitude())/(Nodes.get(i+1).getLongitude()-Nodes.get(i).getLongitude());
				double ratio= (latratio+lonratio)/2;
				
				int hour= lastEventTime.getHour();																//current hour
				double ttnh=lastEventTime.getTimeToNextHour();
				if(durations[i]*ratio*Maths.getFaktor(hour)>ttnh) {							//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durations[i]*ratio*Maths.getFaktor(hour);		;									//calculate the time from sum to next hour
					double hourratio= tohour/durations[i]*Maths.getFaktor(hour)*ratio;				// Calculate ratio of driven way in this section
					toDrivetoIntersection=hourratio*durations[i]*ratio*Maths.getFaktor(hour)+(1-hourratio)*durations[i]*ratio*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
																								//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
				}
				else
				{
					toDrivetoIntersection=durations[i]*ratio*Maths.getFaktor(hour);
				}
				int nextIntersection=-1;
				for(int l=0; l<Intersections.size();l++) {
					for(int k=i;k<Nodes.size();k++) {
						if(Nodes.get(k).getId()==Intersections.get(l).getId()) {
							nextIntersection=k;
							break;	
						}
					
					}
					if(nextIntersection!=-1) {
						break;
					}
						
				}
				
				for(int j=i+1;j<=nextIntersection;j++) { // Nicht bis Node Size, bis n�chste Intersection, wie finden?
					
					if(toDrivetoIntersection+durations[j]*Maths.getFaktor(hour)>ttnh) {
						double tohour=ttnh-durations[j]*Maths.getFaktor(hour);		;									//calculate the time from sum to next hour
						double hourratio= tohour/durations[j]*Maths.getFaktor(hour);									// Calculate ratio of driven way in this section
						toDrivetoIntersection+=hourratio*durations[j]*Maths.getFaktor(hour)+(1-hourratio)*durations[i]*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
						ttnh+=3600;	
						hour+=1;
						if(hour==24) {
							hour=0;
						}
					
					}
					else {
						toDrivetoIntersection+=durations[j]*Maths.getFaktor(hour);
					}
				}
				break;
			}
		}
		
	}
    
}


