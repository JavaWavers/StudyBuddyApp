package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Subject;

/**
 * The {@code CalculativeAlgorithm} class is responsible for calculating and dividing
 * the total study time into tasks or groups that can be distributed equally over a
 * period of weeks until the due date of an exam, revision, or assignment.
 */
public class CalculativeAlgorithm {

  private static double pagesPerMin = 0.2;

  // setters & getters

  /**
   * Sets the user's study speed in pages per minute.
   *
   * @param timePer20Slides the time (in minutes) it takes for the user to study 20 slides.
   */
  public static void setPagesPerMin(double timePer20Slides) {
    if (timePer20Slides <= 0) {
      throw new IllegalArgumentException("Time per 20 slides must be greater than zero.");
    }
    pagesPerMin = 20 / timePer20Slides;
  }

  /**
   * Calculates the total studying time in hours for a subject.
   *
   * @param s the {@code Subject} for which the total study time is calculated.
   * @return the total study time in hours.
   */
  public static double totalStudyingTime(Subject s) {
    if (s == null || s.getExams().isEmpty()) {
      throw new IllegalArgumentException("Subject or its exams list cannot be null or empty.");
    }

    double pages = s.getExams().get(0).getPages();
    double difficulty = s.getDifficultyLevel();

    if (pages <= 0 || difficulty <= 0) {
      throw new IllegalArgumentException("Pages and difficulty level must be greater than zero.");
    }

    return (pages * difficulty) / (pagesPerMin * 60);
  }

  /**
   * Calculates the total studying time required for a specific subject.
   *
   * @param s the {@code Subject} for which the total study time is calculated.
   * @return the total study time in hours.
   */
  public static int studyingTasks(Subject s) {
    if (s == null) {
      throw new IllegalArgumentException("Subject cannot be null.");
    }

    double totalTimeWeek = totalStudyingTime(s);
    return numberOfScheduledTask(totalTimeWeek);
  }

  /**
   * Calculates the number of the tasks that have to be scheduled during the week.
   *
   * @param total the total study time in hours.
   * @return the number of study tasks.
   */
  public static int numberOfScheduledTask(double total) {
    if (total <= 0) {
      throw new IllegalArgumentException("Total study time must be greater than zero.");
    }

    return (int) Math.round(total / 2);
  }
}