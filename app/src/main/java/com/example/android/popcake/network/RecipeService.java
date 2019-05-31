package com.example.android.popcake.network;

import com.example.android.popcake.Const;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    // Check https://github.com/square/retrofit/issues/1567#issuecomment-180115150
    // for the reason for the "./" usage. Basically we already have the full URL for the
    // API and does not need query parameters.
    @GET(Const.API_JSON_FILENAME)
    Call<JsonArray> getRecipes();
}
