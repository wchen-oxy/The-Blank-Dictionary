package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.example.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.example.myapplication.R;
import com.example.myapplication.Translation;

import java.io.File;

import static com.example.myapplication.Constants.DictionaryData.QUERY;
import static com.example.myapplication.Constants.DictionaryData.TRANSLATION_STRING;
import static com.example.myapplication.Constants.DictionaryData.TRANSLATION_TYPE;
import static com.example.myapplication.Constants.Fragment.HOME_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.example.myapplication.Constants.System.APP_NAME;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.Toast.NO_DICT_INSTALLED_TOAST;
import static com.example.myapplication.Constants.Toast.NO_DICT_SELECTED_TOAST;

//note that this uses fragmentManager and not the SupportFragmentManager

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;
    SearchView mainSearchBar;
    IFragmentCommunicator fragmentCommunicator;
    Bundle args;
    myTranslationSpinnerAdapter<String> translationSpinnerAdapter;
    private SharedPreferences pref;
    private boolean mDictionaryInstalled = true;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (IFragmentCommunicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        args = new Bundle();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, 0);
        if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory()))
            mDictionaryInstalled = false;
        Log.d("DICT DETECT", Boolean.toString(mDictionaryInstalled));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
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


//        IN CASE YOU WANT TO USE SEARCHMANAGER AND USE ANOTHER ACTIVITY, UNCOMMENT THIS AND
//        ALSO UNCOMMENT SEARCHABLE STUFF IN ANDROID MANIFEST
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        mainSearchBar.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) != null) {
            mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (args.getString(TRANSLATION_STRING) == null)
                        args.putString(TRANSLATION_STRING, translationSpinnerAdapter.getItem(0));
                    args.putString(QUERY, s);
//                    args.putString(TRANSLATION_TYPE, translation);
                    args.putString(NEW_FRAGMENT, SEARCH_FRAGMENT);
                    Log.d(HOME_FRAGMENT, args.toString());
                    fragmentCommunicator.bundPass(args, false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            Spinner spinner = getView().findViewById(R.id.home_trans_spinner);
            String[] translationTypesArray = Translation.getSet(getContext());
            translationSpinnerAdapter = new myTranslationSpinnerAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, translationTypesArray);
            translationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(translationSpinnerAdapter);
            spinner.setOnItemSelectedListener(this);

        } else {
            mainSearchBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mDictionaryInstalled) {
                        Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                    }
                    if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) == null) {
                        Toast.makeText(getActivity(), NO_DICT_SELECTED_TOAST, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        mainSearchBar.setQuery("", false);
        mainSearchBar.setFocusable(true);
        mainSearchBar.setIconified(true);
        mainSearchBar.clearFocus();
        super.onResume();
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        args.putInt(TRANSLATION_TYPE, pos);
        args.putString(TRANSLATION_STRING, parent.getItemAtPosition(pos).toString());
        translationSpinnerAdapter.itemSelect(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }


}
