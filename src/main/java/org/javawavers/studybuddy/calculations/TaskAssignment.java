package org.javawavers.studybuddy.calculations;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskAssignment {
    // table for the task indexes
    private static int[][] valSchedule;

    // Methods in order to have access to the table for other classes
    public static void setValSchedule(int[][] sc) {
        valSchedule = sc;
    }

    public static int[][] getValSchedule() {
        return valSchedule;
    }

    // stores the remaining hours for the day
    private static double remainingHours;

    /*
     * Methods in order for other classes to have access to the
     * remaining hours for a day
     */
    public static double getRemainingHours() {
        return remainingHours;
    }

    public static void setRemainingHours(double remHours) {
        remainingHours = remHours;
    }
    private static List<Task> tasks;
    public static List<Task> getTasks(){
        return tasks;
    }
    public static int[][] assignTask(List<Task> assTasks, int colSize) {
        Collections.shuffle(assTasks);
        tasks=new ArrayList<>(assTasks);
        if (colSize == 0) {
            throw new IllegalStateException("Column size is not initialized.");
        }

        valSchedule = new int[12][colSize];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < colSize; j++) {
                valSchedule[i][j] = 0;
            }
        }
        int taskIndex = 1;
        tasks.add(tasks.getFirst());
        int taskLength = tasks.size() - 1;

        for (int col = 0; col < colSize; col++) {
            // Merge repetition tasks if needed
            Availability.mergeRepTasks(valSchedule, tasks, col);
            remainingHours = Availability.getTotalAvailableHours(col);
            // Reduce available hours for repetition tasks
            Availability.reduceRepAvailability(col, tasks);

            // Check if there is any availability for the day
            boolean flagNAv = Availability.checkAvailability(col);

            if (flagNAv) {
                for (int row = 0; row < 12; row++) { // Max 12 tasks per day
                    if (taskIndex > taskLength) {
                        break;
                    }

                    if (remainingHours >= 2.0) { // Each task requires 2 hours
                        //flag for deadlines
                        boolean flagEx = false;
                        boolean flagAss = false;
                        if(tasks.get(taskIndex).getTaskType() == 1){
                            // Check exam dates
                            if (!SimulateAnnealing.getExams().isEmpty()) {
                                flagEx = Dates.checkDate(tasks.get(taskIndex), col, SimulateAnnealing.getExams());
                            }
                            if(flagEx){
                                if (valSchedule[row][col] == 0) {
                                    // Store the task index in the schedule
                                    valSchedule[row][col] = taskIndex;
                                    // Reduce the remaining hours
                                    remainingHours -= 2.0;
                                    taskIndex++;

                                    // Generate repetitions for tasks of type 1
                                    LocalDate exDate = Dates.getExDate(tasks.get(taskIndex - 1), SimulateAnnealing.getExams());
                                    assTasks = Repetition.generateRepetitions(tasks, assTasks.get(taskIndex - 1), exDate, col);
                                }
                            }else {
                                taskIndex++;
                            }

                        }else if (tasks.get(taskIndex).getTaskType() == 3){
                            // Check assignment deadlines
                            if (!SimulateAnnealing.getAssignments().isEmpty()) {
                                flagAss = Dates.checkDate(tasks.get(taskIndex), col, SimulateAnnealing.getAssignments());
                            }
                            if(flagAss){
                                if (valSchedule[row][col] == 0) {
                                    // Store the task index in the schedule
                                    valSchedule[row][col] = taskIndex;
                                    // Reduce the remaining hours
                                    remainingHours -= 2.0;
                                    taskIndex++;
                                }
                            }else {
                                taskIndex++;
                            }

                        }

                    } else {
                        // If there are not enough available hours, continue to the next day
                        break;
                    }
                }
            }
        }

        /*
         * Special Check for Type 3 Tasks
         * If there are not enough days or hours, ensure all type 3 tasks (assignments) are assigned
         */
        while (taskIndex <= taskLength) {
            Task task = tasks.get(taskIndex);
            if (task.getTaskType() == 3) {
                for (int col = 0; col < colSize; col++) {
                    for (int row = 0; row < 12; row++) {
                        if (valSchedule[row][col] == 0) {
                            // Forcefully assign the task in any available time slot
                            valSchedule[row][col] = taskIndex;
                            taskIndex++;
                            break; // Exit the loop after assigning the task
                        }
                    }
                    if (taskIndex > taskLength) {
                        break; // Exit the loop if all tasks are assigned
                    }
                }
            } else {
                taskIndex++; // Skip non-type 3 tasks
            }
        }

        return valSchedule;
    }


}