package com.example.daniyal.govava.fragment;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daniyal.govava.HomeFragments;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.ContactDetails;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.daniyal.govava.Utils.Contacts_SharedPrefs.saveContactSharedPref;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;


public class WhatToBuyFRagment0 extends Fragment {

    RelativeLayout rl_openContactList;
    ImageView CoverImage, continueNext;
    EditText ed_contact_name;

    SharedPreferences.Editor sEditor;
    String display_image = "";
    String display_name = "";
    String phone_no = "";
    public static final int CONTACT_PICKER_RESULT = 1234;
    ArrayList<ContactDetails> contactDetailsArrayList = new ArrayList<>();
    ContactDetails contactDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_copy_what_to_buy_fragment2, container, false);
        CoverImage = view.findViewById(R.id.CoverImage);


        sEditor = getActivity().getSharedPreferences("code", MODE_PRIVATE).edit();

        ed_contact_name = view.findViewById(R.id.ed_contact_name);
        //mPager.setCurrentItem(1);
        continueNext = view.findViewById(R.id.continueNext);

        final ViewPager viewPager = ((HomeFragments) getActivity()).mPager;

        continueNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(TextUtils.isEmpty(ed_contact_name.getText()) || CoverImage.getDrawable() == null)) {

                    saveContactSharedPref(getActivity(), contactDetailsArrayList, contactDetails);

                    //ContactDetails contactDetails = new ContactDetails(display_name,display_image);
                    ((HomeFragments)getActivity()).displayName = display_name;
                    ((HomeFragments)getActivity()).displayImage = display_image;
                    ((HomeFragments)getActivity()).phoneNo = phone_no;

                    viewPager.setCurrentItem(1);
                    // getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").add(android.R.id.content, new WhatToBuyFragment()).commit();
                } else if (TextUtils.isEmpty(ed_contact_name.getText()))
                    ed_contact_name.setError("Contact name is required.");
                else if (CoverImage.getDrawable() == null)
                    Toast.makeText(getActivity(), "Image cannot be empty", Toast.LENGTH_SHORT).show();

            }
        });

        rl_openContactList = view.findViewById(R.id.rl_openContactList);
        ed_contact_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(intent, CONTACT_PICKER_RESULT);

            }
        });


        return view;

    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    //  final EditText phoneInput = (EditText) findViewById(R.id.phoneNumberInput);
                    Cursor cursor = null;
                    String phoneNumber = "";
                    String photo = "";

                    int photo_uri = 0;
                    int contact_name = 0;
                    List<String> allNumbers = new ArrayList<String>();
                    int phoneIdx = 0;
                    try {
                        Uri result = data.getData();
                        String id = result.getLastPathSegment();
                        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                        phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        photo_uri = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
                        contact_name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        if (cursor.moveToFirst()) {
                            while (cursor.isAfterLast() == false) {
                                phoneNumber = cursor.getString(phoneIdx);
                                photo = cursor.getString(photo_uri);
                                display_name = cursor.getString(contact_name);
                                allNumbers.add(phoneNumber);
                                cursor.moveToNext();
                            }
                        } else {
                            //no results actions
                        }
                    } catch (Exception e) {
                        //error actions
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }

                        if (display_name != null) {
                            ed_contact_name.setText(display_name);
                            // sEditor.putString("displayNamePreference",display_name);

                            ed_contact_name.setError(null);
                        }
                        Bitmap bitmap;

                        /*else
                            ed_contact_name.setText("");*/
                        if (photo != null) {
                            Glide.with(getActivity()).load(photo).into(CoverImage);

                            Uri myUri = Uri.parse(photo);

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), myUri);
                                display_image = BitMapToString(bitmap);

                                //sEditor.putString("imagePreference", encodeTobase64(bitmap));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            CoverImage.setImageResource(R.drawable.profile_pic);
                        }

                        //ArrayList<ContactDetails> arrayList = new ArrayList<>();


                        contactDetails = new ContactDetails(display_name, display_image);
                      /*  Gson gson = new Gson();
                        String jsonFavorites = gson.toJson(contactDetails);
                        sEditor.putString("contactscode", jsonFavorites);
                        sEditor.commit();*/

                        //sEditor.commit();

                        final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Choose a number");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                String selectedNumber = items[item].toString();
                                selectedNumber = selectedNumber.replace("-", "");
                                phone_no = selectedNumber;
                                // phoneInput.setText(selectedNumber);
                            }
                        });
                        AlertDialog alert = builder.create();
                        if (allNumbers.size() > 1) {
                            alert.show();
                        } else {
                            String selectedNumber = phoneNumber.toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            phone_no = selectedNumber;
                            // phoneInput.setText(selectedNumber);
                        }

                        if (phoneNumber.length() == 0) {
                            //no numbers found actions
                        }
                    }
                    break;
            }
        } else {
            //activity result error actions
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
