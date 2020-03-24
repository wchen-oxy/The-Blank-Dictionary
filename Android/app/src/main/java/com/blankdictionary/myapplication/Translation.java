package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class Translation {
    public static String[] getSet(Context context) {
        String[] result = null;
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                result = context.getResources().getStringArray(R.array.bhutia_array);
                break;
            case (ENGLISH):
                result = context.getResources().getStringArray(R.array.english_array);
                break;
            default:


        }
        return result;

    }
}
