package com.blankdictionary.myapplication.BroadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class myServerStatusReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Successfully Connected to Server!", Toast.LENGTH_SHORT).show();
        context.unregisterReceiver(this);
    }
}
