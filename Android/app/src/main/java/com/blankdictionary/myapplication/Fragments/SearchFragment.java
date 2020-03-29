package com.blankdictionary.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.blankdictionary.myapplication.Adapters.MyQueryResultAdapter;
import com.blankdictionary.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.blankdictionary.myapplication.DatabaseQuery;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishWord;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.HelperInterfaces.IOnBackPressed;
import com.blankdictionary.myapplication.MainActivity;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;

import java.lang.reflect.Field;
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
import static com.blankdictionary.myapplication.Constants.System.DROPDOWN_ROW_HEIGHT;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, IOnBackPressed {
    private int selectedTranslationNumber;
    private boolean initialLaunch = true;
    private boolean returningFromResult = false;
    private Bundle args;
    private ResultWrapper resultWrapper;
    private ArrayList<String> translationHolder = new ArrayList<>();
    private IFragmentCommunicator fragmentCommunicator;
    private myTranslationSpinnerAdapter<String> translationTypeAdapter;
    private Context mContext;
    private RecyclerView.Adapter mQueryResultAdapter;
    private Spinner translationTypeSpinner;
    private SearchView mainSearchView;
    private int dropdownRowHeight;
    private int translationWindow;
    private SharedPreferences pref;
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View item) {
            Bundle args = new Bundle();
            args.putString(NEW_FRAGMENT, RESULT_FRAGMENT);
            int QueryResultPosition = ((RecyclerView.ViewHolder) item.getTag()).getAdapterPosition();
            String queryKey = getQueryKey(resultWrapper, QueryResultPosition);
            args.putString(QUERY_ID, queryKey);
//            MyQueryResultAdapter.MyViewHolder rv = (MyQueryResultAdapter.MyViewHolder) item.getTag();
//            pref.edit().putString(TRANSLATION_STRING, rv.textView.getText().toString());
//            args.putString(TRANSLATION_TYPE_STRING,  rv.textView.getText().toString());
            System.out.println("SELECTED TRANS: " + selectedTranslationNumber);
            args.putInt(TRANSLATION_TYPE_NUM_ID, selectedTranslationNumber);
//            pref.edit().putInt(TRANSLATION_TYPE_NUM_ID, QueryResultPosition).apply();
//            Log.d("cur ", rv.textView.getText().toString());
//            mainSearchView.setQuery(TRANSLATION_STRING, false);
            returningFromResult = true;
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
        mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getArguments();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        dropdownRowHeight = pref.getInt(DROPDOWN_ROW_HEIGHT, 500);

        //pass info to activity in case user switches out by bottom nav
        fragmentCommunicator.bundPass(args, true);
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        String[] translationTypesArray = Translation.getSet(context);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        LayoutManager layoutManager = new LinearLayoutManager(context);
        mainSearchView = view.findViewById(R.id.searchAdvView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        System.out.println("ONCREATEVIEW");
        //INITIALIZE TRANSLATION
        if (initialLaunch) selectedTranslationNumber = args.getInt(TRANSLATION_TYPE_NUM_ID);
        System.out.println(Arrays.toString(translationTypesArray));
        if (!translationHolder.isEmpty()) translationHolder.clear();
        System.out.println(translationTypesArray[selectedTranslationNumber]);
        //we need to add translation data inside ArrayList because we need a reference to the
        //container object
        translationHolder.add(translationTypesArray[selectedTranslationNumber]);
        //CREATE RESULT ADAPTER
        mQueryResultAdapter = new MyQueryResultAdapter(resultWrapper, translationHolder, listener, pref.getString(CURRENTLY_SELECTED_DICTIONARY, ""));
        recyclerView.setAdapter(mQueryResultAdapter);

        //You need to inflate the Fragment's view and call findViewById() on the View it returns.
        translationTypeSpinner = view.findViewById(R.id.adv_trans_spinner);

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
                    mQueryResultAdapter.notifyDataSetChanged();
                }
                fragmentCommunicator.bundPass(args, true);
                return false;
            }
        });

        mainSearchView.setIconifiedByDefault(false);
        mainSearchView.clearFocus();
        try {

            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(translationTypeSpinner);
            // Set popupWindow height to 500px
            System.out.println("Row Height " + dropdownRowHeight);
            popupWindow.setHeight(dropdownRowHeight);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.d("ERROR", "getting default style attribute");
        }
        /** FIXME ADDING THIS THREAD WILL RELOAD THE THING**/
