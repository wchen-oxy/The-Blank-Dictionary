package com.example.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;

//external package

public class SearchFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    Intent intent;
    String text = null;
    SearchView searchView;
//    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        intent = new Intent(getActivity(), SearchActivity.class);
        if (savedInstanceState != null) text = (String) savedInstanceState.getSerializable("query");
        Log.d("SavedState", "AAA" + text);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adv_search, container,false);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        searchView = view.findViewById(R.id.searchAdvView);
        Bundle args = getArguments();
        //Any query text is cleared when iconified. So setIconified to false.


//        if (args == null)  {
//            String persistentVariable = savedInstanceState.getString(PERSISTENT_VARIABLE_BUNDLE_KEY);
//
//            searchView.setQuery(persistentVariable, false);}
//        else {
        searchView.setQuery(args.getString("query"), false);
//        }
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));
        Log.d("tag", "QUERY: " + args.getString("query"));

        //prevent automatically bringing up the keyboard when you move to the fragment
//        searchView.setIconifiedByDefault(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("d", "CLEARED");

//                searchView.setIconified(true);
//                searchView.setFocusable(true);
//                searchView.setIconified(false);
//                searchView.clearFocus();
//                searchView.requestFocus();
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s);
//                intent.setAction(Intent.ACTION_SEARCH);
//                intent.putExtra(SearchManager.QUERY, s);
//                startActivity(intent);
//                text = s;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.


                return true;
            }
        });

        //onbackpress


        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
//



        //second part

        Spinner spinner = getView().findViewById(R.id.adv_trans_spinner);

//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

////        Begin Custom Adaptor
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = CustomTransSpinAdaptor.createFromResource(getActivity(),
//                R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        System.out.println("FRAGMENT");
//        spinner.setAdapter(adapter);

//
//        //Begin Second Custom Adaptor
////         Create an ArrayAdapter using the string array and a default spinner layout
//        CustomTransSpinAdaptor<CharSequence> adapter = new CustomTransSpinAdaptor<CharSequence>(getActivity(),
//                R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
////        ArrayAdapter<CharSequence> adapter = CustomTransSpinAdaptor.createFromResource(getActivity(),
////                R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
//
//        System.out.println("the count is " + adapter.getCount());
////         Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);

//Begin third
        CustomTransSpinAdaptor<String> adapter = new CustomTransSpinAdaptor<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Tibetan to Bhutia", "Bhutia to English"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onPause() {
        Log.d("d", "HEY");

        super.onPause();
        String query = searchView.getQuery().toString();
        Log.d("d", "QUERY: " + query);
        MainActivity setter = (MainActivity) getActivity();
        setter.query = query;



//        getArguments().putString(PERSISTENT_VARIABLE_BUNDLE_KEY, query);
    }
    //Click Listeners for the Translation Option


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("query", text);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
         System.out.println(parent.getItemAtPosition(pos));
         intent.putExtra("Translation", (parent.getItemAtPosition(pos).toString()));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }





}
