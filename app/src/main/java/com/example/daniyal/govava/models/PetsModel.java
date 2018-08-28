package com.example.daniyal.govava.models;

/**
 * Created by Pc on 4/5/2018.
 */

public class PetsModel {

    private String petsName;
    private int petsImages;

    public PetsModel(String petsName, int petsImages) {
        this.petsName = petsName;
        this.petsImages = petsImages;
    }

    public String getPetsName() {
        return petsName;
    }

    public int getPetsImages() {
        return petsImages;
    }
}
