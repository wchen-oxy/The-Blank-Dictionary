package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    System.out.println(getSupportFragmentManager().getFragments());

                    fragmentManager.popBackStack();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
        final ImageButton button = findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                //activity shit
//                Intent myIntent = new Intent(MainActivity.this,SearchActivity.class);
////                myIntent.putExtra("key", value); //Optional parameters
//                MainActivity.this.startActivity(myIntent);

                //fragment shit
                SearchFrag searchFrag = new SearchFrag();
                fragmentManager = getSupportFragmentManager();
                searchTransaction = fragmentManager.beginTransaction();
                searchTransaction.addToBackStack(null);
                searchTransaction.add(R.id.search_frag, searchFrag).commit();
            }
        });
    }

}
