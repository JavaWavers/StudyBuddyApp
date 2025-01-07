package org.javawavers.studybuddy;

/*
 * The ScheduleResult class represents the result of a scheduling process.
 * It stores the score of the schedule, the list of tasks assigned, 
 * and the schedule itself in the form of an array.
 * 
 * This class is used to encapsulate and manage the outcome of a 
 * scheduling attempt for easy comparison and evaluation.
 */
import java.util.List;

public class ScheduleResult {
    double score;
    List<Task> tasks;
    int[][] schedule;

    ScheduleResult(double score, List<Task> tasks, int[][] schedule) {
        this.score = score;
        this.tasks = tasks;
        this.schedule = schedule;
    }

    public double getScore() {
        return score;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int[][] getSchedule() {
        return schedule;
    }
}
