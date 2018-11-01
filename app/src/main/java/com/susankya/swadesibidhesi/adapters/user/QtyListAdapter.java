
package com.susankya.swadesibidhesi.adapters.user;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.Size;

import java.util.ArrayList;

public class QtyListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Size> qtyItems;

    public QtyListAdapter(Context context, ArrayList<Size> qtyItems) {
        this.context = context;
        this.qtyItems = qtyItems;
        //colorArray = context.getResources().getIntArray(R.array.colors);
    }

    @Override
    public int getCount() {
        return qtyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return qtyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.qty_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.qtyTitle);
        txtTitle.setText(qtyItems.get(position).getSize());
        return convertView;
    }
}