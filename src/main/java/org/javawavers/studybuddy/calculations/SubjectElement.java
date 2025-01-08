package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Exam extends SubjectElement {

    private int pages;
    private int revisionPerXPages;
    private double slidesPerMin;

    // Constructors for different versions of Exam class
    public Exam(LocalDate examDate, int pages) {
        super(examDate, null);
        this.pages = pages;
    }

    public Exam(Subject subject, LocalDate examDate, int pages) {
        super(examDate, subject.getCourseName());
        this.pages = pages;
    }

    public Exam(Subject subject, LocalDate examDate, int pages, String name) {
        super(examDate, subject.getCourseName());
        this.pages = pages;
        this.name = name;

    }

    // getters & setters
    // getSubjectName does not need to be overridden
    public void setExamDate(LocalDate examDate) {
        super.setDate(examDate);
    }

    public LocalDate getExamDate() {
        return super.getDate();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRevisionPerXPages() {
        return revisionPerXPages;
    }

    public void setRevisionPerXPages(int revisionPerXPages) {
        this.revisionPerXPages = revisionPerXPages;
    }

    public double getSlidesPerMin() {
        return slidesPerMin;
    }

    public void setSlidesPerMin(double slidesPerMin) {
        this.slidesPerMin = slidesPerMin;
    }

    // toString (we can use it with ui)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(); // StringBuilder provides efficiency and flexibility
        builder.append("Εξέταση{Όνομα Μαθήματος: '").append(super.getName()).append("'");

        if (getExamDate() != null) {
            builder.append("\nΗμερομηνία εξέτασης: '").append(getExamDate()).append("'");
        }

        if (pages != 0) {
            builder.append("\nΣελίδες: '")
                    .append(pages)
                    .append("'");
        }

        if (revisionPerXPages != 0) {
            builder.append("\nΕπανάληψη ανά: '")
                    .append(revisionPerXPages)
                    .append("σελίδες'");
        }

        if (slidesPerMin != 0.0) {
            builder.append("\nΑπαιτούμενα λεπτά για 20 διαφάνειες: '")
                    .append(slidesPerMin)
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
