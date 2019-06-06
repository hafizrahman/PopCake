package com.example.android.popcake;

import android.os.Bundle;

import com.example.android.popcake.viewmodel.ViewModelRecipeList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class ActivityRecipeDetails extends AppCompatActivity {
    private ViewModelRecipeList mRecipeListVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mRecipeListVM = ViewModelProviders.of(this).get(ViewModelRecipeList.class);

    }

}
