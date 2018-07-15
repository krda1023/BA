import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

public class Test_main {
    static double round(double wert){
		double erg=Math.round(wert*Math.pow(10,6))/Math.pow(10, 6);;
		return erg;
	}
	public static void main(String[]args) throws Exception {
		City lastlocation = new City("20", "GPS",8.3903420, 49.0127410);
		City c0= new City("0", "City", 8.390409,49.013296);
		City c1= new City("1", "City", 8.391630,49.014276);
		City c2= new City("2", "City", 8.334120,49.014376);
		All_Cities.addCity(c0);
		All_Cities.addCity(c1);
		All_Cities.addCity(c2);
		All_Cities.addCity(lastlocation);

		ArrayList<City>Nodes= new ArrayList<City>();
		int GPSinNode=0;
		
		double toDrivetoIntersection=0;
		double toDrivetoCity=0;
		double toDrivetoNode=0;
		TimeElement lastEventTime= new TimeElement();
		int hour= lastEventTime.getHour();																//current hour
		double ttnh=lastEventTime.getTimeToNextHour();
		City n1= new City("14795415","Node",8.3903420, 49.0127410);
		City n2= new City("25947870","Node", 8.3904210,49.0134581);
		City n3= new City("4935288158","Node", 8.3904480,49.0137314);
		City n4= new City("4935288181","Node", 8.3904717,49.0139720);
		City n5= new City("1719671859","Node",8.3905241,49.0144687);
		City n6= new City("14795418","Node", 8.3905318,49.0145414);
		City n7= new City("25948133","Node", 8.3916510,49.0144975);
		City n8= new City("1719671856","Node", 8.3916443,49.0144270);
		City n9= new City("4224766861","Node", 8.3916187,49.0141004);
		Nodes.add(n1);
		Nodes.add(n2);
		Nodes.add(n3);
		Nodes.add(n4);
		Nodes.add(n5);
		Nodes.add(n6);
		Nodes.add(n7);
		Nodes.add(n8);
		Nodes.add(n9);
		ArrayList<City>Intersections= new ArrayList<City>();
		City i1= new City("0", "City", 8.390403,49.013296);
		City i2= new City("25947870", "Intersection", 8.390421,49.013458);
		City i3= new City("14795418", "Intersection", 8.390532,49.014541);
		City i4= new City("25948133", "Intersection", 8.391651,49.014498);
		City i5= new City("1", "City", 8.391632,49.014276);
		Intersections.add(i1);
		Intersections.add(i2);
		Intersections.add(i3);
		Intersections.add(i4);
		Intersections.add(i5);
		ArrayList<City>beste=new ArrayList<City>();
		beste.add(lastlocation);
		beste.add(c0);
		beste.add(c1);
		beste.add(c2);
		Tour best= new Tour(beste);
		System.out.println("best: "+best);
		System.out.println("Start operation");
		double durations[]= {204.84,46.6,42,85.3,144.2,27.4,81.1,122.3};
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
		
		best.deleteCity(0);
		if(Intersections.get(1).getType()=="Intersection") {
			best.addatPosition(1, Intersections.get(1));
		}
		
		//delete last location in All_Cities
		All_Cities.deleteCity(lastlocation);
		//Insert next "intersection" if available and do a matrix update for next intersection
		if(Intersections.get(1).getType()=="Intersection"){
			All_Cities.addCity(Intersections.get(1));
			int PosinNode=0;
			for(int a=0; a<Nodes.size();a++) {
				if(Nodes.get(a).getId()==Intersections.get(1).getId()) {
					break;
				}
				PosinNode++;
			}
			for(int n=0;n<PosinNode;n++) {
				if(toDrivetoIntersection+durations[n]*Maths.getFaktor(hour)>ttnh) {
		    		int h_next;
					if(hour==23) {
						h_next=0;
					}
					else {
						h_next=hour+1;
					}
		    		double tohour=ttnh-durations[n]*Maths.getFaktor(hour);		;									//calculate the time from sum to next hour
					double hourratio= tohour/durations[n]*Maths.getFaktor(hour);									// Calculate ratio of driven way in this section
					toDrivetoIntersection+=hourratio*durations[n]*Maths.getFaktor(hour)+(1-hourratio)*durations[n]*Maths.getFaktor(h_next);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;	
					hour+=1;
					if(hour==24) {
						hour=0;
					}
				
				}
				else {
					toDrivetoIntersection+=durations[n]*Maths.getFaktor(hour);
				}
			}
		System.out.println("best: "+best);
		System.out.println("todrivetointersection: "+toDrivetoIntersection);

		for(int f=0;f<durations.length;f++) {
			System.out.print(durations[f]+" ");
		}
		System.out.println();
		for(int g=0;g<All_Cities.numberOfCities();g++) {
			System.out.println(All_Cities.getCity(g));
		}
	}
}
}

    /*	Route route= new Route();
    	double toDrivetoIntersection=0;
    	double[] durations;
    	ArrayList <City>Nodes;
    	ArrayList <City>Intersections;
	TimeElement	lastEventTime= new TimeElement();
		int hour= lastEventTime.getHour();																//current hour
		double ttnh=lastEventTime.getTimeToNextHour();
		City c1= new City("1","City", 8.397889,49.06864);
		City c2= new City("2","City", 8.390443,49.013294);
		City c3= new City("3","City", 8.391678,49.014275);
		City c4= new City("4","City", 8.411368,49.020199);
		City c5= new City("5","City", 8.434368,49.012229);
		City lastCity=c1;
		Population pop = new Population(10,false);
		
		
		ArrayList<City> abc= new ArrayList<City>();
		ArrayList<City> cba= new ArrayList<City>();

		abc.add(c1);
		abc.add(c2);
		abc.add(c3);
		abc.add(c4);
		abc.add(c5);
		cba.add(c1);
		cba.add(c2);
		cba.add(c3);
		cba.add(c5);
		cba.add(c4);
		Tour best= new Tour(abc);
		pop.saveTour(0,best);
		for(int i=1; i<10;i++) {
			Tour t= new Tour(cba);
			pop.saveTour(i, t);
		}
		route.WayFromTo(best);
		durations=route.Duration;
		
		Nodes=route.Nodes_as_City;
		Intersections=route.intersections;
		//Passe Start und End IntersectionCity an damit gleichheit mit All_Cities und tour besteht,
		//sowie start und end node	aus dem gleichen Ziel
		//Berechne duration für neuen Start und End Node über Approximation

		double lat_ratio_start=(Nodes.get(1).getLatitude()-Intersections.get(0).getLatitude())/(Nodes.get(1).getLatitude()-Nodes.get(0).getLatitude());	
		double lon_ratio_start=(Nodes.get(1).getLongitude()-Intersections.get(0).getLongitude())/(Nodes.get(1).getLongitude()-Nodes.get(0).getLongitude());
		double avg_ratio_start= (lat_ratio_start+lon_ratio_start)/2;  
		durations[0]=durations[0]*avg_ratio_start;
		Intersections.set(0,best.getCity(1));
		Nodes.set(0, Intersections.get(0)); //e.location == City "City"
		
		double lat_ratio_end=(Nodes.get(Nodes.size()-1).getLatitude()-Intersections.get(Intersections.size()-1).getLatitude())/(Nodes.get(Nodes.size()-1).getLatitude()-Nodes.get(Nodes.size()-2).getLatitude());	
		double lon_ratio_end=(Nodes.get(Nodes.size()-1).getLongitude()-Intersections.get(Intersections.size()-1).getLongitude())/(Nodes.get(Nodes.size()-1).getLongitude()-Nodes.get(Nodes.size()-2).getLongitude());
		double avg_ratio_end= (lat_ratio_end+lon_ratio_end)/2;
		durations[durations.length-1]=durations[durations.length-1]*avg_ratio_end;
		Intersections.set(Intersections.size()-1,best.getCity(2));
		Nodes.set(Nodes.size()-1, Intersections.get(Intersections.size()-1));
		//RouteServiceEvent event= new RouteServiceEvent(this, Nodes,Intersections, durations,best);
	//	fireEvent(event);
		for ( int t =0; t<pop.populationSize();t++) {
			
			if(Intersections.get(1).getType()=="Intersection"){
				//Wenn Intersection auf Strecke, was fast immer der Fall ist, füge diese in Tour an zweiter Position ein
				pop.getTour(t).addatPosition(1,Intersections.get(1));
			}
			//Wenn nicht passe Tour an, dass nächste Stadt aus Route Request aufjedenfall besucht wird
			else {
				int delete=pop.getTour(t).positionofCity(best.getCity(2));
				pop.getTour(t).deleteCity(delete);
				pop.getTour(t).addatPosition(1, best.getCity(2));
			}
		}
		if(Intersections.get(1).getType()=="Intersection"){
			All_Cities.addCity(Intersections.get(1));
			//Distanzmatrix.updateAllMatrix();
		}
		lastCity=Distanzmatrix.startCity;
		if(Intersections.get(1).getType()=="City") {  //falls keine Intersection auf der Strecke liegt, außer Start und Zielstadt
			for(int n=0; n<Nodes.size()-1;n++) {
				if(toDrivetoIntersection+durations[n]*Maths.getFaktor(hour)>ttnh) {
					double tohour=ttnh-durations[n]*Maths.getFaktor(hour);		;									//calculate the time from sum to next hour
					double hourratio= tohour/durations[n]*Maths.getFaktor(hour);									// Calculate ratio of driven way in this section
					toDrivetoIntersection+=hourratio*durations[n]*Maths.getFaktor(hour)+(1-hourratio)*durations[n]*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;	
					hour+=1;
					if(hour==24) {
						hour=0;
					}
				
				}
				else {
					toDrivetoIntersection+=durations[n]*Maths.getFaktor(hour);
				}
			}
		}
		
		else {
			for(int n=0; n<Nodes.size()-1;n++) {
				if(Nodes.get(n).getId()==Intersections.get(1).getId()) {		//Sobald Node gefunden der gleich der ersten Intersection ist, stoppe
					break;
				}
				else {
					if(toDrivetoIntersection+durations[n]*Maths.getFaktor(hour)>ttnh) {
						double tohour=ttnh-durations[n]*Maths.getFaktor(hour);		;									//calculate the time from sum to next hour
						double hourratio= tohour/durations[n]*Maths.getFaktor(hour);									// Calculate ratio of driven way in this section
						toDrivetoIntersection+=hourratio*durations[n]*Maths.getFaktor(hour)+(1-hourratio)*durations[n]*Maths.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
						ttnh+=3600;	
						hour+=1;
						if(hour==24) {
							hour=0;
						}
					
					}
					else {
						toDrivetoIntersection+=durations[n]*Maths.getFaktor(hour);
					}
				}
			}
		}
		for(int as=0;as<Intersections.size();as++) {
			System.out.print("I"+as+": "+Intersections.get(as).getId()+ " "+Intersections.get(as).getType()+Intersections.get(as).getLongitude()+ " "+Intersections.get(as).getLatitude()+"     ");
		}
		System.out.println();
		for(int as=0;as<Nodes.size();as++) {
			System.out.print("N"+as+": "+Nodes.get(as).getId()+ " "+Nodes.get(as).getType()+Nodes.get(as).getLongitude()+ " "+Nodes.get(as).getLatitude()+"     ");
		}
		System.out.println();
		for(int as=0;as<durations.length;as++) {
			System.out.print("dur"+as+": "+durations[as]+ " ");
		}
		System.out.println("to drive to Intersection "+toDrivetoIntersection);
    }
}
		/*Route r = new Route();
		ArrayList<City> abc= new ArrayList<City>();
		City c1= new City(1,"City", 13.388802,52.517033);
		City c2= new City(2,"City", 13.397621,52.529432);
		City c3= new City(3,"City", 13.375621,52.519432);
		abc.add(c1);
		abc.add(c2);
		abc.add(c3);
				
		Tour best= new Tour(abc);
		try {
			r.WayFromTo(best);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
*/


		
		
		
		
		
		
		
		
		
		/*double[] duration= new double[] {501,312,514,652,23,817,800,21,45,112,193};
		GammaVerteilung Gamma= new GammaVerteilung();
		Zeitfaktoren faktoren= new Zeitfaktoren();
		double[] gammaDuration= new double[duration.length];												//Array for Gamma influenced values
		double sumD=0;
		double sumGd=0;
		for(int i=0; i<duration.length;i++) {													//Loop through duration values and use gamma function
			gammaDuration[i]=Gamma.goGamma(duration[i], 0.5, 0.5, 0.5);	
			sumD+=duration[i];
			sumGd+=gammaDuration[i];
		}
		
		TimeElement now= new TimeElement();
		double durationSumZFEA=0;																//Sum of gamma and time factor influenced values looped
		double[] ZF_EA_duration= new double[gammaDuration.length];										//Array for final values for Simulation, gamma and time factor influenced
		int hour= now.getHour();																//actual hour
		double ttnh=now.getTimeToNextHour();													// Duration to next hour in seconds
		System.out.println(ttnh);	
		for(int j=0; j<gammaDuration.length;j++) {											//Loop through Gamma 
				
				if(durationSumZFEA+gammaDuration[j]*faktoren.getFaktor(hour)>ttnh) {			//If the sum of the values + the actual value is bigger than the time to the next hour
					double tohour=ttnh-durationSumZFEA		;									//calculate the time from sum to next hour
					double ratio= tohour/gammaDuration[j]*faktoren.getFaktor(hour);				// Calculate ratio of driven way in this section
					
					
					

					ZF_EA_duration[j]=ratio*gammaDuration[j]*faktoren.getFaktor(hour)+(1-ratio)*gammaDuration[j]*faktoren.getFaktor(hour+1);		//multiply ratio with value*factor of past hour and the reverse ratio with the value*factor of upcoming hour
					ttnh+=3600;																	// add 3600 seconds to timetonexthour
					durationSumZFEA+=ZF_EA_duration[j];											//Update Sum 
					hour+=1;
					if(hour==24) {
						hour=0;
					}
					System.out.println("WECHSEL");													//Update time factor
					
				}
				else {																			// If actual step is within the same hour
					ZF_EA_duration[j]=gammaDuration[j]*faktoren.getFaktor(hour);				//multiply value with time factor
					durationSumZFEA+=ZF_EA_duration[j];		
					System.out.println("Kein WECHSEL"+ ZF_EA_duration[j]);	//Update Sum
				}
			}	
		
		for(int f=0;f<duration.length;f++) {
			System.out.print(duration[f]+"    ");
			System.out.print(gammaDuration[f]+"    ");
			System.out.print(ZF_EA_duration[f]+"    ");
			System.out.println();
			
			
		}
		
		System.out.println(sumD);
		System.out.println(sumGd);
		System.out.println(durationSumZFEA);*/
	






/*Thread t = new Thread()
{
  @Override public void run()
  {
    try
    {
      while ( true ) System.out.println( "I Like To Move It." );
    }
    catch ( ThreadDeath td )
    {
      System.out.println( "Das Leben ist nicht totzukriegen." );
      throw td;
    }
  }
};
t.start();
try { Thread.sleep( 5000 ); } catch ( Exception e ) { }
t.stop();
System.out.println();
System.out.println("ficken");*/