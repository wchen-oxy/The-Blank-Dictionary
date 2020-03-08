package com.example.myapplication.Fragments;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.SettingsListsAdapter;
import com.example.myapplication.DataSerialization;
import com.example.myapplication.HelperInterfaces.IDelete;
import com.example.myapplication.R;
import java.io.File;
import java.util.ArrayList;
import static com.example.myapplication.Constants.System.APP_DICTIONARY_FILE;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.DATABASE;
import static com.example.myapplication.Constants.System.DATABASE_CLEARED;


public class DictionarySelectionFragment extends Fragment {
    SharedPreferences pref;
    IDelete iDelete;
    Context mContext;
    Boolean editing;
    RecyclerView languagesRecyclerView;
    View rootView;
    BroadcastReceiver broadcastReceiver;
    SettingsListsAdapter settingsListsAdapter;
    ArrayList<String> installed;


    public static DictionarySelectionFragment newInstance() {

        Bundle args = new Bundle();
        DictionarySelectionFragment fragment = new DictionarySelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pref = context.getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode;
        mContext = context;
        editing = false;
        iDelete = (IDelete) context;
        installed = new ArrayList<>();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                installed.clear();
                checkInstalledLanguage(pref);
                settingsListsAdapter.notifyDataSetChanged();
            }
        };
        checkInstalledLanguage(pref);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dictionary_selection, container, false);
        languagesRecyclerView = rootView.findViewById(R.id.dict_pack_recyclerview);
        languagesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        languagesRecyclerView.setLayoutManager(layoutManager);
        settingsListsAdapter = new SettingsListsAdapter(mContext, installed);
        languagesRecyclerView.setAdapter(settingsListsAdapter);

        final Button delete = rootView.findViewById(R.id.dict_pack_delete);
        final Button edit = rootView.findViewById(R.id.dict_pack_edit);
        final ObjectAnimator mLeftAnimation = ObjectAnimator.ofFloat(languagesRecyclerView, "translationX", 130f);
        mLeftAnimation.setDuration(500);
        final ObjectAnimator mRightAnimation = ObjectAnimator.ofFloat(languagesRecyclerView,"translationX", 0);
        mRightAnimation.setDuration(500);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editing) {
                    settingsListsAdapter.makeCheckboxVisible(true);
                    delete.setVisibility(View.VISIBLE);
                    delete.setClickable(true);
                    editing = true;
                    return;
                }
                else{
                    settingsListsAdapter.makeCheckboxVisible(false);
                    delete.setVisibility(View.INVISIBLE);
                    delete.setClickable(false);
                    editing = false;
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDelete.delete();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(DATABASE);
        filter.addAction(DATABASE_CLEARED);
        mContext.registerReceiver(broadcastReceiver, filter);
    }



    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iDelete.clearLangToDeleteList();
    }

    private void checkInstalledLanguage(SharedPreferences pref){
        ArrayList<String> list = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + APP_DICTIONARY_FILE));
        for (String lang: list){
            if (pref.getBoolean(lang, false)) installed.add(lang);
        }
    }

}
