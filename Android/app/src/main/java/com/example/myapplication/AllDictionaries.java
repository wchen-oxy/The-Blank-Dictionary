package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;

import java.util.List;

public abstract class AllDictionaries {
    public abstract List returnDictionary(Bundle args, AppDatabase db);
}
//TODO use polymorphism by putting in a DAO


