package com.example.android.popcake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class FragmentRecipeStepDetails extends Fragment {
    String videoUrl;
    String stepDescription;

    TextView mStepsDescription;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            videoUrl = getArguments().getString(Const.KEY_STEP_VIDEO_URL);
            stepDescription = getArguments().getString(Const.KEY_STEP_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recipe_step_details, container, false);
        mStepsDescription = rootView.findViewById(R.id.step_details_instruction);
        mPlayerView = rootView.findViewById(R.id.step_details_video);

        mStepsDescription.setText(stepDescription);
        return rootView;
    }

    // Constructor for creating Fragment with arguments
    // See https://www.myandroidsolutions.com/2017/10/20/android-viewpager-tutorial/
    public static FragmentRecipeStepDetails newInstance(String videoUrl, String stepDescription) {
        FragmentRecipeStepDetails mFragmentRecipeStepsDetails = new FragmentRecipeStepDetails();
        Bundle args = new Bundle();
        args.putString(Const.KEY_STEP_VIDEO_URL, videoUrl);
        args.putString(Const.KEY_STEP_DESCRIPTION, stepDescription);
        mFragmentRecipeStepsDetails.setArguments(args);
        return mFragmentRecipeStepsDetails;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
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

        //MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_mp4)));
        //mPlayer.prepare(mediaSource, true, false);
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
