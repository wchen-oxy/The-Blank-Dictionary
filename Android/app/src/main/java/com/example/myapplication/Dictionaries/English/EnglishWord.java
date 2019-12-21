package com.example.myapplication.Dictionaries.English;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EnglishWord {
    @PrimaryKey
    public int word;

    @ColumnInfo(name = "ipa")
    public String ipa;

    @ColumnInfo(name = "definition")
    public String def;

    @ColumnInfo(name = "example")
    public String example;
}