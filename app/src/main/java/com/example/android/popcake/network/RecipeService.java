package com.example.android.popcake.network;

import com.example.android.popcake.Const;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET(Const.API_JSON_FILENAME)
    Call<JsonArray> getRecipes();
}
