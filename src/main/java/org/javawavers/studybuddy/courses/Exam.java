package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The {@code Exam} class represents an examination event for a specific course or subject.
 * It extends the {@code SubjectElement} class and includes additional fields such as
 * the number of pages to study, revision per pages, and time required per 20 slides.
 * This class provides functionality to manage exam details, calculate study time,
 * and check for upcoming deadlines.
 *Features:
 * Multiple constructors for different initialization scenarios.
 * Methods to set and get exam details, including pages, revisions, and time per slides.
 * Warning messages and deadline checks for upcoming exams.
 */
public class Exam extends SubjectElement {

  private int pages;
  private int revisionPerPages;
  private double timePer20Slides;

  // Constructors for different versions of Exam class
  /**
   * Constructs an {@code Exam} object with a specified exam date and number of pages.
   *
   * @param examDate The date of the exam.
   * @param pages The number of pages to study for the exam.
   */
  public Exam(LocalDate examDate, int pages) {
    super(examDate, null);
    this.pages = pages;
  }

  /**
   * Constructs an {@code Exam} object with a subject, exam date, and number of pages.
   *
   * @param subject The subject associated with the exam.
   * @param examDate The date of the exam.
   * @param pages The number of pages to study for the exam.
   */
  public Exam(Subject subject, LocalDate examDate, int pages) {
    super(examDate, subject.getCourseName());
    this.pages = pages;
  }

  /**
   * Constructs an {@code Exam} object with a subject, exam date, pages, and name.
   *
   * @param subject The subject associated with the exam.
   * @param examDate The date of the exam.
   * @param pages The number of pages to study for the exam.
   * @param name The name of the exam.
   */
  public Exam(Subject subject, LocalDate examDate, int pages, String name) {
    super(examDate, subject.getCourseName());
    this.pages = pages;
    this.name = name;
  }

  /**
   * Constructs an {@code Exam} object with a subject, exam date, pages, name,
   * revisions per page, and time required per 20 slides.
   *
   * @param subject The subject associated with the exam.
   * @param examDate The date of the exam.
   * @param pages The number of pages to study for the exam.
   * @param name The name of the exam.
   * @param revisionPerPages The number of revisions required per page.
   * @param timePer20Slides The estimated time (in minutes) required for 20 slides.
   */
  public Exam(
      Subject subject,
      LocalDate examDate,
      int pages,
      String name,
      int revisionPerPages,
      int timePer20Slides) {
    super(examDate, subject.getCourseName());
    this.pages = pages;
    this.name = name;
    this.revisionPerPages = revisionPerPages;
    this.timePer20Slides = timePer20Slides;
  }

  /**
   * Constructs an {@code Exam} object with pages, revisions, exam date, and time for 20 slides.
   *
   * @param pages The number of pages to study for the exam.
   * @param revisionPerPages The number of revisions required per page.
   * @param examDate The date of the exam.
   * @param timePer20Slides The estimated time (in minutes) required for 20 slides.
   */
  public Exam(int pages, int revisionPerPages, LocalDate examDate, double timePer20Slides) {
    super(examDate, null);
    this.pages = pages;
    this.revisionPerPages = revisionPerPages;
    this.timePer20Slides = timePer20Slides;
  }

  // getters & setters

  /**
   * Sets the exam date.
   *
   * @param examDate The new exam date.
   */
  public void setExamDate(LocalDate examDate) {
    super.setDate(examDate);
  }

  /**
   * Returns the exam date.
   *
   * @return The exam date.
   */
  public LocalDate getExamDate() {
    return super.getDate();
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public int getRevisionPerPages() {
    return revisionPerPages;
  }

  public void setRevisionPerPages(int revisionPerPages) {
    this.revisionPerPages = revisionPerPages;
  }

  public double getTimePer20Slides() {
    return timePer20Slides;
  }

  public void setTimePer20Slides(double timePer20Slides) {
    this.timePer20Slides = timePer20Slides;
  }

  // toString (we can use it with ui)
  @Override
  public String toString() {
    StringBuilder builder =
        new StringBuilder(); // StringBuilder provides efficiency and flexibility
    builder.append("Εξέταση{Όνομα Μαθήματος: '").append(super.getName()).append("'");

    if (getExamDate() != null) {
      builder.append("\nΗμερομηνία εξέτασης: '").append(getExamDate()).append("'");
    }

    if (pages != 0) {
      builder.append("\nΣελίδες: '").append(pages).append("'");
    }

    if (revisionPerPages != 0) {
      builder.append("\nΕπανάληψη ανά: '").append(revisionPerPages).append("σελίδες'");
    }

    if (timePer20Slides != 0.0) {
      builder
          .append("\nΑπαιτούμενα λεπτά για 20 διαφάνειες: '")
          .append(timePer20Slides)
          .append("'");
    }

    builder.append("}");
    return builder.toString();
  }

  // Other Methods
  /*
   * Returns the Total Time Required for studying
   * based on pages and minutes per 20 pages
   */
  @Override
  public void isDeadLineSoonMessage() {
    // Calculate the remaining days until the exam date
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getExamDate());

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
    long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getExamDate());
    return remainingDays <= 5;
  }
}
