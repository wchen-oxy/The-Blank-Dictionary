package com.example.myapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.Fragments.LanguagePackFrag;

public class myListBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
//        LanguagePackFrag.DOWNLOAD_IN_PROGRSS = false;
        context.unregisterReceiver(this);

    }
}
