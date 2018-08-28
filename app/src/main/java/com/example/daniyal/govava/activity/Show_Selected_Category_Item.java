package com.example.daniyal.govava.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daniyal.govava.Adapters.Selected_Category_Adapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.GsonUtils;
import com.example.daniyal.govava.models.Selected_item;

public class Show_Selected_Category_Item extends BaseActivity {

    String categoryName;

    TextView tv_category_name;
    ImageView iv_backImg;
    GridView gridView_selectedCategory;

    Selected_item selected_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__selected__category__item);
        setupComponents();

        Intent intent = getIntent();

        categoryName = intent.getStringExtra("categoryName");

        //set title
        tv_category_name.setText(categoryName);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="http://api.walmartlabs.com/v1/search?apiKey=n857fahjjqksk3pey8ew78np&query="+categoryName+"&numItems=25&start=1";



        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                         selected_item = GsonUtils.fromJSON(response,Selected_item.class);

                        Selected_Category_Adapter selected_category_adapter = new
                                Selected_Category_Adapter(Show_Selected_Category_Item.this
                        ,selected_item.getItems());

                        gridView_selectedCategory.setAdapter(selected_category_adapter);

                        Log.d("Response is: ","00000001"+response.substring(0,500));
                        //  mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error occur",error.getMessage());
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);






    }

    @Override
    public void initializeComponents() {

        gridView_selectedCategory = (GridView) findViewById(R.id.gridView_selectedCategory);
        iv_backImg = (ImageView) findViewById(R.id.iv_backImg);
        tv_category_name = (TextView) findViewById(R.id.tv_category_name);


    }

    @Override
    public void setupListeners() {



        iv_backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        gridView_selectedCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Show_Selected_Category_Item.this,ItemDetails.class);

                intent.putExtra("selectIteamlist",selected_item.getItems().get(i));
                startActivity(intent);

            }
        });



    }
}
