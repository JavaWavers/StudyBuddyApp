package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The SummaryBoxCalc class provides methods for calculating percentage-based
 * statistics related to task completion. It includes percentages for overall goals,
 * studying, assignments, and revision tasks, based on data extracted from the user's schedule.
 */
public class SummaryBoxCalc {

  /**
   * A list of all weeks retrieved from the static user.
   * This is used to perform calculations on scheduled tasks.
   */
  private static ArrayList<Week> totalWeeks;

  /**
   * Initializes the {@code totalWeeks} field by retrieving all weeks
   * from the static user's schedule. This method ensures that the data
   * is up-to-date before performing calculations.
   */
  private static void setTotalWeeks() {
    totalWeeks = new ArrayList<>(staticUser.getTotalWeeks());
  }

  /**
   * Calculates the percentage of tasks completed across all weeks and days
   * for the static user's schedule. This method includes all types of tasks
   * in the calculation.
   *
   * @return The percentage of completed tasks, as a value between 0 and 100.
   *         Returns 0.0 if there are no tasks.
   */
  public static double percentageCalculatorGoals() {
    setTotalWeeks();
    int totalSum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          totalSum++;
          if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
            sum++;
          }
        }
      }
    }
    if (totalSum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalSum) * 100;
    }
  }

  /**
   * Calculates the percentage of studying tasks completed across all weeks and days
   * for the static user's schedule.
   *
   * @return The percentage of completed studying tasks, as a value between 0 and 100.
   *         Returns 0.0 if there are no studying tasks.
   */
  public static double percentageCalculatorStudying() {
    setTotalWeeks();
    int totalSum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Διάβασμα")) {
            totalSum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalSum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalSum) * 100;
    }
  }

  /**
   * Calculates the percentage of assignment tasks completed across all weeks and days
   * for the static user's schedule.
   *
   * @return The percentage of completed assignment tasks, as a value between 0 and 100.
   *         Returns 0.0 if there are no assignment tasks.
   */
  public static double percentageCalculatorAssignments() {
    setTotalWeeks();
    int totalSum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Εργασία")) {
            totalSum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalSum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalSum) * 100;
    }
  }

  /**
   * Calculates the percentage of revision tasks completed across all weeks and days
   * for the static user's schedule.
   *
   * @return The percentage of completed revision tasks, as a value between 0 and 100.
   *         Returns 0.0 if there are no revision tasks.
   */
  public static double percentageCalculatorRevision() {
    setTotalWeeks();
    int totalSum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Επανάληψη")) {
            totalSum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalSum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalSum) * 100;
    }
  }
}
