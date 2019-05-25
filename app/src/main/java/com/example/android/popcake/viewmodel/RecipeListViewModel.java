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
        mRepository = new RecipeRepository(application);
    }

    // TODO: create function inside the repository to pull data from DB, then use the method below to populate
    public setRecipeList() {
        // mRecipeList = mRepository.TODOthismethod()l
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }
}
