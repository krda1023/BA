import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
public class ReadJSONObject {
	public static void main (String[]args) {
	JSONParser parser = new JSONParser();
	try {
		Object obj = parser.parse(new FileReader("myJSON.json"));
		JSONObject jsonobject= (JSONObject)obj;
		double durations=(double) jsonobject.get("durations");
	}
	catch(FileNotFoundException e) {e.printStackTrace();}
	catch(IOException e) {e.printStackTrace();}
	catch(ParseException e) {e.printStackTrace();}
	catch(Exception e) {e.printStackTrace();}
	
}}
