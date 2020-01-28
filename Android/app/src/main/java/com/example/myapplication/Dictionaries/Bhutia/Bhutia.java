package com.example.myapplication.Dictionaries.Bhutia;


import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.util.List;

import static com.example.myapplication.Constants.DictionaryData.QUERY;
import static com.example.myapplication.Constants.DictionaryData.TRANSLATION_TYPE;

public class Bhutia implements ResultWrapper {
    private Result result;

    public Bhutia(AppDatabase db, String query, String currentTranslationString) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List words = null;

//        <item>English to Bhutia (Formal)</item>
//        <item>English to Bhutia (Informal)</item>
//        <item>Bhutia to English (Formal)</item>
//        <item>Bhutia to English (Informal)</item>
//        <item>Bhutia Script to English (Formal)</item>
//        <item>Bhutia Script to English (Informal)</item>

        switch (currentTranslationString){
            case ("English to Bhutia (Formal)"):
                Log.d("English to Bhutia f", "Found");
//                words = bhutiaDao.getAll();
                words = bhutiaDao.engTranSearch(query+"%");
                break;

            case ("English to Bhutia (Informal)"):
                Log.d("English to Bhutia i", "Found");
                words = bhutiaDao.engTranSearch(query+"%");
                break;

            case ("Bhutia to English (Formal)"):
                Log.d("Bhutia to English f", "Found");
                words = bhutiaDao.bhutRomFormalSearch(query+"%");
                break;

            case ("Bhutia to English (Informal)"):
                Log.d("Bhutia to English i", "Found");
                words = bhutiaDao.bhutRomInformalSearch(query+"%");
                break;


            case ("Bhutia Script to English (Formal)"):
                Log.d("Bhutia to English f", "Found");
                words = bhutiaDao.bhutScriptFormalSearch(query+"%");
                break;

            case ("Bhutia Script to English (Informal)"):
                Log.d("Bhutia to English i", "Found");
                words = bhutiaDao.bhutScriptInformalSearch(query+"%");
                break;

//
//            case ("Bhutia to English"):
//                Log.d("BHUTIA FOUND", "BHUTIA!!");
//                words = bhutiaDao.bhutiaSearch( query+"%");
//                break;
//
//            case ("English to Bhutia"):
//                words = bhutiaDao.englishSearch(query+"%");
//                break;
//
//
//            case ("Tibetan to Bhutia"):
//                Log.d("Tibetan to Bhutia", "Tibetan");
//                words = bhutiaDao.tibetanSearch(query+"%");
//                break;

        }
        Log.d("Resulting words", words.toString());
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
//                words = bhutiaDao.BhutiaSearch( query+"%");
//                return words;
//            case ("English to Bhutia"):
//                words = bhutiaDao.EnglishSearch(query+"%");
//                return words;
//            case ("Tibetan to Bhutia"):
//                Log.d("Tibetan to Bhutia", "Tibetan");
//                words = bhutiaDao.TibetanSearch(query+"%");
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