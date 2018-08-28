package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Selected_item;

import java.util.ArrayList;

/**
 * Created by Pc on 3/30/2018.
 */

public class Selected_Category_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<Selected_item.SubClass> item_list;

    public Selected_Category_Adapter(Context context, ArrayList<Selected_item.SubClass> item_list) {
        this.context = context;
        this.item_list = item_list;
    }



    @Override
    public int getCount() {
        return item_list.size();
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

        if(view == null){

            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.selected_category_item, null);

        }

        ImageView iv_item_img = (ImageView) view.findViewById(R.id.iv_item_img);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        TextView tv_itemName = (TextView) view.findViewById(R.id.tv_itemName);

        Glide.with(context).load(item_list.get(i).getMediumImage()).asBitmap().into(iv_item_img);
        tv_itemName.setText(item_list.get(i).getName());
        tv_price.setText(item_list.get(i).getSalePrice());


        return view;


    }
}
