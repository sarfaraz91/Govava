package com.example.daniyal.govava.models;

import java.io.Serializable;

/**
 * Created by AST on 8/20/2018.
 */

public class ContactDetails implements Serializable {

    public String name,image;

    public ContactDetails(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
