package com.example.android.popcake.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {
    // Check https://github.com/square/retrofit/issues/1567#issuecomment-180115150
    // for the reason for the "./" usage. Basically we already have the full URL for the
    // API and does not need query parameters.
    @GET("./")
    Call<JsonObject> getRecipes();
}
