package com.demo.myflower.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.demo.myflower.pojo.Flower;

/* Created by Ihor Bochkor on 18.10.2020.
 */
@Database(entities = {Flower.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static final String DB_NAME = "flower_db";
    private static AppDatabase database;
    private static final Object LOCK = new Object();
    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }
    public abstract FlowerDao flowerDao();

}
