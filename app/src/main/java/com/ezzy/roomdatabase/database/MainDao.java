package com.ezzy.roomdatabase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //delete query
    @Delete
    void delete(MainData mainData);

    //delete all
    @Delete
    void reset(List<MainData> mainData);

    //update query
    @Query("UPDATE data_table SET title = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //get all data
    @Query("SELECT * FROM data_table")
    List<MainData> getAll();
}
