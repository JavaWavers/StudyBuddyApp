package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import java.util.HashMap;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;

public class SubjDistributionCalc {
  public static HashMap<String, Double> subjectsDistribution() {
    HashMap<String, Double> subjectDistr = new HashMap<>();
    return subjectDistr;
  }

  private static void percentage() {
    ArrayList<Week> weekList = new ArrayList<>(staticUser.getTotalWeeks());
    int totalTasks = 0;
    HashMap<String, Integer> tasksPerSubject = new HashMap<>();
    for (Week w : weekList) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {}
      }
    }
  }
}
