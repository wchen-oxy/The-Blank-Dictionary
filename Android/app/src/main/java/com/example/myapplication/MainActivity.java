package com.example.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FragmentManager fragmentManager;
    FragmentTransaction homeTransaction;
    FragmentTransaction searchTransaction;
    FragmentTransaction settingsTransaction;
    FragmentTransaction favoritesTransaction;
    BottomNavigationView navViewBack;
    SearchView secSearch;
    String query;

    Boolean isAdvSearch = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    System.out.println("HERE ARE THE FRAGMENTS: " + getSupportFragmentManager().getFragments());
//                    fragmentManager.popBackStack();
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

    //code for selecting correct menu item on backstack press
    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.frag_container);
    }

    @Override
    public void onBackPressed(){
        SearchView mainSearch = findViewById(R.id.searchView);
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
        //clear text
        else if (!mainSearch.getQuery().toString().isEmpty()) {
        Log.d("ceck", mainSearch.getQuery().toString());

        Log.d("ceck", "clear text");
        mainSearch.setQuery("",false);

        }
        //return to main page

        else {
            Log.d("ceck", mainSearch.getQuery().toString());
            Log.d("ceck", "backpress");
            //add clear
            isAdvSearch = false;


            super.onBackPressed();}
//        super.onBackPressed();
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        handleIntent(intent);
//    }
//
//    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(getApplicationContext(), "Searching for " + query, Toast.LENGTH_LONG).show();
//
//            //fragment shit
//            SearchFrag searchFrag = new SearchFrag();
//
//            searchTransaction = fragmentManager.beginTransaction();
//            searchTransaction.addToBackStack(null);
//            searchTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").commit();
//
////            updateEntriesView(dbHelper.queryEntriesByName(query));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_frame);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navViewBack = navView;

//        mTextMessage = findViewById(R.id.message);
        fragmentManager = getSupportFragmentManager();
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));
//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                Fragment current = getCurrentFragment();
//                if (current instanceof HomeFrag) {
//                    navView.setSelectedItemId(R.id.navigation_home);
//                }
//                if(current instanceof FavoritesFrag) {
//                    navView.setSelectedItemId(R.id.navigation_favorites);
//                }
//                if (current instanceof SettingsFrag) {
//                    navView.setSelectedItemId(R.id.navigation_settings);
//                }
//            }
//        });



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
