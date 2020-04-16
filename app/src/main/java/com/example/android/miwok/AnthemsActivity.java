package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.android.miwok.WordAdapterButtons.PAUSE_ONLY;
import static com.example.android.miwok.WordAdapterButtons.STOP_AND_SEEK;

public class AnthemsActivity extends AppCompatActivity {

    WordAdapter adapter;
    ArrayList<Word> anthems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        anthems = new ArrayList<Word>();
        anthems.add(new Word("Saudi Arabia", "السعودية", R.drawable.saudi_arabia, R.raw.sa));
        anthems.add(new Word("UAE", "الإمارات", R.drawable.united_arab_emirates, R.raw.ae));
        anthems.add(new Word("Bahrain", "البحرين", R.drawable.bahrain, R.raw.number_three));
        anthems.add(new Word("Oman", "عمان", R.drawable.oman, R.raw.number_four));
        anthems.add(new Word("Egypt", "مصر", R.drawable.egypt, R.raw.eg));
        anthems.add(new Word("Kuwait", "الكويت", R.drawable.kuwait, R.raw.number_six));
        anthems.add(new Word("Morocco", "المغرب", R.drawable.morocco, R.raw.ma));
        anthems.add(new Word("Sudan", "السودان", R.drawable.sudan, R.raw.number_eight));
        anthems.add(new Word("Yemen", "اليمن", R.drawable.yemen, R.raw.number_nine));
        anthems.add(new Word("Jordan", "الأردن", R.drawable.jordan, R.raw.number_ten));
        anthems.add(new Word("Syria", "سوريا", R.drawable.syria, R.raw.number_one));
        anthems.add(new Word("Qatar", "فطر", R.drawable.qatar, R.raw.number_two));
        anthems.add(new Word("Iraq", "العراق", R.drawable.iraq, R.raw.number_three));
        anthems.add(new Word("Libya", "ليبيا", R.drawable.libya, R.raw.number_four));
        anthems.add(new Word("Lebanon", "لبنان", R.drawable.lebanon, R.raw.number_five));
        anthems.add(new Word("Tunisia", "تونس", R.drawable.tunisia, R.raw.tn));
        anthems.add(new Word("Palestine", "فلسطين", R.drawable.palestine, R.raw.number_seven));
        anthems.add(new Word("Algeria", "الجزائر", R.drawable.algeria, R.raw.number_eight));
        anthems.add(new Word("Mauritania", "موريتانيا", R.drawable.mauritania, R.raw.mr));
        anthems.add(new Word("Somalia", "الصومال", R.drawable.somalia, R.raw.number_ten));

        ListView listView = findViewById(R.id.list);
        adapter = new WordAdapter(this, anthems, R.color.category_anthems, STOP_AND_SEEK);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.mp.release();
        adapter.wordPlaying = null;
        adapter.mediaButtons.setPlayVisible(adapter.getContext());
    }
}
