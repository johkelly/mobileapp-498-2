package edu.mines.zfjk.EquipmentCheckout;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced http://wptrafficanalyzer.in/blog/a-listfragment-application-in-android/ for minimum functionality
// of ListFragment

public class InventoryFragment extends ListFragment {

    private static final String logTag = "edu.mines.zfjk.EquipmentCheckout.InventoryFragment.log";

    String[] tempContent = {
            "First",
            "Second",
            "Third",
            "Earth",
            "Fire",
            "Wind",
            "Water",
            "Heart",
            "Captain Planet"
    };

    static String[] rowLayoutMapKeys = new String[] {"name", "image", "description"};
    static int[] rowLayoutMapIds = new int[] {R.id.item_name, R.id.item_thumb, R.id.item_short_descr};

    //TODO: Remove
    private JSONObject[] mockData(){
        JSONObject mock = new JSONObject();
        try{
            mock.put("name", "mockname");
            mock.put("desc", "lorem ipsum dolor sit amet");
            mock.put("thumb_url", "fake");
        }
        catch (JSONException jse){
            Log.d(logTag, String.valueOf(jse.getStackTrace()));
        }
        return new JSONObject[] {mock};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // TODO: Make some fake data
        EquipmentJSONAdapter adapter = new EquipmentJSONAdapter(inflater.getContext(), R.layout.inventory_row, mockData());
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}