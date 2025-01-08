package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;

public abstract class SubjectElement {
    protected String name;
    protected LocalDate deadline;

    public abstract long getRemainingTime();

    public abstract long getTotalRequiredTime();
}
