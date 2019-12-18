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
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
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
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.Context.DOWNLOAD_SERVICE;

public class LanguagePackFrag extends Fragment {
    Activity activtiy;
    Context mContext;
    String TEMP_URL = "https://jsonplaceholder.typicode.com/todos/1";
    String TEMP_REAL_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/db.json";
    String TEMP_ENG_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/db.json";

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
        Button bhutiaButton = rootView.findViewById(R.id.bhutia_dictionary);
        Button englishButton = rootView.findViewById(R.id.english_dictionary);

        final String buttonText = bhutiaButton.getText().toString();
        Log.d("TEXT", buttonText);
        bhutiaButton.setOnClickListener(
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
//            if (getter.br != null) {getter.unregisterReceiver(getter.br); getter.br = null;}
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
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader("Authorization", authDigest());
        Log.d("CHECK2", Environment.getExternalStorageDirectory().toString());




        DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);
        myReceiver.downloadId = downloadID;
        // enqueue puts the download request in the queue.
//        downloadManager.enqueue(request);
        BroadcastReceiver br = new myReceiver(buttonText);
        MainActivity setter = (MainActivity) getActivity();
        setter.br = br;
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        HandlerThread handlerThread = new HandlerThread("LANGUAGE_DOWNLOAD");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        //use looper for the remaining tasks, like convet to json and SQL insert and Delete
        //https://stackoverflow.com/questions/7597742/what-is-the-purpose-of-looper-and-how-to-use-it
        //https://stackoverflow.com/questions/5674518/does-broadcastreceiver-onreceive-always-run-in-the-ui-thread
        Handler handler = new Handler(looper);
        mContext.registerReceiver(br, filter, null, handler);
        Log.d("I HAVE THIS MANY", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).list().length));


    }
    private String authDigest() {
        final String CODE = "Az39dB0n!23";
        byte[] data1 = CODE.getBytes();
        String output = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            data1 = md.digest(data1);

            output = String.format("%02x", new BigInteger(1, data1));

        }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return output;
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


