package org.javawavers.studybuddy.courses;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code ScheduledTask} class represents a scheduled task for a specific subject or course. It
 * includes details such as task name, type, allocated study hours, task status, start and
 * completion times, and associated dates. The class also provides methods to manage the task's
 * status, calculate time variance, and track progress. It supports various constructors for
 * flexibility in creating tasks. Features: Support for different task types and statuses (e.g.,
 * COMPLETED, IN_PROGRESS). Automatic time tracking for start and completion times based on task
 * status. Time variance calculation between start and completion times. Flexible constructors to
 * accommodate tasks with or without specific subjects.
 */
public class ScheduledTask {

  // Fields
  private String taskName;
  private int hoursAllocated;
  private LocalTime timeStarted;
  private LocalTime timeCompleted;
  private TaskStatus taskStatus;
  private LocalDate taskDate;
  private final String subjectName;
  private final String taskType;
  private int taskId;

  /**
   * Enum representing the status of a task.
   *
   * <ul>
   *   <li>{@code COMPLETED}: The task has been completed.
   *   <li>{@code IN_PROGRESS}: The task is currently being worked on.
   *   <li>{@code LATE}: The task is overdue and not yet completed.
   *   <li>{@code UPCOMING}: The task is scheduled for a future date.
   * </ul>
   */
  public enum TaskStatus {
    COMPLETED,
    IN_PROGRESS,
    LATE,
    UPCOMING
  }

  // Constructor

  /**
   * Constructs a {@code ScheduledTask} object with full details, including subject and time
   * tracking.
   *
   * @param taskName The name of the task.
   * @param taskType The type of the task (e.g., "Homework", "Exam Preparation").
   * @param hoursAllocated The recommended number of hours for completing the task.
   * @param taskStatus The current status of the task.
   * @param timeStarted The time the task started.
   * @param timeCompleted The time the task was completed.
   * @param taskDate The date associated with the task.
   * @param subject The subject associated with the task.
   */
  public ScheduledTask(
      String taskName,
      String taskType,
      int hoursAllocated,
      TaskStatus taskStatus,
      LocalTime timeStarted,
      LocalTime timeCompleted,
      LocalDate taskDate,
      Subject subject) {
    this.taskName = taskName;
    this.taskType = taskType;
    this.hoursAllocated = hoursAllocated;
    this.taskStatus = taskStatus;
    this.timeStarted = timeStarted;
    this.timeCompleted = timeCompleted;
    this.taskDate = taskDate;
    this.subjectName = subject.getCourseName();
  }

  /**
   * Constructs a {@code ScheduledTask} object with subject details provided as a string.
   *
   * @param taskName The name of the task.
   * @param taskType The type of the task.
   * @param hoursAllocated The recommended number of hours for completing the task.
   * @param taskStatus The current status of the task.
   * @param timeStarted The time the task started.
   * @param timeCompleted The time the task was completed.
   * @param taskDate The date associated with the task.
   * @param subjectName The name of the subject associated with the task.
   */
  public ScheduledTask(
      String taskName,
      String taskType,
      int hoursAllocated,
      TaskStatus taskStatus,
      LocalTime timeStarted,
      LocalTime timeCompleted,
      LocalDate taskDate,
      String subjectName) {
    this.taskName = taskName;
    this.taskType = taskType;
    this.hoursAllocated = hoursAllocated;
    this.taskStatus = taskStatus;
    this.timeStarted = timeStarted;
    this.timeCompleted = timeCompleted;
    this.taskDate = taskDate;
    this.subjectName = subjectName;
  }

  /**
   * Constructs a {@code ScheduledTask} object with default status (UPCOMING) and no time tracking.
   *
   * @param taskName The name of the task.
   * @param taskType The type of the task.
   * @param hoursAllocated The recommended number of hours for completing the task.
   * @param taskDate The date associated with the task.
   * @param subject The subject associated with the task.
   */
  public ScheduledTask(
      String taskName, String taskType, int hoursAllocated, LocalDate taskDate, Subject subject) {
    this(taskName, taskType, hoursAllocated, TaskStatus.UPCOMING, null, null, taskDate, subject);
  }

  // Getter and Setter for taskName
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  // Getter and Setter for hoursAllocated
  public int getHoursAllocated() {
    return hoursAllocated;
  }

  public void setHoursAllocated(int hoursAllocated) {
    this.hoursAllocated = hoursAllocated;
  }

  // Getter and Setter for taskStatus
  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  /**
   * Sets the current status of the task. Automatically updates the start or completion time if
   * applicable.
   *
   * @param taskStatus The new status of the task.
   */
  public void setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;

    if (taskStatus == TaskStatus.IN_PROGRESS) {
      this.timeStarted = LocalTime.now();
    } else if (taskStatus == TaskStatus.COMPLETED) {
      this.timeCompleted = LocalTime.now();
    }
  }

  // getters for TimeStarted and TimeCompleted
  public LocalTime getTimeStarted() {
    return timeStarted;
  }

  public LocalTime getTimeCompleted() {
    return timeCompleted;
  }

  // Getter and Setter for TaskDate
  public LocalDate getTaskDate() {
    return taskDate;
  }

  public void setTaskDate(LocalDate taskDate) {
    this.taskDate = taskDate;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public String getTaskType() {
    return taskType;
  }

  // Getter and Setter for Task id
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getTaskId() {
    return taskId;
  }

  // Method to check if the task is complete, return true when task is completed
  public boolean isComplete() {
    return taskStatus == TaskStatus.COMPLETED;
  }

  // Method to check if the task is in progress
  public boolean isInProgress() {
    return taskStatus == TaskStatus.IN_PROGRESS;
  }

  // Method to calculate time variance

  /**
   * Calculates the time variance between the start and completion times.
   *
   * @return A string representation of the duration in hours and minutes, or a message if the start
   *     or completion time is not set.
   */
  public String timeVariance() {
    if (timeStarted != null && timeCompleted != null) {
      Duration duration = Duration.between(timeStarted, timeCompleted);
      long hours = duration.toHours();
      long minutes = duration.toMinutes() % 60;
      return hours + " ώρες και " + minutes + " λεπτά";
    } else {
      return "Η ώρα έναρξης ή ολοκλήρωσης δεν έχει οριστεί";
    }
  }

  // toString method
  @Override
  public String toString() {
    return "Όνομα Task: "
        + taskName
        + ",\nΤύπος Task: "
        + taskType
        + ",\nΣυνιστώμενες ώρες μελέτης: "
        + hoursAllocated
        + ", Κατάσταση Εργασίας: "
        + taskStatus;
  }
}
