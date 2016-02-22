package main.java.LinkedIn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.io.FileOutputStream;
//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
//import java.net.URL;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;

public class LNGraph {

	private String accessToken;

	public LNGraph(String accessToken)// Parameterized constructor
	{
		this.accessToken = accessToken;
	}

	// personal info fetching url
	public String getLBGraph() {
		String graph = null;
		try {
			String g = "https://api.linkedin.com/v1/people/~"
					+ accessToken;
			URL u = new URL(g);
			System.out.println("Access token..... " + accessToken);
		    System.out.println("URL.... " + g);
			URLConnection c = u.openConnection();// open the connection
			BufferedReader in = new BufferedReader(
					new java.io.InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			System.out.println("Input line......" + inputLine);
			in.close();
			graph = b.toString();
			System.out.println("Graph data......." + graph);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	// fetching personal info in map

	// fetching personal info in map
	public Map<String, String> getGraphData(String fbGraph) {
		Map<String, String> LNProfile = new HashMap<String, String>();
		try {
			JSONObject json = new JSONObject(fbGraph);

			if (json.has("first_name"))
				LNProfile.put("first_name", json.getString("first_name"));
			if (json.has("headline"))
				LNProfile.put("headline", json.getString("headline"));
			LNProfile.put("last_name", json.getString("last_name"));
			

		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		return LNProfile;
	}

}