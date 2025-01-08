package org.javawavers.studybuddy.calculations;
/* This class is used to assign a score to each valid result
 * that could be used as a recommended study schedule for the user.
 * The SimulateAnnealing class produces 50 valid results.
 * Validity means that the tasks are distributed according to the user's
 * daily availability for studying.
 * Each result is then given a score to determine the most effective one
 * among those that are randomly produced.
 * The maximum score a valid result can achieve is 5.0.
 * -0.2 points are deducted each time the same task type (1-studying,
 * 2-revision, 3-assignment) for the same subject is assigned on the same day.
 * -0.1 points are deducted when a subject is studied two days in a row.
 */


import java.util.ArrayList;
import java.util.List;

public class Scoring {
    public static double calculatescore(List<Task> taskList, int[][] sch, int colsize) {
        // set the score at 5.0. The higher that can possibly be achieved
        double score = 5.0;
        // creating a list that should contain each subject only once
        List<String> uniqueS = new ArrayList<>();
        for (Task task : taskList) {
            String subject = task.getSubject();
            if (!uniqueS.contains(subject)) {
                uniqueS.add(subject); // Προσθήκη του μαθήματος αν δεν υπάρχει ήδη
            }
        }

        // -0.2 penalty for the same task type for the same subject on the same day
        // for each day
        for (int col = 0; col < colsize; col++) {

            // for each individual subject
            for (int index = 0; index < uniqueS.size(); index++) {
                String usubject = uniqueS.get(index);

                // we check if there is also a same task
                for (int row = 0; row < 12; row++) {
                    if (sch[row][col] >= 0 && sch[row][col] < taskList.size()) {

                        Task t = taskList.get(sch[row][col]);

                        /*
                         * checks if the subject from the subject list is the same as
                         * the one from the task list
                         */
                        if (usubject.equals(t.getSubject())) {
                            score -= 0.2;
                        }

                    } else {
                        break;
                    }
                }

            }
        }

        // -0.1 penalty for studying the same subject two days in a row
        // For each column (day)
        for (int col = 0; col < colsize - 1; col++) {
            // Traverse the tasks of the current day
            for (int row = 0; row < 12; row++) {
                if (sch[row][col] >= 0 && sch[row][col] < taskList.size()) {
                    Task curTask = taskList.get(sch[row][col]);
                    String curSubject = curTask.getSubject();
                    // The type of the task (1: Study, 2: Revision, 3: Assignment)
                    int curType = curTask.getTaskType();

                    // Check the next day
                    for (int nextRow = 0; nextRow < 12; nextRow++) {
                        if (sch[nextRow][col + 1] != 0 && sch[nextRow][col + 1] < taskList.size()) {
                            Task nextTask = taskList.get(sch[nextRow][col + 1]);
                            String nextSubject = nextTask.getSubject();
                            int nextType = nextTask.getTaskType();

                            // If the same subject and the same type of task is scheduled on consecutive
                            // days
                            if (curSubject.equals(nextSubject) && curType == nextType) {
                                // Deduct 0.1 points
                                score -= 0.1;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }
}
