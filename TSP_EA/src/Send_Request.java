
import javax.xml.parsers.*;
import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Send_Request {
	All_Cities liste = new All_Cities();
	double[][] erg;																				//2-dim. array for saving table service response
	JSONObject Way;																				//JSON Object for saving routing service response
	static int anfragencounter=0;																// request counter

	public Send_Request(All_Cities liste ) {
			 this.liste=new All_Cities();
			 this.erg= new double[GA.numOfCities+1][GA.numOfCities+1];
	}


	public static int getAnfragencounter(){		
		return anfragencounter;
	}
	
	public double[][] getergebnis(){															//Gets 2 dim array	
		return erg;
	}
	
	public JSONObject getDirection(){															//Gets JSONObject
		return Way;
	}
	

	
	public StringBuffer gogo(String gesamt) throws Exception{									//sends URL and gets response in Stringbuffer
		URL obj = new URL(gesamt);																//create new URL Object
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();						//open a http connection
	    con.setRequestMethod("GET");															
	    con.setRequestProperty("User-Agent", "Mozilla/5.0");
	    // int responseCode = con.getResponseCode();
	    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));	//Create BufferedReader with an InputstreamReader to read InputStream of URL Connection
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) {											//while BufferedReader still has a line to read, append line to StringBuffer
	     	response.append(inputLine);
	    }
	    in.close();																				//Close BufferedReader
	    anfragencounter++;																		//Increase request counter
	    return response;
	}
	
	public void createRouteRequest(Tour fittest) throws Exception{							//Creates String with URL, applies gogo and saves response in an JSONObejct
		String gesamt= "http://router.project-osrm.org/route/v1/driving/";						//Fixed URL Start
		City From=fittest.getCity(0);	//??? Korrekt???
		City To=fittest.getCity(1);		//???Korrekt???
		double x1=From.getLongitude();															//Longitude of departing city
		double y1=From.getLatitude();															//Latitude of departing city
		double x2=To.getLongitude();															//Longitude of destination city
		double y2= To.getLatitude();															//Latitude of destination city
		gesamt+=Double.toString(x1)+","+Double.toString(y1)+";"+Double.toString(x2)+","+Double.toString(y2)+"?steps=true&annotations=true"; //Add coordinates to url string
		//System.out.println(gesamt);
		StringBuffer response = gogo(gesamt);													//Open HTTP Connection and send URL
		Way= new JSONObject(response.toString());												//Save response in JSONObject
		
	}
	//MUSS NOCH GETESTET WERDEN!!!
	public ArrayList<City> getNodes(double[]nodes) throws Exception{		//Muss wahrscheinlich String übergeben werden da Zahl zu groß								//Gets geo-coordinates for all received OSM nodes
		ArrayList<City> Nodes= new ArrayList<City>();																//Contains all nodes that has to be converted
		for(int i=0;i<nodes.length;i++){												
			String url="http://www.openstreetmap.org/api/0.6/node/";								//Fixed URL start
			url+=nodes[i];																		//add node id
			StringBuffer response = gogo(url); 													//Open HTTP Connection and send URL
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();				
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(response.toString());
			document.getDocumentElement().normalize();
			NodeList nList = document.getElementsByTagName("node");
			Node node= nList.item(0);
			Element el= (Element) node;
			String ID =(el.getAttribute("id"));
			int id = Integer.parseInt(ID);
			String LONG=(el.getAttribute("lon"));
			String LAT=(el.getAttribute("lat"));
			double[]pos=new double[2];
			pos[0]=Double.parseDouble(LONG);
			pos[1]=Double.parseDouble(LAT);
			City newWP= new City(id,pos);
			Nodes.add(newWP);
		}
		return Nodes;
	}
	
	public void createAsymMatrix(City stepCity) throws Exception{								//Gets all distances from upcoming WP Node to all cities
		int numOfCities=GA.numOfCities;
		int numberOfCases;
		if(numOfCities%99==0){
		 numberOfCases= numOfCities/99;	
		}
		else{
			numberOfCases=(numOfCities/99)+1;
		}
		for(int asym=1;asym<=numberOfCases;asym++){
			if(numOfCities-(asym*99)<0){      //wenn splitted Asymanfrage 
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				double x = stepCity.getLongitude();
			 	double y = stepCity.getLatitude();
			 	zwischenerg += Double.toString(x);
				zwischenerg+=",";
				zwischenerg+=Double.toString(y);
				zwischenerg+=";";
				for(int position=((asym-1)*99);position<numOfCities;position++){
					City intermediate = All_Cities.getCity(position);
				 	double x1 = intermediate.getLongitude();
				 	double y1=intermediate.getLatitude();
					zwischenerg += Double.toString(x1);
					zwischenerg+=",";
					zwischenerg+=Double.toString(y1);
					if(position==(numOfCities-1))    //-1
					{}
					else{
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
			     for (int positionzeile=((asym-1)*99);positionzeile<numOfCities;positionzeile++){
			    	 int toCityID=All_Cities.getCity(positionzeile).getId();				   	   			    	    	
			    	 erg[fromCityID-1][toCityID-1] = dura_2.getDouble(z);
			    	 z++;				    	    	
			     }			   	    				    	
			    	z=1;
			}	
			
			if(numOfCities-(asym*99)>=0){	//wenn volle 1x99 Anfrage
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				double x = stepCity.getLongitude();
			 	double y = stepCity.getLatitude();
				 zwischenerg += Double.toString(x);
				 zwischenerg+=",";
				 zwischenerg+=Double.toString(y);
				 zwischenerg+=";";
				for(int position=((asym-1)*99);position<asym*99;position++){
					City intermediate = All_Cities.getCity(position);
				 	double x1 = intermediate.getLongitude();
				 	double y1=intermediate.getLatitude();
					zwischenerg += Double.toString(x1);
					zwischenerg+=",";
					zwischenerg+=Double.toString(y1);
					 if(position==((asym*99)-1))    //-1
					 {}
					 else{
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
			     for (int positionzeile=((asym-1)*99);positionzeile<asym*99;positionzeile++){
			    	 int toCityID=All_Cities.getCity(positionzeile).getId();				   	   			    	    	
			    	 erg[fromCityID-1][toCityID-1] = dura_2.getDouble(z);
			    	 z++;				    	    	
			     }			   	    				    	
			    z=1;
			}	
		}
	}

	public void createBasicMatrix() throws Exception {
		// System.out.println("start:"+timestamp1);
		int numOfCities=All_Cities.numberOfCities();
		int numberOfCases;
		int SplittedtoAdd;
		int numberSplittedMatrix = 0;
		int numberSymmMatrix;		
		if(numOfCities%50==0){
			numberOfCases= numOfCities/50;		
		}
		else{
			numberOfCases=(numOfCities/50)+1;
		}
		if(numberOfCases%2==0){
			SplittedtoAdd=numberOfCases-2;
			numberSymmMatrix=numberOfCases/2;
		}
		else{
			SplittedtoAdd=numberOfCases-1;
			numberSymmMatrix=(numberOfCases+1)/2;
		}
		
		for(int x=SplittedtoAdd;x>0;x=x-2){
			if(numberOfCases%2==0){
			numberSplittedMatrix+=2*x;
			}
			else{
				if(x==SplittedtoAdd){
					numberSplittedMatrix+=x;
				}
				else{
					numberSplittedMatrix+=x*2;
				}
			}
		}
		
		/*System.out.println("numOfCities: "+numOfCities);
		System.out.println("Anzahl Sym Matrix: "+numberSymmMatrix);
		System.out.println("Plitt to add: "+SplittedtoAdd);
		System.out.println("Anzahl Splitt Matrix: "+numberSplittedMatrix);
		System.out.println("Number of Cases "+numberOfCases);*/
		
		//Symmetrische Matrizen
		int IFzähler=0;
		for(int sym=1;sym<=numberSymmMatrix;sym++){
			if(numOfCities-(sym*100)<0){			 //letzte Symmetrische Matrix <=100
				if((numOfCities-(sym*100))==-99){
					erg[numOfCities-1][numOfCities-1]=0;
				}
				else{
					String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
					String zwischenerg="";
					for(int position=((sym-1)*100);position<numOfCities;position++){
						City intermediate = All_Cities.getCity(position);
					 	double x = intermediate.getLongitude();
					 	double y=intermediate.getLatitude();
						 zwischenerg += Double.toString(x);
						 zwischenerg+=",";
						 zwischenerg+=Double.toString(y);
						 if(position==(numOfCities-1))    //-1
						 {}
						 else{
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
				     for (int positionzeile=((sym-1)*100);positionzeile<numOfCities;positionzeile++){
				    	 JSONArray dura_2=dura_1.getJSONArray(s);	    	   
				    	 for (int positionspalte=((sym-1)*100);positionspalte<numOfCities;positionspalte++) {
					    	erg[positionzeile][positionspalte] = dura_2.getDouble(z);
					    	// System.out.print(s+" "+z+"    ");
				    	    z++;				    	    	
					    }
				   	    	//System.out.println();
				    	s++;
				    	z=0;
				     }	
			    IFzähler++;
				}
			}		
			if(numOfCities-(sym*100)>=0)  { //100x100 volle
				String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
				String zwischenerg="";
				for(int position=(sym-1)*100;position<sym*100;position++){
					City intermediate = All_Cities.getCity(position);
				 	double x = intermediate.getLongitude();
				 	double y=intermediate.getLatitude();
					zwischenerg += Double.toString(x);
					zwischenerg+=",";
					zwischenerg+=Double.toString(y);
					if(position==(sym*100-1) )  //-1
					{}
					else{
					zwischenerg+=";";
					}
				}
				String gesamt=urlAnfang+zwischenerg;
				//System.out.println(gesamt);
				 StringBuffer response = gogo(gesamt); 
			     JSONObject jobj= new JSONObject(response.toString());
			     JSONArray dura_1 = jobj.getJSONArray("durations");
			    int z=0;
			    int s=0;
			    for (int positionzeile=((sym-1)*100);positionzeile<sym*100;positionzeile++){
			    	JSONArray dura_2=dura_1.getJSONArray(s);  
			    	for (int positionspalte=((sym-1)*100);positionspalte<sym*100;positionspalte++) {
			    		//System.out.print(positionzeile+" "+positionspalte+" "+z+" "+s+"   ");
			    		erg[positionzeile][positionspalte] = dura_2.getDouble(z);
			    		z++;				    	    	
			    	}
			    	//	System.out.println();
			    	s++;
				    z=0;

			    }
			   IFzähler++;
			 //   System.out.println("IF 2: "+IFzähler);
			}
			
			// Splitted Matrizen
			
			if(sym>1){
				if(numOfCities-(sym*100)>=0){
					for(int zeile=1;zeile<=sym-1;zeile++){ //4 volle 50x50	
						for(int caseNR=1;caseNR<=4;caseNR++){	
							switch (caseNR){
								case 1: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++){// /50 Städte der aktuellen zeile
									City intermediate = All_Cities.getCity(a);
								 	double x = intermediate.getLongitude();
								 	double y=intermediate.getLatitude();
									zwischenerg += Double.toString(x);
									zwischenerg+=",";
									zwischenerg+=Double.toString(y);
									zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else{
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
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++){
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
								case 2: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else{
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
								    for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++){
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
								case 3: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<(sym-1)*100+100;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+99)
										{}
										else{
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
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++) {
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100+50;d<(sym-1)*100+100;d++){
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
								case 4: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<(sym-1)*100+100;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+99)
										{}
										else{
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
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100+50;d<(sym-1)*100+100;d++){
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
								 //    System.out.println(gesamt);
								     	//IFzähler++;
									   // System.out.println("IF 3, Case4: "+IFzähler);
									    continue;
								}		
							}						
						}						
					}
				}
				if(numOfCities-(sym*100)<0&&numOfCities-(sym*100)>=-49) {// 2 volle 50x50 2xsplitted
					for(int zeile=1;zeile<=sym-1;zeile++){ //??? Zeilen gehen 100 schritte
						for(int caseNR=1;caseNR<=4;caseNR++){
							switch (caseNR){
								case 1: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++){// /50 Städte der aktuellen zeile
										
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else{
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
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++){
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
								case 2: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++){// /50 Städte der aktuellen zeile									
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<(sym-1)*100+50;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==(sym-1)*100+49)
										{}
										else{									
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
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int d=(sym-1)*100;d<(sym-1)*100+50;d++){
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
								case 3: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<numOfCities;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==numOfCities-1)
										{}
										else{
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
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 for(int d=(sym-1)*100+50;d<numOfCities;d++){
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100+50;e<numOfCities;e++){
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int f=(zeile-1)*100;f<(zeile-1)*100+50;f++){
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
								case 4:{ 
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100+50;b<numOfCities;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==numOfCities-1)
										{}
										else{
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
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 for(int d=(sym-1)*100+50;d<numOfCities;d++){
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
								    	 }
								    	 s=50;
								    	 t++;
								     }
								     
								    for(int e=(sym-1)*100+50;e<numOfCities;e++){
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int f=(zeile-1)*100+50;f<(zeile-1)*100+100;f++){
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
				
				if(numOfCities-(sym*100)<-49){
					for(int zeile=1;zeile<=sym-1;zeile++){ //??? 2x splitted
						for(int caseNR=1;caseNR<=2;caseNR++){
							switch (caseNR){
								case 1: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100;a<(zeile-1)*100+50;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<numOfCities;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==numOfCities-1)
										{}
										else{
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
								     for(int c=(zeile-1)*100;c<(zeile-1)*100+50;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 for(int d=(sym-1)*100;d<numOfCities;d++)
								    	 {
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
								    		 s++;
								    	 }
								    	 s=50;
								    	 t++;
								     }
								    for(int e=(sym-1)*100;e<numOfCities;e++){	
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int f=(zeile-1)*100;f<(zeile-1)*100+50;f++){
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
								case 2: {
									String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
									String zwischenerg="";
									for(int a=(zeile-1)*100+50;a<(zeile-1)*100+100;a++){// /50 Städte der aktuellen zeile
										City intermediate = All_Cities.getCity(a);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										zwischenerg+=";";
									}
									for(int b=(sym-1)*100;b<numOfCities;b++){
										City intermediate = All_Cities.getCity(b);
									 	double x = intermediate.getLongitude();
									 	double y=intermediate.getLatitude();
										zwischenerg += Double.toString(x);
										zwischenerg+=",";
										zwischenerg+=Double.toString(y);
										if(b==numOfCities-1)
										{}
										else{
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
								     for(int c=(zeile-1)*100+50;c<(zeile-1)*100+100;c++){
								    	 JSONArray dura_2=dura_1.getJSONArray(t);
								    	 for(int d=(sym-1)*100;d<numOfCities;d++){
								    		 erg[c][d] = dura_2.getDouble(s);								    		 								    	
									    		s++;
								    	 }
								    	 s=50;
								    	 t++;
								     } 
								    for(int e=(sym-1)*100;e<numOfCities;e++){
								    	 JSONArray dura_3=dura_1.getJSONArray(y);
								    	 for(int f=(zeile-1)*100+50;f<(zeile-1)*100+100;f++){
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
	}

	public void createsmallMatrix() throws Exception{
		String urlAnfang="https://api.openrouteservice.org/matrix?api_key=58d904a497c67e00015b45fce60fe6750d3e4061a1e3178c1db4f08e&profile=driving-car&locations=";
		//String urlAnfang = "http://router.project-osrm.org/table/v1/driving/";
		 String zwischenerg="";
		 for(int i=0; i<All_Cities.numberOfCities();i++){
			 City intermediate = All_Cities.getCity(i);
			 double x = intermediate.getLongitude();
		   	 double y=intermediate.getLatitude();
			 zwischenerg += Double.toString(x);
			 zwischenerg+="%2C";
			 zwischenerg+=Double.toString(y);
			 if(i==(All_Cities.numberOfCities()-1))    //-1
			 {}
			else{
				zwischenerg+="%7C";
			} 
		 }
		 String gesamt=urlAnfang+zwischenerg;
		 System.out.println(gesamt);
		 StringBuffer response = gogo(gesamt); 	 
	     JSONObject jobj= new JSONObject(response.toString());
	     JSONArray dura_1 = jobj.getJSONArray("durations");  
	     for (int t=0; t<All_Cities.numberOfCities();t++){
	    	JSONArray dura_2=dura_1.getJSONArray(t);
	    	System.out.println(dura_2.toString());   
	        for (int i = 0; i < GA.numOfCities; i++) {
	        	System.out.println("erg length: "+ erg.length+ "GA.numofCIties:  "+GA.numOfCities);	    
	   	        System.out.println("dura_1.length: "+dura_1.length()+"  dura_2.length:  "+dura_2.length());
	   	        erg[t][i] = dura_2.getDouble(i);	    	    	
	   	    }
	    }
	}
}
	
	  



