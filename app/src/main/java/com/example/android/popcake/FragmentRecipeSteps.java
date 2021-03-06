package com.example.android.popcake;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popcake.model.Step;
import com.example.android.popcake.viewmodel.ViewModelRecipe;

import java.util.List;

public class FragmentRecipeSteps extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentRecipeSteps.OnListFragmentInteractionListener mListener;
    private RVAdapterRecipeSteps mRecipeRVAdapter;
    private ViewModelRecipe mRecipeListVM;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentRecipeSteps() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentRecipeSteps newInstance(int columnCount) {
        FragmentRecipeSteps fragment = new FragmentRecipeSteps();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps_list, container, false);
        mRecipeListVM = ViewModelProviders.of(getActivity()).get(ViewModelRecipe.class);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);

                // Adding divider line between each row.
                // See https://stackoverflow.com/a/27037230
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        mLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Get recipeID from SharedPreference
            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Const.PREFS_FILE, Context.MODE_PRIVATE);
            int mRecipeId = mSharedPreferences.getInt(Const.PREFS_CURRENT_RECIPE_ID, 1);

            // Create then set the adapter
            mRecipeRVAdapter = new RVAdapterRecipeSteps(
                    mRecipeListVM.getRecipeStepList(mRecipeId).getValue(),
                    mListener);
            recyclerView.setAdapter(mRecipeRVAdapter);

            // Observer for Recipes LiveData
            mRecipeListVM.getRecipeStepList(mRecipeId).observe(this, new Observer<List<Step>>() {
                @Override
                public void onChanged(List<Step> steps) {
                    mRecipeRVAdapter.setSteps(steps);
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int stepId);
    }
}
