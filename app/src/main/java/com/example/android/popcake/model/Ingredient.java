package com.example.android.popcake.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.popcake.Const;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = Const.DB_TABLE_INGREDIENT)
public class Ingredient {
    @PrimaryKey
    private String ingredientId;
    @SerializedName("recipeId")
    @Expose
    private Integer recipeId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public String getIngredientId() { return ingredientId; }

    public void setIngredientId(String ingredientId) { this.ingredientId = ingredientId; }

    public Integer getRecipeId() { return recipeId; }

    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}
