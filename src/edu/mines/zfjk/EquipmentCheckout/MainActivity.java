package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


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
public class MainActivity extends Activity implements DetailsFragmentDispatcher {

    EquipmentModelController emc;

    public static interface refreshListener {
        public void refreshSoft();
        public void refreshHard();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(emc == null){
            emc = new EquipmentModelController();
        }
        setContentView(R.layout.inventory_listing);
        if (findViewById(R.id.solo_fragment_container) != null) {
            InventoryFragment inv = (InventoryFragment) new InventoryFragment();
            inv.emc = emc;
            getFragmentManager().beginTransaction().add(R.id.solo_fragment_container, inv, "inventory_fragment").commit();
            getFragmentManager().executePendingTransactions();
        }
        emc.registerListener((EquipmentModelController.EquipmentModelListener) getFragmentManager().findFragmentByTag("inventory_fragment"));
        emc.fetchData(getPreferences(MODE_PRIVATE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                emc.refreshHard();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("DERP", "Hi mom. portrait");
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("DERP", "landscape");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        if (prefs.contains("selection") && findViewById(R.id.solo_fragment_container) == null) {
            int i = prefs.getInt("selection", 0);
            displayDetailsFor(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        emc.stashData(getPreferences(Context.MODE_PRIVATE));
    }

    @Override
    public void displayDetailsFor(int pos) {
        if (emc.getAllObjects().size() <= pos) return;
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.solo_fragment_container) == null) {
            DetailFragment details = (DetailFragment) fm.findFragmentByTag("details");
            if (details == null) {
                details = new DetailFragment();
                emc.registerListener(details);
                emc.refreshSoft();
                fm.beginTransaction().add(R.id.detail_fragment_container, details, "details").addToBackStack(null).commit();
                fm.executePendingTransactions();
            }
            details.showEquipmentDetailsFor(pos);
        }
        // Single pane layout with multiple activities
        else {
            DetailFragment details = new DetailFragment();
            emc.registerListener(details);
            emc.refreshSoft();
            fm.beginTransaction().replace(R.id.solo_fragment_container, details).addToBackStack(null).commit();
            fm.executePendingTransactions();
            details.showEquipmentDetailsFor(pos);
        }
    }
}