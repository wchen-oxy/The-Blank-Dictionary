package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.Fragments.FavoritesFrag;
import com.example.myapplication.Fragments.HomeFrag;
import com.example.myapplication.Fragments.SearchFrag;
import com.example.myapplication.Fragments.SettingsFrag;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FragmentManager fragmentManager;
    FragmentTransaction homeTransaction;
    FragmentTransaction searchTransaction;
    FragmentTransaction settingsTransaction;
    FragmentTransaction favoritesTransaction;
    BottomNavigationView navViewBack;
    SearchView secSearch;
    public String query;
    public BroadcastReceiver br;

    public Boolean isAdvSearch = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("HERE ARE THE FRAGMENTS: " + getSupportFragmentManager().getFragments());
                    if (isAdvSearch) {
                        SearchFrag searchFrag = new SearchFrag();
                        Bundle arguments = new Bundle();
                        arguments.putString( "query", query);
                        searchFrag.setArguments(arguments);
                        searchTransaction = fragmentManager.beginTransaction();
                        searchTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").commit();
                        return true;
                    }


                    HomeFrag homeFrag = new HomeFrag();
                    homeTransaction = fragmentManager.beginTransaction();
                    homeTransaction.replace(R.id.frag_container, homeFrag).commit();
                    return true;
                case R.id.navigation_favorites:
                    FavoritesFrag favoritesFrag = new FavoritesFrag();
                    favoritesTransaction = fragmentManager.beginTransaction();
                    favoritesTransaction.replace(R.id.frag_container, favoritesFrag).commit();
                    return true;
                case R.id.navigation_settings:
                    SettingsFrag settingsFrag = new SettingsFrag();
                    settingsTransaction = fragmentManager.beginTransaction();
                    settingsTransaction.replace(R.id.frag_container, settingsFrag).commit();

                    return true;
            }
            return false;
        }
    };

    //code for selecting correct menu item on backstack press
    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.frag_container);
    }

    @Override
    public void onBackPressed(){
        SearchView mainSearch = findViewById(R.id.searchAdvView);
        Log.d("TAG", getCurrentFragment().toString());

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        if (!(f instanceof HomeFrag) && !(f instanceof SearchFrag)){
            //favorites or settings
                Log.d("THIS CLASS IS", f.toString());
            navViewBack.setSelectedItemId(R.id.navigation_home);
            Log.d("TAG", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));

            getSupportFragmentManager().popBackStackImmediate();
            Log.d("TAG", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));

            super.onBackPressed();

        }
        //clear text in adv
        else if (mainSearch != null && !mainSearch.getQuery().toString().isEmpty()) {
        Log.d("ceck", mainSearch.getQuery().toString());

        Log.d("ceck", "clear text");
        mainSearch.setQuery("",false);

        }
        //return to main page

        else {
            Log.d("ceck", "backpress");
            //add clear
            isAdvSearch = false;
            super.onBackPressed();}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_frame);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navViewBack = navView;
        fragmentManager = getSupportFragmentManager();
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));

        //fragment shit
        HomeFrag homeFrag = new HomeFrag();
        homeTransaction = fragmentManager.beginTransaction();
        homeTransaction.add(R.id.frag_container, homeFrag, "HOME_FRAG").commit();




    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(br);
    }





}
