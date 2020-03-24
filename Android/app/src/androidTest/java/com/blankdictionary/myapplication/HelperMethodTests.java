package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.blankdictionary.myapplication.DataDownload.NetworkCheck;

import org.junit.Before;
import org.junit.Test;

import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static org.junit.Assert.assertEquals;

public class HelperMethodTests {
    Bundle args;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Before
    public void createSysPref() {
        args = new Bundle();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        pref = context.getSharedPreferences(APP_PREFERENCES, 0);
        editor = pref.edit();

    }

    @Test
    public void testSysPref() {
        editor.putString(CURRENTLY_SELECTED_DICTIONARY, "BHUTIA"); // Storing String
        editor.commit();

        Log.d("Pref Check", pref.getString(CURRENTLY_SELECTED_DICTIONARY, null));
        assertEquals(pref.getString(CURRENTLY_SELECTED_DICTIONARY, null), "BHUTIA");
    }

    @Test
    public void testIsOnline() {
        assertEquals(NetworkCheck.isOnline(), true);
    }
}
