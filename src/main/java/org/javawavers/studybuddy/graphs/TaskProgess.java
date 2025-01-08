package org.javawavers.studybuddy.graphs;

import org.javawavers.studybuddy.courses.ScheduledTask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskProgess {

    private static List<ScheduledTask> tasks = new ArrayList<>();

    public static void setTasks(List<ScheduledTask> tasks) {
        TaskProgess.tasks = tasks;
    }

    public double calculateDailyProgress(LocalDate day) {
        int numberOfTasks = 0;
        int numberOfCompletedTasks = 0;
        for (ScheduledTask task : tasks) {
            if (task.getTaskDate().equals(day)) {
                numberOfTasks++;
                if (task.getTaskStatus().equals(ScheduledTask.TaskStatus.COMPLETED)) {
                    numberOfCompletedTasks++;
                }
            }
        }
        if (numberOfCompletedTasks != 0) {
            return (double) numberOfCompletedTasks / numberOfTasks;
        } else {
            System.out.println("δεν υπάρχει διαθέσιμα ScheduledTask");
            return 0;
        }
    }

    public double calculateWeeklyProgress(LocalDate weekStartDate) {
        LocalDate endOfWeek = weekStartDate.plusDays(6); // Calculate end of the week
        double totalProgress = 0;
        int daysWithTasks = 0;

        for (LocalDate date = weekStartDate; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            double dailyProgress = calculateDailyProgress(date);
            if (dailyProgress > 0) {
                totalProgress += dailyProgress;
                daysWithTasks++;
            }
        }

        if (daysWithTasks > 0) {
            return totalProgress / daysWithTasks;
        } else {
            System.out.println("δεν υπάρχει διαθέσιμα ScheduledTask για την εβδομάδα");
            return 0;
        }
    }

    // Method to calculate daily distribution per subject
    public static Map<String, Double> calculateDailyDistribution(LocalDate date) {
        Map<String, Integer> totalTasksPerSubject = new HashMap<>();
        Map<String, Integer> completedTasksPerSubject = new HashMap<>();

        for (ScheduledTask task : tasks) {
            if (task.getTaskDate().equals(date)) {
                String subject = task.getSubjectName();
                totalTasksPerSubject.put(subject, totalTasksPerSubject.getOrDefault(subject, 0) + 1);
                if (task.isComplete()) {
                    completedTasksPerSubject.put(subject, completedTasksPerSubject.getOrDefault(subject, 0) + 1);
                }
            }
        }

        Map<String, Double> progressPerSubject = new HashMap<>();
        for (String subject : totalTasksPerSubject.keySet()) {
            int total = totalTasksPerSubject.get(subject);
            int completed = completedTasksPerSubject.getOrDefault(subject, 0);
            progressPerSubject.put(subject, total == 0 ? 0.0 : (double) completed / total);
        }

        return progressPerSubject;
    }

    // Method to calculate weekly distribution per subject
    public static Map<String, Double> calculateWeeklyDistribution(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> weeklyProgress = new HashMap<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Map<String, Double> dailyProgress = calculateDailyDistribution(date);

            for (Map.Entry<String, Double> entry : dailyProgress.entrySet()) {
                String subject = entry.getKey();
                double dailyValue = entry.getValue();
                weeklyProgress.put(subject, weeklyProgress.getOrDefault(subject, 0.0) + dailyValue);
            }
        }

        for (String subject : weeklyProgress.keySet()) {
            weeklyProgress.put(subject, weeklyProgress.get(subject) / 7.0);
        }

        return weeklyProgress;
    }
}
