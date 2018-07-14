import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
//Class that initiates Route-Request
// Saves, prepares and manages all returned duration values, Node Ids and Intersection coordinates
public class Route {
	
//VARIABLES:
	double[]Duration;
	String[]Nodes;
	ArrayList<City> Nodes_as_City= new ArrayList<City>();
	ArrayList<City> intersections= new ArrayList<City>();
	JSONObject Way;

//METHODS:
	// Initiates Route Request with Send_Request class
	// reads returned JSON Object and saves all information in Duration, Node and Intersections
	// Converts Nodes and Intersections in City Objects
	public void WayFromTo(Tour best) throws Exception {
		//Route Request
		try {
			Way=Send_Request.createRouteRequest(best);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//find JSON Arrays
		JSONArray routes = Way.getJSONArray("routes");			
		JSONObject r1 = routes.getJSONObject(0);
		JSONArray legs = r1.getJSONArray("legs");
		JSONObject l1= legs.getJSONObject(0);
		JSONObject annotation = l1.getJSONObject("annotation");
		JSONArray nodes=annotation.getJSONArray("nodes");
		JSONArray duration=annotation.getJSONArray("duration");
		JSONArray steps= l1.getJSONArray("steps");
		
		Nodes= new String[nodes.length()];
		Duration= new double[duration.length()];
		
		//Save values of JSON Arrays in Node and Duration
		for(int a=0;a<nodes.length();a++){
			if(a==nodes.length()-1){
			Nodes[a]=String.valueOf(nodes.getLong(a));
			}
			else{
			Nodes[a]=String.valueOf(nodes.getLong(a));
			Duration[a]=duration.getDouble(a);
			}
		}
		
		//Convert Node Ids in City Objects
		//Nodes_as_City=Send_Request.getNodes(Nodes);
		
		City n1= new City("14795415","Node",8.3903420, 49.0127410);
		City n2= new City("25947870","Node", 8.3904210,49.0134581);
		City n3= new City("4935288158","Node", 8.3904480,49.0137314);
		City n4= new City("4935288181","Node", 8.3904717,49.0139720);
		City n5= new City("1719671859","Node",8.3905241,49.0144687);
		City n6= new City("14795418","Node", 8.3905318,49.0145414);
		City n7= new City("25948133","Node", 8.3916510,49.0144975);
		City n8= new City("1719671856","Node", 8.3916443,49.0144270);
		City n9= new City("4224766861","Node", 8.3916187,49.0141004);
		Nodes_as_City.add(n1);
		Nodes_as_City.add(n2);
		Nodes_as_City.add(n3);
		Nodes_as_City.add(n4);
		Nodes_as_City.add(n5);
		Nodes_as_City.add(n6);
		Nodes_as_City.add(n7);
		Nodes_as_City.add(n8);
		Nodes_as_City.add(n9);

		//Create City objects of type "Interesection" from JSON Array "Intersections" in JSONObject "steps"
		//Receive longitude and latitude from location in JSONArray
		//First and last "intersection" JSON Object are type =="City" but keep their coordinates from responses 
		//-> Corresponding "Cities" in All_Cities will be updated in GA
		//ID of first and last intersections equals ID of corresponding city object of type "City"
		//ID of all other "Intersections" equal ID of corresponding city object of type "Node" from Nodes_as_City
		String id="";
		for( int step=0;step<steps.length();step++) {
			JSONObject s1= steps.getJSONObject(step);
			JSONArray intersects= s1.getJSONArray("intersections");
			int counter=0;
																			//	System.out.println("Intersects lenghth :"+intersects.length());
			for(int inter=0; inter<intersects.length();inter++) {
				JSONArray location = intersects.getJSONObject(inter).getJSONArray("location");
				double [] position= new double[2];
				position[0]=location.getDouble(0);
				position[1]=location.getDouble(1);
				
				//== first City Object "Intersection"
				if(inter==0&&step==0) {
					id=best.getCity(1).getId();
					City newCity= new City(id,"City",position);
					//System.out.println(newCity.id+" "+newCity.type+" "+newCity);
					intersections.add(newCity);
					counter++;
				}
				
				//==last City Object "Intersection"
				else if(step==steps.length()-1 && inter==intersects.length()-1) {
					City newCity;
					if(All_Cities.checkForCities()==1) {
						id=All_Cities.startCity.id;
						newCity= new City(id,"City",position);
					}
					else {
						id=best.getCity(2).getId();
						newCity= new City(id,"City",position);
					}
																		//System.out.println(newCity.id+" "+newCity.type+" "+newCity);
					intersections.add(newCity);
					counter++;
				}
				else {
					//													System.out.println("reached at inters: "+inter);
					for(int node=1; node<Nodes_as_City.size();node++) {
					if(Maths.round(position[1],6)==Maths.round(Nodes_as_City.get(node).getLatitude(),6)&&Maths.round(position[0],6)==Maths.round(Nodes_as_City.get(node).getLongitude(),6)) {
						 id=Nodes_as_City.get(node).getId();	
						}
					}
																		//System.out.println("id:" +id);
					City newCity= new City(id,"Intersection",position);//ACHTUNG hier k�nnt ein fehler sein
																						//	System.out.println(newCity.id+" "+newCity.type+" "+newCity);
					intersections.add(newCity);  
				}
			}
		}
	}

}
