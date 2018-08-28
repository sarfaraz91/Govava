package com.example.daniyal.govava.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.daniyal.govava.Adapters.Categories_Adapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.Home_RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {

    GridView gridView_categories;
    Categories_Adapter categories_adapter;
    public LinearLayout ll_back;
    List<Home_RecyclerView> home_recyclerViewList;
    public EditText ed_pets_search;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        context = this;
        ed_pets_search = (EditText)findViewById(R.id.ed_pets_search);
        home_recyclerViewList = new ArrayList<>();
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.men,"men","men"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.women,"women","women"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.teen,"teen","teen"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.art,"art","art"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.auto,"auto","auto"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.baby,"baby","baby"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.beauty,"beauty","beauty"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.books,"books","books"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.cell_phones,"cell phones","cell_phones"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.clothing,"clothing","clothing"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.electronics,"electronics","electronics"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.gifts_registry,"gifts registry","gifts_registry"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.health,"health","health"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.home,"home","home"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.industrial_scientific,"industrial scientific","industrial_scientific"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.jewelry,"jewellery","jewelry"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.movies_tvshows,"movies tvshows","movies_tvshows"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.music,"music","music"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.office,"office","office"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.party_occasions,"party occasions","party_occasions"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.patio_garden,"patio garden","patio_garden"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.personal_care,"personal care","personal_care"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.pets,"pets","pets"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.photo_center,"photo center","photo_center"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.seasonal,"seasonal","seasonal"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.sports,"sports","sports"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.toys,"toys","toys"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.video_games,"video games","video_games"));
        home_recyclerViewList.add(new Home_RecyclerView(R.mipmap.miscellaneous,"miscellaneous","miscellaneous"));


        ed_pets_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                categories_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        ll_back = (LinearLayout)findViewById(R.id.ll_back);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        gridView_categories = (GridView)findViewById(R.id.gridView_categories);
        categories_adapter = new Categories_Adapter(this,home_recyclerViewList);
        gridView_categories.setAdapter(categories_adapter);
        gridView_categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context , HomeCategory.class);
                intent.putExtra("CategoryName",home_recyclerViewList.get(i).getChangedCategoryName());
                context.startActivity(intent);
            }
        });

    }
}
