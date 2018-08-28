package com.example.daniyal.govava.HttpUtils;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class HttpRequestHandler {

    private static final String TAG = "HttpRequestHandler";
    private static HttpRequestHandler httpRequestHandler;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private ImageLoader imageLoader;

    private HttpRequestHandler(){
    }

    public static void setAndroidContext(Context context){
        mContext = context;
    }

    public static HttpRequestHandler getInstance()throws RuntimeException {
        if(mContext==null){
            throw new RuntimeException("Context is not set, call HttpRequestHandler.setAndroidContext(context) first");
        }
        if(httpRequestHandler==null){
            httpRequestHandler = new HttpRequestHandler();
        }
        return httpRequestHandler;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//resend req after time incresed to 20sec from 2.5sec
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
