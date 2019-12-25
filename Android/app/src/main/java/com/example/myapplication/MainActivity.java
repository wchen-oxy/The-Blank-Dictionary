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

import com.example.myapplication.Dictionaries.ResultWrapper;
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
    FragmentTransaction fragmentTransaction;
    BottomNavigationView navViewBack;
    SearchView secSearch;
//    public String query;
    public BroadcastReceiver br;
    public Boolean isAdvSearch = false;
    public FragmentCommunicator fragmentCommunicator;
    Bundle args;

    //TODO implement these
//    @Override
//    public void textPass(String string) {
//        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//        Log.d("RECIEVED", "TEXT");
//
//    }


    //to create all fragments
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
                HomeFrag homeFrag = HomeFrag.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frag_container, homeFrag, "HOME_FRAG").commit();
                break;

            case "SEARCH_FRAGMENT":
                SearchFrag searchFrag = SearchFrag.newInstance(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").addToBackStack("SEARCH_FRAGMENT").commit();
                isAdvSearch = true;
                break;
            case "RESULT_FRAGMENT":
                ResultFragment resultFragment = ResultFragment.newInstance(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, resultFragment).addToBackStack("RESULT_FRAGMENT").commit();
                break;
            case "LANG_DOWNLOAD_FRAGMENT":
                LanguagePackFrag languagePackFrag = LanguagePackFrag.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, languagePackFrag).addToBackStack("LANG_DOWNLOAD_FRAGMENT").commit();
                break;
            case "DICT_SELECT_FRAGMENT":
                DictionarySelectionFrag dictionarySelectionFrag = DictionarySelectionFrag.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, dictionarySelectionFrag).addToBackStack("DICT_SELECT_FRAGMENT").commit();
                break;
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
                        Log.d("Args", "is null");
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_container, SearchFrag.newInstance(args), "ADV_SEARCH_FRAG").commit();
                        return true;
                    }
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, HomeFrag.newInstance()).commit();
                    return true;

                case R.id.navigation_favorites:
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, FavoritesFrag.newInstance()).commit();
                    return true;
                case R.id.navigation_settings:
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, SettingsFrag.newInstance()).commit();

                    return true;
            }
            return false;
        }
    };

    private void clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    //code for selecting correct menu item on backstack press
    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.frag_container);
    }

    //FIXME simplify and remove bug that presses back twice
//    @Override
//    public void onBackPressed(){
//        SearchView mainSearch = findViewById(R.id.searchAdvView);
//        Log.d("TAG", getCurrentFragment().toString());
//
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frag_container);
//        if (!(f instanceof HomeFrag) && !(f instanceof SearchFrag)){
//                Log.d("THIS CLASS IS", f.toString());
//            navViewBack.setSelectedItemId(R.id.navigation_home);
//            Log.d("TAG", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));
//
//            getSupportFragmentManager().popBackStackImmediate();
//            Log.d("TAG", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));
//
//            super.onBackPressed();
//
//        }
//        //clear text in adv
//        else if (mainSearch != null && !mainSearch.getQuery().toString().isEmpty()) {
//        Log.d("ceck", mainSearch.getQuery().toString());
//        mainSearch.setQuery("",false);
//
//        }
////        else if( f instanceof ResultFragment) {
////            fragmentManager.popBackStack();
////            super.onBackPressed();
////        }
//        else if (f instanceof SettingsFrag){
//            Log.d("Settings", "RETURNED");
//
//            super.onBackPressed();
//        }
//
//        else if(f instanceof LanguagePackFrag) super.onBackPressed();
//        else if(f instanceof DictionarySelectionFrag) super.onBackPressed();
//        else {
//            if (f instanceof SearchFrag) ((SearchFrag) f).setmAdapter();
//
//
//            Log.d("ceck", "backpress");
//            //add clear
//            isAdvSearch = false;
////            super.onBackPressed();
//
//            final Fragment fragment = fragmentManager.findFragmentById(R.id.results_frag);
//            if (fragment != null) {
//                fragmentManager.popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//
//
//        }
//    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
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
