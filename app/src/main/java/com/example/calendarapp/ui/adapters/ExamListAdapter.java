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

import com.example.calendarapp.R;
import com.example.calendarapp.ui.models.AssignmentModel;
import com.example.calendarapp.ui.models.ExamModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamViewHolder> {
    private Context mContext;
    private List<ExamModel> mExamList;

    public ExamListAdapter(Context context, List<ExamModel> examList) {
        mContext = context;
        mExamList = examList;
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        public TextView examNameTextView;
        public TextView examDateTextView;
        public TextView examLocationTimeView;
        public TextView examNoteTimeView;
        public Button examEditButton;
        public Button examDeleteButton;

        public ExamViewHolder(View itemView) {
            super(itemView);
            examNameTextView = itemView.findViewById(R.id.examNameTextView);
            examDateTextView = itemView.findViewById(R.id.examDateTextView);
            examLocationTimeView = itemView.findViewById(R.id.examLocationTextView);
            examNoteTimeView = itemView.findViewById(R.id.examNoteTextView);
            examEditButton = itemView.findViewById(R.id.examEditButton);
            examDeleteButton = itemView.findViewById(R.id.examDeleteButton);
        }
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exams, null);
        return new ExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamListAdapter.ExamViewHolder holder, int position) {
        holder.examEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(mExamList.get(position), position);
            }
        });

        holder.examDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                removeItem(currentPosition);
            }
        });

        Collections.sort(mExamList);
        ExamModel exam = mExamList.get(position);

        holder.examNameTextView.setText(exam.getName());
        holder.examDateTextView.setText(exam.getTimeToString());
        holder.examLocationTimeView.setText(exam.getLocation());
        holder.examNoteTimeView.setText(exam.getNote());

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

    @Override
    public int getItemCount() {
        return mExamList.size();
    }

    private void showEditDialog(ExamModel exam, int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.edit_exam_dialog, null);

        // Find views within the dialog layout
        EditText editExamName = dialogView.findViewById(R.id.editExamName);
        Spinner editExamHour = dialogView.findViewById(R.id.editExamHour);
        Spinner editExamMinute = dialogView.findViewById(R.id.editExamMinute);
        Spinner editExamAm = dialogView.findViewById(R.id.editExamAm);
        Spinner editExamDay = dialogView.findViewById(R.id.editExamDay);
        Spinner editExamMonth = dialogView.findViewById(R.id.editExamMonth);
        EditText editExamLocation = dialogView.findViewById(R.id.editExamLocation);
        EditText editExamNote = dialogView.findViewById(R.id.editExamNote);
        Button buttonSave = dialogView.findViewById(R.id.editExamButtonSave);
        Button buttonCancel = dialogView.findViewById(R.id.editExamButtonCancel);

        //Set assignment editText name, note & associated class
        editExamName.setText(exam.getName());
        editExamNote.setText(exam.getNote());
        editExamLocation.setText(exam.getLocation());

        //Set spinner's due hour, minute, am & day, month
        editExamHour.setSelection(exam.getHourIn12HourFormat() - 1);
        editExamMinute.setSelection(exam.getMinute() / 5);
        editExamAm.setSelection(exam.getAmPm().equals("AM") ? 0 : 1);
        editExamDay.setSelection(exam.getDayAsInt() - 1);
        editExamMonth.setSelection(exam.getMonthAsInt() - 1);

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
                int dueHour = Integer.parseInt(editExamHour.getSelectedItem().toString());
                int dueMinute = Integer.parseInt(editExamMinute.getSelectedItem().toString());
                String dueAmPm = editExamAm.getSelectedItem().toString();
                int dueDay = editExamDay.getSelectedItemPosition() + 1;
                int dueMonth = editExamMonth.getSelectedItemPosition() + 1;

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

                int index = mExamList.indexOf(exam);
                if (index != -1) {
                    // Remove the class from its current position in the list
                    removeItem(index);

                    // Update assignment with new information
                    exam.setTime(dueDateTime);
                    exam.setLocation(editExamLocation.getText().toString());
                    exam.setName(editExamName.getText().toString());
                    exam.setNote(editExamNote.getText().toString());

                    // Add the updated class back into the list
                    mExamList.add(exam);

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

    public void removeItem(int position) {
        mExamList.remove(position);
        notifyItemRemoved(position);
    }
}
