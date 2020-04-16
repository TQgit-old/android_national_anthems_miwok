//package com.example.android.miwok;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.media.MediaPlayer;
//import android.media.PlaybackParams;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.util.Xml;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.xmlpull.v1.XmlPullParser;
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
//    private static MediaPlayer mp;
//
//    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects, int colorResourceId) {
//        super(context, 0, objects);
//        mColorResourceId = colorResourceId;
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
//            currentWord.playIcon = listItemView.findViewById(R.id.play_icon);
//            currentWord.pauseIcon = listItemView.findViewById(R.id.pause_icon);
//        } else {
//            if (currentWord.playIcon == null) {
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.addRule(ALIGN_PARENT_RIGHT);
//                layoutParams.addRule(CENTER_VERTICAL);
//
//                Resources r = getContext().getResources();
//                int px = (int) TypedValue.applyDimension(
//                        TypedValue.COMPLEX_UNIT_DIP,
//                        16,
//                        r.getDisplayMetrics());
//                layoutParams.setMargins(0, 0, px, 0);
//
//                currentWord.playIcon = new ImageView(getContext());
//                currentWord.playIcon.setId(R.id.play_icon);
//                currentWord.playIcon.setLayoutParams(layoutParams);
//                currentWord.playIcon.setImageResource(android.R.drawable.ic_media_play);
//                currentWord.playIcon.setContentDescription("Play");
//                currentWord.playIcon.setVisibility(View.VISIBLE);
//
//                currentWord.pauseIcon = new ImageView(getContext());
//                currentWord.pauseIcon.setId(R.id.pause_icon);
//                currentWord.pauseIcon.setLayoutParams(layoutParams);
//                currentWord.pauseIcon.setImageResource(android.R.drawable.ic_media_pause);
//                currentWord.pauseIcon.setBackgroundResource(R.color.category_other);
//                currentWord.pauseIcon.setContentDescription("Pause");
//                currentWord.pauseIcon.setVisibility(View.GONE);
//            }
//            RelativeLayout container = listItemView.findViewById(R.id.text_play_relative_layout);
//            container.removeView(listItemView.findViewById(R.id.play_icon));
//            container.removeView(listItemView.findViewById(R.id.pause_icon));
//            container.addView(currentWord.playIcon);
//            container.addView(currentWord.pauseIcon);
//        }
//
//        LinearLayout textLayout = (LinearLayout) listItemView.findViewById(R.id.text_linear_layout);
//        textLayout.setBackgroundResource(mColorResourceId);
//
//        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_textview);
//        defaultTextView.setText(currentWord.getDefaultTranslation());
//
//        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_textview);
//        miwokTextView.setText(currentWord.getMiwokTranslation());
//
//        ImageView wordImage = (ImageView) listItemView.findViewById(R.id.word_image);
//
//        if (currentWord.hasImage()) {
//            wordImage.setImageResource(currentWord.getImageResourceId());
//            wordImage.setVisibility(View.VISIBLE);
//        } else {
//            wordImage.setVisibility(View.GONE);
//        }
//
//        if (currentWord.mp == null) {
//            currentWord.mp = MediaPlayer.create(getContext(), currentWord.getAudioResourceId());
//            mp = currentWord.mp;
//        }
//
//        currentWord.playIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mp == currentWord.mp) {
//                    mp.start();
//                    currentWord.playIcon.setVisibility(View.GONE);
//                    currentWord.pauseIcon.setVisibility(View.VISIBLE);
//                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            currentWord.playIcon.setVisibility(View.VISIBLE);
//                            currentWord.pauseIcon.setVisibility(View.GONE);
//                        }
//                    });
//                    return;
//                }
//
//                int end = mp.getDuration();
//                mp.seekTo(end);
//                mp.start();
//
//                mp = currentWord.mp;
//                mp.start();
//                currentWord.playIcon.setVisibility(View.GONE);
//                currentWord.pauseIcon.setVisibility(View.VISIBLE);
//                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        currentWord.playIcon.setVisibility(View.VISIBLE);
//                        currentWord.pauseIcon.setVisibility(View.GONE);
//                    }
//                });
//            }
//        });
//
//        currentWord.pauseIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mp != null) {
//                    mp.pause();
//                }
//                currentWord.playIcon.setVisibility(View.VISIBLE);
//                currentWord.pauseIcon.setVisibility(View.GONE);
//            }
//        });
//
//        textLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentWord.playIcon.getVisibility() == VISIBLE) {
//                    currentWord.playIcon.performClick();
//                } else {
//                    currentWord.pauseIcon.performClick();
//                }
//            }
//        });
//
//        return listItemView;
//    }
//}
