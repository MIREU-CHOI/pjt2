package com.example.myapp;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "tb_sample")
public class Entity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "data")
    public String data;
}
