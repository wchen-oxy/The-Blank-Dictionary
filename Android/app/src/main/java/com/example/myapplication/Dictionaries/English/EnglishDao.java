package com.example.myapplication.Dictionaries.English;

import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//FIXME change the SQL commands
@Dao
public interface EnglishDao {
    @Query("SELECT * FROM EnglishWord")
    List<EnglishWord> getAll();

    @Query("SELECT * FROM EnglishWord WHERE word LIKE :query")
    List<EnglishWord> englishSearch(String query);


//    @Query("SELECT * FROM word WHERE uid IN (:userIds)")
//    List<EnglishWord> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM word WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    List<EnglishWord> findByName(String first, String last);
//
//    @Insert
//    void insertAll(Word... words);
//
//    @Delete
//    void delete(Word user);
}
