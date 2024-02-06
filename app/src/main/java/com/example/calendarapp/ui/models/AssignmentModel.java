package com.example.calendarapp.ui.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.format("%s|%s|%s|%s", name, dueDate.format(formatter), associatedClass, note);
    }

    public static AssignmentModel fromStringAssignment(String str) {
        String[] parts = str.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("fromStringAssignment: Invalid string format for AssignmentModel: " + str);
        }
        String name = parts[0];
        LocalDateTime dueDate = LocalDateTime.parse(parts[1]);
        String associatedClass = parts[2];
        String note = parts[3];
        return new AssignmentModel(name, dueDate, associatedClass, note);
    }

}
