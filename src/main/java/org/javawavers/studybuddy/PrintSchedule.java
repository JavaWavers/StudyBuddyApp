package org.javawavers.studybuddy;

import java.time.LocalDate;
import java.util.List;
//τι και αν παρω αυτην την κλαση και την αλλαξω και να κανει μεσα εδω το splitweek και να καλειται και να επιστρεφει 
public class PrintSchedule {
    // The below method prints the schedule to the user
    public static List<Task> printSchedule(int[][] schedule, List<Task> bestTask, int colSize) {

        System.out.println("Προτεινόμενο πρόγραμμα");

        LocalDate today = LocalDate.now();

        for (int i = 0; i < colSize; i++) {
            System.out.println("Μέρα: " + today.plusDays(i)); // Print the current day

            /*
             * if the first two rows of the schedule contain the number zero that signifies
             * one non-available day
             */
            if (!Availability.checkAvailability(i)) {
                System.out.println("Η μέρα έχει οριστεί ως μη διαθέσιμη");
            }
            for (int j = 0; j < 12; j++) {

                // Check if a task is assigned at this position
                if (schedule[j][i] > 0) {
                    // Print the task corresponding to the schedule index
                    System.out.println(bestTask.get(schedule[j][i])); // Task index is stored as +1

                }

            }
            if (i == colSize - 1) {
                break;
            }
        }
        //ExamPage examPage = new ExamPage();
       // System.out.println(examPage.getSchedule());
        return bestTask;

    }

}