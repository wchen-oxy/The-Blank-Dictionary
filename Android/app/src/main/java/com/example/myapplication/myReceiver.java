package com.example.myapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.DOWNLOAD_SERVICE;

public class myReceiver extends BroadcastReceiver {
    static long downloadId;

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        Log.d(TAG, "RECIEVED");
        Log.d("CHECK3", Environment.getExternalStorageDirectory().toString());


//        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Checking if the received broadcast is for our enqueued download by matching download id
        if (downloadId == id) {
            LanguagePackFrag.DOWNLOAD_IN_PROGRSS = false;
            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
//            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
//            Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
//            if (cursor.moveToFirst()) {
//                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
//                String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                Toast.makeText(context, uriString, Toast.LENGTH_LONG).show();
//
//            }
            final File file = new File(Environment.getExternalStorageDirectory(), "Bhutia");
            FileInputStream fis = null;
            String message = "";
            Log.d("CHECK4", Environment.getExternalStorageDirectory().toString());

            try {
                fis = new FileInputStream(file);
                int c;
                while ((c = fis.read()) != -1) {
                    message += String.valueOf((char) c);
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                fis.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException io) {
                io.printStackTrace();
            }
            Log.d("CHECK5", Environment.getExternalStorageDirectory().toString());







        }
        Log.d("CHECK6", Environment.getExternalStorageDirectory().toString());

    }


}

