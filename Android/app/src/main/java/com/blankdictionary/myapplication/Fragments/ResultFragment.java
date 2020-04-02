package com.blankdictionary.myapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.blankdictionary.myapplication.Adapters.DumbSpinnerAdapter;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaLayout;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishLayout;
import com.blankdictionary.myapplication.HelperInterfaces.ILayoutSetter;
import com.blankdictionary.myapplication.R;

import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class ResultFragment extends Fragment {

    private String curDict;
    private Bundle args;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

            }
            return false;
        }
    };
    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        }
    };


    public static ResultFragment newInstance(Bundle args) {
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(args);
        return resultFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        SharedPreferences pref = getContext().getSharedPreferences(APP_PREFERENCES, 0);
        curDict = pref.getString(CURRENTLY_SELECTED_DICTIONARY, null);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        //initialize return listener
//        rootView.findViewById(R.id.fragment_result).setOnClickListener(myListener);
//        //set spinner return listener
        rootView.findViewById(R.id.linearlayout_result_dummy).setOnTouchListener(touchListener);
        ImageButton button = rootView.findViewById(R.id.imagebutton_result_translations);

        button.setOnClickListener(myListener);

        //setting results
        ScrollView dictInfoContainer = rootView.findViewById(R.id.dict_info_container);

        ILayoutSetter layoutSetter = null;
        switch (curDict) {
            case (BHUTIA):
                layoutSetter = new BhutiaLayout(getContext(), inflater, args);
                break;
            case (ENGLISH):
                layoutSetter = new EnglishLayout(getContext(), inflater, args);
                break;

        }
        dictInfoContainer.addView(layoutSetter.getDictionaryLayout().returnView());
        return rootView;

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
}
