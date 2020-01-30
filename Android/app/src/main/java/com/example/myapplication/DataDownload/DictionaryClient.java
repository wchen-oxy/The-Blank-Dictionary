package com.example.myapplication.DataDownload;

import android.util.Log;
import com.loopj.android.http.*;
import static com.example.myapplication.Constants.Network.getAbsoluteUrl;

import static com.example.myapplication.Constants.Network.authDigest;



public class DictionaryClient {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Authorization", authDigest());
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }



}
