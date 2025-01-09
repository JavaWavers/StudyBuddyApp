package org.javawavers.studybuddy.calculations;
/*
 * This class distributes three kinds of tasks (studying -1, repetition -2,
 * assignment -3) into the available days randomly. The algorithm produces 50
 *  valid results,where the tasks are distributed into the available studying
 * hours per day differently (although there are chances for the same results).
 * Then, each result gains a score based on certain criteria, described in the
 * README file. The result with the higher score is considered the final result
 * and is given to the user as a recommended studying schedule.
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.javawavers.studybuddy.courses.Subject;

public class SimulateAnnealing {
    /*
     * The 12 rows represent the maximum tasks that can be assigned to a day,
     * which are 12 tasks of a duration of two hours each. The 8 columns represent
     * the 7 days of the week, starting with Monday. The first column of the table
     * is not used to prevent misinterpretation by the table pointer
     * with a value of zero.
     */

    private static List<Subject> subjects; // List for subjects
    private static List<Task> tasks; // List for each task that is connected with a subject
    private static List<Dates> exams; // List for each exam that is connected with a subject
    private static List<ScheduleResult> scheduleResults;// List for the valid schedule results
    private static List<Dates> assignments;// List for each assignment that is connected with a subject

    public SimulateAnnealing() {
        subjects = new ArrayList<>();
        tasks = new ArrayList<>();
        exams = new ArrayList<>();
        assignments = new ArrayList<>();
        scheduleResults = new ArrayList<>();
        System.out.println("okkkkkkkkkk");
    }

    // Add a new Subject
    public void addSubject(Subject subject) {
        subjects.add(subject);
        // Sets exams for the subject
        subExams(subject);
        subAssignment(subject);

        // Creates tasks for the subject
        subTasks(subject);
        System.out.println("Okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");

    }

    // Setting exams for each subject
    private void subExams(Subject subject) {
        if (!subject.getExams().isEmpty()) {
            // Create a Dates object with the subject name and the exam date
            Dates examDate = new Dates(subject, subject.getExams().get(0).getExamDate());
            // Add the Dates object to the list
            exams.add(examDate);
        }
        // Check if the subject or its exams list is null
        if (subject.getExams() == null || subject.getExams().isEmpty()) {
            throw new IllegalArgumentException("Subject or exam list is invalid. Exams must not be empty.");

        }

    }

    // Setting exams for each subject
    private void subAssignment(Subject subject) {
        if (!subject.getAssignments().isEmpty()) {
            // Create a Dates object with the subject name and the exam date
            Dates assDate = new Dates(subject, subject.getAssignments().get(0).getDeadline());
            // Add the Dates object to the list
            assignments.add(assDate);
        }

    }

    // Setting assignments for non-subject related assignments
    public void subAss2(String name, LocalDate deadline, int estimateHours) {
        // Create a Dates object with the subject name and the exam date
        Dates assDate = new Dates(name, deadline);
        // Add the Dates object to the list
        assignments.add(assDate);
        subTask2(name, estimateHours);
    }

    // Creating tasks for non-subject related assignments
    private void subTask2(String name, int estimateHours) {
        int taskType3 = CalculativeAlgorithm.numberOfScheduledTask(estimateHours);

        // Task creation for each task type

        for (int i = 0; i < taskType3; i++) {
            tasks.add(new Task(name, 3)); // Assignment
        }
    }

    // Creating tasks for each subject
    private void subTasks(Subject subject) {
        // setting the difficulty level

        CalculativeAlgorithm.setPagesPerMin(subject.getExams().get(0).getTimePer20Slides());
        // studying tasks
        int taskType1 = CalculativeAlgorithm.studyingTasks(subject);

        // assignment tasks
        int taskType3 = CalculativeAlgorithm.numberOfScheduledTask(subject.getTotalAssHours());
        // Task creation for each task type
        for (int i = 0; i < taskType1; i++) {
            tasks.add(new Task(subject, 1)); // studying
        }

        for (int i = 0; i < taskType3; i++) {
            tasks.add(new Task(subject, 3)); // Assignment
        }
    }

    // getter for the exam list
    public static List<Dates> getExams() {
        return exams;
    }

    // getter for the assignment list
    public static List<Dates> getAssignments() {
        return assignments;
    }

    private static double bestScoring;
    private static List<Task> bestTask = new ArrayList<>();
    private static int[][] schedule;

    // getters
    public static int[][] getSchedule() {
        return schedule;
    }

    public static List<Task> getBestTask() {
        return bestTask;
    }

    public static List<Subject> getSubjects() {return subjects; }
    // Κατανομή tasks στο πρόγραμμα
    public static void scheduleResult() {
        /*
         * each time the method is called in order to produce the best result
         * the best scoring is set as zero and the list with the best distribution,
         * shuffles an array so that the order of the table elements
         * different from the one that is given
         * The procedure is done 50 times in order to produce 10 possible results
         * Then each list gets a score. The list with the higher score is set as the
         * bestTask
         */

        bestTask.clear();

        // sort exams
        exams = Dates.sortList(exams);
        assignments = Dates.sortList(assignments);
        // The column size of the table is determined by the last examination date
        // The column size of the table is determined by the last examination date
        int colSize = Dates.lastIsDue(exams, assignments);
        List<Task> copyTask;
        for (int i = 0; i < 50; i++) {

            copyTask = new ArrayList<>(tasks);
            double valResultScoring;

            int[][] vSchedule = TaskAssignment.assignTask(copyTask, colSize);

            // list scoring
            // calls static method calculateScore
            valResultScoring = Scoring.calculateScore(TaskAssignment.getTasks(), vSchedule, colSize);
            if (i == 0) {
                bestScoring = valResultScoring;
            }
            // method to assign all the unassigned task type 3
            ScheduleResult result = new ScheduleResult(valResultScoring, TaskAssignment.getTasks(), vSchedule);
            scheduleResults.add(result);

        }
        for (ScheduleResult sr : scheduleResults) {
            bestSchedule(sr.getScore(), sr.getTasks(), sr.getSchedule());
        }
        schedule = Validate.validateSchedule(schedule, bestTask);
         PrintSchedule.printSchedule(schedule, bestTask, colSize);

    }

    public static void bestSchedule(double valResultScoring, List<Task> taskList, int[][] sch) {
        if (valResultScoring >= bestScoring) {
            bestScoring = valResultScoring;
            bestTask = taskList;
            schedule = sch;
        }
    }

}