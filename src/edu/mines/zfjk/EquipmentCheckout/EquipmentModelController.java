package edu.mines.zfjk.EquipmentCheckout;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Data fetching modeled after http://www.vogella.com/articles/AndroidJSON/article.html#androidjson_read
public class EquipmentModelController {

    private static final String logTag = "edu.mines.zfjk.EquipmentCheckout.EquipmentModelController";

    private static String AllObjectsEndpoint = "https://api.mongolab.com/api/1/databases/ponyrent/collections/ponies?apiKey=rXruD8i0AHovrBYVfc30MiuVKCPrmmqh";

    private ArrayList<Equipment> objects;
    private EquipmentAdapter adapter;
    private String rawJSON;

    public EquipmentModelController(EquipmentAdapter ea) {
        adapter = ea;
        objects = new ArrayList<Equipment>();
    }

    public void fetchData(SharedPreferences prefs) {
        rawJSON = prefs.getString("data", null);
        if(rawJSON == null){
            new DownloadEquipmentTask().execute(AllObjectsEndpoint);
            Log.d(logTag, "HTTP Call Out");
            return;
        } else{
            parseRawJSON(rawJSON);
            adapter.addManyEquipment(objects);
        }
        Log.d(logTag, "Retrieved from SharedPrefs");
    }

    private void parseRawJSON(String rawJSON) {
        try {
            JSONArray jsonArray = new JSONArray(rawJSON);
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
            Log.e(EquipmentModelController.class.getName(), e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

    public void stashData(SharedPreferences prefs) {
        if (rawJSON != null) {
            prefs.edit().putString("data", rawJSON).commit();
        }
    }

    private String readURL(String url) {
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

    public ArrayList<Equipment> getAllObjects() {
        return objects;
    }

    private class DownloadEquipmentTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            return readURL(urls[0]);
        }

        protected void onPostExecute(String result) {
            rawJSON = result;
            parseRawJSON(rawJSON);
            adapter.addManyEquipment(objects);
        }
    }

}
