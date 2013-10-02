package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * The structure of this activity's layout and its handling of multiple screen sizes with regard to fragments is
 * adapted from the Android Developers <a href=http://developer.android.com/training/basics/fragments/index.html>FragmentBasics</a>
 * demo, under the <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache v2 License</a>. The demo uses the
 * Fragment Support APIs, however, this app is targeted at new enough Android devices that "standard" Fragment APIs
 * can be used instead.
 */
public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_listing);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of InventoryFragment
            InventoryFragment firstFragment = new InventoryFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }
}