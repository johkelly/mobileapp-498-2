package edu.mines.zfjk.EquipmentCheckout;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private Equipment[] mockData(){
        ArrayList<Equipment> mocks = new ArrayList<Equipment>();
        for(int i = 0; i < 25; i++){
            Equipment mock = new Equipment();
            mock.name = "mockname"+i;
            mock.description = i+"lorem ipsum dolor sit ameter stuf stuff stuff stuff stuffstuf stuff stuff stuff stuffstuf stuff stuff stuff stuffstuf stuff stuff stuff stuff";
            mock.image_url = "http://upload.wikimedia.org/wikipedia/en/a/af/Gray_square_illusion.png";
            mock.thumb_url = "";
            mocks.add(mock);
        }
        return mocks.toArray(new Equipment[1]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // TODO: Make some fake data
        EquipmentModelController emc = new EquipmentModelController();
        EquipmentAdapter adapter = new EquipmentAdapter(inflater.getContext(), R.layout.inventory_row, emc.getAllObjects().toArray(new Equipment[1]));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getActivity().getApplicationContext(), ((TextView)v.findViewById(R.id.item_name)).getText(), Toast.LENGTH_SHORT).show();
    }
}