package com.example.myapplication.BroadcastRecievers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;
import java.io.File;


import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDataInsert;
import com.example.myapplication.Dictionaries.English.EnglishDao;
import com.example.myapplication.Dictionaries.English.EnglishDataInsert;


import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.example.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.example.myapplication.Constants.System.APP_NAME;
import static com.example.myapplication.Constants.System.DATABASE_UPDATED;

public class myDictionaryDownloadReceiver extends BroadcastReceiver {
    String type;
    long id;


    public myDictionaryDownloadReceiver(long id, String type) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        System.out.println("SUCCESSFUL DOWNLOAD: " + downloadManager.getUriForDownloadedFile(id));
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n");

        //Checking if the received broadcast is for our enqueued download by matching download id
        Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();

        final File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME, type);
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

        Log.i("Temp Payload Deleted:", Boolean.toString(file.delete()));
        Intent dictionaryDownloadedIntent = new Intent();
        dictionaryDownloadedIntent.setAction(DATABASE_UPDATED);
        context.sendBroadcast(dictionaryDownloadedIntent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(dictionaryDownloadedIntent);
    }
}

