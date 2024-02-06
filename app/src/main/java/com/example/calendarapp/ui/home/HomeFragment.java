package com.example.calendarapp.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
<<<<<<< HEAD
=======

>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd

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

<<<<<<< HEAD
=======
        //Initializing Recycler View for Classes
>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(getActivity()));

        classList = new ArrayList<>();

        adapter = new ClassListAdapter(getActivity(), classList);
        recyclerViewClasses.setAdapter(adapter);

        classList.add(new ClassModel("CS2340", LocalTime.parse("8:00 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("9:15 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Prof. Pedro"));
        classList.add(new ClassModel("CS3001", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("2:50 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Prof. Ziegler"));
        classList.add(new ClassModel("APPH1040", LocalTime.parse("12:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("1:20 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Dr. Alexandra"));
        classList.add(new ClassModel("ARBC1002", LocalTime.parse("9:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("10:45 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Mr. Ahmed"));
        classList.add(new ClassModel("CS2110", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("3:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. MaryGold"));
        classList.add(new ClassModel("CS3001 - Section B14", LocalTime.parse("5:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("6:15 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.THURSDAY}, "TA Pranav"));
        classList.add(new ClassModel("CS2110 Lab", LocalTime.parse("6:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("7:45 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "TA Alex and TA Kyle"));
<<<<<<< HEAD
=======

>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
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

    // Method to show the edit dialog without prepopulating data
    private void showEmptyEditDialog(View dialogView) {
<<<<<<< HEAD
        // Find views within the dialog layout
=======
        //Views in edit_class_dialog
>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
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

<<<<<<< HEAD
=======
        //Related to custom day_of_week dropdown
>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
        View daysOfWeekListViewLayout = LayoutInflater.from(getContext()).inflate(R.layout.days_of_week_list, null);
        ListView daysOfWeekListView = daysOfWeekListViewLayout.findViewById(R.id.daysOfWeekListView);
        DaysOfWeekAdapter dayAdapter = new DaysOfWeekAdapter(getContext(), R.layout.day_of_week_item, getResources().getStringArray(R.array.days_of_week));
        daysOfWeekListView.setAdapter(dayAdapter);
        scrollView.addView(daysOfWeekListViewLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
<<<<<<< HEAD

=======
>>>>>>> 4b746f25d2701e4f15c8dc690fd429fdf384e2bd
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