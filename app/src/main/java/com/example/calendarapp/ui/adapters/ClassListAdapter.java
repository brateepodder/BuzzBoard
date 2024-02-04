package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ClassModel;
import com.example.calendarapp.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;


public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {

    private Context mContext; // Context reference
    private List<ClassModel> mClassList; // List of class models

    // Constructor
    public ClassListAdapter(Context context, List<ClassModel> classList) {
        mContext = context; // Initialize Context reference
        mClassList = classList; // Initialize list of class models
    }

    // ViewHolder class
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameTextView;
        public TextView timeTextView;
        public TextView daysTextView;
        public TextView instructorsTextView;
        public View classBubbleView; // Add a reference to the background view
        public Button deleteButton;
        public Button editButton;


        public ClassViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            instructorsTextView = itemView.findViewById(R.id.instructorsTextView);
            classBubbleView = itemView.findViewById(R.id.bubble); // Initialize the reference
            deleteButton = itemView.findViewById(R.id.class_delete_button); // Initialize delete button
            editButton = itemView.findViewById(R.id.class_edit_button);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the edit dialog
                showEditDialog(mClassList.get(position), position);
            }
        });

        //Delete button onClickListener
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the corresponding class from the list
                int currentPosition = holder.getAdapterPosition();
                removeItem(currentPosition);
            }
        });

        // Get the data model based on position
        Collections.sort(mClassList);
        ClassModel classModel = mClassList.get(position);

        // Set color of view
        holder.courseNameTextView.setText(classModel.getCourseName());
        holder.timeTextView.setText(classModel.getStartEndTimeAsString());
        holder.daysTextView.setText(classModel.getDaysAsString());
        holder.instructorsTextView.setText(classModel.getInstructors());

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

    private void showEditDialog(ClassModel classModel, int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.edit_class_dialog, null);

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

        // Set current class information in the dialog fields
        editTextCourseName.setText(classModel.getCourseName());
        editTextInstructors.setText(classModel.getInstructors());

        // Set selected values for start time
        LocalTime startTime = classModel.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        String startTimeString = startTime.format(formatter);
        boolean isAM = startTimeString.endsWith("AM");
        // Convert 24-hour format to 12-hour format
        int startHour = startTime.getHour();
        if (startHour == 0) {
            startHour = 12; // Convert 0 (midnight) to 12 AM
        } else if (startHour > 12) {
            startHour -= 12; // Convert 13-23 to 1-11 PM
        }

        int spinnerSelection = (startHour == 12) ? 11 : (startHour - 1);
        spinnerStartHour.setSelection(spinnerSelection);
        spinnerStartMinute.setSelection(startTime.getMinute() / 5);
        spinnerStartAm.setSelection(isAM ? 0 : 1);

        // Set selected values for end time
        LocalTime endTime = classModel.getEndTime();
        String endTimeString = endTime.format(formatter);
        boolean isAM2 = endTimeString.endsWith("AM");
        // Convert 24-hour format to 12-hour format
        int endHour = startTime.getHour();
        if (endHour == 0) {
            endHour = 12; // Convert 0 (midnight) to 12 AM
        } else if (endHour > 12) {
            endHour -= 12; // Convert 13-23 to 1-11 PM
        }

        int spinnerSelection2 = (endHour == 12) ? 11 : (endHour - 1);
        spinnerStartHour.setSelection(spinnerSelection2);
        spinnerEndMinute.setSelection(endTime.getMinute() / 5);
        spinnerEndAm.setSelection(isAM2 ? 0 : 1);

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
                int startHour = Integer.parseInt(spinnerStartHour.getSelectedItem().toString());
                int startMinute = Integer.parseInt(spinnerStartMinute.getSelectedItem().toString());
                String startAmPm = spinnerStartAm.getSelectedItem().toString();

                // Get selected values from spinners for end time
                int endHour = Integer.parseInt(spinnerEndHour.getSelectedItem().toString());
                int endMinute = Integer.parseInt(spinnerEndMinute.getSelectedItem().toString());
                String endAmPm = spinnerEndAm.getSelectedItem().toString();

                // Convert start time to LocalTime object
                LocalTime startTime = LocalTime.of(startHour % 12 + (startAmPm.equals("PM") ? 12 : 0), startMinute);

                // Convert end time to LocalTime object
                LocalTime endTime = LocalTime.of(endHour % 12 + (endAmPm.equals("PM") ? 12 : 0), endMinute);

                // Remove the class from its current position in the list
                int index = mClassList.indexOf(classModel);
                if (index != -1) {
                    // Remove the class from its current position in the list
                    removeItem(index);

                    // Update classModel with new information
                    classModel.setCourseName(editTextCourseName.getText().toString());
                    classModel.setStartTime(startTime);
                    classModel.setEndTime(endTime);
                    classModel.setInstructors(editTextInstructors.getText().toString());

                    // Add the updated class back into the list
                    mClassList.add(classModel);

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

    // Helper method to parse time string into LocalTime object
    private LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mClassList.size();
    }
    public void removeItem(int position) {
        mClassList.remove(position);
        notifyItemRemoved(position);
    }
}