package com.example.android.popcake;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity implements FragmentRecipe.OnListFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(int recipeId) {
        Log.d(Const.APP_TAG, "HEYA, selected item ID is " + recipeId);
        // TODO Actually do something with the id information
        // Since we are now working on Phone mode,
        // I think here we will want to call another activity that has the Details Fragment in it.
    }
}
