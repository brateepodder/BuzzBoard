package com.example.calendarapp.ui.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.time.format.TextStyle;

public class ClassModel implements Comparable<ClassModel> {
    private String courseName;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek[] days;
    private String instructors;
    public ClassModel(String courseName, LocalTime startTime, LocalTime endTime, DayOfWeek[] days, String instructors) {
        this.courseName = courseName;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructors = instructors;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getStartTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a"); // "h:mm a" for 12-hour clock with AM/PM
        String startTimeString = startTime.format(formatter);

        return startTimeString;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() { return endTime; }

    public String getEndTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a"); // "h:mm a" for 12-hour clock with AM/PM
        String startTimeString = endTime.format(formatter);

        return startTimeString;
    }

    public String getStartEndTimeAsString() {
        String startTimeString = getStartTimeAsString();
        String endTimeString = getEndTimeAsString();
        String timeRange = startTimeString + " - " + endTimeString;

        return timeRange;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek[] getDays() {
        return days;
    }

    public String getDaysAsString() {
        StringBuilder daysStringBuilder = new StringBuilder();

        for (int i = 0; i < days.length; i++) {
            String dayName = days[i].getDisplayName(TextStyle.FULL, Locale.getDefault());
            daysStringBuilder.append(dayName);
            if (i < days.length - 1) {
                daysStringBuilder.append(", ");
            }
        }

        return daysStringBuilder.toString();
    }

    public List<Integer> getDaysAsIntegers() {
        List<Integer> dayIntegers = new ArrayList<>();
        for (DayOfWeek day : days) {
            switch (day) {
                case SUNDAY:
                    dayIntegers.add(0);
                    break;
                case MONDAY:
                    dayIntegers.add(1);
                    break;
                case TUESDAY:
                    dayIntegers.add(2);
                    break;
                case WEDNESDAY:
                    dayIntegers.add(3);
                    break;
                case THURSDAY:
                    dayIntegers.add(4);
                    break;
                case FRIDAY:
                    dayIntegers.add(5);
                    break;
                case SATURDAY:
                    dayIntegers.add(6);
                    break;
                default:
                    break;
            }
        }
        return dayIntegers;
    }

    public void setDays(DayOfWeek[] days) {
        this.days = days;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    @Override
    public int compareTo(ClassModel other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        String startTimeString = startTime != null ? startTime.toString() : " ";
        String endTimeString = endTime != null ? endTime.toString() : " ";
        String daysString = days != null ? Arrays.toString(days) : " ";
        String instructorsString = instructors != null ? instructors : " ";

        return String.format(Locale.getDefault(), "%s|%s|%s|%s|%s",
                courseName != null ? courseName : " ",
                startTimeString,
                endTimeString,
                daysString,
                instructorsString);
    }

    public static ClassModel fromStringClass(String data) {
        String[] parts = data.split("\\|");

        String courseName = " ";
        LocalTime startTime = null;
        LocalTime endTime = null;
        DayOfWeek[] days = null;
        String instructors = " ";

        if (parts.length >= 1) {
            courseName = parts[0];
        }
        if (parts.length >= 2) {
            startTime = LocalTime.parse(parts[1]);
        }
        if (parts.length >= 3) {
            endTime = LocalTime.parse(parts[2]);
        }
        if (parts.length >= 4) {
            days = parseDays(parts[3]);
        }
        if (parts.length >= 5) {
            instructors = parts[4];
        }

        return new ClassModel(courseName, startTime, endTime, days, instructors);
    }

    private static DayOfWeek[] parseDays(String daysString) {
        String[] dayStrings = daysString.substring(1, daysString.length() - 1).split(", ");
        DayOfWeek[] days = new DayOfWeek[dayStrings.length];
        for (int i = 0; i < dayStrings.length; i++) {
            days[i] = DayOfWeek.valueOf(dayStrings[i]);
        }
        return days;
    }
}