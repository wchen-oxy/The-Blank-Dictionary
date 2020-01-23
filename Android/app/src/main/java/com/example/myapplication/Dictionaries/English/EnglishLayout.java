package com.example.myapplication.Dictionaries.English;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapplication.DictionaryLayoutHelper;
import com.example.myapplication.HelperInterfaces.ILayoutSetter;
import com.example.myapplication.R;

public class EnglishLayout implements ILayoutSetter {
    private DictionaryLayoutHelper dictionaryLayout;



    //constructor call from ResultFrag
    public EnglishLayout(Context context, LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_bhutia, null);
        this.dictionaryLayout = new DictionaryLayoutHelper(view);

    }

    @Override
    public DictionaryLayoutHelper getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
