package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.room.Room;

import com.blankdictionary.myapplication.Dictionaries.AppDatabase;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.Bhutia;
import com.blankdictionary.myapplication.Dictionaries.English.English;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;

import java.lang.ref.WeakReference;

import static com.blankdictionary.myapplication.Constants.DictionaryData.DATABASE;
import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_STRING;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class DatabaseQuery extends AsyncTask<Bundle, Void, ResultWrapper> {
    private final WeakReference<Context> weakContext;
    private SharedPreferences pref;

    public DatabaseQuery(Context context) {
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences(APP_PREFERENCES, 0);
    }

    @Override
    protected ResultWrapper doInBackground(Bundle... full_query) {
        ResultWrapper resultWrapper = null;
        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, DATABASE).enableMultiInstanceInvalidation().build();
        Bundle args = full_query[0];
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                Log.d("Check", "Inner");
                resultWrapper = new Bhutia(db, args.getString(QUERY), args.getString(TRANSLATION_STRING));

                break;
            case (ENGLISH):
                resultWrapper = new English(db, args.getString(QUERY), args.getString(TRANSLATION_STRING));
        }

        if (resultWrapper == null) throw new NullPointerException();
        return resultWrapper;
    }


}



