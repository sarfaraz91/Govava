package com.example.daniyal.govava.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyal.govava.Adapters.RecentSearch_Adapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.Recent_S_SharedPref;
import com.example.daniyal.govava.activity.HomeCategory;
import com.example.daniyal.govava.activity.SearchActivity;

import java.util.ArrayList;

/**
 * Created by AST on 5/2/2018.
 */

public class SearchFragment extends android.support.v4.app.Fragment {

    public ListView recent_search;
    public TextView clear_search;
    AlertDialog ad;
    RecentSearch_Adapter recentSearch_adapter;
    public ArrayList<String> stringArrayList;
    public EditText ed_pets_search;
    public RelativeLayout clear_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_view, null);
        ed_pets_search = (EditText) view.findViewById(R.id.ed_pets_search);
        recent_search = (ListView) view.findViewById(R.id.recent_search);
        clear_search = (TextView) view.findViewById(R.id.clear_search);
        clear_text = (RelativeLayout) view.findViewById(R.id.clear_text);
        stringArrayList = new ArrayList<>();

        if (Recent_S_SharedPref.getRecentSearch(getActivity()) != null) {
            stringArrayList.addAll(Recent_S_SharedPref.getRecentSearch(getActivity()));
            //  recentSearch_adapter.notifyDataSetChanged();
        }
        recentSearch_adapter = new RecentSearch_Adapter(getActivity(), stringArrayList);
        recent_search.setAdapter(recentSearch_adapter);
        //    setListViewHeightBasedOnChildren(recent_search);
//        stringArrayList.add("Sneaker");
//        stringArrayList.add("Sneaker Men");
//        stringArrayList.add("Sneaker");

        clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.clear_dialog, null);
                TextView cancel_search = (TextView) view1.findViewById(R.id.cancel_search);
                TextView ok_clear = (TextView) view1.findViewById(R.id.ok_clear);
                final RadioButton check_yes = (RadioButton) view1.findViewById(R.id.check_yes);
                final RadioButton check_no = (RadioButton) view1.findViewById(R.id.check_no);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Do you want to clear recent \n searches?");
                cancel_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                ok_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (check_yes.isChecked()) {
                            stringArrayList.clear();
                            Recent_S_SharedPref.setRecentSearch(getActivity(), stringArrayList, "");
                            ad.dismiss();
                            recentSearch_adapter.notifyDataSetChanged();
                        } else if (check_no.isChecked()) {
                            ad.dismiss();
                        }
                    }
                });
                alert.setView(view1);
                alert.create();
                alert.setCancelable(false);
                ad = alert.show();
            }
        });


        ed_pets_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_pets_search.setText("");
            }
        });

        recent_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ed_pets_search.setText(stringArrayList.get(i));
                ed_pets_search.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });


        return view;
    }

    public void performSearch() {
        if (!ed_pets_search.getText().toString().equals("")) {
            //    stringArrayList.add(ed_pets_search.getText().toString());
            Recent_S_SharedPref.setRecentSearch(getActivity(), stringArrayList, ed_pets_search.getText().toString());
            Intent intent = new Intent(getActivity(), HomeCategory.class);
            if (((SearchActivity) getActivity()).categoryName != null) {
                String catName = ((SearchActivity) getActivity()).categoryName + " ";
                intent.putExtra("CategoryName", catName +">"+ ed_pets_search.getText().toString());
                intent.putExtra("fromSearch",true);
                //intent.putExtra("BestBuy",false);

                ((SearchActivity) getActivity()).categoryName = null;
            } else {
                intent.putExtra("CategoryName", ed_pets_search.getText().toString());
                intent.putExtra("BestBuy", true);
            }
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(Recent_S_SharedPref.getRecentSearch(getActivity())!=null){
//            stringArrayList.clear();
//            stringArrayList.addAll(Recent_S_SharedPref.getRecentSearch(getActivity()));
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
