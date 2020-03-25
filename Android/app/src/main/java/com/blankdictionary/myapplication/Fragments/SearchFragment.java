package com.blankdictionary.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.Adapters.MyQueryResultAdapter;
import com.blankdictionary.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.blankdictionary.myapplication.DatabaseQuery;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishWord;
import com.blankdictionary.myapplication.Dictionaries.Result;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.HelperInterfaces.IOnBackPressed;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.SetAdapterThread;
import com.blankdictionary.myapplication.Translation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY;
import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY_ID;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_STRING;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_TYPE;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.RESULT_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, IOnBackPressed {
    SearchView searchView;
    Bundle args;
    ResultWrapper resultWrapper;
    Result result;
    String currentTranslationString;
    int selectedTranslationNumber;
    boolean initial = true;
    ArrayList<String> translationHolder = new ArrayList<>();
    IFragmentCommunicator fragmentCommunicator;
    myTranslationSpinnerAdapter<String> adapter;
    Context mContext;
    private RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    Spinner spinner;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences pref;
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View item) {
            Bundle args = new Bundle();
            args.putString(NEW_FRAGMENT, RESULT_FRAGMENT);
            int position = ((RecyclerView.ViewHolder) item.getTag()).getAdapterPosition();
            args.putString(TRANSLATION_STRING, currentTranslationString);
            args.putString(QUERY_ID, getQueryKey(resultWrapper, position));
            Log.d(TRANSLATION_TYPE, String.valueOf(selectedTranslationNumber));
            fragmentCommunicator.bundPass(args, false);

        }
    };

    public static SearchFragment newInstance(Bundle args) {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(args);
        return searchFragment;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //initialize to return args back to activity/start new fragment
        fragmentCommunicator = (IFragmentCommunicator) context;
        mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, 0);

        try {
            if (!args.getString(QUERY).isEmpty())
                resultWrapper = new DatabaseQuery(getContext()).execute(args).get();
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
        //we need to add translation data inside ArrayList because we need a reference to the
        //container object
        String[] translaltionTypesArray = Translation.getSet(context);
        if (!translationHolder.isEmpty()) translationHolder.clear();
        translationHolder.add(translaltionTypesArray[args.getInt(TRANSLATION_TYPE)]);
        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyQueryResultAdapter(resultWrapper, translationHolder, listener, pref.getString(CURRENTLY_SELECTED_DICTIONARY, null));
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
        searchView.setQuery(args.getString(QUERY), true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!newText.isEmpty()) {
                    args.putString(QUERY, newText);
                    refreshResult();

                } else {
                    args.putString(QUERY, "");
                    resultWrapper.getList().getResult().clear();
                    mAdapter.notifyDataSetChanged();

                }
                fragmentCommunicator.bundPass(args, true);
                return false;
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();

        spinner = getView().findViewById(R.id.adv_trans_spinner);

        final LinearLayout myLayout = view.findViewById(R.id.adv_search_linear_layout);

        myLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.i("TEST", "Layout width : " + searchView.getWidth());
                refreshDropdown(myLayout.getWidth());
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //item selected for avail. translations
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (initial) {
            //initial selection
            System.out.println("Initial: " + parent.getItemAtPosition(pos));
            adapter.itemSelect(selectedTranslationNumber);
            initial = false;
        } else {
            System.out.println("Current item is:  " + parent.getItemAtPosition(pos));
            adapter.itemSelect(pos);
            args.putInt(TRANSLATION_TYPE, pos);
            args.putString(TRANSLATION_STRING, parent.getItemAtPosition(pos).toString());
            resultWrapper.getList().getResult().clear();
            refreshResult();


        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    private String[] translationSet() {
        String[] tranSet = null;
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                tranSet = getResources().getStringArray(R.array.bhutia_array);
                break;
            case (ENGLISH):
                tranSet = getResources().getStringArray(R.array.english_array);
                break;

        }
        return tranSet;
    }

    private void refreshDropdown(int myLayoutWidth){
        String[] translationTypesArray = translationSet();
        adapter = new myTranslationSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, translationTypesArray, myLayoutWidth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        int leftPadding = -1 * searchView.getWidth();
        spinner.setPadding(leftPadding, 0, spinner.getPaddingRight(), 0);

    }

    private String getQueryKey(ResultWrapper resultWrapper, int position) {
        String queryKey = null;
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                BhutiaWord bhutiaWord = (BhutiaWord) resultWrapper.getList().getResult().get(position);
                queryKey = bhutiaWord.eng_trans;
                Log.d("Bhutia Query Id", queryKey);
                break;
            case (ENGLISH):
                EnglishWord englishWord = (EnglishWord) resultWrapper.getList().getResult().get(position);
                queryKey = englishWord.word;
                Log.d("English Query Id", queryKey);
                break;
        }
        return queryKey;
    }

    private void refreshResult() {
        try {
            resultWrapper.getList().getResult().clear();
            //add back into existing ResultWrapper because the adapter needs the original reference to the ResultWrapper
            if (!(args.getString(QUERY).isEmpty())) {
                List refreshedList = returnList();

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        translationHolder.clear();
        translationHolder.add(currentTranslationString);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean clearText() {
        if (!searchView.getQuery().toString().isEmpty()) {
            searchView.setQuery("", true);
            return true;
        }
        return false;

    }

    private List returnList() throws ExecutionException, InterruptedException {
        List resultList = new ArrayList();
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                resultList = new DatabaseQuery(getContext()).execute(args).get().getList().getResult();
                for (BhutiaWord bhutiaWord : (List<BhutiaWord>) resultList) {
                    resultWrapper.getList().getResult().add(bhutiaWord);
                }
                break;
            case (ENGLISH):
                resultList = new DatabaseQuery(getContext()).execute(args).get().getList().getResult();
                for (EnglishWord englishWord : (List<EnglishWord>) resultList) {
                    resultWrapper.getList().getResult().add(englishWord);
                }
                break;
        }

        return resultList;
    }
}



