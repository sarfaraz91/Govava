package com.example.daniyal.govava.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AST on 5/3/2018.
 */

public class Recent_S_SharedPref  {

    private static List<String> search_strings;





    public static void setRecentSearch(Context context, ArrayList<String> arrayList,String s){
        SharedPreferences settings;
        boolean flag = false;
        settings = context.getSharedPreferences("code",Context.MODE_PRIVATE);
        Gson gson = new Gson();

        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).equals(s)){
                flag = true;
            }
        }
        if(flag) {
           /* if (s.equals("")) {
                String jsonFavorites = gson.toJson(arrayList);
                settings.edit().putString("rcode", jsonFavorites).apply();
            }*/
            arrayList.remove(s);

        }
        arrayList.add(0,s);
        String jsonFavorites = gson.toJson(arrayList);
        settings.edit().putString("rcode", jsonFavorites).apply();

    }

    public static ArrayList getRecentSearch(Context context) {
// used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        settings = context.getSharedPreferences("code",Context.MODE_PRIVATE);
//        recieptPrefs = new ArrayList<>();
        if (settings.contains("rcode")) {
            String jsonFavorites = settings.getString("rcode", null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites,String[].class);
            search_strings =  new ArrayList<>(Arrays.asList(favoriteItems));
            // RecieptPref[] pref = favoriteItems;
//            recieptPrefs = new ArrayList(recieptPrefs);
//              recieptPrefs = new ArrayList(favoriteItems);
        } else{
            return null;
        }

        return (ArrayList) search_strings;
    }
}
