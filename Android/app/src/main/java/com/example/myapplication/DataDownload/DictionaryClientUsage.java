package com.example.myapplication.DataDownload;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.example.myapplication.Constants;
import com.example.myapplication.DataSerialization;
import com.loopj.android.http.*;

import org.json.*;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class DictionaryClientUsage {
    private JSONArray list = null;


//    public void check() throws JSONException{
//        DictionaryClient.get("", null, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }
    public void getAvailableDic(final Context context) throws JSONException {
        DictionaryClient.get(Constants.DICT_LIST_URL_PART, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
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

//                list = response;
//                //store file
//                //TODO change to internal storage
//                File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", "list.json");
//                file.setWritable(true);
//
//                try {
//
//
//                    Writer output = new BufferedWriter(new FileWriter(file));
//                    output.write(list.toString());
//                    output.close();
//                    context.sendBroadcast(new Intent("DICTIONARY_LIST_DOWNLOADED"));
//
//                }
//                catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("DICTIONARY_LIST_DOWNLOADED"));

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable error){
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
        DictionaryClient.get(Constants.STATUS_URL_PART, null, new AsyncHttpResponseHandler()
                {

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
                        Log.d("Failed: ", ""+statusCode);
                        Log.d("Error : ", "" + error);

                    }
                }
        );

    }
}
