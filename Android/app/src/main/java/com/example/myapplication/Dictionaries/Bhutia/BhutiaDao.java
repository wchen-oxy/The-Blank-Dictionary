package com.example.myapplication.Dictionaries.Bhutia;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//FIXME change the SQL commands
@Dao
public interface BhutiaDao {
    @Query("SELECT * FROM BhutiaWords")
    List<BhutiaWord> getAll();

    @Query("SELECT * FROM BhutiaWords WHERE romanization LIKE :query")
    List<BhutiaWord> bhutiaSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE eng_trans LIKE :query")
    List<BhutiaWord> englishSearch(String query);

    @Query("SELECT * FROM BhutiaWords WHERE tib_script LIKE :query")
    List<BhutiaWord> tibetanSearch(String query);


//    @Query("SELECT romanization FROM BhutiaWords WHERE romanization like 'Chi%'")
//    List<BhutiaWord> TSearch();


//    @Query("SELECT * FROM word WHERE uid IN (:userIds)")
//    List<BhutiaWord> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM bhutiaWords WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    BhutiaWord findByName(String first, String last);
    //FIXME figure out why you need onConflict, what is it replacing?
    @Insert
    void insertAll(List<BhutiaWord> words);

    @Query("DELETE FROM BhutiaWords")
    void deleteAll();

//    @Delete
//    void deleteAll(BhutiaWord... words);
}