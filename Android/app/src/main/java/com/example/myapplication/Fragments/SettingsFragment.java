package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.example.myapplication.R;

import static com.example.myapplication.Constants.Fragment.DICTIONARY_SELECTION_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.LANGUAGE_DOWNLOAD_FRAGMENT;
import static com.example.myapplication.Constants.Fragment.NEW_FRAGMENT;

public class SettingsFragment extends Fragment {
    IFragmentCommunicator fragmentCommunicator;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (IFragmentCommunicator) context;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_settings, container,false);
        Button languagePack = rootView.findViewById(R.id.language_button);
        Button currentDictionary = rootView.findViewById(R.id.current_dictionary);

        languagePack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Bundle args = new Bundle();
                        args.putString(NEW_FRAGMENT, LANGUAGE_DOWNLOAD_FRAGMENT);
                        fragmentCommunicator.bundPass(args, false);

                    }
                });
        currentDictionary.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString(NEW_FRAGMENT, DICTIONARY_SELECTION_FRAGMENT);
                        fragmentCommunicator.bundPass(args, false);
                    }
                }
        );
        return rootView;
    }


}
