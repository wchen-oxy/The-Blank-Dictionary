package com.blankdictionary.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class myTranslationSpinnerAdapter<T> extends ArrayAdapter<String> {
    private int selectedItem;
    private int outerSearchLinearLayoutWidth;

    public myTranslationSpinnerAdapter(Context context, int textViewResourceId, String[] objects,
                                       int outerSearchLinearLayoutWidth) {
        super(context, textViewResourceId, objects);
        this.outerSearchLinearLayoutWidth = outerSearchLinearLayoutWidth;

    }

    public void itemSelect(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText("");

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {


        View v = super.getDropDownView(position, null, parent);
        if (position == selectedItem) {
            v.setBackgroundColor(Color.rgb(235, 235, 235));
        } else {
            v.setBackgroundColor(Color.WHITE);
        }
        v.setMinimumWidth(outerSearchLinearLayoutWidth);

        return v;
    }

}