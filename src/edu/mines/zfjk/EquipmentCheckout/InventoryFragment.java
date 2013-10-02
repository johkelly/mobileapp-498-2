package edu.mines.zfjk.EquipmentCheckout;

import android.app.ListFragment;
import android.content.Context;
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
    private static final String prefTag = "edu.mines.zfjk.EquipmentCheckout.HorribleTemporaryStorage";

    private EquipmentModelController emc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        EquipmentAdapter adapter = new EquipmentAdapter(inflater.getContext(), R.layout.inventory_row);
        setListAdapter(adapter);
        emc = new EquipmentModelController(adapter);
        emc.fetchData(getActivity().getPreferences(Context.MODE_PRIVATE));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getActivity().getApplicationContext(), ((TextView)v.findViewById(R.id.item_name)).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause(){
        super.onPause();
        emc.stashData(getActivity().getPreferences(Context.MODE_PRIVATE));
    }
}