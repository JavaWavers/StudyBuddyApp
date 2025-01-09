package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Exam extends SubjectElement {

    private int pages;
    private int revisionPerXPages;
    private double minutesPer20Slides;

    //Constructors for different versions of Exam class
    public Exam(Subject subject) { //inherits SubjectElement's Constructor
        super(subject);
    }

    public Exam(Subject subject, LocalDate deadline) {
        super(subject);
        this.deadline = LocalDate.now(); //initialise with today's date
    }

    public Exam(Subject subject, LocalDate deadline, int revisionPerXPages, double minutesPer20Slides) {
        super(subject);
        this.deadline = LocalDate.now();
        this.revisionPerXPages = revisionPerXPages;
        if (minutesPer20Slides >= 0) {
            if (minutesPer20Slides <= 600) {
                this.minutesPer20Slides = minutesPer20Slides;
            } else throw new IllegalArgumentException("Τα λεπτά έχουν ανώτατο όριο τα 600");
        } else {
            throw new IllegalArgumentException("Τα λεπτά πρέπει να είναι μεγαλύτερα απο 0");
        }
    }

    public Exam(Subject subject, LocalDate deadline, int pages, int revisionPerXPages, double minutesPer20Slides) {
        super(subject);
        this.deadline = LocalDate.now();
        this.pages = pages;
        this.revisionPerXPages = revisionPerXPages;
        if (minutesPer20Slides >= 0) {
            if (minutesPer20Slides <= 600) {
                this.minutesPer20Slides = minutesPer20Slides;
            } else throw new IllegalArgumentException("Τα λεπτά έχουν ανώτατο όριο τα 600");
        } else {
            throw new IllegalArgumentException("Τα λεπτά πρέπει να είναι μεγαλύτερα απο 0");
        }
    }

    //getters & setters
    //getSubjectName does not need to be overridden
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public int getPages() { return pages; }

    public void setPages(int pages) { this.pages = pages; }

    public int getRevisionPerXPages() { return revisionPerXPages; }

    public void setRevisionPerXPages(int revisionPerXPages) {
        this.revisionPerXPages = revisionPerXPages;
    }

    public double getMinutesPer20Slides() { return minutesPer20Slides; }

    public void setMinutesPer20Slides(double minutesPer20Slides) {
        this.minutesPer20Slides = minutesPer20Slides;
    }

    //toString (we can use it with ui)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(); //StringBuilder provides efficiency and flexibility
        builder.append("Εξέταση{Όνομα Μαθήματος: '").
                append(subjectName).
                append("'");

        if(deadline != null) {
            builder.append("\nΗμερομηνία εξέτασης: '").
                    append(deadline).
                    append("'");
        }

        if(pages != 0) {
            builder.append("\nΣελίδες: '")
                    .append(pages)
                    .append("'");
        }

        if(revisionPerXPages != 0) {
            builder.append("\nΕπανάληψη ανά: '")
                    .append(revisionPerXPages)
                    .append("σελίδες'");
        }

        if(minutesPer20Slides != 0.0) {
            builder.append("\nΑπαιτούμενα λεπτά για 20 διαφάνειες: '")
                    .append(minutesPer20Slides)
                    .append("'");
        }

        builder.append("}");
        return builder.toString();
    }

    //Other Methods
    /*Returns the Total Time Required for studying
     based on pages and minutes per 20 pages
     */
    @Override
    public String getTotalRequiredTime() {
        double time = (pages*minutesPer20Slides)/20;

        if(time == 60) {
            return "1 ώρα";
        } else if(time > 60){
            int hours = (int) time / 60;
            int min = (int) time % 60;
            return hours + "ώρες και " + min + "λεπτά.";
        } else
            return time + "λεπτά.";
    } //need to re - calculate when the user inputs his study session

    //Returns the remaining days until the exam
    @Override
    public long getRemainingDays() {

        LocalDate today = LocalDate.now();

        if (today.isBefore(deadline)) {
            return ChronoUnit.DAYS.between(today, deadline);
        } else if (deadline.isEqual(today)) {
            System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
            return 0;
        } else {
            throw new IllegalArgumentException("Η ημερομηνία της εξέτασης έχει περάσει " +
                    "ή δεν καταχώρησες σωστή ημερομηνία.");
        }
    }

    public void isExamSoon() {
        long remainingDays = getRemainingDays();

        if(remainingDays <= 10 && remainingDays >0) {
            System.out.println("Απομένουν μόνο " + remainingDays + "μέχρι την εξέταση!");
        } else if(remainingDays == 0) {
            System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
        }
    }

}
