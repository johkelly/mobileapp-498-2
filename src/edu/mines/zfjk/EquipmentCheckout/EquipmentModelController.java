package edu.mines.zfjk.EquipmentCheckout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
// Data fetching modeled after http://www.vogella.com/articles/AndroidJSON/article.html#androidjson_read
public class EquipmentModelController {
	
	private static String AllObjectsEndpoint = "https://api.mongolab.com/api/1/databases/ponyrent/collections/ponies?apiKey=rXruD8i0AHovrBYVfc30MiuVKCPrmmqh";

	private ArrayList<Equipment> objects;
	
	public EquipmentModelController() 
	{	
		// This one is async
		//new DownloadEquipmentTask().execute(AllObjectsEndpoint);
		
		// Try doing this one first
		try {
			JSONArray jsonArray = new JSONArray(readURL(AllObjectsEndpoint));
			Log.i(EquipmentModelController.class.getName(), "Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i(EquipmentModelController.class.getName(), jsonObject.toString());
				
				Equipment e = new Equipment();
				e.name = jsonObject.getString("name");
				e.description = "Element: " + jsonObject.getString("element") + "\nRace: " + jsonObject.getString("race");
				objects.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String readURL(String url)
	{
		StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(EquipmentModelController.class.toString(), "Failed to download Database");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
	}
	
	public ArrayList<Equipment> getAllObjects()
	{
		return objects;
	}
	
	private class DownloadEquipmentTask extends AsyncTask<String, Integer, String> {
	     protected String doInBackground(String... urls) {
	        return readURL(urls[0]);
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         // TODO: optionally set up some kind of download progress callback
	    	 //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(String result) {
	    	 try {
	 			JSONArray jsonArray = new JSONArray(result);
	 			Log.i(EquipmentModelController.class.getName(), "Number of entries " + jsonArray.length());
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject jsonObject = jsonArray.getJSONObject(i);
	 				Log.i(EquipmentModelController.class.getName(), jsonObject.toString());
	 				
	 				Equipment e = new Equipment();
	 				e.name = jsonObject.getString("name");
	 				e.description = "Element: " + jsonObject.getString("element") + "\nRace: " + jsonObject.getString("race");
	 				objects.add(e);
	 			}
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	     }
	 }

}
