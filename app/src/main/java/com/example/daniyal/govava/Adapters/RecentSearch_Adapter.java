package com.example.daniyal.govava.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.daniyal.govava.R;

import java.util.ArrayList;

/**
 * Created by AST on 5/2/2018.
 */

public class RecentSearch_Adapter extends BaseAdapter  implements Filterable{

    public Activity activity;
    public ArrayList<String> strings;
    public LayoutInflater layoutInflater;
    public TextView text_search;

    public RecentSearch_Adapter(Activity activity, ArrayList<String> strings) {
        this.activity = activity;
        this.strings = strings;
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v  = layoutInflater.inflate(R.layout.recent_item,null);

        text_search = (TextView)v.findViewById(R.id.text_search);

        text_search.setText(strings.get(i));

        return v;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
