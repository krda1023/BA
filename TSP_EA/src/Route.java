import org.json.JSONObject;

public class Route {
	All_Cities liste;
	JSONObject Way;
	Send_Request anfrage;
	Tour fittest;
	
	public Route()
	{
		this.liste=new All_Cities();
		this.anfrage=new Send_Request(liste);
		this.fittest=Run.getPop().getFittest();
	}
	
	public void WayFromTo() 
	{
		try {
		anfrage.createDirectionRequest(fittest);
		Way=anfrage.getDirection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
