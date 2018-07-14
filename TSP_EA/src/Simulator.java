import java.util.ArrayList;
//Simulator that for event-based and time-depending simulation
//Using Gamma-function for simulating new values
//checks after every iteration of the EA if it has to fire an event
//Implements RouteServiceListener with which it receives simulation command and API data
//Creates three different types of events, based on API data -> "AtCity", "AtIntersection" & "GPS"
public class Simulator implements RouteServiceListener {
	
//VARIABLES:
	//List for upcoming Events
	private ArrayList<AtEvent> upcomingEvents = new ArrayList<AtEvent>();
	//List for past Events
	private ArrayList<AtEvent> pastEvents = new ArrayList<AtEvent>();
	//Simulation parameters and data
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;
	Tour best;
	double[] duration;
	double[] TF_Gamma_duration;
	int GPS_frequency=10;
	double k;
	double theta;
	double shiftDistance;
	//ListenerList
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();

//CONSTRUCTOR
	public Simulator() {
		this.k=EA.c;
		this.theta=EA.theta;
		this.shiftDistance=EA.shiftDistance;
		
	}
	
//METHODS:
	public void addListener(myListener toAdd) {
		listenerList.add(toAdd);
	}
	
	//Methods that compares actual time with event time of all events
	//Fires event in case of time overlaps and moves specific event in the event lists
	public void checkForEvents(){
		AtEvent currentEvent = null;
		long jetzt= System.currentTimeMillis();
		
		for(int i=0; i<upcomingEvents.size();i++){
			if(jetzt>( upcomingEvents.get(i)).getEventTime()){
				pastEvents.add(upcomingEvents.get(i));
				currentEvent=upcomingEvents.get(i);
				break;

			}
			else{}
		}
		upcomingEvents.remove(currentEvent);
		try {
			fireAtEvent(currentEvent);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
	}

	//Fires event and activates the correct event handling mehtods in EA
	public void fireAtEvent(AtEvent e) throws Exception{
		
		if(e.getEventType()=="City"){
			listenerList.get(0).atCity(e);
		}
		
		if(e.getEventType()=="Intersection"){
			listenerList.get(0).atIntersection(e);
			
		}	
		if(e.getEventType()=="GPS"){
			listenerList.get(0).GPS_Signal(e);
		}
	}
	
	//Event handling methods for RouteServicEvent
	//Starts simulation of AtEvents
	public void EAdidRequest(RouteServiceEvent e) {
		Nodes=e.Nodes;
		Intersection= e.Intersection;
		duration=e.durations;
		best=e.best;
		upcomingEvents = new ArrayList<AtEvent>();
		pastEvents = new ArrayList<AtEvent>();
		createEvents();
		
	}
	
	//Creates all atEvents of the current route, "GPS", "AtIntersection" & "atCity
	public void createEvents() {
		double[] GammaDuration= new double[duration.length];												//Array for Gamma influenced values
																	
		for(int i=0; i<duration.length;i++) {													//Loop through duration values and use gamma function
			GammaDuration[i]=Maths.goGamma(duration[i], k, theta, shiftDistance);	
		}
		
		TimeElement now= new TimeElement();
		double durationSumZFEA=0;																//Sum of gamma and time factor influenced values looped
		TF_Gamma_duration= new double[GammaDuration.length];										//Array for final values for Simulation, gamma and time factor influenced
		int hour= now.getHour();																//actual hour
		double ttnh=now.getTimeToNextHour();												// Duration to next hour in seconds
			for(int j=0; j<GammaDuration.length;j++) {											//Loop through Gamma 
				
				if(durationSumZFEA+GammaDuration[j]*Maths.getFaktor(hour)>ttnh) {			//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durationSumZFEA		;									//calculate the time from sum to next hour
					double ratio= tohour/GammaDuration[j]*Maths.getFaktor(hour);				// Calculate ratio of driven way in this section
					TF_Gamma_duration[j]=ratio*GammaDuration[j]*Maths.getFaktor(hour)+(1-ratio)*GammaDuration[j]*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
					durationSumZFEA+=TF_Gamma_duration[j];											//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					System.out.println("WECHSEL");													//Update time factor
					
				}
				else {																			// If actual step is within the same hour
					TF_Gamma_duration[j]=GammaDuration[j]*Maths.getFaktor(hour);				//multiply value with time factor
					durationSumZFEA+=TF_Gamma_duration[j];		
					System.out.println("Kein WECHSEL"+ TF_Gamma_duration[j]);	//Update Sum
				}
			}
			
	
		
		int numberofGPSEvents= (int)durationSumZFEA/GPS_frequency;
		double eventTimeSum=0;
		
		for(int events=0; events<numberofGPSEvents;events++) {
			eventTimeSum+=5;
			double diffrence=0;
			double sum_d=0;
			double ratio=0;
			int positionInDurArray=0;
			for(int findNode=0;findNode<TF_Gamma_duration.length;findNode++) {
				sum_d+=TF_Gamma_duration[findNode]; 
				if(sum_d>eventTimeSum) {
					positionInDurArray=findNode;
					diffrence=sum_d-eventTimeSum;
					ratio= 1-(diffrence/TF_Gamma_duration[findNode]);
					break;
				}
			}
				
			double lat1= Nodes.get(positionInDurArray).getLatitude();
			double lon1=Nodes.get(positionInDurArray).getLongitude();
			double lat2=Nodes.get(positionInDurArray+1).getLatitude();
			double lon2=Nodes.get(positionInDurArray+1).getLongitude();
			
			double newlat= (lat2-lat1)*ratio+lat1;
			double newlon=(lon2-lon1)*ratio+lon1;
			City GPS = new City(Integer.toString(events),"GPS",newlon,newlat);
			AtEvent ev= new AtEvent(this,GPS,now.startInMilli+(long)(eventTimeSum*1000));
			upcomingEvents.add(ev);
		}
		
		for(int inters=1; inters<Intersection.size();inters++) { 
			double sumIntD=0;
			if(inters==Intersection.size()-1) {
				for(int dur=0;dur<TF_Gamma_duration.length;dur++) {
					sumIntD+=TF_Gamma_duration[dur];
				}											//Letztes Event, Intersection last = City "City"
				AtEvent ev= new AtEvent(this,Intersection.get(inters) ,(long)(now.startInMilli+(sumIntD*1000)));
				if(Intersection.get(inters).getLatitude()==Distanzmatrix.startCity.getLatitude()&&Intersection.get(inters).getLongitude()==Distanzmatrix.startCity.getLongitude()) {
					ev.status="Erste Stadt wieder erreicht";
				}
			
				
				else if(All_Cities.checkForCities()==1) {
					ev.status="Letze Stadt erreicht";
				}
				upcomingEvents.add(ev);
				break;
			}
			
			else {
				for(int node=0;node<Nodes.size();node++) {
					sumIntD+=TF_Gamma_duration[node];   
					if(Intersection.get(inters).getId()==Nodes.get(node).getId()) { //Hier hat Intersection noch die ID der korresponiderenden Nodes
							AtEvent ev= new AtEvent(this,Intersection.get(inters),(long)(now.startInMilli+(sumIntD*1000)));
							if(All_Cities.checkForCities()==2 &&Intersection.get(inters+1).getType()=="City") {
								ev.status="Operatoren-Stop";
							}
							upcomingEvents.add(ev);
							break;	
					}	
				}
			}	
		}
	}

}
	