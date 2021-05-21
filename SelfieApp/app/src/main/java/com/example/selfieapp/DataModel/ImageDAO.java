package com.example.selfieapp.DataModel;

import androidx.room.Query;

import java.util.List;

public class ImageDAO {
    @Query("Select * from Images")
    List<SelfieImage> getAllImages() {
        return null;
    }

}
