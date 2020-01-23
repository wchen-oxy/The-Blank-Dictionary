package com.example.myapplication.Dictionaries.English;



import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.util.List;

public class English implements ResultWrapper {
    private Result result;

    public English(Bundle args, AppDatabase db) {
        EnglishDao EnglishDao = db.getEnglishDao();
        List words = null;

        switch (args.getString("TRANSLATION")){
            case ("English to English"):
                Log.d("BHUTIA FOUND", "BHUTIA!!");
                words = EnglishDao.englishSearch(args.getString("query"));
//
//            case ("English to Bhutia"):
//                words = bhutiaDao.EnglishSearch(args.getString("query")+"%");
//
//            case ("Tibetan to Bhutia"):
//                Log.d("Tibetan to Bhutia", "Tibetan");
//                words = bhutiaDao.TibetanSearch(args.getString("query")+"%");
        }
        this.result = new Result(words);

//        this.result = new Result(words);

        //shouldn't reach here
        //assert

    }

    //    public List returnDictionary(Bundle args, AppDatabase db) {
//        BhutiaDao bhutiaDao = db.getBhutiaDao();
//
//        switch (args.getString("TRANSLATION")){
//            case ("Bhutia to English"):
//                Log.d("BHUTIA FOUND", "BHUTIA!!");
//                words = bhutiaDao.BhutiaSearch( args.getString("query")+"%");
//                return words;
//            case ("English to Bhutia"):
//                words = bhutiaDao.EnglishSearch(args.getString("query")+"%");
//                return words;
//            case ("Tibetan to Bhutia"):
//                Log.d("Tibetan to Bhutia", "Tibetan");
//                words = bhutiaDao.TibetanSearch(args.getString("query")+"%");
//                return words;
//        }
//
//        //shouldn't reach here
//        //assert
//        return null;
//    }
    @Override
    public Result getList(){
        return this.result;
    }
}