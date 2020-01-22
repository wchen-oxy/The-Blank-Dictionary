package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.CustomTransSpinAdaptor;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaLayout;
import com.example.myapplication.Dictionaries.English.EnglishLayout;
import com.example.myapplication.LayoutSetter;
import com.example.myapplication.R;
import com.example.myapplication.DumbSpinnerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String curDict;
    private Bundle args;
    private View.OnTouchListener touchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Log.d("TOuch", "FIRES");
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



    public ResultFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ResultFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(Bundle args) {
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(args);
        return resultFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        SharedPreferences pref = getContext().getSharedPreferences("BlankDictPref", 0);
        curDict = pref.getString("CurDict", null);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.final_result, container,false);

        //initialize return listener
        rootView.findViewById(R.id.final_result_frag).setOnClickListener(myListener);

        //set spinner return listener
        Spinner spinner = rootView.findViewById(R.id.final_result_adv_trans_spinner);
        DumbSpinnerAdapter dumbSpinnerAdapter = new DumbSpinnerAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, myListener);
        dumbSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnTouchListener(touchListener);

//        TextView textView = rootView.findViewById(android.R.id.text1);
//        textView.setOnClickListener(myListener);

        spinner.setAdapter(dumbSpinnerAdapter);


        //setting results
        ScrollView dictInfoContainer = rootView.findViewById(R.id.dict_info_container);

//        View view = inflater.inflate(R.layout.z_final_bhutia, null);
//        dictInfoContainer.addView(view);

        LayoutSetter layoutSetter = null;
        switch (curDict){
            case ("BHUTIA"):
                layoutSetter = new BhutiaLayout(getContext(), inflater, args);
                break;
            case ("ENGLISH"):
                layoutSetter = new EnglishLayout(getContext(),inflater, args);
                break;

        }
        dictInfoContainer.addView(layoutSetter.getDictionaryLayout().returnView());

        return rootView;


    }




    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
