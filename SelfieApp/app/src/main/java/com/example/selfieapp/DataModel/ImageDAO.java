package com.example.selfieapp.DataModel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDAO {
    @Query("SELECT * FROM images")
    List<SelfieImage> getAll();

    @Insert
    void insertAll(SelfieImage... images);
}
