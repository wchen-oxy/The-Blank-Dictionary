package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.CustomTransSpinAdaptor;
import com.example.myapplication.FragmentCommunicator;
import com.example.myapplication.R;

import java.io.File;

//note that this uses fragmentManager and not the SupportFragmentManager

public class HomeFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;
    SearchView mainSearchBar;
    String TRANSLATION = null;
    FragmentCommunicator fragmentCommunicator;
    Bundle args;
    CustomTransSpinAdaptor<String> dadapter;
    private SharedPreferences pref;
    private boolean DictionaryInstalled = true;

    public static HomeFrag newInstance(){
        return new HomeFrag();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (FragmentCommunicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        args = new Bundle();
        pref = getContext().getSharedPreferences("BlankDictPref", 0);
        if (!(new File(Environment.getExternalStorageDirectory(), "BlankDictionary").isDirectory()))
            DictionaryInstalled = false;
        Log.d("DICT DETECT", Boolean.toString(DictionaryInstalled));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_frag, container, false);
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

        if (DictionaryInstalled && pref.getString("CurDict", null) != null) {
            mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (TRANSLATION == null) TRANSLATION = dadapter.getItem(0);
                    args.putString("query", s);
                    args.putString("TRANSLATION", TRANSLATION);
                    args.putString("NEW_FRAGMENT", "SEARCH_FRAGMENT");
                    Log.d("HOMEFRAG", args.toString());
                    fragmentCommunicator.bundPass(args, false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            Spinner spinner = getView().findViewById(R.id.home_trans_spinner);

//            String[] stringArray = getResources().getStringArray(R.array.bhutia_array);
            String[] stringArray = tranlationSet();
            dadapter = new CustomTransSpinAdaptor<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, stringArray);
            dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dadapter);
            spinner.setOnItemSelectedListener(this);

        } else {
            mainSearchBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!DictionaryInstalled) {
                        Toast.makeText(getActivity(), "Please Download a Dictionary from Settings First", Toast.LENGTH_LONG).show();
                    }
                    if (DictionaryInstalled && pref.getString("CurDict", null) == null) {
                        Toast.makeText(getActivity(), "Choose your current dictionary in Settings.", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private String[] tranlationSet(){
        String[] result = null;
        switch (pref.getString("CurDict", null)) {
            case ("BHUTIA"):
                result = getResources().getStringArray(R.array.bhutia_array);
                break;
            case("ENGLISH"):
                result = getResources().getStringArray(R.array.english_array);
                break;

        }
        return result;
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
        TRANSLATION = parent.getItemAtPosition(pos).toString();
        args.putInt("TRANSLATION_ID", pos);
        dadapter.itemSelect(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }


}
