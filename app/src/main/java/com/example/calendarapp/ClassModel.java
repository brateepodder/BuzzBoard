package com.example.calendarapp;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.format.TextStyle;

public class ClassModel implements Comparable<ClassModel>{
    private String courseName;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek[] days;
    private String instructors;

    // Constructor
    public ClassModel(String courseName, LocalTime startTime, LocalTime endTime, DayOfWeek[] days, String instructors) {
        this.courseName = courseName;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructors = instructors;
    }

    // Getters and setters
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
            // Map DayOfWeek enum values to integers (e.g., Sunday = 0, Monday = 1, etc.)
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
                    // Handle unexpected cases
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

}