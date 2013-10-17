package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import static edu.mines.zfjk.EquipmentCheckout.EquipmentModelController.EquipmentModelListener;

// Referenced http://wptrafficanalyzer.in/blog/a-listfragment-application-in-android/ for minimum functionality
// of ListFragment

public class InventoryFragment extends ListFragment implements EquipmentModelListener {

    private static final String logTag = "edu.mines.zfjk.EquipmentCheckout.InventoryFragment.log";
    private static final String prefTag = "edu.mines.zfjk.EquipmentCheckout.HorribleTemporaryStorage";

    // private EquipmentModelController emc;
    private EquipmentAdapter adapter;

    private DetailsFragmentDispatcher dispatcher;

    public void setDetailDispatcher(DetailsFragmentDispatcher dfd){
        dispatcher = dfd;
    }

    // http://developer.android.com/training/basics/fragments/communicating.html
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            dispatcher = (DetailsFragmentDispatcher) activity;
        } catch (ClassCastException cce){
            throw new ClassCastException(activity.toString() + " must implement DetailFragmentDispatcher");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        adapter = new EquipmentAdapter(inflater.getContext(), R.layout.inventory_row);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        // Equipment modelTarget = emc.getAllObjects().get(position);
        dispatcher.displayDetailsFor(position);
    }

    @Override
    public void onPause(){
        super.onPause();
        // emc.stashData(getActivity().getPreferences(Context.MODE_PRIVATE));
    }

    @Override
    public void modelUpdate(List<Equipment> newModel) {
        adapter.bindToList(newModel);
        adapter.notifyDataSetChanged();
    }
}