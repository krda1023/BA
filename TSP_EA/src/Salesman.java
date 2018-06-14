import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Salesman implements RouteServiceListener {
	private static ArrayList<AtEvent> upcomingEvents = new ArrayList<AtEvent>();
	private static ArrayList<AtEvent> pastEvents = new ArrayList<AtEvent>();
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;
	double[] duration;
	double[] ZF_GA_duration;
	GammaVerteilung Gamma = new GammaVerteilung();
	int GPS_frequency=10;
	double k=1;
	double theta=1;
	double shiftDistance=0;
	private ArrayList<myListener> listenerList= new ArrayList<myListener>();
	Zeitfaktoren faktoren= new Zeitfaktoren();
	public void addListener(myListener toAdd) {
		listenerList.add(toAdd);
	}
	
	
    static double round(double wert, double stellen){
		double erg=Math.round(wert*Math.pow(10,stellen))/Math.pow(10, stellen);;
		return erg;
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
			fireEvent(currentEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	public void fireEvent(AtEvent e) throws Exception{
		
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


	@Override
	public void GAdidRequest(RouteServiceEvent e) {
		Nodes=e.Nodes;
		Intersection= e.Intersection;
		duration=e.durations;
		createEvents();
		
	}
	
	
	public void createEvents() {
		double[] gammaDuration= new double[duration.length];												//Array for Gamma influenced values
																	
		for(int i=0; i<duration.length;i++) {													//Loop through duration values and use gamma function
			gammaDuration[i]=Gamma.goGamma(duration[i], k, theta, shiftDistance);	
		}
		
		TimeElement now= new TimeElement();
		double durationSumZFGA=0;																//Sum of gamma and time factor influenced values looped
		ZF_GA_duration= new double[gammaDuration.length];										//Array for final values for Simulation, gamma and time factor influenced
		int hour= now.getHour();																//actual hour
		double ttnh=now.getTimeToNextHour();													// Duration to next hour in seconds
			for(int j=0; j<gammaDuration.length;j++) {											//Loop through Gamma 
				
				if(durationSumZFGA+gammaDuration[j]*faktoren.getFaktor(hour)>ttnh) {			//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durationSumZFGA		;									//calculate the time from sum to next hour
					double ratio= tohour/gammaDuration[j]*faktoren.getFaktor(hour);				// Calculate ratio of driven way in this section
					ZF_GA_duration[j]=ratio*gammaDuration[j]*faktoren.getFaktor(hour)+(1-ratio)*gammaDuration[j]*faktoren.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
					durationSumZFGA+=ZF_GA_duration[j];											//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					System.out.println("WECHSEL");													//Update time factor
					
				}
				else {																			// If actual step is within the same hour
					ZF_GA_duration[j]=gammaDuration[j]*faktoren.getFaktor(hour);				//multiply value with time factor
					durationSumZFGA+=ZF_GA_duration[j];		
					System.out.println("Kein WECHSEL"+ ZF_GA_duration[j]);	//Update Sum
				}
			}
			
		//Passe Start und End Node an	
		double lat_ratio_start=(Nodes.get(1).getLatitude()-Intersection.get(0).getLatitude())/(Nodes.get(1).getLatitude()-Nodes.get(0).getLatitude());	
		double lon_ratio_start=(Nodes.get(1).getLongitude()-Intersection.get(0).getLongitude())/(Nodes.get(1).getLongitude()-Nodes.get(0).getLongitude());
		double avg_ratio_start= (lat_ratio_start+lon_ratio_start)/2;
		ZF_GA_duration[0]=ZF_GA_duration[0]*avg_ratio_start;
		Nodes.set(0, Intersection.get(0));
		
		double lat_ratio_end=(Nodes.get(Nodes.size()-1).getLatitude()-Intersection.get(Intersection.size()-1).getLatitude())/(Nodes.get(Nodes.size()-1).getLatitude()-Nodes.get(Nodes.size()-2).getLatitude());	
		double lon_ratio_end=(Nodes.get(Nodes.size()-1).getLongitude()-Intersection.get(Intersection.size()-1).getLongitude())/(Nodes.get(Nodes.size()-1).getLongitude()-Nodes.get(Nodes.size()-2).getLongitude());
		double avg_ratio_end= (lat_ratio_end+lon_ratio_end)/2;
		ZF_GA_duration[ZF_GA_duration.length-1]=ZF_GA_duration[ZF_GA_duration.length-1]*avg_ratio_end;
		Nodes.set(Nodes.size()-1, Intersection.get(Intersection.size()-1));
		
		
		int numberofGPSEvents= (int)durationSumZFGA/GPS_frequency;
		double eventTimeSum=0;
		
		for(int events=0; events<numberofGPSEvents;events++) {
			eventTimeSum+=5;
			double diffrence=0;
			double sum_d=0;
			double ratio=0;
			int positionInDurArray=0;
			for(int findNode=0;findNode<ZF_GA_duration.length;findNode++) {
				sum_d+=ZF_GA_duration[findNode]; 
				if(sum_d>eventTimeSum) {
					positionInDurArray=findNode;
					diffrence=sum_d-eventTimeSum;
					ratio= 1-(diffrence/ZF_GA_duration[findNode]);
					break;
				}
			}
				
			double lat1= Nodes.get(positionInDurArray).getLatitude();
			double lon1=Nodes.get(positionInDurArray).getLongitude();
			double lat2=Nodes.get(positionInDurArray+1).getLatitude();
			double lon2=Nodes.get(positionInDurArray+1).getLongitude();
			
			double newlat= (lat2-lat1)*ratio+lat1;
			double newlon=(lon2-lon1)*ratio+lon1;
			
			AtEvent ev= new AtEvent(this,"GPS_Signal",now.startInMilli+(long)(eventTimeSum*1000),newlon,newlat);
			upcomingEvents.add(ev);
		}
		
		for(int inters=1; inters<Intersection.size();inters++) { 
			double sumIntD=0;
			for(int node=0;node<Nodes.size();node++) {
				sumIntD+=ZF_GA_duration[node];   
				if(Intersection.get(inters).getId()==Nodes.get(node).getId()) {
					if(inters==Intersection.size()-1) {
						
						AtEvent ev= new AtEvent(this,"AtCity",(long)(now.startInMilli+(sumIntD*1000)),Intersection.get(inters).getLatitude(),Intersection.get(inters).getLongitude());
						upcomingEvents.add(ev);
						break;
					}
					else {
						
						AtEvent ev= new AtEvent(this,"AtIntersection",(long)(now.startInMilli+(sumIntD*1000)),Intersection.get(inters).getLatitude(),Intersection.get(inters).getLongitude());
						upcomingEvents.add(ev);
						break;
					}
					
				}
			}
			
		}
		
		
	}
}
	
