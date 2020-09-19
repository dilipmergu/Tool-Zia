package com.e.toolsharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  Intent i = new Intent(MainActivity.this,LoginActivity.class);
              //  startActivity(i);


            }
        },SPLASH_TIME_OUT);
    }
}