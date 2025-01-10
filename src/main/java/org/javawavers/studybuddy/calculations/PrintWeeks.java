package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.ScheduledTask;

import java.util.List;
public class PrintWeeks {
    public void printWeeks(List<Week> weekList) {
        int weekNumber = 1; // Μετρητής για τον αριθμό εβδομάδας
        for (Week week : weekList) {
            System.out.println("Week number: " + weekNumber);
            int dayNumber = 1; // Μετρητής για τον αριθμό ημέρας

            // Εκτύπωση των ημερών της εβδομάδας
            for (Day day : week.daysOfWeek) {
                System.out.println("  Day " + dayNumber + ":");
                if (day.todayTasks.isEmpty()) {
                    System.out.println("    No tasks scheduled.");
                } else {
                    for (ScheduledTask task : day.todayTasks) {
                        System.out.println("    " + task.toString());
                    }
                }
                dayNumber++;
            }
            weekNumber++;
        }
    }
}