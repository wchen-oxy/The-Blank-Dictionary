package com.blankdictionary.myapplication.Dictionaries.Bhutia;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BhutiaWords")
public class BhutiaWord {
    @PrimaryKey
    @NonNull
    public int entry_id;
    @ColumnInfo(name = "eng_trans")
    public String eng_trans;
    @ColumnInfo(name = "bhut_rom_formal")
    public String bhut_rom_formal;
    @ColumnInfo(name = "bhut_rom_informal")
    public String bhut_rom_informal;
    @ColumnInfo(name = "bhut_script_formal")
    public String bhut_script_formal;
    @ColumnInfo(name = "bhut_script_informal")
    public String bhut_script_informal;
    @ColumnInfo(name = "source")
    public String source;

    public BhutiaWord(int entry_id, String eng_trans, String bhut_rom_formal,
                      String bhut_rom_informal, String bhut_script_formal,
                      String bhut_script_informal, String source) {

        this.entry_id = entry_id;
        this.eng_trans = eng_trans;
        this.bhut_rom_formal = bhut_rom_formal;
        this.bhut_rom_informal = bhut_rom_informal;
        this.bhut_script_formal = bhut_script_formal;
        this.bhut_script_informal = bhut_script_informal;
        this.source = source;
    }
}