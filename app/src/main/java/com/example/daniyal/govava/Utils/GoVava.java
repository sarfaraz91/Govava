package com.example.daniyal.govava.Utils;

import android.app.Application;
import android.content.Context;

import com.example.daniyal.govava.HttpUtils.HttpRequestHandler;
//import android.support.multidex.MultiDex;
//
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
//import com.twitter.sdk.android.Twitter;
//import com.twitter.sdk.android.core.TwitterAuthConfig;
//import io.fabric.sdk.android.Fabric;


public class GoVava extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
//    private static final String TWITTER_KEY = "62UstOrcpGR1U2WlMGszbepZB";
//    private static final String TWITTER_SECRET = "AjPVSiK7wnwifj4Kuz40YdrqZHkGD9fZAJEW4HAIQCF46Ae2NB";


    public static final String TAG = GoVava.class.getSimpleName();

    private static Context context;
    private static GoVava mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        mInstance = this;
        context = this.getApplicationContext();
//        SystemClock.sleep(2000);
//        FacebookSdk.sdkInitialize(context);
//        AppEventsLogger.activateApp(this);
        HttpRequestHandler.setAndroidContext(this);

        //Overriding default fonts
//        FontsOverride.setDefaultFont(this, "MONOSPACE", "Helvetica45Default.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "Helvetica47.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "HelveticaNeue-CondensedBold.otf");

//        catchAllUncaughtException();
    }

    public static synchronized GoVava getInstance() {
        return mInstance;
    }
    public static Context getContext(){
        return context;
    }

}
