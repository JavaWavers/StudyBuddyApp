package org.javawavers.studybuddy.availability;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.Locale;


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
        LocalDate today= LocalDate.now();//The date of the day that we curently are
        //The date that we want to assert the tasks
        LocalDate examDate=today.plusDays(i);

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

    
}
