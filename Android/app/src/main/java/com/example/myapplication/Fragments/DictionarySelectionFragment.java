package com.example.myapplication.Fragments;

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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

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

import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.DATABASE;
import static com.example.myapplication.Constants.System.DATABASE_CLEARED;
import static com.example.myapplication.Constants.System.DATABASE_UPDATED;
import static com.example.myapplication.Constants.Toast.DICTIONARY_DELETED;


public class DictionarySelectionFragment extends Fragment {
    public static boolean DOWNLOAD_IN_PROGRESS;
    SharedPreferences pref;
    IDelete iDelete;
    Context mContext;
    Boolean isCurrentlyEditing;
    RecyclerView languagesRecyclerView;
    View rootView;
    BroadcastReceiver refreshListBroadcastReciever;
    SettingsListsAdapter settingsListsAdapter;
    ImageButton close;
    ImageButton editDelete;
    ArrayList<String> available;


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
                if (isCurrentlyEditing) {
                    Toast.makeText(context, DICTIONARY_DELETED, Toast.LENGTH_SHORT).show();
                    isCurrentlyEditing = false;
                    settingsListsAdapter.makeCheckboxVisible(false);
                    close.setVisibility(View.GONE);
                    editDelete.setBackground(getResources().getDrawable(R.drawable.outline_edit_white_48));
                    iDelete.clearLangToDeleteList();
                } else {
                    DOWNLOAD_IN_PROGRESS = false;
                    ArrayList<String> installedLanguages = new ArrayList<>();
                    checkInstalled(installedLanguages);
                    if (installedLanguages.size() == 1)
                        pref.edit().putString(CURRENTLY_SELECTED_DICTIONARY, installedLanguages.get(0)).apply();

                }
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
        close = rootView.findViewById(R.id.dict_pack_delete);
        editDelete = rootView.findViewById(R.id.dict_pack_edit_delete);
        close.setVisibility(View.GONE);
        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isCurrentlyEditing) {
                    editDelete.setBackground(getResources().getDrawable(R.drawable.delete_states));
                    settingsListsAdapter.makeCheckboxVisible(true);
                    close.setVisibility(View.VISIBLE);
                    close.setClickable(true);
                    isCurrentlyEditing = true;

                } else {
                    if (iDelete.getLangListCount() > 0)
                        iDelete.delete();

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDelete.setBackground(getResources().getDrawable(R.drawable.outline_edit_white_48));
                settingsListsAdapter.makeCheckboxVisible(false);
                close.setVisibility(View.GONE);
                close.setClickable(false);
                isCurrentlyEditing = false;
                clearCheckbox();

            }
        });
        return rootView;
    }

    private void clearCheckbox() {
        for (int i = 0; i < languagesRecyclerView.getChildCount(); i++) {
            CheckBox checkBox = languagesRecyclerView.getChildAt(i).findViewById(R.id.dict_checkbox);
            checkBox.setChecked(false);
        }
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

    private void checkInstalled(ArrayList<String> installedLanguages) {
        for (String lang : available) {
            if (pref.getBoolean(lang, false)) installedLanguages.add(lang);
        }
    }

}
