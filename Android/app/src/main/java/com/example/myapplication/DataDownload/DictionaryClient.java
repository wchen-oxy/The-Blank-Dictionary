package com.example.myapplication.DataDownload;

import android.util.Log;
import com.example.myapplication.Constants;

import com.loopj.android.http.*;


public class DictionaryClient {
//    private static final String BASE_URL = "http://10.0.2.2:8000/";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("Authorization", AUTH_KEY);
        client.addHeader("Authorization", Constants.authDigest(Constants.CODE));
        Log.d("HEADER", "AUTH LOADED");
        Log.d("URL", Constants.getAbsoluteUrl(url));
        client.get(Constants.getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(Constants.getAbsoluteUrl(url), params, responseHandler);
    }



}
