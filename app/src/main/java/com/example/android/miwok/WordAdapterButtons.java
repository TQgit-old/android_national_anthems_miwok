package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by TQ on 19-Feb-18.
 */

public class WordAdapterButtons {

    private ImageView currentPlayIcon;
    private ImageView currentPauseIcon;
    private ImageView currentStopIcon;
    private ImageView currentRewindIcon;
    private ImageView currentForwardIcon;
    private View clickBlocker;
    SeekBar currentSeekBar;
    private final int mediaButtonsState;

    public static final int PAUSE_ONLY = 0;
    public static final int STOP_AND_SEEK = 1;

    public WordAdapterButtons(Context context, int state) {
        mediaButtonsState = state;

        if (state != PAUSE_ONLY && state != STOP_AND_SEEK) {
            Log.e("WordAdapterButtons", "Invalid state in constructor, fields null");
            return;
        }

        if (state == STOP_AND_SEEK) {
            currentStopIcon = new ImageView(context);
            currentRewindIcon = new ImageView(context);
            currentForwardIcon = new ImageView(context);
            clickBlocker = new View(context);
            currentSeekBar = new SeekBar(context);
        }

        currentPlayIcon = new ImageView(context);
        currentPauseIcon = new ImageView(context);
    }

    public void setViews(ImageView playIcon, ImageView pauseIcon, ImageView stopIcon, ImageView rewindIcon,
                         ImageView forwardIcon, View blockerView, SeekBar seekBar) {
        if (mediaButtonsState != PAUSE_ONLY && mediaButtonsState != STOP_AND_SEEK) {
            Log.e("WordAdapterButtons", "Invalid state, fields null");
            return;
        }
        currentPlayIcon = playIcon;
        currentPauseIcon = pauseIcon;
        currentStopIcon = stopIcon;
        currentRewindIcon = rewindIcon;
        currentForwardIcon = forwardIcon;
        clickBlocker = blockerView;
        currentSeekBar = seekBar;
        currentSeekBar.getProgressDrawable().setColorFilter(Color.parseColor("#F06292"), PorterDuff.Mode.MULTIPLY);
    }

    public void setPlayVisible(Context context) {
        if (mediaButtonsState == STOP_AND_SEEK) {
            currentStopIcon.setVisibility(View.GONE);
            currentRewindIcon.setVisibility(View.GONE);
            currentForwardIcon.setVisibility(View.GONE);
            clickBlocker.setVisibility(View.GONE);
            currentSeekBar.setVisibility(View.GONE);

//            TypedValue outValue = new TypedValue();
//            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
//            clickBlocker.setBackgroundResource(outValue.resourceId);
        }

        currentPlayIcon.setVisibility(View.VISIBLE);
        currentPauseIcon.setVisibility(View.GONE);
    }

    public void setPauseVisible() {
        if (mediaButtonsState == STOP_AND_SEEK) {
            currentStopIcon.setVisibility(View.VISIBLE);
            currentRewindIcon.setVisibility(View.VISIBLE);
            currentForwardIcon.setVisibility(View.VISIBLE);
            clickBlocker.setVisibility(View.VISIBLE);
            currentSeekBar.setVisibility(View.VISIBLE);
        }

        currentPlayIcon.setVisibility(View.GONE);
        currentPauseIcon.setVisibility(View.VISIBLE);
    }
}
