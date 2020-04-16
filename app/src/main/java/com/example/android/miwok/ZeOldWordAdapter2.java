//package com.example.android.miwok;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.media.AudioFocusRequest;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.List;
//
//import static android.view.View.VISIBLE;
//import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
//import static android.widget.RelativeLayout.CENTER_VERTICAL;
//
///**
// * Created by TQ on 10-Feb-18.
// */
//
//public class WordAdapter extends ArrayAdapter<Word> {
//
//    private int mColorResourceId;
//
//    MediaPlayer mp;
//    private MediaPlayer.OnCompletionListener completionListener;
//    private AudioManager audioManager;
//    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
//
//    Word wordPlaying;
//    ImageView currentPlayIcon;
//    ImageView currentPauseIcon;
//
//    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects, int colorResourceId) {
//        super(context, 0, objects);
//        mColorResourceId = colorResourceId;
//        mp = new MediaPlayer();
//        currentPlayIcon = new ImageView(getContext());
//        currentPauseIcon = new ImageView(getContext());
//        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
//        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
//            @Override
//            public void onAudioFocusChange(int focusChange) {
//                switch (focusChange) {
//                    case AudioManager.AUDIOFOCUS_GAIN:
//                        if (mp != null) {
//                            mp.start();
//                        }
//                    case AudioManager.AUDIOFOCUS_LOSS:
//                        mp.release();
//                        mp = null;
//                        wordPlaying = null;
//                        currentPlayIcon.setVisibility(View.VISIBLE);
//                        currentPauseIcon.setVisibility(View.GONE);
//                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
//                        mp.pause();
//                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                        mp.pause();
//                }
//            }
//        };
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        final Word currentWord = getItem(position);
//
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.list_item, parent, false);
//        }
//
//        RelativeLayout textLayout = listItemView.findViewById(R.id.text_play_relative_layout);
//        textLayout.setBackgroundResource(mColorResourceId);
//
//        TextView defaultTextView = listItemView.findViewById(R.id.default_textview);
//        defaultTextView.setText(currentWord.getDefaultTranslation());
//
//        TextView miwokTextView = listItemView.findViewById(R.id.miwok_textview);
//        miwokTextView.setText(currentWord.getMiwokTranslation());
//
//        ImageView wordImage = listItemView.findViewById(R.id.word_image);
//
//        if (currentWord.hasImage()) {
//            wordImage.setImageResource(currentWord.getImageResourceId());
//            wordImage.setVisibility(View.VISIBLE);
//        } else {
//            wordImage.setVisibility(View.GONE);
//        }
//
//        final ImageView playIcon = listItemView.findViewById(R.id.play_icon);
//        final ImageView pauseIcon = listItemView.findViewById(R.id.pause_icon);
//
//        if (currentWord == wordPlaying && mp.isPlaying()) {
//            playIcon.setVisibility(View.GONE);
//            pauseIcon.setVisibility(View.VISIBLE);
//            completionListener = null;
//            mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    playIcon.setVisibility(View.VISIBLE);
//                    pauseIcon.setVisibility(View.GONE);
//                    audioManager.abandonAudioFocus(audioFocusChangeListener);
//                }
//            });
//            currentPlayIcon = playIcon;
//            currentPauseIcon = pauseIcon;
//        } else {
//            playIcon.setVisibility(View.VISIBLE);
//            pauseIcon.setVisibility(View.GONE);
//        }
//
//        playIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentWord == wordPlaying) {
//                    mp.start();
//                    playIcon.setVisibility(View.GONE);
//                    pauseIcon.setVisibility(View.VISIBLE);
//                    completionListener = null;
//                    mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            playIcon.setVisibility(View.VISIBLE);
//                            pauseIcon.setVisibility(View.GONE);
//                            audioManager.abandonAudioFocus(audioFocusChangeListener);
//                        }
//                    });
//                    return;
//                }
//
//                mp.release();
//                mp = null;
//                completionListener = null;
//                currentPlayIcon.setVisibility(View.VISIBLE);
//                currentPauseIcon.setVisibility(View.GONE);
//
//                currentPlayIcon = playIcon;
//                currentPauseIcon = pauseIcon;
//
//                wordPlaying = currentWord;
//                mp = MediaPlayer.create(getContext(), wordPlaying.getAudioResourceId());
//                audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,
//                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
//
//                mp.start();
//                playIcon.setVisibility(View.GONE);
//                pauseIcon.setVisibility(View.VISIBLE);
//                mp.setOnCompletionListener(completionListener = new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        playIcon.setVisibility(View.VISIBLE);
//                        pauseIcon.setVisibility(View.GONE);
//                        audioManager.abandonAudioFocus(audioFocusChangeListener);
//                    }
//                });
//            }
//        });
//
//        pauseIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.pause();
//                playIcon.setVisibility(View.VISIBLE);
//                pauseIcon.setVisibility(View.GONE);
//            }
//        });
//
//        textLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (playIcon.getVisibility() == VISIBLE) {
//                    playIcon.performClick();
//                } else {
//                    pauseIcon.performClick();
//                }
//            }
//        });
//
//        return listItemView;
//    }
//}
