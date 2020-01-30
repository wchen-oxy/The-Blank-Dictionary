package com.example.myapplication.Dictionaries.Bhutia;


import android.util.Log;
import java.util.List;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;


public class Bhutia implements ResultWrapper {
    private Result result;

    public Bhutia(AppDatabase db, String query, String currentTranslationString) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List words = null;

        switch (currentTranslationString){
            case ("English to Bhutia (Formal)"):
                Log.d("English to Bhutia f", "Found");
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

        }
        Log.d("Resulting words", words.toString());
        this.result = new Result(words);

    }

    @Override
    public Result getList(){
        return this.result;
    }
}