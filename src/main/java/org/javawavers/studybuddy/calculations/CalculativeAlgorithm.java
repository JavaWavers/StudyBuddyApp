package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Subject;

/**
 * The {@code CalculativeAlgorithm} class is responsible for calculating and dividing the total
 * study time into tasks or groups that can be distributed equally over a period of weeks until the
 * due date of an exam, revision, or assignment.
 *
 * <p>This class includes methods to:
 * <li>Set and retrieve the study speed in pages per minute.
 * <li>Calculate the total study time required for a subject.
 * <li>Determine the number of study tasks required per week.
 * <li>Divide the total study time into tasks of two-hour durations. The class is designed to work
 *     with the {@code Subject} class to calculate study time based on the subject's difficulty
 *     level, number of pages, and the user's speed in studying.
 */
public class CalculativeAlgorithm {
  /**
   * The number of pages a user can study per minute. This value is calculated based on the user's
   * input of how much time it takes to study 20 slides.
   */
  private static double pagesPerMin = 0.2;

  // setters & getters

  /**
   * Sets the user's study speed in pages per minute.
   *
   * <p>This method takes the time (in minutes) required to study 20 slides as input and calculates
   * the pages per minute. The result is stored in the {@code pagesPerMin} variable.
   *
   * @param timePer20Slides the time (in minutes) it takes for the user to study 20 slides.
   */
  public static void setPagesPerMin(double timePer20Slides) {

    pagesPerMin = 20 / timePer20Slides;
  }

  /**
   * Retrieves the user's study speed in pages per minute.
   *
   * @return the current value of {@code pagesPerMin}.
   */
  public static double getPagesPerMin() {
    return pagesPerMin;
  }

  // calculates total studying time in hours
  public static double totalStudyingTime(Subject s) {
    // The total studying time required for a subject
    return (s.getExams().get(0).getPages() * s.getDifficultyLevel()) / (pagesPerMin * 60);
  }

  /**
   * Calculates the total studying time required for a specific subject.
   *
   * <p>The total study time is determined by considering the number of pages in the subject's exam,
   * the subject's difficulty level, and the user's study speed in pages per minute. The result is
   * returned in hours.
   *
   * @param s the {@code Subject} for which the total study time is calculated.
   * @return the total study time in hours.
   */
  public static int studyingTasks(Subject s) {
    double totalTimeWeek = totalStudyingTime(s);
    return numberOfScheduledTask(totalTimeWeek);
  }

  /**
   * Calculates the number of the tasks that have to be scheduled during the week The static method
   * numberOfScheduledTask takes as an argument the total studying time whether it refers to total
   * studying time for the exam, for revision or for an assignment. Then the method divides it into
   * studying groups of a duration of two hours each
   */
  public static int numberOfScheduledTask(double total) {
    return (int) Math.round(total / 2);
  }
}
