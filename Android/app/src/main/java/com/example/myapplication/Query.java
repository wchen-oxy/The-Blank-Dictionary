package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.Bhutia;
import com.example.myapplication.Dictionaries.English.English;
import com.example.myapplication.Dictionaries.English.EnglishDao;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.lang.ref.WeakReference;

public class Query extends AsyncTask<Bundle, Void, ResultWrapper> {
    private final WeakReference<Context> weakContext;
//    private DictionaryStrategy dictionaryStrategy;
    private SharedPreferences pref;

    public Query(Context context){
        //look up what weak reference is
        this.weakContext = new WeakReference<>(context);
        pref = context.getSharedPreferences("BlankDictPref", 0);

    }


    @Override
    protected ResultWrapper doInBackground(Bundle... full_query) {
        ResultWrapper resultWrapper = null;

        //implemented the Bhutia version only

        AppDatabase db = Room.databaseBuilder(weakContext.get(), AppDatabase.class, "Database").enableMultiInstanceInvalidation().build();

        Bundle args = full_query[0];
        Log.d("PREF", args.getString("TRANSLATION"));

        //TODO implement the strategy for different dictionary packs here, translation selection goes in the concrete implementations
//        Log.d("PREF", pref.getString("CurDict", null).toString());
        switch (pref.getString("CurDict", null)) {
            case("BHUTIA"):
                Log.d("Check", "Inner");
                resultWrapper = new Bhutia(args, db);
//                BhutiaDao bhutiaDao = db.getBhutiaDao();
//                Bhutia ad = new Bhutia();
//                return ad.returnDictionary(args);
//                resultWrapper.setBhutiaWordList(new Bhutia().returnDictionary(args, db));
//                Log.d("THING", new Bhutia().returnDictionary(args, db).toString());
                //do list stuff here
                break;
            case("ENGLISH"):
                resultWrapper = new English(args, db);

//                resultWrapper = new ResultWrapper("ENGLISH");
//                EnglishDao englishDao  = db.getEnglishDao();
//                resultWrapper.setEnglishWordList(new English().returnDictionary(args, db));
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
        if (resultWrapper == null) throw new NullPointerException();
        return resultWrapper;
        }





}



