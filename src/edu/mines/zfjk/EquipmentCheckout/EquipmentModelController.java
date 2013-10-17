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
import java.util.Arrays;
import java.util.List;

// Data fetching modeled after http://www.vogella.com/articles/AndroidJSON/article.html#androidjson_read
public class EquipmentModelController implements MainActivity.refreshListener {

    public static interface EquipmentModelListener {
        public void modelUpdate(List<Equipment> newModel);
    }

    private static final String logTag = "edu.mines.zfjk.EquipmentCheckout.EquipmentModelController";

    private static String AllObjectsEndpoint = "https://api.mongolab.com/api/1/databases/ponyrent/collections/books?apiKey=rXruD8i0AHovrBYVfc30MiuVKCPrmmqh";

    private List<Equipment> objects;
    // private EquipmentAdapter adapter;
    private List<EquipmentModelListener> listeners;
    private String rawJSON;

    public EquipmentModelController() {
        // adapter = ea;
        listeners = new ArrayList<EquipmentModelListener>();
        objects = new ArrayList<Equipment>();
    }

    /**
     * Let listeners know they should rebind the data if possible
     */
    @Override
    public void refreshSoft() {
        notifyListeners();
    }

    /**
     * fetch new data and inform the listeners
     */
    @Override
    public void refreshHard() {
        objects.clear();
        fetchData();
        notifyListeners();
    }

    public void registerListener(EquipmentModelListener eml){
        listeners.add(eml);
    }

    public void removeListener(EquipmentModelListener eml){
        listeners.remove(eml);
    }

    private void notifyListeners() {
        for(EquipmentModelListener eml : listeners){
            eml.modelUpdate(objects);
        }
    }

    /**
     * Hard fetch via network call.
     */
    public void fetchData(){
        new DownloadEquipmentTask().execute(AllObjectsEndpoint);
        Log.d(logTag, "HTTP Call Out");
    }

    /**
     * Fetch from sharedprefs, if possible, network if not.
     * @param prefs SharedPreferences to fetch cached data from.
     */
    public void fetchData(SharedPreferences prefs) {
        rawJSON = prefs.getString("data", null);
        if(rawJSON == null || rawJSON.isEmpty()){
            new DownloadEquipmentTask().execute(AllObjectsEndpoint);
            Log.d(logTag, "HTTP Call Out");
            return;
        } else{
            parseRawJSON(rawJSON);
            notifyListeners();
            //adapter.addManyEquipment(objects);
        }
        Log.d(logTag, "Retrieved from SharedPrefs");
    }

    /**
     * Parse a JSON object into an Equipment Object
     * @param rawJSON JSON string to parse
     */
    private void parseRawJSON(String rawJSON) {
        try {
            JSONArray jsonArray = new JSONArray(rawJSON);
            Log.i(EquipmentModelController.class.getName(), "Number of entries " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i(EquipmentModelController.class.getName(), jsonObject.toString());

                Equipment e = new Equipment();
                e.name = jsonObject.getString("name");
                e.type = jsonObject.getString("type");
                e.image_url = jsonObject.getString("image");
                e.description = jsonObject.getString("description");
                
                Rental r = new Rental();
                JSONObject jsonRental = jsonObject.getJSONObject("rental");
                r.checkout_date = jsonRental.getString("checkout_date");
                r.checkout_due = jsonRental.getString("checkout_due");
                r.is_checked_out = jsonRental.getBoolean("checkout_status");
                r.renter = jsonRental.getString("renter");
                
                e.rental = r;

                objects.add(e);
            }

        } catch (Exception e) {
            Log.e(EquipmentModelController.class.getName(), Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }

    /**
     * Cache the data.
     * @param prefs SharedPreferences to cache the data in.
     */
    public void stashData(SharedPreferences prefs) {
        if (rawJSON != null && !rawJSON.isEmpty()) {
            prefs.edit().putString("data", rawJSON).commit();
        }
    }

    /**
     * Given a URL, retrieve a string from the network.
     * @param url URL to request a string from
     * @return Received string
     */
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

    public List<Equipment> getAllObjects() {
        return objects;
    }

    /**
     * Allows asynchronously downloading strings in the background.
     */
    private class DownloadEquipmentTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            Log.d(logTag, "fetching from " + urls[0]);
            return readURL(urls[0]);
        }

        protected void onPostExecute(String result) {
            rawJSON = result;
            parseRawJSON(rawJSON);
            notifyListeners();
            //adapter.addManyEquipment(objects);
        }
    }

}
