package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
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

    private Context mContext;
    private List<ClassModel> mClassList;

    public ClassListAdapter(Context context, List<ClassModel> classList) {
        mContext = context;
        mClassList = classList;
    }
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameTextView;
        public TextView timeTextView;
        public TextView daysTextView;
        public TextView instructorsTextView;
        public View classBubbleView;
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
    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the edit dialog
                showEditDialog(mClassList.get(position), position);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                removeItem(currentPosition);
            }
        });

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

        int colorIndex = position % colorResources.size();

        int selectedColorResource = colorResources.get(colorIndex);

        int selectedColor = ContextCompat.getColor(mContext, selectedColorResource);

        Drawable backgroundDrawable = ContextCompat.getDrawable(mContext, R.drawable.class_bubble);
        backgroundDrawable.setTint(selectedColor);
    }

    private void showEditDialog(ClassModel classModel, int position) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.edit_class_dialog, null);

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

        View daysOfWeekListViewLayout = LayoutInflater.from(mContext).inflate(R.layout.days_of_week_list, null);
        ListView daysOfWeekListView = daysOfWeekListViewLayout.findViewById(R.id.daysOfWeekListView);

        DaysOfWeekAdapter dayAdapter = new DaysOfWeekAdapter(mContext, R.layout.day_of_week_item, mContext.getResources().getStringArray(R.array.days_of_week));
        daysOfWeekListView.setAdapter(dayAdapter);

        scrollView.addView(daysOfWeekListViewLayout);

        editTextCourseName.setText(classModel.getCourseName());
        editTextInstructors.setText(classModel.getInstructors());

        dayAdapter.setSelectedDays(classModel.getDays());

        LocalTime startTime = classModel.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        String startTimeString = startTime.format(formatter);
        boolean isAM = startTimeString.endsWith("AM");
        int startHour = startTime.getHour();
        if (startHour == 0) {
            startHour = 12;
        } else if (startHour > 12) {
            startHour -= 12;
        }

        int spinnerSelection = (startHour == 12) ? 11 : (startHour - 1);
        spinnerStartHour.setSelection(spinnerSelection);
        spinnerStartMinute.setSelection(startTime.getMinute() / 5);
        spinnerStartAm.setSelection(isAM ? 0 : 1);
        LocalTime endTime = classModel.getEndTime();
        String endTimeString = endTime.format(formatter);
        boolean isAM2 = endTimeString.endsWith("AM");
        int endHour = startTime.getHour();
        if (endHour == 0) {
            endHour = 12;
        } else if (endHour > 12) {
            endHour -= 12;
        }

        int spinnerSelection2 = (endHour == 12) ? 11 : (endHour - 1);
        spinnerEndHour.setSelection(spinnerSelection2);
        spinnerEndMinute.setSelection(endTime.getMinute() / 5);
        spinnerEndAm.setSelection(isAM2 ? 0 : 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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

                LocalTime endTime = LocalTime.of(endHour % 12 + (endAmPm.equals("PM") ? 12 : 0), endMinute);

                classModel.setDays(dayAdapter.getSelectedDaysArray());

                int index = mClassList.indexOf(classModel);
                if (index != -1) {
                    removeItem(index);

                    classModel.setCourseName(editTextCourseName.getText().toString());
                    classModel.setStartTime(startTime);
                    classModel.setEndTime(endTime);
                    classModel.setInstructors(editTextInstructors.getText().toString());

                    mClassList.add(classModel);
                    notifyDataSetChanged();
                }
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
    private LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
    }
    @Override
    public int getItemCount() {
        return mClassList.size();
    }
    public void removeItem(int position) {
        mClassList.remove(position);
        notifyItemRemoved(position);
    }
}