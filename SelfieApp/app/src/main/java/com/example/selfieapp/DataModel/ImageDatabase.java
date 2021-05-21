package com.example.selfieapp.DataModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SelfieImage.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract ImageDAO imageDAO();
}
