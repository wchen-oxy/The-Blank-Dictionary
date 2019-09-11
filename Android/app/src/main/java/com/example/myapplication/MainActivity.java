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
                    return true;
                case R.id.navigation_favorites:
                    fragmentManager.popBackStack();
                    FavoritesFrag favoritesFrag = new FavoritesFrag();
//                    fragmentManager = getSupportFragmentManager();
                    favoritesTransaction = fragmentManager.beginTransaction();
                    favoritesTransaction.addToBackStack(null);
                    favoritesTransaction.add(R.id.frag_container, favoritesFrag).commit();
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.popBackStack();

//                    mTextMessage.setText(R.string.title_notifications);
//                    System.out.println(getSupportFragmentManager().getFragments());
                    SettingsFrag settingsFrag = new SettingsFrag();
//                    fragmentManager = getSupportFragmentManager();
                    settingsTransaction = fragmentManager.beginTransaction();
                    settingsTransaction.addToBackStack(null);
                    settingsTransaction.add(R.id.frag_container, settingsFrag).commit();

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
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final SearchView mainSearchBar = findViewById(R.id.searchView);
        fragmentManager = getSupportFragmentManager();
        final String TAG = "MyActivity";


        mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            @Override
//            public boolean onQueryTextSubmitted(String query) {
//                // Called when the user submits the query.
//                return true;
//            }
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//                System.out.println(s);
//                intent.setAction(Intent.ACTION_SEARCH);
//                intent.putExtra(SearchManager.QUERY, s);
//                startActivity(intent);



                //fragment shit
                SearchFrag searchFrag = new SearchFrag();
                Bundle arguments = new Bundle();
                arguments.putString( "query", s);
                searchFrag.setArguments(arguments);
                searchTransaction = fragmentManager.beginTransaction();
                searchTransaction.addToBackStack(null);
                searchTransaction.replace(R.id.frag_container, searchFrag).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.
                return true;
            }
        });


    }

}
