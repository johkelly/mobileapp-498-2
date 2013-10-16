package edu.mines.zfjk.EquipmentCheckout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/9/13
 * Time: 3:44 PM
 */
public class CheckoutStatusFragment extends Fragment {
	
	public Rental rental;

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
        return inflater.inflate(R.layout.checkout_status, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // TODO: Upgrade to using newInstance() static method and bundle and parcelable
        if(rental == null){
            throw new IllegalStateException("CheckoutFragment not populated with an Rental object");
        }
        update();
    }
    
    public void update() {
    	TextView userLabel = (TextView) getView().findViewById(R.id.checkout_user);
    	userLabel.setText(rental.renter);
    	
    	TextView checkedOutLabel = (TextView) getView().findViewById(R.id.checkout_out);
    	checkedOutLabel.setText(rental.checkout_date);
    	
    	TextView checkedOutDue = (TextView) getView().findViewById(R.id.checkout_due);
    	checkedOutDue.setText(rental.checkout_due);
    	
    	RelativeLayout inLayout = (RelativeLayout) getView().findViewById(R.id.in_layout);
    	RelativeLayout outLayout = (RelativeLayout) getView().findViewById(R.id.out_layout);
    	
    	if (rental.is_checked_out) {
    		inLayout.setVisibility(View.INVISIBLE);
    		outLayout.setVisibility(View.VISIBLE);
    	} else {
    		inLayout.setVisibility(View.VISIBLE);
    		outLayout.setVisibility(View.INVISIBLE);
    	}
    }

}
