package com.example.myapplication.Dictionaries.Bhutia;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//FIXME change the SQL commands
@Dao
public interface BhutiaDao {
    @Query("SELECT * FROM BhutiaWords")
    List<BhutiaWord> getAll();

//    @Query("SELECT * FROM word WHERE uid IN (:userIds)")
//    List<BhutiaWord> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM bhutiaWords WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    BhutiaWord findByName(String first, String last);

    @Insert
    void insertAll(List<BhutiaWord> words);

    @Delete
    void delete(BhutiaWord words);
}