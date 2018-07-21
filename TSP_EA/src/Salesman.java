import java.util.ArrayList;

public class Salesman implements RouteServiceListener {
	private ArrayList<AtEvent> upcomingEvents = new ArrayList<AtEvent>();
	private ArrayList<AtEvent> pastEvents = new ArrayList<AtEvent>();
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;
	Tour best;
	double[] duration;
	double[] ZF_EA_duration;
	int GPS_frequency=10;
	double k;
	double theta;
	double shiftDistance;
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();

	public Salesman() {
		this.k=EA.c;
		this.theta=EA.theta;
		this.shiftDistance=EA.shiftDistance;
		
	}
	
	public void addListener(myListener toAdd) {
		listenerList.add(toAdd);
	}
	
	public void checkForEvents()
	{	AtEvent currentEvent = null;
		long jetzt= System.currentTimeMillis();
		
		for(int i=0; i<upcomingEvents.size();i++)
		{
			if(jetzt>( upcomingEvents.get(i)).getEventTime())  
			{
				pastEvents.add(upcomingEvents.get(i));
				currentEvent=upcomingEvents.get(i);
				break;

			}
			else
			{}
		}
		upcomingEvents.remove(currentEvent);
		try {
			fireAtEvent(currentEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	/*	if(getFahrtzeit()>((AtEvent) upcomingEvents.get(0)).getEventTime())
		{	//fire
			for(myListener lis: listenerList)
			{	AtEvent ae= (AtEvent)upcomingEvents.get(0);
				lis.arrivedAtCity(ae);
				lis.arrivedAtWP(ae);
				// übergib event an listener der daruaf reagiert sobald Fahrtzeit größer eventzeit
			}
		} */
	}
	
	public void fireAtEvent(AtEvent e) throws Exception{
		
		if(e.getEventType()=="AtCity"){
			listenerList.get(0).atCity(e);
		}
		
		if(e.getEventType()=="AtIntersection"){
			listenerList.get(0).atIntersection(e);
			
		}	
		if(e.getEventType()=="GPS"){
			listenerList.get(0).GPS_Signal(e);
		}
	}
	
	public void EAdidRequest(RouteServiceEvent e) {
		Nodes=e.Nodes;
		Intersection= e.Intersection;
		duration=e.durations;
		best=e.best;
		createEvents();
		
	}
	
	public void createEvents() {
		double[] gammaDuration= new double[duration.length];												//Array for Gamma influenced values
																	
		for(int i=0; i<duration.length;i++) {													//Loop through duration values and use gamma function
			gammaDuration[i]=Maths.goGamma(duration[i], k, theta, shiftDistance);	
		}
		
		TimeElement now= new TimeElement();
		double durationSumZFEA=0;																//Sum of gamma and time factor influenced values looped
		ZF_EA_duration= new double[gammaDuration.length];										//Array for final values for Simulation, gamma and time factor influenced
		int hour= now.getHour();																//actual hour
		double ttnh=now.getTimeToNextHour();												// Duration to next hour in seconds
			for(int j=0; j<gammaDuration.length;j++) {											//Loop through Gamma 
				
				if(durationSumZFEA+gammaDuration[j]*Maths.getFaktor(hour)>ttnh) {			//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durationSumZFEA		;									//calculate the time from sum to next hour
					double ratio= tohour/gammaDuration[j]*Maths.getFaktor(hour);				// Calculate ratio of driven way in this section
					ZF_EA_duration[j]=ratio*gammaDuration[j]*Maths.getFaktor(hour)+(1-ratio)*gammaDuration[j]*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
					durationSumZFEA+=ZF_EA_duration[j];											//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					System.out.println("WECHSEL");													//Update time factor
					
				}
				else {																			// If actual step is within the same hour
					ZF_EA_duration[j]=gammaDuration[j]*Maths.getFaktor(hour);				//multiply value with time factor
					durationSumZFEA+=ZF_EA_duration[j];		
					System.out.println("Kein WECHSEL"+ ZF_EA_duration[j]);	//Update Sum
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
			for(int findNode=0;findNode<ZF_EA_duration.length;findNode++) {
				sum_d+=ZF_EA_duration[findNode]; 
				if(sum_d>eventTimeSum) {
					positionInDurArray=findNode;
					diffrence=sum_d-eventTimeSum;
					ratio= 1-(diffrence/ZF_EA_duration[findNode]);
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
			//AtEvent ev= new AtEvent(this,GPS,now.startInMilli+(long)(eventTimeSum*1000));
		//	upcomingEvents.add(ev);
		}
		
		for(int inters=1; inters<Intersection.size();inters++) { 
			double sumIntD=0;
			if(inters==Intersection.size()-1) {
				for(int dur=0;dur<ZF_EA_duration.length;dur++) {
					sumIntD+=ZF_EA_duration[dur];
				}											//Letztes Event, Intersection last = City "City"
			//	AtEvent ev= new AtEvent(this,Intersection.get(inters) ,(long)(now.startInMilli+(sumIntD*1000)));
				if(Intersection.get(inters).getLatitude()==Distanzmatrix.startCity.getLatitude()&&Intersection.get(inters).getLongitude()==Distanzmatrix.startCity.getLongitude()) {
	//				ev.status="Erste Stadt wieder erreicht";
				}
			
				
				else if(All_Cities.checkForCities()==1) {
		//			ev.status="Letze Stadt erreicht";
				}
		//		upcomingEvents.add(ev);
				break;
			}
			
			else {
				for(int node=0;node<Nodes.size();node++) {
					sumIntD+=ZF_EA_duration[node];   
					if(Intersection.get(inters).getId()==Nodes.get(node).getId()) { //Hier hat Intersection noch die ID der korresponiderenden Nodes
		//					AtEvent ev= new AtEvent(this,Intersection.get(inters),(long)(now.startInMilli+(sumIntD*1000)));
							if(All_Cities.checkForCities()==2 &&Intersection.get(inters+1).getType()=="City") {
				//				ev.status="Operatoren-Stop";
							}
			//				upcomingEvents.add(ev);
							break;	
					}	
				}
			}	
		}
	}

}
	
