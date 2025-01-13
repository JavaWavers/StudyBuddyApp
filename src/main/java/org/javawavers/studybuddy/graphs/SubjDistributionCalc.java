package org.javawavers.studybuddy.graphs;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import java.util.HashMap;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;


public class SubjDistributionCalc {
  // Stores the percentage distribution of tasks for each subject
  private static HashMap<String, Double> subjectDist;
  /**
   * Calculates and returns the distribution of tasks as a percentage for each subject.
   * return A HashMap where the key is the subject name, and the value is the percentage of tasks.
  */
  public static HashMap<String, Double> subjectsDistribution() {
    subjectDist = new HashMap<>();
    percentage();
    return subjectDist;
  }

    /**
     * Calculates the percentage of tasks assigned to each subject.
     * The method processes the user's task data by iterating through the weeks,
     * days, and tasks. It counts the tasks assigned to each subject and computes
     * their percentage relative to the total number of tasks.
     * The calculated percentages are stored in the {@code subjectDistr} map.
     */
  private static void percentage() {
    ArrayList<Week> weekList = new ArrayList<>(staticUser.getTotalWeeks());
    ArrayList<Subject> subjList = new ArrayList<>(staticUser.getSubjects());
    int totalTasks = 0;
    HashMap<String, Integer> tasksPerSubject = new HashMap<>();
    for (Subject s : subjList) {
      tasksPerSubject.put(s.getCourseName(), 0);
    }
    for (Week w : weekList) {
      for (Day d : w.getDaysOfWeek()) {
        for (ScheduledTask s : d.getTodayTasks()) {
          String subjName = s.getSubjectName();
          if (tasksPerSubject.containsKey(subjName)) {
            totalTasks++;
            tasksPerSubject.put(subjName, tasksPerSubject.get(subjName) + 1);
          }
        }
      }
    }
    for (String s : tasksPerSubject.keySet()) {
      subjectDist.put(s, tasksPerSubject.get(s) / (double) totalTasks);
    }
  }
}
