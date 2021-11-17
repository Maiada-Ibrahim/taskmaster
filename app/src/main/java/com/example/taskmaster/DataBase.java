package com.example.taskmaster;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Task.class}, version = 1)
public abstract class  DataBase extends RoomDatabase {
    public abstract Daorep taskDao();


    private static DataBase db =null;
    public static DataBase getDataBaseObj(Context context){
        if (db==null){
         db = Room.databaseBuilder(context.getApplicationContext(),DataBase.class, "tasks").allowMainThreadQueries().build();
        }
        return db;
    }

}
