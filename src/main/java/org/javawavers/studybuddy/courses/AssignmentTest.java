package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.util.Scanner;

public class AssignmentTest {

    public void run() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("εργασια:");
        String title = scanner.nextLine();
        System.out.println("προθεσμια : (yyyy - MM - DD)");
        String inputDate = scanner.nextLine();
        
        // Εξασφάλισε ότι η ημερομηνία είναι στην κατάλληλη μορφή
        inputDate = inputDate.replace(" ", "").replace("-", "");
        
        // Εάν είναι σε μορφή "yyyyMMdd", κάνε την αναγνώσιμη για το LocalDate
        if(inputDate.length() == 8) {
            inputDate = inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
        }
        
        // Αναλύει την ημερομηνία
        LocalDate deadline = LocalDate.parse(inputDate);
        System.out.println("ωρες:");
        int hours = scanner.nextInt();


        Assignment as = new Assignment(title, deadline, hours, null);

        long remaingdays = as.getRemainingDays(deadline);
        System.out.println(as);
        as.setTitle(title);

        scanner.close();
        


    }
    public static void main(String[] args) {
        AssignmentTest test = new AssignmentTest();
        test.run();
    }
}
