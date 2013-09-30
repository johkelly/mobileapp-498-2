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
import org.json.JSONException;
import org.json.JSONObject;

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
    private Equipment[] data = null;

    public EquipmentAdapter(Context context, int layoutResId, Equipment[] data){
        super(context, layoutResId, data);
        this.context = context;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @Override
    public int getCount(){
        return data.length;
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

        Equipment item = data[pos];

//        holder.name.setText(itemJSON.getString("name"));
//        holder.name.setText(itemJSON.getString("description").substring(0, 40));

        holder.name.setText(item.name);
        holder.descr.setText(item.description.substring(0, 40));
        UrlImageViewHelper.setUrlDrawable(holder.thumbnail, item.image_url);

        return row;
    }

    static class ItemHolder{
        ImageView thumbnail;
        TextView name;
        TextView descr;
    }
}
