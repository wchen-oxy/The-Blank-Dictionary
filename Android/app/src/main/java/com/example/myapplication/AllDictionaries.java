package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;

import java.util.List;

abstract class AllDictionaries {
    abstract List returnDictionary(Bundle args, AppDatabase db);
}
//TODO use polymorphism by putting in a DAO

class Bhutia extends AllDictionaries{
    List returnDictionary(Bundle args, AppDatabase db) {
        BhutiaDao bhutiaDao = db.getBhutiaDao();
        switch (args.getString("TRANSLATION")){
            case ("Bhutia to English"):
                Log.d("BHUTIA FOUND", "BHUTIA!!");
                List<BhutiaWord> words = bhutiaDao.EngSearch( args.getString("query")+"%");
                return words;
            case ("Tibetan to Bhutia"):
                Log.d("Tibet not done", "Tibetan");
                return null;
        }
        //shouldn't reach here
        //assert
        return null;
    }
}

class English extends AllDictionaries{
    List returnDictionary(Bundle args, AppDatabase db){
        return null;
    }
}
