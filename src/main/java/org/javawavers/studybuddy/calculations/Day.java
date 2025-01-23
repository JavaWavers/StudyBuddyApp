package org.javawavers.studybuddy.calculations;



import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.ScheduledTask;


/**
 * Represents a day with a list of scheduled tasks.
 * Provides methods to manage and retrieve tasks for the day.
 */
public class Day {
  public List<ScheduledTask> todayTasks;

  /**
   * List of scheduled tasks for the day.
   */
  public Day(List<ScheduledTask> todayTasks) {
    this.todayTasks = todayTasks;
  }

  /**
   * Constructs a Day object with an empty task list.
   */
  public Day() {
    todayTasks = new ArrayList<>();
  }

  /**
   * Returns the entire list of scheduled tasks for the day.
   *
   * @return the list of scheduled tasks.
   */
  public List<ScheduledTask> getTodayTasks() {
    return todayTasks;
  }

  /**
   * Returns a specific scheduled task by index.
   *
   * @param index the index of the task in the list.
   * @return the scheduled task at the specified index.
   * @throws IndexOutOfBoundsException if the index is out of range.
   */
  public ScheduledTask getTodayScheduledTask(int index) {
    return todayTasks.get(index);
  }

  /**
   * Returns all the tasks for the day (same as {@link #getTodayTasks()}).
   *
   * @return the list of all tasks for the day.
   */
  public List<ScheduledTask> getAllTasks() {
    return todayTasks;
  }
}
