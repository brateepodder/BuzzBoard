package com.example.calendarapp.ui.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExamModel implements Comparable<ExamModel> {
    private String name;
    private LocalDateTime time;
    private String location;
    private String note;

    // Constructor
    public ExamModel(String name, LocalDateTime time, String location, String note) {
        this.name = name;
        this.time = time;
        this.location = location;
        this.note = note;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTimeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy, hh:mma");
        return time.format(formatter);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getHourIn12HourFormat() {
        int hour = time.getHour();
        if (hour == 0) {
            return 12; // Convert midnight (0 hour) to 12 AM
        } else if (hour <= 12) {
            return hour; // For hours from 1 to 12, return as is
        } else {
            return hour % 12; // Convert hours from 13 to 23 to 1 to 11 PM
        }
    }

    public int getMinute() {
        return time.getMinute();
    }

    public String getAmPm() {
        return time.format(DateTimeFormatter.ofPattern("a"));
    }

    public int getDayAsInt() {
        return time.getDayOfMonth();
    }

    public int getMonthAsInt() {
        return time.getMonthValue();
    }

    public int compareTo(ExamModel other) {
        return this.time.compareTo(other.time);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.format("%s|%s|%s|%s",
                name != null ? name : " ",
                time != null ? time.format(formatter) : " ",
                location != null ? location : " ",
                note != null ? note : " ");
    }

    public static ExamModel fromStringExam(String str) {
        String[] parts = str.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid string format for ExamModel: " + str);
        }
        String name = " ";
        LocalDateTime time = null;
        String location = " ";
        String note = " ";

        if (parts.length >= 1) {
            name = parts[0];
        }
        if (parts.length >= 2) {
            time = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        if (parts.length >= 3) {
            location = parts[2];
        }
        if (parts.length >= 4) {
            note = parts[3];
        }
        return new ExamModel(name, time, location, note);
    }

}
