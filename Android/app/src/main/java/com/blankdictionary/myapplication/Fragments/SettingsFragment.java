package com.blankdictionary.myapplication.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankdictionary.myapplication.DataDownload.DictionaryClientUsage;
import com.blankdictionary.myapplication.DataDownload.HttpBadRequestException;
import com.blankdictionary.myapplication.DialogFragments.FilePermissionDialogFragment;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.R;

import java.io.File;

import static com.blankdictionary.myapplication.Constants.Fragment.ABOUT_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.DICTIONARY_SELECTION_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.System.APP_NAME;

public class SettingsFragment extends Fragment {
    IFragmentCommunicator fragmentCommunicator;
    Activity activity;
    Context context;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (IFragmentCommunicator) context;
        activity = getActivity();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button currentDictionary = rootView.findViewById(R.id.current_dictionary);
        currentDictionary.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Permission is not granted
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                        , 101);
                                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED)
                                    new FilePermissionDialogFragment().showNow(getParentFragmentManager(), "dialog");


                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.
                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                        , 101);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        } else {
                            //make directory for file storage
                            if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory())) {
                                new File(Environment.getExternalStorageDirectory(), APP_NAME).mkdir();
                                Handler mainHandler = new Handler(Looper.getMainLooper());

                                Runnable myRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            new DictionaryClientUsage().checkStatus(context);
                                        } catch (HttpBadRequestException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                };
                                mainHandler.post(myRunnable);
                            }
                            // Permission has already been granted
                            Bundle args = new Bundle();
                            args.putString(NEW_FRAGMENT, DICTIONARY_SELECTION_FRAGMENT);
                            fragmentCommunicator.bundPass(args, false);
                        }

                    }
                }
        );

        Button aboutApp = rootView.findViewById(R.id.about_app_button);
        aboutApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putString(NEW_FRAGMENT, ABOUT_FRAGMENT);
                        fragmentCommunicator.bundPass(args, false);
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 101);
    }
}
