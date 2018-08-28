package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Men_cloths_items;
import com.example.daniyal.govava.models.Men_items;

import java.util.List;

/**
 * Created by Pc on 4/3/2018.
 */


public class Men_Cloths_Adapter extends BaseAdapter {

    private Context context;
    private List<Men_cloths_items> item_list;

    public Men_Cloths_Adapter(Context context, List<Men_cloths_items> item_list) {
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

        if (view == null) {

            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            view = layoutInflater.inflate(R.layout.men_cloths_items, null);

        }

        Men_cloths_items men_cloths_items = item_list.get(i);


        ImageView img_coll = (ImageView) view.findViewById(R.id.img_coll);
        TextView tv_coll_title = (TextView) view.findViewById(R.id.tv_coll_title);

   /*     Glide.with(context).load(item_list.get(i).getMediumImage()).asBitmap().into(iv_item_img);
        tv_itemName.setText(item_list.get(i).getName());
        tv_price.setText(item_list.get(i).getSalePrice());*/

        img_coll.setImageResource(men_cloths_items.getColl_img());
        tv_coll_title.setText(men_cloths_items.getColl_title().toString());


        return view;
    }
}