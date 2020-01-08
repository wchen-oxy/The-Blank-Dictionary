package com.example.myapplication.Dictionaries.Bhutia;


import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.AllDictionaries;
import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.util.List;

public class Bhutia implements ResultWrapper {
    private Result result;

    public Bhutia(Bundle args, AppDatabase db) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List words = null;

//        <item>English to Bhutia (Formal)</item>
//        <item>English to Bhutia (Informal)</item>
//        <item>Bhutia to English (Formal)</item>
//        <item>Bhutia to English (Informal)</item>
//        <item>Bhutia Script to English (Formal)</item>
//        <item>Bhutia Script to English (Informal)</item>

        switch (args.getString("TRANSLATION")){
            case ("English to Bhutia (Formal)"):
                Log.d("English to Bhutia f", "Found");
//                words = bhutiaDao.getAll();
                words = bhutiaDao.engTranSearch(args.getString("query")+"%");
                break;

            case ("English to Bhutia (Informal)"):
                Log.d("English to Bhutia i", "Found");
                words = bhutiaDao.engTranSearch(args.getString("query")+"%");
                break;

            case ("Bhutia to English (Formal)"):
                Log.d("Bhutia to English f", "Found");
                words = bhutiaDao.bhutRomFormalSearch(args.getString("query")+"%");
                break;

            case ("Bhutia to English (Informal)"):
                Log.d("Bhutia to English i", "Found");
                words = bhutiaDao.bhutRomInformalSearch(args.getString("query")+"%");
                break;


            case ("Bhutia Script to English (Formal)"):
                Log.d("Bhutia to English f", "Found");
                words = bhutiaDao.bhutScriptFormalSearch(args.getString("query")+"%");
                break;

            case ("Bhutia Script to English (Informal)"):
                Log.d("Bhutia to English i", "Found");
                words = bhutiaDao.bhutScriptInformalSearch(args.getString("query")+"%");
                break;

//
//            case ("Bhutia to English"):
//                Log.d("BHUTIA FOUND", "BHUTIA!!");
//                words = bhutiaDao.bhutiaSearch( args.getString("query")+"%");
//                break;
//
//            case ("English to Bhutia"):
//                words = bhutiaDao.englishSearch(args.getString("query")+"%");
//                break;
//
//
//            case ("Tibetan to Bhutia"):
//                Log.d("Tibetan to Bhutia", "Tibetan");
//                words = bhutiaDao.tibetanSearch(args.getString("query")+"%");
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