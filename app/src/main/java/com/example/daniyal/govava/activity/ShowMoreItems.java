package com.example.daniyal.govava.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.etsy.android.grid.StaggeredGridView;
import com.example.daniyal.govava.Adapters.ShowMoreItems_Adapter;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.GsonUtils;
import com.example.daniyal.govava.models.Men_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowMoreItems extends AppCompatActivity {


    private static final String TAG = "home";
    private final String showMoreItems_url = "http://api.walmartlabs.com/v1/trends?apiKey=n857fahjjqksk3pey8ew78np&format=json";

    List<Men_items> items;
    ShowMoreItems_Adapter showMoreItems_adapter;
    ProgressDialog progressDialog;
    StaggeredGridView gridview;
    public LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_items);

        progressDialog = new ProgressDialog(this);

        gridview = (StaggeredGridView)findViewById(R.id.staggeredGridView1);
        ll_back = (LinearLayout)findViewById(R.id.ll_back);
        items = new ArrayList<>();
        showMoreItems_adapter = new ShowMoreItems_Adapter(this,items);
        //listView.setAllowReordering(true);
        // initialize your items array
        gridview.setAdapter(showMoreItems_adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Men_items men_items = items.get(i);
                startActivity(new Intent(ShowMoreItems.this, ItemDetails.class).putExtra("men_items",men_items));
            }
        });
        getItemsFromService();

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void getItemsFromService()
    {
        Log.d(TAG,"getItemsFromService success : ");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, showMoreItems_url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.hide();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray itemJsonArray = jsonObject.getJSONArray("items");
                    Log.d(TAG,"response : "+response);

                    for(int i=0; i<itemJsonArray.length(); i++)
                    {
                        JSONObject itemJsonObject = itemJsonArray.getJSONObject(i);

                        Men_items men_items = GsonUtils.fromJSON(itemJsonObject,Men_items.class);

                        items.add(men_items);

                    }

                    showMoreItems_adapter.notifyDataSetChanged();
                    //asymmetricAdapter.notifyDataSetChanged();
                    //Log.d(TAG,"showMoreItems_adapter.getItem(0)"+showMoreItems_adapter.getCount());
//                    Log.d(TAG,"asymmetricAdapter.getCount()"+asymmetricAdapter.getCount());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"error : "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
