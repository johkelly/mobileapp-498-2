package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
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

    public EquipmentModelController emc;
    private EquipmentAdapter adapter;

    private DetailsFragmentDispatcher dispatcher;

    public void setDetailDispatcher(DetailsFragmentDispatcher dfd){
        dispatcher = dfd;
    }

    // http://developer.android.com/training/basics/fragments/communicating.html

    /**
     * Callback for when this fragment attaches to an activity. Here we capture the parent as a dispatcher.
     * @param activity parent activity being attached to
     */
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            dispatcher = (DetailsFragmentDispatcher) activity;
        } catch (ClassCastException cce){
            throw new ClassCastException(activity.toString() + " must implement DetailFragmentDispatcher");
        }
    }

    /**
     * Callback for when this fragment creates its views/layouts.
     * @param inflater utility object
     * @param container where this fragment is going
     * @param savedInstanceState unused
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        adapter = new EquipmentAdapter(inflater.getContext(), R.layout.inventory_row);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Callback for when an item in the listing was clicked
     * @param l Containing ListView
     * @param v Row view for the item
     * @param position Index of the item
     * @param id unused
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        // Equipment modelTarget = emc.getAllObjects().get(position);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        prefs.edit().putInt("selection", position).commit();

        dispatcher.displayDetailsFor(position);
    }

    /**
     * Callback for resuming this fragment.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (emc != null) {
            emc.refreshSoft();
        }
    }

    /**
     * Callback for an EquipmentModel to inform this object that it needs to update its data reference.
     * @param newModel Reference to the new List of data objects
     */
    @Override
    public void modelUpdate(List<Equipment> newModel) {
        adapter.bindToList(newModel);
        adapter.notifyDataSetChanged();
    }
}