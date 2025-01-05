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

public class ExamDates {
    private String subjName;
    private LocalDate examDate;

    // Constructor
    public ExamDates(Subject subj, LocalDate examDate) {
        this.subjName = subj.getCourseName();
        this.examDate = examDate;
    }

    // getters
    public String getSubName() {
        return subjName;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    /*
     * Sorts the list of ExamDates by the exam date.
     * The list will be sorted in ascending order,
     * with the earliest exam date at the beginning and the latest at the end.
     */
    public static List<ExamDates> sortExam(List<ExamDates> exams) {

        // Sort the list of exams by exam date in ascending order
        exams.sort(Comparator.comparing(ExamDates::getExamDate));
        return exams;
    }

    // calculates in how many days the last exam is due to
    public static int lastExIsDue(List<ExamDates> exams) {
        // Ensure the list is not empty
        if (exams == null || exams.isEmpty()) {
            throw new IllegalArgumentException("The exams list is empty or null");
        }

        // Get the current date
        LocalDate today = LocalDate.now();

        // Get the date of the last exam (last element of the sorted list)
        LocalDate lastExamDate = exams.getLast().getExamDate();

        // Return the calculated days
        return (int) ChronoUnit.DAYS.between(today, lastExamDate);
    }

    // The method accepts the task that needs to be registered in the results table.
// From this task, we retrieve its name and check, for each ExamDates object,
// if the day when we want to assign the task is before the exam date.
// If this condition is met, the method returns true; otherwise, it returns false.
// It accepts the task, the number of days until the exam, AND the list of exam dates.

    public static boolean checkExamDate(Task task, int day, List<ExamDates> exams) {

        String subj = task.getSubject(); // keeps the name of the subject in order to find the exam date
        LocalDate exD = null;// keeps the exam date
        // Iterate through the list of ExamDates to find the exam for the task's subject
        for (ExamDates examDate : exams) {
            if (subj.equals(examDate.getSubName())) {
                exD = examDate.getExamDate();
            }
        }

        // Ημερομηνία που θέλουμε να βάλουμε το task
        LocalDate dateAssigned = LocalDate.now().plusDays(day);
        return dateAssigned.isBefore(exD);

    }

    public static LocalDate getExDate(Task task, List<ExamDates> exams) {
        String subj = task.getSubject(); // keeps the name of the subject in order to find the exam date
        LocalDate exD = null;// keeps the exam date
        // Iterate through the list of ExamDates to find the exam for the task's subject
        for (ExamDates examDate : exams) {
            if (subj.equals(examDate.getSubName())) {
                exD = examDate.getExamDate();
            }
        }
        return exD;
    }

}
