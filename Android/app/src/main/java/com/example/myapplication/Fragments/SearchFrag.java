package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.CustomTransSpinAdaptor;
import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Query;
import com.example.myapplication.R;
import com.example.myapplication.MyAdapter;
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
    private MyAdapter.OnItemClickListener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) text = (String) savedInstanceState.getSerializable("query");

        args = getArguments();
        listener = new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BhutiaWord item) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }
        };

        try {
            results = new Query(getContext()).execute(args).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adv_search, container,false);
        Context context = rootView.getContext();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(results, listener);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        searchView = view.findViewById(R.id.searchAdvView);
        //Any query text is cleared when iconified. So setIconified to false.
        searchView.setQuery(args.getString("query"), true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.
                //create new thread that runs simultaneously
                args.putString("query", newText);

                //pass in newText paramater into thread

                try {
                    results = new Query(getContext()).execute(args).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mAdapter = new MyAdapter(results, listener);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setAdapter(new MyAdapter(results, listener));
                return false;
            }
        });
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        Spinner spinner = getView().findViewById(R.id.adv_trans_spinner);
        CustomTransSpinAdaptor<String> adapter = new CustomTransSpinAdaptor<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Bhutia to English", "Tibetan to Bhutia"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        String query = searchView.getQuery().toString();
        MainActivity setter = (MainActivity) getActivity();
        setter.query = query;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("query", text);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         System.out.println(parent.getItemAtPosition(pos));
         args.putString("TRANSLATION", parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}

