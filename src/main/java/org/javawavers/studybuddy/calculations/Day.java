package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.*;

public class Day {
  private List<ScheduledTask> todayTasks;
  public Day(List<ScheduledTask> todayTasks) {
    this.todayTasks = todayTasks;
  }

  // constructor
  public Day() {
    todayTasks = new ArrayList<>();
  }

  // returns the whole list
  public List<ScheduledTask> getTodayTasks() {
    return todayTasks;
  }

  // returns the specified task from the list
  public ScheduledTask getTodayScheduledTask(int index) {
    return todayTasks.get(index);
  }

}
