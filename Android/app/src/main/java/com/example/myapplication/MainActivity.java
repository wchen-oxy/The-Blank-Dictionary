package com.example.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FragmentManager fragmentManager;
    FragmentTransaction homeTransaction;
    FragmentTransaction searchTransaction;
    FragmentTransaction settingsTransaction;
    FragmentTransaction favoritesTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    System.out.println("HERE ARE THE FRAGMENTS: " + getSupportFragmentManager().getFragments());
                    fragmentManager.popBackStack();
                    HomeFrag homeFrag = new HomeFrag();
                    homeTransaction = fragmentManager.beginTransaction();
                    homeTransaction.replace(R.id.frag_container, homeFrag).commit();
                    return true;
                case R.id.navigation_favorites:
//                    fragmentManager.popBackStack();
                    FavoritesFrag favoritesFrag = new FavoritesFrag();
//                    fragmentManager = getSupportFragmentManager();
                    favoritesTransaction = fragmentManager.beginTransaction();
//                    favoritesTransaction.addToBackStack(null);
                    favoritesTransaction.replace(R.id.frag_container, favoritesFrag).commit();
                    return true;
                case R.id.navigation_settings:
//                    fragmentManager.popBackStack();

//                    mTextMessage.setText(R.string.title_notifications);
//                    System.out.println(getSupportFragmentManager().getFragments());
                    SettingsFrag settingsFrag = new SettingsFrag();
//                    fragmentManager = getSupportFragmentManager();
                    settingsTransaction = fragmentManager.beginTransaction();
//                    settingsTransaction.addToBackStack(null);
                    settingsTransaction.replace(R.id.frag_container, settingsFrag).commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_frame);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        mTextMessage = findViewById(R.id.message);
        fragmentManager = getSupportFragmentManager();
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));

        //fragment shit
        HomeFrag homeFrag = new HomeFrag();
        homeTransaction = fragmentManager.beginTransaction();
//        homeTransaction.addToBackStack(null);
        homeTransaction.add(R.id.frag_container, homeFrag, "HOME_FRAG").commit();


    }



//    @Override
//    public void onBackPressed() {
//
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }
//
//    }

}
