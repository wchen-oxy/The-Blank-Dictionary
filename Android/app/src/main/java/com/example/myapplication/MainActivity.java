package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.myapplication.Fragments.DictionarySelectionFrag;
import com.example.myapplication.Fragments.FavoritesFrag;
import com.example.myapplication.Fragments.HomeFrag;
import com.example.myapplication.Fragments.LanguagePackFrag;
import com.example.myapplication.Fragments.ResultFragment;
import com.example.myapplication.Fragments.SearchFrag;
import com.example.myapplication.Fragments.SettingsFrag;


public class MainActivity extends AppCompatActivity implements FragmentCommunicator{
    private TextView mTextMessage;

    FragmentManager fragmentManager;
    FragmentTransaction homeTransaction;
    FragmentTransaction searchTransaction;
    FragmentTransaction settingsTransaction;
    FragmentTransaction favoritesTransaction;
    FragmentTransaction langFragTransaction;
    BottomNavigationView navViewBack;
    SearchView secSearch;
//    public String query;
    public BroadcastReceiver br;
    public Boolean isAdvSearch = false;
    public FragmentCommunicator fragmentCommunicator;
    Bundle args;

    //TODO implement these
    @Override
    public void textPass(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("RECIEVED", "TEXT");

    }

    @Override
    public void bundPass(Bundle args, boolean isPause) {
        if (isPause) {this.args = args;
        Log.d("BUND", "PASSEd");
        }
        else {
            fragController(args);
        }
    }


    private void fragController(Bundle args){
        switch (args.getString("NEW_FRAGMENT")){
            case "HOME_FRAGMENT":
                HomeFrag homeFrag = new HomeFrag();
                homeTransaction = fragmentManager.beginTransaction();
                homeTransaction.add(R.id.frag_container, homeFrag, "HOME_FRAG").commit();
                break;

            case "SEARCH_FRAGMENT":
                System.out.println();
                SearchFrag searchFrag = new SearchFrag();
                searchFrag.setArguments(args);
                searchTransaction = fragmentManager.beginTransaction();
//                searchTransaction.addToBackStack(null);
                searchTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").addToBackStack(null).commit();
                isAdvSearch = true;
                break;

            case "RESULT_FRAGMENT":
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(args);
                FragmentTransaction t = fragmentManager.beginTransaction();
                t.replace(R.id.results_frag, resultFragment);
                t.addToBackStack("RESULT_FRAG");
                t.commit();
                break;
            case "LANG_DOWNLOAD_FRAGMENT":
                LanguagePackFrag languagePackFrag = new LanguagePackFrag();
                langFragTransaction = fragmentManager.beginTransaction();
                langFragTransaction.replace(R.id.frag_container, languagePackFrag).addToBackStack(null).commit();
                break;
            case "DICT_SELECT_FRAGMENT":
                DictionarySelectionFrag dictionarySelectionFrag = DictionarySelectionFrag.newInstance();
                langFragTransaction = fragmentManager.beginTransaction();
                langFragTransaction.replace(R.id.frag_container, dictionarySelectionFrag).addToBackStack(null).commit();
                Toast.makeText(this, "Dict Changed", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = this.getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("CurDict", "BHUTIA"); // Storing boolean - true/false
                editor.commit(); // commit changes


        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("HERE ARE THE FRAGMENTS: " + getSupportFragmentManager().getFragments());
                    if (isAdvSearch) {
                        SearchFrag searchFrag = new SearchFrag();
//                        Bundle args = getArguments();

                            searchFrag.setArguments(args);
                            Log.d("Args", "is null");

//                        Log.d("arguments", args.getString("TRANSLATION"));
//                        Log.d("arguments", args.getString("query"));


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
        mainSearch.setQuery("",false);

        }
//        else if( f instanceof ResultFragment) {
//            fragmentManager.popBackStack();
//            super.onBackPressed();
//        }
        else if (f instanceof SettingsFrag){
            Log.d("Settings", "RETURNED");

            super.onBackPressed();
        }
        else {
            if (f instanceof SearchFrag) ((SearchFrag) f).setmAdapter();


            Log.d("ceck", "backpress");
            //add clear
            isAdvSearch = false;
//            super.onBackPressed();

            final Fragment fragment = fragmentManager.findFragmentById(R.id.results_frag);
            if (fragment != null) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }


        }
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
        Bundle args = new Bundle();
        args.putString("NEW_FRAGMENT", "HOME_FRAGMENT");
        fragController(args);

//        //setup for shared pref
//        //FIXME CHANGE SO THAT APP ON STARTUP DYNAMICALLY CHANGES TO DICTIONARY
//        SharedPreferences pref = getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("CurDict", "BHUTIA"); // Storing boolean - true/false
//        editor.commit(); // commit changes
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }



}
