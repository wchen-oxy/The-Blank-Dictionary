package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.room.Room;

import com.example.myapplication.CustomTransSpinAdaptor;
import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Fragments.dummy.DummyContent;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Query;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//FIXME ADD ENUM FOR THE LANGAUGE TYPES IN SEPARATE FILE
//external package

public class SearchFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    Intent intent;
    String text = null;
    SearchView searchView;
    AppDatabase db;
    Bundle args;
    List results;
    private BhutiaWordFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";
private static class QueryParam{
    QueryParam(){

    }

}

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        intent = new Intent(getActivity(), SearchActivity.class);
        if (savedInstanceState != null) text = (String) savedInstanceState.getSerializable("query");
        Log.d("SavedState", "AAA" + text);


//        //create db connection
//        //FIXME
        //new thread for a query
        //query consists of search term, translation direction,
        args = getArguments();
        Log.d("Rez", args.get("query").toString());



        try {
            results = new Query(getContext()).execute(args).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            results = new Query().execute(full_query).get();
        for (Object bw:results){
            BhutiaWord b = (BhutiaWord) bw;
            Log.d("RESUKTS", b.romanization);
        }



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adv_search, container,false);
        Context context = rootView.getContext();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        searchView = view.findViewById(R.id.searchAdvView);

        //Any query text is cleared when iconified. So setIconified to false.


//        if (args == null)  {
//            String persistentVariable = savedInstanceState.getString(PERSISTENT_VARIABLE_BUNDLE_KEY);
//
//            searchView.setQuery(persistentVariable, false);}
//        else {
        searchView.setQuery(args.getString("query"), true);
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

//Begin third
        CustomTransSpinAdaptor<String> adapter = new CustomTransSpinAdaptor<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Bhutia to English", "Tibetan to Bhutia"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BhutiaWordFragment.OnListFragmentInteractionListener) {
            mListener = (BhutiaWordFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
//         intent.putExtra("Translation", (parent.getItemAtPosition(pos).toString()));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}

