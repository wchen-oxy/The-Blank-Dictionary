package com.blankdictionary.myapplication.DataDownload;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankdictionary.myapplication.BroadcastRecievers.myDictionaryDownloadReceiver;
import com.blankdictionary.myapplication.BroadcastRecievers.myServerStatusReciever;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.blankdictionary.myapplication.Constants.IntentFilters.SERVER_REACHED;
import static com.blankdictionary.myapplication.Constants.Network.LANG_DOWNLOAD_HANDLER_THREAD_NAME;
import static com.blankdictionary.myapplication.Constants.Network.REQUEST_AUTH_HEADER;
import static com.blankdictionary.myapplication.Constants.Network.REQUEST_DESCRIPTION;
import static com.blankdictionary.myapplication.Constants.Network.REQUEST_TITLE;
import static com.blankdictionary.myapplication.Constants.Network.authDigest;
import static com.blankdictionary.myapplication.Constants.System.APP_DICTIONARY_FOLDER;
import static com.blankdictionary.myapplication.Constants.System.APP_NAME;
import static com.blankdictionary.myapplication.Constants.Toast.DICTIONARY_IS_DOWNLOADING_TOAST;

public class DownloadRequest {
    Context mContext;
    File file;

    public DownloadRequest(Context context) {
        this.mContext = context;
    }

    public void start(String url, String lang) {
        makeFile(url, lang);
        launchDownload(url, file, lang);

    }

    private void makeFile(String url, String buttonText) {
        Log.d("Download URL: ", url);
        Toast.makeText(mContext, DICTIONARY_IS_DOWNLOADING_TOAST, Toast.LENGTH_SHORT).show();
        file = new File(mContext.getExternalFilesDir(null) + APP_DICTIONARY_FOLDER , buttonText);
        System.out.println( String.valueOf(new File(mContext.getExternalFilesDir(null) + APP_DICTIONARY_FOLDER )));
        Log.d("Is Folder Writable", String.valueOf(new File(mContext.getExternalFilesDir(null) + APP_DICTIONARY_FOLDER ).canWrite()));

        file.setWritable(true);
        if (file.exists()) {
            Log.d("File", file.getAbsolutePath());
            Log.v("Write Permission", Boolean.toString(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
            Log.d("Is Readable", Boolean.toString(file.canRead()));
            Log.d("Is Writable", Boolean.toString(file.canWrite()));
            Log.d("Prev. File Deleted", Boolean.toString(file.delete()));
            Log.d("Pref. File Exists", Boolean.toString(file.exists()));
        }


    }

    private void launchDownload(String url, File file, String lang) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(REQUEST_DESCRIPTION)
                .setTitle(REQUEST_TITLE)
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader(REQUEST_AUTH_HEADER, authDigest());
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        final long id = downloadManager.enqueue(request);
        BroadcastReceiver broadcastReceiver = new myDictionaryDownloadReceiver(id, lang);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        HandlerThread handlerThread = new HandlerThread(LANG_DOWNLOAD_HANDLER_THREAD_NAME);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        mContext.registerReceiver(broadcastReceiver, filter, null, handler);

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
                        System.out.println("Still running");
                    } else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_FAILED) {
                        System.out.println("FAILED");
                        downloading = false;
                        Log.i("handleData()", "Reason: " + cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON)));

                        cursor.close();
                    } else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        cursor.close();
                    }


                }
            }
        }).start();

    }
}
