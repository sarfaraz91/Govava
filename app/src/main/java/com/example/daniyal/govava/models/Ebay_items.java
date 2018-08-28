package com.example.daniyal.govava.models;

import java.io.Serializable;

/**
 * Created by Pc on 4/11/2018.
 */

public class Ebay_items implements Serializable{


    private String itemId,title,galleryURL;


    public Ebay_items(String itemId, String title, String galleryURL) {
        this.itemId = itemId;
        this.title = title;
        this.galleryURL = galleryURL;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGalleryURL() {
        return galleryURL;
    }

    public void setGalleryURL(String galleryURL) {
        this.galleryURL = galleryURL;
    }
}
