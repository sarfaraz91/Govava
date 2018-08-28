package com.example.daniyal.govava.models;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Pc on 4/4/2018.
 */

public class Home_RecyclerView {

    private int rv_img;
    private String categoryName,changedCategoryName;

    public Home_RecyclerView(int rv_img, String categoryName,String changedCategoryName) {
        this.rv_img = rv_img;
        this.categoryName = categoryName;
        this.changedCategoryName = changedCategoryName;
    }

    public String getChangedCategoryName() {
        return changedCategoryName;
    }

    public int getRv_img() {
        return rv_img;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
