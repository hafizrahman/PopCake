package com.example.android.popcake.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.android.popcake.model.Step;

@Dao
public interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Step step);
}
