package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Fragments.LanguagePackFrag;
import com.example.myapplication.R;

public class SettingsFrag extends Fragment {

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.settings_frag, container,false);
        Button languagePack = rootView.findViewById(R.id.language_button);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction langFragTransaction;
        langFragTransaction = fragmentManager.beginTransaction();

        languagePack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        langFragTransaction.addToBackStack(null);
                        LanguagePackFrag languagePackFrag = new LanguagePackFrag();
                        langFragTransaction.replace(R.id.frag_container, languagePackFrag).commit();

                    }
                });

        return rootView;
    }


}
