package com.example.android.popcake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity implements FragmentRecipe.OnListFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(int recipeId) {
        // Save recipeId in SharedPreference so fragments can grab this info
        SharedPreferences mSharedPreferences = getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        int mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_RECIPE_ID, 0);

        // Save the recipe ID
        if (mRecipeId != 0) {
            mEditor.putInt(Const.PREFS_CURRENT_RECIPE_ID, recipeId);
            mEditor.apply(); // asynchronous, but should be OK as it's a small data.
        }

        // Since we are now working on Phone mode,
        // I think here we will want to call another activity that has the Details Fragment in it.
        Intent intent = new Intent(this, ActivityRecipeDetails.class);
        intent.putExtra("PGK_RECIPE_ID", recipeId);
        startActivity(intent);
    }
}
