package com.example.myapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Dictionaries.English.EnglishDao;
import com.example.myapplication.Fragments.LanguagePackFrag;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;


import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class myReceiver extends BroadcastReceiver {
    public static long downloadId;
    private EnglishDao englishDao;
    private BhutiaDao bhutiaDao;

    String type;


    public myReceiver(String type) {
        this.type = type;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        Log.d(TAG, "RECIEVED");
        Log.d("CHECK3", Environment.getExternalStorageDirectory().toString());

        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Checking if the received broadcast is for our enqueued download by matching download id
        if (downloadId == id) {
            LanguagePackFrag.DOWNLOAD_IN_PROGRSS = false;
            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();

            final File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", type);
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
                Log.d("Mess", message);
                fis.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("noFile", message);

            }
            catch (IOException io) {
                Log.d("IO fail", message);
                io.printStackTrace();
            }
            Log.d("CHECK5", Environment.getExternalStorageDirectory().toString());

            //builds database upon completion of download
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").enableMultiInstanceInvalidation().build();
            //build an abstract factory
            try{
            switch (type) {
                case "Bhutia": {
                    BhutiaDao bhutiaDao = db.getBhutiaDao();
                    DataInsert.BhuInsert(bhutiaDao, file);

                }
                case "English": {
                    EnglishDao englishDao = db.getEnglishDao();

                }

            }
            } catch (IOException io){
                io.printStackTrace();
            }




        }
        Log.d("Is it Here?", Boolean.toString(new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", type).exists()));
        Log.d("CHECK6", Environment.getExternalStorageDirectory().toString());

    }






}

