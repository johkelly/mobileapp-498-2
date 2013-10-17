package edu.mines.zfjk.EquipmentCheckout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 9/29/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
// http://www.ezzylearning.com/tutorial.aspx?tid=1763429
public class EquipmentAdapter extends ArrayAdapter<Equipment>{
    private Context context;
    private int layoutResId;
    private List<Equipment> data;

    /**
     * Ctor allowing creation-time possession of data
     * @param context
     * @param layoutResId
     * @param data
     */
    public EquipmentAdapter(Context context, int layoutResId, Equipment[] data){
        super(context, layoutResId, data);
        this.context = context;
        this.layoutResId = layoutResId;
        this.data = Arrays.asList(data);
    }

    /**
     * Ctor allowing late possession of data
     * @param context
     * @param layoutResId
     */
    public EquipmentAdapter(Context context, int layoutResId){
        super(context, layoutResId);
        this.context = context;
        this.layoutResId = layoutResId;
        data = new ArrayList<Equipment>();
    }

    /**
     * Begin possessing a new List of data
     * @param newData New List to possess
     */
    public void bindToList(List<Equipment> newData){
        data = newData;
    }

    @Override
    public int getCount(){
        return data.size();
    }

    /**
     * Return an inflated and populated view to use as a row in a ListView for the given object at pos
     * @param pos Index of the item the inflated view is for
     * @param convertView Old view to reuse, if possible
     * @param parent View the returned view will go into
     * @return An inflated and populated view to use as a row in a ListView for the given object at pos
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        View row = convertView;
        ItemHolder holder = new ItemHolder();

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResId, parent, false);
        }

        holder.thumbnail = (ImageView)row.findViewById(R.id.item_thumb);
        holder.name = (TextView)row.findViewById(R.id.item_name);
        holder.descr = (TextView)row.findViewById(R.id.item_short_descr);

        row.setTag(holder);

        Equipment item = data.get(pos);

        holder.name.setText(item.name);
        holder.descr.setText(item.type);
        UrlImageViewHelper.setUrlDrawable(holder.thumbnail, item.image_url);

        return row;
    }

    /**
     * Basically a convenience struct
     */
    static class ItemHolder{
        ImageView thumbnail;
        TextView name;
        TextView descr;
    }
}
