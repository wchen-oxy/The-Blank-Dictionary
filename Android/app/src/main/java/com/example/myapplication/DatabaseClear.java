package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.Bhutia;
import com.example.myapplication.Dictionaries.English.English;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.myapplication.Constants.DictionaryData.DATABASE;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.DATABASE_CLEARED;

public class DatabaseClear extends AsyncTask<ArrayList<String>, Void, Boolean> {
    private final WeakReference<Context> weakContext;
    private SharedPreferences pref;
    private Editor editor;
    private Context context;

    public DatabaseClear(Context context) {
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences(APP_PREFERENCES, 0);
        editor = pref.edit();
        this.context = context;

    }

    @Override
    protected Boolean doInBackground(ArrayList<String>... languagesToDelete) {
        String currentDictionary = pref.getString(CURRENTLY_SELECTED_DICTIONARY, "");
        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, DATABASE).enableMultiInstanceInvalidation().build();

        for (String lang : languagesToDelete[0]) {
            if (currentDictionary.equals(lang)) {
                editor.remove(CURRENTLY_SELECTED_DICTIONARY).apply();
            }

            switch (lang) {
                case Constants.SupportedDictionaries.BHUTIA:
                    new Bhutia(db);
                    pref.edit().remove(Constants.SupportedDictionaries.BHUTIA).apply();

                    break;
                case Constants.SupportedDictionaries.ENGLISH:
                    new English(db);
                    pref.edit().remove(Constants.SupportedDictionaries.ENGLISH).apply();

                    break;
                default:

            }
        }


        Intent filesDeletedIntent = new Intent();
        filesDeletedIntent.setAction(DATABASE_CLEARED);
        context.sendBroadcast(filesDeletedIntent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(filesDeletedIntent);
        return true;
    }


}



