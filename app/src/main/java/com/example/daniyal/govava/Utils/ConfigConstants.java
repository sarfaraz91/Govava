package com.example.daniyal.govava.Utils;

import java.util.HashMap;

public class ConfigConstants {

    public static final String API_BASE_URL = "http://api.walmartlabs.com/v1/";
    public static final boolean PRINT_LOGS = true;

    public static final HashMap<String, String> RESPONSE_CODE = new HashMap<String,String>();
    static {
        RESPONSE_CODE.put("001","Successful login");
        RESPONSE_CODE.put("002","Successful User Registeration");
        RESPONSE_CODE.put("003","User Updated");
        RESPONSE_CODE.put("004","Reset request has been mailed to you");
        RESPONSE_CODE.put("101","Incorrect Phone Number/Password");
        RESPONSE_CODE.put("102","Email Already Exist");
        RESPONSE_CODE.put("1021","Phone Already Exist");
        RESPONSE_CODE.put("802","Review is submitted");
        RESPONSE_CODE.put("801","Review not submitted please Try again");
        RESPONSE_CODE.put("104","You Are not Register Customer");
        RESPONSE_CODE.put("201","Order Request Has been Sent");
        RESPONSE_CODE.put("301","Order Submittion Failed");
        RESPONSE_CODE.put("302","Your balance is not enough for this Order");
        RESPONSE_CODE.put("503","Feedback Submitted");
        RESPONSE_CODE.put("303","Session Expire");
        RESPONSE_CODE.put("602","No Order found");
    }

}
