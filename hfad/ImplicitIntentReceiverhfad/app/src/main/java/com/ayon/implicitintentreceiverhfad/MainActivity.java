package com.ayon.implicitintentreceiverhfad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getStringExtra(Intent.EXTRA_TEXT) != null) {
            ((TextView) findViewById(R.id.TextView)).setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
