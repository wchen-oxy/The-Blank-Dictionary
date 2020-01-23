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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.Serialization;
import com.example.myapplication.myReceiver;

import org.json.JSONArray;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

//FIXME Add 404 reaction to download
public class LanguagePackFrag extends Fragment {
    Activity activtiy;
    Context mContext;
    String TEMP_URL = "https://jsonplaceholder.typicode.com/todos/1";
    String TEMP_REAL_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/db.json";
    String TEMP_ENG_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/eng.json";
    String TEMP_FAKE_URL = "https://raw.githubusercontent.com/wchen-oxy/Json/master/test.json";


    JSONArray list = null;

    public static Boolean DOWNLOAD_IN_PROGRSS = false;

    public static LanguagePackFrag newInstance(){
       return new LanguagePackFrag();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        activtiy = getActivity();

    }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.lang_pack_list, container,false);

        ArrayList<String> lists = Serialization.deserializer(new File(Environment.getExternalStorageDirectory()+"/BlankDictionary/list.json"));
        LinearLayout linearLayout = rootView.findViewById(R.id.lang_pack_root_layout);

        //case where there are downloaded languages

        for (String s:lists) {
            Button button = new Button(mContext);
            button.setTypeface(Typeface.create("alegreya_sans", Typeface.NORMAL));
            button.setText(s);
            button.getBackground().setAlpha(0);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.resize_arrow, 0);
            button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(
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
                                    Button b = (Button) v;
                                    dataDownload(Constants.getAbsoluteUrl(Constants.DOWNLOAD_URL_PART) +  b.getText().toString(), b.getText().toString());
                                    DOWNLOAD_IN_PROGRSS = true;
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "A dictionary is already downloading.", Toast.LENGTH_SHORT).show();
                            }

//

                        }
                    });


            if (linearLayout != null) {
                linearLayout.addView(button);
            }


        }



//            ArrayList<String> lists = loadJSONFromAsset();
        if (!lists.isEmpty()) {
            Log.d("download", lists.toString());
            Log.d("Download,specific", lists.get(0));
        }
        else{
            Toast.makeText(mContext, "Can't Connect to server to download available dictionaries.", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    //need to convert into asynchtask

    private void dataDownload(String url, String buttonText){
        Toast.makeText(getActivity(),"DOWNLOADING",Toast.LENGTH_SHORT).show();
        Log.d("CHECK1", Environment.getExternalStorageDirectory().toString());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        File file = new File(Environment.getExternalStorageDirectory()+"/BlankDictionary", buttonText);
//        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + buttonText);
        Log.d("noAbs", file.getAbsolutePath().toString());
        Log.d("noAbs", Environment.getExternalStorageDirectory().toString());
        file.setWritable(true);
        if (file.exists()) {
            Log.d("File", file.getAbsolutePath() );
//            if (getter.br != null) {getter.unregisterReceiver(getter.br); getter.br = null;}
            try {

                Log.v("PERMISSION", Boolean.toString(ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
                System.out.println("file delete!");

                Log.d("REadable", Boolean.toString(file.canRead()));
                Log.d("Writable", Boolean.toString(file.canWrite()));

                Log.d("DeleteStat", Boolean.toString(file.delete()));
                Log.d("does exist?", Boolean.toString(file.exists()));


            } catch(Exception e) {
                System.out.println("file not deleted");
            }
        }


        request.setDescription("Your Dictionary is now downloading.")
                .setTitle("Dictionay Download Started.")
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader("Authorization", Constants.authDigest(Constants.CODE));
        Log.d("CHECK2", Environment.getExternalStorageDirectory().toString());




        DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);

        BroadcastReceiver broadcastReceiver = new myReceiver(buttonText);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        HandlerThread handlerThread = new HandlerThread("LANGUAGE_DOWNLOAD");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        mContext.registerReceiver(broadcastReceiver, filter, null, handler);

//        myReceiver.downloadId = downloadID;
//        // enqueue puts the download request in the queue.
////        downloadManager.enqueue(request);
//        BroadcastReceiver br = new myReceiver(buttonText);
//        MainActivity setter = (MainActivity) getActivity();
//
//        setter.br = br;
//        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
////        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        HandlerThread handlerThread = new HandlerThread("LANGUAGE_DOWNLOAD");
//        handlerThread.start();
//        Looper looper = handlerThread.getLooper();
//        //use looper for the remaining tasks, like convet to json and SQL insert and Delete
//        //https://stackoverflow.com/questions/7597742/what-is-the-purpose-of-looper-and-how-to-use-it
//        //https://stackoverflow.com/questions/5674518/does-broadcastreceiver-onreceive-always-run-in-the-ui-thread
//        Handler handler = new Handler(looper);
//        mContext.registerReceiver(br, filter, null, handler);
//        Log.d("I HAVE THIS MANY", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).list().length));


    }


//    public ArrayList<String> loadJSONFromAsset() throws FileNotFoundException {
//        ArrayList<String> json = new ArrayList<>();
//
////        Scanner s = new Scanner(new File(Environment.getExternalStorageDirectory()+"/BlankDictionary/list.json"));
////        ArrayList<String> list = new ArrayList<String>();
////        while (s.hasNext()){
////            list.add(s.next());
////        }
////        s.close();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory()+"/BlankDictionary/list.json"))) {
//            while (br.ready()) {
//                json.add(br.readLine());
//            }
//        }
//        catch(FileNotFoundException fe) {
//            fe.printStackTrace();
//
//        }
//        catch (IOException ie){
//            ie.printStackTrace();
//        }
//
//        Log.d("Pre-list",  json.get(0).toString());
//
//
////        try {
////
////            InputStream is = getActivity().getAssets().open();
////            int size = is.available();
////            byte[] buffer = new byte[size];
////            is.read(buffer);
////            is.close();
////            json = new String(buffer, "UTF-8");
////        } catch (IOException ex) {
////            ex.printStackTrace();
////            return null;
////        }
//        return json;
//    }
//




}


