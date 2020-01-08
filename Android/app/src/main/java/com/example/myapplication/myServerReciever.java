package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.DataDownload.DictionaryClientUsage;

public class myServerReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
        Log.d("Server Check", "BROADCAST RECIEVED");



    }
}
