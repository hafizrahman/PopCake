package com.example.android.popcake;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popcake.FragmentRecipe.OnListFragmentInteractionListener;
import com.example.android.popcake.model.Recipe;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private final OnListFragmentInteractionListener mListener;

    public MyRecipeRecyclerViewAdapter(List<Recipe> items, OnListFragmentInteractionListener listener) {
        mRecipes = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mRecipes.get(position);
        holder.mRecipeNameView.setText(mRecipes.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        }
        else {
            return mRecipes.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mRecipeNameView;
        Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRecipeNameView = view.findViewById(R.id.fragment_recipe_name);
        }
    }

    // This will get called by Observer of the LiveData that's created on the ActivityMain
    // in order to fill and update the adapter's data, if a change is observed.
    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        this.notifyDataSetChanged();
    }
}
