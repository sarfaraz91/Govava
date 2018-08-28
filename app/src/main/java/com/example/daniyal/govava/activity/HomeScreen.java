package com.example.daniyal.govava.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.daniyal.govava.Adapters.Home_rv_Adapter;
import com.example.daniyal.govava.HomeFragments;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.GsonUtils;
import com.example.daniyal.govava.models.Home_RecyclerView;
import com.example.daniyal.govava.models.Men_items;
import com.example.daniyal.govava.models.User_Info;
import com.example.daniyal.govava.Utils.CircleTransform;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.daniyal.govava.activity.MainActivity.navItemIndex;

public class HomeScreen extends AppCompatActivity {

    TextView HomeShowmore, Handpicked, Categories;
    ImageView imageView,image1,image3,image2;
    RelativeLayout iv_hamburger_icon;
    EditText ed_search;

    Context context =this;

    RecyclerView rv_horizontal;

    LinearLayout ll_hamburger;


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, EditProfile;
    private Toolbar toolbar;

    private static final String TAG_HOME = "home";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    public Men_items men_items,men_items2,men_items3;

    ImageView HomeMen;

    Home_rv_Adapter home_rv_adapter;
    List<Home_RecyclerView> home_recyclerViewList;



   private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";

   private final String showMoreItems_url = "http://api.walmartlabs.com/v1/trends?apiKey=n857fahjjqksk3pey8ew78np&format=json";

    TextView tv_price,tv_name,tv_service,tv_price2,tv_name2,tv_service2,tv_price3,tv_name3,tv_service3;

    public ProgressDialog progressDialog;
    public RelativeLayout clear_text;
    FirebaseAuth firebaseAuth;
   DatabaseReference databaseReference;
   public RelativeLayout rl_img2,container_2,container_3;
   String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_name = (TextView)findViewById(R.id.tv_name);
        //tv_service = (TextView)findViewById(R.id.tv_service);
        tv_price2 = (TextView)findViewById(R.id.tv_price2);
        tv_name2 = (TextView)findViewById(R.id.tv_name2);
       // tv_service2 = (TextView)findViewById(R.id.tv_service2);
        tv_price3 = (TextView)findViewById(R.id.tv_price3);
        tv_name3 = (TextView)findViewById(R.id.tv_name3);
        //tv_service3 = (TextView)findViewById(R.id.tv_service3);
        clear_text = (RelativeLayout)findViewById(R.id.clear_text);
        rl_img2 = (RelativeLayout)findViewById(R.id.rl_img2);
        container_2 = (RelativeLayout)findViewById(R.id.container_2);
        container_3 = (RelativeLayout)findViewById(R.id.container_3);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        getItemsFromService();
/*
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Handpicked.setTypeface(typeface3);
        Categories.setTypeface(typeface3);
        HomeShowmore.setTypeface(typeface3);
*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        EditProfile = (TextView) navHeader.findViewById(R.id.TextView_EditProfile);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);


        //if(firebaseUser.getUid() != null) {

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Info user_info = dataSnapshot.getValue(User_Info.class);
                name = user_info.getFullName();

                if(!TextUtils.isEmpty(name))
                {
                    txtName.setText(name);
                }
                String profile_img = user_info.getPhotoUrl();

                if(!TextUtils.isEmpty(profile_img)){
                    Glide.with(context).load(profile_img).crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(getApplicationContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgProfile);
                }else{
                    Glide.with(context).load(R.mipmap.contactlist_img_four)
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(context))
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
        if(!TextUtils.isEmpty(userEmail)) {
            EditProfile.setText(userEmail);
        }
        // }


        User_Info fbData;

       /* if(getIntent().hasExtra("fbData")) {
            fbData = (User_Info) getIntent().getSerializableExtra("fbData");

            Log.d("000888","fbData : "+fbData);
            String name = fbData.getFullName();
            txtName.setText(name);

            String email = fbData.getEmail();
            EditProfile.setText(email);

            String img = fbData.getPhotoUrl();
            Log.d(TAG_HOME,"img : "+img);


            Glide.with(context).load(img).crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgProfile);

        }*//*else{
            databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User_Info user_info = dataSnapshot.getValue(User_Info.class);
                    name = user_info.getFullName();
                    Log.d("home", "user_info " + user_info);
                    txtName.setText(name);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            String userId = firebaseUser.getUid();
            String userEmail = firebaseUser.getEmail();
            EditProfile.setText(userEmail);

             Glide.with(this).load(R.mipmap.contactlist_img_four)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        }*/

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_search.setText("");
            }
        });


        ed_search = (EditText)findViewById(R.id.ed_search);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                home_rv_adapter.getFilter().filter(ed_search.getText().toString().replace(" ",""));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
//your code

                Intent intent = new Intent(HomeScreen.this,SearchActivity.class);
                startActivity(intent);

                }
                return false;
            }
        });

        HomeShowmore = (TextView) findViewById(R.id.HomeShowmore);

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




        home_rv_adapter = new Home_rv_Adapter(this,home_recyclerViewList);

        rv_horizontal = (RecyclerView)findViewById(R.id.rv_horizontal) ;
        rv_horizontal.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_horizontal.setAdapter(home_rv_adapter);




        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);

        image1 = (ImageView)findViewById(R.id.image1);

        image2 = (ImageView)findViewById(R.id.image2);
//        image2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,
//                        "Under Progress !", Toast.LENGTH_LONG).show();
//            }
//        });
        image3 = (ImageView)findViewById(R.id.image3);
