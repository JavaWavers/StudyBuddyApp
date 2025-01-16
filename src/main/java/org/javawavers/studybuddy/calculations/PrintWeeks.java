package org.javawavers.studybuddy.calculations;

import java.util.List;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The {@code PrintWeeks} class is responsible for printing the weekly schedule.
 * It takes a list of {@code Week} objects and prints the details of each week,
 * including the tasks assigned for each day.
 */
public class PrintWeeks {
  /**
   * Prints the weekly schedule.
   * For each week in the list, it iterates through its days and displays the tasks scheduled
   * for each day. If no tasks are scheduled for a day, it prints a message indicating that.
   *
   * @param weekList A list of {@link Week} objects representing the weekly schedule.
   */
  public void printWeeks(List<Week> weekList) {
    int weekNumber = 1; // Counter for the week number
    for (Week week : weekList) {
      System.out.println("Week number: " + weekNumber);
      int dayNumber = 1; // Counter for the day number

      // Print the days of the week
      for (Day day : week.getDaysOfWeek()) {
        System.out.println("  Day " + dayNumber + ":");
        if (day.todayTasks.isEmpty()) {
          System.out.println("    No tasks scheduled.");
        } else {
          for (ScheduledTask task : day.todayTasks) {
            System.out.println("    " + task.toString());
          }
        }
        dayNumber++;
      }
      weekNumber++;
    }
  }
}
