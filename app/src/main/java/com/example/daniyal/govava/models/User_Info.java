package com.example.daniyal.govava.models;

import java.io.Serializable;

/**
 * Created by Pc on 4/16/2018.
 */

public class User_Info implements Serializable{

    private String fullName,email,password,address,phone;
    private String uid;
    private String photoUrl;

    public User_Info(String uid,String fullName, String email, String password, String address, String phone,String photoUrl) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.uid = uid;
     }

     public User_Info()
     {

     }

    public User_Info(String fullName,String email,String photoUrl)
    {
        this.email = email;
        this.fullName = fullName;
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }
}
