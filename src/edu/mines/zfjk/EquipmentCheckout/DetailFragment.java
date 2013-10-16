package edu.mines.zfjk.EquipmentCheckout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 9/29/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailFragment extends Fragment {
    Equipment e; // package local

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            // TODO
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inventory_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // TODO: Upgrade to using newInstance() static method and bundle and parcelable
        if(e == null){
            throw new IllegalStateException("DetailFragment not populated with an Equipment object");
        }
        TextView nameView = (TextView) getView().findViewById(R.id.detail_name);
        nameView.setText(e.name);
    }

    public void update() {
        TextView nameView = (TextView) getView().findViewById(R.id.detail_name);
        nameView.setText(e.name);
    }
}
