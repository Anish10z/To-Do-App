package com.codegama.todolistapplication.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codegama.todolistapplication.R;

public class AlarmActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView description;
    private TextView timeAndData;
    private Button closeButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        timeAndData = findViewById(R.id.timeAndData);
        closeButton = findViewById(R.id.closeButton);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        mediaPlayer.start();

        if (getIntent().getExtras() != null) {
            String alarmTitle = getIntent().getStringExtra("TITLE");
            String alarmDesc = getIntent().getStringExtra("DESC");
            String alarmDate = getIntent().getStringExtra("DATE");
            String alarmTime = getIntent().getStringExtra("TIME");

            Log.d("AlarmActivity", "Alarm activity started with title: " + alarmTitle + ", desc: " + alarmDesc + ", date: " + alarmDate + ", time: " + alarmTime);

            title.setText(alarmTitle);
            description.setText(alarmDesc);
            timeAndData.setText(alarmDate + ", " + alarmTime);
        }

        Glide.with(getApplicationContext()).load(R.drawable.alert).into(imageView);
        closeButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
