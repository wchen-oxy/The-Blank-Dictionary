package com.example.myapplication.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import static com.example.myapplication.Constants.System.APP_NAME;
import static com.example.myapplication.Constants.System.FONT_STYLE;
import static com.example.myapplication.Constants.Toast.BAD_SERVER_CONNECTION_TOAST;
import static com.example.myapplication.Constants.Toast.BUTTON_SELECTED_TOAST;
import static com.example.myapplication.Constants.Toast.DICTIONARY_IS_DOWNLOADING_TOAST;
import static com.example.myapplication.Constants.Toast.DICT_STILL_DOWNLOADING_TOAST;

//FIXME Add 404 reaction to download
public class LanguagePackFragment extends Fragment {
    public static Boolean DOWNLOAD_IN_PROGRSS = false;
    Activity activtiy;
    Context mContext;

    public static LanguagePackFragment newInstance() {
        return new LanguagePackFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        activtiy = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_language_pack_list, container, false);

        ArrayList<String> lists = DataSerialization.deserializer(new File(Environment.getExternalStorageDirectory() + "/BlankDictionary/list.json"));
        LinearLayout linearLayout = rootView.findViewById(R.id.lang_pack_root_layout);

        //case where there are downloaded languages
        for (String s : lists) {
            Button button = new Button(mContext);
            button.setTypeface(Typeface.create(FONT_STYLE, Typeface.NORMAL));
            button.setText(s);
            button.getBackground().setAlpha(0);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.resize_arrow, 0);
            button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            if (!DOWNLOAD_IN_PROGRSS) {
//                        Toast test = new Toast();
                                Toast.makeText(getActivity(), BUTTON_SELECTED_TOAST, Toast.LENGTH_SHORT).show();
                                if (ContextCompat.checkSelfPermission(activtiy, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    // Permission is not granted
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(activtiy,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        // Show an explanation to the user *asynchronously* -- don't block
                                        // this thread waiting for the user's response! After the user
                                        // sees the explanation, try again to request the permission.
                                    }
//                                    else {
//                                        // No explanation needed; request the permission
//                                        ActivityCompat.requestPermissions(activtiy,
//                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
//                                                , 101);
//
//                                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                                        // app-defined int constant. The callback method gets the
//                                        // result of the request.
//                                    }
                                } else {
                                    // Permission has already been granted
                                    Button b = (Button) v;
                                    dataDownload(getAbsoluteUrl(DOWNLOAD_URL_PART) + b.getText().toString(), b.getText().toString());
                                    DOWNLOAD_IN_PROGRSS = true;
                                }
                            } else {
                                Toast.makeText(getActivity(), DICT_STILL_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            if (linearLayout != null) {
                linearLayout.addView(button);
            }
        }

        if (!lists.isEmpty()) {
            Log.d("download", lists.toString());
            Log.d("Download,specific", lists.get(0));
        } else {
            Toast.makeText(mContext, BAD_SERVER_CONNECTION_TOAST, Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void dataDownload(String url, String buttonText) {
        Log.d("Download URL: ", url);
        Toast.makeText(getActivity(), DICTIONARY_IS_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
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
//
//        makeDownloadRequest(url, file);
//        BroadcastReceiver broadcastReceiver = new myDictionaryDownloadReceiver(buttonText);
//        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        HandlerThread handlerThread = new HandlerThread(LANG_DOWNLOAD_HANDLER_THREAD_NAME);
//        handlerThread.start();
//        Looper looper = handlerThread.getLooper();
//        Handler handler = new Handler(looper);
//        mContext.registerReceiver(broadcastReceiver, filter, null, handler);
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
}


