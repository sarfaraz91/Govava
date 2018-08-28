package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Home_RecyclerView;
import com.example.daniyal.govava.models.Men_items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc on 4/17/2018.
 */

public class Categories_Adapter extends BaseAdapter implements Filterable {

    Context context;
    List<Home_RecyclerView> home_recyclerViewList;
    public List<Home_RecyclerView> item_list;

    public Categories_Adapter(Context context, List<Home_RecyclerView> home_recyclerViewList) {
        this.context = context;
        this.home_recyclerViewList = home_recyclerViewList;
        this.item_list = home_recyclerViewList;
    }

    @Override
    public int getCount() {
        return home_recyclerViewList.size();
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
            view = layoutInflater.inflate(R.layout.home_rv_items, null);

        }

        Home_RecyclerView home_recyclerView = home_recyclerViewList.get(i);


        ImageView iv_rv = (ImageView)view.findViewById(R.id.iv_rv);
        iv_rv.setImageResource(home_recyclerView.getRv_img());

        TextView tv_category_name = (TextView)view.findViewById(R.id.tv_category_name);
        tv_category_name.setText(home_recyclerView.getCategoryName());



        return view;
    }

    @Override
    public Filter getFilter() {
        Log.d("TAG","filtered");

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                /*mAdapterUsed.clear();
                mAdapterUsed.addAll((List<T>) results.values);*/

                home_recyclerViewList = (List<Home_RecyclerView>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Home_RecyclerView> FilteredArrayNames = new ArrayList<Home_RecyclerView>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < item_list.size(); i++) {
                    Home_RecyclerView itemNames = item_list.get(i);
                    if (itemNames.getCategoryName().toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(itemNames);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }
}
