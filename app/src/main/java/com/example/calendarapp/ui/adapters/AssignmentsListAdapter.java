package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ui.models.AssignmentModel;
import com.example.calendarapp.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AssignmentsListAdapter extends RecyclerView.Adapter<AssignmentsListAdapter.AssignmentsViewHolder> {
    private Context mContext; // Context reference
    private List<AssignmentModel> mAssignmentList; // List of class models

    public AssignmentsListAdapter(Context context, List<AssignmentModel> assignmentList) {
        mContext = context; // Initialize Context reference
        mAssignmentList = assignmentList; // Initialize list of class models
    }

    public List<AssignmentModel> getAssignmentList() {
        return this.mAssignmentList;
    }

    public void setAssignmentList(List<AssignmentModel> AssignmentList) {
        mAssignmentList = AssignmentList;
    }

    public static class AssignmentsViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentNameTextView;
        public TextView assignmentsAssociatedCourse;
        public TextView assignmentDueDateTextView;
        public TextView assignmentNote;
        public Button assignmentsEditButton;
        public Button assignmentsDeleteButton;

        public AssignmentsViewHolder(View itemView) {
            super(itemView);
            assignmentNameTextView = itemView.findViewById(R.id.assignmentNameTextView);
            assignmentsAssociatedCourse = itemView.findViewById(R.id.assignmentsAssociatedCourse);
            assignmentDueDateTextView = itemView.findViewById(R.id.assignmentDueDateTextView);
            assignmentsEditButton = itemView.findViewById(R.id.assignmentsEditButton);
            assignmentsDeleteButton = itemView.findViewById(R.id.assignmentsDeleteButton);
            assignmentNote = itemView.findViewById(R.id.assignmentsNotes);
        }
    }
    @NonNull
    @Override
    public AssignmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignments, parent, false);
        return new AssignmentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentsListAdapter.AssignmentsViewHolder holder, int position) {
        holder.assignmentsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(mAssignmentList.get(position), position);
            }
        });

        holder.assignmentsDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                removeItem(currentPosition);
            }
        });

        Collections.sort(mAssignmentList);
        AssignmentModel assignment = mAssignmentList.get(position);

        holder.assignmentNameTextView.setText(assignment.getName());
        holder.assignmentDueDateTextView.setText(assignment.getDueDateToString());
        holder.assignmentsAssociatedCourse.setText(assignment.getAssociatedClassAsString());
        holder.assignmentNote.setText(assignment.getNote());

        ArrayList<Integer> colorResources = new ArrayList<>(Arrays.asList(
                R.color.bubble_1,
                R.color.bubble_2,
                R.color.bubble_3,
                R.color.bubble_4,
                R.color.bubble_5,
                R.color.bubble_6,
                R.color.bubble_7,
                R.color.bubble_8,
                R.color.bubble_9,
                R.color.bubble_10,
                R.color.yellow
        ));

        // Calculate the color index based on the position
        int colorIndex = position % colorResources.size();

        // Get the color resource corresponding to the calculated index
        int selectedColorResource = colorResources.get(colorIndex);

        // Get the actual color integer value from the color resource
        int selectedColor = ContextCompat.getColor(mContext, selectedColorResource);

        Drawable backgroundDrawable = ContextCompat.getDrawable(mContext, R.drawable.class_bubble);
        backgroundDrawable.setTint(selectedColor);
    }

    private void showEditDialog(AssignmentModel assignment, int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.edit_assignment_dialog, null);

        // Find views within the dialog layout
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

        //Set assignment editText name, note & associated class
        editAssignmentName.setText(assignment.getName());
        editAssignmentNote.setText(assignment.getNote());
        editAssignmentAssociatedClass.setText(assignment.getAssociatedClass());

        //Set spinner's due hour, minute, am & day, month
        editAssignmentDueHour.setSelection(assignment.getHourIn12HourFormat() - 1);
        editAssignmentDueMinute.setSelection(assignment.getMinute() / 5);
        editAssignmentDueAm.setSelection(assignment.getAmPm().equals("AM") ? 0 : 1);
        editAssignmentDueDay.setSelection(assignment.getDayAsInt() - 1);
        editAssignmentDueMonth.setSelection(assignment.getMonthAsInt() - 1);

        // Show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener for save button
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

                    // Get the current year and create a LocalDateTime instance
                LocalDateTime currentDateTime = LocalDateTime.now();
                int currentYear = currentDateTime.getYear();

                LocalDateTime dueDateTime = LocalDateTime.of(currentYear, dueMonth, dueDay, dueHour, dueMinute);

                int index = mAssignmentList.indexOf(assignment);
                if (index != -1) {
                    // Remove the class from its current position in the list
                    removeItem(index);

                    // Update assignment with new information
                    assignment.setDueDate(dueDateTime);
                    assignment.setAssociatedClass(editAssignmentAssociatedClass.getText().toString());
                    assignment.setName(editAssignmentName.getText().toString());
                    assignment.setNote(editAssignmentNote.getText().toString());

                    // Add the updated class back into the list
                    mAssignmentList.add(assignment);

                    // Notify the adapter of the data change
                    notifyDataSetChanged();
                }

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Set click listener for cancel button
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
    }



    @Override
    public int getItemCount() {
        return mAssignmentList.size();
    }

    public void removeItem(int position) {
        mAssignmentList.remove(position);
        notifyItemRemoved(position);
    }
}
