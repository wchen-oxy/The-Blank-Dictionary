package com.example.myapplication.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.myReceiver;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static android.content.Context.DOWNLOAD_SERVICE;

public class LanguagePackFrag extends Fragment {
    Activity activtiy;
    Context mContext;
    String TEMP_URL = "https://jsonplaceholder.typicode.com/todos/1";
    String TEMP_REAL_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/db.json";
    public static Boolean DOWNLOAD_IN_PROGRSS = false;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);activtiy = getActivity();
        if (!(new File(Environment.getExternalStorageDirectory(), "BlankDictionary").isDirectory())){
            new File(Environment.getExternalStorageDirectory(), "BlankDictionary").mkdir();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.lang_pack_list, container,false);
        Button languagePack = rootView.findViewById(R.id.dictionary_selected);
        final String buttonText = languagePack.getText().toString();
        Log.d("TEXT", buttonText);
        languagePack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!DOWNLOAD_IN_PROGRSS){
//                        Toast test = new Toast();
                        Toast.makeText(getActivity(),"Selected",Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(activtiy, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Permission is not granted
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activtiy,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.
                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(activtiy,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                        ,101);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        } else {
                            // Permission has already been granted
                            dataDownload2(TEMP_REAL_URL, buttonText);
                            DOWNLOAD_IN_PROGRSS = true;
                        }
                        }
                        else {
                            Toast.makeText(getActivity(), "A dictionary is already downloading.", Toast.LENGTH_SHORT).show();
                        }

//

                    }
                });

        return rootView;
    }

    //need to convert into asynchtask

    private void dataDownload2(String url, String buttonText){
        Toast.makeText(getActivity(),"DOWNLOADING",Toast.LENGTH_SHORT).show();
        Log.d("CHECK1", Environment.getExternalStorageDirectory().toString());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", buttonText);
//        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + buttonText);
        Log.d("noAbs", file.getAbsolutePath().toString());
        Log.d("noAbs", Environment.getExternalStorageDirectory().toString());
        file.setWritable(true);
        if (file.exists()) {
            MainActivity getter = (MainActivity) getActivity();
            Log.d("File", file.getAbsolutePath() );
            if (getter.br != null) {getter.unregisterReceiver(getter.br); getter.br = null;}
            try {

                Log.v("PERMISSION", Boolean.toString(ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
                System.out.println("file delete!");

                Log.d("REadable", Boolean.toString(file.canRead()));
                Log.d("Writable", Boolean.toString(file.canWrite()));

                Log.d("DeleteStat", Boolean.toString(file.delete()));
                Log.d("DeleteStat", Boolean.toString(file.exists()));


            } catch(Exception e) {
                System.out.println("file not deleted");
            }
        }
        request.setDescription("Your Dictionary is now downloading.")
                .setTitle("Dictionay Download Started.")
                .setDestinationUri(Uri.fromFile(file));
        Log.d("CHECK2", Environment.getExternalStorageDirectory().toString());




        DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);
        myReceiver.downloadId = downloadID;
        // enqueue puts the download request in the queue.
//        downloadManager.enqueue(request);
        //FIXME PUT ON SEPARATE THREAD
        BroadcastReceiver br = new myReceiver(buttonText);
        MainActivity setter = (MainActivity) getActivity();
        setter.br = br;
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mContext.registerReceiver(br, filter);
        Log.d("I HAVE THIS MANY", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).list().length));


    }
    private void fakeDownload(String url) {
        String json = "[{\"romanization\": \"Chik\", \"ipa\": \"Chi-ih-yak\", \"category\": \"example\", \"eng_trans\": \"1\", \"tib_script\": \"1\", \"example\": \"there is Chik car here.\"}, {\"romanization\": \"Chikia\", \"ipa\": \"Ch-kik-ya\", \"category\": \"imaginary\", \"eng_trans\": \"adslfa\", \"tib_script\": \"\\u4e2d\\u6587\", \"example\": null}, {\"romanization\": \"Kuzu\", \"ipa\": \"Coo-Hazoo\", \"category\": \"Another Example\", \"eng_trans\": \"English\", \"tib_script\": \"adfadf\", \"example\": \"this makes no sense.\"}, {\"romanization\": \"lol\", \"ipa\": \"f\", \"category\": \"ads\", \"eng_trans\": \"df\", \"tib_script\": \"asdf\", \"example\": \"asdf\"}]";
        Gson gson = new Gson();
        gson.toJson(json);

    }
    //data download shit
//    private static class dataDownload extends AsyncTask<String, Void, Void> {
//        String TEMP_URL = "https://jsonplaceholder.typicode.com/todos/1";
//
//
//        @Override
//        protected Void doInBackground(String... URL) {
//
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(TEMP_URL));
//            File file = new File(Environment.DIRECTORY_DOWNLOADS, "Bhutia");
//            request.setDescription("Your Dictionary is now downloading.")
//                    .setTitle("Dictionay Download Started.")
//                    .setDestinationUri(Uri.fromFile(file));
//
////            DownloadManager downloadManager= (DownloadManager) super.getSystemService(DOWNLOAD_SERVICE);
////            long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
////
////            dm.enqueue(request);
//            return null;
//        }
//    }




}


