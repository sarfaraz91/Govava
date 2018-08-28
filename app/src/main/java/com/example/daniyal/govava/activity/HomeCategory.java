package com.example.daniyal.govava.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.daniyal.govava.Adapters.MenAdapter;
import com.example.daniyal.govava.Helper.FavouritesDbHelper;
import com.example.daniyal.govava.HomeFragments;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.CircleTransform;
import com.example.daniyal.govava.Utils.GsonUtils;
import com.example.daniyal.govava.models.Men_items;
import com.example.daniyal.govava.models.User_Info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeCategory extends AppCompatActivity {

    //ImageView iv_backImg;

    GridView gridView_menItems;
    List<Men_items> men_itemsArrayList;
    MenAdapter menAdapter;

    FavouritesDbHelper favouritesDbHelper;
    String urlBestbuy = "";
    TextView tv_category_name;

    LinearLayout ll_back;

    public String url = "";
    String categoryName = "";

    ProgressDialog progressDialog;
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";


    List<Men_items> searchMen_itemsList;
    List<Men_items> tempMen_itemsList;

    //int startPoint = 1;
    int temp = 1;
    int startpoint = 1;
    public static String CURRENT_TAG = "home";
    int pageNum = 1;
    int currPageNum;
    public ImageButton filter_btn;
    EditText ed_men_search;
    private static final String TAG = "men2";
    public RelativeLayout clear_text;
    public String range_from = "", range_to = "";
    DatabaseReference databaseReference;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    FirebaseAuth firebaseAuth;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, EditProfile;
    String name = "";
    private Toolbar toolbar;
    private Timer timer;

    private boolean search;
    Boolean fromSearch;
    Boolean bestBuy = false;
    String displayName,displayImage,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        searchMen_itemsList = new ArrayList<>();
        tempMen_itemsList = new ArrayList<>();

        favouritesDbHelper = new FavouritesDbHelper(this);
        clear_text = (RelativeLayout) findViewById(R.id.clear_text);
        ed_men_search = (EditText) findViewById(R.id.ed_men_search);
        filter_btn = (ImageButton) findViewById(R.id.filter_btn);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        EditProfile = (TextView) navHeader.findViewById(R.id.TextView_EditProfile);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_men_search.setText("");
            }
        });


        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Info user_info = dataSnapshot.getValue(User_Info.class);
                name = user_info.getFullName();

                if (!TextUtils.isEmpty(name)) {
                    txtName.setText(name);
                }
                String profile_img = user_info.getPhotoUrl();

                if (!TextUtils.isEmpty(profile_img)) {
                    Glide.with(HomeCategory.this).load(profile_img).crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(getApplicationContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgProfile);
                } else {
                    Glide.with(HomeCategory.this).load(R.mipmap.contactlist_img_four)
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(HomeCategory.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgProfile);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String userId = firebaseUser.getUid();
        String userEmail = firebaseUser.getEmail();
        if (!TextUtils.isEmpty(userEmail)) {
            EditProfile.setText(userEmail);
        }


        ed_men_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                //menAdapter.getFilter().filter(charSequence);


                /*final Handler handler = new Handler();
                Timer timer2 = new Timer();

                if (menAdapter.getCount() == 0) {
                    if (charSequence.length() > 2) {
                        search = true;

                        TimerTask testing = new TimerTask() {
                            public void run() {
                                handler.post(new Runnable() {
                                    public void run() {
                                        getWalmart(1, categoryName + "%20" + charSequence.toString());
                                        getFromEbay(pageNum, categoryName + "%20" + charSequence.toString());
                                        getFromBestBuy(pageNum, categoryName + "%20" + charSequence.toString());
                                    }

                                });
                            }
                        };
                        timer2.schedule(testing, 2000);


                        *//*if(charSequence.toString().matches("last of us")){
                            getWalmart(1,charSequence.toString());
                            Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_SHORT).show();
                        }*//*
                    }
                }*/


            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if (timer != null) {
                    timer.cancel();
                }*/

            }
        });

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategory.this, FilterActivity.class);
                startActivity(intent);
            }
        });


        progressDialog = new ProgressDialog(this);
        loadNavHeader();
        setUpNavigationView();

        tv_category_name = (TextView) findViewById(R.id.tv_category_name);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CategoryName");
        displayName = intent.getStringExtra("displayName");
        displayImage = intent.getStringExtra("displayImage");
        phoneNo = intent.getStringExtra("phoneNo");

        fromSearch = getIntent().getBooleanExtra("fromSearch", false);
        if (fromSearch) {
            int index = categoryName.indexOf(">");
            ed_men_search.setText(categoryName.substring(categoryName.indexOf(">")+1,categoryName.length()));
        }

        ed_men_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromSearch) {
                    int index = categoryName.indexOf(">");
                    ed_men_search.setText(categoryName.substring(categoryName.indexOf(">")+1,categoryName.length()));
                    String[] category = categoryName.split(" ");


                    Intent intent = new Intent(HomeCategory.this, SearchActivity.class);
                    intent.putExtra("CategoryName", category[0]);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeCategory.this, SearchActivity.class);
                    intent.putExtra("CategoryName", categoryName);
                    startActivity(intent);
                }

            }
        });


        if (intent.getStringExtra("CategoryHeader") != null) {
            range_from = intent.getStringExtra("range_from");
            range_to = intent.getStringExtra("range_to");
            tv_category_name.setText("Buy");

        } else {
            tv_category_name.setText(categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase().replace("_", " "));

            if (categoryName.contains(">")) {
                int index = categoryName.indexOf(">");
                tv_category_name.setText(categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1, index - 1).toLowerCase().replace("_", " "));
            }
        }


        //url = "http://api.walmartlabs.com/v1/search?apiKey=n857fahjjqksk3pey8ew78np&query=" + categoryName + "&numItems=25&start=";


        ll_back = (LinearLayout) findViewById(R.id.ll_back);

        //iv_backImg = (ImageView)findViewById(R.id.iv_backImg);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        gridView_menItems = (GridView) findViewById(R.id.gridView_menItems);
        men_itemsArrayList = new ArrayList<>();

        menAdapter = new MenAdapter(this, men_itemsArrayList,false,false);

        gridView_menItems.setAdapter(menAdapter);

        gridView_menItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Men_items men_items = men_itemsArrayList.get(i);
                Log.d(TAG, "clicked");
                Intent intent = new Intent(HomeCategory.this, ItemDetails.class);
                intent.putExtra("men_items", men_items);
                intent.putExtra("displayName",displayName);
                intent.putExtra("displayImage",displayImage);
                intent.putExtra("phoneNo",phoneNo);

                startActivity(intent);
            }
        });




       /* gridView_menItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override{

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                gridView_menItems.getItemAtPosition(i);

                Intent intent = new Intent(HomeCategory.this , ItemDetails.class);
                startActivity(intent);

          }
        });*/

       bestBuy = getIntent().getBooleanExtra("BestBuy", false);
       getFromEbay(pageNum, categoryName);
       getWalmart(startpoint, categoryName);
       if(bestBuy){
           getFromBestBuy(pageNum, categoryName);
       }

        // Log.d("0099","startpoint1 : "+startPoint);
        gridView_menItems.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int threshold = 1;
                int count = gridView_menItems.getCount();

                if (i == SCROLL_STATE_IDLE) {
                    if (gridView_menItems.getLastVisiblePosition() >= count - threshold && i < 2) {
                        Log.i("TESTINGGG", "loading more data");
                        // Execute LoadMoreDataTask AsyncTask
                        //  savingMap_fragment.LoadMore();


                        if (search) {
                            return;
                        } else {
                            startpoint = startpoint + 25;
                            Log.d("0099", "startpoint13 : " + startpoint);
                            getWalmart(startpoint, categoryName);
                            ++pageNum;
                          //  Log.d("0099", "pageNum : " + pageNum);
                            getFromEbay(pageNum, categoryName);
                            if(bestBuy) {
                                getFromBestBuy(pageNum, categoryName);
                            }
                            }

                        /*startpoint = startpoint + 25;
                        Log.d("0099", "startpoint13 : " + startpoint);
                        getWalmart(startpoint,categoryName);
                        ++pageNum;
                        Log.d("0099", "pageNum : " + pageNum);
                        getFromEbay(pageNum,categoryName);
                        getFromBestBuy(pageNum,categoryName);*/
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.d(TAG, "scrolled");



              /*  if(i + i1 >= i2){
                    // End has been reached

                    Log.d("0099","i : "+i);
                    Log.d("0099","i1 : "+i1);
                    Log.d("0099","i2 : "+i2);



                    startpoint = startpoint + 25;
                    Log.d("0099","startpoint13 : "+startpoint);
                    getWalmart(startpoint);
                    *//*++pageNum;
                    Log.d("0099","pageNum : "+pageNum);
                    getFromEbay(pageNum);*//*
                }*/


            }
        });
    }

    public void getWalmart(int startpoint, String categoryName) {

        String url = "http://api.walmartlabs.com/v1/search?apiKey=n857fahjjqksk3pey8ew78np&query=" + categoryName + "&numItems=25&start=";


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        //progressDialog.setCancelable(false);

        if (range_from.equals("")) {
//            if (url.contains("start")) {
//                url.replace("start" + (startpoint - 1), "");
//            }
            url = url + startpoint;
        } else {
            if (url.contains("start")) {
                if (startpoint == 1) {
                    url = url + startpoint;
                } else {
                    url = url.replace("start=" + (startpoint - 25), "start=" + startpoint);
                }
            }
//            if(url.contains("start")){
//                url = url +startpoint;
//            }else {
//                url = url + "start=" + startpoint;
//            }
            if (url.contains("&facet=on&facet.range=price:[" + range_from.replace("$", "") + " TO " + range_to + "]")) {

            } else {
                url = url + "&facet=on&facet.range=price:[" + range_from.replace("$", "") + " TO " + range_to + "]";
            }
        }
        url = url.replace(" ", "%20");
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("0099", "response " + response);
                        progressDialog.hide();

                        /*if(search)
                        {
                            men_itemsArrayList.clear();
                        }*/


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject itemsJsonObject1 = jsonArray.getJSONObject(i);

                                Men_items men_items = GsonUtils.fromJSON(itemsJsonObject1, Men_items.class);
                                men_items.setServiceName("At Walmart");
                               /* Men_items men_items = new Men_items(itemsJsonObject1.getInt("itemId"),itemsJsonObject1.getString("largeImage"),
                                        itemsJsonObject1.getString("salePrice"),
                                        itemsJsonObject1.getString("name"));
*/
                                if (search) {
                                    men_itemsArrayList.add(0, men_items);
                                } else {
                                    men_itemsArrayList.add(men_items);
                                }


                            }

                            menAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("0099", "error :" + error);
                progressDialog.hide();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void getFromEbay(int pageNum, final String categoryName) {

        currPageNum = pageNum;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://svcs.ebay.com/services/search/FindingService/v1?RESPONSE-DATA-FORMAT=JSON",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.hide();

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("findItemsByKeywordsResponse");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("searchResult");
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                            JSONArray jsonArray2 = jsonObject2.getJSONArray("item");


                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject3 = jsonArray2.getJSONObject(i);

                                Men_items men_items = new Men_items("At Ebay", jsonObject3.getJSONArray("viewItemURL").get(0).toString(), jsonObject3.getJSONArray("itemId").get(0).toString(), jsonObject3.getJSONArray("galleryURL").get(0).toString().replace("\"\"", ""), jsonObject3.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__").toString(), jsonObject3.getJSONArray("title").get(0).toString());
                                JSONArray listingInfo = jsonObject3.getJSONArray("listingInfo");
                                JSONObject jsonObject4 = listingInfo.optJSONObject(0);
                                String s = (String) jsonObject4.getJSONArray("buyItNowAvailable").get(0);
                                if (s.equals("false")) {

                                    men_items.setStock("Not Available");
                                } else {
                                    men_items.setStock("Available");
                                }
                                Log.d("0023", "img : " + jsonObject3.getJSONArray("galleryURL").get(0).toString());
                                if (search) {
                                    men_itemsArrayList.add(0, men_items);
                                } else {
                                    men_itemsArrayList.add(men_items);
                                }

                            }


                            menAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("0023", "getFromEbay : " + response.toString());
                        // pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                //pDialog.hide();
                Log.d("0023", "error :" + error);

            }
        }) {

            @Override
            public String getBodyContentType() {
                Log.d("0023", "success 4");

                return "application/xml; charset=" +
                        getParamsEncoding();
            }


            @Override
            public Map<String, String> getHeaders()
                    throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();

                headers.put("X-EBAY-SOA-SECURITY-APPNAME", "GovavaAp-Govava-PRD-57879bc50-8fa9c62f");
                headers.put("X-EBAY-SOA-OPERATION-NAME", "findItemsByKeywords");

                return headers;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                Log.d("0023", "success 3");
                String postData = "";
                if (!range_from.equals("")) {
                    postData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">\n" +
                            "\t<keywords>" + categoryName + "</keywords>\n" +
                            "\t<paginationInput>" +
                            "<entriesPerPage>25</entriesPerPage>" +
                            "<pageNumber>" + currPageNum +
                            "</pageNumber>" +
                            "</paginationInput>\n" +
                            "<itemFilter>\n" +
                            "<name>MaxPrice</name>\n" +
                            "<value>" + range_to + "</value>\n" +
                            "<paramName>Currency</paramName>\n" +
                            " <paramValue>USD</paramValue>\n" +
                            "</itemFilter>\n" +
                            "<itemFilter>\n" +
                            "<name>MinPrice</name>\n" +
                            "<value>" + range_from.replace("$", "") + "</value>\n" +
                            "<paramName>Currency</paramName>\n" +
                            "<paramValue>USD</paramValue>\n" +
                            "</itemFilter>" +
                            "</findItemsByKeywordsRequest>";
                } else {
                    postData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">\n" +
                            "\t<keywords>" + categoryName + "</keywords>\n" +
                            "\t<paginationInput>" +
                            "<entriesPerPage>25</entriesPerPage>" +
                            "<pageNumber>" + currPageNum +
                            "</pageNumber>" +
                            "</paginationInput>\n" +
                            "</findItemsByKeywordsRequest>";
                }
                // TODO get your final output
                try {
                    return postData == null ? null :
                            postData.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException uee) {
                    // TODO consider if some other action should be taken
                    return null;
                }
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }

    public void getFromBestBuy(int pageNum, final String categoryName) {
        currPageNum = pageNum;
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String new_categoryName = categoryName;
        if (bestBuy) {
            new_categoryName = categoryName.replace(">", " ");
        }

        Log.d(TAG, "success bestbuyrequest");
        if (range_from.equals("")) {
//            if (url.contains("start")) {
//                url.replace("start" + (startpoint - 1), "");
//            }
            urlBestbuy = "https://api.bestbuy.com/v1/products(search=" + new_categoryName + ")?format=json&apiKey=Wt6wJOJHpFET7gLtAAfSdPrq&pageSize=25&page=" + currPageNum;
        } else {
            if (urlBestbuy.contains("page")) {
                if (currPageNum == 1) {
                    urlBestbuy = "https://api.bestbuy.com/v1/products(search=" + new_categoryName + ")?format=json&apiKey=Wt6wJOJHpFET7gLtAAfSdPrq&pageSize=25&page=" + currPageNum;
                } else {
                    urlBestbuy = urlBestbuy.replace("page=" + (currPageNum - 1), "page=" + currPageNum);
                }
            }
//            if(url.contains("start")){
//                url = url +startpoint;
//            }else {
//                url = url + "start=" + startpoint;
//            }
            if (urlBestbuy.contains("&salePrice>" + range_from.replace("$", "") + "&salePrice<" + range_to + ")?format=json&apiKey=Wt6wJOJHpFET7gLtAAfSdPrq&pageSize=25")) {

            } else {
                urlBestbuy = "https://api.bestbuy.com/v1/products(search=" + new_categoryName + "&salePrice>" + range_from.replace("$", "") + "&salePrice<" + range_to + ")?format=json&apiKey=Wt6wJOJHpFET7gLtAAfSdPrq&pageSize=25&page=" + currPageNum;
            }
        }

        Log.d(TAG, "urlBestbuy : " + urlBestbuy);

        urlBestbuy = urlBestbuy.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBestbuy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response : " + response);


                        progressDialog.hide();


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            //   Log.d(TAG,"response : "+response);


                            JSONArray products = jsonObject.getJSONArray("products");

                            Log.d(TAG, "product : " + products);

                            for (int i = 0; i < products.length();   i++) {


                                JSONObject itemsJsonObject1 = products.getJSONObject(i);

                                Men_items men_items = GsonUtils.fromJSON(itemsJsonObject1, Men_items.class);
                                men_items.setStock(men_items.getAvailableOnline());
                                men_items.setProductTemplate(String.valueOf(itemsJsonObject1.getString("productTemplate")));
                                men_items.setServiceName("At BestBuy");
                                //men_items.setLargeImage(itemsJsonObject1.get("mediumImage").toString());
                                Log.d(TAG, "name : " + men_items.getname());


                               /* Men_items men_items = new Men_items(itemsJsonObject1.getInt("itemId"),itemsJsonObject1.getString("largeImage"),
                                        itemsJsonObject1.getString("salePrice"),
                                        itemsJsonObject1.getString("name"));
*/
                                if (men_items.getProductTemplate().toLowerCase().contains(categoryName.toLowerCase())) {

                                    men_itemsArrayList.add(men_items);
                                } else
                                    men_itemsArrayList.add(men_items);


                                // Log.d(TAG, "men_itemsArrayList : " + men_itemsArrayList.get(i));

                            }


                            menAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            Log.d(TAG, "exception : " + e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error : " + error);

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void removeBackSlash(String url) {
        String result = url.replace("\"\"", "");
        Log.d("0023", "url rem " + result);
    }

    public void changeEndPoint(int endPoint) {
        if (endPoint == 25) {
            endPoint++;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();

    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
//                    case R.id.nav_Pets:
//                        Log.d("0012","pets reached");
//                        startActivity(new Intent(HomeCategory.this, Pets_Activity.class));
//                        //Toast.makeText(getApplicationContext(), "nav_pets!", Toast.LENGTH_LONG).show();
//                        break;

                    case R.id.nav_WhatToBuy:
                        Log.d("0012", "fragment reached");
                        startActivity(new Intent(HomeCategory.this, HomeFragments.class));
                        //finish();
                        Log.d("991", "success");
                        break;
                    case R.id.nav_Home:
                        startActivity(new Intent(HomeCategory.this, HomeScreen.class));
                        //     Toast.makeText(getApplicationContext(), "HOme!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Settings:
                        Log.d("0012", "settings reached");
                        startActivity(new Intent(HomeCategory.this, Settings.class));
                        //finish();
                        //Toast.makeText(getApplicationContext(), "settings!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Categories:
                        Log.d("0012", "Category reached");
                        startActivity(new Intent(HomeCategory.this, Category.class));
                        //finish();
                        //Toast.makeText(getApplicationContext(), "Category!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Favorites:
                        Log.d("0012", "Category reached");
                        startActivity(new Intent(HomeCategory.this, FavouritesActivity.class));
                        //finish();
                        //Toast.makeText(getApplicationContext(), "Favourites!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_HelpContact:
                        Log.d("0012", "Category reached");
                        startActivity(new Intent(HomeCategory.this, Help_Contacts.class));
                        //finish();
                        //Toast.makeText(getApplicationContext(), "Favourites!", Toast.LENGTH_LONG).show();
                        break;


                    // case R.id.nav_about_us:
                    // launch new intent instead of loading fragment
                    // startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    //drawer.closeDrawers();
                    // return true;
                    // case R.id.nav_privacy_policy:
                    // launch new intent instead of loading fragment
                    // startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                    // drawer.closeDrawers();
                    // return true;
                    default:
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        //selectNavMenu();

        // set toolbar title
//        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void loadNavHeader() {
        // name, website
        // txtName.setText("Ravi Tamada");
        // txtWebsite.setText("www.androidhive.info");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
       /* Glide.with(this).load(R.mipmap.contactlist_img_four)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);*/

        // showing dot next to notifications label
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);
    }


}
