package org.javawavers.studybuddy.database;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

public class DataDashboard {
  /*δέχεται τη συνολική λίστα με τις βδομάδες
   *μολισ ενα τασκ γινεται completed ανανεώνεται η dashboard
   * παίρνει όλα τα τασκ από όλες τις εβδομάδες και βγάζει το ποσοστό
   * completed task * 100 / συνολικά τασκ και τα εμφανίζει
   */
  private static ArrayList<Week> totalWeeks;

  private static void setTotalWeeks() {
    totalWeeks = new ArrayList<>(staticUser.getTotalWeeks());
  }

  public static double percentageCalculatorGoals() {
    setTotalWeeks();
    int totalsum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          totalsum++;
          if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
            sum++;
          }
        }
      }
    }
    if (totalsum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalsum) * 100;
    }
  }

  public static double percentageCalculatorStudying() {
    setTotalWeeks();
    int totalsum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Διάβασμα")) {
            totalsum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalsum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalsum) * 100;
    }
  }

  public static double percentageCalculatorAssignments() {
    setTotalWeeks();
    int totalsum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Εργασία")) {
            totalsum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalsum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalsum) * 100;
    }
  }

  public static double percentageCalculatorRevision() {
    setTotalWeeks();
    int totalsum = 0;
    int sum = 0;
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          if (s.getTaskType().equals("Επανάληψη")) {
            totalsum++;
            if (s.getTaskStatus() == ScheduledTask.TaskStatus.COMPLETED) {
              sum++;
            }
          }
        }
      }
    }
    if (totalsum == 0) {
      return 0.0;
    } else {
      return ((double) sum / totalsum) * 100;
    }
  }
}
