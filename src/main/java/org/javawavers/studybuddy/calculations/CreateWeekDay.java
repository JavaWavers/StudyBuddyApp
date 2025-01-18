package org.javawavers.studybuddy.calculations;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;
import org.javawavers.studybuddy.database.DataInserter;

/**
 * The CreateWeekDay class is responsible for managing the schedule of tasks for days and weeks. It
 * calculates weekly schedules based on a scheduling matrix and a list of tasks and persists them in
 * the database.
 */
public class CreateWeekDay {
  /** Default constructor. */
  public CreateWeekDay() {}

  /**
   * Generates weekly schedules based on the given schedule matrix and task list, and saves the
   * schedule to the static user and database.
   *
   * @param schedule The scheduling matrix (rows: tasks, columns: days).
   * @param bestTask The list of Task objects.
   * @param colSize The number of days (columns) in the schedule.
   * @throws IllegalArgumentException If the inputs are invalid.
   */
  public void managerWeekDay(int[][] schedule, List<Task> bestTask, int colSize) {
    try {
      ArrayList<Week> totalWeeks = new ArrayList<>();
      valInputs(schedule, bestTask, colSize);
      LocalDate today = LocalDate.now(); // Today's date
      int daysUntilMonday = daysUntilMonday(today);
      Week currentWeek = new Week();
      // Fill days before today with empty tasks
      fillDaysBeforeToday(daysUntilMonday, currentWeek);
      fillTotalWeeks(schedule, bestTask, colSize, daysUntilMonday, today, totalWeeks, currentWeek);
      saveToDataB(totalWeeks);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Validates the inputs to ensure the schedule, task list, and column size are valid.
   *
   * @param schedule The scheduling matrix.
   * @param bestTask The list of Task objects.
   * @param colSize The number of days (columns) in the schedule.
   * @throws IllegalArgumentException If the inputs are null or invalid.
   */
  private void valInputs(int[][] schedule, List<Task> bestTask, int colSize) {
    if (schedule == null) {
      throw new IllegalArgumentException("Schedule can't be null");
    }
    if (bestTask == null) {
      throw new IllegalArgumentException("Best task list can't be  null");
    }
    if (colSize <= 0) {
      throw new IllegalArgumentException("Column size must be greater than 0");
    }
  }

  /**
   * Calculates the number of days from today until the most recent Monday.
   *
   * @param today The current date.
   * @return The number of days since Monday.
   */
  private int daysUntilMonday(LocalDate today) {
    DayOfWeek currentDayOfWeek = today.getDayOfWeek();
    return currentDayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();
  }

  /**
   * Fills the days before today with empty tasks in the current week.
   *
   * @param daysUntilM The number of days before today until the most recent Monday.
   * @param currentWeek The current Week object to be updated.
   */
  private void fillDaysBeforeToday(int daysUntilM, Week currentWeek) {
    for (int d = 0; d < daysUntilM; d++) {
      Day day = new Day(); // Day with no tasks
      currentWeek.getDaysOfWeek().add(day);
    }
  }

  /**
   * Processes the schedule matrix to generate weeks and days.
   *
   * @param schedule The scheduling matrix.
   * @param bestTask The list of Task objects.
   * @param colSize The number of days (columns) in the schedule.
   * @param today The current date.
   * @param daysUntilMonday The number of days since the most recent Monday.
   * @param totalWeeks The list to store all generated weeks.
   * @param currentWeek The current Week object being populated.
   */
  private void fillTotalWeeks(
      int[][] schedule,
      List<Task> bestTask,
      int colSize,
      int daysUntilMonday,
      LocalDate today,
      List<Week> totalWeeks,
      Week currentWeek) {
    List<ScheduledTask> scheduledTasksForDay = new ArrayList<>();
    for (int dayIndex = 0; dayIndex < colSize; dayIndex++) {
      LocalDate currentDate = today.plusDays(dayIndex - daysUntilMonday); // Calculate current date
      // Generate tasks for the current day
      generateTasksForDay(schedule, bestTask, dayIndex, scheduledTasksForDay, currentDate);

      // Create a Day object and add tasks
      Day currentDay = new Day();
      currentDay.todayTasks.addAll(scheduledTasksForDay);

      // Add the day to the week
      currentWeek.getDaysOfWeek().add(currentDay);

      // Save the week if complete or if it's the last day
      if (currentWeek.getDaysOfWeek().size() == 7 || dayIndex == colSize - 1) {
        totalWeeks.add(currentWeek);
        currentWeek = new Week();
      }
    }
  }

  /**
   * Generates tasks for a specific day based on the schedule matrix and task list.
   *
   * @param schedule The scheduling matrix.
   * @param bestTask The list of Task objects.
   * @param dayIndex The index of the current day in the schedule.
   * @param scheduledTasksForDay The list to store scheduled tasks for the current day.
   * @param currentDate The current date.
   */
  private void generateTasksForDay(
      int[][] schedule,
      List<Task> bestTask,
      int dayIndex,
      List<ScheduledTask> scheduledTasksForDay,
      LocalDate currentDate) {

    scheduledTasksForDay.clear();
    for (int[] s : schedule) {
      int taskId = s[dayIndex];
      if (taskId > 0) { // If a task exists in this slot
        Task task = bestTask.get(taskId);
        String taskType = determineTaskType(task.getTaskType());
        Subject subject = new Subject(task.getSubject());
        ScheduledTask scheduledTask =
            new ScheduledTask(
                task.getSubject(),
                taskType,
                (int) Math.ceil(task.getTaskHours()),
                currentDate,
                subject);
        scheduledTasksForDay.add(scheduledTask);
      }
    }
  }

  /**
   * Determines the task type as a descriptive string based on its integer representation.
   *
   * @param taskType The integer representing the task type.
   * @return A string describing the task type.
   */
  private String determineTaskType(int taskType) {
    if (taskType == 1) {
      return "Διάβασμα";
    } else if (taskType == 2) {
      return "Επανάληψη";
    } else {
      return "Εργασία";
    }
  }

  /**
   * Saves the generated weeks to the static user and the database.
   *
   * @param totalWeeks The list of generated weeks.
   */
  private void saveToDataB(List<Week> totalWeeks) {
    staticUser.setTotalWeeks(totalWeeks);
    PrintWeeks printWeeks = new PrintWeeks();
    printWeeks.printWeeks(totalWeeks);

    int id = staticUser.getUserId();
    int i = 0;
    int j = 0;

    for (Week w : totalWeeks) {
      DataInserter.insertWeek(i, id);
      for (Day d : w.getDaysOfWeek()) {
        DataInserter.insertDay(j, id, i);
        for (ScheduledTask t : d.getAllTasks()) {
          DataInserter.insertTask(
              t.getTaskName(),
              t.getHoursAllocated(),
              t.getTimeStarted(),
              t.getTimeCompleted(),
              t.getTaskStatus(),
              t.getTaskDate(),
              t.getSubjectName(),
              t.getTaskType(),
              id,
              j);
        }
        j++;
      }
      i++;
    }
  }
}
