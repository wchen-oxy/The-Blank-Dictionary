package com.example.myapplication.Dictionaries.Bhutia;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BhutiaWords")
public class BhutiaWord {
    public BhutiaWord(String romanization, String ipa, String category, String engTrans, String tibScript, String example){
        this.romanization = romanization;
        this.ipa = ipa;
        this.category = category;
        this.engTrans = engTrans;
        this.tibScript = tibScript;
        this.example = example;
    }
    @PrimaryKey @NonNull
    public String romanization;

    @ColumnInfo(name = "ipa")
    public String ipa;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "eng_trans")
    public String engTrans;

    @ColumnInfo(name = "tib_script")
    public String tibScript;

    @ColumnInfo(name = "example")
    public String example;
}