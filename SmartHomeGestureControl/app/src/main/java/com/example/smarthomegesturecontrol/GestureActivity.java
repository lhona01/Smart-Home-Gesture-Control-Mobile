package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
    In this screen, the camera interface will be opened for the user to record the practice gesture. The
    video will be captured for five (5) seconds, and the video will be saved with this filename format:
    ● [GESTURE NAME]_PRACTICE_[practice number]_[USER LASTNAME].mp4
 */
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

    // ----------------------
    // Record video
    public void startCamera(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Record for 5 sec
        cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // get Uri of captured video
            assert data != null;
            Uri videoUri = data.getData();
            // play video on videoView
            videoview.setVideoURI(videoUri);
            videoview.start();
        }
    }

    // -----------------------------------
    // Store recording to server .mp4 format
}