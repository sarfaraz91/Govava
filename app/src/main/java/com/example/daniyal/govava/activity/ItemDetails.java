package com.example.daniyal.govava.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.daniyal.govava.Helper.ContactsDbHelper;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.ContactDetails;
import com.example.daniyal.govava.models.Men_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.daniyal.govava.Utils.Contacts_SharedPrefs.getContactsSharedPref;

public class ItemDetails extends BaseActivity {


    ImageView  iv_item_img,ItemDetailBtn,img_rating;
    TextView tv_item_Desc,salePrice,tv_item_name,long_desc,RatingNumber,tv_standard_ship_rate,tv_model_num,tv_stock,tv_numReviews,tv_rating;
    //LinearLayout ll_back;

    RelativeLayout rl_buyItNow,rl_share,iv_backImg;

    Context context;
    String displayName,displayImage,phoneNo;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        setupComponents();
        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);

        RatingBar rb2 = (RatingBar) findViewById(R.id.ratingBar2);
//        LayerDrawable stars = (LayerDrawable) rb2.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);



/*
        Selected_item.SubClass subClass = getIntent().getExtras().getParcelable("selectIteamlist");

        tv_item_Desc.setText(subClass.getName());
        Glide.with(ItemDetails.this).load(subClass.getMediumImage()).asBitmap().into(iv_item_img);*/

        /*Intent intent = getIntent();
        int itemId = intent.getIntExtra("itemId",0) ;
        String categoryName = intent.getStringExtra("CategoryName");*/
        initializeComponents();

        /*Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("img");

        Bitmap img = BitmapFactory.decodeByteArray(b, 0, b.length);

        iv_item_img.setImageBitmap(img);*/
        ArrayList<ContactDetails> contactDetailsArrayList = getContactsSharedPref(context);


        rl_buyItNow = (RelativeLayout)findViewById(R.id.rl_buyItNow);
        rl_share = (RelativeLayout)findViewById(R.id.rl_share);


        Intent intent = getIntent();
        final Men_items men_items = (Men_items)intent.getSerializableExtra("men_items");
        displayName = intent.getStringExtra("displayName");
        displayImage = intent.getStringExtra("displayImage");
        phoneNo = intent.getStringExtra("phoneNo");

        /*final int int_phoneNo;
        if(phoneNo != null) {
           int_phoneNo = Integer.parseInt(phoneNo);
        }else
            int_phoneNo = 0;*/

        rl_buyItNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactsDbHelper contactsDbHelper = new ContactsDbHelper(context);
                contactsDbHelper.insertContactGift(men_items.getName(),men_items.getSalePrice(),men_items.getlargeImage(),men_items.getServiceName(),phoneNo);


                if(men_items.getProductUrl() != null) {
                    String url = men_items.getProductUrl();
                    Uri uri = Uri.parse(url);
                    openWeb(url);
                    /*Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/
                }

                if(men_items.getViewItemURL() != null)
                {
                    String url = men_items.getViewItemURL();
                    Uri uri = Uri.parse(url);
                    openWeb(url);
                 /*   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/
                }
            }
        });




        salePrice.setText("$ "+men_items.getSalePrice());
        Glide.with(context).load(men_items.getlargeImage()).asBitmap().into(iv_item_img);
        tv_item_name.setText(men_items.getname());
        //Glide.with(context).load(men_items.getCustomerRatingImage()).asBitmap().into(img_rating);

        if(men_items.getShortDescription() != null) {
            long_desc.setText(men_items.getShortDescription());
            Log.d("9911","ll : "+long_desc.getText());
        }else{
            long_desc.setText("...");
        }
        if(men_items.getCustomerRating() != null){
            RatingNumber.setText(String.format("%.1f",Double.parseDouble(men_items.getCustomerRating())));
            rb.setRating(Float.parseFloat(men_items.getCustomerRating()));
            rb2.setRating(Float.parseFloat(men_items.getCustomerRating()));

            Log.d("9911","rating : "+RatingNumber.getText().toString());

        }else{
            RatingNumber.setText("0.0");
        }
        if(men_items.getStandardShipRate() != null)
        {
            tv_standard_ship_rate.setText("$ "+men_items.getStandardShipRate());
        }else{
            tv_standard_ship_rate.setText("0.00");
        }
        if(men_items.getModelNumber() != null)
        {
            tv_model_num.setText(men_items.getModelNumber());
        }else{
            tv_model_num.setText("....");
        }
        if(men_items.getStock() != null)
        {
            tv_stock.setText(men_items.getStock());
        }else{
            tv_stock.setText("....");
        }
        if(men_items.getNumReviews() != null)
        {
            tv_numReviews.setText("("+men_items.getNumReviews()+")");

        }else{
            tv_numReviews.setText("(0)");
        }
        if(men_items.getNumReviews() != null)
        {
            tv_rating.setText(men_items.getNumReviews()+" rating");

        }else{
            tv_rating.setText("0");
        }




        //iv_item_img.setImageResource(men_items.getMen_img());



        ItemDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetails.this , ItemDetails.class);
                startActivity(intent);
            }
        });

        iv_backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, men_items.getProductUrl()); // Simple text and URL to share
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }

    public void openWeb(String url){
        WebView mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview .loadUrl(url);
        setContentView(mWebview );
    }

    @Override
    public void initializeComponents() {

/*
        ll_back = (LinearLayout)findViewById(R.id.ll_back);
*/
        context = this;
        iv_item_img  = (ImageView) findViewById(R.id.iv_item_img);
        //img_rating = (ImageView) findViewById(R.id.img_rating);
        ItemDetailBtn = (ImageView) findViewById(R.id.ItemDetailBtn);
        iv_backImg = (RelativeLayout) findViewById(R.id.iv_backImg);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        salePrice = (TextView) findViewById(R.id.salePrice);
        long_desc = (TextView) findViewById(R.id.long_desc);
        RatingNumber = (TextView) findViewById(R.id.RatingNumber);

        tv_standard_ship_rate = (TextView) findViewById(R.id.tv_standard_ship_rate);
        tv_model_num = (TextView) findViewById(R.id.tv_model_num);
        tv_stock = (TextView) findViewById(R.id.tv_stock);
        tv_numReviews = (TextView) findViewById(R.id.tv_numReviews);
        tv_rating = (TextView) findViewById(R.id.tv_rating);


    }

    @Override
    public void setupListeners() {

        iv_backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void getData(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("items");







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
