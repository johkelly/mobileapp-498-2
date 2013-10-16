package edu.mines.zfjk.EquipmentCheckout;

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

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 9/29/13
 * Time: 6:38 PM
 */
public class DetailFragment extends Fragment {
    Equipment e; // package local

    ArrayList<Equipment> equipment;

    // http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-androi
    private static View view;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // TODO: Upgrade to using newInstance() static method and bundle and parcelable
        if(e == null){
            throw new IllegalStateException("DetailFragment not populated with an Equipment object");
        }
        update();
    }

    public void update() {
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
    }

    public void updateDetailsForEquipment(int position){
        Equipment e = equipment.get(position);
        // TODO
    }
}
