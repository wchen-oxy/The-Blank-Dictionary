package com.blankdictionary.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
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
public class SearchFragInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    private Intent launch = new Intent().setAction(Intent.ACTION_MAIN);

    @AfterClass
    public static void clearPref() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit().remove(CURRENTLY_SELECTED_DICTIONARY).commit();
    }

    public String[] getHomeTransSpinner(String language) {

        switch (language) {
            case BHUTIA:
                return mActivityRule.getActivity().getResources().getStringArray(R.array.bhutia_array);
            case ENGLISH:
                return mActivityRule.getActivity().getResources().getStringArray(R.array.english_array);
        }
        return null;

    }

    @Test
    public void checkTranslationSpinner() {
        sharedPreferences.edit().remove(CURRENTLY_SELECTED_DICTIONARY).commit();
        sharedPreferences.edit().putString(CURRENTLY_SELECTED_DICTIONARY, BHUTIA).apply();
        mActivityRule.launchActivity(launch);
        assertEquals(sharedPreferences.getString(CURRENTLY_SELECTED_DICTIONARY, "nope"), BHUTIA);
        String[] target = getHomeTransSpinner(BHUTIA);
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.home_trans_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(target[2]))).perform(click());
        onView(withId(R.id.home_trans_spinner)).check(matches(withSpinnerText(containsString("Bhutia to English (Formal)"))));
        onView(withId(R.id.home_search_view))
                .perform(typeText("chik")).perform(pressKey(
                KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.adv_trans_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(target[0]))).perform(click());
        onView(withId(R.id.adv_trans_spinner)).check(matches(withSpinnerText(containsString("English to Bhutia (Formal)"))));


//        onView(withId(R.id.home_search_view))
//                .perform(typeText("chik"));
//        onView(withId(R.id.home_search_view)).check(matches(hasImeAction(EditorInfo.IME_ACTION_NEXT)));
    }


}
