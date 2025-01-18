package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

public class ScoringTest {
  public static void main(String[] args) {
    // Example tasks
    List<Task> taskList = new ArrayList<>();
    taskList.add(new Task("Math", 1)); // Study Math
    taskList.add(new Task("Science", 1)); // Study Science
    taskList.add(new Task("Math", 2)); // Revise Math

    // Example schedule
    int[][] schedule = {
      {0, 1, -1}, // Day 1: Study Math, Day 2: Study Science
      {0, -1, -1}, // Day 1: Study Math again (penalty), Day 2: Nothing
      {-1, 1, -1} // Day 2: Study Science again (no penalty for repetition across rows)
    };

    try {
      double score = Scoring.calculateScore(taskList, schedule, 3);
      System.out.println("Final Score: " + score);
    } catch (IllegalArgumentException e) {
      System.err.println("Error calculating score: " + e.getMessage());
    }
  }
}
