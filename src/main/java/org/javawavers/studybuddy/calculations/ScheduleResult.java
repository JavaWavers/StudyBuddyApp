package org.javawavers.studybuddy.calculations;

import java.util.List;

/**
 * The ScheduleResult class represents the result of a scheduling process. It stores the score of
 * the schedule, the list of tasks assigned, and the schedule itself in the form of an array. This
 * class is used to encapsulate and manage the outcome of a scheduling attempt for easy comparison
 * and evaluation.
 */
public class ScheduleResult {
  double score;
  List<Task> tasks;
  int[][] schedule;

  /**
   * Constructor for the ScheduleResult class. It initializes the score, tasks, and schedule based
   * on the provided values.
   *
   * @param score The score of the schedule indicating its quality.
   * @param tasks The list of tasks assigned in this schedule.
   * @param schedule The 2D array representing the schedule.
   */
  ScheduleResult(double score, List<Task> tasks, int[][] schedule) {
    this.score = score;
    this.tasks = tasks;
    this.schedule = schedule;
  }

  public double getScore() {
    return score;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public int[][] getSchedule() {
    return schedule;
  }
}
