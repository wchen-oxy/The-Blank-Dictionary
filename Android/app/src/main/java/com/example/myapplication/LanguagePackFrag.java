package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LanguagePackFrag extends Fragment {
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.lang_pack_list, container,false);
        Button languagePack = rootView.findViewById(R.id.dictionary_selected);


        languagePack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        Toast test = new Toast();
                        Toast.makeText(getActivity(),"Selected",Toast.LENGTH_SHORT).show();
                        dataDownload();

                    }
                });

        return rootView;
    }

    private void dataDownload(){
        
    }


}
