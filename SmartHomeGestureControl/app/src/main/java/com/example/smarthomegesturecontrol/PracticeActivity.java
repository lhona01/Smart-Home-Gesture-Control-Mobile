package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
    The video of an expert performing the gesture will be shown on this screen. Screen 2 will have
    another button that says "PRACTICE". Once this button is pressed, the user will be taken to Screen
    3.
 */
public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practice);

        Intent intent = getIntent();
        String videoName = intent.getStringExtra("videoName");

        ((TextView)findViewById(R.id.textView)).setText(intent.getStringExtra("gesture") + " tutorial");

        VideoView videoView = findViewById(R.id.ExpertVideo);
        Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/raw/" + videoName);
        videoView.setVideoURI(uri);

        videoView.start();
    }

    public void replayVideo(View view) {
        VideoView v = findViewById(R.id.ExpertVideo);
        v.start();
    }

    public void setGestureActivity(View view) {
        Intent intent = getIntent();
        Intent newIntent = new Intent(PracticeActivity.this, GestureActivity.class);

        intent.putExtra("gestureName", intent.getStringExtra("gesture"));
        startActivity(newIntent);
    }
}