package com.example.calendarapp.ui.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AssignmentModel implements Comparable<AssignmentModel> {
    private String name;
    private LocalDateTime dueDate;
    private String associatedClass;
    private String note;

    // Constructor
    public AssignmentModel(String name, LocalDateTime dueDate, String associatedClass, String note) {
        this.name = name;
        this.dueDate = dueDate;
        this.associatedClass = associatedClass;
        this.note = note;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getDueDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy, hh:mma");
        return dueDate.format(formatter);
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssociatedClass() {
        return associatedClass;
    }

    public String getAssociatedClassAsString() {
        if (associatedClass != null) {
            return associatedClass.toString(); // Assuming you have overridden toString() in ClassModel
        } else {
            return ""; // Return an empty string if associatedClass is null
        }
    }

    public void setAssociatedClass(String associatedClass) {
        this.associatedClass = associatedClass;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getHourIn12HourFormat() {
        int hour = dueDate.getHour();
        if (hour == 0) {
            return 12; // Convert midnight (0 hour) to 12 AM
        } else if (hour <= 12) {
            return hour; // For hours from 1 to 12, return as is
        } else {
            return hour % 12; // Convert hours from 13 to 23 to 1 to 11 PM
        }
    }

    public int getMinute() {
        return dueDate.getMinute();
    }

    public String getAmPm() {
        return dueDate.format(DateTimeFormatter.ofPattern("a"));
    }

    public int getDayAsInt() {
        return dueDate.getDayOfMonth();
    }

    public int getMonthAsInt() {
        return dueDate.getMonthValue();
    }

    public int compareTo(AssignmentModel other) {
        return this.dueDate.compareTo(other.dueDate);
    }

}
