package com.blankdictionary.myapplication;

import android.content.Context;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.blankdictionary.myapplication.Fragments.HomeFragment;
import com.blankdictionary.myapplication.Fragments.SearchFragment;

import static com.blankdictionary.myapplication.Constants.Fragment.HOME_FRAGMENT;

public class SetAdapterThread extends Thread {
    Context mContext;
    Spinner spinner;
    String[] translationTypes;
    int outerLayoutWidth;
    myTranslationSpinnerAdapter<String> mAdapter;
    Fragment fragment;
    HomeFragment homeFragment;
    SearchFragment searchFragment;

    public SetAdapterThread(Context mContext, myTranslationSpinnerAdapter<String> mAdapter,
            Spinner spinner, String[] translationTypes, int outerLayoutWidth, Fragment fragment){
        this.mContext = mContext;
        this.mAdapter = mAdapter;
        this.spinner = spinner;
        this.translationTypes = translationTypes;
        this.outerLayoutWidth = outerLayoutWidth;
        this.fragment = fragment;



    }

    public void run(){

        mAdapter = new myTranslationSpinnerAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item, translationTypes, outerLayoutWidth);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(mAdapter);
        if (fragment instanceof HomeFragment) spinner.setOnItemSelectedListener( (HomeFragment) fragment);

        else {
            spinner.setOnItemSelectedListener((SearchFragment) fragment);
        }

    }
}
