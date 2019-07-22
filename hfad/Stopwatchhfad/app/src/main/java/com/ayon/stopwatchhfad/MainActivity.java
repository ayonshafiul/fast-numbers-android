package com.ayon.stopwatchhfad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Ayon";
    private int deciSeconds = 0;
    private boolean running = false;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( savedInstanceState != null ) {
            deciSeconds = savedInstanceState.getInt("deciSeconds");
            running = savedInstanceState.getBoolean("running");
        }
        final Button startBtn = findViewById(R.id.start_btn);
        final Button pauseBtn = findViewById(R.id.pause_btn);
        final Button resetBtn = findViewById(R.id.reset_btn);
        startBtn.setOnClickListener(ocl);
        pauseBtn.setOnClickListener(ocl);
        resetBtn.setOnClickListener(ocl);
        runStopwatch();
        Log.i(TAG, "onCreate()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");
    }



    // listener to handle all the button clicks start,pause, reset
    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start_btn:
                    running = true;
                    break;
                case R.id.pause_btn:
                    running = false;
                    break;
                case R.id.reset_btn:
                    running = false;
                    deciSeconds = 0;
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        running = wasRunning;
        Log.i(TAG,"OnStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
        Log.i(TAG,"OnStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("deciSeconds", deciSeconds);
        outState.putBoolean("running", running);
        Log.i(TAG, "onSaveInstanceState()");
    }

    private void runStopwatch() {
        final TextView sw = findViewById(R.id.timeDisplay);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int seconds = deciSeconds / 10;
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                int decS = deciSeconds % 10;
                String stopwatchString = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", hours, minutes, secs, decS);
                sw.setText(stopwatchString);
                if ( running ) {
                    deciSeconds++;
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}
