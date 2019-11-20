package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

public class DictionaryLayout {
    Bundle args;
    View rootView;
    //recieve custom view from each subclass
    public DictionaryLayout(View view){
        this.rootView = view;
    }
//    method to return view to ResultFrag
    public View returnView(){
        return rootView;
    }
}
