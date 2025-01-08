package org.javawavers.studybuddy.calculations;
/*
 * This class is used to store the exam date for each subject.
 * It holds information about the subject and the specific date of the exam.
 * Additionally, this class is responsible for controlling the distribution of tasks
 * for a subject up until the exam date. In other words, the exam date is the last day
 * by which all tasks must be completed and distributed.
 *
 * The class provides functionality to sort a list of exam dates in ascending order,
 * with the earliest exam date coming first and the latest coming last.
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class Dates {
    private String subjName;
    private LocalDate deadLine;

    // Constructor
    public Dates(Subject subj, LocalDate deadLine) {
        this.subjName = subj.getCourseName();
        this.deadLine = deadLine;
    }

    public Dates(String name, LocalDate deadLine) {
        this.subjName = name;
        this.deadLine = deadLine;
    }

    // getters
    public String getSubName() {
        return subjName;
    }

    public LocalDate getDate() {
        return deadLine;
    }

    /*
     * Sorts the list of ExamDates by the exam date.
     * The list will be sorted in ascending order,
     * with the earliest exam date at the beginning and the latest at the end.
     */
    public static List<Dates> sortList(List<Dates> list) {

        // Sort the list of exams by exam date in ascending order
        list.sort(Comparator.comparing(Dates::getDate));
        return list;
    }

    // calculates in how many days the last exam is due to
    public static int lastIsDue(List<Dates> exams, List<Dates> assignments) {
        if (exams.isEmpty() && !assignments.isEmpty()) {
            // Get the current date
            LocalDate today = LocalDate.now();
            // Get the date of the last assignment deadline (last element of the sorted
            // list)
            LocalDate lastAssDate = assignments.getLast().getDate();
            // Return the calculated days
            return (int) ChronoUnit.DAYS.between(today, lastAssDate);
        } else if (!exams.isEmpty() && assignments.isEmpty()) {
            // Get the current date
            LocalDate today = LocalDate.now();
            // Get the date of the last exam (last element of the sorted list)
            LocalDate lastExamDate = exams.getLast().getDate();
            // Return the calculated days
            return (int) ChronoUnit.DAYS.between(today, lastExamDate);
        } else if (!exams.isEmpty() && !assignments.isEmpty()) {
            // Get the current date
            LocalDate today = LocalDate.now();
            // Get the date of the last assignment deadline (last element of the sorted
            // list)
            LocalDate lastAssDate = assignments.getLast().getDate();
            // Get the date of the last exam (last element of the sorted list)
            LocalDate lastExamDate = exams.getLast().getDate();
            // return the deadline which is most faraway
            if (lastAssDate.isBefore(lastExamDate)) {
                return (int) ChronoUnit.DAYS.between(today, lastExamDate);
            } else {
                return (int) ChronoUnit.DAYS.between(today, lastAssDate);
            }

        } else {
            return 0;
        }
    }

    // The method accepts the task that needs to be registered in the results table.
    // From this task, we retrieve its name and check, for each ExamDates object,
    // if the day when we want to assign the task is before the exam date.
    // If this condition is met, the method returns true; otherwise, it returns
    // false.
    // It accepts the task, the number of days until the exam, AND the list of exam
    // dates.

    public static boolean checkDate(Task task, int day, List<Dates> elements) {
        String subj = task.getSubject(); // keeps the name of the subject in order to find the exam date
        LocalDate date = null; // keeps the exam date or the deadline

        // Iterate through the list of Dates to find the matching subject
        for (Dates e : elements) {
            if (subj.equals(e.getSubName())) {
                date = e.getDate();
                break; // Stop searching once we find the matching subject
            }
        }

        // Αν δεν βρεθεί η ημερομηνία, επιστρέφεται false
        if (date == null) {
            return false; // No valid date to compare
        }

        // Ημερομηνία που θέλουμε να βάλουμε το task
        LocalDate dateAssigned = LocalDate.now().plusDays(day);
        return dateAssigned.isBefore(date); // Returns true if the assigned date is before the exam/deadline
    }

    public static LocalDate getExDate(Task task, List<Dates> exams) {
        String subj = task.getSubject(); // keeps the name of the subject in order to find the exam date
        LocalDate exD = null;// keeps the exam date
        // Iterate through the list of exams to find the exam for the task's subject
        for (Dates examDate : exams) {
            if (subj.equals(examDate.getSubName())) {
                exD = examDate.getDate();
            }
        }
        return exD;
    }

    public static LocalDate getAssDate(Task task, List<Dates> assignments) {
        String subj = task.getSubject(); // keeps the name of the subject in order to find the deadline
        LocalDate assD = null;// keeps the exam deadline
        // Iterate through the list of assignments to find the one that matches for the
        // task's subject
        for (Dates assDate : assignments) {
            if (subj.equals(assDate.getSubName())) {
                assD = assDate.getDate();
            }
        }
        return assD;
    }

}