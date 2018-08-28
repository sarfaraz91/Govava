package com.example.daniyal.govava.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.daniyal.govava.models.ContactDetails;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AST on 8/21/2018.
 */

public class Contacts_SharedPrefs {

    public static void saveContactSharedPref(Context mContext, ArrayList<ContactDetails> contactDetailsArrayList, ContactDetails contactDetails) {
        SharedPreferences mPrefs = mContext.getSharedPreferences("newcode", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
       // prefsEditor.clear().commit();
        Gson gson = new Gson();
        if(getContactsSharedPref(mContext) != null) {
            ArrayList<ContactDetails> contactDetailsArrayListold = getContactsSharedPref(mContext);

            contactDetailsArrayList.addAll(contactDetailsArrayListold);
            //ArrayList<ContactDetails> contactDetailsArrayListnew = getCompanyDetailsSharedPref(mContext);
            //contactDetailsArrayList.add(contactDetails);

            Boolean isNewContact = true;
            for(int i=0; i<contactDetailsArrayListold.size(); i++){
                if(contactDetailsArrayListold.get(i).name.matches(contactDetails.name)){
                   isNewContact = false;
                }
            }

            if(isNewContact){
                contactDetailsArrayList.add(contactDetails);
            }

        }

        String json = gson.toJson(contactDetailsArrayList);
        prefsEditor.putString("contactssecret", json);
        prefsEditor.commit();
    }

    public static ArrayList<ContactDetails> getContactsSharedPref(Context mContext) {
        SharedPreferences mPrefs = mContext.getSharedPreferences("newcode", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = "";
        if(mPrefs.contains("contactssecret")) {

            json = mPrefs.getString("contactssecret", "");
        }
            if (json.equalsIgnoreCase("")) {
                return null;
            }
            ContactDetails array[] = gson.fromJson(json, ContactDetails[].class);

            return new ArrayList<ContactDetails>(Arrays.asList(array));

    }

}
