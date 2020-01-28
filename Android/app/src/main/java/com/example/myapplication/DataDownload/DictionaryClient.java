package com.example.myapplication.DataDownload;

import android.util.Log;
import com.example.myapplication.Constants;

import com.loopj.android.http.*;
import static com.example.myapplication.Constants.Network.getAbsoluteUrl;

import static com.example.myapplication.Constants.Network.authDigest;



public class DictionaryClient {
//    private static final String BASE_URL = "http://10.0.2.2:8000/";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("Authorization", AUTH_KEY);
        client.addHeader("Authorization", authDigest());
        Log.d("HEADER", "AUTH LOADED");
        Log.d("URL", getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }



}
