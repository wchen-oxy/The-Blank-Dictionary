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
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BroadcastRecievers.myDictionaryDownloadReceiver;
import com.example.myapplication.BroadcastRecievers.myServerStatusReciever;
import com.example.myapplication.DataSerialization;
import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.myapplication.Constants.IntentFilters.SERVER_REACHED;
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
import static com.example.myapplication.Constants.System.DOWNLOAD_ID;
import static com.example.myapplication.Constants.System.DOWNLOAD_TYPE;
import static com.example.myapplication.Constants.System.TRANSPARENT_COLOR;
import static com.example.myapplication.Constants.Toast.BAD_SERVER_CONNECTION_TOAST;
import static com.example.myapplication.Constants.Toast.BUTTON_SELECTED_TOAST;
import static com.example.myapplication.Constants.Toast.DICTIONARY_IS_DOWNLOADING_TOAST;
import static com.example.myapplication.Constants.Toast.DICT_STILL_DOWNLOADING_TOAST;
import static com.example.myapplication.Constants.Toast.DOWNLOAD_DICTIONARY_PROMPT;
import static com.example.myapplication.Constants.Toast.DOWNLOAD_PROMPT;

public class SettingsListsAdapter extends RecyclerView.Adapter<SettingsListsAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<String> available;
    ArrayList<String> installed;
    boolean checkboxVisiblity;
    View view;
    Context mContext;
    SharedPreferences pref;


    private int checkedPosition;
    public static Boolean DOWNLOAD_IN_PROGRSS;



    public SettingsListsAdapter(Context context, Activity activity, ArrayList<String> available, boolean DOWNLOAD_IN_PROGRSS) {
        this.mContext = context;
        this.activity = activity;
        this.available = available;
        this.checkedPosition = -1;
        this.DOWNLOAD_IN_PROGRSS = DOWNLOAD_IN_PROGRSS;

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
        if ((pref.getString(CURRENTLY_SELECTED_DICTIONARY, "")).equals(language)) {
//            Toast.makeText(mContext, installed.get(position), Toast.LENGTH_SHORT).show();
            checkedPosition = position;
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(BUTTON_FOCUSED_COLOR));
            holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.BOLD);
        } else {
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(TRANSPARENT_COLOR));
            holder.textView.setTypeface(null, Typeface.NORMAL);
        }


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
            holder.langNameLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Toast.makeText(mContext, DOWNLOAD_PROMPT, Toast.LENGTH_SHORT).show();
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

    public void notifyDownloadComplete(){
        DOWNLOAD_IN_PROGRSS = false;
    }
    private void dataDownload(String url, String buttonText) {
        Log.d("Download URL: ", url);
        Toast.makeText(mContext, DICTIONARY_IS_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME, buttonText);
        Log.d("Is Folder Writable",  String.valueOf(new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME).canWrite()));

        Log.d("FILEPATH: ", file.getAbsolutePath().toString());
        Log.d("FILE Exists??", String.valueOf(file.isFile()));
        file.setWritable(true);
        if (file.exists()) {
            Log.d("File", file.getAbsolutePath());
            Log.v("Write Permission", Boolean.toString(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
            Log.d("Is Readable", Boolean.toString(file.canRead()));
            Log.d("Is Writable", Boolean.toString(file.canWrite()));
            Log.d("Prev. File Deleted", Boolean.toString(file.delete()));
            Log.d("Pref. File Exists", Boolean.toString(file.exists()));
        }

        makeDownloadRequest(url, file, buttonText);

    }

    private void makeDownloadRequest(String url, File file, String type) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(REQUEST_DESCRIPTION)
                .setTitle(REQUEST_TITLE)
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader(REQUEST_AUTH_HEADER, authDigest());
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        System.out.println("DOWNLIADMANAGER IS>" +  mContext.getSystemService(DOWNLOAD_SERVICE));
        final long id = downloadManager.enqueue(request);
        BroadcastReceiver broadcastReceiver = new myDictionaryDownloadReceiver(id, type);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        HandlerThread handlerThread = new HandlerThread(LANG_DOWNLOAD_HANDLER_THREAD_NAME);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        mContext.registerReceiver(broadcastReceiver, filter, null, handler);
        System.out.println("FILE path from download: " + file.getAbsolutePath());
//
        //slow af internet debug
        myServerStatusReciever myServerStatusReciever = new myServerStatusReciever();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.registerReceiver(myServerStatusReciever, new IntentFilter(SERVER_REACHED));


        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor c = downloadManager.query(query);
        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(id); //filter by id which you have receieved when reqesting download from download manager
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    System.out.println(bytes_downloaded + " out of " + bytes_total);
                    System.out.println("CURRENT STATUS " + cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING) {
                        System.out.println("Still running");}
                    else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_FAILED) {
                        System.out.println("FAILED");
                        downloading = false;
                        Log.i("handleData()", "Reason: " + cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON)));

                        cursor.close();
                    }
                    else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        cursor.close();
                    }


                }
            }
        }).start();




//        pref.edit().putLong(DOWNLOAD_ID, id).apply();
//        pref.edit().putString(DOWNLOAD_TYPE, type).apply();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public LinearLayout dictRowLinearLayout;
        public LinearLayout langNameLinearLayout;
        public ImageButton downloadButton;

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

}

