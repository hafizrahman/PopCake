package com.example.android.popcake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;

import com.example.android.popcake.model.Step;
import com.example.android.popcake.viewmodel.ViewModelRecipe;

import java.util.List;

public class ActivityStepsDetails extends AppCompatActivity {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private ViewModelRecipe mRecipeListVM;
    int mRecipeId;
    int currentStepNumber;
    List<Step> mListSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        Intent i = getIntent();
        currentStepNumber = i.getIntExtra(Const.PACKAGE_STEP_NUMBER, 1);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.vp_activity_steps_details);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // Make the starting position of the viewpager the currently selected step
        Log.d(Const.APP_TAG, "ZZZZZ WHAAT" + currentStepNumber);

        // Get recipeID from SharedPreference
        SharedPreferences mSharedPreferences = getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
        mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_RECIPE_ID, 1);

        // Observer for Recipes LiveData
        mRecipeListVM = ViewModelProviders.of(this).get(ViewModelRecipe.class);
        mRecipeListVM.getRecipeStepList(mRecipeId).observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                mListSteps = steps;
                mPagerAdapter.notifyDataSetChanged();
                mPager.setCurrentItem(currentStepNumber);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public FragmentRecipeStepDetails getItem(int position) {
            Step currentStep = mListSteps.get(position);
            return FragmentRecipeStepDetails.newInstance(
                    currentStep.getVideoURL(),
                    currentStep.getDescription());
        }

        @Override
        public int getCount() {
            if (mListSteps == null) {
                return 0;
            }
            else {
                return mListSteps.size();
            }
        }
    }
}
