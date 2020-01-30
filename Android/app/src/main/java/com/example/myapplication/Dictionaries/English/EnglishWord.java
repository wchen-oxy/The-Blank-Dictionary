package com.example.myapplication.Dictionaries.English;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EnglishWords")
public class EnglishWord {
    public EnglishWord(String ipa, String definition, String example){

    }
    @PrimaryKey
    public int word;

    @ColumnInfo(name = "ipa")
    public String ipa;

    @ColumnInfo(name = "definition")
    public String def;

    @ColumnInfo(name = "example")
    public String example;
}