import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {
	All_Cities liste;
	double[]Nodes;
	All_WP WP;
	double[]Duration;
	double[]Distance;
	Send_Request anfrage;
	Tour fittest;
	JSONObject Way;
	
	public Route()
	{
		this.liste=new All_Cities();
		this.anfrage=new Send_Request(liste);
		this.fittest=Run.getPop().getFittest();
	}
	
	public void WayFromTo() throws Exception 
	{
		try {
		anfrage.createDirectionRequest(fittest);
		Way=anfrage.getDirection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JSONObject routes = Way.getJSONObject("routes");
		JSONObject legs= routes.getJSONObject("legs");
		JSONObject annotations= legs.getJSONObject("annotation");
		JSONObject metadata=annotations.getJSONObject("metadata");
		JSONArray nodes=metadata.getJSONArray("nodes");
		JSONArray duration=metadata.getJSONArray("duration");
		JSONArray distance=metadata.getJSONArray("distance");
		
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
		WP=anfrage.getWP(Nodes);
		for(int s=0;s<WP.numberOfCities();s++)
		{
			System.out.println(WP.getCity(0));
		}
		
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
	
	public All_WP getWP()
	{
		//zieh aus JSOn object nodes wenn nodes =null
		return WP;
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
