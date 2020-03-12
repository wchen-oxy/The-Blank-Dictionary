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
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.SettingsListsAdapter;
import com.example.myapplication.DataSerialization;
import com.example.myapplication.HelperInterfaces.IDelete;
import com.example.myapplication.Adapters.AdapterHelpers.MyDetailsLookup;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;

import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.DATABASE;
import static com.example.myapplication.Constants.System.DATABASE_CLEARED;
import static com.example.myapplication.Constants.System.DATABASE_UPDATED;


public class DictionarySelectionFragment extends Fragment {
    SharedPreferences pref;
    IDelete iDelete;
    Context mContext;
    Boolean isCurrentlyEditing;
    RecyclerView languagesRecyclerView;
    View rootView;
    BroadcastReceiver refreshListBroadcastReciever;
    SettingsListsAdapter settingsListsAdapter;
    Button delete;
    ArrayList<String> available;
    public static boolean DOWNLOAD_IN_PROGRESS;


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
        isCurrentlyEditing = false;
        iDelete = (IDelete) context;
        DOWNLOAD_IN_PROGRESS = false;

        refreshListBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


//                checkInstalledLanguage(pref);
                if (isCurrentlyEditing) {
                    isCurrentlyEditing = false;
                    settingsListsAdapter.makeCheckboxVisible(false);
                    delete.setVisibility(View.GONE);
                }
                DOWNLOAD_IN_PROGRESS = false;
                settingsListsAdapter.notifyDownloadComplete();
                settingsListsAdapter.notifyDataSetChanged();
            }
        };
        available = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + "/BlankDictionary/list.json"));

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
        settingsListsAdapter = new SettingsListsAdapter(mContext, getActivity(), available, DOWNLOAD_IN_PROGRESS);
        languagesRecyclerView.setAdapter(settingsListsAdapter);

        //FIXME: IMPLEMENT RECYCLERVIEW SELECTOR
//        //Recyclerview selector
//        SelectionTracker tracker = new SelectionTracker.Builder<>(
//            "lanuguage",
//                languagesRecyclerView,
//                new StableIdKeyProvider(languagesRecyclerView),
//                new MyDetailsLookup(languagesRecyclerView),
//                StorageStrategy.createLongStorage())
//                .build();





        delete = rootView.findViewById(R.id.dict_pack_delete);
        final ImageButton edit = rootView.findViewById(R.id.dict_pack_edit);
        final ObjectAnimator mLeftAnimation = ObjectAnimator.ofFloat(languagesRecyclerView, "translationX", 130f);
        mLeftAnimation.setDuration(500);
        final ObjectAnimator mRightAnimation = ObjectAnimator.ofFloat(languagesRecyclerView, "translationX", 0);
        mRightAnimation.setDuration(500);
//        System.out.println("INSTALLED: " + installed.get(0));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Edit Clicked");


                if (!isCurrentlyEditing) {
                    System.out.println("Edit true");

                    settingsListsAdapter.makeCheckboxVisible(true);
                    delete.setVisibility(View.VISIBLE);
                    delete.setClickable(true);
                    isCurrentlyEditing = true;
                    return;
                } else {
                    settingsListsAdapter.makeCheckboxVisible(false);
                    delete.setVisibility(View.INVISIBLE);
                    delete.setClickable(false);
                    isCurrentlyEditing = false;
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Delete Clicked");
                iDelete.delete();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter deleteFilter = new IntentFilter(DATABASE);
        deleteFilter.addAction(DATABASE_CLEARED);
        mContext.registerReceiver(refreshListBroadcastReciever, deleteFilter);

        IntentFilter refreshFilter = new IntentFilter(DATABASE);
        refreshFilter.addAction(DATABASE_UPDATED);
        mContext.registerReceiver(refreshListBroadcastReciever, refreshFilter);

        settingsListsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(refreshListBroadcastReciever);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iDelete.clearLangToDeleteList();
    }

//    private void checkInstalledLanguage(SharedPreferences pref) {
//        ArrayList<String> list = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + APP_DICTIONARY_FILE));
//        for (String lang : list) {
//            if (pref.getBoolean(lang, false)) installed.add(lang);
//        }
//    }

}
