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

    public EquipmentAdapter(Context context, int layoutResId, Equipment[] data){
        super(context, layoutResId, data);
        this.context = context;
        this.layoutResId = layoutResId;
        this.data = Arrays.asList(data);
    }

    public EquipmentAdapter(Context context, int layoutResId){
        super(context, layoutResId);
        this.context = context;
        this.layoutResId = layoutResId;
        data = new ArrayList<Equipment>();
    }

    public void bindToList(List<Equipment> newData){
        data = newData;
    }

    @Override
    public int getCount(){
        return data.size();
    }

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

    static class ItemHolder{
        ImageView thumbnail;
        TextView name;
        TextView descr;
    }
}
