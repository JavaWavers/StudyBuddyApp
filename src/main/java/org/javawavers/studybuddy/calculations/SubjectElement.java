package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class SubjectElement {
    protected String name;
    protected LocalDate deadLine;

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

    // returns true if the deadline is in less than 5 days
    public boolean isDeadLineSoon() {
        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDate());
        return remainingDays <= 5;
    }
}
