package com.example.myapplication.Fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.SettingsListsAdapter;
import com.example.myapplication.DataSerialization;
import com.example.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;

import static com.example.myapplication.Constants.System.APP_DICTIONARY_FILE;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.FONT_STYLE;
import static com.example.myapplication.Constants.Toast.DICTIONARY_SELECTED_TOAST;

public class DictionarySelectionFragment extends Fragment {
    IFragmentCommunicator fragmentCommunicator;

    Context mContext;
    Boolean editing;
    RecyclerView languagesRecyclerView;
    View rootView;

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
        editing = false;
        fragmentCommunicator = (IFragmentCommunicator) context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dictionary_selection, container, false);


        SharedPreferences pref = mContext.getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode

        ArrayList<String> list = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + APP_DICTIONARY_FILE));
        languagesRecyclerView = rootView.findViewById(R.id.dict_pack_recyclerview);
        languagesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        languagesRecyclerView.setLayoutManager(layoutManager);
        final SettingsListsAdapter settingsListsAdapter = new SettingsListsAdapter(list);
        languagesRecyclerView.setAdapter(settingsListsAdapter);

//
//        final LinearLayout linearLayout = rootView.findViewById(R.id.dict_pack_list_linear_layout);
//        makeLayout(pref, linearLayout, list);
//
//        //case where there is no downloaded languages
//        TextView noListTextView = new TextView(mContext);
//        noListTextView.setText(R.string.no_list_found);
//
//        if (linearLayout.getChildCount() == 0) linearLayout.addView(noListTextView);
        final Button delete = rootView.findViewById(R.id.dict_pack_delete);
        final Button edit = rootView.findViewById(R.id.dict_pack_edit);

//        final ObjectAnimator mLeftAnimation = ObjectAnimator.ofFloat(linearLayout, "translationX", 130f);
//        mLeftAnimation.setDuration(500);
//
//        final ObjectAnimator mRightAnimation = ObjectAnimator.ofFloat(linearLayout,"translationX", 0);
//        mRightAnimation.setDuration(500);

        final ObjectAnimator mLeftAnimation = ObjectAnimator.ofFloat(languagesRecyclerView, "translationX", 130f);
        mLeftAnimation.setDuration(500);

        final ObjectAnimator mRightAnimation = ObjectAnimator.ofFloat(languagesRecyclerView,"translationX", 0);
        mRightAnimation.setDuration(500);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!editing) {
                    settingsListsAdapter.makeCheckboxVisible(true);


                    Toast.makeText(mContext, "Edit Clicked", Toast.LENGTH_SHORT).show();
//                    mLeftAnimation.start();
                    delete.setVisibility(View.VISIBLE);
                    delete.setClickable(true);
                    editing = true;
                    return;
                }
                else{
                    settingsListsAdapter.makeCheckboxVisible(false);

//                    mRightAnimation .start();
                    delete.setVisibility(View.INVISIBLE);
                    delete.setClickable(false);
                    editing = false;
                }

            }
        });


        return rootView;
    }


    private void makeLayout(SharedPreferences pref, LinearLayout linearLayout, ArrayList<String> list){
        final SharedPreferences.Editor editor = pref.edit();

        for (String s : list) {
            //if it doesn't exist, it will return false
            if (pref.getBoolean(s, false) == false) {
                continue;
            }
//            Button select = new Button(mContext);
//            select.setText("f");
//            select.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//            select.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            linearLayout.addView(select);



            Button button = new Button(mContext);
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

    }


}
