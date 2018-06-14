import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {
	All_Cities liste;
	double[]Nodes;
	ArrayList<City> Allnodes;
	double[]Duration;
	double[]Distance;
	ArrayList<City> intersections;
	ArrayList<double[]> allDurations =new ArrayList<double[]>();
	Send_Request anfrage;
	
	GammaVerteilung gamma= new GammaVerteilung();
	JSONObject Way;
	
	public Route()
	{
		this.liste=new All_Cities();
		this.anfrage=new Send_Request(liste);
		
	}
	

	public void WayFromTo(Tour best) throws Exception 
	{
		try {
		anfrage.createRouteRequest(best);
		Way=anfrage.getDirection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JSONObject routes = Way.getJSONObject("routes");
		JSONObject legs= routes.getJSONObject("legs");
		JSONObject annotations= legs.getJSONObject("annotation");
		JSONArray nodes=annotations.getJSONArray("nodes");
		JSONArray duration=annotations.getJSONArray("duration");
		JSONArray distance=annotations.getJSONArray("distance");
		JSONArray steps= legs.getJSONArray("steps");
		
		int id=0; //????
		for( int step=0;step<steps.length();step++) {
			JSONObject stepObject= steps.getJSONObject(step);
			JSONArray intersects= stepObject.getJSONArray("intersection");
			for(int inter=0; inter<intersects.length();inter++) {
				JSONArray location = intersects.getJSONObject(inter).getJSONArray("location");
				double [] position= new double[2];
				position[0]=location.getDouble(0);
				position[1]=location.getDouble(1);
				City newCity= new City(id,position);
				intersections.add(newCity);  //.....nochmal überdneken, nodeabgleich erforderlich
				id++;
				
			}
			
		}
		
		for(int a=0;a<nodes.length();a++)
		{
			if(a==nodes.length()-1)
			{
			Nodes[a]=nodes.getDouble(a);
			}
			else
			{
			Nodes[a]=nodes.getDouble(a);
			Duration[a]=duration.getDouble(a);
			Distance[a]=distance.getDouble(a);
			}
		}
		Allnodes=anfrage.getNodes(Nodes);
		
		for(int inters=1; inters<intersections.size();inters++) {		//Übergebe NodeID an Intersection zur Identifikation
			for(int node=0; node<Allnodes.size();node++) {
				if(intersections.get(inters).getLatitude()==Maths.round(Allnodes.get(node).getLatitude(),6)&&intersections.get(inters).getLongitude()==Maths.round(Allnodes.get(node).getLongitude(),6)) {
					intersections.get(inters).setID(Allnodes.get(node).getId());
				}
			}
		}
		/*for(int s=0;s<Allnodes.size();s++)
		{
			System.out.println(Allnodes.get(s));
		}*/
		
	}
	

	
	public double[] PositionBetweenNodes(City from, City to, double wert) //Wert zwischen 0-1
	{
		double[] position= new double[2];
		double longDif=to.getLongitude()-from.getLongitude();
		double latDif=to.getLatitude()-from.getLatitude();
		position[0]=from.getLongitude()+(longDif*wert);
		position[1]= from.getLatitude()+(latDif*wert);
		return position;
	}
	
	public ArrayList<City> getallNodes()
	{
		//zieh aus JSOn object nodes wenn nodes =null
		return Allnodes;
	}
	public double[]getDuration()
	{
		//zieh aus JSOn object duration wenn duration =null
		return Duration;
	}
	public double[]getDistance()
	{
		//zieh aus JSOn object distance wenn distance =null
		return Distance;
	}
}
