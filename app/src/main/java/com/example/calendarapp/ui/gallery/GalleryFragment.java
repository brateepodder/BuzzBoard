package com.example.calendarapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.R;
import com.example.calendarapp.databinding.FragmentGalleryBinding;
import com.example.calendarapp.ui.adapters.AssignmentsListAdapter;
import com.example.calendarapp.ui.models.AssignmentModel;

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
        assignmentList.add(new AssignmentModel("English Reading", LocalDateTime.of(2024, 2, 12, 14, 15), "English Class", "Read chapters 5-7 and write a summary"));
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
        EditText editAssignmentName = dialogView.findViewById(R.id.editAssignmentName);
        Spinner editAssignmentDueHour = dialogView.findViewById(R.id.editExamDay);
        Spinner editAssignmentDueMinute = dialogView.findViewById(R.id.editExamMinute);
        Spinner editAssignmentDueAm = dialogView.findViewById(R.id.editExamAm);
        Spinner editAssignmentDueDay = dialogView.findViewById(R.id.editAssignmentDueDateDay);
        Spinner editAssignmentDueMonth = dialogView.findViewById(R.id.editExamMonth);
        EditText editAssignmentAssociatedClass = dialogView.findViewById(R.id.editAssignmentAssociatedClass);
        EditText editAssignmentNote = dialogView.findViewById(R.id.editAssignmentNote);
        Button buttonSave = dialogView.findViewById(R.id.editExamButtonSave);
        Button buttonCancel = dialogView.findViewById(R.id.editAssignmentButtonCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dueHour = Integer.parseInt(editAssignmentDueHour.getSelectedItem().toString());
                int dueMinute = Integer.parseInt(editAssignmentDueMinute.getSelectedItem().toString());
                String dueAmPm = editAssignmentDueAm.getSelectedItem().toString();
                int dueDay = editAssignmentDueDay.getSelectedItemPosition() + 1;
                int dueMonth = editAssignmentDueMonth.getSelectedItemPosition() + 1;

                if (dueAmPm.equals("PM")) {
                    if (dueHour < 12) {
                        dueHour += 12;
                    }
                } else {
                    if (dueHour == 12) {
                        dueHour = 0;
                    }
                }

                LocalDateTime currentDateTime = LocalDateTime.now();
                int currentYear = currentDateTime.getYear();

                LocalDateTime dueDateTime = LocalDateTime.of(currentYear, dueMonth, dueDay, dueHour, dueMinute);

                String associatedClass = editAssignmentAssociatedClass.getText().toString();
                String assignmentName = editAssignmentName.getText().toString();
                String assignmentNote = editAssignmentNote.getText().toString();

                AssignmentModel assignment = new AssignmentModel(assignmentName, dueDateTime, associatedClass, assignmentNote);
                assignmentList.add(assignment);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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