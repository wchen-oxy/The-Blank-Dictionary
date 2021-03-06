package com.blankdictionary.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.blankdictionary.myapplication.Adapters.MyQueryResultAdapter;
import com.blankdictionary.myapplication.DatabaseQuery;
import com.blankdictionary.myapplication.DialogFragments.TranslationDialogFragment;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishWord;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.HelperInterfaces.IOnBackPressed;
import com.blankdictionary.myapplication.HelperInterfaces.ITranslationDialogListener;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY;
import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY_ID;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_TYPE_NUM_ID;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.RESULT_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class SearchFragment extends Fragment implements IOnBackPressed, ITranslationDialogListener {
    private Bundle args;
    private ResultWrapper resultWrapper;
    private ArrayList<String> translationHolder = new ArrayList<>();
    private IFragmentCommunicator fragmentCommunicator;
    private MyQueryResultAdapter queryResultAdapter;
    private SearchView mainSearchView;
    private SharedPreferences pref;
    private String[] translationTypesArray;
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View item) {
            args.putString(NEW_FRAGMENT, RESULT_FRAGMENT);
            int QueryResultPosition = ((RecyclerView.ViewHolder) item.getTag()).getAdapterPosition();
            String queryKey = getQueryKey(resultWrapper, QueryResultPosition);
            args.putString(QUERY_ID, queryKey);
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
        //initialLaunchize to return args back to activity/start new fragment
        fragmentCommunicator = (IFragmentCommunicator) context;
        translationTypesArray = Translation.getSet(context);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //pass info to activity in case user switches out by bottom nav
        fragmentCommunicator.bundPass(args, true);
        try {
            if (!args.getString(QUERY, "").isEmpty())
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int selectedTranslationNumber;
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_queries);
        LayoutManager layoutManager = new LinearLayoutManager(context);
        mainSearchView = view.findViewById(R.id.searchview_adv_search);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //INITIALIZE TRANSLATION
        selectedTranslationNumber = args.getInt(TRANSLATION_TYPE_NUM_ID, 0);
        System.out.println(Arrays.toString(translationTypesArray));
        System.out.println(translationTypesArray[selectedTranslationNumber]);
        //we need to add translation data inside ArrayList because we need a reference to the
        //container object
        //CREATE RESULT ADAPTER

        queryResultAdapter = new MyQueryResultAdapter(resultWrapper,
                translationTypesArray[selectedTranslationNumber],
                listener,
                pref.getString(CURRENTLY_SELECTED_DICTIONARY,
                        ""));

        recyclerView.setAdapter(queryResultAdapter);

        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        ImageButton translationTypeButton = view.findViewById(R.id.imagebutton_adv_translations);
//        Spinner translationTypeSpinner = view.findViewById(R.id.adv_trans_spinner);

        //Any query text is cleared when iconified. So setIconified to false.
        mainSearchView.setQuery(args.getString(QUERY), true);
        mainSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mainSearchView.clearFocus();
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
                    queryResultAdapter.notifyDataSetChanged();
                }
                fragmentCommunicator.bundPass(args, true);
                return false;
            }
        });

        mainSearchView.setIconifiedByDefault(false);
        mainSearchView.clearFocus();
//

        translationTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TranslationDialogFragment translationDialogFragment = new TranslationDialogFragment(args.getInt(TRANSLATION_TYPE_NUM_ID, 0));
                translationDialogFragment.setTargetFragment(SearchFragment.this, 0);
                translationDialogFragment.show(getActivity().getSupportFragmentManager(), "Warning");
            }
        });


    }


    private void refreshResult() {
        System.out.println("Refresh");
        resultWrapper.getList().getResult().clear();
        try {

            //add back into existing ResultWrapper because the adapter needs the original reference to the ResultWrapper
            if (!(args.getString(QUERY, "").isEmpty())) {
                returnList();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        queryResultAdapter.notifyTranslation(translationTypesArray[args.getInt(TRANSLATION_TYPE_NUM_ID)]);
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


    @Override
    public boolean clearText() {
        if (!mainSearchView.getQuery().toString().isEmpty()) {
            mainSearchView.setQuery("", true);
            mainSearchView.setIconified(true);

            return true;
        }
        return false;

    }

    private void returnList() throws ExecutionException, InterruptedException {
        List resultList;
        resultWrapper.getList().getResult().clear();
        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
            case (BHUTIA):
                resultList = new DatabaseQuery(getContext()).execute(args).get().getList().getResult();
                for (BhutiaWord bhutiaWord : (List<BhutiaWord>) resultList) {
                    System.out.println(bhutiaWord.eng_trans);
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
    }

    @Override
    public void sendSelectedTranslation(int pos) {
        System.out.println("Tran " + pos);
//        selectedTranslationNumber = pos;
        args.putInt(TRANSLATION_TYPE_NUM_ID, pos);
        refreshResult();
    }
}



