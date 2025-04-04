package com.example.employeescheduler;

import java.util.*;

public class Employee {
    private final String name;
    private final String[] preferences; // Shift preferences for each day
    private final Map<String, String> assignedShifts = new HashMap<>(); // Tracks shifts assigned
    private int workDays = 0; // Days worked this week

    // Constructor
    public Employee(String name, String[] preferences) {
        this.name = name;
        this.preferences = preferences;
    }

    // Get Employee's Name
    public String getName() {
        return name;
    }

    // Check if Employee Can Work a Specific Shift
    public boolean canWork(String day, String shift) {
        int dayIndex = getDayIndex(day);
        if (dayIndex >= 0 && dayIndex < preferences.length) {
            return preferences[dayIndex].equalsIgnoreCase(shift) && workDays < 5 && !assignedShifts.containsKey(day);
        }
        return false;
    }

    // Assign a Specific Shift to the Employee
    public void assignShift(String day, String shift) {
        assignedShifts.put(day, shift);
        workDays++;
    }

    // Check if Employee has Remaining Workdays
    public boolean hasRemainingWorkDays() {
        return workDays < 5;
    }

    // Utility: Get the Index of the Day
    private int getDayIndex(String day) {
        switch (day) {
            case "Monday": return 0;
            case "Tuesday": return 1;
            case "Wednesday": return 2;
            case "Thursday": return 3;
            case "Friday": return 4;
            case "Saturday": return 5;
            case "Sunday": return 6;
            default: return -1;
        }
    }
}