//        image3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,
//                        "Under Progress !", Toast.LENGTH_LONG).show();
//            }
//        });



        //  HomeMen = (ImageView)findViewById(R.id.HomeMen);

        loadNavHeader();
        setUpNavigationView();

        iv_hamburger_icon = (RelativeLayout) findViewById(R.id.iv_hamburger_icon);
        iv_hamburger_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        HomeShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , ShowMoreItems.class);
                startActivity(intent);
            }
        });

        rl_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, ItemDetails.class).putExtra("men_items",men_items));
            }
        });

        container_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, ItemDetails.class).putExtra("men_items",men_items2));

            }
        });


        container_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeScreen.this, ItemDetails.class).putExtra("men_items",men_items3));

            }
        });



       /* HomeMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , Men_Cloths.class);
                startActivity(intent);

            }
        });
*/
        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();


    }

    public void getItemsFromService()
    {
        Log.d(TAG_HOME,"getItemsFromService success : ");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showMoreItems_url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray itemJsonArray = jsonObject.getJSONArray("items");
                    Log.d(TAG_HOME,"response : "+response);

                    JSONObject jsonObjectRandom1 = itemJsonArray.getJSONObject(new Random().nextInt(itemJsonArray.length()));
                    JSONObject jsonObjectRandom2 = itemJsonArray.getJSONObject(new Random().nextInt(itemJsonArray.length()));
                    JSONObject jsonObjectRandom3 = itemJsonArray.getJSONObject(new Random().nextInt(itemJsonArray.length()));

                     men_items = GsonUtils.fromJSON(jsonObjectRandom1,Men_items.class);

                    Glide.with(getApplicationContext()).load(jsonObjectRandom1.get("largeImage"))
                            .into(image1);
                //    jsonObjectRandom1.get("msrp");
                   // jsonObjectRandom1.get("price");
                    tv_name.setText(jsonObjectRandom1.get("name").toString());
                    if(jsonObjectRandom1.has("msrp")){
                        tv_price.setText("$ " + jsonObjectRandom1.get("msrp").toString());
                    }else {
                        tv_price.setText("$ " + jsonObjectRandom1.get("salePrice").toString());
                    }
                    //tv_service.setText("At Walmart");

                    Glide.with(getApplicationContext()).load(jsonObjectRandom2.get("largeImage"))
                            .into(image2);

                    tv_name2.setText(jsonObjectRandom2.get("name").toString());
                 //   tv_price2.setText("$ "+jsonObjectRandom2.get("msrp").toString());
                    if(jsonObjectRandom1.has("msrp")){
                        tv_price2.setText("$ " + jsonObjectRandom2.get("msrp").toString());
                    }else {
                        tv_price2.setText("$ " + jsonObjectRandom2.get("salePrice").toString());
                    }
                    //tv_service2.setText("At Walmart");

                    Glide.with(getApplicationContext()).load(jsonObjectRandom3.get("largeImage"))
                            .into(image3);

                    tv_name3.setText(jsonObjectRandom3.get("name").toString());
                 //   tv_price3.setText("$ "+jsonObjectRandom3.get("msrp").toString());
                    if(jsonObjectRandom1.has("msrp")){
                        tv_price3.setText("$ " + jsonObjectRandom3.get("msrp").toString());
                    }else {
                        tv_price3.setText("$ " + jsonObjectRandom3.get("salePrice").toString());
                    }
                   // tv_service3.setText("At Walmart");


                    men_items3 = GsonUtils.fromJSON(jsonObjectRandom3,Men_items.class);
                    men_items2 = GsonUtils.fromJSON(jsonObjectRandom2,Men_items.class);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG_HOME,"error : "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
    //    getItemsFromService();
    }





    @Override
    protected void onStop() {
        super.onStop();
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

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
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

    public void clickEventSlide() {
        drawer.openDrawer(Gravity.START);
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
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
                    case R.id.nav_Pets:
                        Log.d("0012","pets reached");
                        startActivity(new Intent(HomeScreen.this, Pets_Activity.class));
                        //Toast.makeText(getApplicationContext(), "nav_pets!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_WhatToBuy:
                        Log.d("0012","fragment reached");

                        startActivity(new Intent(HomeScreen.this, HomeFragments.class));
                        Log.d("991","success");
                        break;
                    case R.id.nav_Home:
                        //startActivity(new Intent(HomeScreen.this,WhatToBuyFragment.class));
                        //Toast.makeText(getApplicationContext(), "HOme!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Settings:
                        Log.d("0012","settings reached");
                        startActivity(new Intent(HomeScreen.this,Settings.class));
                        //Toast.makeText(getApplicationContext(), "settings!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Categories:
                        Log.d("0012","Category reached");
                        startActivity(new Intent(HomeScreen.this,Category.class));
                        //Toast.makeText(getApplicationContext(), "Category!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Favorites:
                        Log.d("0012","Category reached");
                        startActivity(new Intent(HomeScreen.this,FavouritesActivity.class));
                        //Toast.makeText(getApplicationContext(), "Favourites!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_HelpContact:
                        Log.d("0012","Category reached");
                        startActivity(new Intent(HomeScreen.this,Help_Contacts.class));
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

    @Override
    public void onBackPressed() {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeScreen/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        Log.d("id is",""+id);
        //noinspection SimplifiableIfStatement
//        if (id == R.id.nav_Pets) {
////            startActivity(new Intent(HomeScreen.this,Pets.class));
////            Toast.makeText(getApplicationContext(), "nav_pets!", Toast.LENGTH_LONG).show();
//            return true;
//        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }




    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Log.d("id is",""+id);

//        if (id == R.id.nav_Pets) {
//            Log.d("id is",""+id);
//            Toast.makeText(getApplicationContext(), "All pets", Toast.LENGTH_LONG).show();
//            // Handle the camera action
//        }
         if (id == R.id.nav_Home) {
            Toast.makeText(getApplicationContext(), "All HomeScreen", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
