package com.example.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.myapplication.SearchAdaptor;

import org.json.JSONArray;

public class SearchActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_search);


        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("THis is query: " + query);
            System.out.println("This is option" + intent.getStringExtra("Translation"));
//           TODO:implement this doMySearch(query);


        }
    }

    public JSONArray search(String query) {
        JSONArray result = new JSONArray();
        //FIXME
        return result;
    }

}
