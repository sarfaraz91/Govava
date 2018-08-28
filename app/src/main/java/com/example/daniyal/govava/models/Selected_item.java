package com.example.daniyal.govava.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pc on 3/30/2018.
 */

public class Selected_item implements Parcelable {

    private ArrayList<SubClass> items;

    public Selected_item(ArrayList<SubClass> items) {
        this.items = items;
    }


    protected Selected_item(Parcel in) {
        items = in.createTypedArrayList(SubClass.CREATOR);
    }

    public static final Creator<Selected_item> CREATOR = new Creator<Selected_item>() {
        @Override
        public Selected_item createFromParcel(Parcel in) {
            return new Selected_item(in);
        }

        @Override
        public Selected_item[] newArray(int size) {
            return new Selected_item[size];
        }
    };

    public ArrayList<SubClass> getItems() {
        return items;
    }

    public void setItems(ArrayList<SubClass> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(items);
    }


    public static class SubClass implements Parcelable{

    private  String salePrice, name , mediumImage;


    public SubClass(String salePrice, String name, String mediumImage ) {
        this.mediumImage = mediumImage;
        this.salePrice = salePrice;
        this.name = name;
    }

        protected SubClass(Parcel in) {
            salePrice = in.readString();
            name = in.readString();
            mediumImage = in.readString();
        }

        public static final Creator<SubClass> CREATOR = new Creator<SubClass>() {
            @Override
            public SubClass createFromParcel(Parcel in) {
                return new SubClass(in);
            }

            @Override
            public SubClass[] newArray(int size) {
                return new SubClass[size];
            }
        };

        public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(salePrice);
            parcel.writeString(name);
            parcel.writeString(mediumImage);
        }
    }
}