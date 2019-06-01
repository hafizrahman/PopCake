package com.example.android.popcake.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.popcake.Const;
import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.model.Recipe;
import com.example.android.popcake.model.Step;

@Database(entities = {Ingredient.class, Recipe.class, Step.class }, version = 1, exportSchema = false)
public abstract class PopCakeRoomDatabase extends RoomDatabase {
    public abstract IngredientDAO ingredientDAO();
    public abstract  RecipeDAO recipeDAO();
    public abstract  StepDAO stepDAO();

    // Singleton setup, because we only want one instance of DB opened at the same time
    private static PopCakeRoomDatabase INSTANCE;
    public static PopCakeRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (PopCakeRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PopCakeRoomDatabase.class,
                            Const.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        else {
            Log.d(Const.APP_TAG, "Database already made");

        }
        return INSTANCE;
    }
}
