package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Validate {
    /**
     * Main method to validate the schedule using multiple validity checks.
     * @param validSchedule - the 2D array representing the schedule
     * @param taskList - the list of tasks
     * @return - the validated schedule
     */
    public static int[][] validateSchedule(int[][] validSchedule, List<Task> taskList) {
        validSchedule = deadlineValidity(validSchedule, taskList);
        validSchedule = availabilityValidity(validSchedule, taskList);
        validSchedule = assignmentsValidity(validSchedule, taskList);
        return validSchedule;
    }

    /**
     * Ensures that no task is scheduled after its associated deadline.
     */
    private static int[][] deadlineValidity(int[][] validSchedule, List<Task> taskList) {
        List<Dates> examList = new ArrayList<>(SimulateAnnealing.getExams());
        LocalDate examDate = LocalDate.now();

        for (int col = 0; col < validSchedule[0].length; col++) {
            for (int row = 0; row < 12; row++) {
                if (validSchedule[row][col] != 0) {
                    String subjectName = taskList.get(validSchedule[row][col]).getSubject();

                    // Find the corresponding exam date
                    for (Dates e : examList) {
                        if (subjectName.equals(e.getSubName())) {
                            examDate = e.getDate();
                            break;
                        }
                    }

                    // Check if the task is scheduled after the exam date
                    if (examDate.isAfter(LocalDate.now().plusDays(col))) {
                        continue;
                    } else {
                        validSchedule[row][col] = 0; // Clear invalid task
                    }
                }
            }
        }
        return validSchedule;
    }

    /**
     * Ensures that tasks are scheduled within available hours for each day.
     */
    private static int[][] availabilityValidity(int[][] validSchedule, List<Task> taskList) {
        for (int col = 0; col < validSchedule[0].length; col++) {
            double dayAvailability = Availability.getTotalAvailableHours(col);

            for (int row = 0; row < 12; row++) {
                if (Availability.checkAvailability(col)) {
                    if (validSchedule[row][col] != 0) {
                        // Deduct hours based on task type
                        if (dayAvailability >= 2.0) {
                            if (taskList.get(validSchedule[row][col]).getTaskType() == 1) {
                                dayAvailability -= 2.0;
                            } else if (taskList.get(validSchedule[row][col]).getTaskType() == 2) {
                                dayAvailability -= 1.0 / 3.0;
                            } else {
                                dayAvailability -= 2.0; // Default case
                            }
                        } else {
                            validSchedule[row][col] = 0; // Clear task if insufficient hours
                        }
                    }
                } else {
                    validSchedule[row][col] = 0; // Clear task if day is unavailable
                }
            }
        }
        return validSchedule;
    }

    /**
     * Ensures that all assignments (TaskType == 3) are scheduled,
     * forcefully assigning them if needed.
     */
    private static int[][] assignmentsValidity(int[][] validSchedule, List<Task> taskList) {
        // Create a list of all unassigned tasks of type 3
        List<Integer> unassignedTasks = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskType() == 3) {
                unassignedTasks.add(i);
            }
        }

        // Check if tasks are already scheduled, and remove them from unassignedTasks
        for (int col = 0; col < validSchedule[0].length; col++) {
            for (int row = 0; row < 12; row++) {
                int taskIndex = validSchedule[row][col];
                if (unassignedTasks.contains(taskIndex)) {
                    unassignedTasks.remove(Integer.valueOf(taskIndex)); // Task already scheduled
                }
            }
        }

        int col=0;
        // Forcefully assign remaining tasks of type 3 to available slots
        for (int taskIndex : unassignedTasks) {
            boolean assinged=false;
            while (!assinged) {
                if (col < validSchedule[0].length) {
                    if (Availability.checkAvailability(col)) {
                        for (int row = 0; row < 12; row++) {
                            if (validSchedule[row][col] == 0) { // Empty slot found
                                validSchedule[row][col] = taskIndex; // Assign task
                                col++;
                                assinged = true;
                                break; // Exit inner loop
                            }
                        }
                    } else {
                        //continue to the next Available day
                        col++;

                    }
                }
                if (col == validSchedule[0].length) {
                    //starts over the procedure
                    col = 0;
                }
            }
        }
        return validSchedule;
    }
}
