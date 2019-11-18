package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomTransSpinAdaptor<T> extends ArrayAdapter<String> {
    int selectedItem;

    public CustomTransSpinAdaptor(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }
    public void itemSelect(int selectedItem){
        this.selectedItem = selectedItem;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText("");
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v = null;
        v = super.getDropDownView(position, null, parent);
        // If this is the selected item position
        if (position == selectedItem) {
            v.setBackgroundColor(Color.rgb(235, 235, 235));
        }
        else {
            // for other views
            v.setBackgroundColor(Color.WHITE);

        }
        return v;
    }

}