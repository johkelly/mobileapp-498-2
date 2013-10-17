package edu.mines.zfjk.EquipmentCheckout;

import android.content.Context;
import android.content.SharedPreferences;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 9/29/13
 * Time: 6:38 PM
 */
public class DetailFragment extends Fragment implements EquipmentModelController.EquipmentModelListener{

    private List<Equipment> equipment;

    // http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-android
    private static View view;

    /**
     * Callback for when this fragment is created
     * @param savedInstanceState unused, except by super
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Callback for when this fragment's view is created
     * @param inflater Utility object
     * @param container Where this object will be inserted
     * @param savedInstanceState Unused
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        // http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-androi
        if(view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.inventory_detail, container, false);
        } catch (InflateException ie){
            // details are already there?
        }
        return view;
    }

    /**
     * Callback for when the view for this fragment has been successfully created. We can now access its child Views
     * @param view Inflated View
     * @param savedInstanceState Retrieve what item to select, if possible/necessary
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // TODO: Upgrade to using newInstance() static method and bundle and parcelable
        if(savedInstanceState != null){
            int pos = savedInstanceState.getInt("position");
            // 0 is an error indicator: could not find position key
            if(pos == 0){
                return ;
            }
            // decrement position by 1 to get an index
            pos -= 1;
            showEquipmentDetailsFor(pos);
        }
    }

    /**
     * Update the View for a given Equipment object
     * @param pos Index of the Equipment object in the current shared data model
     */
    public void showEquipmentDetailsFor(int pos) {
        if (equipment.size() <= pos) return;

        Equipment e = equipment.get(pos);

        TextView nameView = (TextView) getView().findViewById(R.id.detail_name);
        nameView.setText(e.name);
        
        TextView detailView = (TextView) getView().findViewById(R.id.detail_type);
        detailView.setText(e.type);
        
        TextView descriptionView = (TextView) getView().findViewById(R.id.detail_description);
        descriptionView.setText(e.description);
        
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(imageView, e.image_url);
        
        CheckoutStatusFragment checkout = (CheckoutStatusFragment) getFragmentManager().findFragmentById(R.id.checkout_status_fragment);
        checkout.rental = e.rental;
        checkout.update();
    }

    @Override
    public void modelUpdate(List<Equipment> newModel) {
        equipment = newModel;

    }
}
