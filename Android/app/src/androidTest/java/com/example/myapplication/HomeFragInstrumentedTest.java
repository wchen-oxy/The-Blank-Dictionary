package com.example.myapplication;

import android.content.SharedPreferences;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.Fragments.HomeFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class HomeFragInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testTitleChange() {
        SharedPreferences preferences = mainActivityActivityTestRule.getActivity().getSharedPreferences(APP_PREFERENCES, 0);
        preferences.edit().putString(CURRENTLY_SELECTED_DICTIONARY, "Bhutia");
        onView(withId(R.id.mainTitle)).check(matches(withText("The Bhutia Dictionary")));
    }

    @Test
    public void testTranslationSpinner(){
//        SharedPreferences preferences = mainActivityActivityTestRule.getActivity()
        String target = getHomeTransSpinner("Bhutia")[0];
        onView(withId(R.id.home_trans_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(target))).perform(click());
        onView(withId(R.id.home_trans_spinner)).check(matches(withSpinnerText(containsString("English of Bhutia (Formal)"))));

    }

    public String[] getHomeTransSpinner(String language){
        switch (language) {
            case "Bhutia":
                return mainActivityActivityTestRule.getActivity().getResources().getStringArray(R.array.bhutia_array);
            case "English":
                return mainActivityActivityTestRule.getActivity().getResources().getStringArray(R.array.english_array);


        }
        return null;

    }

}

