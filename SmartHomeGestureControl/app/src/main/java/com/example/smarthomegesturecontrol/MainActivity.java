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
import java.util.Map;

/*
    Screen 1
        A drop-down menu of 17 different gestures will be shown on this screen. Once a single gesture is
    selected, the user will be taken to Screen 2.
    ● Gesture list: {Turn on lights, Turn off lights, Turn on fan, Turn off fan, Increase fan speed,
    decrease fan speed, Set Thermostat to specified temperature, gestures one for each digit
    0,1,2,3,4,5,6,7,8,9}
 */
public class MainActivity extends AppCompatActivity {
    final String[] gestures = new String[] {
            "Select a gesture",
            "Turn on lights", "Turn off lights", "Turn on fan", "Turn off fan", "Increase fan speed",
            "Decrease fan speed", "Set Thermostat to specified temperature",
            "Gesture 1", "Gesture 2", "Gesture 3", "Gesture 4", "Gesture 5",
            "Gesture 6", "Gesture 7", "Gesture 8", "Gesture 9"
    };

    final String[] gestureVideo = new String[] {
            "light_on", "light_off", "fan_on", "fan_off", "increase_fan_speed",
            "decrease_fan_speed", "set_thermo",
            "h0", "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9"
    };

    protected Map<String, String> gestureDictionary = InitializeGestureVideo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById((R.id.GestureDropdown));
        setSpinner(spinner);
        onSpinnerItemSelect(spinner);
    };

    private void setSpinner(Spinner spinner) {
        // Convert string to views
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, gestures);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

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
                intent.putExtra("gesture", selectedItem);
                intent.putExtra("videoName", gestureDictionary.get(selectedItem));

                startActivity(intent);

                Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected, if needed

            }
        });
    }

    private Map<String, String> InitializeGestureVideo() {
        Map<String, String> dictionary = new HashMap<>();

        for (int i = 0; i < gestures.length; i++)
        {
            // don't add "Select a Gesture
            if ( i == 0 )
                continue;
            else
                dictionary.put(gestures[i], gestureVideo[i - 1]);
        }

        return dictionary;
    }
}