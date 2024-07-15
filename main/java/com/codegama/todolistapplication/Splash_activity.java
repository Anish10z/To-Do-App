package com.codegama.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codegama.todolistapplication.activity.MainActivity;

public class Splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ihome=new Intent(Splash_activity.this, MainActivity.class);
                startActivity(ihome);
                finish();
            }
        },4000);

    }
}