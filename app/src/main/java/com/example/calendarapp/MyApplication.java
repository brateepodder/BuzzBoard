package com.example.calendarapp;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the ThreeTen Android Backport library
        AndroidThreeTen.init(this);
    }
}