//        String[] translationTypesArray = Translation.getSet(mContext);
        translationTypeAdapter = new myTranslationSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, translationTypesArray, selectedTranslationNumber, 500);
        translationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translationTypeSpinner.setAdapter(translationTypeAdapter);
        translationTypeSpinner.setOnItemSelectedListener(this);
        translationTypeSpinner.setSelection(selectedTranslationNumber);

//        final LinearLayout myLayout = view.findViewById(R.id.adv_search_linear_layout);
//        if (initialLaunch) myLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("TEST", "Layout width : " + mainSearchView.getWidth());
//
//            }
//        });
//        if (initialLaunch) {
//        myLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("TEST", "Layout width : " + mainSearchView.getWidth());
//                refreshDropdown(myLayout.getWidth());
//            }
//        });
//        }
    }


    //item selected for avail. translations
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        System.out.println("Made it do OnItem");

        if (initialLaunch) {
            System.out.println("INITIAL");
            //initialLaunch selection
            System.out.println("Initial Translation: " + selectedTranslationNumber);
            translationTypeAdapter.notifyNewSelectedItem(selectedTranslationNumber);

            initialLaunch = false;
        } else {

            if (returningFromResult) {
                returningFromResult = false;
                return;
            }

            System.out.println("NOT");
            System.out.println(selectedTranslationNumber);

                System.out.println("Selected else");
                selectedTranslationNumber = pos;



            System.out.println("TRANSLATION TYPE Num " + selectedTranslationNumber);
            System.out.println("TRANSLATION TYPE " + Translation.getSet(mContext)[selectedTranslationNumber]);

            translationTypeAdapter.notifyNewSelectedItem(selectedTranslationNumber);
            args.putInt(TRANSLATION_TYPE_NUM_ID, selectedTranslationNumber);

            refreshResult();
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        pref.edit().putInt(TRANSLATION_TYPE_NUM_ID, selectedTranslationNumber).apply();
//    }

    private void refreshResult() {
        resultWrapper.getList().getResult().clear();
        try {

            //add back into existing ResultWrapper because the adapter needs the original reference to the ResultWrapper
            if (!(args.getString(QUERY).isEmpty())) {
                System.out.println("Refresh Inner");
                 returnList();
//                System.out.println(refreshedList.get(0));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        translationHolder.clear();
        mQueryResultAdapter.notifyDataSetChanged();
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }

//    private String[] translationSet() {
//        String[] tranSet = null;
//        switch (pref.getString(CURRENTLY_SELECTED_DICTIONARY, null)) {
//            case (BHUTIA):
//                tranSet = getResources().getStringArray(R.array.bhutia_array);
//                break;
//            case (ENGLISH):
//                tranSet = getResources().getStringArray(R.array.english_array);
//                break;
//
//        }
//        return tranSet;
//    }

    private void refreshDropdown(int myLayoutWidth) {
        String[] translationTypesArray = Translation.getSet(mContext);
        translationTypeAdapter = new myTranslationSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, translationTypesArray, selectedTranslationNumber, myLayoutWidth);
        translationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translationTypeSpinner.setAdapter(translationTypeAdapter);
        translationTypeSpinner.setOnItemSelectedListener(this);

        int leftPadding = -1 * mainSearchView.getWidth();
        translationTypeSpinner.setPadding(leftPadding, 0, translationTypeSpinner.getPaddingRight(), 0);

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



//    @Override
//    public void onResume() {
//        super.onResume();
//        final String searchPlaceholder = pref.getString(TRANSLATION_STRING, "");
//        if (!searchPlaceholder.isEmpty())
//
//        {mainSearchView.post(
//
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mainSearchView.setQuery(searchPlaceholder, false);
//                    }
//                    });
//                }
//
//    }

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
}



