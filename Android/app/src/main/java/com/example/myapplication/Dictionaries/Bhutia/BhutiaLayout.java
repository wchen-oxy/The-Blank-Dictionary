package com.example.myapplication.Dictionaries.Bhutia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.DictionaryLayout;
import com.example.myapplication.LayoutSetter;
import com.example.myapplication.R;

public class BhutiaLayout implements LayoutSetter {
    private DictionaryLayout dictionaryLayout;


    //constructor call from ResultFrag
    public BhutiaLayout(LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_bhutia, null);
        //begin filling in text with args

        this.dictionaryLayout = new DictionaryLayout(view);

    }

    @Override
    public DictionaryLayout getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
