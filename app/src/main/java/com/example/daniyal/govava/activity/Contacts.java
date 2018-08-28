package com.example.daniyal.govava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.daniyal.govava.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);





      /*  AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://api.walmartlabs.com/v1/search?apiKey=n857fahjjqksk3pey8ew78np&query=Dog&numItems=25&start=1", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"

                String str = new String(response);

                Log.d("values Are ", str);

                try {

                    JSONObject jsonObject = new JSONObject(str);

                    String name = jsonObject.getString("salePrice");

                    Log.d("values Are from json", name);

                } catch (JSONException e) {
              //              Log.d("values Are from json", name);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("values Are from json", e.getMessage());
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

*/

    }
}
