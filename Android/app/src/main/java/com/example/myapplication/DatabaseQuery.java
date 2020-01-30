package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.room.Room;
import java.lang.ref.WeakReference;
import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.Bhutia;
import com.example.myapplication.Dictionaries.English.English;
import com.example.myapplication.Dictionaries.ResultWrapper;

import static com.example.myapplication.Constants.DictionaryData.DATABASE;
import static com.example.myapplication.Constants.DictionaryData.QUERY;
import static com.example.myapplication.Constants.DictionaryData.TRANSLATION_STRING;
import static com.example.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.example.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class DatabaseQuery extends AsyncTask<Bundle, Void, ResultWrapper> {
    private final WeakReference<Context> weakContext;
    private SharedPreferences pref;

    public DatabaseQuery(Context context){
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences(APP_PREFERENCES, 0);
    }

    @Override
    protected ResultWrapper doInBackground(Bundle... full_query) {
        ResultWrapper resultWrapper = null;
        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, DATABASE).enableMultiInstanceInvalidation().build();
        Bundle args = full_query[0];
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case(BHUTIA):
                Log.d("Check", "Inner");
                resultWrapper = new Bhutia(db, args.getString(QUERY), args.getString(TRANSLATION_STRING));

                break;
            case(ENGLISH):
                resultWrapper = new English(db, args.getString(QUERY), args.getString(TRANSLATION_STRING));
        }

        if (resultWrapper == null) throw new NullPointerException();
        return resultWrapper;
        }





}



