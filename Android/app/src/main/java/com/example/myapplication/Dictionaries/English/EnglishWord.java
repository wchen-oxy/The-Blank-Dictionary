package com.example.myapplication.Dictionaries.English;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EnglishWords")
public class EnglishWord {

    @PrimaryKey @NonNull
    public String word;
    @ColumnInfo(name = "ipa")
    public String ipa;
    @ColumnInfo(name = "definition")
    public String definition;
    @ColumnInfo(name = "example")
    public String example;

    public EnglishWord(String word, String ipa, String definition, String example) {
        this.word = word;
        this.ipa = ipa;
        this.definition = definition;
        this.example = example;
    }
}