import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//Main algorithm and optimizer based on evolutionary algorithm
//Implements Listener and reacts on time depending events created by the simulator
//Sets properties and parameters, activates the correct operators for the EA
public class EA implements myListener {

//VARIABLES:			
    //Properties
	GUI_Start form;
	static boolean OP_Stop=false;
	static boolean lastCityvisited=false;
	int i;
	int j;
	int k;
	//EA parameters
    static int numOfCities;
	static int popSize=25;			
	static int iterations1=1000;
	static int iterations2=100000;
	static long timeStop=0;
	static double mutationRate =1;
	static double crossoverRate = 1;
	static int m� =5;
	static double generationGap=0;
    static int tournamentSize = 1;	
	static double c=0.5;
	static double theta=0.5;
	static double shiftDistance=0.5;
	static int blockedCities=2;
	static int elitismoffset=0;
	static Population pop;
	static Population newOffsprings;  
	static boolean dynamicP=false;
	//Operators
	static boolean ox2C=false;
	static boolean ordC=false;
	static boolean pmxC=false;
	static boolean cycC=true
			;
	static boolean disM=false;
	static boolean insM=false;
	static boolean invM=true;
	static boolean excM=false;
	static boolean mexM=false;
	static boolean elitism=true;
	//API request data
	ArrayList<City> Nodes;
	ArrayList<City> Intersections;
	static double[] durations;
	//Eventhandling and duration calculation
	static int EventCounter=0;
	static Tour best;
	static Tour lastbest;
	static City lastLocation;
	static double toDrivetoCity=0;
	static double toDrivetoIntersection=0;
	static double toDrivetoNode=0;
	static TimeElement lastEventTime;
	
