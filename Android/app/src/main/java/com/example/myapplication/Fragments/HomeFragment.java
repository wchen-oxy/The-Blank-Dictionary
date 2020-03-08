package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
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
import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.example.myapplication.Constants.System.APP_NAME;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.DISABLED_COLOR;
import static com.example.myapplication.Constants.Toast.NO_DICT_INSTALLED_TOAST;
import static com.example.myapplication.Constants.Toast.NO_DICT_SELECTED_TOAST;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;
    SearchView mainSearchBar;
    IFragmentCommunicator fragmentCommunicator;
    Bundle args;
    myTranslationSpinnerAdapter<String> translationSpinnerAdapter;
    Spinner spinner;
    Context mContext;
    TextView textView;
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
        textView = new TextView(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainSearchBar = view.findViewById(R.id.searchView);
        spinner = getView().findViewById(R.id.home_trans_spinner);


        if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) != null) {
            //include this to prevent the search bar from opening keyboard
            mainSearchBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainSearchBar.setIconified(false);
                }
            });
            //actual listener for submitting text
            mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (args.getString(TRANSLATION_STRING) == null)
                        args.putString(TRANSLATION_STRING, translationSpinnerAdapter.getItem(0));
                    args.putString(QUERY, s);
                    args.putString(NEW_FRAGMENT, SEARCH_FRAGMENT);
                    fragmentCommunicator.bundPass(args, false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            String[] translationTypesArray = Translation.getSet(getContext());
            translationSpinnerAdapter = new myTranslationSpinnerAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, translationTypesArray);
            translationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(translationSpinnerAdapter);
            spinner.setOnItemSelectedListener(this);

        } else {
            mainSearchBar.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            LinearLayout outerLinearLayout = view.findViewById(R.id.outer_search_linear_layout);
            outerLinearLayout.setBackgroundColor(Color.parseColor(DISABLED_COLOR));

            if (!mDictionaryInstalled) {
                Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                outerLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) == null) {
                Toast.makeText(getActivity(), NO_DICT_SELECTED_TOAST, Toast.LENGTH_LONG).show();
                outerLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), NO_DICT_SELECTED_TOAST, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        mainSearchBar.setQuery("", false);
        mainSearchBar.setFocusable(true);
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
