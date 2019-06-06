package com.example.android.popcake.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.popcake.model.Recipe;
import com.example.android.popcake.repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mRecipeList;
    public RecipeListViewModel(Application application) {
        super(application);
        // This will create the repository, in which it will also do a network call.
        mRepository = new RecipeRepository(application);
        setRecipeList();
    }

    // TODO: create function inside the repository to pull data from DB, then use the method below to populate
    public void setRecipeList() {
        mRecipeList = mRepository.getListRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }
}
