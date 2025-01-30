package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/*
    Screen 1
        A drop-down menu of 17 different gestures will be shown on this screen. Once a single gesture is
    selected, the user will be taken to Screen 2.
    ● Gesture list: {Turn on lights, Turn off lights, Turn on fan, Turn off fan, Increase fan speed,
    decrease fan speed, Set Thermostat to specified temperature, gestures one for each digit
    0,1,2,3,4,5,6,7,8,9}
 */
public class MainActivity extends AppCompatActivity {
    protected class VideoName {
        String practiceVideoName;
        String saveVideoName;

        public VideoName(String practiceVideoName, String saveVideoName) {
            this.practiceVideoName = practiceVideoName;
            this.saveVideoName = saveVideoName;
        }
    }
    protected LinkedHashMap<String, VideoName> videoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setVideoName();
        Spinner spinner = findViewById((R.id.GestureDropdown));
        setSpinner(spinner);
        onSpinnerItemSelect(spinner);
    };

    private void setVideoName() {
        videoFile = new LinkedHashMap<>();

        videoFile.put("Select a gesture", new VideoName("", ""));
        videoFile.put("Turn On Light", new VideoName("light_on", "LightOn"));
        videoFile.put("Turn Off Light", new VideoName("light_off", "LightOff"));
        videoFile.put("Turn On Fan", new VideoName("fan_on", "FanOn"));
        videoFile.put("Turn Off Fan", new VideoName("fan_off", "FanOff"));
        videoFile.put("Increase Fan Speed", new VideoName("increase_fan_speed", "FanUp"));
        videoFile.put("Decrease Fan Speed", new VideoName("decrease_fan_speed", "FanDown"));
        videoFile.put("Set Thermostat to specified temperature", new VideoName("set_thermo", "SetThermo"));
        videoFile.put("0", new VideoName("h0", "Num0"));
        videoFile.put("1", new VideoName("h1", "Num1"));
        videoFile.put("2", new VideoName("h2", "Num2"));
        videoFile.put("3", new VideoName("h2", "Num3"));
        videoFile.put("4", new VideoName("h4", "Num4"));
        videoFile.put("5", new VideoName("h5", "Num5"));
        videoFile.put("6", new VideoName("h6", "Num6"));
        videoFile.put("7", new VideoName("h7", "Num7"));
        videoFile.put("8", new VideoName("h8", "Num8"));
        videoFile.put("9", new VideoName("h9", "Num9"));
    }

    // Dropdown menu
    private void setSpinner(Spinner spinner) {
        // Convert hashtable key to array to show in dropdown
        String[] gestures = videoFile.keySet().toArray(new String[0]);
        // Convert string to views
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, gestures);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    // on Item selected on dropdown
    public void onSpinnerItemSelect(Spinner spinner) {
        // Set an OnItemSelectedListener to listen for item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);

                // If the title is selected, do nothing
                if("Select a gesture".equals(selectedItem))
                    return;

                // Go to next View
                Intent intent = new Intent(MainActivity.this, PracticeActivity.class);
                intent.putExtra("practiceVideoName", Objects.requireNonNull(videoFile.get(selectedItem)).practiceVideoName);
                intent.putExtra("saveVideoName", Objects.requireNonNull(videoFile.get(selectedItem)).saveVideoName);

                startActivity(intent);

                Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected, if needed

            }
        });
    }
}