	private ArrayList<RouteServiceListener> listenerList= new ArrayList<RouteServiceListener>();
	
	
	
	
//METHODS:
	//Methods for setting properties

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
				iterations1/=iteration.getValue();		
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
				}
				if(e.getSource()==JmexM) {
					j=5;
					
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
			
		
			iterText= new JLabel("Wieviel iterations sollen durchgef�hrt werden?");
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
		
	
		Distanzmatrix.createAll_Cities();
		numOfCities=Distanzmatrix.CreatingnumOfCities;
		Distanzmatrix.createDurationMatrix();
		Distanzmatrix.createAllMatrixes();
		System.out.print(" Fakotren: ");
		for(int a=0; a<Maths.Faktoren.length;a++)
		{
			System.out.print(" "+Maths.Faktoren[a]);
		}
		System.out.println();
		System.out.print("Gamma Fakotren: ");
		for(int a=0; a<Maths.GammaFaktoren.length;a++)
		{
			System.out.print(" "+Maths.GammaFaktoren[a]);
		}
		System.out.println();
	}
	
	public static void blockedCities() {
	    	if(EA.EventCounter==0) {
	    		blockedCities=1;
	    	}
	    	else{			
	    		blockedCities=2;
	    	}
	    }

	//Methods for evolutionary algorithm
	//Evolve population by using recombination, mutation, selection and replacing operators
	//Initializes first population
	    	//If true: Initialize first population and generate individuals
	public void evolvePopulation(boolean initilize) {
	    	    if(OP_Stop==false) {	
	    	    	if(initilize) {
	    	    		pop = new Population(popSize, true);
	    	    	}	
	    	    	/* 	if(checking) {
	    	   	for(int i=0; i<pop.populationSize();i++) {
	    	    			System.out.println(pop.getTour(i));
	    	    		}
	    	    	}*/
	    	    	
	    	    //	newOffsprings= new Population(popSize*m�, false);
	    	    	  	newOffsprings= new Population(popSize, false);
	    	     //Muss noch zu new Generation werden   Population newOffsprings = new Population(pop.populationSize(), false);     //Create new population, no initialisation      
	    	        int elitismOffset = 0;
	    	        if (elitism) {																//Keep best tour elitism=true
	    	            newOffsprings.saveTour(0, pop.getFittest());
	    	            elitismOffset = 1;							
	    	        }
	    	       
	    	       
	    	       if(ox2C){
	    	    	   for (int z = elitismOffset; z < newOffsprings.populationSize(); z++) {   //Loop through every tour of the population
	    	    		   if ((z+1)<newOffsprings.populationSize()) {							//If more than 2 tours are left, use Ox2-Crossover    		
	    	    			   Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	    		        		Tour parent2 = tournamentSelection(pop);       					// Choose second parent chromosome with tournament selection
	    		        		Tour childs[]= Ox2Crossover(parent1,parent2);					//Receive offsprings in an Tour array
	    		        		Tour child1=childs[0];
	    		        		Tour child2=childs[1];            
	    		        		newOffsprings.saveTour(z, child1);    							//Save first offspring
	    		        		newOffsprings.saveTour((z+1),child2);    						//Save second offspring
	    		        		z=z+1;						
	    	    		   }        	
	    	        	else {																	// If one tour is left, use order crossover
	    	          		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	    	                Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
	    	                Tour child= OrderCrossover(parent1,parent2);						//Receive offspring
	    	                newOffsprings.saveTour(z, child);                    				// save offspring in new Population
	    	        	}      	
	    	    		 
	    	    	   }
	    	    	   
	    	       }
	    	       if(cycC){
	    	    	   for (int z = elitismOffset; z < newOffsprings.populationSize(); z++) {   	 // Loop through all tours of population
	    	    		   if ((z+1)<newOffsprings.populationSize()) {       	       				//If more than 2 tours are left, use Cycle-Crossover  
	    	    			   Tour parent1 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection
	    	    			   Tour parent2 = tournamentSelection(pop);								// Choose second parent chromosome with tournament selection         
	    			           Tour childs[]= CycleC(parent1,parent2);								//Receive offsprings in an Tour array with offsprings         
	    			           newOffsprings.saveTour(z, childs[0]);        						//Save first offspring in new population
	    			           newOffsprings.saveTour((z+1),childs[1]);          					//Save second offspring in new population
	    			           z=z+1;
	    			       	}
	    	        	
	    	    		   else {																	// If one tour is left, use order crossover
	    	             		Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	    	                   Tour parent2 = tournamentSelection(pop);           				   	// Choose second parent chromosome with tournament selection
	    	                   Tour child= OrderCrossover(parent1,parent2);							//Receive offspring
	    	                   newOffsprings.saveTour(z, child);                    				// save offspring in new Population
	    	           		}
	    	    		
	    	    	   }
	    	       }
	    	       if(pmxC) {      
	    	    	   for (int z = elitismOffset; z < newOffsprings.populationSize(); z++) {		// Loop through all tours of population
	    	    		   if ((z+1)<newOffsprings.populationSize()) {        						//If more than 2 tours are left, use PMX-Crossover  
	    	    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chomosome with tournament selection
	    	    			   Tour parent2 = tournamentSelection(pop);	          					//Choose second parent chromosome with tournament selection
	    	    			   Tour childs[]= PMX(parent1,parent2);									//Receive Tour array with offsprings            
	    	    			   newOffsprings.saveTour(z, childs[0]);        						//Save first offspring in new population
	    	    			   newOffsprings.saveTour((z+1),childs[1]);          					//Save second offspring in new population
	    	    			   z=z+1;																
	    	    		   }       	
	    	    		   else {																	//If one tour is left use order crossover
	    	    			   Tour parent1 = tournamentSelection(pop);								//Choose first parent chromosome with tournament selection
	    	    			   Tour parent2 = tournamentSelection(pop);     						//Choose second parent chromosome with tournament selection         
	    	    			   Tour child= OrderCrossover(parent1,parent2);							//receive offspring
	    	    			   newOffsprings.saveTour(z, child);                       				//save offspring
	    	    		   }
	    	    	   }
	    	       }
	    	       if(ordC) {       
	    	    	   for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {		//Loop through all tours of population
	    	               Tour parent1 = tournamentSelection(pop);									//Choose first parent chromosome with tournament selection
	    	               Tour parent2 = tournamentSelection(pop);									//Choose second parent chromosome with tournament selection
	    	               Tour child = OrderCrossover(parent1, parent2);							//receive offspring
	    	               newOffsprings.saveTour(i, child);										//Save offspring
	    	             
	    	    	   }
	    	       }
	    	       
	    	       
	    	       
	    	       if(disM) {
	    	           for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {        //Loop through all tours of new population and use displacement mutation
	    	        	   DisplacementMutation(newOffsprings.getTour(i));
	    	        	  
	    	           }
	    	       }
	    	       if(mexM) {
	    	           for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {		//Loop through all tours of new population and use multiple exchange mutation
	    	        	   MultipleExchangeMutation(newOffsprings.getTour(i));
	    	           }
	    	       }
	    	       if(excM) {
	    	           for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {		//Loop through all tours of new population and use exchange mutation
	    	           		ExchangeMutation(newOffsprings.getTour(i));
	    	           }
	    	       }
	    	       if(insM) {
	    	    	   for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {		//Loop through all tours of new population and use insertion mutation
	    	    		   InsertionMutation(newOffsprings.getTour(i));
	    	    		  
	    	    	   }
	    	       }
	    	       if(invM) {				
	    	    	   for (int i = elitismOffset; i < newOffsprings.populationSize(); i++) {		//Loop through all tours of new population and use inversion mutation
	    	        	InversionMutation(newOffsprings.getTour(i));
	    	    	   }
	    	       }
	    	    /*  for( int af=0; af<newOffsprings.populationSize();af++) {
	    	    	  System.out.println(newOffsprings.getTour(af));
	    	       }*/
	    	     
	    	      
	    	       //REINSERTION
	    	       //Haben check for duplicates und orderdiffrence, saveALl ist positionszuweisung falsch. 
	    	
  	    	/*	
	    	       
	    	       newOffsprings.eliminateDuplicates();
	    	       if(newOffsprings.checkforNull()>=pop.populationSize()) {
	    	    	   for(int pp=0;pp<pop.populationSize();pp++) {
	    	    		   pop.saveTour(pp, newOffsprings.getFittest());
	    	    		   newOffsprings.deleteTour(newOffsprings.getFittest());
	    	    	   }
	    	       }
	    	       else {
	    	    		   int tofill=pop.populationSize()-newOffsprings.checkforNull();
	    	    		   for(int a=0;a<pop.populationSize();a++) {
	    	    			   
	    	    			   //Tritt nur ein wenn soviele Duplikate vorkommen dasss Pop nur nch soviel elemente enth�lt wie zu f�llen sind
	    	    			   if(pop.populationSize()-a==tofill) {
	    	    				System.out.println("hi");
	    	    				   for(int a1=0;a1<pop.populationSize();a1++) {
	    	    					   if(pop.getTour(a1)!=null) {
	    	    						   for(int a11=0;a11<newOffsprings.populationSize();a11++) {
	    	    							   if(newOffsprings.getTour(a11)==null) {
	    	    								   newOffsprings.saveTour(a11, new Tour(pop.getTour(a1)));
	    	    								   System.out.println("add no choice");
	    	    								   tofill--;
	    	    								   break;
	    	    							   }
	    	    						   }
	    	    					   }
	    	    					   if(tofill==0) {
	    	    						   break;
	    	    					   }  
	    	    				   }
	    	    				   if(tofill==0) {
	    	    					   pop=newOffsprings;
	    	    					   break;
	    	    				   }
	    	    				   else {
	    	    					   System.out.println("MASSSSSIVE MISTAKE IN REPLACEMENT");
	    	    				   }
	    	    			   }
	    	    			   Tour besttoAdd= new Tour(pop.getFittest());
	    	    			   boolean same=false;
	    	    			   for(int aa=0;aa<newOffsprings.populationSize();aa++) {
	    	    				   if(newOffsprings.getTour(aa)!=null&&besttoAdd.checkforOrderDiffrence(newOffsprings.getTour(aa))==false) {
	    	    					   same=true;
	    	    					   break;
	    	    				   }
	    	    			   }
	    	    			   if(same==false) {
	    	    				   for(int aaa=0;aaa<newOffsprings.populationSize();aaa++) {
	    	    					   if(newOffsprings.getTour(aaa)==null) {
	    	    						   newOffsprings.saveTour(aaa, besttoAdd);
	    	    						   System.out.println("added toAdd");
	    	    						   tofill--;
	    	    					   }
	    	    				   }
	    	    			   }
	    	    			   pop.deleteTour(pop.getFittest());
	    	    			   if(tofill==0) {
	    	    				   pop=newOffsprings;
	    	    				   break;
	    	    			   }
	    	    			   
	    	    		   }
	    	    		   
	    	       }
	    	       
	    	    		
	    	       
	    	
	    	    }
	    	    else {
	    	    	//System.out.println("OP STOP!!");
	    	    }*/
	    	       
	    	    }
	    	    pop=newOffsprings  ;
	    	    best= pop.getFittest();
	    	
	    	  
	    	 		
			}
	    	 
	        
    
    


	//Run the correct operator
    public void operate(String Recombination) {
  	  
    	
		Tour child1=null;
		Tour child2=null;
		int Case=0;
		if(Recombination.equals("Cycle")) {
			Case=1;
		  }
		else if(Recombination==("PMX")) {
			Case=2;
		  }
		else if(Recombination=="Ox2") {
			Case=3;
		  }
		else if(Recombination=="Ord") {
			Case=4;
		}
		
		for (int z = 0; z < EA.pop.populationSize(); z++) {   //Loop through every tour of the population
			
				if ((z+1)<pop.populationSize()) {		
					Tour parent1 = tournamentSelection(pop);							//Choose first parent chromosome with tournament selection
	        		Tour parent2 = tournamentSelection(pop); 
					if(Math.random()<crossoverRate) {//If more than 2 tours are left, use Ox2-Crossover    		
	    			         					// Choose second parent chromosome with tournament selection
		        		switch (Case) {
							case 1:
								Tour childs1[]= CycleC(parent1, parent2);
								child1=childs1[0];
								child2=childs1[1];
								break;
							case 2:
							Tour childs2[]= PMX(parent1, parent2);
							child1=childs2[0];
							child2=childs2[1];
							case 3:
								Tour childs3[]=Ox2Crossover(parent1,parent2);
								child1=childs3[0];
								child2=childs3[1];
							case 4:
								child1 = OrderCrossover(parent1, parent2);
							default:
								break;
						}   
		        		switch (Case) {
							case 1: case 2: case 3:
								newOffsprings.saveTour(z, child1);    							
				        		newOffsprings.saveTour((z+1),child2);    						
				        		z=z+1;
								break;
							case 4:
								newOffsprings.saveTour(z, child1);
							default:
								break;
						}						
	    		   }  
					else {
						newOffsprings.saveTour(z, parent1);
						newOffsprings.saveTour(z+1, parent2);
					}
					
				}
	        	else {		
	        		Tour parent1 = tournamentSelection(pop);							
	                Tour parent2 = tournamentSelection(pop);  
	        		if(Math.random()<crossoverRate) {
	          		         				   
	                child1= OrderCrossover(parent1,parent2);						
	                newOffsprings.saveTour(z, child1);    
	        		}			
	        		else {
	        			 newOffsprings.saveTour(z, parent1); 
	        	}      	 	
			}
		}
	}
	
	
    //Recombination operators
    public static Tour OrderCrossover(Tour parent1, Tour parent2) {
    	//Check how many city objects have to be locked in the tour
    	blockedCities();
    
        Tour child = new Tour();
        
        //Get start and end position of substring
        int number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		
        int number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		
       
        while(number1==number2)	{										
    		number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));		
    		number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities)); 
    		continue;
    	}
        int startPos= Math.min(number1, number2)+blockedCities;						
        int endPos= Math.max(number1, number2)+blockedCities;        				     
        
        //Set locked cities in child
        for(int bl=0;bl<blockedCities;bl++) {
        	child.setCity(bl, parent1.getCity(bl));
        }
        
        //copy subtring from parent1 to child
        for (int i =blockedCities; i < parent1.tourSize(); i++) {	
            if (i >= startPos && i <= endPos) {						
                child.setCity(i, parent1.getCity(i));            
            } 
        }
        //Fill up with remaining cities of P2
        for (int i = blockedCities; i < parent2.tourSize(); i++) {
            if (!child.containsCity(parent2.getCity(i))) {			
                for (int ii = blockedCities; ii < child.tourSize(); ii++) {			
                    if (child.getCity(ii) == null) {					
                        child.setCity(ii, parent2.getCity(i));			
                        break;
                    }
                }
            }
        }
        return child;
    }
   
    public static Tour[] Ox2Crossover(Tour parent1, Tour parent2) {	
    	
    	blockedCities();
    	
    	Tour child1=new Tour();
    	Tour child2=new Tour();
    	Tour[] kids= new Tour[2];
    	
    	//Get start and end position of substrings
    	int number1 = (int) (Math.random() *( parent1.tourSize()-blockedCities));	
    	int number2 = (int) (Math.random() * ( parent1.tourSize()-blockedCities));	
    	while(number1==number2)	{											
    		number1 = (int) (Math.random() * (parent1.tourSize()-blockedCities));
    		number2 = (int) (Math.random() * (parent1.tourSize()-blockedCities)); 
    		continue;
    	}
    	int startPos= Math.min(number1, number2)+blockedCities;						
    	int endPos= Math.max(number1, number2)+blockedCities;
    	
    	//Set blocked cities in child
    	for(int bl=0;bl<blockedCities;bl++) {
           	child1.setCity(bl, parent1.getCity(bl));
           	child2.setCity(bl,parent2.getCity(bl));
        }
    	//Copy substring of parent 1 in child 2 and parent2 in child1						
    	for(int j=blockedCities;j<parent1.tourSize();j++) {   			
    		if(j >= startPos && j <= endPos) {							
    			City cityP1=parent1.getCity(j);							
    			City cityP2=parent2.getCity(j);
    			child1.setCity(j, cityP2);								
    			child2.setCity(j, cityP1);								
    			}
    	}   
    	//Fill up child1 with remaining cities in parent1, analog with child 2 and parent2
    	for(int k=blockedCities;k<parent1.tourSize();k++) {    	
    		if (!child1.containsCity(parent1.getCity(k))) {   		
    			for (int ii = blockedCities; ii < child1.tourSize(); ii++)  {       
                    if (child1.getCity(ii) == null)	{		 			                
                    	City city1 = parent1.getCity(k);
                        child1.setCity(ii, city1);						
                        break;
                    }
                }
            }
    		if (!child2.containsCity(parent2.getCity(k))) {			
                for (int ii = 0; ii < child2.tourSize(); ii++) {                          
                    if (child2.getCity(ii) == null) {      			
                    	City city2 = parent2.getCity(k);
                        child2.setCity(ii, city2);					
                        break;
                    }
                }
    		}
    
    	}
    	
    	kids[0]=child1;											
    	kids[1]=child2;
    	return kids;
    }
    
    public static Tour[] PMX (Tour parent1, Tour parent2) { //Muss noch gemacht werden	
    	blockedCities();
    	int number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
		int number2 = (int) (Math.random() *(parent1.tourSize()-blockedCities));
		Tour kids[]=new Tour[2];
		Tour child1=new Tour();
		Tour child2= new Tour();
		
		while (number1 == number2) {
			number1 =(int) (Math.random() *(parent1.tourSize()-blockedCities));
			number2= (int) (Math.random() *(parent1.tourSize()-blockedCities));
		}
		int cut1= Math.min(number1, number2)+blockedCities;					
    	int cut2= Math.max(number1, number2)+blockedCities;
		for(int bl=0;bl<blockedCities;bl++) {
	       	child1.setCity(bl, parent1.getCity(bl));
	       	child2.setCity(bl,parent2.getCity(bl));
	    }
		for(int j=cut1;j<=cut2;j++) {	
			City c1= parent1.getCity(j);
			City c2= parent2.getCity(j);
			child1.setCity(j, c1);
			child2.setCity(j, c2);
		} 	
		for(int jj=cut1;jj<=cut2;jj++) {
			City inter1=parent1.getCity(jj);
			City inter2=parent2.getCity(jj);
			int pos1=jj;
			int pos2=jj;
			if(child1.containsCity(inter2)==false) {			
				do {
					
					inter2=parent1.getCity(pos2);
					pos2=parent2.positionofCity(inter2);
					inter2=parent2.getCity(pos2);
				}
				while(child1.containsCity(inter2)==true);
				child1.setCity(pos2, parent2.getCity(jj));
			}
			if(child2.containsCity(inter1)==false) {	
				do {
					inter1=parent2.getCity(pos1);
					pos1=parent1.positionofCity(inter1);
					inter1=parent1.getCity(pos1);
				}
				while(child2.containsCity(inter1)==true);
				child2.setCity(pos1, parent1.getCity(jj));
			}
		}
		for(int jjj=blockedCities;jjj<parent1.tourSize();jjj++) {
			City c1= parent1.getCity(jjj);
			City c2=parent2.getCity(jjj);
			
			if(child1.containsCity(c2)==false) {
				child1.setCity(jjj, c2);
			}
			if(child2.containsCity(c1)==false) {
				child2.setCity(jjj, c1);
			}
		}
		  kids[0]=child1;
		  kids[1]=child2;  
		  return kids;
    }

 
	public static Tour[] CycleC(Tour parent1, Tour parent2) {
		blockedCities();
		Tour child1=new Tour();
	 	Tour child2=new Tour();
	 	City city1;
	 	City city2;
	 	Tour[] kids= new Tour[2];											
	 	int cyclecounter=0;													
	 	//Position within a cycle
	 	int position=blockedCities;														
	 	//Start of a cycle
	 	int start=blockedCities;														
	 	ArrayList<City> notvisited= new ArrayList<City>();					
	    //Set blocked cities in child
	 	for(int bl=0;bl<blockedCities;bl++) {
           	child1.setCity(bl, parent1.getCity(bl));
           	child2.setCity(bl,parent2.getCity(bl));
        }
	 	//add all cities of parent1 to list 
	 	for(int a=blockedCities; a<parent1.tourSize();a++) {		 					
			 City ci= parent1.getCity(a);
			 notvisited.add(ci);											
		}
	 	//Do cycles while the list is not empty and 
	 	//distinguish in operation between odd and straight  numbers of cycle rounds	
	 	while(notvisited.isEmpty()==false) { 								
	 		if(cyclecounter%2==1) {											
	 			for(int a=blockedCities; a<parent1.tourSize();a++) {		
					if(notvisited.contains(parent1.getCity(a))) {			
						start=a;												
						position=start;										
						break;
					}
				
				}
	 			do {	 					 				
	 				city1=parent1.getCity(position);
	 				child2.setCity(position, city1);						
	 				city2=parent2.getCity(position);
	 				child1.setCity(position, city2);						
	 				position=parent1.positionofCity(city2);	 				
	 				 if(notvisited.contains(city2)) {	 				  	
	 					 notvisited.remove(city2);
	 				 }
	 				 if(notvisited.contains(city1)) { 						
	 					 notvisited.remove(city1);
	 				 }
	 			}
	 			while(parent1.getCity(start)!=city2);						
	 			cyclecounter++;												
	 			continue;
			}
	 		
	 		if(cyclecounter==0) {									
	 			do {			
	 				city1=parent1.getCity(position);				
	 				child1.setCity(position, city1);
	 				city2=parent2.getCity(position);				
	 				child2.setCity(position, city2);
	 				position=parent1.positionofCity(city2);	 			
	 				if(notvisited.contains(city2)) {  				
	 					notvisited.remove(city2);						
	 				}
	 				if(notvisited.contains(city1)) {			 		
	 					notvisited.remove(city1);
	 				 }
	 			}
	 			while(parent1.getCity(start)!=city2);						
	 			cyclecounter++;												
	 			continue;			
	 		}	
	 		if(cyclecounter%2==0&&cyclecounter!=0) {						
	 			for(int a=0; a<parent1.tourSize();a++) { 					
	 				if(notvisited.contains(parent1.getCity(a))) {			
	 					start=a;											
	 					position=start;											 					break;
	 				}
	 			
	 			}
	 			do {	 			
	 				city1=parent1.getCity(position);						
	 				child1.setCity(position, city1);
	 				 city2=parent2.getCity(position);						
	 				 child2.setCity(position, city2);
	 				 position=parent1.positionofCity(city2);				
	 				 if(notvisited.contains(city2)) {		  				
	 					 notvisited.remove(city2);							
	 				 }
	 				 if(notvisited.contains(city1)){						
	 					 notvisited.remove(city1);
	 				 }	
	 			}
	 			while(parent1.getCity(start)!=city2);						
	 			cyclecounter++;											
	 			
	 			continue;
	 		}	 		
	 	}
	 	kids[0]=child1;														
	 	kids[1]=child2;
		return kids;
	 }
 
	//Swap two randomly chosen cities
	//Mutation operators
    public static void ExchangeMutation(Tour tour) {   	
    	if(Math.random()<mutationRate) {
    		blockedCities();
	    	int tourPos1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities; 			
	        int tourPos2 = (int) ((tour.tourSize() -blockedCities)* Math.random())+blockedCities;
	        while(tourPos1==tourPos2){					
	             tourPos1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;			
	             tourPos2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;    
	        }       
	        City city1 = tour.getCity(tourPos1);								
	        City city2 = tour.getCity(tourPos2);
	        tour.setCity(tourPos2, city1);
	        tour.setCity(tourPos1, city2); 
    	}
    }
    
    //select and copy a substring of tour and paste it mirrored
    private static void InversionMutation(Tour tour) {  
    	if(Math.random()<mutationRate) {
    		blockedCities();
	    	Tour child= new Tour();
	    	int number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;				//Create two random positions that should be swapped
			int number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
	    	while(number1==number2) { 		  							
	    		number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
	    		number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities; 
			}   	
	    	int startPos= Math.min(number1, number2);							
			int endPos= Math.max(number1, number2);								
	    	int b= endPos;													
	    	int d= startPos;												
	    	for(int a=startPos; a<b;a++) {									
	    		City city1= tour.getCity(b);
	    		child.setCity(a,city1);											
	    		b=b-1;															
	    	}
	    	
	    	for(int c=endPos;c>d;c--) {											
	    		City city1=tour.getCity(d);										
	    		child.setCity(c,city1);											
	    		d=d+1;															
	    	}		
	    	
	    	for (int i = blockedCities; i < tour.tourSize(); i++) {				
	    		if (!child.containsCity(tour.getCity(i))) {	 				    			 
	    			City city1 =tour.getCity(i);
	    			 child.setCity(i, city1);									 
	    		 }
	    	}  	
	    	tour=child;    
    	}
    }
    
    //Cut a substring and paste it at random position
    private static void DisplacementMutation(Tour tour) {    
    	blockedCities();
    	if(Math.random()<mutationRate) {
	    	Tour child = new Tour();    	
	    	int number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;	
	    	int number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;	
	    	while(number1==number2) { 		  								
	    		number1 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
	    		number2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;  
	    	}
	    	int startPos= Math.min(number1, number2);						
			int endPos= Math.max(number1, number2);								
	    	int insertPos=(int) (Math.random() * (tour.tourSize()-(endPos-startPos)-blockedCities))+blockedCities; 	//create random position for insertion
	    	int zaehler=0;														
	    	for(int i=blockedCities; i<tour.tourSize();i++) { 							
	    		if(i >= startPos && i <= endPos) { 								 
	    			City city1= tour.getCity(i);
	    			child.setCity(insertPos+zaehler, city1);						
	    			zaehler+=1;													
	    		}
	    	}
	    	zaehler=0;															
	    	for(int j=blockedCities;j<tour.tourSize();j++) {   					
	    		if (!child.containsCity(tour.getCity(j))) {						
	                for (int ii = 0; ii < child.tourSize(); ii++) {
	                    if (child.getCity(ii) == null) {						
	                    	City city1 = tour.getCity(j);
	                        child.setCity(ii, city1);							
	                        break;
	                    }
	                }
	            }
	    	}
	    	tour=child;  
    	}
    }
    
    //Cut a random city and paste it at random position
    private static void InsertionMutation(Tour tour)    {  	  	
    	blockedCities();
    	if(Math.random() < mutationRate) {
	    	int oldPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;	
	    	int newPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;  									
	    	while(oldPos==newPos) {		
	    		oldPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;		   
	    		newPos = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;
	        }
	    	City citytaken=tour.getCity(oldPos);						
	    	if(oldPos<newPos) { 										
	    		for(int a=(oldPos+1); a<=newPos;a++) {  		
	    			City city1=tour.getCity(a);
	    			tour.setCity((a-1), city1);
	    		}
	    	}
	    	if(oldPos>newPos) { 									
	    		for(int b=(oldPos-1);b>=newPos;b--) {		
	    			City city1=tour.getCity(b);
	    			tour.setCity((b+1), city1);
	    		}
	    	}
	    	tour.setCity(newPos, citytaken);							
    	}
    }
    //Loop through tour and depending on mutation rate swap the city of the loop with another random city
    private static void MultipleExchangeMutation(Tour tour) {  
    	blockedCities();
    	for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){	
            if(Math.random() < mutationRate){               				
                int tourPos2 = (int) ((tour.tourSize()-blockedCities) * Math.random())+blockedCities;        
                City city1 = tour.getCity(tourPos1); 						
                City city2 = tour.getCity(tourPos2);             
                tour.setCity(tourPos2, city1);							
                tour.setCity(tourPos1, city2);
            }
        }
    }
    
    //Choose number of random tours (=tournament size) and select the fittest of them
    //Selection operators
    private static Tour tournamentSelection(Population pop) {				    
        Population tournament = new Population(tournamentSize, false);		 
        for (int i = 0; i < tournamentSize; i++) { 							
            int randomId = (int) (Math.random() * pop.populationSize());	
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest();								
        return fittest;
    }
	
    //Roulettewheel selection where the fitness of a tour equals its likelyhood to be chosen
    private static Tour RWS(Population pop)								
    {	Tour chosen=new Tour();
    	Tour inter=new Tour();
    	double Totalslotsize=0;
    	double slotsize2=0;
    	for(int i=0;i<pop.populationSize();i++) {							
    		inter=pop.getTour(i);
    		Totalslotsize+=inter.getFitness();								
    	} 	
    	double select=Math.random()*Totalslotsize;							
    	for(int j=0; j<pop.populationSize();j++) {  						
    		inter=pop.getTour(j);
    		slotsize2+=inter.getFitness();									
    		if(select<=slotsize2) {											
    			chosen=inter;
    			break;
    		}
    	}
    	return chosen;
    }
    
    //Starts the simulation
    //First Route Request, tour and All_Cities Adaption
    //sets algorithm to run=true
    //Replacing operators
    
    //Start dynamic algorithm and process
    public void start() throws Exception {
    	dynamicP=true;
    	Route route= new Route();
		lastEventTime= new TimeElement();
		System.out.println("START DYNAMIC PROCESS: "+lastEventTime);
		System.out.println();
		
		route.WayFromTo(best);
		durations=route.Duration;
		EventCounter++;
		Nodes=route.Nodes_as_City;
		Intersections=route.intersections;
		
		//Adaped first and last duration value with duration approximation from first intersection to second node
		//and penultimate node to last intersection 
		//replace first and last node with first and last city object in "Intersection"
		//Set coordinates of cities of the route by using coordinates of first and last "intersection" 

		double lat_ratio_start=(Nodes.get(1).getLatitude()-Intersections.get(0).getLatitude())/(Nodes.get(1).getLatitude()-Nodes.get(0).getLatitude());	
		double lon_ratio_start=(Nodes.get(1).getLongitude()-Intersections.get(0).getLongitude())/(Nodes.get(1).getLongitude()-Nodes.get(0).getLongitude());
		double avg_ratio_start= (lat_ratio_start+lon_ratio_start)/2; 
		
		if(avg_ratio_start<0) { //NOCHMAL DOUBLE CHEKCEN
			avg_ratio_start=avg_ratio_start*(-1);
		}
		durations[0]=durations[0]*avg_ratio_start;
		
	
	
		All_Cities.getCity(0).setCoordinates(Intersections.get(0).getLongitude(), Intersections.get(0).getLatitude());
		Distanzmatrix.startCity.setCoordinates(Intersections.get(0).getLongitude(), Intersections.get(0).getLatitude());
		Nodes.set(0, Intersections.get(0)); 
		
		double lat_ratio_end=(Nodes.get(Nodes.size()-1).getLatitude()-Intersections.get(Intersections.size()-1).getLatitude())/(Nodes.get(Nodes.size()-1).getLatitude()-Nodes.get(Nodes.size()-2).getLatitude());	
		double lon_ratio_end=(Nodes.get(Nodes.size()-1).getLongitude()-Intersections.get(Intersections.size()-1).getLongitude())/(Nodes.get(Nodes.size()-1).getLongitude()-Nodes.get(Nodes.size()-2).getLongitude());
		double avg_ratio_end= (lat_ratio_end+lon_ratio_end)/2;
		if(avg_ratio_end<0) {//HIER STECKT DER FEHLER
			avg_ratio_end=avg_ratio_end*(-1);
		}
		durations[durations.length-1]=durations[durations.length-1]*avg_ratio_end;
		
		int pos2 = All_Cities.destinationCities.indexOf(best.getCity(1)); 
		All_Cities.getCity(pos2).setCoordinates(Intersections.get(Intersections.size()-1).getLongitude(), Intersections.get(Intersections.size()-1).getLatitude());
		Nodes.set(Nodes.size()-1, Intersections.get(Intersections.size()-1));

	
		//Inform simulator
		RouteServiceEvent event= new RouteServiceEvent(this, Nodes,Intersections, durations,best,lastEventTime);
		fireEvent(event);
		
		//Adapt all tours in actual population and fittest tour "best"
	
		for ( int t =0; t<pop.populationSize();t++) {
			if(Intersections.get(1).getType()=="Intersection"){
				pop.getTour(t).addatPosition(1,Intersections.get(1));
			}
			else {
				int delete=pop.getTour(t).positionofCity(best.getCity(2));
				pop.getTour(t).deleteCity(delete);
				pop.getTour(t).addatPosition(1, best.getCity(2));
			}
		}

		/*if(Intersections.get(1).getType()=="Intersection"){
			All_Cities.addCity(Intersections.get(1));
		}*/

		/*best.deleteCity(0);
		if(Intersections.get(1).getType()=="Intersection") {
			best.addatPosition(1, Intersections.get(1));
		}*/
		//If the next location will be an intersection, add to All_Cities
		if(Intersections.get(1).getType()=="Intersection"){
			All_Cities.addCity(Intersections.get(1));
			Distanzmatrix.updateAllMatrix();
		}
		
		//If there is no intersection, calculate duration to next city
		if(Intersections.get(1).getType()=="City") {  
			toDriveto("City",0,Nodes.size()-1,1);
		}
		//If there is an intersection, calculate duration to next intersection
		else {
			int PosinNode=0;
			for(int a=0; a<Nodes.size();a++) {
				if(Nodes.get(a).getId()==Intersections.get(1).getId()) {
					break;
				}
				PosinNode++;				
			}
			toDriveto("Intersection",0,PosinNode,1);
		}
		//Save actual position and best tour for comparison reasons at the next event
		lastLocation=All_Cities.getCity(0);
		lastbest=new Tour(best);
		
		//Start dynamic algorithm and simulation
		
		/*for(int xx=0; xx<Distanzmatrix.matrix.length;xx++) {
			for(int yy=0; yy<Distanzmatrix.matrix.length;yy++) {
				System.out.print(Distanzmatrix.matrix[xx][yy]+ " ");
			}
			System.out.println();
		}*/
		Run.runs=true;
		System.out.println("Best at Start: "+best.getDuration()+" "+best);
		System.out.println("tDtN: "+toDrivetoNode+" tDtI: "+ toDrivetoIntersection+" tDtC: "+toDrivetoCity+" IntersectionValue "+best.IntersectionValue+" SymmValue: "+best.allsymmValue);
		System.out.println();
		
		for(int i=0;i<pop.populationSize();i++) {
			System.out.println(pop.getTour(i));
		}
    }
   
  
    //Event-handling method for arriving at a "City"
    // Do route request, adapt data, inform simulator, adapt tours and All_Cities
    //do matrix update, do toDriveto calculation
    //Event-handling and event-related methods
    @Override
	public void atCity(AtEvent e){
   	System.out.println("Arrived at City");
    	System.out.println(e);
    	System.out.println("Time atCity:" +new TimeElement(e.getEventTime()));
	    //reset toDriveto values
    	toDrivetoNode=0;
		toDrivetoIntersection=0;
		toDrivetoCity=0;
		EventCounter++;
		Route route= new Route();
		lastEventTime= new TimeElement(e.getEventTime());	
		System.out.println();
		
		Tour lastRequest=null;
		
		//Turn of dynamic algorithm when we are back at our starting city
		if(e.status=="Erste Stadt wieder erreicht") {
			Run.runs=false;
		}
		if(e.status=="Letzte Stadt erreicht") {
			lastCityvisited=true;
			System.out.println("Letzte Stadt erreicht");
		}
		if(Run.runs==true) {
		//Do the route request and save data
		
			//if we arrive at the last "City"
			if(lastCityvisited) {
				ArrayList <City> abc= new ArrayList<City>();
				//adding best(0) just for referencing purpose in Wayfromto
				abc.add(best.getCity(0));			
				abc.add(best.getCity(1));
				abc.add(Distanzmatrix.startCity);
				lastRequest= new Tour (abc);
				
				try {
					route.WayFromTo(lastRequest);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				try {
					route.WayFromTo(best);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			durations=route.Duration;
			Nodes=route.Nodes_as_City;
			Intersections=route.intersections;
			
			//Adaped first and last duration value with duration approximation from first intersection to second node
			//and penultimate node to last intersection 
			//replace first and last node with first and last city object in "Intersection"
			//Set coordinates of destination city of the route by using coordinates of last "intersection" 
			double lat_ratio_start=(Nodes.get(1).getLatitude()-Intersections.get(0).getLatitude())/(Nodes.get(1).getLatitude()-Nodes.get(0).getLatitude());	
			double lon_ratio_start=(Nodes.get(1).getLongitude()-Intersections.get(0).getLongitude())/(Nodes.get(1).getLongitude()-Nodes.get(0).getLongitude());
			double avg_ratio_start= (lat_ratio_start+lon_ratio_start)/2;  
			durations[0]=durations[0]*avg_ratio_start;
			
			double lat_ratio_end=(Nodes.get(Nodes.size()-1).getLatitude()-Intersections.get(Intersections.size()-1).getLatitude())/(Nodes.get(Nodes.size()-1).getLatitude()-Nodes.get(Nodes.size()-2).getLatitude());	
			double lon_ratio_end=(Nodes.get(Nodes.size()-1).getLongitude()-Intersections.get(Intersections.size()-1).getLongitude())/(Nodes.get(Nodes.size()-1).getLongitude()-Nodes.get(Nodes.size()-2).getLongitude());
			double avg_ratio_end= (lat_ratio_end+lon_ratio_end)/2;
			durations[durations.length-1]=durations[durations.length-1]*avg_ratio_end;			
			if(All_Cities.checkForCities()>1){
				int pos = All_Cities.destinationCities.indexOf(best.getCity(2));
				All_Cities.getCity(pos).setCoordinates(Intersections.get(Intersections.size()-1).getLongitude(), Intersections.get(Intersections.size()-1).getLatitude());		
			}
			Nodes.set(0, Intersections.get(0)); 
			Nodes.set(Nodes.size()-1, Intersections.get(Intersections.size()-1));
			
			//Inform simulator
			RouteServiceEvent event= new RouteServiceEvent(this, Nodes,Intersections, durations,best,lastEventTime);
			fireEvent(event);		
			//Adapt all tours in actual population and fittest tour "best"
			
			for ( int t =0; t<pop.populationSize();t++) {
				//Delete last location
				pop.getTour(t).deleteCity(0);
				//add city object "intersection" if available
				if(Intersections.get(1).getType()=="Intersection"){
					pop.getTour(t).addatPosition(1,Intersections.get(1));
				}
				//if no "intersection" available set next city in best as next destination in all tours
				else {
					if(All_Cities.checkForCities()>1&&lastCityvisited==false) {
					int delete=pop.getTour(t).positionofCity(best.getCity(2));
					pop.getTour(t).deleteCity(delete);
					pop.getTour(t).addatPosition(1, best.getCity(2));
					}
				}
			}
			
		
		
			//delete last location in All_Cities
			All_Cities.deleteCity(lastLocation);
			//Insert next "intersection" if available and do a matrix update for next intersection
			if(Intersections.get(1).getType()=="Intersection"){
				All_Cities.addCity(Intersections.get(1));
				if(e.status==null||All_Cities.checkForCities()>2) {
					try {
						Distanzmatrix.updateAllMatrix();
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}		
		
			// toDriveto values calculation
			
			//Case 3,4 and extra case   ??? NOCHMAL �BERPR�FEN
			if(OP_Stop==true||Intersections.get(1).getType()=="City") { 
				
					toDriveto("City",0,Nodes.size()-1,1);
				
			}	
			//Case 1,2
			else { 
				int PosinNode=0;
				for(int a=0; a<Nodes.size();a++) {
					if(Nodes.get(a).getId()==Intersections.get(1).getId()) {
						break;
					}
					PosinNode++;
				}		
					toDriveto("Intersection",0,PosinNode,1);	
			}
//			System.out.println("Best: "+best.getDuration()+" "+best);
		}
		else {
			for ( int t =0; t<pop.populationSize();t++) {
				//Delete last location
				pop.getTour(t).deleteCity(0);
				pop.getTour(t).addatPosition(0, Distanzmatrix.startCity);
				}
			All_Cities.deleteCity(lastLocation);
			All_Cities.addCity(Distanzmatrix.startCity);
			}
			
	
		//Save actual position and best tour for comparison reasons at the next event
		lastLocation=e.location;  
		lastbest=new Tour(best);
		System.out.println("Best at City: "+best.getDuration()+" "+best);
		System.out.println("tDtN: "+toDrivetoNode+" tDtI: "+ toDrivetoIntersection+" tDtC: "+toDrivetoCity+" IntersectionValue "+best.IntersectionValue+" SymmValue: "+best.allsymmValue);
		System.out.println();
    }
    //Event-handling method for arriving at a "City"
    //Distinguish change or no change in solution since last intersection/city
    //If there is a change: Do route request, adapt data, inform simulator,
    //adapt tours and All_Cities,do matrix request, do toDriveto calculation
    //If there is no change : /adapt tours and All_Cities,do matrix request, do toDriveto calculation
	@Override
	public void atIntersection(AtEvent e) { 
	System.out.println("Arrived at Intersection");
	System.out.println(e);
   	System.out.println("Time at Intersection:" +new TimeElement(e.getEventTime()));
		EventCounter++;
		toDrivetoIntersection=0;
		toDrivetoCity=0;
		toDrivetoNode=0;
		lastEventTime= new TimeElement(e.getEventTime());	
		
		System.out.println();
		
		
		//if there is a change in solution since the last event location
		if(All_Cities.checkForCities()>1 && !(lastbest.getCity(2).getId().equals(best.getCity(2).getId()))) {  //EQUAL
			System.out.println("ROUTENWECHSEL");
			Route route= new Route();
			try {
				route.WayFromTo(best);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			durations=route.Duration;
			Nodes=route.Nodes_as_City;
			Intersections=route.intersections;
			
			//Adaped first and last duration value with duration approximation from first intersection to second node
			//and penultimate node to last intersection 
			//replace first and last node with first and last city object in "Intersection"
			//Set coordinates of destination city of the route by using coordinates of last "intersection" 
			double lat_ratio_start=(Nodes.get(1).getLatitude()-Intersections.get(0).getLatitude())/(Nodes.get(1).getLatitude()-Nodes.get(0).getLatitude());	
			double lon_ratio_start=(Nodes.get(1).getLongitude()-Intersections.get(0).getLongitude())/(Nodes.get(1).getLongitude()-Nodes.get(0).getLongitude());
			double avg_ratio_start= (lat_ratio_start+lon_ratio_start)/2;  
			durations[0]=durations[0]*avg_ratio_start;
			
			double lat_ratio_end=(Nodes.get(Nodes.size()-1).getLatitude()-Intersections.get(Intersections.size()-1).getLatitude())/(Nodes.get(Nodes.size()-1).getLatitude()-Nodes.get(Nodes.size()-2).getLatitude());	
			double lon_ratio_end=(Nodes.get(Nodes.size()-1).getLongitude()-Intersections.get(Intersections.size()-1).getLongitude())/(Nodes.get(Nodes.size()-1).getLongitude()-Nodes.get(Nodes.size()-2).getLongitude());
			double avg_ratio_end= (lat_ratio_end+lon_ratio_end)/2;
			durations[durations.length-1]=durations[durations.length-1]*avg_ratio_end;
			
			int pos = All_Cities.destinationCities.indexOf(best.getCity(2));
			All_Cities.getCity(pos).setCoordinates(Intersections.get(Intersections.size()-1).getLongitude(), Intersections.get(Intersections.size()-1).getLatitude());
			
			Nodes.set(0, Intersections.get(0)); 
			Nodes.set(Nodes.size()-1, Intersections.get(Intersections.size()-1));
			
			//Inform simulator
			RouteServiceEvent event= new RouteServiceEvent(this, Nodes,Intersections, durations,best,lastEventTime);
			fireEvent(event);
			
			//Adapt all tours in actual population and fittest tour "best"
			for ( int t =0; t<pop.populationSize();t++) {
				//Delete last location
				pop.getTour(t).deleteCity(0);
				if(Intersections.get(1).getType()=="Intersection"){
					//add city object "intersection" if available
					pop.getTour(t).addatPosition(1,Intersections.get(1));
				}
				//add city object "intersection" if available
				else {
					int delete=pop.getTour(t).positionofCity(best.getCity(2));
					pop.getTour(t).deleteCity(delete);
					pop.getTour(t).addatPosition(1, best.getCity(2));
				}
			}
			//best.deleteCity(0);
			/*if(Intersections.get(1).getType()=="Intersection") {		//Hier liegt der Hund begraben!!!
				best.addatPosition(1, Intersections.get(1));
			}*/
			//System.out.println(pop.getFittest());
			//Do the same with Tour "best
		
			System.out.println();
			if(lastLocation.getType()=="GPS"||lastLocation.getType()=="Intersection") {
				All_Cities.deleteCity(lastLocation);
			}
			else {
				for(int a=0;a<All_Cities.numberOfCities();a++) {
					if(All_Cities.getCity(a).getId()==lastLocation.getId()) {
						All_Cities.deleteCity(All_Cities.getCity(a));
						break;
					}
				}
			}
			//Insert next "intersection" if available and do a matrix update for next intersection
			if(best.getCity(1).getType()=="Intersection"){
				All_Cities.addCity(Intersections.get(1));
				try {
					Distanzmatrix.updateAllMatrix();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		
			// toDriveto values calculation
			//case 2,3,4
			if(Intersections.get(1).getType()=="City") { 
				
					toDriveto("City",0,Nodes.size()-1,1); 
				
			}
			//case: 1
			else {
				int PosinNode=0;
				for(int a=0; a<Nodes.size();a++) {
					if(Nodes.get(a).getId()==Intersections.get(1).getId()) {
						break;
					}
					PosinNode++;
				}
				
					toDriveto("Intersection",0,PosinNode,1);
				
			}
			
		
			
		
			
			
		}
		//No channge in best solution since last intersection/city
		else{			
			//Stopp evolve Population because there is no other solution possible anymore
			if(e.status=="Operatoren-Stop") {
				OP_Stop=true;
				System.out.println("OP Stop//");
			}
			//adapt all tours of population
			int PosinInter=-1;
			for(int srch=0; srch<Intersections.size();srch++) {
				if(e.location.id==Intersections.get(srch).getId()) {
					PosinInter=srch;
					break;
				}
			}
		
			for ( int t =0; t<pop.populationSize();t++) {
				//delete last location
				pop.getTour(t).deleteCity(0);
				
				if(Intersections.get(PosinInter+1).getType()=="Intersection"){
					//add city object "intersection" if available
					pop.getTour(t).addatPosition(1,Intersections.get(PosinInter+1));
					
				}
				//add city object "intersection" if available
				else {
					if(pop.getTour(t)!=best&&lastCityvisited==false) {
					int delete=pop.getTour(t).positionofCity(best.getCity(1));
					pop.getTour(t).deleteCity(delete);
					pop.getTour(t).addatPosition(1, best.getCity(1));
					
					}
				}
			}
		
			//do the same with best tour
			/*best.deleteCity(0);
			if(Intersections.get(PosinInter+1).getType()=="Intersection") {
				best.addatPosition(1, Intersections.get(PosinInter+1));
			}*/
			//adapt All_Cities
		
			
			if(lastLocation.getType()=="GPS"||lastLocation.getType()=="Intersection") {
				All_Cities.deleteCity(lastLocation);
			}
			else {
				for(int a=0;a<All_Cities.numberOfCities();a++) {
					if(All_Cities.getCity(a).getId()==lastLocation.getId()) {
						All_Cities.deleteCity(All_Cities.getCity(a));
						break;
					}
				}
			}
	
			
			//Insert next "intersection" if available and do a matrix update for next intersection
			if(Intersections.get(PosinInter+1).getType()=="Intersection"){
				All_Cities.addCity(Intersections.get(PosinInter+1));
				if(All_Cities.checkForCities()>=2 &&e.location!=Intersections.get(Intersections.size()-2)) {		//Nur Update wenn All_cities gr��er gleich 2 und nicht vorletzter Node (==echter letzter Node)
					try {
						Distanzmatrix.updateAllMatrix();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}	
		
		
			//toDriveto calculation
			//case 2,3,4
			if(All_Cities.checkForCities()<2||Intersections.get(PosinInter+1).getType()=="City") {  //FALL 2 -> Wenn letzte echte Intersection ereicht ist
				int PosinNode=0;
				for(int a=0; a<Nodes.size();a++) {
					if(Nodes.get(a).getId()==e.location.getId()) {
						break;
					}
					PosinNode++;
				}
					toDriveto("City",PosinNode,Nodes.size()-1,1);
				
			}
			//case: 1
			else {
				int PosinNode1=0;
				for(int a=0; a<Nodes.size();a++) {
					if(Nodes.get(a).getId()==e.location.getId()) {
						break;
					}
					PosinNode1++;
				}
				int PosinNode2=0;
				for(int n=0; n<Nodes.size()-1;n++) {
					if(Nodes.get(n).getId()==Intersections.get(PosinInter+1).getId()) {	
						break;
					}
					PosinNode2++;
				}
				toDriveto("Intersection",PosinNode1,PosinNode2,1);
					
				}
			}		
		
		lastbest=new Tour (best);//AUFPASSEN
		lastLocation=e.location;
		System.out.println("Best at Intersection: "+best.getDuration()+" "+best);
		System.out.println("tDtN: "+toDrivetoNode+" tDtI: "+ toDrivetoIntersection+" tDtC: "+toDrivetoCity+" IntersectionValue "+best.IntersectionValue+" SymmValue: "+best.allsymmValue);
		System.out.println();
	}
	//Event-handling method for GPS events
	//Localizes position and allocates the 2 nodes we are in between
	//adapts tours in population and All_Cities
	//calculates duration to next node and then to next intersection/city
	@Override
	public void GPS_Signal(AtEvent e)  {
	System.out.println("Arrived at GPS");
	System.out.println(e);
		System.out.println("Time at GPS:" +new TimeElement(e.getEventTime()));
		int GPSinNode=0;
		EventCounter++;
		toDrivetoIntersection=0;
		toDrivetoCity=0;
		toDrivetoNode=0;
		lastEventTime= new TimeElement(e.getEventTime());
		System.out.println();
	
		
		//Allocate the nodes we are in between through spanning a rectangle with coordinates and 
		//analyze if we are located in this rectangle
		
		for( int i=0; i<Nodes.size()-1;i++) {	
			double maxLat=0;
			double minLat=0;
			double maxLon=0;
			double minLon=0;
		
			 maxLat= Math.max(Nodes.get(i).getLatitude(),Nodes.get(i+1).getLatitude());
			 minLat= Math.min(Nodes.get(i).getLatitude(),Nodes.get(i+1).getLatitude());
			 maxLon= Math.max(Nodes.get(i).getLongitude(),Nodes.get(i+1).getLongitude());
			 minLon= Math.min(Nodes.get(i).getLongitude(),Nodes.get(i+1).getLongitude());
			
			if(e.getLatitude()<=maxLat&&e.getLatitude()>=minLat&&e.getLongitude()<=maxLon&&e.getLongitude()>=minLon) {
				break;
			}
			GPSinNode++;  
		}

		//Adapt tours, delete last location and add actual GPS position
		
		for ( int t =0; t<pop.populationSize();t++) {
			pop.getTour(t).deleteCity(0);
			pop.getTour(t).addatPosition(0,e.location);
		}
	
		//Delete last location and add actual GPS position in All_Cities
		if(lastLocation.getType()=="GPS"||lastLocation.getType()=="Intersection") {
			All_Cities.deleteCity(lastLocation);
		}
		else {
			for(int a=0;a<All_Cities.numberOfCities();a++) {
				if(All_Cities.getCity(a).getId()==lastLocation.getId()) {
						All_Cities.deleteCity(All_Cities.getCity(a));
						break;
				}
			}
		} 
		All_Cities.addCity(e.location);		
	
		 //Calculate duration to next Node (position in ArrayList=GPSinNode+1)
		double latratio= (Nodes.get(GPSinNode+1).getLatitude()-e.getLatitude())/(Nodes.get(GPSinNode+1).getLatitude()-Nodes.get(GPSinNode).getLatitude());
		double lonratio=(Nodes.get(GPSinNode+1).getLongitude()-e.getLongitude())/(Nodes.get(GPSinNode+1).getLongitude()-Nodes.get(GPSinNode).getLongitude());
		double ratio= (latratio+lonratio)/2;
		toDriveto("Node",GPSinNode,0,ratio);	
		System.out.println();
		//toDriveto calculation
		//case 1
		GPSinNode++;
		if(OP_Stop==false&&All_Cities.checkForIntersection()>0)	{
			int nextIntersection=0;
			for(int l=1; l<Intersections.size();l++) {
				for(int k=GPSinNode;k<Nodes.size();k++) {
					if(Nodes.get(k).getId()==Intersections.get(l).getId()) { 
						nextIntersection=k;  
						break;	
					}			
				}
				if(nextIntersection!=0) {
					break;
				}	
			}
			toDrivetoIntersection+=toDrivetoNode;
			toDriveto("Intersection",GPSinNode,nextIntersection,1);
			
		}
		// case 2,3,4	
		else {	
			toDrivetoCity+=toDrivetoNode;
			toDriveto("City",GPSinNode+1,durations.length,1);					
		}
		System.out.println();
		lastLocation=e.location;
		System.out.println("Best at GPS: "+best.getDuration()+" "+best);
		System.out.println("tDtN: "+toDrivetoNode+" tDtI: "+ toDrivetoIntersection+" tDtC: "+toDrivetoCity+" IntersectionValue "+best.IntersectionValue+" SymmValue: "+best.allsymmValue);
		System.out.println();
	}
    
	//Method to calculate the duration from the acutal position to the next destination
	//next destination could be an intersection or city
	//Considers daytime through hour depending factor multiplication
	//toDriveto is always involved to allocate total distance with Tour.getDuration()
	public static void toDriveto(String Location,int start, int end, double ratio) { //Wenn kein Node, ratio das �bergeben wird ist irrelevant
    	if(Location=="City") {
    		System.out.println();
    		int hour= lastEventTime.getHour();
    	;
    		long sumDurTF=lastEventTime.startInMilli+(long)toDrivetoNode*1000;
	    	long nexthour=lastEventTime.timeAtNextHour;
	    	int h_next;

    		if(sumDurTF>nexthour) {
    			hour++;
    			nexthour+=3600000;	
    			
    		}
    		if(hour==24) {
				hour=0;
			}
			if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
			}
	    	for(int a=start; a<end;a++) {
	    		if(sumDurTF+durations[a]*1000*Maths.getFaktor(hour)>nexthour) {
					long houroverlaps=(long)(sumDurTF+durations[a]*Maths.getFaktor(hour)*1000-nexthour);		;									//calculate the time from sum to next hour
					double houroverlapsratio= Maths.round(houroverlaps/(durations[a]*Maths.getFaktor(hour)*1000),5);									// Calculate ratio of driven way in this section
					toDrivetoCity+=(1-houroverlapsratio)*durations[a]*Maths.getFaktor(hour)+(houroverlapsratio)*durations[a]*Maths.getFaktor(h_next);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					sumDurTF+=(1-houroverlapsratio)*durations[a]*1000*Maths.getFaktor(hour)+(houroverlapsratio)*durations[a]*1000*Maths.getFaktor(h_next);
					nexthour+=3600000;	
				
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					if(hour==23) {
						h_next=0;
					}
					else {
						h_next=hour+1;
					}
				
				}
	    		else {
	    			
	    			toDrivetoCity+=durations[a]*Maths.getFaktor(hour);
	    			sumDurTF+=durations[a]*Maths.getFaktor(hour)*1000;
	    			
	    		}
	    		
    		//System.out.println("tDtC: "+toDrivetoCity);
	    	}
	    	toDrivetoCity= Maths.round(toDrivetoCity, 3);
    	}
    	else if(Location=="Intersection") {
//    		System.out.println();
	    	int hour= lastEventTime.getHour();
	    	long sumDurTF=lastEventTime.startInMilli+(long)toDrivetoNode*1000;
	    	long nexthour=lastEventTime.timeAtNextHour;
//	    	System.out.println("Intersection: sumDurTF: "+sumDurTF);
	    
	    	int h_next;
    		if(sumDurTF>nexthour) {
    			hour++;
    			nexthour+=3600000;	
    		}
    		if(hour==24) {
				hour=0;
			}
    		if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
			}
    		
	    	for(int b=start; b<end;b++) {

    	    		if(sumDurTF+durations[b]*1000*Maths.getFaktor(hour)>nexthour) {
    					long houroverlaps=(long)(sumDurTF+durations[b]*Maths.getFaktor(hour)*1000-nexthour);											//calculate the time from sum to next hour
    					double houroverlapsratio= Maths.round(houroverlaps/(durations[b]*Maths.getFaktor(hour)*1000),5);									// Calculate ratio of driven way in this section
    					toDrivetoIntersection+=(1-houroverlapsratio)*durations[b]*Maths.getFaktor(hour)+(houroverlapsratio)*durations[b]*Maths.getFaktor(h_next);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
    					sumDurTF+=(1-houroverlapsratio)*durations[b]*1000*Maths.getFaktor(hour)+(houroverlapsratio)*durations[b]*1000*Maths.getFaktor(h_next);
    					
    					nexthour+=3600000;	
    					hour+=1;
    					
    					if(hour==24) {
    						hour=0;
    					}
    					if(hour==23) {
    						h_next=0;
    					}
    					else {
    						h_next=hour+1;
    					}
    				
    				}
    	    		else {
    	    			
    	    			toDrivetoIntersection+=durations[b]*Maths.getFaktor(hour);
    	    			sumDurTF+=durations[b]*Maths.getFaktor(hour)*1000;
//    	    			;
    	    			
    	    		}
	    	
	    	
    			}
	    	toDrivetoIntersection=Maths.round(toDrivetoIntersection, 3);
    	}
     	else if(Location=="Node") {
     		int hour= lastEventTime.getHour();
//     		
	    	long nexthour=lastEventTime.timeAtNextHour;
	    	long sumDurTF=lastEventTime.startInMilli;
//	    	
	    	
	    	int h_next;
			if(hour==23) {
				h_next=0;
			}
			else {
				h_next=hour+1;
				
			}	    	
//			
			if(sumDurTF+durations[start]*ratio*1000*Maths.getFaktor(hour)>nexthour) {
				long houroverlaps=(long)(sumDurTF+durations[start]*ratio*Maths.getFaktor(hour)*1000-nexthour);		;									//calculate the time from sum to next hour
				double houroverlapsratio= Maths.round(houroverlaps/(durations[start]*ratio*Maths.getFaktor(hour)*1000),5);									// Calculate ratio of driven way in this section
				toDrivetoNode+=(1-houroverlapsratio)*durations[start]*ratio*Maths.getFaktor(hour)+(houroverlapsratio)*durations[start]*Maths.getFaktor(h_next);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
			
				
    			
			}	
    		else {
    			toDrivetoNode+=durations[start]*ratio*Maths.getFaktor(hour);
    			
    			
    		}
			toDrivetoNode=Maths.round(toDrivetoNode, 3);
	    	
     	}
    	
    }
	//adds listener to listener list
	public void addRouteServiceListener(RouteServiceListener toAdd) {
		listenerList.add(toAdd);
	}
	//activates listener method in simulator class
	public void fireEvent(RouteServiceEvent e)
	{
		listenerList.get(0).EAdidRequest(e);
	}
	
}


