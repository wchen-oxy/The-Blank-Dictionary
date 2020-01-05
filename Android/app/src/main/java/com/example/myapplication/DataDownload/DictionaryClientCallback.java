package com.example.myapplication.DataDownload;

import org.json.JSONArray;

public interface DictionaryClientCallback {
    public void onDictionaryClientResponse(boolean success, JSONArray response);
}
