package com.ayon.fastnumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ((RadioGroup) findViewById(R.id.radio_game_duration)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                int duration = 20;
                switch (i) {
                    case R.id.radioButton:
                        duration = 20;
                        break;
                    case R.id.radioButton2:
                        duration = 40;
                        break;
                    case R.id.radioButton3:
                        duration = 60;
                        break;
                    case R.id.radioButton4:
                        duration = 80;
                        break;
                }
                edit.putInt(MainActivity.DURATION,duration);
                edit.apply();
            }
        });

        ((RadioGroup) findViewById(R.id.radio_game_type)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                int gameType = Game.GREATER_THAN;
                switch (i) {
                    case R.id.radioButton5 :
                        gameType = Game.GREATER_THAN;
                        break;
                    case R.id.radioButton6 :
                        gameType = Game.LESS_THAN;
                        break;
                }
                edit.putInt(MainActivity.GAME_TYPE,gameType);
                edit.apply();
            }
        });
    }
}
