package com.example.android.popcake.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.android.popcake.Const;
import com.example.android.popcake.database.IngredientDAO;
import com.example.android.popcake.database.PopCakeRoomDatabase;
import com.example.android.popcake.database.RecipeDAO;
import com.example.android.popcake.database.StepDAO;
import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.model.Recipe;
import com.example.android.popcake.model.Step;
import com.example.android.popcake.network.RecipeService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private RecipeService service;
    private final IngredientDAO mIngredientDAO;
    private final RecipeDAO mRecipeDAO;
    private final StepDAO mStepDAO;

    private List<Recipe> mListRecipes;

    public RecipeRepository(Application application) {
        PopCakeRoomDatabase mPopCakeRoomDB;

        // Init DB
        mPopCakeRoomDB = PopCakeRoomDatabase.getDatabase(application);
        mIngredientDAO = mPopCakeRoomDB.ingredientDAO();
        mRecipeDAO = mPopCakeRoomDB.recipeDAO();
        mStepDAO = mPopCakeRoomDB.stepDAO();

        // Init network
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(RecipeService.class);
        final Call<JsonArray> mRecipes = service.getRecipes();

        mRecipes.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray mResponse = response.body();
                //Log.d(Const.APP_TAG, mResponse.toString());

                // Saving to DB
                Type listType = new TypeToken<List<Recipe>>() {}.getType();
                mListRecipes = new Gson().fromJson(mResponse, listType);

                Recipe tempRecipe;
                Ingredient tempIngredient;
                Step tempStep;

                for(int i = 0; i < mListRecipes.size(); i++) {
                    tempRecipe = mListRecipes.get(i);
                    new insertRecipeAsyncTask(mRecipeDAO).execute(tempRecipe);
                    // Insert Ingredients
                    for(int j = 0; j < tempRecipe.getIngredients().size(); j++) {
                        tempIngredient = tempRecipe.getIngredients().get(j);
                        tempIngredient.setRecipeId(tempRecipe.getId());
                        tempIngredient.setIngredientId(generateUniqueID(i, j));
                        new insertIngredientAsyncTask(mIngredientDAO).execute(tempIngredient);
                    }
                    // Insert Steps
                    for(int j = 0; j < tempRecipe.getSteps().size(); j++) {
                        tempStep = tempRecipe.getSteps().get(j);
                        tempStep.setRecipeId(tempRecipe.getId());
                        tempStep.setStepId(generateUniqueID(i, tempRecipe.getId()));
                        //Log.d(Const.APP_TAG, "ZZZ steps" + generateUniqueID(i, j));
                        new insertStepAsyncTask(mStepDAO).execute(tempStep);
                    }
                }
            }

            private String generateUniqueID(int recipeId, int itemId) {
                return recipeId + ":" + itemId;
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d(Const.APP_TAG, "Network fail.");
                t.printStackTrace();

            }
        });

    }

    // Insert method for Recipe
    static class insertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO mAsyncTaskDAO;
        insertRecipeAsyncTask(RecipeDAO dao) { mAsyncTaskDAO = dao; }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            mAsyncTaskDAO.insert(recipes[0]);
            return null;
        }
    }

    // Insert method for Ingredients
    static class insertIngredientAsyncTask extends AsyncTask<Ingredient, Void, Void> {
        private IngredientDAO mAsyncTaskDAO;
        insertIngredientAsyncTask(IngredientDAO dao) { mAsyncTaskDAO = dao; }

        @Override
        protected Void doInBackground(Ingredient... ingredients) {
            mAsyncTaskDAO.insert(ingredients[0]);
            return null;
        }
    }

    // Insert method for Steps
    static class insertStepAsyncTask extends AsyncTask<Step, Void, Void> {
        private StepDAO mAsyncTaskDAO;
        insertStepAsyncTask(StepDAO dao) { mAsyncTaskDAO = dao; }

        @Override
        protected Void doInBackground(Step... steps) {
            mAsyncTaskDAO.insert(steps[0]);
            return null;
        }
    }

    // Method to return a LiveData of a List of Recipes
    public LiveData<List<Recipe>> getListRecipes() {
        return mRecipeDAO.getRecipes();
    }

    // Method to return a LiveData of a List of Ingredient of a particular Recipe
    public LiveData<List<Ingredient>> getListIngredients(int recipeId) {
        Log.d(Const.APP_TAG, "ZZZ calling ingredients for recipe ID " + recipeId);
        return mIngredientDAO.getIngredientsForRecipe(recipeId);
    }

    // Method to return a LiveData of a List of Steps of a particular Recipe
    public LiveData<List<Step>> getListSteps(int recipeId) {
        return mStepDAO.getStepsForRecipe(recipeId);
    }
}
