package com.example.calendarapp.ui.home;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ClassModel;
import com.example.calendarapp.databinding.FragmentHomeBinding;
import com.example.calendarapp.ui.adapters.ClassListAdapter;
import com.example.calendarapp.R;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewClasses;
    private ClassListAdapter adapter;
    private List<ClassModel> classList;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize recycler view
        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize class list
        classList = new ArrayList<>();

        // Initialize adapter
        adapter = new ClassListAdapter(getActivity(), classList);
        recyclerViewClasses.setAdapter(adapter);

        // Add sample data (you can replace this with your actual data)
        classList.add(new ClassModel("CS2340", LocalTime.parse("8:00 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("9:15 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Prof. Pedro"));
        classList.add(new ClassModel("CS3001", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("2:50 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Prof. Ziegler"));
        classList.add(new ClassModel("APPH1040", LocalTime.parse("12:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("1:20 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "Dr. Alexandra"));
        classList.add(new ClassModel("ARBC1002", LocalTime.parse("9:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("10:45 AM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Mr. Ahmed"));
        classList.add(new ClassModel("CS2110", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("3:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. MaryGold"));
        classList.add(new ClassModel("CS3001 - Section B14", LocalTime.parse("5:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("6:15 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.THURSDAY}, "TA Pranav"));
        classList.add(new ClassModel("CS2110 Lab", LocalTime.parse("6:30 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("7:45 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY}, "TA Alex and TA Kyle"));
        // Notify adapter about data change
        adapter.notifyDataSetChanged();

        Button addButton = root.findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.edit_class_dialog, null);

                // Call the method to show the edit dialog without prepopulating data
                showEmptyEditDialog(dialogView);
            }
        });

        return root;
    }

    // Method to show the edit dialog without prepopulating data
    private void showEmptyEditDialog(View dialogView) {
        // Find views within the dialog layout
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
        ListView dayListView = dialogView.findViewById(R.id.editClassDayDropdown);
        dayListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener for save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values from spinners for start time
                int startHour = Integer.parseInt(spinnerStartHour.getSelectedItem().toString());
                int startMinute = Integer.parseInt(spinnerStartMinute.getSelectedItem().toString());
                String startAmPm = spinnerStartAm.getSelectedItem().toString();

                // Get selected values from spinners for end time
                int endHour = Integer.parseInt(spinnerEndHour.getSelectedItem().toString());
                int endMinute = Integer.parseInt(spinnerEndMinute.getSelectedItem().toString());
                String endAmPm = spinnerEndAm.getSelectedItem().toString();

                // Convert start time to LocalTime object
                LocalTime startTime = LocalTime.of(startHour % 12 + (startAmPm.equals("PM") ? 12 : 0), startMinute);
                String courseName = editTextCourseName.getText().toString();

                // Convert end time to LocalTime object
                LocalTime endTime = LocalTime.of(endHour % 12 + (endAmPm.equals("PM") ? 12 : 0), endMinute);

                // Get selected days from the ListView
                SparseBooleanArray checkedItems = dayListView.getCheckedItemPositions();
                List<DayOfWeek> selectedDays = new ArrayList<>();
                for (int i = 0; i < checkedItems.size(); i++) {
                    if (checkedItems.valueAt(i)) {
                        int position = checkedItems.keyAt(i);
                        // Convert position to DayOfWeek enum value (Sunday is 0, Monday is 1, etc.)
                        DayOfWeek day = DayOfWeek.of(position + 1);
                        selectedDays.add(day);
                    }
                }

                // Update the days of the week for classModel
                DayOfWeek[] days = selectedDays.toArray(new DayOfWeek[selectedDays.size()]);

                ClassModel classModel = new ClassModel(editTextCourseName.getText().toString(), startTime, endTime, days, editTextInstructors.getText().toString());

                classList.add(classModel);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        // Set click listener for cancel button
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