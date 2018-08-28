package com.example.daniyal.govava.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.daniyal.govava.Adapters.LayoutAdapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.fragment.SaveProduct;
import com.example.daniyal.govava.fragment.SearchFragment;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public TabLayout tab_layout;
    public ViewPager view_pager;
    public ArrayList<Fragment> arrayList;
    public SearchFragment searchFragment;
    public SaveProduct saveProduct;
    public RelativeLayout back_img;
    public View view,view1;
    public String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tab_layout = (TabLayout)findViewById(R.id.tab_layout);
        view_pager  = (ViewPager)findViewById(R.id.view_pager);
        arrayList = new ArrayList<>();
        searchFragment = new SearchFragment();
        saveProduct  =new SaveProduct();
        back_img = (RelativeLayout)findViewById(R.id.back_img);
        arrayList.add(searchFragment);
        arrayList.add(saveProduct);
        view  = getLayoutInflater().inflate(R.layout.custom_tab,null);
        view.findViewById(R.id.icon).setBackgroundResource(R.mipmap.recentlysearch_search_icon);
        view1  = getLayoutInflater().inflate(R.layout.custom_tab,null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.mipmap.recentlysearch_saved_search);
        tab_layout.addTab(tab_layout.newTab().setCustomView(view));
        tab_layout.addTab(tab_layout.newTab().setCustomView(view1));
        final LayoutAdapter adapter = new LayoutAdapter(getSupportFragmentManager(),arrayList);


        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CategoryName");


        //is line se tablayout k neche jo shade araaha hai woh change hoga pageviewer k mutabik
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));




        view_pager.setOffscreenPageLimit(0);
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
//                int tabIconColor = ContextCompat.getColor(SearchActivity.this, R.color.colorWhite);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                if(tab.getPosition()==0) {
                    tab.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.mipmap.recentlysearch_search_icon_deselect);
                }else{
                    tab.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.mipmap.recentlysearch_saved_search_deselect);

                }
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                int tabIconColor = ContextCompat.getColor(SearchActivity.this,R.color.colorss);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                if(tab.getPosition()==0) {
                    tab.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.mipmap.searchbar_search_icon);

                }else{
                    tab.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.mipmap.recentlysearch_saved_search_deselect);

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
