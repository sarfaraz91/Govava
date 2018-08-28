package com.example.daniyal.govava.HttpUtils;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyal.govava.Listener.ServiceListener;
import com.example.daniyal.govava.Utils.ConfigConstants;

import org.json.JSONObject;

//import cz.msebera.android.httpclient.protocol.HttpRequestHandler;

public class RestAPI {

    public static void PostRequest(String TAG, String apiEndpoint, JSONObject jsonObj, final ServiceListener<JSONObject, VolleyError> listener) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ConfigConstants.API_BASE_URL + apiEndpoint,
                jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest,TAG);
    }

    public static void PostXMLRequest(String TAG, String apiEndpoint, JSONObject jsonObj, final ServiceListener<JSONObject, VolleyError> listener) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ConfigConstants.API_BASE_URL + apiEndpoint,
                jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.success(response);
                Log.d("0011", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/xml; charset=" +
                        getParamsEncoding();
            }
        };

        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest,TAG);
    }

    public static void GetRequest(String TAG, String apiEndpoint, final ServiceListener<JSONObject, VolleyError> listener){

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ConfigConstants.API_BASE_URL + apiEndpoint,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });

        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest,TAG);
    }
}
