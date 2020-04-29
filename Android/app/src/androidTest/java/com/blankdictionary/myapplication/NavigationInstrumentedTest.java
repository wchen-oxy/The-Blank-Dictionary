package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

@RunWith(AndroidJUnit4ClassRunner.class)
public class NavigationInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);
    private SharedPreferences sharedPreferences;
    private Intent launch = new Intent();


    @AfterClass
    public static void clearPref() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit().remove(CURRENTLY_SELECTED_DICTIONARY).commit();
    }

    @Before
    public void setSharedPreferences() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        launch.setAction(Intent.ACTION_MAIN);
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(CURRENTLY_SELECTED_DICTIONARY).commit();
    }

    @Test
    public void checkWhileNoDictionarySelected(){
        mActivityRule.launchActivity(launch);
        onView(withId(R.id.navigation_settings)).perform(click());
        onView(withId(R.id.navigation_home)).perform(click());



    }



}
