package com.ayon.fastnumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        ((Button) findViewById(R.id.button_single)).setText(String.valueOf(sharedPreferences.getInt(MainActivity.SINGLE_HIGH_SCORE,-1)));
        ((Button) findViewById(R.id.button_double)).setText(String.valueOf(sharedPreferences.getInt(MainActivity.DOUBLE_HIGH_SCORE,-1)));

    }
}
