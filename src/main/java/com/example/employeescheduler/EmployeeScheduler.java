package com.example.employeescheduler;

import java.util.*;

public class EmployeeScheduler {
    // Constants: Days and Shifts
    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] SHIFTS = {"Morning", "Afternoon", "Evening"};

    // Data Structures
    private final Map<String, Employee> employees = new HashMap<>(); // Store employees by name
    private final Map<String, Map<String, List<String>>> schedule = new LinkedHashMap<>(); // Weekly schedule

    public static void main(String[] args) {
        EmployeeScheduler scheduler = new EmployeeScheduler();
        scheduler.collectEmployees(); // Add employees and their preferences
        scheduler.generateSchedule(); // Generate the weekly schedule
        scheduler.printSchedule();    // Output the final schedule
    }

    // Step 1: Collect Employee Data
    private void collectEmployees() {
        // Add employees and their daily preferences
        employees.put("Alice", new Employee("Alice", new String[]{
                "Morning", "Morning", "Morning", "Morning", "Afternoon", "Evening", "Morning"
        }));
        employees.put("Bob", new Employee("Bob", new String[]{
                "Afternoon", "Afternoon", "Evening", "Morning", "Afternoon", "Morning", "Morning"
        }));
        employees.put("Charlie", new Employee("Charlie", new String[]{
                "Evening", "Morning", "Morning", "Evening", "Morning", "Morning", "Afternoon"
        }));
        employees.put("Dave", new Employee("Dave", new String[]{
                "Morning", "Evening", "Afternoon", "Morning", "Afternoon", "Evening", "Morning"
        }));
        employees.put("Eve", new Employee("Eve", new String[]{
                "Evening", "Afternoon", "Afternoon", "Evening", "Morning", "Morning", "Evening"
        }));
    }

    // Step 2: Generate Weekly Schedule
    private void generateSchedule() {
        // Initialize schedule (map days and shifts)
        for (String day : DAYS) {
            schedule.put(day, new LinkedHashMap<>());
            for (String shift : SHIFTS) {
                schedule.get(day).put(shift, new ArrayList<>());
            }
        }

        // Assign shifts for each day
        for (String day : DAYS) {
            for (String shift : SHIFTS) {
                // Get list of employees available on this day/shift
                List<Employee> availableEmployees = getAvailableEmployees(day, shift);

                // Assign at least 2 employees to the shift
                while (schedule.get(day).get(shift).size() < 2 && !availableEmployees.isEmpty()) {
                    Employee selectedEmployee = availableEmployees.remove(0);

                    // Assign this employee to the shift
                    schedule.get(day).get(shift).add(selectedEmployee.getName());
                    selectedEmployee.assignShift(day, shift);
                }

                // Handle conflicts: If fewer than 2 employees assigned, assign random employees
                if (schedule.get(day).get(shift).size() < 2) {
                    for (Employee employee : employees.values()) {
                        if (employee.hasRemainingWorkDays() && !schedule.get(day).get(shift).contains(employee.getName())) {
                            schedule.get(day).get(shift).add(employee.getName());
                            employee.assignShift(day, shift);

                            if (schedule.get(day).get(shift).size() == 2) {
                                break; // Stop once shift is full
                            }
                        }
                    }
                }
            }
        }
    }

    // Step 3: Get Available Employees for a Specific Shift
    private List<Employee> getAvailableEmployees(String day, String shift) {
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (employee.canWork(day, shift)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }

    // Step 4: Print the Weekly Schedule
    private void printSchedule() {
        System.out.println("=== Weekly Employee Schedule ===\n");
        for (String day : schedule.keySet()) {
            System.out.println(day + ":");
            for (String shift : schedule.get(day).keySet()) {
                System.out.printf("  %-10s: %s%n", shift, String.join(", ", schedule.get(day).get(shift)));
            }
            System.out.println();
        }
    }
}