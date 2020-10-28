package com.blankdictionary.myapplication.Dictionaries;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishDao;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishWord;


//FIXME should change exportSchema to True: https://stackoverflow.com/questions/44322178/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we
@Database(entities = {BhutiaWord.class, EnglishWord.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BhutiaDao getBhutiaDao();

    public abstract EnglishDao getEnglishDao();


}



