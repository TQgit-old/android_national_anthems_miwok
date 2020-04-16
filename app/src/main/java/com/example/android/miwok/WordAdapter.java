package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by TQ on 10-Feb-18.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private Toast toastOnScreen;
    private int mColorResourceId;
    private int mediaButtonsState;

    MediaPlayer mp;
    private MediaPlayer.OnCompletionListener completionListener;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    private int currentDuration;
    private TextView currentDefaultView;
    private final int REWIND_AND_FORWARD_AMOUNT = 3000;

    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            mediaButtons.currentSeekBar.setProgress(mp.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 200);
        }
    };

    Word wordPlaying;
    WordAdapterButtons mediaButtons;

    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects, int colorResourceId, int mediaButtonsState) {
        super(context, 0, objects);
        toastOnScreen = new Toast(getContext());
        mColorResourceId = colorResourceId;
        this.mediaButtonsState = mediaButtonsState;
        currentDefaultView = new TextView(getContext());
        mp = new MediaPlayer();
        mediaButtons = new WordAdapterButtons(getContext(), mediaButtonsState);
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (mp != null) {
                            mp.start();
                        }
                    case AudioManager.AUDIOFOCUS_LOSS:
                        mp.release();
                        wordPlaying = null;
                        mediaButtons.setPlayVisible(getContext());
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (mp != null) {
                            mp.pause();
                        }
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (mp != null) {
                            mp.pause();
                        }
                }
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Word currentWord = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final RelativeLayout textPlayRelativeLayout = listItemView.findViewById(R.id.text_play_relative_layout);
        textPlayRelativeLayout.setBackgroundResource(mColorResourceId);

        final TextView defaultTextView = listItemView.findViewById(R.id.default_textview);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        TextView miwokTextView = listItemView.findViewById(R.id.miwok_textview);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        ImageView wordImage = listItemView.findViewById(R.id.word_image);

        if (currentWord.hasImage()) {
            wordImage.setImageResource(currentWord.getImageResourceId());
            wordImage.setVisibility(View.VISIBLE);
        } else {
            wordImage.setVisibility(View.GONE);
        }

        final View clickBlocker = listItemView.findViewById(R.id.click_blocker_view);
        final ImageView playIcon = listItemView.findViewById(R.id.play_icon);
        final ImageView pauseIcon = listItemView.findViewById(R.id.pause_icon);
        final ImageView stopIcon = listItemView.findViewById(R.id.stop_icon);
        final ImageView rewindIcon = listItemView.findViewById(R.id.rewind_icon);
        final ImageView forwardIcon = listItemView.findViewById(R.id.forward_icon);
        final SeekBar seekBar = listItemView.findViewById(R.id.seekbar);

        if (currentWord == wordPlaying) {
            mediaButtons.setViews(playIcon, pauseIcon, stopIcon, rewindIcon, forwardIcon, clickBlocker, seekBar);
            currentDefaultView = defaultTextView;
            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            if (mp.isPlaying()) {
                mediaButtons.setPauseVisible();
                if (mediaButtonsState == WordAdapterButtons.STOP_AND_SEEK) {
                    defaultTextView.setVisibility(View.INVISIBLE);
                    mediaButtons.currentSeekBar.setMax(currentDuration);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                }
                completionListener = null;
                mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaButtons.setPlayVisible(getContext());
                        defaultTextView.setVisibility(View.VISIBLE);
                        audioManager.abandonAudioFocus(audioFocusChangeListener);
                        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
                    }
                });
            } else {
                mediaButtons.setPlayVisible(getContext());
                defaultTextView.setVisibility(VISIBLE);
            }
        } else {
            defaultTextView.setVisibility(VISIBLE);
            playIcon.setVisibility(View.VISIBLE);
            pauseIcon.setVisibility(View.GONE);
            stopIcon.setVisibility(View.GONE);
            rewindIcon.setVisibility(View.GONE);
            forwardIcon.setVisibility(View.GONE);
            clickBlocker.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);
        }

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWord == wordPlaying) {
                    mp.start();
                    mediaButtons.setPauseVisible();
                    if (mediaButtonsState == WordAdapterButtons.STOP_AND_SEEK) {
                        defaultTextView.setVisibility(View.INVISIBLE);
                        mediaButtons.currentSeekBar.setMax(currentDuration);
                        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                    }
                    completionListener = null;
                    mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaButtons.setPlayVisible(getContext());
                            defaultTextView.setVisibility(VISIBLE);
                            audioManager.abandonAudioFocus(audioFocusChangeListener);
                        }
                    });
                    return;
                }

                mp.release();
                mp = null;
                completionListener = null;
                mediaButtons.setPlayVisible(getContext());
                currentDefaultView.setVisibility(VISIBLE);
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

                mediaButtons.setViews(playIcon, pauseIcon, stopIcon, rewindIcon, forwardIcon, clickBlocker, seekBar);
                currentDefaultView = defaultTextView;

                wordPlaying = currentWord;
                mp = MediaPlayer.create(getContext(), wordPlaying.getAudioResourceId());
                audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                currentDuration = mp.getDuration();

                mp.start();
                mediaButtons.setPauseVisible();
                if (mediaButtonsState == WordAdapterButtons.STOP_AND_SEEK) {
                    mediaButtons.currentSeekBar.setMax(currentDuration);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                }
                if (mediaButtonsState == WordAdapterButtons.STOP_AND_SEEK) {
                    defaultTextView.setVisibility(View.INVISIBLE);
                }
                mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaButtons.setPlayVisible(getContext());
                        defaultTextView.setVisibility(VISIBLE);
                        audioManager.abandonAudioFocus(audioFocusChangeListener);
                        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
                    }
                });
            }
        });

        pauseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                mediaButtons.setPlayVisible(getContext());
                defaultTextView.setVisibility(VISIBLE);
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            }
        });

        textPlayRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playIcon.getVisibility() == VISIBLE) {
                    playIcon.performClick();
                } else {
                    pauseIcon.performClick();
                }
            }
        });

        stopIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                wordPlaying = null;
                mediaButtons.setPlayVisible(getContext());
                defaultTextView.setVisibility(VISIBLE);
                audioManager.abandonAudioFocus(audioFocusChangeListener);
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            }
        });

        rewindIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int endPosition = mp.getCurrentPosition() - REWIND_AND_FORWARD_AMOUNT;
                if (endPosition <= 0) {
                    toastOnScreen.cancel();
                    toastOnScreen = Toast.makeText(getContext(), "لقد وصلت لبداية النشيد", Toast.LENGTH_SHORT);
                    toastOnScreen.show();
                }
                mp.seekTo(endPosition);
            }
        });

        forwardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int endPosition = mp.getCurrentPosition() + REWIND_AND_FORWARD_AMOUNT;
                if (endPosition >= currentDuration) {
                    toastOnScreen.cancel();
                    toastOnScreen = Toast.makeText(getContext(), "لقد وصلت لنهاية النشيد", Toast.LENGTH_SHORT);
                    toastOnScreen.show();
                }
                mp.seekTo(endPosition);
            }
        });

        return listItemView;
    }
}
