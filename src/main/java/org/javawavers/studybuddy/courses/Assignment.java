package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Assignment extends SubjectElement {
  private int estimateHours;
  private String description;
  private LocalDate completedDate;
  private int difficulty;

  // different types of constructors
  // constructor without any parameter
  public Assignment() {
    super(null, null);
  }

  // constructor with parameters
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

  private static boolean validEstHours(int estimateHours, LocalDate deadLine) {
    LocalDate today = LocalDate.now();
    maxHours = (int) ChronoUnit.DAYS.between(today, deadLine) * 14;
    return (estimateHours < maxHours);
  }

  // getters
  public String getTitle() {
    return super.getName();
  }

  public LocalDate getCompletedDate() {
    return completedDate;
  }

  public LocalDate getDeadline() {
    return super.getDate();
  }

  public int getEstimateHours() {
    return estimateHours;
  }

  public String getDescription() {
    return description;
  }

  // setters
  public void setTitle(String title) {
    super.setName(title);
  }

  public void setCompletedDate(LocalDate completedDate) {
    this.completedDate = completedDate;
  }

  public void setDeadline(LocalDate deadLine) {
    super.setDate(deadLine);
  }

  public void setEstimateHours(int estimateHours) {
    this.estimateHours = estimateHours;
  }

  public void setDescription(String description) {
    this.description = description;
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
