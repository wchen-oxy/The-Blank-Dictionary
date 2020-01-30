package com.example.myapplication.BroadcastRecievers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDataInsert;
import com.example.myapplication.Dictionaries.English.EnglishDao;
import com.example.myapplication.Dictionaries.English.EnglishDataInsert;
import com.example.myapplication.Fragments.LanguagePackFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.example.myapplication.Constants.SupportedDictionaries.ENGLISH;

public class myDictionaryDownloadReceiver extends BroadcastReceiver {
    String type;


    public myDictionaryDownloadReceiver(String type) {
        this.type = type;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");

//        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Checking if the received broadcast is for our enqueued download by matching download id
            LanguagePackFragment.DOWNLOAD_IN_PROGRSS = false;
            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();

            final File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", type);

            String message = "";

            try {
                FileInputStream fis = new FileInputStream(file);
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
            //builds database upon completion of download
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").fallbackToDestructiveMigration().enableMultiInstanceInvalidation().build();
            //create a value to store in shared preferences
            SharedPreferences pref = context.getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
            final SharedPreferences.Editor editor = pref.edit();
            //build an abstract factory
            switch (type) {
                case BHUTIA: {
                    BhutiaDao bhutiaDao = db.getBhutiaDao();
                    BhutiaDataInsert.BhuInsert(bhutiaDao, file);
                    editor.putBoolean(BHUTIA, true);
                    editor.commit();
                    break;

                }
                case ENGLISH: {
                    EnglishDao englishDao = db.getEnglishDao();
                    EnglishDataInsert.engInsert(englishDao, file);
                    editor.putBoolean(ENGLISH, true);
                    editor.commit();
                    break;

                }
                default:
                    Log.d("myDictionaryReciever", "Something went wrong");

            }


//        }

    }






}

