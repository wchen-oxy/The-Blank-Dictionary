package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import com.blankdictionary.myapplication.Dictionaries.AppDatabase;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.Bhutia;
import com.blankdictionary.myapplication.Dictionaries.English.English;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.blankdictionary.myapplication.Constants.DictionaryData.DATABASE;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.blankdictionary.myapplication.Constants.System.DATABASE_CLEARED;

public class DatabaseClear extends AsyncTask<ArrayList<String>, Void, Boolean> {
    private final WeakReference<Context> weakContext;
    private SharedPreferences pref;
    private Editor editor;

    DatabaseClear(Context context) {
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences(APP_PREFERENCES, 0);
        editor = pref.edit();

    }

    @Override
    protected Boolean doInBackground(ArrayList<String>... languagesToDelete) {
        String currentDictionary = pref.getString(CURRENTLY_SELECTED_DICTIONARY, "");
        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, DATABASE)
                .enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration()
                .build();

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
                    return false;

            }
        }


        Intent filesDeletedIntent = new Intent();
        filesDeletedIntent.setAction(DATABASE_CLEARED);
        weakContext.get().sendBroadcast(filesDeletedIntent);
        LocalBroadcastManager.getInstance(weakContext.get()).sendBroadcast(filesDeletedIntent);
        return true;
    }


}



