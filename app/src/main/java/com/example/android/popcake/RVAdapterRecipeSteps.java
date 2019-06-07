package com.example.android.popcake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popcake.model.Recipe;
import com.example.android.popcake.model.Step;

import java.util.List;


public class RVAdapterRecipeSteps extends RecyclerView.Adapter<RVAdapterRecipes.ViewHolder> {

    private List<Step> mSteps;
    private final OnListFragmentInteractionListener mListener;

    public RVAdapterRecipeSteps(List<Step> items, OnListFragmentInteractionListener listener) {
        mSteps = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_step, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mRecipeNameView;
        Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRecipeNameView = view.findViewById(R.id.fragment_recipe_step_name);
        }
    }
}
