package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The BarChartCalc class contains static methods for calculating total study hours, assignment
 * hours, revision hours, and overall task hours for the current week.
 */
public class BarChartCalc {

  /**
   * Calculates the total hours for all completed tasks in the current week.
   *
   * @return the total number of hours allocated to completed tasks.
   */
  public static int calcTotalHours() {
    int totalHours = 0;
    staticUser.calculateCurrentWeek(); // Determine the current week
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      // Iterate over all days in the current week
      for (Day d : currentWeek.getDaysOfWeek()) {
        // Iterate over all tasks for the current day
        for (ScheduledTask s : d.getTodayTasks()) {
          // checks if the task is completed
          if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
            totalHours += s.getHoursAllocated();
          }
        }
      }
    }
    return totalHours;
  }

  /**
   * Calculates the total study hours for completed study tasks in the current week.
   *
   * @return the total number of hours allocated to completed study tasks.
   */
  public static int calcStudyHours() {
    int studyHours = 0;
    staticUser.calculateCurrentWeek(); // Determine the current week
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      // Iterate over all days in the current week
      for (Day d : currentWeek.getDaysOfWeek()) {
        // Iterate over all tasks for the current day
        for (ScheduledTask s : d.getTodayTasks()) {
          // checks if the task is completed
          if (s.getTaskType().equals("Διάβασμα")
              && (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED)) {
            studyHours += s.getHoursAllocated();
          }
        }
      }
    }
    return studyHours;
  }

  /**
   * Calculates the total assignment hours for completed assignment tasks in the current week.
   *
   * @return the total number of hours allocated to completed assignment tasks.
   */
  public static int calcAssignmentHours() {
    int assignmentHours = 0;
    staticUser.calculateCurrentWeek(); // Determine the current week
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      // Iterate over all days in the current week
      for (Day d : currentWeek.getDaysOfWeek()) {
        // Iterate over all tasks for the current day
        for (ScheduledTask s : d.getTodayTasks()) {
          // checks if the task is completed
          if (s.getTaskType().equals("Εργασίες")
              && (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED)) {
            assignmentHours += s.getHoursAllocated();
          }
        }
      }
    }
    return assignmentHours;
  }

  /**
   * Calculates the total revision hours for completed revision tasks in the current week.
   *
   * @return the total number of hours allocated to completed revision tasks.
   */
  public static int calcRevisionHours() {
    int revisionHours = 0;
    staticUser.calculateCurrentWeek(); // checks if the task is completed
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      // Iterate over all days in the current week
      for (Day d : currentWeek.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Επανάληψη")
              && (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED)) {
            revisionHours += s.getHoursAllocated();
          }
        }
      }
    }
    return revisionHours;
  }
}
