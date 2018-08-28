package com.example.daniyal.govava.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.daniyal.govava.Adapters.MenAdapter;
import com.example.daniyal.govava.Helper.ContactsDbHelper;
import com.example.daniyal.govava.Helper.FavouritesDbHelper;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.ContactDetails;
import com.example.daniyal.govava.models.Men_items;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.daniyal.govava.Utils.Contacts_SharedPrefs.getContactsSharedPref;

public class FavouritesActivity extends AppCompatActivity {

    private static final String TAG = "favouritesActivity";
    FavouritesDbHelper favouritesDbHelper;
    List<Men_items> menItemsList;
    ImageView menu_icon;

    Boolean contacts = false;
    Boolean fav = false;

    MenAdapter menAdapter;

    TextView tv_test;

    GridView gridView_favourites;
    EditText ed_fav_search;
    public LinearLayout ll_back;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ed_fav_search = (EditText) findViewById(R.id.ed_fav_search);
        menu_icon = findViewById(R.id.menu_icon);

        preferences = getSharedPreferences("code", MODE_PRIVATE);


        ed_fav_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                menAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(FavouritesActivity.this, menu_icon);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.fav_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_products:
                                //your action
                                menItemsList.clear();
                                getFavouritesData();
                                break;
                            case R.id.action_contacts:
                                getContacts();
                                break;
                            default:
                                return FavouritesActivity.super.onOptionsItemSelected(item);
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });


        gridView_favourites = (GridView) findViewById(R.id.gridView_favourites);

        menItemsList = new ArrayList<>();
        favouritesDbHelper = new FavouritesDbHelper(this);

        contacts = false;
        fav = true;

        menAdapter = new MenAdapter(this, menItemsList, fav, contacts);
        gridView_favourites.setAdapter(menAdapter);
        gridView_favourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (contacts) {
                    getGifts();
                    //Toast.makeText(getApplicationContext(), "in process", Toast.LENGTH_SHORT).show();
                } else {
                    Men_items men_items = menItemsList.get(i);
                    Log.d(TAG, "clicked");
                    Intent intent = new Intent(FavouritesActivity.this, ItemDetails.class);
                    intent.putExtra("men_items", men_items);
                    startActivity(intent);
                }
            }
        });


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ToggleButton toggle_fav = (ToggleButton) findViewById(R.id.toggle_fav);
        //toggle_fav.setEnabled(false);
        //toggle_fav.setVisibility(View.GONE);
        // tv_test = (TextView)findViewById(R.id.tv_test);

        getFavouritesData();
    }

    public void getGifts(){
        SharedPreferences sharedgifts = getSharedPreferences("giftsCode", MODE_PRIVATE);

        if(sharedgifts != null){
            sharedgifts.getString("displayName", null);
            sharedgifts.getString("displayImage", null);
            String phone_number = sharedgifts.getString("phoneNo", null);


               ContactsDbHelper contactsDbHelper = new ContactsDbHelper(FavouritesActivity.this);
               Cursor cursor = contactsDbHelper.getContactGifts(phone_number);
               String name = "";
               String price = "";
               String img = "";
               String serviceName = "";

               while (cursor.moveToNext()) {
                   name = cursor.getString(cursor.getColumnIndex(ContactsDbHelper.COLUMN_NAME));
                   price = cursor.getString(cursor.getColumnIndex(ContactsDbHelper.COLUMN_PRICE));
                   img = cursor.getString(cursor.getColumnIndex(ContactsDbHelper.COLUMN_IMG));
                   serviceName = cursor.getString(cursor.getColumnIndex(ContactsDbHelper.COLUMN_SERVICE_NAME));
               }

               menItemsList.clear();
               menItemsList.add(new Men_items(name, price, img, serviceName));
               menAdapter = new MenAdapter(this, menItemsList, true, false);
                gridView_favourites.setAdapter(menAdapter);
        }
        else
        Toast.makeText(getApplicationContext(), "No gifts selected for this contact !", Toast.LENGTH_SHORT).show();


    }

    public void getFavouritesData() {
        Cursor cursor = favouritesDbHelper.getAllContacts();

        while (cursor.moveToNext()) {
/*
            tv_test.append(cursor.getString(0));
            tv_test.append(cursor.getString(1));
            tv_test.append(cursor.getString(2));*/

            menItemsList.add(new Men_items(cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4)));
        }

        contacts = false;
        fav = true;

        menAdapter = new MenAdapter(this, menItemsList, fav, contacts);
        gridView_favourites.setAdapter(menAdapter);
        //menAdapter.notifyDataSetChanged();
        cursor.getCount();
    }

    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void getContacts() {
        String displayName = "";
        String img = "";
        String bitmapToString = "";

        menItemsList.clear();
        ArrayList<ContactDetails> arrayList;

        //ContactDetails contactDetails = getCompanyDetailsSharedPref(getApplicationContext());
        ArrayList<ContactDetails> contactDetailsArrayList = getContactsSharedPref(getApplicationContext());

        for (int i = 0; i < contactDetailsArrayList.size(); i++) {
            menItemsList.add(new Men_items(contactDetailsArrayList.get(i).name, "", contactDetailsArrayList.get(i).image, ""));

        }

        contacts = true;
        fav = true;

        menAdapter = new MenAdapter(this, menItemsList, fav, contacts);
        gridView_favourites.setAdapter(menAdapter);
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
