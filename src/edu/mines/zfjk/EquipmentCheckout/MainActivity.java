package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

/**
 * The structure of this activity's layout and its handling of multiple screen sizes with regard to fragments is
 * based loosely from the Android Developers <a href=http://developer.android.com/training/basics/fragments/index.html>FragmentBasics</a>
 * demo, under the <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache v2 License</a>. The demo uses the
 * Fragment Support APIs, however, this app is targeted at new enough Android devices that "standard" Fragment APIs
 * can be used instead.
 * <p/>
 * Mobile Development Application 2, Intermediate Submission
 * Zachary Fleischman, John Kelly
 * WIP
 */
public class MainActivity extends Activity implements DetailFragmentDispatcher {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_listing);
    }

    @Override
    public void displayDetailsFor(Equipment e) {
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.detail_fragment_container) != null) {
            DetailFragment details = (DetailFragment) fm.findFragmentByTag("details");
            if (details == null) {
                details = new DetailFragment();
                details.e = e;
                fm.beginTransaction().add(R.id.detail_fragment_container, details, "details").addToBackStack(null).commit();
            } else {
                details.e = e;
                details.update();
            }
        }
        // Single pane layout with multiple activities
        else {
            // TODO
            DetailFragment detailFrag = new DetailFragment();
            detailFrag.e = e;
            fm.beginTransaction().replace(R.id.solo_inventory_fragment_container, detailFrag).addToBackStack(null).commit();
        }
    }
}