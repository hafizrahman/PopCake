package com.example.android.popcake.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.android.popcake.model.Ingredient;

@Dao
public interface IngredientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);
}
