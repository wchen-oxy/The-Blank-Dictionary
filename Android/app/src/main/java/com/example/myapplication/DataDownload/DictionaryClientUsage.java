package com.example.myapplication.DataDownload;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.DataSerialization;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.myapplication.Constants.Network.DICT_LIST_URL_PART;
import static com.example.myapplication.Constants.Network.STATUS_URL_PART;


public class DictionaryClientUsage {
    private JSONArray list = null;


    public void getAvailableDic(final Context context) throws JSONException {
        DictionaryClient.get(DICT_LIST_URL_PART, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("RECEIVED RESPONSE", response.toString());
                ArrayList<String> arrayListResponse = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        arrayListResponse.add((String) response.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                DataSerialization.serializer(arrayListResponse);

                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("DICTIONARY_LIST_DOWNLOADED"));

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable error) {
//                super.onFailure( statusCode,  headers, errorResponse, error);
                Log.d("RECEIVED RESPONSE", "FAILURE");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("RECEIVED RESPONSE", "FAILURE");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("RECEIVED RESPONSE", "FAILURE");

            }
        });

    }

    public void checkStatus(final Context context) throws HttpBadRequestException {
        DictionaryClient.get(STATUS_URL_PART, null, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Success", "Passed");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("SERVER_REACHED"));
                        //begin download of new list of text
                        try {
                            getAvailableDic(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + error);

                    }
                }
        );

    }
}
