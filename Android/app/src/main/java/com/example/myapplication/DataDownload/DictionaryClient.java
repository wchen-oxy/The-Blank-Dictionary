package com.example.myapplication.DataDownload;

import android.util.Log;

import com.loopj.android.http.*;



public class DictionaryClient {
//    private static final String BASE_URL = "http://blank-dictionary.herokuapp.com/";
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static final String AUTH_KEY = "888ae83db4ff636ae057e03772f45f30947940c956d2b9160b24fd55963f868b";
    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("Authorization", AUTH_KEY);
        client.addHeader("Authorization", AUTH_KEY);
        Log.d("HEADER", "AUTH LOADED");
        Log.d("URL", getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
