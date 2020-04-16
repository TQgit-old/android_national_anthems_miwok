//package com.example.android.miwok;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout.LayoutParams;
//
//import java.security.AccessController;
//
//import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
//import static android.widget.RelativeLayout.CENTER_VERTICAL;
//import static java.security.AccessController.*;
//
///**
// * Created by TQ on 17-Feb-18.
// */
//
//public class WordAdapterCurrentlyPlaying {
//
//    public Word wordPlaying;
//    public final ImageView currentPlayIcon;
//    public final ImageView currentPauseIcon;
//
//    private final LayoutParams LAYOUT_PARAMS;
//
//    public WordAdapterCurrentlyPlaying(Context context) {
//        LAYOUT_PARAMS = makeLayoutParams(context);
//        currentPlayIcon = makePlayIcon(context);
//        currentPauseIcon = makePauseIcon(context);
//    }
//
//    private LayoutParams makeLayoutParams(Context context) {
//        LayoutParams layout = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout.addRule(ALIGN_PARENT_RIGHT);
//        layout.addRule(CENTER_VERTICAL);
//
//        Resources r = context.getResources();
//        int px = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                16,
//                r.getDisplayMetrics());
//        layout.setMargins(0, 0, px, 0);
//        return layout;
//    }
//
//    private ImageView makePlayIcon(Context context) {
//        ImageView playIcon = new ImageView(context);
//        playIcon.setId(R.id.play_icon);
//        playIcon.setLayoutParams(LAYOUT_PARAMS);
//        playIcon.setImageResource(android.R.drawable.ic_media_play);
//        playIcon.setContentDescription("Play");
//        playIcon.setVisibility(View.VISIBLE);
//        return playIcon;
//    }
//
//    private ImageView makePauseIcon(Context context) {
//        ImageView pauseIcon = new ImageView(context);
//        pauseIcon.setId(R.id.pause_icon);
//        pauseIcon.setLayoutParams(LAYOUT_PARAMS);
//        pauseIcon.setImageResource(android.R.drawable.ic_media_pause);
//        pauseIcon.setBackgroundResource(R.color.category_other);
//        pauseIcon.setContentDescription("Pause");
//        pauseIcon.setVisibility(View.GONE);
//        return pauseIcon;
//    }
//
//}
