package com.example.myapplication.Dictionaries.Bhutia;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BhutiaWords")
public class BhutiaWord {
    public BhutiaWord(String ipa, String category, String eng_trans, String bhut_rom_formal,
                      String bhut_rom_informal, String bhut_script_formal, String bhut_script_informal,
                      String example, String spoken_b, String spoken_e){

        this.ipa = ipa;
        this.category = category;
        this.eng_trans = eng_trans;
        this.bhut_rom_formal = bhut_rom_formal;
        this.bhut_rom_informal = bhut_rom_informal;
        this.bhut_script_formal = bhut_script_formal;
        this.bhut_rom_informal = bhut_script_informal;
        this.example = example;
        this.spoken_b = spoken_b;
        this.spoken_e = spoken_e;
    }
    @PrimaryKey @NonNull
    public String eng_trans;

    @ColumnInfo(name = "ipa")
    public String ipa;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "bhut_rom_formal")
    public String bhut_rom_formal;

    @ColumnInfo(name = "bhut_rom_informal")
    public String bhut_rom_informal;

    @ColumnInfo(name = "bhut_script_formal")
    public String bhut_script_formal;

    @ColumnInfo(name = "bhut_script_informal")
    public String bhut_script_informal;

    @ColumnInfo(name = "example")
    public String example;

    @ColumnInfo(name = "spoken_b")
    public String spoken_b;

    @ColumnInfo(name = "spoken_e")
    public String spoken_e;
}