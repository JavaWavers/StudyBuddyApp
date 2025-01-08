package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
import java.util.List;

public class PrintSchedule {
    // The below method prints the schedule to the user
    public static void printSchedule(int[][] schedule, List<Task> besttask, int colsize) {

        System.out.println("Προτεινόμενο πρόγραμμα");

        LocalDate today = LocalDate.now();

        for (int i = 0; i < colsize; i++) {
            // Μπορώ να δημιουργώ αντικείμενα τυπου day και να τα βάζω σε λιστα μέσα στο
            // αντικείμενο week και εδώ να κρατάω μια λίστα με όλες
            // τις βδομάδεςΣ
            System.out.println("Μέρα: " + today.plusDays(i)); // Print the current day

            /*
             * if the first two rows of the schedule contain the number zero that signifies
             * one non available day
             */
            if (!Availability.checkAvailability(i)) {
                System.out.println("Η μέρα έχει οριστεί ως μη διαθέσιμη");
            }
            for (int j = 0; j < 12; j++) {

                // Check if a task is assigned at this position
                if (schedule[j][i] > 0) {
                    // Print the task corresponding to the schedule index
                    System.out.println(besttask.get(schedule[j][i])); // Task index is stored as +1

                }

            }
            if (i == colsize - 1) {
                break;
            }
        }

    }

}
