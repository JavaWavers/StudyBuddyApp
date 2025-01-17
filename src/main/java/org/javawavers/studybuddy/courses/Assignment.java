package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The {@code Assignment} class represents an assignment that is part of a course.
 * It extends the {@code SubjectElement} class and includes additional fields such as
 * estimated hours to complete, difficulty, and completed date.
 *This class provides functionality to validate estimated hours, set and get assignment
 * details, and check if the deadline is approaching soon.
 *Features:
 *Constructors to initialize assignments with or without parameters.
 *Deadline validation based on estimated hours and maximum hours allowed.
 *Deadline warning messages for approaching deadlines.
 */
public class Assignment extends SubjectElement {
  private int estimateHours;
  private LocalDate completedDate;
  private int difficulty;

  // different types of constructors
  /**
   * Default constructor. Creates an empty assignment with no title or deadline.
   */
  public Assignment() {
    super(null, null);
  }

  /**
   * Parameterized constructor. Creates an assignment with specified details.
   *
   * @param title The title of the assignment.
   * @param deadline The deadline for the assignment.
   * @param estimateHours The estimated hours needed to complete the assignment.
   * @param difficulty The difficulty level of the assignment.
   * @throws IllegalArgumentException if the estimated hours exceed the maximum allowed hours
   *         based on the deadline.
   */
  public Assignment(String title, LocalDate deadline, int estimateHours, int difficulty) {
    super(deadline, title);
    if (validEstHours(estimateHours, deadLine)) {
      this.estimateHours = estimateHours;
    } else {
      System.out.println("Οι ώρες πρέπει να είναι μικρότερες από:" + maxHours);
      throw new IllegalArgumentException(
              "Invalid estimate hours: Hours must fit within the given deadline.");
    }
    this.difficulty = difficulty;
  }

  private static int maxHours;

  /**
   * Validates the estimated hours for the assignment.
   *
   * @param estimateHours The estimated hours to complete the assignment.
   * @param deadLine The deadline for the assignment.
   * @return {@code true} if the estimated hours are less than the maximum allowed,
   *     otherwise {@code false}.
   */
  private static boolean validEstHours(int estimateHours, LocalDate deadLine) {
    LocalDate today = LocalDate.now();
    maxHours = (int) ChronoUnit.DAYS.between(today, deadLine) * 14;
    return (estimateHours < maxHours);
  }

  // getters
  /**
   * Returns the title of the assignment.
   *
   * @return The title of the assignment.
   */
  public String getTitle() {
    return super.getName();
  }

  /**
   * Returns the date the assignment was completed.
   *
   * @return The completed date of the assignment, or {@code null} if not set.
   */
  public LocalDate getCompletedDate() {
    return completedDate;
  }

  public LocalDate getDeadline() {
    return super.getDate();
  }

  public int getEstimateHours() {
    return estimateHours;
  }


  // setters
  /**
   * Sets the title of the assignment.
   *
   * @param title The new title of the assignment.
   */
  public void setTitle(String title) {
    super.setName(title);
  }

  public void setCompletedDate(LocalDate completedDate) {
    this.completedDate = completedDate;
  }

  /**
   * Sets the deadline for the assignment.
   *
   * @param deadLine The new deadline for the assignment.
   */
  public void setDeadline(LocalDate deadLine) {
    super.setDate(deadLine);
  }

  public void setEstimateHours(int estimateHours) {
    this.estimateHours = estimateHours;
  }

  @Override
  public void isDeadLineSoonMessage() {
    // Calculate the remaining days until the exam date
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDeadline());

    if (remainingDays <= 10 && remainingDays > 0) {
      System.out.println("Απομένουν μόνο " + remainingDays + " ημέρες μέχρι την εξέταση!");
    } else if (remainingDays == 0) {
      System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
    } else if (remainingDays < 0) {
      System.out.println("Η εξέταση έχει ήδη περάσει.");
    }
  }

  @Override
  // returns true if the deadline is in less than 5 days
  public boolean isDeadLineSoon() {
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDeadline());
    return remainingDays <= 5;
  }
}
