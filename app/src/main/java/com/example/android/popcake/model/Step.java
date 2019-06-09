package com.example.android.popcake.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.popcake.Const;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = Const.DB_TABLE_STEP)
public class Step {
    @PrimaryKey
    @NonNull
    private String stepId;
    @SerializedName("recipeId")
    @Expose
    private Integer recipeId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public String getStepId() { return stepId; }

    public void setStepId(String stepId) { this.stepId = stepId; }

    public Integer getRecipeId() { return recipeId; }

    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getVideoURL() { return videoURL; }

    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }

    public String getThumbnailURL() { return thumbnailURL; }

    public void setThumbnailURL(String thumbnailURL) { this.thumbnailURL = thumbnailURL; }
}