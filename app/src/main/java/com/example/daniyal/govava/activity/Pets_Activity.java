package com.example.daniyal.govava.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.daniyal.govava.Adapters.PetsAdapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.PetsModel;

import java.util.ArrayList;
import java.util.List;

public class Pets_Activity extends BaseActivity {

    GridView gridView_pets;
    String[] petsName;
    public LinearLayout ll_back;

    int[] petsImg;


    List<PetsModel> petsModelList;

    EditText ed_pets_search;
    PetsAdapter petsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_);
        setupComponents();

        petsModelList = new ArrayList<>();

        ll_back = (LinearLayout)findViewById(R.id.ll_back);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ed_pets_search = (EditText)findViewById(R.id.ed_pets_search);
        ed_pets_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //petsAdapter.getFilter().filter(charSequence);
                petsAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        petsModelList.add(new PetsModel("CATS",R.mipmap.cats));
        petsModelList.add(new PetsModel("DOGS",R.mipmap.dog));
        petsModelList.add(new PetsModel("HORSES", R.mipmap.horse));
        petsModelList.add(new PetsModel("SNAKES", R.mipmap.snake));
        petsModelList.add(new PetsModel("HAMSTERS",R.mipmap.hamster));
        petsModelList.add(new PetsModel("MONKEYS",R.mipmap.monkey));
        petsModelList.add(new PetsModel("BIRDS",R.mipmap.birds));



      /*  petsName = new String[]{"CATS", "DOGS", "HORSES", "SNAKES", "HAMSTERS", "MONKEYS", "BIRDS"};
        petsImg = new int[]{ R.mipmap.cats, R.mipmap.dog, R.mipmap.horse, R.mipmap.snake, R.mipmap.hamster, R.mipmap.monkey, R.mipmap.birds,};*/

        petsAdapter = new PetsAdapter(Pets_Activity.this, petsModelList);

       gridView_pets.setAdapter(petsAdapter);
    }

    @Override
    public void initializeComponents() {

        gridView_pets = (GridView) findViewById(R.id.gridView_pets);


    }

    @Override
    public void setupListeners() {

     /*   gridView_pets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //Log.d("item name ","0777777"+petsName[i]);

                Intent intent = new Intent(Pets_Activity.this,HomeCategory.class);
                intent.putExtra("CategoryName",petsModelList.get(i).getPetsName());
                startActivity(intent);



            }
        });*/
    }

    public void loadData()
    {
       // StringRequest stringRequest = new StringRequest(Request.Method.GET,)
    }


}
