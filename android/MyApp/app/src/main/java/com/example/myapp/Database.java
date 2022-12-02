package com.example.myapp;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Entity.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract Dao getDao();

    public static Database getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, Database.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    private static Database INSTANCE = null;

}
