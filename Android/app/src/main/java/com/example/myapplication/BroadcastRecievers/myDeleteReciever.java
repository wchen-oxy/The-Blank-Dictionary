package com.example.myapplication.BroadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.Adapters.SettingsListsAdapter;

public class myDeleteReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ONE RECIEVER GOT");
        context.unregisterReceiver(this);
        //get instance of settings list adapter to refresh list


    }
}
