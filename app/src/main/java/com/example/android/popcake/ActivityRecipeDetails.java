package com.example.android.popcake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.model.Step;
import com.example.android.popcake.viewmodel.ViewModelRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class ActivityRecipeDetails extends AppCompatActivity  implements FragmentRecipeSteps.OnListFragmentInteractionListener {
    private ViewModelRecipe mRecipeListVM;
    TextView tvRecipeDetailsIngredient;
    List<Step> mCurrentStepsData;
    int mRecipeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        tvRecipeDetailsIngredient = findViewById(R.id.recipe_details_ingredients);

        // Get recipeID from SharedPreference
        // Save recipeId in SharedPreference so fragments can grab this info
        SharedPreferences mSharedPreferences = getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
        mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_RECIPE_ID, 1);

        mRecipeListVM = ViewModelProviders.of(this).get(ViewModelRecipe.class);

        // TODO: Get LiveData of the list of Ingredients based on recipeId and Observe the LiveData
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

        mRecipeListVM.getRecipeStepList(mRecipeId).observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                mCurrentStepsData = steps;
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

        Log.d(Const.APP_TAG, "HEYA, selected Step ID is " + stepId + " and Recipe Id is " + mRecipeId);
        // TODO: Since we are now working on Phone mode,
        // we will want to call another activity that has the Step Details Fragment in it.
        // The tricky part here is that:
        // 1) we have the recipeId, but no Steps data, so
        // 2) we have to somehow pull the Steps data
        // 3) We need to bundle that data and send it to the next activity. Using Serializable
        //    seems like a good idea https://stackoverflow.com/a/31972180
        // We also have a VM instance in this class already, so we can use that to pull the data
        // I did the piece of code already above (the `mCurrentStepsData = steps;` line), so
        // That probably solves all the problem.

       /*
        Intent intent = new Intent(this, ActivityRecipeDetails.class);
        intent.putExtra("PGK_RECIPE_ID", recipeId);
        startActivity(intent);
        */
    }

}
