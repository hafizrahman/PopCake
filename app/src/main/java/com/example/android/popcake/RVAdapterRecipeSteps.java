package com.example.android.popcake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popcake.FragmentRecipeSteps.OnListFragmentInteractionListener;
import com.example.android.popcake.model.Step;

import java.util.List;


public class RVAdapterRecipeSteps extends RecyclerView.Adapter<RVAdapterRecipeSteps.ViewHolder> {

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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mSteps.get(position);
        holder.tvRecipeStepView.setText(mSteps.get(position).getShortDescription());

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
        if (mSteps == null) {
            return 0;
        }
        else {
            return mSteps.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView tvRecipeStepView;
        Step mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvRecipeStepView = view.findViewById(R.id.fragment_recipe_step_name);
        }
    }

    // This will get called by Observer of the LiveData
    // in order to fill and update the adapter's data, if a change is observed.
    public void setSteps(List<Step> steps){
        mSteps = steps;
        this.notifyDataSetChanged();
    }
}
