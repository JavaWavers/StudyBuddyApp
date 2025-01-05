package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
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

    public static int[][] assignTask(List<Task> assTasks, int colSize) {
        Collections.shuffle(assTasks);
        /*
         * The table valSchedule stores the index of the task Array list, after the
         * tasks have been distributed into the available hours
         */
        if (colSize == 0) {
            throw new IllegalStateException(
                    "Column size is not initialized. ");
        }

        valSchedule = new int[12][colSize];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < colSize; j++) {
                valSchedule[i][j] = 0;
            }
        }

        // Index of the tasks Array List
        int taskIndex = 1;
        /*
         * The length of the task list before the tasks of type two
         * are assigned
         */
        int taskLength = assTasks.size() - 1;
        // available hours for the day
        for (int col = 0; col < colSize; col++) {
            /*
             * checks if there is already a revision assigned
             * & Merge repetition tasks if needed
             */
            Availability.mergeRepTasks(valSchedule, assTasks, col);
            remainingHours =  Availability.getTotalAvailableHours(col);
            // reduce Availability accordingly to the assigned repetition tasks
            Availability.reduceRepAvailability(col, assTasks);

            // check non Availability for a day
            boolean flagNAv;
            flagNAv = Availability.checkAvailability(col);

            if (flagNAv) {

                for (int row = 0; row < 12; row++) { // Max 12 tasks per day
                    // the loop ends when every task is assigned to a day
                    if (taskIndex >= taskLength) {
                        break;
                    }

                    if (remainingHours >= 2.0) { // each task requires 2 hours
                        /*
                         * check if the exam date of the subject's task that we want to
                         * assign to a day has passed
                         */
                        boolean flagEx;

                        flagEx = ExamDates.checkExamDate(assTasks.get(taskIndex), col, SimulateAnnealing.getExams());
                        LocalDate exDate = ExamDates.getExDate(assTasks.get(taskIndex),
                                SimulateAnnealing.getExams());

                        if (flagEx) {

                            if (valSchedule[row][col] == 0) {
                                // corrects the schedule table

                                // store the task index
                                valSchedule[row][col] = taskIndex;
                                // The remaining hours is reduced by 2 hours
                                remainingHours -= 2.0;
                                taskIndex++;
                                // Assign task Type 2 only if task type =1
                                if (assTasks.get(taskIndex).getTaskType() == 1) {
                                    assTasks = Repetition.generateRepetitions(assTasks, assTasks.get(taskIndex), exDate,
                                            col);
                                }

                            }
                        } else {
                            // continue to the next task
                            taskIndex++;
                        }
                    } else {
                        // if there are not enough available hours we continue to the next day
                        break;
                    }
                }
            }
        }

        // return the table with the valid result
        return valSchedule;
    }
}
