package com.example.android.miwok;

import android.media.MediaPlayer;
import android.widget.ImageView;

/**
 * Created by TQ on 10-Feb-18.
 */

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE;

    private static final int NO_IMAGE = -1;

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID, int audioResourceId) {
        this(defaultTranslation, miwokTranslation, audioResourceId);
        mImageResourceId = imageResourceID;
    }

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    // Getters
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage() {
        return (mImageResourceId != NO_IMAGE);
    }
}
