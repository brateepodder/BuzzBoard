package com.example.calendarapp.ui.gallery;

import android.bluetooth.BluetoothAssignedNumbers;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.R;
import com.example.calendarapp.databinding.FragmentGalleryBinding;
import com.example.calendarapp.ui.adapters.AssignmentsListAdapter;
import com.example.calendarapp.ui.adapters.ClassListAdapter;
import com.example.calendarapp.ui.models.AssignmentModel;
import com.example.calendarapp.ui.models.ClassModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private RecyclerView recyclerViewAssignments;
    private List<AssignmentModel> assignmentList;
    private AssignmentsListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewAssignments = root.findViewById(R.id.recyclerViewAssignments);
        recyclerViewAssignments.setLayoutManager(new LinearLayoutManager(getActivity()));

        assignmentList = new ArrayList<>();

        adapter = new AssignmentsListAdapter(getActivity(), assignmentList);
        recyclerViewAssignments.setAdapter(adapter);

        assignmentList.add(new AssignmentModel("Math Homework", LocalDateTime.of(2024, 2, 10, 12, 0), "Math Class", "Complete exercises 1-5"));
        assignmentList.add(new AssignmentModel("History Essay", LocalDateTime.of(2024, 2, 15, 15, 30), "History Class", "Research and write essay on World War II"));
        assignmentList.add(new AssignmentModel("Science Project", LocalDateTime.of(2024, 2, 20, 10, 0), "Science Class", "Prepare presentation slides and materials"));
        adapter.notifyDataSetChanged();

        Button addButton = root.findViewById(R.id.assignmentAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.edit_assignment_dialog, null);
                showEditDialog(dialogView);
            }
        });

        return root;
    }

    private void showEditDialog(View dialogView) {
        // Find views within the edit_assignment_dialog layout
        EditText editAssignmentName = dialogView.findViewById(R.id.editAssignmentName);
        Spinner editAssignmentDueHour = dialogView.findViewById(R.id.editAssignmentHour);
        Spinner editAssignmentDueMinute = dialogView.findViewById(R.id.editAssignmentMinute);
        Spinner editAssignmentDueAm = dialogView.findViewById(R.id.editAssignmentAm);
        Spinner editAssignmentDueDay = dialogView.findViewById(R.id.editAssignmentDueDateDay);
        Spinner editAssignmentDueMonth = dialogView.findViewById(R.id.editAssignmentDueDateMonth);
        EditText editAssignmentAssociatedClass = dialogView.findViewById(R.id.editAssignmentAssociatedClass);
        EditText editAssignmentNote = dialogView.findViewById(R.id.editAssignmentNote);
        Button buttonSave = dialogView.findViewById(R.id.editAssignmentButtonSave);
        Button buttonCancel = dialogView.findViewById(R.id.editAssignmentButtonCancel);

        //Popup the edit_assignment_dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values from spinners for start time
                int dueHour = Integer.parseInt(editAssignmentDueHour.getSelectedItem().toString());
                int dueMinute = Integer.parseInt(editAssignmentDueMinute.getSelectedItem().toString());
                String dueAmPm = editAssignmentDueAm.getSelectedItem().toString();
                int dueDay = editAssignmentDueDay.getSelectedItemPosition() + 1;
                int dueMonth = editAssignmentDueMonth.getSelectedItemPosition() + 1;

                // Convert the AM/PM string to 24-hour format
                if (dueAmPm.equals("PM")) {
                    if (dueHour < 12) {
                        dueHour += 12;
                    }
                } else {
                    if (dueHour == 12) {
                        dueHour = 0;
                    }
                }

                //Get the current year and create a LocalDateTime instance
                LocalDateTime currentDateTime = LocalDateTime.now();
                int currentYear = currentDateTime.getYear();

                LocalDateTime dueDateTime = LocalDateTime.of(currentYear, dueMonth, dueDay, dueHour, dueMinute);

                //Get assignment name, note & class from editText views
                String associatedClass = editAssignmentAssociatedClass.getText().toString();
                String assignmentName = editAssignmentName.getText().toString();
                String assignmentNote = editAssignmentNote.getText().toString();

                //Create new assignment, add to assignmentList, notify of change
                AssignmentModel assignment = new AssignmentModel(assignmentName, dueDateTime, associatedClass, assignmentNote);
                assignmentList.add(assignment);
                adapter.notifyDataSetChanged();

                // Dismiss the dialog
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