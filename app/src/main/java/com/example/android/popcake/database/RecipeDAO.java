package com.example.android.popcake.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.android.popcake.model.Recipe;

@Dao
public interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);
}
