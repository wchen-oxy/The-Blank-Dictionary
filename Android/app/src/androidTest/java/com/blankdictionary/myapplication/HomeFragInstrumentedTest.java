package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import com.blankdictionary.myapplication.Adapters.MyQueryResultAdapter;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class HomeFragInstrumentedTest {

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
    public void checkRemovedTitle() {
        mActivityRule.launchActivity(launch);
        onView(withId(R.id.textview_hero_dictionary_title)).check(matches(withText("The Blank Dictionary")));
        onView(withId(R.id.navigation_settings)).perform(click());
        sharedPreferences.edit().putString(CURRENTLY_SELECTED_DICTIONARY, BHUTIA).apply();
        assertEquals(sharedPreferences.getString(CURRENTLY_SELECTED_DICTIONARY, "nope"), BHUTIA);

    }

    @Test
    public void checkBhutiaTranslation() {
        mActivityRule.launchActivity(launch);
        sharedPreferences.edit().putString(CURRENTLY_SELECTED_DICTIONARY, BHUTIA).apply();
        assertEquals(sharedPreferences.getString(CURRENTLY_SELECTED_DICTIONARY, "nope"), BHUTIA);
        String[] target = getHomeTranslationSet(BHUTIA);
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.imagebutton_home_translations)).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview_translations)).perform(
                RecyclerViewActions.<MyQueryResultAdapter.MyViewHolder>actionOnItemAtPosition(0, click())

        );
        onView(withText("English to Bhutia (Formal)")).check(matches(isDisplayed()));


    }

    @Test
    public void checkEnglishTranslation() {
        mActivityRule.launchActivity(launch);
        sharedPreferences.edit().putString(CURRENTLY_SELECTED_DICTIONARY, ENGLISH).apply();
        assertEquals(sharedPreferences.getString(CURRENTLY_SELECTED_DICTIONARY, "nope"), ENGLISH);
        String[] target = getHomeTranslationSet(BHUTIA);
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.imagebutton_home_translations)).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview_translations)).perform(
                RecyclerViewActions.<MyQueryResultAdapter.MyViewHolder>actionOnItemAtPosition(0, click())
        );
        onView(withText("English to English")).check(matches(isDisplayed()));
    }

    public String[] getHomeTranslationSet(String language) {

        switch (language) {
            case BHUTIA:
                return mActivityRule.getActivity().getResources().getStringArray(R.array.bhutia_array);
            case ENGLISH:
                return mActivityRule.getActivity().getResources().getStringArray(R.array.english_array);
        }
        return null;

    }

}

