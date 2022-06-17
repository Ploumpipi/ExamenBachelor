package com.example.examenbachelor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    final Handler h = new Handler();
    final Runnable r = () -> {
        if(!isFinishing()) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        h.postDelayed(r, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        h.removeCallbacks(r);
    }
}