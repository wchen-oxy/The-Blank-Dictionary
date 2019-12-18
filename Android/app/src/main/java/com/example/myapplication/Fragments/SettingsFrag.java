package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.FragmentCommunicator;
import com.example.myapplication.Fragments.LanguagePackFrag;
import com.example.myapplication.R;

public class SettingsFrag extends Fragment {
    FragmentCommunicator fragmentCommunicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (FragmentCommunicator) context;

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.settings_frag, container,false);
        Button languagePack = rootView.findViewById(R.id.language_button);
        Button currentDictionary = rootView.findViewById(R.id.current_dictionary);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction langFragTransaction;
        langFragTransaction = fragmentManager.beginTransaction();

        languagePack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        langFragTransaction.addToBackStack(null);
//                        LanguagePackFrag languagePackFrag = new LanguagePackFrag();
//                        langFragTransaction.replace(R.id.frag_container, languagePackFrag).addToBackStack(null).commit();
                        Bundle args = new Bundle();
                        args.putString("NEW_FRAGMENT", "LANG_DOWNLOAD_FRAGMENT");
                        fragmentCommunicator.bundPass(args, false);

                    }
                });
        currentDictionary.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("NEW_FRAGMENT", "DICT_SELECT_FRAGMENT");
                        fragmentCommunicator.bundPass(args, false);
//                        DictionarySelectionFrag dictionarySelectionFrag = DictionarySelectionFrag.newInstance();
//                        langFragTransaction.replace(R.id.frag_container, dictionarySelectionFrag).addToBackStack(null).commit();
//                        Toast.makeText(getActivity(), "Dict Changed", Toast.LENGTH_SHORT).show();
//                        SharedPreferences pref = getActivity().getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
//                        SharedPreferences.Editor editor = pref.edit();
////                        editor.putString("CurDict", "BHUTIA"); // Storing boolean - true/false
//                        editor.commit(); // commit changes
                    }
                }
        );


        return rootView;
    }


}
