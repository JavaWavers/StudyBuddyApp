
package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Exam extends SubjectElement {

    private int pages;
    private int revisionPerXPages;
    private double timePer20Slides;

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

    public Exam(Subject subject, LocalDate examDate, int pages, String name, int revisionPerXPages,
                int timePer20Slides) {
        super(examDate, subject.getCourseName());
        this.pages = pages;
        this.name = name;
        this.revisionPerXPages = revisionPerXPages;
        this.timePer20Slides = timePer20Slides;

    }

    public Exam(int pages, int revisionPerXPages, LocalDate examDate, double timePer20Slides) {
        super(examDate, null);
        this.pages = pages;
        this.revisionPerXPages = revisionPerXPages;
        this.timePer20Slides = timePer20Slides;
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

    public double getTimePer20Slides() {
        return timePer20Slides;
    }

    public void setTimePer20Slides(double timePer20Slides) {
        this.timePer20Slides = timePer20Slides;
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

        if (timePer20Slides != 0.0) {
            builder.append("\nΑπαιτούμενα λεπτά για 20 διαφάνειες: '")
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
