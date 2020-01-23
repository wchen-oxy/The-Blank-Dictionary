package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import com.example.myapplication.DataDownload.Connectivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataDownload.DictionaryClientUsage;
import com.example.myapplication.DataDownload.HttpBadRequestException;
import com.example.myapplication.Fragments.DictionarySelectionFrag;
import com.example.myapplication.Fragments.FavoritesFrag;
import com.example.myapplication.Fragments.HomeFrag;
import com.example.myapplication.Fragments.LanguagePackFrag;
import com.example.myapplication.Fragments.ResultFragment;
import com.example.myapplication.Fragments.SearchFrag;
import com.example.myapplication.Fragments.SettingsFrag;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements FragmentCommunicator{
    private TextView mTextMessage;


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView navViewBack;
    myServerReciever myServerReciever;
    LocalBroadcastManager localBroadcastManager;
    SearchView secSearch;
//    public String query;
    public BroadcastReceiver br;
    public BroadcastReceiver myListBroadcastReciever;
    public Boolean isAdvSearch = false;
    public FragmentCommunicator fragmentCommunicator;
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
                    LanguagePackFrag languagePackFrag = LanguagePackFrag.newInstance();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, languagePackFrag).addToBackStack("LANG_DOWNLOAD_FRAGMENT").commit();
                }


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

//                case R.id.navigation_favorites:
//                    clearBackStack();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frag_container, FavoritesFrag.newInstance()).commit();
//                    return true;

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

        //SECTION FOR GETTING AVAILABLE DICTIONARIES AHEAD OF TIME
        if (!(new File(Environment.getExternalStorageDirectory(), "BlankDictionary").isDirectory())){
            new File(Environment.getExternalStorageDirectory(), "BlankDictionary").mkdir();
        }

            //check internet connection
            if (!isOnline()) Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

            //check if connection to server is possible
            myServerReciever = new myServerReciever();
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.registerReceiver(myServerReciever, new IntentFilter("SERVER_REACHED"));

            myListBroadcastReciever = new myListBroadcastReciever();
            localBroadcastManager.registerReceiver(myListBroadcastReciever, new IntentFilter("DICTIONARY_LIST_DOWNLOADED"));
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
//            BroadcastReceiver downloadReceiver = new myListBroadcastReciever();
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myServerReciever);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myListBroadcastReciever);

        super.onDestroy();

    }



}
