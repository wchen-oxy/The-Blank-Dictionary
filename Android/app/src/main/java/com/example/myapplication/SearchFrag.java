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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         intent = new Intent(getActivity(),SearchActivity.class);


    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adv_search, container,false);
        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        final SearchView searchView = (SearchView) rootView.findViewById(R.id.searchView);
        final Bundle args = getArguments();
        //Any query text is cleared when iconified. So setIconified to false.

        searchView.setQuery(args.getString("query"), false);
        Log.d("tag", "total on stack is " + Integer.toString(getFragmentManager().getBackStackEntryCount()));

        //prevent automatically bringing up the keyboard when you move to the fragment
//        searchView.setIconifiedByDefault(false);
//        searchView.setIconified(true);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s);
//                intent.setAction(Intent.ACTION_SEARCH);
//                intent.putExtra(SearchManager.QUERY, s);
//                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.
                return true;
            }
        });

        //onbackpress

        searchView.setFocusable(false);
        searchView.setIconified(false);
        searchView.clearFocus();




// //USE THIS to measure height/width of wrap content
//        final LinearLayout below= (LinearLayout) rootView.findViewById(R.id.search_linear_layout);
//        below.post(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Log.i("TAG", "Layout width :"+ below.getWidth());
//                Log.i("TAG", "Layout height :"+ below.getHeight());
//            }
//        });

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spinner spinner = (Spinner) getView().findViewById(R.id.planets_spinner);

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



    //Click Listeners for the Translation Option

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
