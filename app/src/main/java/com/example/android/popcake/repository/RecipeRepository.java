package com.example.android.popcake.repository;

import android.app.Application;
import android.util.Log;

import com.example.android.popcake.Const;
import com.example.android.popcake.database.IngredientDAO;
import com.example.android.popcake.database.PopCakeRoomDatabase;
import com.example.android.popcake.database.RecipeDAO;
import com.example.android.popcake.database.StepDAO;
import com.example.android.popcake.network.RecipeService;
import com.google.gson.JsonArray;

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

    public RecipeRepository(Application application) {
        PopCakeRoomDatabase mPopCakeRoomDB;

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
                //JsonArray mResults = mResponse.getAsJsonArray();
                // TODO: Do something with the response, save it to the database
                Log.d(Const.APP_TAG, mResponse.toString());
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d(Const.APP_TAG, "Network fail.");
                t.printStackTrace();

            }
        });

        // Init DB
        mPopCakeRoomDB = PopCakeRoomDatabase.getDatabase(application);
        mIngredientDAO = mPopCakeRoomDB.ingredientDAO();
        mRecipeDAO = mPopCakeRoomDB.recipeDAO();
        mStepDAO = mPopCakeRoomDB.stepDAO();
    }
}
