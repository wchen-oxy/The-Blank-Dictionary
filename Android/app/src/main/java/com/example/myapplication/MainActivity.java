package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.example.myapplication.BroadcastRecievers.myAvailableDictionaryReciever;
import com.example.myapplication.BroadcastRecievers.myServerStatusReciever;
import com.example.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.example.myapplication.HelperInterfaces.IOnBackPressed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataDownload.DictionaryClientUsage;
import com.example.myapplication.DataDownload.HttpBadRequestException;
import com.example.myapplication.Fragments.DictionarySelectionFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.LanguagePackFragment;
import com.example.myapplication.Fragments.ResultFragment;
import com.example.myapplication.Fragments.SearchFragment;
import com.example.myapplication.Fragments.SettingsFragment;

import java.io.File;
import java.io.IOException;

import static com.example.myapplication.Constants.Fragment.DICTIONARY_SELECTION_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.HOME_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.LANGUAGE_DOWNLOAD_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.RESULT_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.SETTINGS_FRAGMENT;
import static com.example.myapplication.Constants.IntentFilters.DICTIONARY_LIST_DOWNLOADED;
import static com.example.myapplication.Constants.IntentFilters.SERVER_REACHED;
import static com.example.myapplication.Constants.Network.NO_INTERNET_ERROR;
import static com.example.myapplication.Constants.System.APP_NAME;

//import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;


public class MainActivity extends AppCompatActivity implements IFragmentCommunicator {
    private TextView mTextMessage;


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView navViewBack;
    com.example.myapplication.BroadcastRecievers.myServerStatusReciever myServerStatusReciever;
    LocalBroadcastManager localBroadcastManager;
    SearchView secSearch;
//    public String query;
    public BroadcastReceiver br;
    public BroadcastReceiver myAvailableDictionaryReciever;
    public Boolean isAdvSearch = false;
    public IFragmentCommunicator fragmentCommunicator;
    final Context context = this;

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
        switch (args.getString(NEW_FRAGMENT)){
            case HOME_FRAGMENT:
                HomeFragment homeFragment = HomeFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frag_container, homeFragment, HOME_FRAGMENT).commit();
                break;

            case SEARCH_FRAGMENT:
                SearchFragment searchFragment = SearchFragment.newInstance(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, searchFragment, SEARCH_FRAGMENT).addToBackStack(SEARCH_FRAGMENT).commit();
                isAdvSearch = true;
                break;
            case RESULT_FRAGMENT:
                ResultFragment resultFragment = ResultFragment.newInstance(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, resultFragment).addToBackStack(RESULT_FRAGMENT).commit();
                break;
            case DICTIONARY_SELECTION_FRAGMENT:
                DictionarySelectionFragment dictionarySelectionFragment = DictionarySelectionFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, dictionarySelectionFragment).addToBackStack(DICTIONARY_SELECTION_FRAGMENT).commit();
                break;

            case LANGUAGE_DOWNLOAD_FRAGMENT:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                ,101);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    LanguagePackFragment languagePackFragment = LanguagePackFragment.newInstance();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, languagePackFragment).addToBackStack(LANGUAGE_DOWNLOAD_FRAGMENT).commit();
                }


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
                        fragmentTransaction.replace(R.id.frag_container, SearchFragment.newInstance(args), SEARCH_FRAGMENT).commit();
                        return true;
                    }
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, HomeFragment.newInstance(), HOME_FRAGMENT).commit();
                    return true;

//                case R.id.navigation_favorites:
//                    clearBackStack();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frag_container, FavoritesFrag.newInstance()).commit();
//                    return true;

                case R.id.navigation_settings:
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, SettingsFragment.newInstance(), SETTINGS_FRAGMENT).commit();

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

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);

        //if it is an instance, and if the instance returns true, do nothing
        if (fragment instanceof IOnBackPressed && ((IOnBackPressed) fragment).clearText()) return;
        //otherwise do something
        Log.d("SUPER", "BACKPRESSEd");
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_main_frame);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navViewBack = navView;
        fragmentManager = getSupportFragmentManager();
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));
        Bundle args = new Bundle();
        args.putString(NEW_FRAGMENT, HOME_FRAGMENT);
        fragController(args);

//        //setup for shared pref
//        //FIXME CHANGE SO THAT APP ON STARTUP DYNAMICALLY CHANGES TO DICTIONARY
//        SharedPreferences pref = getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("CurDict", "BHUTIA"); // Storing boolean - true/false
//        editor.commit(); // commit changes

        //SECTION FOR GETTING AVAILABLE DICTIONARIES AHEAD OF TIME
        if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory())){
            new File(Environment.getExternalStorageDirectory(),APP_NAME).mkdir();
        }

            //check internet connection
            if (!isOnline()) Toast.makeText(this, NO_INTERNET_ERROR, Toast.LENGTH_SHORT).show();

            //check if connection to server is possible
            myServerStatusReciever = new myServerStatusReciever();
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.registerReceiver(myServerStatusReciever, new IntentFilter(SERVER_REACHED));

            myAvailableDictionaryReciever = new myAvailableDictionaryReciever();
            localBroadcastManager.registerReceiver(myAvailableDictionaryReciever, new IntentFilter(DICTIONARY_LIST_DOWNLOADED));
//
//            //check if connection to server is possible
//            BroadcastReceiver serverReceiver = new myServerReciever();
//            IntentFilter serverfilter = new IntentFilter("SERVER_REACHED");
//            HandlerThread serverThread = new HandlerThread("SERVER_STATUS");
//            serverThread.start();
//            Looper looper = serverThread.getLooper();
//            Handler handler = new Handler(looper);
//            this.registerReceiver(serverReceiver, serverfilter, null, handler);
//
//            //begin reciever of new list
//            BroadcastReceiver downloadReceiver = new myAvailableDictionaryReciever();
//            IntentFilter downloadFilter = new IntentFilter("DICTIONARY_LIST_DOWNLOADED");
//            HandlerThread downloadThread = new HandlerThread("LANGUAGE_DOWNLOAD");
//            downloadThread.start();
//            looper = downloadThread.getLooper();
//            handler = new Handler(looper);
//            this.registerReceiver(downloadReceiver, downloadFilter, null, handler);

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable(){
            @Override
            public void run() {
                //Code that uses AsyncHttpClient
                try {
                    new DictionaryClientUsage().checkStatus(context);
                } catch (HttpBadRequestException e) {
                    e.printStackTrace();
                }

            }

        };
        mainHandler.post(myRunnable);

//
//
//        Connectivity connectivity = new Connectivity(this);
//                connectivity.start();




    }

    @Override
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myServerStatusReciever);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myAvailableDictionaryReciever);

        super.onDestroy();

    }



}
