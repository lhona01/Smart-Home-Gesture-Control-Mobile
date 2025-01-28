package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.inappmessaging.model.Button;

import java.io.File;

public class GestureActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gesture);

        videoview = findViewById(R.id.videoview);
        startCamera(null);
    }

    public void startCamera(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // get Uri of captured video
            Uri videoUri = data.getData();

            // play video on videoView
            videoview.setVideoURI(videoUri);

            videoview.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}