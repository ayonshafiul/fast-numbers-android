package com.doyababa.demoapp3;



import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends MyMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiobtn_main);
        RadioGroup rg = ((RadioGroup) findViewById(R.id.rgroup));
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup compoundButton, int checkedId) {
                Toast.makeText(MainActivity.this,String.valueOf(checkedId == R.id.radio1), Toast.LENGTH_SHORT).show();
                Log.i("AYON", String.valueOf(checkedId == R.id.radio1));
            }
        });
    }
}
