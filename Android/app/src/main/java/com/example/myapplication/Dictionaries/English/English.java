package com.example.myapplication.Dictionaries.English;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Result;
import com.example.myapplication.Dictionaries.ResultWrapper;

import java.util.List;

public class English implements ResultWrapper {
    private Result result;

    public English(AppDatabase db, String query, String currentTranslationString) {
        EnglishDao EnglishDao = db.getEnglishDao();
        List words = null;

        switch (currentTranslationString) {
            case ("English to English"):
                words = EnglishDao.englishSearch(query + "%");
        }
        this.result = new Result(words);

    }

    @Override
    public Result getList() {
        return this.result;
    }
}