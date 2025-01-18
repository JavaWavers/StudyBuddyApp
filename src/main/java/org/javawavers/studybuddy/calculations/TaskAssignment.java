package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is responsible for assigning tasks to a schedule. The tasks include studying,
 * revision, and assignments. It allocates the tasks based on the available hours for each day and
 * also manages the merging of repetition tasks and assignment deadlines. The task assignment
 * follows a randomized process, and the goal is to ensure that all tasks are scheduled within the
 * given time slots. The assignment process involves checking for available time, handling
 * repetitions for study tasks, ensuring that tasks are scheduled within their respective deadlines,
 * and providing a fallback mechanism if no dedicated slot is available for assignments.
 */
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

  public static List<Task> getTasks() {
    return tasks;
  }

  /**
   * Assigns tasks to a schedule based on available hours and deadlines. The tasks are randomly
   * shuffled and then allocated to time slots for each day. The method checks for task types,
   * available hours, and deadlines (such as exam or assignment dates).Special consideration is
   * given to assignment tasks to ensure they are assigned even if no dedicated time slots are
   * available. This method performs the task assignment in multiple stages: 1. Merges repetition
   * tasks if necessary. 2. Allocates tasks to available time slots, respecting task durations and
   * remaining hours. 3. Ensures all assignment tasks (type 3) are assigned, even when no specific
   * slot is available.
   *
   * @param assTasks The list of tasks to be assigned.
   * @param colSize The number of columns (days) in the schedule.
   * @return A 2D array representing the task schedule after assignments are made.
   * @throws IllegalStateException if there is an error with task assignment.
   */
  public static int[][] assignTask(List<Task> assTasks, int colSize) {
    Collections.shuffle(assTasks);
    tasks = new ArrayList<>(assTasks);
    // if (colSize == 0) {
    // throw new IllegalStateException("Column size is not initialized.");
    // }

    valSchedule = new int[12][colSize];
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < colSize; j++) {
        valSchedule[i][j] = 0;
      }
    }
    int taskIndex = 1;
    if (!tasks.isEmpty()) {
      tasks.add(tasks.get(0));
    } else {
      throw new IllegalStateException(
          "Η λίστα tasks είναι κενή και δεν μπορεί να προστεθεί το πρώτο στοιχείο.");
    }
    int taskLength = tasks.size() - 1;

    for (int col = 0; col < colSize; col++) {
      // Merge repetition tasks if needed
      Availability.mergeRepTasks(valSchedule, tasks, col);
      remainingHours = Availability.getTotalAvailableHours(col);
      // Reduce available hours for repetition tasks
      Availability.reduceRepAvailability(col, tasks);

      // Check if there is any availability for the day
      boolean flagNav = Availability.checkAvailability(col);

      if (flagNav) {
        for (int row = 0; row < 12; row++) { // Max 12 tasks per day
          if (taskIndex > taskLength) {
            break;
          }

          if (remainingHours >= 2.0) { // Each task requires 2 hours
            // flag for deadlines
            boolean flagEx = false;
            boolean flagAss = false;
            if (tasks.get(taskIndex).getTaskType() == 1) {
              // Check exam dates
              if (!SimulateAnnealing.getExams().isEmpty()) {
                flagEx = Dates.checkDate(tasks.get(taskIndex), col, SimulateAnnealing.getExams());
              }
              if (flagEx) {
                if (valSchedule[row][col] == 0) {
                  // Store the task index in the schedule
                  valSchedule[row][col] = taskIndex;
                  // Reduce the remaining hours
                  remainingHours -= 2.0;
                  taskIndex++;

                  // Generate repetitions for tasks of type 1
                  LocalDate exDate =
                      Dates.getExDate(tasks.get(taskIndex - 1), SimulateAnnealing.getExams());
                  assTasks =
                      Repetition.generateRepetitions(
                          tasks, assTasks.get(taskIndex - 1), exDate, col);
                }
              } else {
                taskIndex++;
              }

            } else if (tasks.get(taskIndex).getTaskType() == 3) {
              // Check assignment deadlines
              if (!SimulateAnnealing.getAssignments().isEmpty()) {
                flagAss =
                    Dates.checkDate(tasks.get(taskIndex), col, SimulateAnnealing.getAssignments());
              }
              if (flagAss) {
                if (valSchedule[row][col] == 0) {
                  // Store the task index in the schedule
                  valSchedule[row][col] = taskIndex;
                  // Reduce the remaining hours
                  remainingHours -= 2.0;
                  taskIndex++;
                }
              } else {
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
     * Ensure all type 3 tasks (assignments) are assigned, even if no dedicated time
     * slot exists.
     */
    while (taskIndex < taskLength) {
      Task task = tasks.get(taskIndex);

      // Process only Type 3 tasks
      if (task.getTaskType() == 3) {
        boolean taskAssigned = false;

        // Iterate over all columns (days) and rows (slots)
        for (int col = 0; col < colSize && !taskAssigned; col++) {
          for (int row = 0; row < 12; row++) {
            if (valSchedule[row][col] == 0) { // Check if the slot is empty
              valSchedule[row][col] = taskIndex; // Assign the task
              taskAssigned = true; // Mark as assigned
              break; // Exit the inner loop after assigning the task
            }
          }
        }

        // If no slot is available, you might log a warning or take additional action
        if (!taskAssigned) {
          System.out.println("Warning: Could not assign Task ID " + taskIndex);
        }
      }

      // Move to the next task
      taskIndex++;
    }

    return valSchedule;
  }
}
