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
        LocalDate lastExamDate = exams.get(exams.size() - 1).getExamDate();

        // Calculate the number of days between today and the last exam date
        int daysUntilLastExam = (int) ChronoUnit.DAYS.between(today, lastExamDate);

        // Return the calculated days
        return daysUntilLastExam;
    }

    // Η μέθοδος δέχεται το τασκ που θέλει να καταχωρηθεί στον πίνακα με τα
    // αποτελέσματα.
    // Από αυτό το τασκ παίρνουμε το όνομά του και ελέγχουμε για κάθε αντικείμενο
    // ExamDates αν η ημέρα που θέλουμε να βάλουμε το τασκ είναι πριν την ημερομηνία
    // του
    // του exam
    // Αν όντως ισχύει η μέθοδος επιστρέφει true αλλιώς επιστρέφει false
    // δέχεται το τασκ και το σε πόσες μέρες θέλουμε να βάλουμε το ejam ΚΑΙ δέχετα
    // αι τη λ΄στα με τις
    // ημερομηνίες των exam
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
        if (dateAssigned.isBefore(exD)) {
            return true;
        } else {
            return false;
        }

    }

    public static LocalDate getExDate(Task task, int day, List<ExamDates> exams) {
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
