package org.javawavers.studybuddy.calculations;

/*This class is responsible for calculating and dividing the total studying
 * time into groups (referred also as tasks) equally for each week till the
 * due day
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculativeAlgorithm {
    // The page number that a user is physically possible to study per min
    private static double pagespermin = 0.2;

    // The total studying time required for a subject
    private static double totaltime;

    // Studying hours per week
    private static double totaltimeweek;
    // setters & getters

    // setter for pages per minute
    public static void setPagespermin(double ppm) {
        pagespermin = ppm;
    }

    // getter for pages per minute
    public static double getPagespermin() {
        return pagespermin;
    }

    // calculates total studying time in hours
    public static double totalStudyingTime(Subject s) {
        totaltime = (s.getExams().get(0).getPages() * s.getDifficultyLevel()) / (pagespermin * 60);
        return totaltime;
    }

    // total studying tasks per week
    public static int studyingPerweek(Subject s) {
        // calculates the remaining days until the exam day
        long weeksuntilexam = ChronoUnit.DAYS.between(LocalDate.now(), s.getExams().get(0).getExamDate());
        // casting into double for calculating purposes, converts into weeks as an
        // integer number
        int weeks = (int) ((double) weeksuntilexam / 7);
        // calculating the total time needed to study each week, equally divided for
        // each
        // week
        // The calculation is
        totaltimeweek = totalStudyingTime(s) / weeks;
        return numberOfScheduledTask(totaltimeweek);
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
