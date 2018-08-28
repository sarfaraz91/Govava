package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyal.govava.activity.HomeCategory;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.activity.Pets_Activity;
import com.example.daniyal.govava.models.Home_RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc on 4/4/2018.
 */

public class Home_rv_Adapter extends RecyclerView.Adapter<Home_rv_Adapter.Home_rv_ViewHolder> implements Filterable{

    private Context context;
    private List<Home_RecyclerView> item_list;
    private List<Home_RecyclerView> clone_item_list;

    public Home_rv_Adapter(Context context, List<Home_RecyclerView> item_list) {
        this.context = context;
        this.item_list = item_list;
        this.clone_item_list = item_list;
    }

    @Override
    public Home_rv_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater)
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = layoutInflater.inflate(R.layout.home_rv_items, null);

        return new Home_rv_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Home_rv_ViewHolder holder, int position) {

        final Home_RecyclerView home_recyclerView = item_list.get(position);
        holder.tv_category_name.setText(home_recyclerView.getCategoryName());
        holder.iv_rv.setImageResource(home_recyclerView.getRv_img());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(home_recyclerView.getChangedCategoryName().equalsIgnoreCase("pets"))
                {
                    Intent intent = new Intent(context , Pets_Activity.class);
                    intent.putExtra("CategoryName", home_recyclerView.getChangedCategoryName());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, HomeCategory.class);
                    intent.putExtra("CategoryName", home_recyclerView.getChangedCategoryName());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                /*mAdapterUsed.clear();
                mAdapterUsed.addAll((List<T>) results.values);*/

                item_list = (List<Home_RecyclerView>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Home_RecyclerView> FilteredArrayNames = new ArrayList<Home_RecyclerView>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < clone_item_list.size(); i++) {
                    Home_RecyclerView categoryNames = clone_item_list.get(i);
                    if (categoryNames.getCategoryName().toLowerCase().startsWith(constraint.toString()) ||categoryNames.getCategoryName().toLowerCase().contains(constraint.toString()))  {
                        FilteredArrayNames.add(categoryNames);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;    }


    public class Home_rv_ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_rv;
        TextView tv_category_name;
        View view;


        public Home_rv_ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            iv_rv = (ImageView)itemView.findViewById(R.id.iv_rv);
            tv_category_name = (TextView)itemView.findViewById(R.id.tv_category_name);

        }
    }

}
