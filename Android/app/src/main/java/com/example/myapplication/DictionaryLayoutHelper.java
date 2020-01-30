package com.example.myapplication;

import android.view.View;

public class DictionaryLayoutHelper {
    View rootView;

    //recieve custom view from each subclass
    public DictionaryLayoutHelper(View view) {
        this.rootView = view;
    }

    //    method to return view to ResultFrag
    public View returnView() {
        return rootView;
    }
}
