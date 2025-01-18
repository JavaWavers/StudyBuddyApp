package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Repetition} class is responsible for generating and scheduling
 * repetition tasks to reinforce learning.
 */
public class Repetition {

  /**
   * This method generates a schedule for repetitions based on the study task.
   * Repetitions are scheduled at fixed and doubling intervals.
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
   */
  static void intervalRep(
          List<RepetitionTask> rep, LocalDate studyDate, LocalDate examDate, String subject) {
    try {
      int[] fixedIntervals = {1, 7, 16, 35};
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
   */
  static void doubleIntervalRep(
          List<RepetitionTask> rep, LocalDate studyDate, LocalDate examDate, String subject) {
    try {
      int interval = 35;
      while (true) {
        interval *= 2; // Double the interval
        LocalDate repetitionDate = studyDate.plusDays(interval);
        if (repetitionDate.isBefore(examDate)) {
          rep.add(new RepetitionTask(subject, repetitionDate));
        } else {
          break;
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while generating doubling interval repetitions: "
              + e.getMessage(), e);
    }
  }

  /**
   * Assigns repetition tasks into the existing schedule.
   */
  public static List<Task> assRepetitions(
          List<RepetitionTask> rep, List<Task> tasks, String subject) {
    int[][] schedule = TaskAssignment.getValSchedule();
    if (schedule == null) {
      throw new IllegalStateException("Schedule is not initialized.");
    }

    try {
      for (RepetitionTask r : rep) {
        LocalDate today = LocalDate.now();
        int daysBetween = (int) ChronoUnit.DAYS.between(today, r.getDate());

        // Ensure daysBetween is within bounds of the schedule
        if (daysBetween < 0 || daysBetween >= schedule[0].length) {
          throw new IndexOutOfBoundsException("Days between exceeds schedule bounds.");
        }

        for (int i = 0; i < 12; i++) {
          if (schedule[i][daysBetween] == 0 && Availability.checkAvailability(daysBetween)) {
            if (TaskAssignment.getRemainingHours() > 1.0 / 3.0) {
              tasks.add(new Task(subject, 2));
              schedule[i][daysBetween] = tasks.size() - 1;
              break;
            }
          }
        }
      }

      TaskAssignment.setValSchedule(schedule);
      return tasks;
    } catch (IndexOutOfBoundsException e) {
      throw new RuntimeException("Index out of bounds when assigning repetition tasks: "
              + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Error while assigning repetition tasks: " + e.getMessage(), e);
    }
  }

  /**
   * Helper class to represent a repetition task.
   */
  public static class RepetitionTask {
    private String subject;
    private LocalDate date;

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
