package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Repetition {

    /*
     * This method generates a schedule for repetitions based on the study task.
     * A repetition task is scheduled for:
     * - The next day after the study task.
     * -After 1 day
     * -After 3 days
     * - After 7 days.
     * - After 14 days.
     * - After 28 days.
     * - Then it doubles the interval days till the exam date.
     */
    public static List<Task> generateRepetitions(List<Task> tasks, Task studyTask, LocalDate examDate, int day) {

        List<RepetitionTask> repetitions = new ArrayList<>();

        // Get the date of the study task
        LocalDate studyDate = LocalDate.now().plusDays(day);
        String subject = studyTask.getSubject();

        LocalDate repetitionDate;
        // Add repetition after specific intervals
        int[] fixedIntervals = { 1, 3, 7, 14, 28 }; // Fixed intervals in days
        for (int f : fixedIntervals) {
            repetitionDate = studyDate.plusDays(f);
            if (repetitionDate.isBefore(examDate)) {
                repetitions.add(new RepetitionTask(subject, repetitionDate));
            }
        }

        // Add repetitions with doubling intervals until the exam date
        int interval = 35;
        while (true) {
            interval *= 2; // Double the interval
            repetitionDate = studyDate.plusDays(interval);
            if (repetitionDate.isBefore(examDate)) {
                repetitions.add(new RepetitionTask(subject, repetitionDate));
            } else {
                break; // Stop if the date exceeds the exam date
            }
        }
        return assRepetitions(repetitions, tasks, subject);
    }

    // class that assigns the repetition tasks into the schedule
    public static List<Task> assRepetitions(List<RepetitionTask> rep, List<Task> tasks, String subject) {
        // Retrieve the current schedule
        int[][] schedule = SimulateAnnealing.getValSchedule();
        if (schedule == null) {
            throw new IllegalStateException(
                    "Schedule is not initialized.");
        }

        for (RepetitionTask r : rep) {
            LocalDate today = LocalDate.now();
            int daysBetween = (int) ChronoUnit.DAYS.between(today, r.getDate());

            if (daysBetween >= 0 && daysBetween < schedule[0].length) { // Ensure daysBetween is within bounds
                for (int i = 0; i < 12; i++) { // Iterate through rows (max 12 tasks per day)
                    // check for an unassigned slot and if the day is available
                    if (schedule[i][daysBetween] == 0 && Availability.checkAvailability(daysBetween)) {
                        /*
                         * Each task of type 1 and 3 takes 2 hours, while each task of type 2 takes 20
                         * minutes
                         */
                        if (SimulateAnnealing.getRemainingHours() > 1.0 / 3.0) {
                            // Add a new repetition task
                            tasks.add(new Task(subject, 2)); // 2 represents a repetition task
                            // Assign the task index to the schedule
                            schedule[i][daysBetween] = tasks.size() - 1;
                            break; // Exit loop after assigning the task
                        }

                    }
                }
            }
        }

        // Update the modified schedule
        SimulateAnnealing.setValSchedule(schedule);

        return tasks;
    }

    // Helper class to represent a repetition task
    public static class RepetitionTask {
        private String subject;
        private LocalDate date;

        public RepetitionTask(String subject, LocalDate date) {
            this.subject = subject;
            this.date = date;
        }

        public String getSubject() {
            return subject;
        }

        public LocalDate getDate() {
            return date;
        }
    }

}
