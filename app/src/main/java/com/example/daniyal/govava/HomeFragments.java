package com.example.daniyal.govava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.daniyal.govava.activity.HomeCategory;
import com.example.daniyal.govava.activity.Settings;
import com.example.daniyal.govava.fragment.WhatToBuyFRagment0;
import com.example.daniyal.govava.fragment.WhatToBuyFragment;
import com.example.daniyal.govava.fragment.WhatToBuyFragment2;
import com.example.daniyal.govava.fragment.WhatToBuyFragment3;
import com.example.daniyal.govava.fragment.WhatToBuyFragment4;
import com.example.daniyal.govava.Utils.CircleTransform;
import com.example.daniyal.govava.models.ContactDetails;

import java.util.ArrayList;

import static com.example.daniyal.govava.activity.MainActivity.navItemIndex;

public class HomeFragments extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    TextView HomeShowmore, Handpicked, Categories;
    ImageView imageView, iv_hamburger_icon, image1, image3, image2;

    Context context = this;


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, EditProfile;
    private Toolbar toolbar;

    private static final String TAG_HOME = "home";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    ImageView HomeMen;
    public ArrayList<Fragment> fragmentArrayList;
    public WhatToBuyFragment whatToBuyFragment;
    public WhatToBuyFragment2 whatToBuyFragment2;
    public WhatToBuyFragment3 whatToBuyFragment3;
    public WhatToBuyFragment4 whatToBuyFragment4;
    public WhatToBuyFRagment0 whatToBuyFRagment0;
    public String s1 = "";
    public String s2 = "";
    public String s3_to = "";
    public String s3_from = "";
    public String s4 = "";

    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";

    public String displayName,displayImage,phoneNo;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragments);



        whatToBuyFragment = new WhatToBuyFragment();
        whatToBuyFragment2 = new WhatToBuyFragment2();
        whatToBuyFragment3 = new WhatToBuyFragment3();
        whatToBuyFragment4 = new WhatToBuyFragment4();

        whatToBuyFRagment0 = new WhatToBuyFRagment0();

        mPager = (ViewPager)findViewById(R.id.pager);
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(whatToBuyFRagment0);
        fragmentArrayList.add(whatToBuyFragment);
        fragmentArrayList.add(whatToBuyFragment2);
        fragmentArrayList.add(whatToBuyFragment3);
        fragmentArrayList.add(whatToBuyFragment4);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragmentArrayList);
        mPager.setAdapter(mPagerAdapter);




/*
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Handpicked.setTypeface(typeface3);
        Categories.setTypeface(typeface3);
        HomeShowmore.setTypeface(typeface3);
*/

        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fl_forFragment, new WhatToBuyFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
*/



        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_forFragment, new WhatToBuyFragment());
        fragmentTransaction.commit();
*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        EditProfile = (TextView) navHeader.findViewById(R.id.TextView_EditProfile);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);



        //  HomeMen = (ImageView)findViewById(R.id.HomeMen);

        loadNavHeader();
        setUpNavigationView();


        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();


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
        Glide.with(this).load(R.mipmap.contactlist_img_four)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

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
//                    case R.id.nav_Pets:
//                        Log.d("0012", "pets reached");
//                        startActivity(new Intent(HomeFragments.this, Pets_Activity.class));
//                        Toast.makeText(getApplicationContext(), "nav_pets!", Toast.LENGTH_LONG).show();
//                        break;

                    case R.id.nav_WhatToBuy:
                        Log.d("0012", "fragment reached");
                        startActivity(new Intent(HomeFragments.this, WhatToBuyFragment.class));
                        Log.d("991", "success");
                        Toast.makeText(getApplicationContext(), "what to buy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Home:
                        //startActivity(new Intent(HomeScreen.this,WhatToBuyFragment.class));
                        Toast.makeText(getApplicationContext(), "HOme!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Settings:
                        Log.d("0012", "settings reached");
                        startActivity(new Intent(HomeFragments.this, Settings.class));
                        Toast.makeText(getApplicationContext(), "settings!", Toast.LENGTH_LONG).show();
                        break;

                  /*  case R.id.nav_WhatToBuy:
                        startActivity(new Intent(HomeScreen.this,WhatToBuyFragment.class));
                        Toast.makeText(getApplicationContext(), "what to buy!", Toast.LENGTH_LONG).show();
                        break;
                  */
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
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


        Log.d("id is", "" + id);
        //noinspection SimplifiableIfStatement
//        if (id == R.id.nav_Pets) {
////            startActivity(new Intent(HomeFragments.this, Pets.class));
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


        Log.d("id is", "" + id);

//        if (id == R.id.nav_Pets) {
//            Log.d("id is", "" + id);
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<Fragment> fragmentArrayList;

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public void jumpToPage1(View view){
        mPager.setCurrentItem(1);
    }

    public void jumpToPage(View view) {
        mPager.setCurrentItem(2);
     //   Toast.makeText(HomeFragments.this,""+whatToBuyFragment.getText(),Toast.LENGTH_SHORT).show();
        s1 = whatToBuyFragment.getText();
        if(s1.contains("(Infant)")){
            s1 = s1.replace("(Infant)","");
        }
        String s[] = s1.split("-");
        s1 = s[1];
    }
    public void jumpToPage2(View view) {
        mPager.setCurrentItem(3);
     //   Toast.makeText(HomeFragments.this,""+whatToBuyFragment2.getText(),Toast.LENGTH_SHORT).show();
        s2 = whatToBuyFragment2.getText();

    }
    public void jumpToPage3(View view) {
        mPager.setCurrentItem(4);
     //   Toast.makeText(HomeFragments.this,""+whatToBuyFragment3.getText(),Toast.LENGTH_SHORT).show();
        s3_to = whatToBuyFragment3.getText();
        if(s3_to.contains("More")){
         s3_to = "";
        }else {
            String s[] = s3_to.split("-");
            s3_to = s[1];
            s3_from = s[0];
        }
    }

    public void jumpToPage4(View view) {
     //   Toast.makeText(HomeFragments.this,""+whatToBuyFragment4.getText(),Toast.LENGTH_SHORT).show();
        s4 = whatToBuyFragment4.getText();
        if(s4.contains("/")){
            String s[] = s4.split("/");
            s4= s[1];
        }

        SharedPreferences.Editor mysharedPrefsGifts = context.getSharedPreferences("giftsCode",MODE_PRIVATE).edit();
        mysharedPrefsGifts.putString("displayName",displayName);
        mysharedPrefsGifts.putString("displayImage",displayImage);
        mysharedPrefsGifts.putString("phoneNo",phoneNo);
        mysharedPrefsGifts.commit();

        Intent intent = new Intent(HomeFragments.this, HomeCategory.class);
        intent.putExtra("CategoryName","age "+s1+" "+s2+" "+s4);
        intent.putExtra("CategoryHeader","Buy");
        intent.putExtra("range_from",s3_from);
        intent.putExtra("range_to",s3_to);
        intent.putExtra("displayName",displayName);
        intent.putExtra("displayImage",displayImage);
        intent.putExtra("phoneNo",phoneNo);

        startActivity(intent);
    }

}