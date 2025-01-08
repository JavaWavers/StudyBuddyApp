package org.javawavers.studybuddy.courses;

import java.time.LocalDate;

public abstract class SubjectElement {
    protected String subjectName;
    protected LocalDate deadline;

    //Constructors
    public SubjectElement(Subject subject) {
        this.subjectName = subject.getCourseName();
    }

    public SubjectElement(Subject subject, LocalDate deadline) {
        this.subjectName = subject.getCourseName();
        this.deadline = deadline;
    }

    //getters
    public String getSubjectName() {
        return subjectName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    //other methods
    public abstract long getRemainingDays();
    public abstract String getTotalRequiredTime();
}
