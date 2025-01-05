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

import java.util.ArrayList;
import java.util.List;

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
    private static List<ExamDates> exams; // List for each exam that is connected with a subject
    private static List<ScheduleResult> scheduleResults;// List for the valid schedule results

    public SimulateAnnealing() {
        this.subjects = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.scheduleResults = new ArrayList<>();
    }

    // Add a new Subject
    public void addSubject(Subject subject) {
        subjects.add(subject);
        // Sets exams for the subject
        subExams(subject);

        // Creates tasks for the subject
        subTasks(subject);

    }

    // Setting exams for each subject
    private void subExams(Subject subject) {

        // Create an ExamDates object with the subject name and the exam date
        ExamDates examDate = new ExamDates(subject, subject.getExams().get(0).getExamDate());
        // Add the ExamDates object to the list
        exams.add(examDate);

    }

    // Creating tasks for each subject
    private void subTasks(Subject subject) {
        // studying tasks
        int taskType1 = CalculativeAlgorithm.studyingPerWeek(subject);
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
    public static List<ExamDates> getExams() {
        return exams;
    }

    private static double bestscoring;
    private static List<Task> besttask = new ArrayList<>();
    private static int[][] schedule;
    // The column size of the table is determined by the last examination date
    private static int colsize = 0;
    private static int index;

    // Κατανομή tasks στο πρόγραμμα
    public static void scheduleResult() {
        /*
         * each time the method is called in order to produce the best result
         * the best scoring is set as zero and the list with the best distribution,
         * shuffles an array so that the order of the table elements
         * different from the one that is given
         * The procedure is done 50 times in order to produce 10 possible results
         * Then each list gets a score. The list with the higher score is set as the
         * besttask
         */
        bestscoring = 0.0;
        besttask.clear();

        // sort exams
        exams = ExamDates.sortExam(exams);
        // The column size of the table is determined by the last examination date
        colsize = ExamDates.lastExIsDue(exams);
        List<Task> coppyTask;
        for (int i = 0; i < 50; i++) {

            coppyTask = new ArrayList<>(tasks);
            double valresultscoring = 0.0;

            int[][] vschedule = TaskAssignment.assignTask(coppyTask, colsize);

            // list scoring
            // calls static method calculatescore
            valresultscoring = Scoring.calculateScore(coppyTask, vschedule, colsize);
            if (i == 0) {
                bestscoring = valresultscoring;
            }
            ScheduleResult result = new ScheduleResult(valresultscoring, coppyTask, vschedule);
            scheduleResults.add(result);

        }
        for (ScheduleResult sr : scheduleResults) {
            bestschedule(sr.getScore(), sr.getTasks(), sr.getSchedule(), scheduleResults.indexOf(sr));
        }
        PrintSchedule.printSchedule(schedule, besttask, colsize);

    }

    public static void bestschedule(double valresultscoring, List<Task> taskList, int[][] sch, int in) {
        if (valresultscoring >= bestscoring) {
            bestscoring = valresultscoring;
            besttask = taskList;
            schedule = sch;
            index = in;
        }
    }

}
