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
import java.util.Collections;
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
        int taskType1 = CalculativeAlgorithm.studyingPerweek(subject);
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
            ///////////
            System.out.println("copy tasks in the big of the method" + coppyTask.size());
            System.out.println("tasks in the big of the method" + coppyTask.size());

            double valresultscoring = 0.0;

            int[][] vschedule = assignTask(coppyTask);

            // list scoring
            valresultscoring = setScore(coppyTask, vschedule);
            if (i == 0) {
                bestscoring = valresultscoring;
            }
            ScheduleResult result = new ScheduleResult(valresultscoring, coppyTask, vschedule);
            scheduleResults.add(result);
            System.out.println("done" + i + " " + valresultscoring);
            /////////////////////////////////////////
            System.out.println("copy tasks in the big of the method" + coppyTask.size());
            System.out.println("tasks in the big of the method" + coppyTask.size());

        }
        for (ScheduleResult sr : scheduleResults) {
            bestschedule(sr.getScore(), sr.getTasks(), sr.getSchedule(), scheduleResults.indexOf(sr));
        }
        System.out.println(index);
        PrintSchedule.printSchedule(schedule, besttask, colsize);

    }

    // calls static method calculatescore
    public static double setScore(List<Task> taskList, int[][] sch) {
        return Scoring.calculatescore(taskList, sch, colsize);
    }

    public static void bestschedule(double valresultscoring, List<Task> taskList, int[][] sch, int in) {
        if (valresultscoring >= bestscoring) {
            bestscoring = valresultscoring;
            besttask = taskList;
            schedule = sch;
            index = in;
        }
    }

    // table for the task indexes
    private static int[][] valSchedule;

    // Methods in order to have acess to the table for other classes
    public static void setValSchedule(int[][] sc) {
        valSchedule = sc;
    }

    public static int[][] getValSchedule() {
        return valSchedule;
    }

    // stores the remaining hours for the day
    private static double remainingHours;

    /*
     * Methods in order for other classes to have access to the
     * remaining hours for a day
     */
    public static double getRemainingHours() {
        return remainingHours;
    }

    public static void setRemainingHours(double remHours) {
        remainingHours = remHours;
    }

    public static int[][] assignTask(List<Task> asstasks) {
        Collections.shuffle(asstasks);
        /*
         * The table valSchedule stores the index of the task Array list, after the
         * tasks have been distributed into the available hours
         */
        if (colsize == 0) {
            throw new IllegalStateException(
                    "Column size is not initialized. ");
        }

        valSchedule = new int[12][colsize];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < colsize; j++) {
                valSchedule[i][j] = 0;
            }
        }

        // Index of the tasks Array List
        int taskIndex = 1;
        /*
         * The length of the task list before the tasks of type two
         * are assigned
         */
        int tasklength = asstasks.size() - 1;
        // available hours for the day
        for (int col = 0; col < colsize; col++) {
            if (col == 0) {
                System.out.println("assined tasks in the big of the method" + asstasks.size());
            }
            /*
             * checks if there is already a revision assigned
             * & Merge repetition tasks if needed
             */
            Availability.mergeRepTasks(valSchedule, asstasks, col);
            remainingHours = (double) Availability.getTotalAvailableHours(col);
            // reduce Availability accordingly to the assigned repetition tasks
            Availability.reduceRepAvailability(col, asstasks);

            // check non Availability for a day
            boolean flagNAv;
            flagNAv = Availability.checkAvailability(col);

            if (flagNAv) {

                for (int row = 0; row < 12; row++) { // Max 12 tasks per day
                    // the loop ends when every task is assigned to a day
                    if (taskIndex >= tasklength) {
                        break;
                    }

                    if (remainingHours >= 2.0) { // each task requires 2 hours
                        /*
                         * check if the exam date of the subject's task that we want to
                         * assign to a day has passed
                         */
                        boolean flagEx = false;

                        flagEx = ExamDates.checkExamDate(asstasks.get(taskIndex), col, exams);
                        LocalDate exDate = ExamDates.getExDate(asstasks.get(taskIndex), col, exams);

                        if (flagEx) {

                            if (valSchedule[row][col] == 0) {
                                // corrects the schedule table

                                // store the task index
                                valSchedule[row][col] = taskIndex;
                                // The remaining hours is reduced by 2 hours
                                remainingHours -= 2.0;
                                taskIndex++;
                                // Assign task Type 2 only if task type =1
                                if (asstasks.get(taskIndex).getTaskType() == 1) {
                                    asstasks = Repetition.generateRepetitions(asstasks, asstasks.get(taskIndex), exDate,
                                            col);
                                }

                            } else {
                                // continues to the next row
                                continue;
                            }
                        } else {
                            // continue to the next task
                            taskIndex++;
                        }
                    } else {
                        // if there are not enough available hours we continue to the next day
                        break;
                    }
                }
            }
            if (col == colsize - 1) {
                System.out.println("assined tasks in the end of the method" + asstasks.size());
                // clear the list

            }

        }

        // return the table with the valid result
        return valSchedule;
    }
}
