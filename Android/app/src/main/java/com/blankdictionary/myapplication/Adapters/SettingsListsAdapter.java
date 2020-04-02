package com.blankdictionary.myapplication.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.DataDownload.DownloadRequest;
import com.blankdictionary.myapplication.R;

import java.util.ArrayList;

import static com.blankdictionary.myapplication.Constants.Network.DOWNLOAD_URL_PART;
import static com.blankdictionary.myapplication.Constants.Network.getAbsoluteUrl;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.BUTTON_FOCUSED_COLOR;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.blankdictionary.myapplication.Constants.System.TRANSPARENT_COLOR;
import static com.blankdictionary.myapplication.Constants.Toast.DICT_STILL_DOWNLOADING_TOAST;
import static com.blankdictionary.myapplication.Constants.Toast.DOWNLOAD_PROMPT;

public class SettingsListsAdapter extends RecyclerView.Adapter<SettingsListsAdapter.MyViewHolder> {
    public static Boolean DOWNLOAD_IN_PROGRSS;
    Activity activity;
    ArrayList<String> available;
    boolean checkboxVisiblity;
    boolean downloadButtonVisible;
    View view;
    Context mContext;
    SharedPreferences pref;
    private int checkedPosition;


    public SettingsListsAdapter(Context context, Activity activity, ArrayList<String> available, boolean DOWNLOAD_IN_PROGRSS) {
        this.mContext = context;
        this.activity = activity;
        this.available = available;
        this.checkedPosition = -1;
        this.downloadButtonVisible = false;
        SettingsListsAdapter.DOWNLOAD_IN_PROGRSS = DOWNLOAD_IN_PROGRSS;
        pref = context.getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode;

    }

    @NonNull
    @Override
    public SettingsListsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dict_select, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String language = available.get(position);

        holder.checkBox.setTag(language);
        holder.textView.setText(language);

        if (pref.getBoolean(language, false)) {
            if (!checkboxVisiblity) {
                if (pref.getString(CURRENTLY_SELECTED_DICTIONARY, "").equals(language)) {
                    holder.selectedDictionaryImage.setVisibility(View.VISIBLE);
                } else {
                    holder.selectedDictionaryImage.setVisibility(View.GONE);
                }
                holder.checkBox.setVisibility(View.GONE);
            } else {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.selectedDictionaryImage.setVisibility(View.GONE);
            }

            holder.downloadButton.setVisibility(View.GONE);
            holder.langNameLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkedPosition != position) {
                        String capitaliedLang = available.get(position).substring(0, 1).toUpperCase() + available.get(position).substring(1).toLowerCase();
                        Toast.makeText(mContext, "You are now using the " + capitaliedLang + " dictionary.", Toast.LENGTH_SHORT).show();
                        pref.edit().putString(CURRENTLY_SELECTED_DICTIONARY, available.get(position)).apply();
                        checkedPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            holder.selectedDictionaryImage.setVisibility(View.GONE);
            if (!checkboxVisiblity) {
                holder.downloadButton.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.GONE);
            } else {
                holder.downloadButton.setVisibility(View.GONE);
            }
            holder.downloadButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            if (!DOWNLOAD_IN_PROGRSS) {
                                requestPermission(available.get(position));
                            } else {
                                Toast.makeText(mContext, DICT_STILL_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            holder.langNameLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, DOWNLOAD_PROMPT, Toast.LENGTH_SHORT).show();
                }
            });

        }

        //Section that indicates selection or not
        if ((pref.getString(CURRENTLY_SELECTED_DICTIONARY, "")).equals(language)) {
            checkedPosition = position;
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(BUTTON_FOCUSED_COLOR));
            holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.BOLD);
        } else {
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(TRANSPARENT_COLOR));
            holder.textView.setTypeface(null, Typeface.NORMAL);
        }

    }

    private void requestPermission(String language) {
        //                        Toast test = new Toast();
//        Toast.makeText(mContext, BUTTON_SELECTED_TOAST, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
            // Permission has already been granted
            DOWNLOAD_IN_PROGRSS = true;
            DownloadRequest downloadRequest = new DownloadRequest(mContext);
            downloadRequest.start(getAbsoluteUrl(DOWNLOAD_URL_PART) + language, language);
        }
    }

    @Override
    public int getItemCount() {
        return available.size();
    }


    public void makeCheckboxVisible(boolean visible) {
        checkboxVisiblity = visible;
        notifyDataSetChanged();
    }

    public void notifyDownloadComplete() {
        DOWNLOAD_IN_PROGRSS = false;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public LinearLayout dictRowLinearLayout;
        public LinearLayout langNameLinearLayout;
        public ImageButton downloadButton;
        public ImageView selectedDictionaryImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_hero_dictionary_title);
            checkBox = itemView.findViewById(R.id.checkbox_dictionary);
            downloadButton = itemView.findViewById(R.id.button_start_download);
            selectedDictionaryImage = itemView.findViewById(R.id.imageview_installed_dictionary);
            dictRowLinearLayout = itemView.findViewById(R.id.dict_pack_row_linear_layout);
            langNameLinearLayout = itemView.findViewById(R.id.linearlayout_dictionary_container);

            //enable transition in linear layout
//            dictRowLinearLayout.getLayoutTransition()
//                    .enableTransitionType(LayoutTransition.CHANGING);

        }
    }

}

