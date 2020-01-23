package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.example.myapplication.Serialization;

import java.io.File;
import java.util.ArrayList;

public class DictionarySelectionFrag extends Fragment {
    Context mContext;

    public static DictionarySelectionFrag newInstance() {

        Bundle args = new Bundle();
        DictionarySelectionFrag fragment = new DictionarySelectionFrag();
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
        View rootView = inflater.inflate(R.layout.dictionary_selection, container,false);


        SharedPreferences pref = getActivity().getSharedPreferences("BlankDictPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        //new one
        ArrayList<String> list = Serialization.deserializer(new File(Environment.getExternalStorageDirectory()+"/BlankDictionary/list.json"));
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
            button.setTypeface(Typeface.create("alegreya_sans", Typeface.NORMAL));
            button.setText(s);
            button.getBackground().setAlpha(0);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.resize_arrow, 0);
            button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Button b = (Button) v;
                            editor.putString("CurDict", b.getText().toString()); // Storing String
                            editor.commit(); // commit changes
                            Toast.makeText(mContext, "Dictionary Selected", Toast.LENGTH_SHORT).show();
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
