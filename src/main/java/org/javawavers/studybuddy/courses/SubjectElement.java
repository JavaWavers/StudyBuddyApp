package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a general element related to a subject, such as an assignment or exam. Provides
 * functionality to manage deadlines and calculate the time remaining until the deadline.
 */
public abstract class SubjectElement {
  protected String name;
  protected LocalDate deadLine;

  /**
   * Constructs a SubjectElement with the given deadline and name.
   *
   * @param deadLine the deadline for the subject element
   * @param name the name of the subject element
   */
  public SubjectElement(LocalDate deadLine, String name) {
    this.deadLine = deadLine;
    this.name = name;
  }

  public LocalDate getDate() {
    return deadLine;
  }

  public void setDate(LocalDate deadLine) {
    this.deadLine = deadLine;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Prints a message to indicate how soon the deadline is, based on the current date. - If the
   * deadline is today, prints "The exam is today!" - If the deadline is within 10 days, prints the
   * number of days remaining. - If the deadline has passed, prints "The exam has already passed."
   */
  public void isDeadLineSoonMessage() {
    // Calculate the remaining days until the exam date
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDate());

    if (remainingDays <= 10 && remainingDays > 0) {
      System.out.println("Απομένουν μόνο " + remainingDays + " ημέρες μέχρι την εξέταση!");
    } else if (remainingDays == 0) {
      System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
    } else if (remainingDays < 0) {
      System.out.println("Η εξέταση έχει ήδη περάσει.");
    }
  }

  /**
   * Checks if the deadline is within the next 5 days (inclusive).
   *
   * @return {@code true} if the deadline is within 5 days, otherwise {@code false}
   */
  public boolean isDeadLineSoon() {
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDate());
    return remainingDays <= 5;
  }
}
