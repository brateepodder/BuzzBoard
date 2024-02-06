package com.example.calendarapp.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.calendarapp.ui.models.ClassModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ClassListSharedPreferences {

    private static final String PREF_NAME = "ClassListPrefs";
    private static final String KEY_CLASS_LIST = "classList";

    private final SharedPreferences sharedPreferences;

    public ClassListSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save the classList to SharedPreferences
    public void saveClassList(List<ClassModel> classList) {
        Gson gson = new Gson();
        String json = gson.toJson(classList);
        sharedPreferences.edit().putString(KEY_CLASS_LIST, json).apply();
    }

    // Load the classList from SharedPreferences
    public List<ClassModel> loadClassList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_CLASS_LIST, "");
        Type type = new TypeToken<List<ClassModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
}