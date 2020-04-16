/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.android.miwok.WordAdapterButtons.PAUSE_ONLY;

public class NumbersActivity extends AppCompatActivity {

    WordAdapter adapter;
    ArrayList<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        words = new ArrayList<Word>();
        words.add(new Word(getString(R.string.default_one), "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word(getString(R.string.default_two), "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word(getString(R.string.default_three), "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word(getString(R.string.default_four), "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word(getString(R.string.default_five), "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word(getString(R.string.default_six), "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word(getString(R.string.default_seven), "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(getString(R.string.default_eight), "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(getString(R.string.default_nine), "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(getString(R.string.default_ten), "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        ListView listView = findViewById(R.id.list);
        adapter = new WordAdapter(this, words, R.color.category_numbers, PAUSE_ONLY);
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
