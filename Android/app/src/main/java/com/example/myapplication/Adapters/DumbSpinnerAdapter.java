package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DumbSpinnerAdapter extends ArrayAdapter {
    View.OnClickListener listener;

    public DumbSpinnerAdapter(@NonNull Context context, int resource, View.OnClickListener listener) {
        super(context, resource);
        this.listener = listener;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText("");
        return view;
    }
}
