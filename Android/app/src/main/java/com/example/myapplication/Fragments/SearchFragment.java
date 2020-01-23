package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;
import com.example.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.example.myapplication.HelperInterfaces.IOnBackPressed;
import com.example.myapplication.Adapters.MyAdapter;
import com.example.myapplication.DatabaseQuery;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


//FIXME ADD ENUM FOR THE LANGAUGE TYPES IN SEPARATE FILE
//TODO AUTOQUERY WORDS WHEN TRANSLATION IS CHANGED?
//external package

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, IOnBackPressed {
    String text = null;
    SearchView searchView;
    Bundle args;
//    List results;
    ResultWrapper resultWrapper;
    Result result;
    String TRANSLATION;
    int TRANSLATION_DIRECTION;
    boolean INITIAL = true;
    String queryKey = null;
    ArrayList<String> TranslationArrayList = new ArrayList<>();


    IFragmentCommunicator fragmentCommunicator;
    myTranslationSpinnerAdapter<String> adapter;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences pref;
    private View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View item) {
                Bundle args = new Bundle();
                args.putString("NEW_FRAGMENT", "RESULT_FRAGMENT");
                int position = ((RecyclerView.ViewHolder) item.getTag()).getAdapterPosition();
                args.putString("TRANSLATION", TRANSLATION);
                args.putInt("TRANSLATION_DIRECTION", TRANSLATION_DIRECTION);
                args.putString("QUERY_ID", getQueryKey(resultWrapper, position));
                Log.d("TRANSLATION DIRECTION", String.valueOf(TRANSLATION_DIRECTION));
                Log.d("TRANSLATION", TRANSLATION);
                Log.d("QUERY_KEY", queryKey);
                fragmentCommunicator.bundPass(args, false);

        }
    };


    public static SearchFragment newInstance(Bundle args){
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(args);
        return searchFragment;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //to return args back to activity/start new fragment
        fragmentCommunicator = (IFragmentCommunicator) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) text = (String) savedInstanceState.getSerializable("query");
        args = getArguments();
        pref = getContext().getSharedPreferences("BlankDictPref", 0);

        TRANSLATION = args.getString("TRANSLATION");
        TranslationArrayList.add(TRANSLATION);
        TRANSLATION_DIRECTION = args.getInt("TRANSLATION_DIRECTION");
//        Log.d("SELECTS", String.valueOf(args.getInt("TRANSLATION_ID")));
//        Log.d("SELECTS", args.getString("TRANSLATION"));

//        Toast.makeText(getContext(), "Called again", Toast.LENGTH_LONG).show();


        try {
            if (!args.getString("query").isEmpty())
                resultWrapper = new DatabaseQuery(getContext()).execute(args).get();

//                resultWrapper.getList().getResult().add(Translation);

//                results = new Query(getContext()).execute(args).get();

                //insert your method for setting the class attribute per your result
//                setResultWrapper(results);
//                passResults = (ArrayList) results;


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Context context = rootView.getContext();
        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(resultWrapper, TranslationArrayList, listener, pref.getString("CurDict", null));
        recyclerView.setAdapter(mAdapter);


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //pass info to activity in case user switches out by bottom nav
        fragmentCommunicator.bundPass(args, true);
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
//                FragmentManager fm = getActivity()
//                        .getSupportFragmentManager();
//                fm.popBackStack("RESULT_FRAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
                if (!newText.isEmpty()) {
                    args.putString("query", newText);
                    refreshResult();

                } else {
                    args.putString("query", "");
                    args.putString("TRANSLATION", TRANSLATION);
                    resultWrapper.getList().getResult().clear();
                    mAdapter.notifyDataSetChanged();
//                    if (results != null) results.clear();
//                    recyclerView.setAdapter(mAdapter);

                }
                fragmentCommunicator.bundPass(args, true);
                return false;
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();


        String[] stringArray = translationSet();
        Spinner spinner = getView().findViewById(R.id.adv_trans_spinner);
        adapter = new myTranslationSpinnerAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
//        fragmentCommunicator.textPass("Text Successfully Passed");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("query", text);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (INITIAL) {
            System.out.println("INITIAL: " + parent.getItemAtPosition(pos));

            adapter.itemSelect(TRANSLATION_DIRECTION);
            INITIAL = false;
        } else {
            System.out.println("CUR ITEM IS " + parent.getItemAtPosition(pos));
            adapter.itemSelect(pos);
            args.putInt("TRANSLATION_DIRECTION", pos);
            TRANSLATION_DIRECTION = pos;
            TRANSLATION = parent.getItemAtPosition(pos).toString();
            //if new translation is selected, clear old irrelevant results
            resultWrapper.getList().getResult().clear();
            refreshResult();


        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

//    protected void setFragment(Fragment fragment) {
//        FragmentTransaction t = getFragmentManager().beginTransaction();
//        t.addToBackStack(null);
//        t.replace(R.id.results_frag, fragment);
//        t.commit();
//    }


//    public void setmAdapter() {
//        recyclerView.setAdapter(mAdapter);
//    }

    private String[] translationSet(){
        String[] tranSet = null;
        switch (pref.getString("CurDict", null)) {
            case ("BHUTIA"):
                tranSet = getResources().getStringArray(R.array.bhutia_array);
                break;
            case("ENGLISH"):
                tranSet = getResources().getStringArray(R.array.english_array);
                break;

        }
        return tranSet;
    }

    private String getQueryKey(ResultWrapper resultWrapper, int position){
        switch (pref.getString("CurDict", null)) {
            case ("BHUTIA"):
                BhutiaWord bhutiaWord = (BhutiaWord) resultWrapper.getList().getResult().get(position);
                queryKey = bhutiaWord.eng_trans;
                Log.d("QUERY ID", queryKey);
                break;
            case ("ENGLISH"):


                break;
        }
        return queryKey;
    }

    private void refreshResult(){
        args.putString("TRANSLATION", TRANSLATION);
        try {
            resultWrapper.getList().getResult().clear();
            //add back into existing ResultWrapper because the adapter needs the original reference to the ResultWrapper
            if (!(args.getString("query").isEmpty())) {
                List<BhutiaWord> list = new DatabaseQuery(getContext()).execute(args).get().getList().getResult();
                for (BhutiaWord bhutiaWord : list) {
                    resultWrapper.getList().getResult().add(bhutiaWord);
                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TranslationArrayList.clear();
        TranslationArrayList.add(TRANSLATION);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean clearText() {
        if (!searchView.getQuery().toString().isEmpty()){
            Log.d("Cleartext", "something here");
            Log.d("Cleartext", searchView.getQuery().toString());

            searchView.setQuery("", true);
            return true;
        }
        Log.d("Cleartext", "nothing here");

        return false;

    }
}



