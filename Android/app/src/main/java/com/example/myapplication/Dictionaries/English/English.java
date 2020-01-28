package com.example.myapplication.Dictionaries.English;



import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.util.List;

import static com.example.myapplication.Constants.DictionaryData.QUERY;
import static com.example.myapplication.Constants.DictionaryData.TRANSLATION_TYPE;

public class English implements ResultWrapper {
    private Result result;

    public English(AppDatabase db, String query, String currentTranslationString) {
        EnglishDao EnglishDao = db.getEnglishDao();
        List words = null;

        switch (currentTranslationString){
            case ("English to English"):
                words = EnglishDao.englishSearch(query+"%");
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