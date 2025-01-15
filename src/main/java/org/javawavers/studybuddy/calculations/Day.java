package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The {@code Day} class represents a single day in the study schedule.
 * It holds a list of tasks that are scheduled to be completed on that day.
 *
 * <p>This class provides methods to:
 * Retrieve the full list of scheduled tasks for the day.</li>
 * Access specific tasks from the day's schedule by index.</li>
 *
 * <p>Each day can include multiple {@link ScheduledTask} objects that define the details
 * of the tasks assigned for that day.
 */
public class Day {
  public List<ScheduledTask> todayTasks;

  /**
   * Constructs a {@code Day} object with an empty list of scheduled tasks.
   */
  public Day() {
    todayTasks = new ArrayList<>();
  }

  /**
   * Retrieves the list of tasks scheduled for the day.
   *
   * @return a {@code List} of {@link ScheduledTask} objects for the current day.
   */
  public List<ScheduledTask> getTodayTasks() {
    return todayTasks;
  }

  /**
   * Retrieves a specific task from the list of tasks scheduled for the day.
   *
   * @param index the position of the task in the list (0-based index).
   * @return the {@link ScheduledTask} object at the specified index.
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   ({@code index < 0 || index >= todayTasks.size()}).
   */
  public ScheduledTask getTodayScheduledTask(int index) {
    return todayTasks.get(index);
  }
}
