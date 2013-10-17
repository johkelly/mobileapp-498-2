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
 *
 * We were forced to compromise on the fragment functionality of the app because managing multiple layouts, across
 * multiple screen sizes, across multiple orientations proved to be too complex. We believe that the landscape-only
 * layout provided is a reasonable compromise of functionality and visual appeal that still satisfies the requirement
 * to use fragments.
 *
 * We otherwise believe we satisfied our App Proposal by including a Master/Detail multi-pane/single-pane layout via
 * fragments, an asynchronous web call off the main thread, local persistence of fetched data, and local persistence
 * of selected item state.
 *
 * Outside tutorials/code resources are noted near their relevant classes/functions, where appropriate.
 */
public class MainActivity extends Activity implements DetailsFragmentDispatcher {

    /**
     * For objects wishing to respond to the Refresh action bar button.
     */
    public static interface refreshListener {
        public void refreshSoft();
        public void refreshHard();
    }

    EquipmentModelController emc;

    /**
     * Callback for Activity creation
     * @param savedInstanceState Not used
     */
    @Override
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

    /**
     * Callback for menu (ActionBar) creation.
     * @param menu Menu object to be populated
     * @return true to display the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_action, menu);
        return true;
    }

    /**
     * Menu item selected callback.
     * @param item MenuItem selected
     * @return true to consume the selection event
     */
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

    /**
     * Lifecycle callback for resuming activity.
     * Restore selection and model data here.
     */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        if (prefs.contains("selection") && findViewById(R.id.solo_fragment_container) == null) {
            int i = prefs.getInt("selection", 0);
            displayDetailsFor(i);
        }
    }

    /**
     * Lifecycle callback for pausing activity.
     * Stash the model data here.
     */
    @Override
    protected void onPause() {
        super.onPause();
        emc.stashData(getPreferences(Context.MODE_PRIVATE));
    }

    /**
     * Dispatch a DetailsFragment according to the current layout
     * @param pos index of the Equipment object to display details for
     */
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