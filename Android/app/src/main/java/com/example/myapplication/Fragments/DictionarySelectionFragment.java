package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.DataSerialization;

import java.io.File;
import java.util.ArrayList;

import static com.example.myapplication.Constants.System.APP_DICTIONARY_FILE;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.FONT_STYLE;
import static com.example.myapplication.Constants.Toast.DICTIONARY_SELECTED_TOAST;

public class DictionarySelectionFragment extends Fragment {
    Context mContext;

    public static DictionarySelectionFragment newInstance() {

        Bundle args = new Bundle();
        DictionarySelectionFragment fragment = new DictionarySelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dictionary_selection, container,false);


        SharedPreferences pref = getActivity().getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        //new one
        ArrayList<String> list = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + APP_DICTIONARY_FILE));
        LinearLayout linearLayout = rootView.findViewById(R.id.dict_pack_list_linear_layout);



        for (String s:list) {
            //if it doesn't exist, it will return false
            if (pref.getBoolean(s,false) == false) {
                Log.d("DictSelect", "Inner");
                continue;
            }
            Log.d("DictSelect", "Outer");

            //create new button
            Button button = new Button(getActivity());
            button.setTypeface(Typeface.create(FONT_STYLE, Typeface.NORMAL));
            button.setText(s);
            button.getBackground().setAlpha(0);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.resize_arrow, 0);
            button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Button b = (Button) v;
                            editor.putString(CURRENTLY_SELECTED_DICTIONARY, b.getText().toString().toUpperCase()); // Storing String
                            editor.commit(); // commit changes
                            Toast.makeText(mContext, DICTIONARY_SELECTED_TOAST, Toast.LENGTH_SHORT).show();
                        }
                    });


            if (linearLayout != null) {
                linearLayout.addView(button);
            }


        }


        //case where there is no downloaded languages
        TextView noListTextView = new TextView(mContext);
        noListTextView.setText(R.string.no_list_found);
        if (linearLayout.getChildCount() == 0) linearLayout.addView(noListTextView);

        return rootView;
    }


}
