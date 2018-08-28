package com.example.daniyal.govava.models;

/**
 * Created by Pc on 4/4/2018.
 */

public class Cart_addMore {

    private int prod_img;
    private String title,desc,price,saved_price;

    public Cart_addMore(int prod_img, String title, String desc, String price, String saved_price) {
        this.prod_img = prod_img;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.saved_price = saved_price;
    }

    public int getProd_img() {
        return prod_img;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public String getSaved_price() {
        return saved_price;
    }
}
