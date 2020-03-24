package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.example.myapplication", appContext.getPackageName());
//    }
//}

public class ExampleInstrumentedTest {
    private static final String PREFS_NAME = "prefs";
    private static final String KEY_PREF = "KEY_PREF";
    private SharedPreferences sharedPreferences;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getTargetContext();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Test
    public void put_and_get_preference() throws Exception {
        String string1 = "test";
        String string3 = "test2";
        sharedPreferences.edit().putString(KEY_PREF, string1).apply();
        String string2 = sharedPreferences.getString(KEY_PREF, "");

        // Verify that the received data is correct.
        assertEquals(string1, string2);

        sharedPreferences.edit().putString(KEY_PREF, string3).apply();
        String string4 = sharedPreferences.getString(KEY_PREF, "");
        assertEquals(string3, string4);

    }

    @After
    public void after() {
        sharedPreferences.edit().putString(KEY_PREF, null).apply();
    }
}