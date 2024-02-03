package com.example.calendarapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendarapp.R;

public class UserInputClasses extends AppCompatActivity {

    private ListView dayDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input_classes); // Replace with the name of your XML layout file

        dayDropdown = findViewById(R.id.day_dropdown);

        // Example of populating the ListView with dummy data
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, days);
        dayDropdown.setAdapter(adapter);
    }
}