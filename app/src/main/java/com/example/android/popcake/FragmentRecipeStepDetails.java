package com.example.android.popcake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.popcake.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class FragmentRecipeStepDetails extends Fragment {
    String videoUrl;
    String stepDescription;
    int stepNumber;
    int mRecipeId;
    List<Step> mListSteps;

    TextView mStepsDescription;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    Button mPreviousButton;
    Button mNextButton;
    Boolean videoExists;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    private OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener {
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            videoUrl = getArguments().getString(Const.KEY_STEP_VIDEO_URL);
            stepDescription = getArguments().getString(Const.KEY_STEP_DESCRIPTION);
            stepNumber = getArguments().getInt(Const.KEY_STEP_NUMBER);
        }
        if(videoUrl != null) {
            videoExists = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recipe_step_details, container, false);

        mStepsDescription = rootView.findViewById(R.id.step_details_instruction);
        mPlayerView = rootView.findViewById(R.id.step_details_video);
        // Hide player if there's no proper video URL
        if(! videoExists) {
            mPlayerView.setVisibility(View.GONE);
        }
        mStepsDescription.setText(stepDescription);
        mNextButton = rootView.findViewById(R.id.btn_step_details_next);
        mPreviousButton = rootView.findViewById(R.id.btn_step_details_prev);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });

        // Hide Previous button if it's the fragment for recipe step 0
        // (because there's nothing more previous than it)
        if(stepNumber == 0) {
            mPreviousButton.setVisibility(View.GONE);
        }

        return rootView;
    }

    // Constructor for creating Fragment with arguments
    // See https://www.myandroidsolutions.com/2017/10/20/android-viewpager-tutorial/
    public static FragmentRecipeStepDetails newInstance(String videoUrl, String stepDescription, int StepNumber) {
        FragmentRecipeStepDetails mFragmentRecipeStepsDetails = new FragmentRecipeStepDetails();
        Bundle args = new Bundle();
        args.putString(Const.KEY_STEP_VIDEO_URL, videoUrl);
        args.putString(Const.KEY_STEP_DESCRIPTION, stepDescription);
        args.putInt(Const.KEY_STEP_NUMBER, StepNumber);
        mFragmentRecipeStepsDetails.setArguments(args);
        return mFragmentRecipeStepsDetails;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (videoExists && Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (videoExists && (Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoExists && Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (videoExists && Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mPlayerView.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(currentWindow, playbackPosition);
        }

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "PopCake"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl));
        // Prepare the player with the source.
        mPlayer.prepare(videoSource);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
