package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Repetition} class is responsible for generating and scheduling
 * repetition tasks. Repetition tasks are designed to reinforce learning by
 * scheduling review sessions for specific intervals after the initial study task.
 */
public class Repetition {

  /**
   * This method generates a schedule for repetitions based on the study task.
   * A repetition task is scheduled for:
   * - The next day after the study task.
   * - After 1 day
   * - After 7 days.
   * - After 16 days.
   * - After 35 days.
   * - Then it doubles the interval days till the exam date.
   */
  public static List<Task> generateRepetitions(
          List<Task> tasks, Task studyTask, LocalDate examDate, int day) {
    // Validation of input parameters
    if (tasks == null) {
      throw new IllegalArgumentException("The task list can't be null.");
    }
    if (studyTask == null) {
      throw new IllegalArgumentException("The study task can't be null.");
    }
    if (examDate == null) {
      throw new IllegalArgumentException("The exam date can't be null.");
    }
    if (day < 0) {
      throw new IllegalArgumentException("The day can't be negative.");
    }
    List<RepetitionTask> rep = new ArrayList<>();
    try {
      // Get the date of the study task
      LocalDate studyDate = LocalDate.now().plusDays(day);
      String subject = studyTask.getSubject();

      // Generate repetitions for fixed intervals
      intervalRep(rep, studyDate, examDate, subject);

      // Generate repetitions for doubling intervals
      doubleIntervalRep(rep, studyDate, examDate, subject);

      // Assign repetitions to the schedule
      return assRepetitions(rep, tasks, subject);

    } catch (Exception e) {
      throw new RuntimeException("An error occurred while generating repetitions: "
              + e.getMessage(), e);
    }
  }

  /**
   * Generates repetition tasks for fixed intervals.
   *
   * @param rep       The list of repetition tasks.
   * @param studyDate The starting date of the study task.
   * @param examDate  The exam date.
   * @param subject   The subject of the study task.
   */
  private static void intervalRep(
          List<RepetitionTask> rep, LocalDate studyDate, LocalDate examDate, String subject) {
    try {
      int[] fixedIntervals = {1, 7, 16, 35}; // Fixed intervals in days
      for (int f : fixedIntervals) {
        LocalDate repetitionDate = studyDate.plusDays(f);
        if (repetitionDate.isBefore(examDate)) {
          rep.add(new RepetitionTask(subject, repetitionDate));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while generating fixed interval repetitions: "
              + e.getMessage(), e);
    }
  }

  /**
   * Generates repetition tasks for doubling intervals.
   *
   * @param rep       The list of repetition tasks.
   * @param studyDate The starting date of the study task.
   * @param examDate  The exam date.
   * @param subject   The subject of the study task.
   */
  private static void doubleIntervalRep(
          List<RepetitionTask> rep, LocalDate studyDate, LocalDate examDate, String subject) {
    try {
      int interval = 35; // Start doubling after 35 days
      while (true) {
        interval *= 2; // Double the interval
        LocalDate repetitionDate = studyDate.plusDays(interval);
        if (repetitionDate.isBefore(examDate)) {
          rep.add(new RepetitionTask(subject, repetitionDate));
        } else {
          break; // Stop if the date exceeds the exam date
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while generating doubling interval repetitions: "
              + e.getMessage(), e);
    }
  }

  /**
   * Assigns repetition tasks into the existing schedule.
   * The method ensures tasks are assigned to available slots on valid days.
   *
   * @param rep     The list of repetition tasks to assign.
   * @param tasks   The list of all tasks to update with the new repetitions.
   * @param subject The subject associated with the tasks.
   * @return The updated list of tasks including the newly assigned repetition
   *         tasks.
   */
  public static List<Task> assRepetitions(
          List<RepetitionTask> rep, List<Task> tasks, String subject) {
    // Retrieve the current schedule
    int[][] schedule = TaskAssignment.getValSchedule();
    if (schedule == null) {
      throw new IllegalStateException("Schedule is not initialized.");
    }
    try {
      for (RepetitionTask r : rep) {
        LocalDate today = LocalDate.now();
        int daysBetween = (int) ChronoUnit.DAYS.between(today, r.getDate());
        // Ensure daysBetween is within bounds
        if (daysBetween >= 0 && daysBetween < schedule[0].length) {
          for (int i = 0; i < 12; i++) { // Iterate through rows (max 12 tasks per day)
            // check for an unassigned slot and if the day is available
            if (schedule[i][daysBetween] == 0 && Availability.checkAvailability(daysBetween)) {
              /*
               * Each task of type 1 and 3 takes 2 hours, while each task of type 2 takes 20
               * minutes
               */
              if (TaskAssignment.getRemainingHours() > 1.0 / 3.0) {
                // Add a new repetition task
                tasks.add(new Task(subject, 2)); // 2 represents a repetition task
                // Assign the task index to the schedule
                schedule[i][daysBetween] = tasks.size() - 1;
                break; // Exit loop after assigning the task
              }
            }
          }
        }

      }
      // Update the modified schedule
      TaskAssignment.setValSchedule(schedule);
      return tasks;
    } catch (Exception e) {
      throw new RuntimeException("Error while assigning repetition tasks: " + e.getMessage(), e);
    }
  }

  /**
   * Helper class to represent a repetition task.
   * Each repetition task includes a subject and a date.
   */
  public static class RepetitionTask {
    private String subject;
    private LocalDate date;

    /**
    *Constructor.
     */
    public RepetitionTask(String subject, LocalDate date) {
      this.subject = subject;
      this.date = date;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public LocalDate getDate() {
      return date;
    }

    public void setDate(LocalDate date) {
      this.date = date;
    }
  }
}
