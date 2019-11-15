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
import android.widget.Toast;

import com.example.myapplication.Fragments.FavoritesFrag;
import com.example.myapplication.Fragments.HomeFrag;
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
    BottomNavigationView navViewBack;
    SearchView secSearch;
    public String query;
    public BroadcastReceiver br;
    public Boolean isAdvSearch = false;
    public FragmentCommunicator fragmentCommunicator;

    //TODO implement these
    @Override
    public void textPass(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("RECIEVED", "TEXT");

    }

    @Override
    public void bundPass(Bundle args) {
        fragController(args);
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
//        else if( f instanceof ResultFragment) {
//            fragmentManager.popBackStack();
//            super.onBackPressed();
//        }

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
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }



}
