package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.HashMap;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The {@code LineChartCalc} class calculates the data required to visualize
 * a user's weekly progress.
 * It provides a method to calculate the total number of completed task hours for each day
 * of the current week. This data can be used to generate a line chart or other visualizations
 * to track progress.
 */
public class LineChartCalc {

  /**
   * Calculates the total number of hours spent on completed tasks for each day of the current week.
   * If a current week is identified, the method iterates through its days and computes the total
   * hours allocated to tasks marked as {@code COMPLETED}. If no current week is available, it
   * initializes the progress data with zero hours for all days.
   *
   * @return A {@code HashMap} where the key is the day number (1 to 7), and the value is the total
   *     hours of completed tasks for that day.
   */
  public static HashMap<Integer, Integer> weekProgress() {
    HashMap<Integer, Integer> progress = new HashMap<>();
    staticUser.calculateCurrentWeek();
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      int dayNumb = 1;
      for (Day d : currentWeek.getDaysOfWeek()) {
        int totalHours = 0;
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
            totalHours += s.getHoursAllocated();
          }
        }
        progress.put(dayNumb, totalHours);
      }
    } else {
      for (int day = 1; day < 8; day++) {
        progress.put(day, 0);
      }
    }
    return progress;
  }
}
