package com.example.myapplication.Dictionaries.English;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EnglishWord {
    @PrimaryKey
    public int romanization;

    @ColumnInfo(name = "Word")
    public String word;

    @ColumnInfo(name = "Ipa")
    public String ipa;

    @ColumnInfo(name = "Definition")
    public String def;

    @ColumnInfo(name = "Example")
    public String example;
}