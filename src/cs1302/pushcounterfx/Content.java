package cs1302.pushcounterfx;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Content {
	private String[] artUrlArray;
	
	public Content(String search) throws MalformedURLException {
		
		try {
			String refined = "https://itunes.apple.com/search?term="+search+"&entity=album";
			URL url = new URL(refined);
			InputStreamReader reader = new InputStreamReader(url.openStream());
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(reader);
			JsonObject root = je.getAsJsonObject();                      // root of response
			JsonArray results = root.getAsJsonArray("results");          // "results" array
			int numResults = results.size(); 
			artUrlArray = new String[numResults];// "results" array size
			for (int i = 0; i < numResults; i++) {                       
			    JsonObject result = results.get(i).getAsJsonObject();    // object i in array
			    JsonElement artworkUrl100 = result.get("artworkUrl100"); // artworkUrl100 member
			    if (artworkUrl100 != null) {                             // member might not exist
			         String artUrl = artworkUrl100.getAsString();        // get member as string
			         artUrlArray[i] = artUrl;                         // print the string
			    } // if
			} // for
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getArtUrlArray() {
		return artUrlArray;
	}
}
