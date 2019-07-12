package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;



public class SearchAdaptor {
    JSONObject pack;


    public JSONObject Search (String translation){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(translation, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

//            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    pack=new JSONObject(new String(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });


        return null;

    }

}
