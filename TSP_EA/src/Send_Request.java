
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

	public class Send_Request {
		
		
		All_Cities staedteliste = new All_Cities();
	     double[][] erg;
	     JSONObject Way;
	    

	     static int anfragencounter=0;
		
		


	public Send_Request(All_Cities liste ) {
			 this.staedteliste=liste;
			 this.erg= new double[liste.numberOfCities()+1][liste.numberOfCities()+1];
		}

	public static int getAnfragencounter()
	{
		return anfragencounter;
	}
	
	public double[][] getergebnis()
	{ return erg;}
	
	public JSONObject getDirection()
	{
		return Way;
	}
	
	

	public void setStaedteliste()
	{
		staedteliste=new All_Cities();
	}
	
	public StringBuffer gogo(String gesamt) throws Exception
	{
		URL obj = new URL(gesamt);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     con.setRequestMethod("GET");
	     con.setRequestProperty("User-Agent", "Mozilla/5.0");
	     int responseCode = con.getResponseCode();
	     BufferedReader in = new BufferedReader(
	     new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) 
	     {
	     	response.append(inputLine);
	     }
	     in.close();
	     anfragencounter++;
	     return response;
	}
	
	public void createDirectionRequest(Tour fittest) throws Exception
	{
		String gesamt= "http://router.project-osrm.org/table/v1/driving/";
		
		City From=fittest.getCity(0);
		City To=fittest.getCity(1);
		double x1=From.getLongitude();
		double y1=From.getLatitude();
		double x2=To.getLongitude();
		double y2= To.getLatitude();
		gesamt += Double.toString(x1);
		 gesamt+=",";
		 gesamt+=Double.toString(y1);
		 gesamt+=";";
		 gesamt+= Double.toString(x2);
		 gesamt=",";
		 gesamt+=Double.toString(y2);
		 gesamt +="?steps=true";
		 StringBuffer response = gogo(gesamt);
		 Way= new JSONObject(response.toString());
		
	}
	
	public void createAsymMatrix(City stepCity) throws Exception
	{	
		int anzahlstädte=All_Cities.numberOfCities();
		int numberOfCases;
		if(anzahlstädte%99==0)
		{
		 numberOfCases= anzahlstädte/99;
		
		}
		else
		{
			numberOfCases=(anzahlstädte/99)+1;
			;
		}
		for(int asym=1;asym<=numberOfCases;asym++)
		{
			if(anzahlstädte-(asym*99)<0)      //wenn splitted Asymanfrage 
			{
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				double x = stepCity.getLongitude();
			 	double y = stepCity.getLatitude();
				 zwischenerg += Double.toString(x);
				 zwischenerg+=",";
				 zwischenerg+=Double.toString(y);
				 zwischenerg+=";";
				for(int position=((asym-1)*99);position<anzahlstädte;position++)
				{
					City intermediate = All_Cities.getCity(position);
				 	double x1 = intermediate.getLongitude();
				 	double y1=intermediate.getLatitude();
					 zwischenerg += Double.toString(x1);
					 zwischenerg+=",";
					 zwischenerg+=Double.toString(y1);
					 if(position==(anzahlstädte-1))    //-1
					 {}
					 else
					 {
					 zwischenerg+=";";
					 }
				}
				 String gesamt=urlAnfang+zwischenerg+"?sources=0";
				// System.out.println(gesamt);
				 StringBuffer response = gogo(gesamt); 
			     JSONObject jobj= new JSONObject(response.toString());
			     JSONArray dura_1 = jobj.getJSONArray("durations");
			     int z=1;
			     
			     int fromCityID=stepCity.getId();
			     JSONArray dura_2=dura_1.getJSONArray(0);
			     for (int positionzeile=((asym-1)*99);positionzeile<anzahlstädte;positionzeile++)
			     {
			    	 	int toCityID=All_Cities.getCity(positionzeile).getId();				   	   			    	    	
			    	    	 erg[fromCityID-1][toCityID-1] = dura_2.getDouble(z);
			    	    	 z++;				    	    	
			     }			   	    				    	
			    	z=1;
			}	
			
			if(anzahlstädte-(asym*99)>=0)	//wenn volle 1x99 Anfrage
			{
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				double x = stepCity.getLongitude();
			 	double y = stepCity.getLatitude();
				 zwischenerg += Double.toString(x);
				 zwischenerg+=",";
				 zwischenerg+=Double.toString(y);
				 zwischenerg+=";";
				for(int position=((asym-1)*99);position<asym*99;position++)
				{
					City intermediate = All_Cities.getCity(position);
				 	double x1 = intermediate.getLongitude();
				 	double y1=intermediate.getLatitude();
					zwischenerg += Double.toString(x1);
					zwischenerg+=",";
					zwischenerg+=Double.toString(y1);
					 if(position==((asym*99)-1))    //-1
					 {}
					 else
					 {
						 zwischenerg+=";";
					 }
				}
				 String gesamt=urlAnfang+zwischenerg+"?sources=0";
			//	 System.out.println(gesamt);
				 StringBuffer response = gogo(gesamt); 
			     JSONObject jobj= new JSONObject(response.toString());
			     JSONArray dura_1 = jobj.getJSONArray("durations");
			     int z=1;
			     
			     int fromCityID=stepCity.getId();
			     JSONArray dura_2=dura_1.getJSONArray(0);
			     for (int positionzeile=((asym-1)*99);positionzeile<asym*99;positionzeile++)
			     {
			    	 	int toCityID=All_Cities.getCity(positionzeile).getId();				   	   			    	    	
			    	 	erg[fromCityID-1][toCityID-1] = dura_2.getDouble(z);
			    	 	z++;				    	    	
			     }			   	    				    	
			    	z=1;
			}	
		}
	}

	public void createBasicMatrix() throws Exception 
	{
		 Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		// System.out.println("start:"+timestamp1);
		int anzahlstädte=All_Cities.numberOfCities();
		int numberOfCases;
		int SplittedtoAdd;
		int numberSplittedMatrix = 0;
		int numberSymmMatrix;
		
		if(anzahlstädte%50==0)
		{
		 numberOfCases= anzahlstädte/50;
		
		}
		else
		{
			numberOfCases=(anzahlstädte/50)+1;
			;
		}
		if(numberOfCases%2==0)
		{
			SplittedtoAdd=numberOfCases-2;
			numberSymmMatrix=numberOfCases/2;
		}
		else
		{
			SplittedtoAdd=numberOfCases-1;
			numberSymmMatrix=(numberOfCases+1)/2;
		}
		
		for(int x=SplittedtoAdd;x>0;x=x-2)
		{	
			if(numberOfCases%2==0)
			{
			numberSplittedMatrix+=2*x;
			}
			else
			{
				if(x==SplittedtoAdd)
				{
					numberSplittedMatrix+=x;
				}
				else
				{
					numberSplittedMatrix+=x*2;
				}
			}
		}
		
		/*System.out.println("Anzahlstädte: "+anzahlstädte);
		System.out.println("Anzahl Sym Matrix: "+numberSymmMatrix);
		System.out.println("Plitt to add: "+SplittedtoAdd);
		System.out.println("Anzahl Splitt Matrix: "+numberSplittedMatrix);
		System.out.println("Number of Cases "+numberOfCases);*/
		
		//Symmetrische Matrizen
		int IFzähler=0;
		for(int sym=1;sym<=numberSymmMatrix;sym++)
		{
			
			
			if(anzahlstädte-(sym*100)<0)  //letzte Symmetrische Matrix <=100
			{	if((anzahlstädte-(sym*100))==-99)
				{
				erg[anzahlstädte-1][anzahlstädte-1]=0;
				}
				else
				{
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				for(int position=((sym-1)*100);position<anzahlstädte;position++)
				{
					City intermediate = All_Cities.getCity(position);
				 	double x = intermediate.getLongitude();
				 	double y=intermediate.getLatitude();
					 zwischenerg += Double.toString(x);
					 zwischenerg+=",";
					 zwischenerg+=Double.toString(y);
					 if(position==(anzahlstädte-1))    //-1
					 {}
					 else
					 {
					 zwischenerg+=";";
					 }
				}
				 String gesamt=urlAnfang+zwischenerg;
				// System.out.println(gesamt);
				 StringBuffer response = gogo(gesamt); 
			     JSONObject jobj= new JSONObject(response.toString());
			     JSONArray dura_1 = jobj.getJSONArray("durations");
			     int z=0;
			     int s=0;
			     for (int positionzeile=((sym-1)*100);positionzeile<anzahlstädte;positionzeile++)
			     {
			    	    JSONArray dura_2=dura_1.getJSONArray(s);	    	   
				    	for (int positionspalte=((sym-1)*100);positionspalte<anzahlstädte;positionspalte++)
				   	    {
			    	    	//System.out.print(positionzeile+" "+positionspalte+" penis ");
			    	    	 erg[positionzeile][positionspalte] = dura_2.getDouble(z);
				    	    	// System.out.print(s+" "+z+"    ");
			    	    	 z++;				    	    	
			    	    }
			   	    	//System.out.println();

			    	s++;
			    	z=0;

			    }	
			    IFzähler++;
			    System.out.println("IF 1: "+IFzähler);
			}
			}
			
			if(anzahlstädte-(sym*100)>=0)   //100x100 volle
			{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				for(int position=(sym-1)*100;position<sym*100;position++)
				{	City intermediate = All_Cities.getCity(position);
				 	double x = intermediate.getLongitude();
				 	double y=intermediate.getLatitude();
					zwischenerg += Double.toString(x);
					zwischenerg+=",";
					zwischenerg+=Double.toString(y);
					if(position==(sym*100-1) )  //-1
					{}
					else
					{
					zwischenerg+=";";
					}
				}
				String gesamt=urlAnfang+zwischenerg;
				System.out.println(gesamt);
				 StringBuffer response = gogo(gesamt); 
			     JSONObject jobj= new JSONObject(response.toString());
			     JSONArray dura_1 = jobj.getJSONArray("durations");
			    int z=0;
			    int s=0;
			    for (int positionzeile=((sym-1)*100);positionzeile<sym*100;positionzeile++)
				{
			    	JSONArray dura_2=dura_1.getJSONArray(s);  
			    	for (int positionspalte=((sym-1)*100);positionspalte<sym*100;positionspalte++) 
			    	{
			    		//System.out.print(positionzeile+" "+positionspalte+" "+z+" "+s+"   ");
			    		erg[positionzeile][positionspalte] = dura_2.getDouble(z);
			    		z++;				    	    	
			    	}
			    	//	System.out.println();
			    	s++;
				    z=0;

				    }
			   IFzähler++;
			    System.out.println("IF 2: "+IFzähler);
				}
			
			// Splitted Matrizen
			
			if(sym>1)
			{
				if(anzahlstädte-(sym*100)>=0)
				{	
					for(int zeile=1;zeile<=sym-1;zeile++) //4 volle 50x50
					{	
						for(int caseNR=1;caseNR<=4;caseNR++)
						{	
							switch (caseNR)
							{
								case 1: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									
									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								  //   IFzähler++;
									   // System.out.println("IF 3, Case1: "+IFzähler);
									    continue;
								}
								case 2: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								   //  IFzähler++;
									  //  System.out.println("IF 3, Case2: "+IFzähler);
									    continue;
								}
								case 3: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<(sym-1)*100+100;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+99)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;

									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100+50;d<(sym-1)*100+100;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								     //IFzähler++;
									    //System.out.println("IF 3, Case3: "+IFzähler);
									    continue;
								}
								case 4: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<(sym-1)*100+100;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+99)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									
									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100+50;d<(sym-1)*100+100;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
									    		System.out.print(s+" ");
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								     System.out.println(gesamt);
								     	//IFzähler++;
									   // System.out.println("IF 3, Case4: "+IFzähler);
									    continue;
								}		
							}						
						}						
					}
				}
				if(anzahlstädte-(sym*100)<0&&anzahlstädte-(sym*100)>=-49) // 2 volle 50x50 2xsplitted
				{
					for(int zeile=1;zeile<=sym-1;zeile++) //??? Zeilen gehen 100 schritte
					{	
						for(int caseNR=1;caseNR<=4;caseNR++)
						{	
							switch (caseNR)
							{
								case 1: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 
									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								    // IFzähler++;
									 //   System.out.println("IF 4, Case1: "+IFzähler);
									    continue;
								}
								case 2: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 

									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);
								    		 erg[d][c]= dura_3.getDouble(x);
									    		s++;
									    		x++;
								    	 }
								    	 s=50;
								    	 x=0;
								    	 t++;
								    	 y++;

								     }
								  //   IFzähler++;
									  //  System.out.println("IF 4, Case2: "+IFzähler);
									    continue;
								}
								case 3: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<anzahlstädte;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==anzahlstädte-1)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 

									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 
								    	 for(int d=(sym-1)*100+50;d<anzahlstädte;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
									    											    	
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100+50;e<anzahlstädte;e++)
								    {	
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
			    	 
								    	 for(int f=(zeile-1)*100;f<(zeile-1)*100+50;f++)
								    	 { 
									    	 
								    	 	erg[e][f] = dura_3.getDouble(x);		
										    x++;											    		
									    }
									     x=0;
									    	
									     y++;
								    }

								  //  IFzähler++;
								//    System.out.println("IF 4, Case3: "+IFzähler);
								    continue;
								}
								case 4: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<anzahlstädte;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==anzahlstädte-1)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 

									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 
								    	 for(int d=(sym-1)*100+50;d<anzahlstädte;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
									    											    	
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100+50;e<anzahlstädte;e++)
								    {	
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
			    	 
								    	 for(int f=(zeile-1)*100+50;f<(zeile-1)*100+100;f++)
								    	 { 
									    	 
								    	 	erg[e][f] = dura_3.getDouble(x);		
										    x++;											    		
									    }
									     x=0;
									    	
									     y++;
								    }
								//    IFzähler++;
								    //System.out.println("IF 4, Case4: "+IFzähler);
								    continue;
								}	
							}						
						}						
					}
				}
				
				if(anzahlstädte-(sym*100)<-49)
				{
					for(int zeile=1;zeile<=sym-1;zeile++) //??? 2x splitted
					{	
						for(int caseNR=1;caseNR<=2;caseNR++)
						{	
							switch (caseNR)
							{
								
								case 1: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<anzahlstädte;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==anzahlstädte-1)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									String gesamt=urlAnfang+zwischenerg;
									 
									System.out.println(gesamt);
									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 
								    	 for(int d=(sym-1)*100;d<anzahlstädte;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
									    											    	
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100;e<anzahlstädte;e++)
								    {	
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
			    	 
								    	 for(int f=(zeile-1)*100;f<(zeile-1)*100+50;f++)
								    	 { 
									    	 
								    	 	erg[e][f] = dura_3.getDouble(x);		
										    x++;											    		
									    }
									     x=0;
									    	
									     y++;
								    }
								    IFzähler++;
								    System.out.println("IF 5, Case1: "+IFzähler);
								    continue;
								}
								case 2: 
								{	String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++)// /50 Städte der aktuellen zeile
									{
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<anzahlstädte;b++)
									{
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==anzahlstädte-1)
										{}
										else
										{
											zwischenerg+=";";
										}
										
									}
									
									String gesamt=urlAnfang+zwischenerg;
									 
								    System.out.println(gesamt);

									 StringBuffer response = gogo(gesamt); 
								     JSONObject jobj= new JSONObject(response.toString());
								     JSONArray dura_1 = jobj.getJSONArray("durations");
								     int t=0;
								     int s=50;
								     int x=0;
								     int y=50;
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++)
								     {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 
								    	 for(int d=(sym-1)*100;d<anzahlstädte;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
									    											    	
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100;e<anzahlstädte;e++)
								    {	
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
			    	 
								    	 for(int f=(zeile-1)*100+50;f<(zeile-1)*100+100;f++)
								    	 { 
									    	 
								    	 	erg[e][f] = dura_3.getDouble(x);		
										    x++;											    		
									    }
									     x=0;
									    	
									     y++;
								    }
								    IFzähler++;
								 System.out.println("IF 5, Case2: "+IFzähler);
								    continue;
								}
							}						
						}						
					}
				}	
			}
		}
		 Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		 System.out.println("end:"+timestamp2);
	}

	public void createsmallMatrix() throws Exception
	{
		String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
		 String zwischenerg="";
		 for(int i=0; i<All_Cities.numberOfCities();i++)
		 {
			 	City intermediate = All_Cities.getCity(i);
			 	double x = intermediate.getLongitude();
			 	double y=intermediate.getLatitude();
				 zwischenerg += Double.toString(x);
				 zwischenerg+=",";
				 zwischenerg+=Double.toString(y);
				 if(i==(All_Cities.numberOfCities()-1))    //-1
				 {}
				 else
				 {
				 zwischenerg+=";";
				 }
			 
		 }
		 String gesamt=urlAnfang+zwischenerg;
		 StringBuffer response = gogo(gesamt); 
	     JSONObject jobj= new JSONObject(response.toString());
	     JSONArray dura_1 = jobj.getJSONArray("durations");
	     
	    for (int t=0; t<All_Cities.numberOfCities();t++)
	    {
	    	JSONArray dura_2=dura_1.getJSONArray(t);
	    	   
	        for (int i = 0; i < All_Cities.numberOfCities(); i++) 
	   	    {    	    	
	   	    	 erg[t][i] = dura_2.getDouble(i);	    	    	
	   	    }
	    }
	}
}
	
	  



