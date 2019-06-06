package com.example.android.popcake;

import android.os.Bundle;
import android.util.Log;

import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.viewmodel.ViewModelRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class ActivityRecipeDetails extends AppCompatActivity {
    private ViewModelRecipe mRecipeListVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // TODO: Get RecipeId data from previous Activity (ActivityMain in this case)
        int recipeId = 1;

        mRecipeListVM = ViewModelProviders.of(this).get(ViewModelRecipe.class);

        // TODO: Get LiveData of the list of Ingredients based on recipeId
        // TODO: Observe the LiveData above
        mRecipeListVM.getRecipeIngredientList(recipeId).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                String ingredientsText = convertIngredientsListToString(ingredients);
                Log.d(Const.APP_TAG, ingredientsText);
            }
        });

        // TODO: Create method to check LiveData change, and massage the list and convert it into string
        // TODO: Put that string into the @+id/recipe_details_ingredients TextView
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

}
