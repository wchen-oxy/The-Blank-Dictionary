package com.blankdictionary.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankdictionary.myapplication.BroadcastRecievers.myAvailableDictionaryReciever;
import com.blankdictionary.myapplication.BroadcastRecievers.myServerStatusReciever;
import com.blankdictionary.myapplication.DataDownload.DictionaryClientUsage;
import com.blankdictionary.myapplication.DataDownload.HttpBadRequestException;
import com.blankdictionary.myapplication.DataDownload.NetworkCheck;
import com.blankdictionary.myapplication.DialogFragments.FilePermissionDialogFragment;
import com.blankdictionary.myapplication.Fragments.AboutFragment;
import com.blankdictionary.myapplication.Fragments.DictionarySelectionFragment;
import com.blankdictionary.myapplication.Fragments.HomeFragment;
import com.blankdictionary.myapplication.Fragments.ResultFragment;
import com.blankdictionary.myapplication.Fragments.SearchFragment;
import com.blankdictionary.myapplication.Fragments.SettingsFragment;
//import com.blankdictionary.myapplication.Fragments.TestHomeFrag;
import com.blankdictionary.myapplication.HelperInterfaces.IDelete;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.HelperInterfaces.IOnBackPressed;
import com.blankdictionary.myapplication.HelperInterfaces.IShowDialogWarning;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

import static com.blankdictionary.myapplication.Constants.Fragment.ABOUT_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.DICTIONARY_SELECTION_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.HOME_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.RESULT_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.SETTINGS_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.IntentFilters.DICTIONARY_LIST_DOWNLOADED;
import static com.blankdictionary.myapplication.Constants.IntentFilters.SERVER_REACHED;
import static com.blankdictionary.myapplication.Constants.Network.NO_INTERNET_ERROR;
import static com.blankdictionary.myapplication.Constants.System.APP_NAME;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class MainActivity extends AppCompatActivity implements IFragmentCommunicator, IDelete, IShowDialogWarning {
    final Context context = this;
    public BroadcastReceiver availableDictionaryReciever;
    public Boolean isAdvSearch = false;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationMenu;
    myServerStatusReciever myServerStatusReciever;
    LocalBroadcastManager localBroadcastManager;
    Bundle args;
    ArrayList<String> langToDelete;
    private String activeLang = "";
    private SharedPreferences pref;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (isAdvSearch && activeLang.equals(pref.getString(CURRENTLY_SELECTED_DICTIONARY, ""))) {
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frag_container, SearchFragment.newInstance(args), SEARCH_FRAGMENT).commit();
                        return true;
                    }
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, HomeFragment.newInstance(), HOME_FRAGMENT).commit();
                    return true;

                case R.id.navigation_settings:
                    clearBackStack();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, SettingsFragment.newInstance(), SETTINGS_FRAGMENT).commit();

                    return true;
            }
            return false;
        }
    };

    //to create all fragments
    @Override
    public void bundPass(Bundle args, boolean isPause) {
        if (isPause) {
            this.args = args;
        } else {
            fragController(args);
        }
    }

    @Override
    public void showWarning() {
        new FilePermissionDialogFragment().showNow(fragmentManager, "dialog");
    }


    private void fragController(Bundle args) {
        switch (args.getString(NEW_FRAGMENT)) {
            case HOME_FRAGMENT:
                HomeFragment homeFragment = HomeFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frag_container, homeFragment, HOME_FRAGMENT).commit();
                isAdvSearch = true;
                break;
            case SEARCH_FRAGMENT:
                SearchFragment searchFragment = SearchFragment.newInstance(args);
                activeLang = pref.getString(CURRENTLY_SELECTED_DICTIONARY, null);
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
                langToDelete = new ArrayList<>();
                DictionarySelectionFragment dictionarySelectionFragment = DictionarySelectionFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, dictionarySelectionFragment).addToBackStack(DICTIONARY_SELECTION_FRAGMENT).commit();
                break;

            case ABOUT_FRAGMENT:
                AboutFragment aboutFragment = AboutFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, aboutFragment).addToBackStack(ABOUT_FRAGMENT).commit();
                break;


        }
    }

    private void clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        //if it is an instance, and if the instance returns true, do nothing
        if (fragment instanceof IOnBackPressed && ((IOnBackPressed) fragment).clearText()) {
            return;
        }
        //otherwise do something
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(APP_PREFERENCES, 0);
        setContentView(R.layout.partial_main_frame);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationMenu = navView;
        fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(NEW_FRAGMENT, HOME_FRAGMENT);
        fragController(args);

        //SECTION FOR GETTING AVAILABLE DICTIONARIES AHEAD OF TIME
        if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory())) {
            new File(Environment.getExternalStorageDirectory(), APP_NAME).mkdir();
        }
        //check internet connection
        if (!NetworkCheck.isOnline())
            Toast.makeText(this, NO_INTERNET_ERROR, Toast.LENGTH_SHORT).show();
        //check if connection to server is possible
        myServerStatusReciever = new myServerStatusReciever();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(myServerStatusReciever, new IntentFilter(SERVER_REACHED));

        availableDictionaryReciever = new myAvailableDictionaryReciever();
        localBroadcastManager.registerReceiver(availableDictionaryReciever, new IntentFilter(DICTIONARY_LIST_DOWNLOADED));

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    new DictionaryClientUsage().checkStatus(context);
                } catch (HttpBadRequestException e) {
                    e.printStackTrace();
                }
            }

        };
        mainHandler.post(myRunnable);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myServerStatusReciever);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(availableDictionaryReciever);
        super.onDestroy();

    }


    //Checkbox related functions
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            langToDelete.add(view.getTag().toString());
            System.out.println(view.getTag().toString());
        } else {
            for (int i = 0; i < langToDelete.size(); i++) {
                if (langToDelete.get(i) == view.getTag().toString()) langToDelete.remove(i);
            }
        }
    }

    @Override
    public void delete() {
        System.out.println((langToDelete));
        new DatabaseClear(this).execute(langToDelete);
    }

    @Override
    public void clearLangToDeleteList() {
        langToDelete.clear();
    }

    @Override
    public int getLangListCount() {
        return langToDelete.size();
    }


}
