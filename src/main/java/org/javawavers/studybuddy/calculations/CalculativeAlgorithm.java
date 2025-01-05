package org.javawavers.studybuddy.calculations;

/*This class is responsible for calculating and dividing the total studying
 * time into groups (referred also as tasks) equally for each week till the
 * due day
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculativeAlgorithm {
    // The page number that a user is physically possible to study per min
    private static double pagesPerMin = 0.2;

    // setters & getters

    // setter for pages per minute
    public static void setPagesPerMin(double ppm) {
        pagesPerMin = ppm;
    }

    // getter for pages per minute
    public static double getPagesPerMin() {
        return pagesPerMin;
    }

    // calculates total studying time in hours
    public static double totalStudyingTime(Subject s) {
        // The total studying time required for a subject
        return (s.getExams().getFirst().getPages() * s.getDifficultyLevel()) / (pagesPerMin * 60);
    }

    // total studying tasks per week
    public static int studyingPerWeek(Subject s) {
        // calculates the remaining days until the exam day
        long weeksUntilExam = ChronoUnit.DAYS.between(LocalDate.now(), s.getExams().getFirst().getExamDate());
        // casting into double for calculating purposes, converts into weeks as an
        // integer number
        int weeks = (int) ((double) weeksUntilExam / 7);
        // calculating the total time needed to study each week, equally divided for
        // each
        // week
        // The calculation is
        // Studying hours per week
        double totalTimeWeek = totalStudyingTime(s) / weeks;
        return numberOfScheduledTask(totalTimeWeek);
    }

    /*
     * Calculates the number of the tasks that have to be scheduled during the week
     * The static method numberOfScheduledTask takes as an argument the total
     * studying time whether it refers to total studying time for the exam, for
     * revision
     * or for an assignment. Then the method divides it into studying groups of a
     * duration of two hours each
     */
    public static int numberOfScheduledTask(double total) {
        return (int) Math.round(total / 2);
    }

}
