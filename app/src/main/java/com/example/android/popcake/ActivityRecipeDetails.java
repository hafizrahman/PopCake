package com.example.android.popcake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.viewmodel.ViewModelRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class ActivityRecipeDetails extends AppCompatActivity  implements FragmentRecipeSteps.OnListFragmentInteractionListener {
    private ViewModelRecipe mRecipeListVM;
    TextView tvRecipeDetailsIngredient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        tvRecipeDetailsIngredient = findViewById(R.id.recipe_details_ingredients);

        // Get recipeID from SharedPreference
        // Save recipeId in SharedPreference so fragments can grab this info
        SharedPreferences mSharedPreferences = getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
        int mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_RECIPE_ID, 1);

        mRecipeListVM = ViewModelProviders.of(this).get(ViewModelRecipe.class);

        // TODO: Get LiveData of the list of Ingredients based on recipeId
        // TODO: Observe the LiveData above
        mRecipeListVM.getRecipeIngredientList(mRecipeId).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                //Log.d(Const.APP_TAG, "ZZZ Y List size is " + ingredients.size());
                String ingredientsText = convertIngredientsListToString(ingredients);
                //Log.d(Const.APP_TAG, ingredientsText);
                //Log.d(Const.APP_TAG, "------");
                tvRecipeDetailsIngredient.setText(ingredientsText);
            }
        });
    }

    private String convertIngredientsListToString(List<Ingredient> ingredients) {
        String result = "";
        for(Ingredient ingredient : ingredients) {
            result += ingredient.getQuantity().toString() + " ";
            result += ingredient.getMeasure() + " ";
            result += ingredient.getIngredient();
            result += "\n";
        }

        return result;
    }

    @Override
    public void onListFragmentInteraction(int stepId) {
        // Save recipeId in SharedPreference so fragments can grab this info
        /*
        SharedPreferences mSharedPreferences = getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        int mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_STEP_ID, 0);
        if (mRecipeId == 0) {
            mEditor.putInt(Const.PREFS_CURRENT_STEP_ID, stepId);
            mEditor.apply(); // asynchronous, but should be OK as it's a small data.
        }
        */

        Log.d(Const.APP_TAG, "HEYA, selected Step ID is " + stepId);
        // TODO: Since we are now working on Phone mode,
        // we will want to call another activity that has the Step Details Fragment in it.
        // The tricky part here is that:
        // 1) we have the recipeId, but no Steps data, so
        // 2) we have to somehow pull the Steps data
        // 3) We need to bundle that data and send it to the next activity
        // 4)

       /*
        Intent intent = new Intent(this, ActivityRecipeDetails.class);
        intent.putExtra("PGK_RECIPE_ID", recipeId);
        startActivity(intent);
        */
    }

}
