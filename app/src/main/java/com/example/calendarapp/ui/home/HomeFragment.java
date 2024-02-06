package com.example.calendarapp.ui.home;

import android.app.AlertDialog;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ui.models.ClassModel;
import com.example.calendarapp.databinding.FragmentHomeBinding;
import com.example.calendarapp.ui.adapters.ClassListAdapter;
import com.example.calendarapp.R;
import com.example.calendarapp.ui.adapters.DaysOfWeekAdapter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewClasses;
    private ClassListAdapter adapter;
    private List<ClassModel> classList;
    private FragmentHomeBinding binding;

    public ClassListAdapter getClassListAdapter() {
        return this.adapter;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(getActivity()));

        classList = new ArrayList<>();

        adapter = new ClassListAdapter(getActivity(), classList);
        recyclerViewClasses.setAdapter(adapter);

        classList.add(new ClassModel("MATH 101", LocalTime.parse("10:00 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("11:15 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Prof. Johnson"));
        classList.add(new ClassModel("BIO 204", LocalTime.parse("1:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("2:45 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. Williams"));
        classList.add(new ClassModel("HIST 301", LocalTime.parse("3:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("4:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Prof. Davis"));
        classList.add(new ClassModel("CHEM 202", LocalTime.parse("9:00 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("10:15 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. Anderson"));
        classList.add(new ClassModel("PSYC 101", LocalTime.parse("11:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("12:45 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Prof. Smith"));
        classList.add(new ClassModel("PHYS 211 LAB", LocalTime.parse("2:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("5:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.FRIDAY}, "TA Jennifer"));
        classList.add(new ClassModel("ART 302 STUDIO", LocalTime.parse("3:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("6:00 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.THURSDAY}, "Prof. Thompson"));

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

    private DayOfWeek convertPositionToDayOfWeek(int position) {
        return DayOfWeek.of(position + 1);
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
        binding = null;
    }
}