package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;

import java.lang.ref.WeakReference;
import java.util.List;

public class Query extends AsyncTask<Bundle, Void, List<BhutiaWord>> {
    private final WeakReference<Context> weakContext;
    private DictionaryStrategy dictionaryStrategy;
    private SharedPreferences pref;

    public Query(Context context){
        //look up what weak reference is
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences("BlankDictPref", 0);

    }


    @Override
    protected List doInBackground(Bundle... full_query) {
        List list = null;

        //implemented the Bhutia version only

        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, "Database").enableMultiInstanceInvalidation().build();

        Bundle args = full_query[0];
        Log.d("PREF", args.getString("TRANSLATION"));

        //TODO implement the strategy for different dictionary packs here, translation selection goes in the concrete implementations
//        Log.d("PREF", pref.getString("CurDict", null).toString());
        switch (pref.getString("CurDict", null)) {
            case("BHUTIA"):
                  BhutiaDao bhutiaDao = db.getBhutiaDao();
//                Bhutia ad = new Bhutia();
//                return ad.returnDictionary(args);
                return new Bhutia().returnDictionary(args, db);
                //do list stuff here
        }


//        if (pref.getString("CurDict", null).equals("BHUTIA")) {
//            BhutiaDao bhutiaDao = db.getBhutiaDao();
//            if (args.getString("TRANSLATION").equals("Bhutia to English")) {
//                List<BhutiaWord> words = bhutiaDao.EngSearch( args.getString("query")+"%");
//                Log.d("BHUTIA FOUND", "BHUTIA!!");
//                return words;
//                }
//            if (args.getString("TRANSLATION").equals("Tibetan to Bhutia")){
//                Log.d("Tibet not done", "Tibetan");
//            }
//            return list;
//        }

//        Log.d("dict found?", Boolean.toString(pref.getBoolean("Bhutia",false)));
//        if (pref.getBoolean("Bhutia",false) != false ) Log.d("BHUTIA FOUND", "BHUTIA!!");
//        List<BhutiaWord> words = bhutiaDao.TSearch( );
        if (list == null) throw new NullPointerException();
        return list;
        }





}



