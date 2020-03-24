package com.blankdictionary.myapplication.Dictionaries.Bhutia;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BhutiaDao {
    @Query("SELECT * FROM BhutiaWords")
    List<BhutiaWord> getAll();

    @Query("SELECT * FROM BhutiaWords WHERE eng_trans LIKE :query")
    List<BhutiaWord> engTranSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE bhut_rom_formal LIKE :query")
    List<BhutiaWord> bhutRomFormalSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE bhut_rom_informal LIKE :query")
    List<BhutiaWord> bhutRomInformalSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE bhut_script_formal LIKE :query")
    List<BhutiaWord> bhutScriptFormalSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE bhut_script_informal LIKE :query")
    List<BhutiaWord> bhutScriptInformalSearch(String query);

    //FIXME figure out why you need onConflict, what is it replacing?
    @Insert
    void insertAll(List<BhutiaWord> words);

    @Query("DELETE FROM BhutiaWords")
    void deleteAll();

}