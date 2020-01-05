package com.example.myapplication.DataDownload;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpException;


public class DictionaryClientUsage {
    private JSONArray list = null;
    private boolean status = false;

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
        DictionaryClient.get("all/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);

                Log.d("RECIEVED RESPONSE", response.toString());
                list = response;
                //store file
                //TODO change to internal storage
                File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", "list.json");
                file.setWritable(true);

                try {
                    Writer output = null;
                    output = new BufferedWriter(new FileWriter(file));
                    output.write(list.toString());
                    output.close();
                    context.sendBroadcast(new Intent("LIST_DOWNLOAD_COMPLETE"));

                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


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

    public boolean checkStatus() throws HttpBadRequestException {
        DictionaryClient.get("status/", null, new AsyncHttpResponseHandler()
                {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Success", "Passed");
                        status = true;

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Failed: ", ""+statusCode);
                        Log.d("Error : ", "" + error);

                    }
                }
        );
        Log.d("Status", String.valueOf(status));

        return status;
    }
}
