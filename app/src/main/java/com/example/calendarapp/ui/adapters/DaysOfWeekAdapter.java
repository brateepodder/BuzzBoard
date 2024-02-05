package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.calendarapp.R;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DaysOfWeekAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;
    private List<Boolean> mSelections;
    private List<DayOfWeek> mSelectedDays;

    public DaysOfWeekAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mSelections = new ArrayList<>(Arrays.asList(new Boolean[objects.length]));
        Collections.fill(mSelections, Boolean.FALSE);
        mSelectedDays = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView dayTextView = convertView.findViewById(R.id.dayTextView);
        String dayOfWeek = getItem(position);
        dayTextView.setText(dayOfWeek);

        // Set background color based on selection
        if (mSelections.get(position)) {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gatech_blue));
            dayTextView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            dayTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }

        // Handle item click to toggle selection
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = mSelections.get(position);
                mSelections.set(position, !isSelected);
                notifyDataSetChanged();

                // Update selected days list
                String selectedDay = getItem(position);
                DayOfWeek dayOfWeek = convertDayOfWeekFromString(selectedDay);
                if (isSelected) {
                    mSelectedDays.remove(dayOfWeek);
                } else {
                    mSelectedDays.add(dayOfWeek);
                }
            }
        });

        return convertView;
    }

    private DayOfWeek convertDayOfWeekFromString(String dayOfWeekString) {
        switch (dayOfWeekString) {
            case "Monday":
                return DayOfWeek.MONDAY;
            case "Tuesday":
                return DayOfWeek.TUESDAY;
            case "Wednesday":
                return DayOfWeek.WEDNESDAY;
            case "Thursday":
                return DayOfWeek.THURSDAY;
            case "Friday":
                return DayOfWeek.FRIDAY;
            case "Saturday":
                return DayOfWeek.SATURDAY;
            case "Sunday":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of week string: " + dayOfWeekString);
        }
    }

    public DayOfWeek[] getSelectedDaysArray() {
        return mSelectedDays.toArray(new DayOfWeek[mSelectedDays.size()]);
    }

    public void setSelectedDays(DayOfWeek[] selectedDays) {
        // Clear previous selections
        mSelectedDays.clear();

        // Iterate through selectedDays and find corresponding positions
        for (DayOfWeek day : selectedDays) {
            int position = getPositionForDay(day);
            if (position != -1) {
                mSelections.set(position, true);
                mSelectedDays.add(day);
            }
        }

        notifyDataSetChanged();
    }

    private int getPositionForDay(DayOfWeek day) {
        switch(day) {
            case MONDAY:
                return 0;
            case TUESDAY:
                return 1;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 3;
            case FRIDAY:
                return 4;
            case SATURDAY:
                return 5;
            case SUNDAY:
                return 6;
            default:
                throw new IllegalArgumentException("Index out of bounds, 0-6 only for days of week.");
        }
    }

    private String convertDayOfWeekToString(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                throw new IllegalArgumentException("Invalid day of week: " + day);
        }
    }

}
