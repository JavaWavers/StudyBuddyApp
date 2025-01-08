package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {
        // Create subjects
        Subject Maths = new Subject("Maths", null,4);
        Exam e1 = new Exam(400,20,LocalDate.of(2025, 2, 24),50);
        Maths.addExam(e1);
        Assignment a1 = new Assignment("Mathsασσ", LocalDate.of(2025, 2, 18), 13);

        Maths.addAssignment(a1);
        // Maths.addAssignment(a2);
        Subject History = new Subject("History", 4, null, null);
        Exam e2 = new Exam(100,30,LocalDate.of(2025, 2, 1),90);

        // assignment without any exam
        Assignment a2 = new Assignment("Ass1", LocalDate.of(2025, 1, 18), 13);
        Assignment a3 = new Assignment("Ass1", LocalDate.of(2025, 1, 18), 13);
        History.addExam(e2);
        ArrayList<Subject> subs = new ArrayList<>();
        subs.add(Maths);
        subs.add(History);
        // create Availability
        Availability.setAvailability(1, 6); // Monday: 6 available hours
        Availability.setAvailability(2, 4); // Tuesday: 4 available hours
        Availability.setAvailability(3, 7); // Wednesday: 7 available hours
        Availability.setAvailability(4, 4); // Thursday: 4 available hours
        Availability.setAvailability(5, 6); // Friday: 6 available hours
        Availability.setAvailability(6, 6); // Saturday: 6 available hours
        Availability.setAvailability(7, 6); // Sunday: 6 available hour
        // create non Availability
        Availability.setNonAvailability(LocalDate.of(2025, 1, 10));

        Availability.setNonAvailability(LocalDate.of(2025, 1, 4));

        SimulateAnnealing sAnnealing = new SimulateAnnealing();

        for (Subject s : subs) {
            sAnnealing.addSubject(s);

        }

        sAnnealing.subAss2(a2.getName(), a2.getDeadline(), a2.getEstimateHours());
        sAnnealing.subAss2(a3.getName(), a3.getDeadline(), a3.getEstimateHours());
        SimulateAnnealing.scheduleResult();
    }
}
