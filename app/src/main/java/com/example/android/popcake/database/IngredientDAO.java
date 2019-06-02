package com.example.android.popcake.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.popcake.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    // Get all ingredients for a particular recipe
    @Query("SELECT * from tb_ingredient WHERE recipeId = :recipeId")
    LiveData<List<Ingredient>> getIngredientsForRecipe(Integer recipeId);
}
