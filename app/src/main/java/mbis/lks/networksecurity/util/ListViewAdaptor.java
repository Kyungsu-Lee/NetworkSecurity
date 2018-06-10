package mbis.lks.networksecurity.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mbis.lks.networksecurity.R;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class ListViewAdaptor extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<UserIDItem> data;
    private int layout;

    public ListViewAdaptor(Context context, int layout, ArrayList<UserIDItem> data)
    {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public String getItem(int position){return data.get(position).getName();}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        UserIDItem listviewitem = data.get(position);
        TextView name = (TextView)convertView.findViewById(R.id.item_name);
        name.setText(listviewitem.getName());
        return convertView;
    }
}
