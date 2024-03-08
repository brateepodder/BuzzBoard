package com.example.calendarapp.ui.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.databinding.FragmentClassesBinding;
import com.example.calendarapp.ui.models.ClassModel;
import com.example.calendarapp.ui.adapters.ClassListAdapter;
import com.example.calendarapp.R;
import com.example.calendarapp.ui.adapters.DaysOfWeekAdapter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassFragment extends Fragment {
    private ClassViewModel classViewModel;
    private RecyclerView recyclerViewClasses;
    private ClassListAdapter adapter;
    private List<ClassModel> classList;
    private FragmentClassesBinding binding;
    private SharedPreferences sharedPreferences;


    public ClassListAdapter getClassListAdapter() {
        return this.adapter;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(getActivity()));

        classList = new ArrayList<>();

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        loadClassesFromSharedPreferences();


        adapter = new ClassListAdapter(getActivity(), classList);
        recyclerViewClasses.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        Button addButton = root.findViewById(R.id.classAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.edit_class_dialog, null);
                showEmptyEditDialog(dialogView);
            }
        });

        return root;
    }

    public List<ClassModel> getClassList() {
        return this.classList;
    }

    private void loadClassesFromSharedPreferences() {
        Set<String> classSet = sharedPreferences.getStringSet("classSet", new HashSet<>());
        for (String classString : classSet) {
            ClassModel classModel = ClassModel.fromStringClass(classString);
            if (classModel != null) {
                classList.add(classModel);
            }
        }
    }

    private void saveClassesToSharedPreferences() {
        Set<String> classSet = new HashSet<>();
        for (ClassModel classModel : classList) {
            classSet.add(classModel.toString());
        }
        sharedPreferences.edit().putStringSet("classSet", classSet).apply();
    }

    private void showEmptyEditDialog(View dialogView) {
        EditText editTextCourseName = dialogView.findViewById(R.id.editClassCourseName);
        Spinner spinnerStartHour = dialogView.findViewById(R.id.editClassStartHour);
        Spinner spinnerStartMinute = dialogView.findViewById(R.id.editClassStartMinute);
        Spinner spinnerStartAm = dialogView.findViewById(R.id.editClassStartAm);
        Spinner spinnerEndHour = dialogView.findViewById(R.id.editClassEndHour);
        Spinner spinnerEndMinute = dialogView.findViewById(R.id.editClassEndMinute);
        Spinner spinnerEndAm = dialogView.findViewById(R.id.editClassEndAm);
        EditText editTextInstructors = dialogView.findViewById(R.id.editClassInstructor);
        Button buttonSave = dialogView.findViewById(R.id.editClassButtonSave);
        Button buttonCancel = dialogView.findViewById(R.id.editClassButtonCancel);
        ScrollView scrollView = dialogView.findViewById(R.id.editClassDayDropdown);

        View daysOfWeekListViewLayout = LayoutInflater.from(getContext()).inflate(R.layout.days_of_week_list, null);
        ListView daysOfWeekListView = daysOfWeekListViewLayout.findViewById(R.id.daysOfWeekListView);
        DaysOfWeekAdapter dayAdapter = new DaysOfWeekAdapter(getContext(), R.layout.day_of_week_item, getResources().getStringArray(R.array.days_of_week));
        daysOfWeekListView.setAdapter(dayAdapter);
        scrollView.addView(daysOfWeekListViewLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startHour = Integer.parseInt(spinnerStartHour.getSelectedItem().toString());
                int startMinute = Integer.parseInt(spinnerStartMinute.getSelectedItem().toString());
                String startAmPm = spinnerStartAm.getSelectedItem().toString();

                int endHour = Integer.parseInt(spinnerEndHour.getSelectedItem().toString());
                int endMinute = Integer.parseInt(spinnerEndMinute.getSelectedItem().toString());
                String endAmPm = spinnerEndAm.getSelectedItem().toString();

                LocalTime startTime = LocalTime.of(startHour % 12 + (startAmPm.equals("PM") ? 12 : 0), startMinute);
                String courseName = editTextCourseName.getText().toString();
                LocalTime endTime = LocalTime.of(endHour % 12 + (endAmPm.equals("PM") ? 12 : 0), endMinute);
                DayOfWeek[] days = dayAdapter.getSelectedDaysArray();

                ClassModel classModel = new ClassModel(editTextCourseName.getText().toString(), startTime, endTime, days, editTextInstructors.getText().toString());

                classList.add(classModel);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel button click
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveClassesToSharedPreferences();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        classViewModel = new ViewModelProvider(requireActivity()).get(ClassViewModel.class);
        List<ClassModel> classList = getClassList();
        classViewModel.setClassList(classList);
    }
}