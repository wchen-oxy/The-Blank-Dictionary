package com.example.myapplication.Adapters;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BroadcastRecievers.myDictionaryDownloadReceiver;
import com.example.myapplication.DataSerialization;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.myapplication.Constants.Network.DOWNLOAD_URL_PART;
import static com.example.myapplication.Constants.Network.LANG_DOWNLOAD_HANDLER_THREAD_NAME;
import static com.example.myapplication.Constants.Network.REQUEST_AUTH_HEADER;
import static com.example.myapplication.Constants.Network.REQUEST_DESCRIPTION;
import static com.example.myapplication.Constants.Network.REQUEST_TITLE;
import static com.example.myapplication.Constants.Network.authDigest;
import static com.example.myapplication.Constants.Network.getAbsoluteUrl;
import static com.example.myapplication.Constants.SupportedDictionaries.ENGLISH;
import static com.example.myapplication.Constants.System.APP_NAME;
import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.BUTTON_FOCUSED_COLOR;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.TRANSPARENT_COLOR;
import static com.example.myapplication.Constants.Toast.BAD_SERVER_CONNECTION_TOAST;
import static com.example.myapplication.Constants.Toast.BUTTON_SELECTED_TOAST;
import static com.example.myapplication.Constants.Toast.DICTIONARY_IS_DOWNLOADING_TOAST;
import static com.example.myapplication.Constants.Toast.DICT_STILL_DOWNLOADING_TOAST;
import static com.example.myapplication.Constants.Toast.DOWNLOAD_DICTIONARY_PROMPT;

public class SettingsListsAdapter extends RecyclerView.Adapter<SettingsListsAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<String> available;
    ArrayList<String> installed;
    boolean checkboxVisiblity;
    View view;
    Context mContext;
    SharedPreferences pref;
    String selected;

    private int checkedPosition;
    public static Boolean DOWNLOAD_IN_PROGRSS;



    public SettingsListsAdapter(Context context, Activity activity, ArrayList<String> available, boolean DOWNLOAD_IN_PROGRSS) {
        this.mContext = context;
        this.activity = activity;
        this.available = available;
        this.checkedPosition = -1;
        this.DOWNLOAD_IN_PROGRSS = DOWNLOAD_IN_PROGRSS;

        pref = context.getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode;
        selected = pref.getString(CURRENTLY_SELECTED_DICTIONARY, "");

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
        String language = available.get(position);
        //fill in viewholder information
        holder.checkBox.setTag(language);
        holder.textView.setText(language);
        //checkbox visibility from edit button
        if (!checkboxVisiblity) holder.checkBox.setVisibility(View.GONE);
        else if (checkboxVisiblity && pref.getBoolean(language, false)) {
            holder.checkBox.setVisibility(View.VISIBLE);
        }

////        Toast.makeText(mContext, selected, Toast.LENGTH_SHORT).show();
//        System.out.println("Selected one is: " + selected);
//        //check if the selected language matches the current viewholder
//
//        //FIXME: REMOVE AFTER FIXING SELECTVIEW
//        if (selected.equals(language)) {
////            Toast.makeText(mContext, installed.get(position), Toast.LENGTH_SHORT).show();
//            checkedPosition = position;
//            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(BUTTON_FOCUSED_COLOR));
//        } else {
//            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(TRANSPARENT_COLOR));
//        }


        //initialize download button
        if (pref.getBoolean(language, false))  {
            holder.downloadButton.setVisibility(View.GONE);
            holder.langNameLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.d("Pos", String.valueOf(position));
                    if (checkedPosition != position) {
                        Toast.makeText(mContext, available.get(position), Toast.LENGTH_SHORT).show();
                        pref.edit().putString(CURRENTLY_SELECTED_DICTIONARY, available.get(position)).apply();
                        checkedPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }

        else {
            holder.downloadButton.setVisibility(View.VISIBLE);
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
        }

//        if (!available.isEmpty()) {
//            Log.d("download", available.toString());
//            Log.d("Download,specific", available.get(0));
//        } else {
//            Toast.makeText(mContext, BAD_SERVER_CONNECTION_TOAST, Toast.LENGTH_LONG).show();
//        }
    }

    private void requestPermission(String language){
        //                        Toast test = new Toast();
        Toast.makeText(mContext, BUTTON_SELECTED_TOAST, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else {
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

            dataDownload(getAbsoluteUrl(DOWNLOAD_URL_PART) + language, language);
            DOWNLOAD_IN_PROGRSS = true;
        }
    }

    @Override
    public int getItemCount() {
        return available.size();
    }


    public void makeCheckboxVisible(boolean visible) {
        if (visible) {
            System.out.println("checkbox true Clicked");

            checkboxVisiblity = visible;
            notifyDataSetChanged();

        } else {
            System.out.println("checkbox false Clicked");

            checkboxVisiblity = visible;
            notifyDataSetChanged();
        }
    }
    private void dataDownload(String url, String buttonText) {
        Log.d("Download URL: ", url);
        Toast.makeText(mContext, DICTIONARY_IS_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME, buttonText);
        file.setWritable(true);
        if (file.exists()) {
            Log.d("File", file.getAbsolutePath());
            Log.v("Write Permission", Boolean.toString(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
            Log.d("Is Readable", Boolean.toString(file.canRead()));
            Log.d("Is Writable", Boolean.toString(file.canWrite()));
            Log.d("Prev. File Deleted", Boolean.toString(file.delete()));
            Log.d("Pref. File Exists", Boolean.toString(file.exists()));
        }

        makeDownloadRequest(url, file);
        BroadcastReceiver broadcastReceiver = new myDictionaryDownloadReceiver(buttonText);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        HandlerThread handlerThread = new HandlerThread(LANG_DOWNLOAD_HANDLER_THREAD_NAME);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        mContext.registerReceiver(broadcastReceiver, filter, null, handler);
    }

    private void makeDownloadRequest(String url, File file) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(REQUEST_DESCRIPTION)
                .setTitle(REQUEST_TITLE)
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader(REQUEST_AUTH_HEADER, authDigest());
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public LinearLayout dictRowLinearLayout;
        public LinearLayout langNameLinearLayout;
        public Button downloadButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.language_text);
            checkBox = itemView.findViewById(R.id.dict_checkbox);
            downloadButton = itemView.findViewById(R.id.download_button);
            dictRowLinearLayout = itemView.findViewById(R.id.dict_pack_row_linear_layout);
            langNameLinearLayout = itemView.findViewById(R.id.dict_pack_language_title_linear_layout);
            //enable transition in linear layout
            dictRowLinearLayout.getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);

        }
    }
        //FIXME: IMPLEMENT RECYCLERVIEW SELECTOR
//    final public class MyDetailsLookup extends ItemDetailsLookup {
//        private final RecyclerView mRecyclerView;
//
//        MyDetailsLookup(RecyclerView recyclerView) {
//            this.mRecyclerView = recyclerView;
//        }
//
//
//        @Nullable
//        @Override
//        public ItemDetails getItemDetails(@NonNull MotionEvent e) {
//            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
//            if (view != null) {
//                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//                if (holder instanceof MyHolder) {
//                    return ((MyHolder) holder).getItemDetails();
//                }
//            }
//            return null;
//        }
//    }
}
