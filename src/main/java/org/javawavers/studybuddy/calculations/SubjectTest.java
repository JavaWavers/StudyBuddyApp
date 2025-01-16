package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;

/**
 * The SubjectTest class represents a subject that a user is studying. It includes information
 * about the subject's name, difficulty range, total page number for study, and the exam date.
 * This class is used to store and retrieve information for a specific subject in the study scheduling system.
 */
public class SubjectTest {
  private String name;
  private double difficultyrange;
  private int totalPageNumber;
  private LocalDate examDate;

  /**
   * Constructs a new SubjectTest with the specified name, difficulty range,
   * total page number, and exam date.
   *
   * @param n The name of the subject.
   *
   * @param d The difficulty range of the subject.
   *
   * @param t The total number of pages for the subject.
   *
   * @param e The exam date for the subject.
   */
  public SubjectTest(String n, double d, int t, LocalDate e) {
    this.name = n;
    this.difficultyrange = d;
    this.totalPageNumber = t;
    this.examDate = e;
  }

  public int getNumberplate() {
    return totalPageNumber;
  }

  public double getDifficultyrange() {
    return difficultyrange;
  }

  public LocalDate getExamDate() {
    return examDate;
  }

  public String getName() {
    return name;
  }
}
