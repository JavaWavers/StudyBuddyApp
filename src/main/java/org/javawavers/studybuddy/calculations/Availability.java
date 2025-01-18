package org.javawavers.studybuddy.calculations;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * The {@code Availability} class is responsible for managing the availability of study time for
 * each day of the week and handling specific non-available dates. This class provides methods to:
 * Set the available study hours per day of the week. Mark specific dates as non-available for
 * studying. Check if a specific day is available for scheduling tasks. Retrieve total available
 * study hours for a given day. Merge repetitive tasks in a schedule. Reduce available study hours
 * based on tasks assigned.
 */
public class Availability {
  /**
   * A table for the available studying time for each day of the week The first element of the table
   * is never used. Each number from 1 to 7 represents a day of the week starting with Monday
   */
  private static int[] avPerDay = new int[8];

  /*
   * Gives the ability to the user to insert certain dates that there
   * is no available time for studying
   */
  private static LinkedList<LocalDate> dates = new LinkedList<>();

  /** Default constructor. */
  public Availability() {}

  /** Setting the availability per day of the week. */
  public static void setAvailability(int i, int av) {
    if (i <= 0 || i > 7) {
      throw new IllegalArgumentException(" Ο αριθμός πρέπει να είναι μεταξύ του 1και του 7");
    }
    avPerDay[i] = av;
  }

  /** Sets the availability per day of the week using the values from the {@code StaticUser}. */
  public static void setAvPerDay() {
    System.out.println("DONE");
    avPerDay = staticUser.getAvPerDay();
  }

  /**
   * Marks a specific date as non-available for studying.
   *
   * @param l the {@code LocalDate} to mark as non-available
   */
  public static void setNonAvailability(LocalDate l) {
    // checks if a date is already used
    if (!dates.contains(l)) {
      dates.add(l); // insert a day that there is no availability for studying
    } else {
      // if the date is already used, a message is given to the user
      System.out.println("Η ημερομηνία " + l + " έχει ήδη καταχωρηθεί");
    }
  }

  /**
   * Checks if the day that the program asserts a task to the user is set as non-available and
   * returns true if the day is Available.
   */
  public static boolean checkAvailability(int day) {
    LocalDate taskDate = LocalDate.now().plusDays(day);
    return !dates.contains(taskDate);
  }

  /**
   * Retrieves the total available study hours for a specific day.
   *
   * @param i the number of days from today (e.g., 0 for today, 1 for tomorrow, etc.)
   * @return the total available study hours for the specified day
   * @throws IllegalArgumentException if the day name is invalid
   */
  public static int getTotalAvailableHours(int i) {
    LocalDate today = LocalDate.now(); // The date of the day that we currently are
    // The date that we want to assert the tasks
    LocalDate examDate = today.plusDays(i);

    // Name of the day as a String type Data
    String dayName =
        examDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("el"));

    return switch (dayName) {
      case "Δευτέρα" -> avPerDay[1];
      case "Τρίτη" -> avPerDay[2];
      case "Τετάρτη" -> avPerDay[3];
      case "Πέμπτη" -> avPerDay[4];
      case "Παρασκευή" -> avPerDay[5];
      case "Σάββατο" -> avPerDay[6];
      case "Κυριακή" -> avPerDay[7];
      default -> throw new IllegalArgumentException("Ημέρα μη έγκυρη: " + dayName);
    };
  }

  /**
   * Merges repetitive tasks (of type 2) for a specific column in the schedule. When multiple
   * repetitive tasks for the same subject exist, they are merged into one task, and their total
   * hours are updated.
   *
   * @param schedule the 2D array representing the schedule
   * @param tasks the list of tasks
   * @param col the column index in the schedule to process
   */
  public static void mergeRepTasks(int[][] schedule, List<Task> tasks, int col) {
    // Get a list of subject names from the tasks
    List<String> subjectNames =
        tasks.stream()
            .map(Task::getSubject) // Apply the getSubject() method on each Task to extract the
            // subject name
            .toList(); // Collect the results into a new list
    int[] rowsInd = new int[2];
    for (String s : subjectNames) {
      int num = 0;
      int pointerIndex = 0;
      for (int row = 0; row < 12; row++) {
        int taskIndex = schedule[row][col]; // Get the task index from the schedule
        if (taskIndex != 0) { // Check if there is a task in this slot
          Task task = tasks.get(taskIndex); // Retrieve the task from the tasks list

          // Check if the task is a repetition task (type 2)
          if (task.getTaskType() == 2) {
            String subjectName = task.getSubject(); // Get the subject name
            if (subjectName.equals(s)) {
              num++;
            }
            if (num == 1) {
              pointerIndex = taskIndex;
            } else if (num == 2) {
              rowsInd[0] = row;
            } else if (num == 3) {
              rowsInd[1] = row;
              tasks
                  .get(pointerIndex)
                  .setTaskHours(tasks.get(pointerIndex).getTaskHours() + 2.0 / 3.0);
              // clear the slots whose tasks are merged into the first one
              for (int i = 0; i < 2; i++) {
                schedule[rowsInd[i]][col] = 0;
              }
              // continues to the next rows in case there are more revision tasks that can be
              // merged
              num = 0;
            }
          }
        }
      }
    }
    TaskAssignment.setValSchedule(schedule);
  }

  /**
   * This method reduces the available hours for a specific day based on the tasks scheduled in a
   * given column.
   */
  public static void reduceRepAvailability(int col, List<Task> tasks) {
    int[][] schedule = TaskAssignment.getValSchedule();
    for (int row = 0; row < 12; row++) {
      double reducedHours = 0.0;
      if (schedule[row][col] != 0) {
        reducedHours = tasks.get(schedule[row][col]).getTaskHours();
      }
      TaskAssignment.setRemainingHours(TaskAssignment.getRemainingHours() - reducedHours);
    }
  }
}
