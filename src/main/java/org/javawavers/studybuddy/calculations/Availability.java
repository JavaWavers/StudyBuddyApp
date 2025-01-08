package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Availability {
    /*
     * A table for the available studying time for each day of the week
     * The first element of the table is never used.
     * Each number from 1 to 7 represents a day of the week starting with Monday
     */
    private static int[] avperday = new int[8];

    /*
     * Gives the ability to the user to insert certain dates that there
     * is no available time for studying
     */
    private static LinkedList<LocalDate> dates = new LinkedList<>();

    // constructor
    public Availability() {

    }

    /*
     * setting the availability per day of the week
     */
    public static void setAvailability(int i, int av) {
        if (i <= 0 || i > 7) {
            throw new IllegalArgumentException(" Ο αριθμός πρέπει να είναι μεταξύ του 1και του 7");
        }
        avperday[i] = av;
    }

    // insert a day that there is no availability for studying
    public static void setNonAvailability(LocalDate l) {
        // checks if a date is already used
        if (!dates.contains(l)) {
            dates.add(l);
        } else {
            // if the date is already used, a message is given to the user
            System.out.println("Η ημερομηνία " + l + " έχει ήδη καταχωρηθεί");
        }

    }

    /*
     * checks if the day that the program asserts a task to the user is set as non
     * available
     * and returns true if the day is Available
     */

    public static boolean checkAvailability(int day) {
        LocalDate taskDate = LocalDate.now().plusDays(day);
        return !dates.contains(taskDate);
    }

    // returns the total available hours for the day that is asked
    public static int getTotalAvailableHours(int i) {
        LocalDate today = LocalDate.now();// The date of the day that we curently are
        // The date that we want to assert the tasks
        LocalDate examDate = today.plusDays(i);

        // Name of the day as a String type Data
        String dayName = examDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("el"));

        switch (dayName) {
            case "Δευτέρα":
                return avperday[1];
            case "Τρίτη":
                return avperday[2];
            case "Τετάρτη":
                return avperday[3];
            case "Πέμπτη":
                return avperday[4];
            case "Παρασκευή":
                return avperday[5];
            case "Σάββατο":
                return avperday[6];
            case "Κυριακή":
                return avperday[7];
            default:
                throw new IllegalArgumentException("Ημέρα μη έγκυρη: " + dayName);
        }

    }

    public static void mergeRepTasks(int[][] schedule, List<Task> tasks, int col) {
        // Get a list of subject names from the tasks
        List<String> subjectNames = tasks.stream()
                .map(Task::getSubject) // Apply the getSubject() method on each Task to extract the subject name
                .collect(Collectors.toList()); // Collect the results into a new list
        int[] rowsInd = new int[2];
        for (String s : subjectNames) {
            int num = 0;
            int pointerIndex = 0;
            for (int row = 0; row < 12; row++) {
                int taskIndex = schedule[row][col]; // Get the task index from the schedule
                if (taskIndex != 0) { // Check if there is a task in this slot
                    Task task = tasks.get(taskIndex); // Retrieve the task from the tasks list

                    // Check if the task is a repetition task (type 2)
                    if (task.getTaskType() == 2) {
                        String subjectName = task.getSubject(); // Get the subject name
                        if (subjectName.equals(s)) {
                            num++;
                        }
                        if (num == 1) {
                            pointerIndex = taskIndex;
                        } else if (num == 2) {
                            rowsInd[0] = row;
                        } else if (num == 3) {
                            rowsInd[1] = row;
                            tasks.get(pointerIndex).setTaskHours(tasks.get(pointerIndex).getTaskHours() + 2.0 / 3.0);
                            // clear the slots whose tasks are merged into the first one
                            for (int i = 0; i < 2; i++) {
                                schedule[rowsInd[i]][col] = 0;
                            }
                            // continues to the next rows in case there are more revision tasks that can be
                            // merged
                            num = 0;
                            continue;
                        }
                    }
                }
            }
        }
        SimulateAnnealing.setValSchedule(schedule);
    }

    /*
     * This method reduces the available hours for a specific day based on the tasks
     * scheduled in a given column.
     */
    public static void reduceRepAvailability(int col, List<Task> tasks) {
        int[][] schedule = SimulateAnnealing.getValSchedule();
        for (int row = 0; row < 12; row++) {
            double reducedHours = 0.0;
            if (schedule[row][col] != 0) {
                reducedHours = tasks.get(schedule[row][col]).getTaskHours();
            }
            SimulateAnnealing.setRemainingHours(SimulateAnnealing.getRemainingHours() - reducedHours);
        }
    }
}
