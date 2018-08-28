package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

//import com.example.daniyal.govava.R;
import com.bumptech.glide.Glide;
import com.example.daniyal.govava.Helper.FavouritesDbHelper;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Home_RecyclerView;
import com.example.daniyal.govava.models.Men_items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc on 4/3/2018.
 */

public class MenAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = "menAdapter";

    private Context context;
    private List<Men_items> item_list;
    private List<Men_items> clone_item_list;
    FavouritesDbHelper favouritesDbHelper;
    Boolean fav,contacts;
    SharedPreferences prefs;

    public MenAdapter(Context context, List<Men_items> item_list, Boolean fav,Boolean contacts) {
        this.context = context;
        this.item_list = item_list;
        this.clone_item_list = item_list;
        favouritesDbHelper = new FavouritesDbHelper(context);
        this.fav = fav;
        this.contacts = contacts;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            view = layoutInflater.inflate(R.layout.men_items, null);

        }

        final Men_items men_items = item_list.get(i);


        Log.d(TAG, "views");

        final ImageView img_men = (ImageView) view.findViewById(R.id.img_men);
        final TextView price = (TextView) view.findViewById(R.id.price);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView tv_serviceName = (TextView) view.findViewById(R.id.tv_serviceName);

        TextView from = view.findViewById(R.id.from);

        tv_serviceName.setText(men_items.getServiceName());

        final ToggleButton toggle_fav = (ToggleButton) view.findViewById(R.id.toggle_fav);

        if (fav) {
            toggle_fav.setChecked(true);
        }

        toggle_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (fav) {
                    if (!isChecked) {
                        toggle_fav.setChecked(false);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                favouritesDbHelper.deleteContact(men_items.getName(),men_items.getSalePrice(),men_items.getLargeImage(),men_items.getServiceName());
                                item_list.size();
                                item_list.remove(i);
                                notifyDataSetChanged();
                            }
                        }, 500);

                    }
                }else
                {
                    long insert = favouritesDbHelper.insertFavourite(men_items.getname(), men_items.getSalePrice(),
                            men_items.getlargeImage(), men_items.getServiceName());
                    favouritesDbHelper.getAllContacts();
                    Log.d("7755", "name : " + men_items.getname());
                }


            }
        });


     /*   toggle_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long insert = favouritesDbHelper.insertFavourite(men_items.getname(), men_items.getSalePrice(),
                        men_items.getlargeImage(), men_items.getServiceName());
                favouritesDbHelper.getAllContacts();
                Log.d("7755", "name : " + men_items.getname());


            }
        });*/

        prefs = context.getSharedPreferences("code",Context.MODE_PRIVATE);

        if(fav){
            if(contacts) {
                price.setVisibility(View.GONE);
                tv_serviceName.setVisibility(View.GONE);
                from.setVisibility(View.GONE);
                toggle_fav.setVisibility(View.GONE);

                if(men_items.getlargeImage() != null){
                    img_men.setImageBitmap(StringToBitMap(men_items.getlargeImage()));
                }else{
                    img_men.setImageResource(R.drawable.profile_pic);
                }
            }
        }

        if(fav){
            if(!contacts)
            Glide.with(context).load(men_items.getlargeImage()).asBitmap().into(img_men);
        }
        else
            Glide.with(context).load(men_items.getlargeImage()).asBitmap().into(img_men);

        /*tv_itemName.setText(item_list.get(i).getName());
        tv_price.setText(item_list.get(i).getSalePrice());*/

        //img_men.setImageResource(Integer.parseInt(men_items.getMen_img()));
        if (men_items.getSalePrice() != null) {
            price.setText("$ " + men_items.getSalePrice().toString());
        } else {
            price.setText("$");
        }
        if (men_items.getname() != null) {

            name.setText(men_items.getname().toString());
        } else {
            name.setText("-");
        }

        return view;
    }

    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public Filter getFilter() {
        Log.d(TAG, "filtered");

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                /*mAdapterUsed.clear();
                mAdapterUsed.addAll((List<T>) results.values);*/

                item_list = (List<Men_items>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Men_items> FilteredArrayNames = new ArrayList<Men_items>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < clone_item_list.size(); i++) {
                    Men_items itemNames = clone_item_list.get(i);
                    if (itemNames.getname().toLowerCase().startsWith(constraint.toString())) {
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