package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;

public class SubjectTest {
    private String name;
    private double difficultyrange;
    private int totalpagenumber;
    private LocalDate examDate;

    // sets the name the difficulty range the total number an the exam date
    public SubjectTest(String n, double d, int t, LocalDate e) {
        this.name = n;
        this.difficultyrange = d;
        this.totalpagenumber = t;
        this.examDate = e;
    }

    public int getTotalpagenumber() {
        return totalpagenumber;
    }

    public double getDifficultyrange() {
        return difficultyrange;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public String getName() {
        return name;
    }
}
