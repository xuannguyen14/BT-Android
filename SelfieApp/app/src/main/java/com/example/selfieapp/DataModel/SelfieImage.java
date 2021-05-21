package com.example.selfieapp.DataModel;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Images")
public class SelfieImage {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "Name")
    String name;
    //@ColumnInfo(name = "Image")
    //Bitmap image;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] image;

    public SelfieImage() {

    }

    public SelfieImage(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
//    public SelfieImage(String name, Bitmap image) {
//        this.name = name;
//        this.image = image;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Bitmap getImage() {
//        return image;
//    }
//
//    public void setImage(Bitmap image) {
//        this.image = image;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
