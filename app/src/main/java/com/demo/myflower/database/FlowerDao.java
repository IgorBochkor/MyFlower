package com.demo.myflower.database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.demo.myflower.pojo.Flower;

import java.util.List;

/* Created by Ihor Bochkor on 18.10.2020.
 */
@androidx.room.Dao
public interface FlowerDao {
    @Query("SELECT * FROM flower_table")
    LiveData<List<Flower>> getAllFlowers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFlowers(List<Flower> flowers);

    @Query("DELETE FROM flower_table")
    void deleteAllFlowers();
}
