package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to assign a score to each valid result that could be used as a recommended
 * study schedule for the user. The SimulateAnnealing class produces 50 valid results. Validity
 * means that the tasks are distributed according to the user's daily availability for studying.
 * Each result is then given a score to determine the most effective one among those that are
 * randomly produced. The maximum score a valid result can achieve is 5.0. -0.2 points are deducted
 * each time the same task type (1-studying, 2-revision, 3-assignment) for the same subject is
 * assigned on the same day. -0.1 points are deducted when the algorithm suggests that the user
 * should study the same subject two days in a row
 */
public class Scoring {
  /**
   * Calculates the total score for a study schedule based on task assignments and subject
   * distribution. The score starts at 5.0 and is reduced based on penalties.
   *
   * @param taskList The list of tasks (study, revision, assignments) in the schedule.
   * @param sch The 2D schedule array, where each cell contains the index of the task assigned to
   *     that time slot.
   * @param colSize The number of days in the schedule (number of columns in the schedule array).
   * @return The calculated score of the schedule.
   * @throws IllegalArgumentException If the schedule array or task list is invalid.
   */
  public static double calculateScore(List<Task> taskList, int[][] sch, int colSize) {
    if (taskList == null || sch == null || colSize <= 0) {
      throw new IllegalArgumentException(
          "Invalid input: taskList, sch," + " or colSize can not be null");
    }
    // set the score at 5.0. The higher that can possibly be achieved
    double score = 5.0;

    // Deduct penalties for the same task type for the same subject on the same day
    score -= sameDayPenalty(taskList, sch, colSize);

    // Deduct penalties for studying the same subject on consecutive days
    score -= consDaysPenalty(taskList, sch, colSize);

    return score;
  }

  /**
   * Applies a penalty for assigning the same task type (e.g., studying) to the same subject on the
   * same day.
   *
   * @param taskList The list of tasks in the schedule.
   * @param sch The 2D schedule array.
   * @param colSize The number of days in the schedule.
   * @return The total penalty for same-day task repetition.
   */
  private static double sameDayPenalty(List<Task> taskList, int[][] sch, int colSize) {
    double penalty = 0.0;
    // creating a list that should contain each subject only once
    List<String> uniqueS = new ArrayList<>();
    for (Task task : taskList) {
      String subject = task.getSubject();
      if (!uniqueS.contains(subject)) {
        uniqueS.add(subject); // Προσθήκη του μαθήματος αν δεν υπάρχει ήδη
      }
    }

    /*
     *-0.2 penalty for the same task type for the same subject on the same day
     * for each day
     */
    for (int col = 0; col < colSize; col++) {

      // for each individual subject
      for (String u : uniqueS) {

        // we check if there is also a same task
        for (int row = 0; row < 12; row++) {
          if (sch[row][col] >= 0 && sch[row][col] < taskList.size()) {
            Task t = taskList.get(sch[row][col]);
            if (t.getTaskType() == 1) {
              /*
               * checks if the subject from the subject list is the same as
               * the one from the task list
               */
              if (u.equals(t.getSubject())) {
                penalty += 0.2;
              }
            }

          } else {
            break;
          }
        }
      }
    }
    return penalty;
  }

  /**
   * Applies a penalty for scheduling the same subject for studying on consecutive days.
   *
   * @param taskList The list of tasks in the schedule.
   * @param sch The 2D schedule array.
   * @param colSize The number of days in the schedule.
   * @return The total penalty for consecutive-day subject repetition.
   */
  private static double consDaysPenalty(List<Task> taskList, int[][] sch, int colSize) {
    double penalty = 0.0;
    // -0.1 penalty for studying the same subject two days in a row
    // For each column (day)
    for (int col = 0; col < colSize - 1; col++) {
      // Traverse the tasks of the current day
      for (int row = 0; row < 12; row++) {
        if (sch[row][col] >= 0 && sch[row][col] < taskList.size()) {
          Task curTask = taskList.get(sch[row][col]);
          String curSubject = curTask.getSubject();
          // The type of the task (1: Study, 2: Revision, 3: Assignment)
          int curType = curTask.getTaskType();

          // Check the next day
          for (int nextRow = 0; nextRow < 12; nextRow++) {
            if (sch[nextRow][col + 1] != 0 && sch[nextRow][col + 1] < taskList.size()) {
              Task nextTask = taskList.get(sch[nextRow][col + 1]);
              String nextSubject = nextTask.getSubject();
              int nextType = nextTask.getTaskType();

              // If the same subject and the same type of task is scheduled on consecutive
              // days
              // except from assignments
              if (curSubject.equals(nextSubject) && curType == nextType && curType == 1) {
                // Deduct 0.1 points
                penalty += 0.1;
                break;
              }
            }
          }
        }
      }
    }
    return penalty;
  }
}
