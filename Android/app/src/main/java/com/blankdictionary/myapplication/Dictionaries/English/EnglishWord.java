package com.blankdictionary.myapplication.Dictionaries.English;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EnglishWords")
public class EnglishWord {

    @PrimaryKey
    @NonNull
    public int entry_id;
    @ColumnInfo(name = "word")
    public String word;
    @ColumnInfo(name = "definition")
    public String definition;


    public EnglishWord(int entry_id, String word, String definition) {
        this.entry_id = entry_id;
        this.word = word;
//        this.ipa = ipa;
        this.definition = definition;
//        this.example = example;
    }
}