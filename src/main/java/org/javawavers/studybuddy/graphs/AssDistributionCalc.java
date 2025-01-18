package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import java.util.HashMap;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.ScheduledTask;

/**
 * The {@code AssDistributionCalc} class is responsible for calculating the distribution of tasks
 * across assignments. It provides utilities to compute and retrieve the percentage of tasks
 * assigned to each assignment.
 */
public class AssDistributionCalc {
  // Stores the percentage distribution of tasks for each Assignment
  private static HashMap<String, Double> assDist;

  /**
   * Calculates and returns the distribution of tasks as a percentage for each assignment. return A
   * HashMap where the key is the assignment name, and the value is the percentage of tasks.
   */
  public static HashMap<String, Double> assignmentsDistribution() {
    assDist = new HashMap<>();
    percentage();
    return assDist;
  }

  /**
   * Calculates the percentage of tasks assigned to each assignment. The method processes the user's
   * task data by iterating through the weeks, days, and scheduled tasks. It counts the tasks
   * assigned to each assignment and computes their percentage relative to the total number of
   * tasks. The calculated percentages are stored in the {@code assDist} map.
   */
  private static void percentage() {
    ArrayList<Week> weekList = new ArrayList<>(staticUser.getTotalWeeks());
    ArrayList<Assignment> assList = new ArrayList<>(staticUser.getAssignments());
    int totalTasks = 0;
    HashMap<String, Integer> tasksPerAssignment = new HashMap<>();
    // if there are assignments in the list
    if (!assList.isEmpty()) {
      for (Assignment a : assList) {
        tasksPerAssignment.put(a.getName(), 0);
      }
      for (Week w : weekList) {
        for (Day d : w.getDaysOfWeek()) {
          for (ScheduledTask s : d.getTodayTasks()) {
            String assName = s.getSubjectName();
            if (tasksPerAssignment.containsKey(assName)) {
              totalTasks++;
              tasksPerAssignment.put(assName, tasksPerAssignment.get(assName) + 1);
            }
          }
        }
        for (String s : tasksPerAssignment.keySet()) {
          assDist.put(s, tasksPerAssignment.get(s) / (double) totalTasks);
        }
      }
      // if there are not any assignments
    } else {
      assDist.put("none", 100.0);
    }
  }
}
