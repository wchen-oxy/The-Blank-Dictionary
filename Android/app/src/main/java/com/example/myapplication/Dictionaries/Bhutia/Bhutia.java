package com.example.myapplication.Dictionaries.Bhutia;


import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.AllDictionaries;
import com.example.myapplication.Dictionaries.AppDatabase;

import java.util.List;

public class Bhutia extends AllDictionaries {

    public List returnDictionary(Bundle args, AppDatabase db) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        List<BhutiaWord> words = null;
        switch (args.getString("TRANSLATION")){
            case ("Bhutia to English"):
                Log.d("BHUTIA FOUND", "BHUTIA!!");
                words = bhutiaDao.BhutiaSearch( args.getString("query")+"%");
                return words;
            case ("English to Bhutia"):
                words = bhutiaDao.EnglishSearch(args.getString("query")+"%");
                return words;
            case ("Tibetan to Bhutia"):
                Log.d("Tibetan to Bhutia", "Tibetan");
                words = bhutiaDao.TibetanSearch(args.getString("query")+"%");
                return words;
        }

        //shouldn't reach here
        //assert
        return null;
    }
}