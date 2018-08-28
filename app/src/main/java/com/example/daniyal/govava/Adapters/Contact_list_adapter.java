package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyal.govava.R;

import java.util.ArrayList;

/**
 * Created by Pc on 3/28/2018.
 */

public class Contact_list_adapter extends BaseAdapter {

    private Context context;
    private ArrayList list;

    public Contact_list_adapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.contacts_list_item, null);
        }


        ImageView iv_contact_image = (ImageView) view.findViewById(R.id.iv_contact_image);
        TextView tv_contact_name = (TextView) view.findViewById(R.id.tv_contact_name);
        TextView tv_contact_address = (TextView) view.findViewById(R.id.tv_contact_address);



        return view;
    }
}
