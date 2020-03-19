package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.Fragments.HomeFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.myapplication.Constants.Fragment.HOME_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MyInterfaceTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    void basicLifeCycleTest() throws Throwable {
        final FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(NEW_FRAGMENT, HOME_FRAGMENT);
        mActivityRule.getActivity().bundPass(args, false);
        assertEquals(fragmentManager.getBackStackEntryCount(), 1);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        HomeFragment fragment = (HomeFragment) fragmentList.get(0);
        fragmentManager.beginTransaction().remove(fragment);


    }


}

