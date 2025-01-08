package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;

public class Exam {
    private LocalDate examDate;
    private int pages;

    // Constructor
    public Exam(LocalDate examDate, int pages) {
        this.examDate = examDate;
        this.pages = pages;
    }

    // Getters and setters
    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
