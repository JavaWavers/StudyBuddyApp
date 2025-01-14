package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.HashMap;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

public class LineChartCalc {

  public static HashMap<Integer, Integer> weekProgress() {
    HashMap<Integer, Integer> progress = new HashMap<>();
    staticUser.calculateCurrentWeek();
    Week currentWeek = staticUser.getCurrentWeek();
    if (currentWeek != null) {
      int dayNumb = 1;
      for (Day d : currentWeek.getDaysOfWeek()) {
        int totalHours = 0;
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
            totalHours += s.getHoursAllocated();
          }
        }
        progress.put(dayNumb, totalHours);
      }
    } else {
      for (int day = 1; day < 8; day++) {
        progress.put(day, 0);
      }
    }
    return progress;
  }
}
