package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;

import java.lang.ref.WeakReference;
import java.util.List;

public class Query extends AsyncTask<Bundle, Void, List<BhutiaWord>> {
    private final WeakReference<Context> weakContext;

    public Query(Context context){
        //look up what weak reference is
        this.weakContext = new WeakReference<>(context);

    }



    @Override
    protected List doInBackground(Bundle... full_query) {
        //implemented the Bhutia version only
        Bundle args = full_query[0];
        args.getString("TRANSLATION");
        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, "Database").enableMultiInstanceInvalidation().build();
        Log.d("Thread1", "Check");
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List<BhutiaWord> words = bhutiaDao.EngSearch( args.getString("query")+"%");
//        List<BhutiaWord> words = bhutiaDao.TSearch( );
        return words;
        }





}



