package com.example.smarthomegesturecontrol;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
    In this screen, the camera interface will be opened for the user to record the practice gesture. The
    video will be captured for five (5) seconds, and the video will be saved with this filename format:
    ● [GESTURE NAME]_PRACTICE_[practice number]_[USER LASTNAME].mp4
 */
public class GestureActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;
    VideoView videoview;
    OkHttpClient client;
    String url;
    Uri videoUri;
    String videoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gesture);

        Intent intent = getIntent();
        videoName = intent.getStringExtra("saveVideoName") + ".mp4";

        client = new OkHttpClient();
        url = "http://192.168.0.186:5000/upload";

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
            videoUri = data.getData();
            // play video on videoView
            videoview.setVideoURI(videoUri);
            videoview.start();
        }
    }

    // Replay Video
    public void reviewVideo(View v) {
        videoview.start();
    }

    // -----------------------------------
    // Store recording to server .mp4 format
    public void uploadRecording(View v) {
        if (videoUri == null) {
            Toast.makeText(this, "Failed to get video file path", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoPath = getRealPathFromURI(this, videoUri);
        if (videoPath == null) {
            Toast.makeText(this, "Failed to get video file path", Toast.LENGTH_SHORT).show();
            return;
        }

        File videoFile = new File(videoPath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", videoName,
                        RequestBody.create(MediaType.parse("video/mp4"),
                                videoFile)).build();

        Request request = new Request.Builder().url(url)
                .post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(GestureActivity.this,
                        "Upload failed:" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> Toast.makeText(GestureActivity.this,
                        "Upload successful", Toast.LENGTH_SHORT).show());
            }
        });
    }

    // get the actual file path of a video from its URI in an Android application.
    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                return cursor.getString(columnIndex);
            }
        }
        return null;
    }
}