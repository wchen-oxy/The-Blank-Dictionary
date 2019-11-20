package com.example.myapplication.Dictionaries.English;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.DictionaryLayout;
import com.example.myapplication.LayoutSetter;
import com.example.myapplication.R;

public class EnglishLayout implements LayoutSetter {
    private DictionaryLayout dictionaryLayout;
    private View view;



    //constructor call from ResultFrag
    public EnglishLayout(LayoutInflater inflater, ViewGroup container, Bundle args) {
        //create your layout here.
        //once created, set dictionary layout to resulting view
        view = inflater.inflate(R.layout.english_final_result, container,false);
        this.dictionaryLayout = new DictionaryLayout(view);

    }

    @Override
    public DictionaryLayout getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
