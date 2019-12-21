package com.example.myapplication.Dictionaries;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Dictionaries.English.EnglishWord;

import java.util.List;

public class ResultWrapper {
    List<BhutiaWord> bhutiaWordList;
    List<EnglishWord> englishWordList;

    public void setBhutiaWordList(List<BhutiaWord> bhutiaWordList) {
        this.bhutiaWordList = bhutiaWordList;
    }

    public void setEnglishWordList(List<EnglishWord> englishWordList) {
        this.englishWordList = englishWordList;
    }

    public List<BhutiaWord> getBhutiaWordList() {
        return bhutiaWordList;
    }

    public List<EnglishWord> getEnglishWordList() {
        return englishWordList;
    }
}
