package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.activity.HomeCategory;
import com.example.daniyal.govava.activity.Pets_Activity;
import com.example.daniyal.govava.models.Home_RecyclerView;
import com.example.daniyal.govava.models.Men_items;
import com.example.daniyal.govava.models.PetsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc on 3/29/2018.
 */

public class PetsAdapter extends BaseAdapter implements Filterable{

    Context context;
    /*String[] petsName;
    String [] clone_petsName;
    int[] petsImages;*/

    List<PetsModel> petsModelList;
    List<PetsModel> copyPetsModelListl;


    public PetsAdapter(Context context, List<PetsModel> petsModelList) {
        this.context = context;
        this.petsModelList = petsModelList;
        this.copyPetsModelListl = petsModelList;
    }

    /*public PetsAdapter(Context context, String[] petsName, int[] petsImages) {
        this.petsName = petsName;
        this.petsImages = petsImages;
        this.context = context;
        this.clone_petsName = petsName;
    }*/

    @Override
    public int getCount() {
        return petsModelList.size();
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

            view = layoutInflater.inflate(R.layout.pet_item, null);
        }

        final PetsModel petsModel = petsModelList.get(i);

        ImageView iv_petsimg1 = (ImageView) view.findViewById(R.id.iv_petsimg1);
        TextView  tv_petsName = (TextView) view.findViewById(R.id.tv_petsName);

        RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HomeCategory.class);
                intent.putExtra("CategoryName",petsModel.getPetsName());
                context.startActivity(intent);
            }
        });


        iv_petsimg1.setImageResource(petsModel.getPetsImages());
        tv_petsName.setText(petsModel.getPetsName());

        return view;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                petsModelList = (List<PetsModel>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                ArrayList<PetsModel> FilteredArrayNames = new ArrayList<PetsModel>();


                // perform your search here using the searchConstraint String.
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < copyPetsModelListl.size(); i++) {
                    PetsModel petsModel = copyPetsModelListl.get(i);
                    if (petsModel.getPetsName().toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(petsModel);
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
