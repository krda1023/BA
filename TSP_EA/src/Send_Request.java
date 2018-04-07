
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

	public class Send_Request {
		
		
		All_Cities staedteliste = new All_Cities();
	     double[][] erg;
		
		
	


	public Send_Request(All_Cities liste ) {
			 this.staedteliste=liste;
		}

	public double[][] getergebnis()
	{ return erg;}


	public void call_me() throws Exception {
		
		String urlAnfang = "https://api.openrouteservice.org/matrix?api_key=%0958d904a497c67e00015b45fce60fe6750d3e4061a1e3178c1db4f08e&profile=driving-car&locations=";
		 String zwischenerg="";
		 for(int i=0; i<staedteliste.numberOfCities();i++)
		 {
			 	City intermediate = staedteliste.getCity(i);
			 	double x = intermediate.getX();
			 	double y=intermediate.getY();
				 zwischenerg += Double.toString(x);
				 zwischenerg+="%2C";
				 zwischenerg+=Double.toString(y);
				 zwischenerg+="%7C";
			 
		 }
		 String gesamt=urlAnfang+zwischenerg;
		 
		 String url = "https://api.openrouteservice.org/matrix?api_key=%0958d904a497c67e00015b45fce60fe6750d3e4061a1e3178c1db4f08e&profile=driving-car&locations=9.330093%2C9.657916%2C48.477473%7C9.970093%2C48.477473%7C9.207916%2C49.153868%7C37.573242%2C55.801281%7C115.663757%2C38.106467";
	     URL obj = new URL(url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     // optional default is GET
	     con.setRequestMethod("GET");
	     //add request header
	     con.setRequestProperty("User-Agent", "Mozilla/5.0");
	     int responseCode = con.getResponseCode();
	   //  System.out.println("Sending 'GET' request to URL : " + url);
	  //   System.out.println("Response Code : " + responseCode);
	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();
	    //System.out.println(response.toString());
	     
	     JSONObject jobj= new JSONObject(response.toString());
	     JSONArray dura_1 = jobj.getJSONArray("durations");
	     
	    for (int t=0; t<dura_1.length();t++)
	    {
	    	    JSONArray dura_2=dura_1.getJSONArray(t);
	    	    double matrixs;
	    	    for (int i = 0; i < dura_2.length(); i++) {
	    	        matrixs = dura_2.getDouble(i);
	    	        
					erg[t][i]= matrixs;
	    	       // System.out.print(" "+ matrixs);
	    	    }
	    	//    System.out.println("");}
	    	    
		
	    	
	    	}}}
	    
		
	
	/*public static void main (String[]args) {
	try {
        Send_Request.call_me();
       } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}*/
	    



