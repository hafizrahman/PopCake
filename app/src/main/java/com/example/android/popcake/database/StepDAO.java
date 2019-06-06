package com.example.android.popcake.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.popcake.model.Step;

import java.util.List;

@Dao
public interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Step step);


    // Get all steps for a particular recipe
    @Query("SELECT * from tb_step WHERE recipeId = :recipeId")
    LiveData<List<Step>> getStepsForRecipe(Integer recipeId);
}
