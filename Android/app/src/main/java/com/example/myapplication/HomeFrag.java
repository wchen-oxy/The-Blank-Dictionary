package com.example.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    SearchView mainSearchBar;



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
        mainSearchBar = view.findViewById(R.id.searchView);

        mainSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainSearchBar.setIconified(false);
                Log.d("d", "CLEARED");

            }
        });
//        mainSearchBar.clearFocus();
//        mainSearchBar.setFocusable(false);
//        mainSearchBar.setIconified(false);

//        mainSearchBar.setEnabled(false);
//        System.out.println("IS IT ICONIFIED 2" + mainSearchBar.isIconified());
//        mainSearchBar.setIconifiedByDefault(false);
//

//        IN CASE YOU WANT TO USE SEARCHMANAGER AND USE ANOTHER ACTIVITY, UNCOMMENT THIS AND
//        ALSO UNCOMMENT SEARCHABLE STUFF IN ANDROID MANIFEST
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        mainSearchBar.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            @Override
//            public boolean onQueryTextSubmitted(String query) {
//                // Called when the user submits the query.
//                return true;
//            }

            @Override
            public boolean onQueryTextSubmit(String s) {


                //fragment shit
                SearchFrag searchFrag = new SearchFrag();
                Bundle arguments = new Bundle();
                arguments.putString( "query", s);
                searchFrag.setArguments(arguments);
                searchTransaction = fragmentManager.beginTransaction();
                searchTransaction.addToBackStack(null);
                searchTransaction.replace(R.id.frag_container, searchFrag, "ADV_SEARCH_FRAG").commit();
                MainActivity setter = (MainActivity)getActivity();
                setter.isAdvSearch = true;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.
                return true;
            }
        });

        Log.d("checker", "FINISHED");
        Log.d("second", mainSearchBar.getQuery().toString());



    }

    @Override
    public void onResume() {
       mainSearchBar.setQuery("", false);

        mainSearchBar.setFocusable(true);
        mainSearchBar.setIconified(true);
//        System.out.println("IS IT ICONIFIED 1 " + mainSearchBar.isIconified());
        mainSearchBar.clearFocus();
       super.onResume();
    }


//    @Override
//    public void onDestroyView(){
//        super.onDestroyView();
//        Log.d("thing", "destroyed");
//        SearchView mainSearchBar = viewGrab.findViewById(R.id.searchView);
//        mainSearchBar.setQuery("", false);
//        mainSearchBar.clearFocus();
//
//    }


    }
