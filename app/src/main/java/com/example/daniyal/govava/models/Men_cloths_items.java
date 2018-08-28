package com.example.daniyal.govava.models;

/**
 * Created by Pc on 4/3/2018.
 */

public class Men_cloths_items {

    private String coll_title;
    private int coll_img;


    public Men_cloths_items(int coll_img,String coll_title) {
        this.coll_title = coll_title;
        this.coll_img = coll_img;
    }

    public String getColl_title() {
        return coll_title;
    }

    public int getColl_img() {
        return coll_img;
    }
}
