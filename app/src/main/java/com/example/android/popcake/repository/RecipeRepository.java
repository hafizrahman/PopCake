package com.example.android.popcake.repository;

import android.app.Application;
import android.util.Log;

import com.example.android.popcake.Const;
import com.example.android.popcake.network.RecipeService;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private RecipeService service;

    public RecipeRepository(Application application) {
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

        Call<JsonObject> mRecipes = service.getRecipes();
        mRecipes.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject mResponse = response.body();
                // TODO: Do something with the response, save it to the database
                Log.d(Const.APP_TAG, mResponse.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(Const.APP_TAG, "Network fail.");
                t.printStackTrace();

            }
        });
    }
}
