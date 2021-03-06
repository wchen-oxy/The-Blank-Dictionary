package com.blankdictionary.myapplication.Dictionaries.Bhutia;


import android.util.Log;

import com.blankdictionary.myapplication.Dictionaries.AppDatabase;
import com.blankdictionary.myapplication.Dictionaries.Result;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;

import java.util.List;


public class Bhutia implements ResultWrapper {
    private Result result;

    public Bhutia(AppDatabase db) {
        //delete all
        db.getBhutiaDao().deleteAll();
    }


    public Bhutia(AppDatabase db, String query, String currentTranslationString) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List words = null;

        switch (currentTranslationString) {
            case ("English to Bhutia (Formal)"):
                Log.d("English to Bhutia (for)", "Found");
                words = bhutiaDao.engTranSearch(query + "%");
                break;

            case ("English to Bhutia (Informal)"):
                Log.d("English to Bhutia (inf)", "Found");
                words = bhutiaDao.engTranSearch(query + "%");
                break;

            case ("Bhutia to English (Formal)"):
                Log.d("Bhutia to English (for)", "Found");
                words = bhutiaDao.bhutRomFormalSearch(query + "%");
                break;

            case ("Bhutia to English (Informal)"):
                Log.d("Bhutia to English (inf)", "Found");
                words = bhutiaDao.bhutRomInformalSearch(query + "%");
                break;


            case ("Bhutia Script to English (Formal)"):
                Log.d("Bhu Script to English", "Found");
                words = bhutiaDao.bhutScriptFormalSearch(query + "%");
                break;

            case ("Bhutia Script to English (Informal)"):
                Log.d("Bhu Script to English", "Found");
                words = bhutiaDao.bhutScriptInformalSearch(query + "%");
                break;

        }
        Log.d("Resulting words", words.toString());
        this.result = new Result(words);

    }

    @Override
    public Result getList() {
        return this.result;
    }
}