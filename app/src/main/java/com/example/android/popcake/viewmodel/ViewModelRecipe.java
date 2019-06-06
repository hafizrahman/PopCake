package com.example.android.popcake.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.model.Recipe;
import com.example.android.popcake.repository.RecipeRepository;

import java.util.List;

public class ViewModelRecipe extends AndroidViewModel {
    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mRecipeList;
    private LiveData<List<Ingredient>> mRecipeIngredientList;
    public ViewModelRecipe(Application application) {
        super(application);
        // This will create the repository, in which it will also do a network call.
        mRepository = new RecipeRepository(application);
        setRecipeList();
    }

    private void setRecipeList() {
        mRecipeList = mRepository.getListRecipes();
    }

    public LiveData<List<Ingredient>> getRecipeIngredientList(int recipeId) {
        mRecipeIngredientList = mRepository.getListIngredients(recipeId);
        return mRecipeIngredientList;
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }

}
