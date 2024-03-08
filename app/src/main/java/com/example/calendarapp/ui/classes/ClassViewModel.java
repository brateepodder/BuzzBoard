package com.example.calendarapp.ui.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calendarapp.ui.models.ClassModel;

import java.util.List;

public class ClassViewModel extends ViewModel {

    private MutableLiveData<List<ClassModel>> classListLiveData = new MutableLiveData<>();

    public void setClassList(List<ClassModel> classList) {
        classListLiveData.setValue(classList);
    }

    public LiveData<List<ClassModel>> getClassList() {
        return classListLiveData;
    }}