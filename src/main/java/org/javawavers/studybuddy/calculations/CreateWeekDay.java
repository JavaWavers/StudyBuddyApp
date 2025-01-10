package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateWeekDay {
    private List<Week> totalWeeks; // Η λίστα που περιέχει όλες τις εβδομάδες

    public CreateWeekDay() {
        totalWeeks = new ArrayList<>();
    }

    public List<Week> getTotalWeeks() {
        return totalWeeks;
    }

    public Week getTheWeek(int index) {
        return totalWeeks.get(index);
    }

    /**
     * Η μέθοδος managerWeekDay δημιουργεί το πρόγραμμα για τις μέρες της εβδομάδας,
     * με βάση το πρόγραμμα (schedule) και τις εργασίες (bestTask).
     *
     * @param schedule  Ο πίνακας προγραμματισμού (γραμμές: εργασίες, στήλες: μέρες).
     * @param bestTask  Η λίστα με τα αντικείμενα Task.
     * @param colSize   Ο αριθμός των ημερών (στηλών) στο πρόγραμμα.
     */
    public void managerWeekDay(int[][] schedule, List<Task> bestTask, int colSize) {
        LocalDate today = LocalDate.now(); // Η σημερινή ημερομηνία
        List<ScheduledTask> scheduledTasksForDay = new ArrayList<>();
        Week currentWeek = new Week();

        for (int dayIndex = 0; dayIndex < colSize; dayIndex++) {
            LocalDate currentDate = today.plusDays(dayIndex); // Υπολογισμός της τρέχουσας ημερομηνίας

            // Δημιουργία λίστας για τα ScheduledTasks της ημέρας
            scheduledTasksForDay.clear();
            for (int taskIndex = 0; taskIndex < schedule.length; taskIndex++) {
                int taskId = schedule[taskIndex][dayIndex];
                if (taskId > 0) { // Αν υπάρχει εργασία για τη συγκεκριμένη θέση
                    Task task = bestTask.get(taskId - 1); // Ανακτά το Task από τη λίστα
                    ScheduledTask scheduledTask = new ScheduledTask(
                            task.getSubject(),
                            (int) Math.ceil(task.getTaskHours()),
                            currentDate,
                            new Subject(task.getSubject()) // Δημιουργία Subject από το Task
                    );
                    scheduledTasksForDay.add(scheduledTask);
                }
            }

            // Δημιουργία αντικειμένου Day για την τρέχουσα ημέρα
            Day currentDay = new Day();
            currentDay.todayTasks.addAll(scheduledTasksForDay);

            // Προσθήκη της ημέρας στην εβδομάδα
            currentWeek.daysOfWeek.add(currentDay);

            // Αν η εβδομάδα ολοκληρωθεί ή είναι η τελευταία ημέρα, αποθήκευσέ την
            if (currentWeek.daysOfWeek.size() == 7 || dayIndex == colSize - 1) {
                totalWeeks.add(currentWeek);
                currentWeek = new Week(); // Ξεκίνα νέα εβδομάδα
            }
        }
        PrintWeeks printWeeks = new PrintWeeks();
        printWeeks.printWeeks(totalWeeks);
    }
}
