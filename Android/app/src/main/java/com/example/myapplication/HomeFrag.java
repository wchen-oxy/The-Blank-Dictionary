package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

//note that this uses fragmentManager and not the SupportFragmentManager

public class HomeFrag extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.home_frag, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SearchView mainSearchBar = view.findViewById(R.id.searchView);
        mainSearchBar.setEnabled(false);
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
                searchTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").commit();
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
