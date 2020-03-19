package com.example.myapplication.DataDownload;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.BroadcastRecievers.myDictionaryDownloadReceiver;
import com.example.myapplication.BroadcastRecievers.myServerStatusReciever;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.myapplication.Constants.IntentFilters.SERVER_REACHED;
import static com.example.myapplication.Constants.Network.LANG_DOWNLOAD_HANDLER_THREAD_NAME;
import static com.example.myapplication.Constants.Network.REQUEST_AUTH_HEADER;
import static com.example.myapplication.Constants.Network.REQUEST_DESCRIPTION;
import static com.example.myapplication.Constants.Network.REQUEST_TITLE;
import static com.example.myapplication.Constants.Network.authDigest;

public class DownloadRequest {
    Context mContext;
    public DownloadRequest(Context context){
        this.mContext = context;
    }
    public void begin(String url, File file, String type) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(REQUEST_DESCRIPTION)
                .setTitle(REQUEST_TITLE)
                .setDestinationUri(Uri.fromFile(file))
                .addRequestHeader(REQUEST_AUTH_HEADER, authDigest());
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        final long id = downloadManager.enqueue(request);
        BroadcastReceiver broadcastReceiver = new myDictionaryDownloadReceiver(id, type);
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
