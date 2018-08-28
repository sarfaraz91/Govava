package com.example.daniyal.govava.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Men_items;
import com.example.daniyal.govava.models.ShowMoreItems_Model;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pc on 4/24/2018.
 */

public class ShowMoreItems_Adapter extends BaseAdapter{

    private List<Men_items> item_list;
    private Context context;
    private final Random mRandom;
   // private final ArrayList<Integer> mBackgroundColors;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    public RelativeLayout rl_img2;

    public ShowMoreItems_Adapter(Context context,List<Men_items> item_list) {
        mRandom = new Random();
        this.item_list = item_list;
        this.context = context;
       /* mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(Color.parseColor("#ffffff"));
        mBackgroundColors.add(Color.parseColor("#ffffff"));
        mBackgroundColors.add(Color.parseColor("#ffffff"));*/
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


            view = layoutInflater.inflate(R.layout.showmoreitems2, null);

        }
        //rl_img2 = (RelativeLayout)view.findViewById(R.id.rl_img2);
        double positionHeight = getPositionRatio(i);
        /*int backgroundIndex = i >= mBackgroundColors.size() ?
                i % mBackgroundColors.size() : i;
        rl_img2.setBackgroundColor(mBackgroundColors.get(backgroundIndex));*/
        Men_items men_items = item_list.get(i);

        ImageView img_product = (ImageView)view.findViewById(R.id.img_product);
        Glide.with(context).load(men_items.getlargeImage()).asBitmap().into(img_product);

        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
      //  tv_price.setHeightRatio(positionHeight);
        tv_price.setText("$ "+men_items.getSalePrice());
       // DynamicHeightTextView tv_name = (DynamicHeightTextView) view.findViewById(R.id.tv_name);
        //tv_name.setHeightRatio(positionHeight);

        //tv_name.setText(men_items.getname());
        TextView tv_service = (TextView) view.findViewById(R.id.tv_service);
        tv_service.setText(men_items.getname());
      //  tv_service.setHeightRatio(positionHeight);

        View view1 = view.findViewById(R.id.view);

       // boolean odd = false;
        view1.setVisibility(View.VISIBLE);

        if(i % 2 == 1){
            view1.setVisibility(View.GONE);        }

        /*if(odd){
            view1.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
*/
        return view;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d("TAG", "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}
