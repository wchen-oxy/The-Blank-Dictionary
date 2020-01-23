package com.example.myapplication.DataDownload;

import android.content.Context;

public class Connectivity extends Thread {
    Context context;
    public Connectivity(Context context){
        this.context = context;
    }

    public void run(){
        try {
            new DictionaryClientUsage().checkStatus(context);
        } catch (HttpBadRequestException e) {
            e.printStackTrace();
        }
    }
}
