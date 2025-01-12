
package org.javawavers.studybuddy.calculations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.*;

public class CreateWeekDay {
    private ArrayList<Week> totalWeeks; // The list containing all weeks

    public CreateWeekDay() {
        totalWeeks = new ArrayList<>();
    }

    public ArrayList<Week> getTotalWeeks() {
        return totalWeeks;
    }

    public Week getTheWeek(int index) {
        return totalWeeks.get(index);
    }

    /**
     * The managerWeekDay method creates the schedule for the days of the week,
     * based on the schedule (schedule matrix) and the tasks (bestTask).
     *  schedule  The scheduling matrix (rows: tasks, columns: days).
     *  bestTask  The list of Task objects.
     *  colSize   The number of days (columns) in the schedule.
     */
    public void managerWeekDay(int[][] schedule, List<Task> bestTask, int colSize) {
        ArrayList<Week> totalWeeks=new ArrayList<>();
        LocalDate today = LocalDate.now(); // Today's date
        DayOfWeek currentDayOfWeek = today.getDayOfWeek();
        int daysUntilMonday = currentDayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();

        // Initialize the first week
        Week currentWeek = new Week();

        // Fill days before today with empty tasks
        for (int i = 0; i < daysUntilMonday; i++) {
            Day emptyDay = new Day(); // Day with no tasks
            currentWeek.getDaysOfWeek().add(emptyDay);
        }

        List<ScheduledTask> scheduledTasksForDay = new ArrayList<>();

        for (int dayIndex = 0; dayIndex < colSize; dayIndex++) {
            LocalDate currentDate = today.plusDays(dayIndex - daysUntilMonday); // Calculate current date

            // Clear the scheduled tasks for the day
            scheduledTasksForDay.clear();
            for (int taskIndex = 0; taskIndex < schedule.length; taskIndex++) {
                int taskId = schedule[taskIndex][dayIndex];
                String taskType = " ";
                if (taskId > 0) { // If there is a task for the specific slot
                    Task task = bestTask.get(taskId); // Retrieve the Task from the list
                    if (task.getTaskType() == 1) {
                        taskType = "Διάβασμα";
                    } else if (task.getTaskType() == 2) {
                        taskType = "Επανάληψη";
                    } else {
                        taskType = "Εργασία";
                    }

                    ScheduledTask scheduledTask = new ScheduledTask(
                            task.getSubject(), taskType,
                            (int) Math.ceil(task.getTaskHours()),
                            currentDate,
                            new Subject(task.getSubject()) // Create Subject from the Task
                    );
                    scheduledTasksForDay.add(scheduledTask);
                }
            }

            // Create a Day object for the current day
            Day currentDay = new Day();
            currentDay.todayTasks.addAll(scheduledTasksForDay);

            // Add the day to the week
            currentWeek.getDaysOfWeek().add(currentDay);

            // If the week is complete or it's the last day, save it
            if (currentWeek.getDaysOfWeek().size() == 7 || dayIndex == colSize - 1) {
                totalWeeks.add(currentWeek);
                currentWeek = new Week(); // Start a new week
            }
        }
        //staticUser.setTotalWeeks(totalWeeks);
        PrintWeeks printWeeks = new PrintWeeks();
        printWeeks.printWeeks(totalWeeks);

    }
}
