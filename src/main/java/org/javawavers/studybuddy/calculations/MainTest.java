package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Subject;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {
        // Create subjects
        org.javawavers.studybuddy.courses.Subject.Subject Maths = new org.javawavers.studybuddy.courses.Subject.Subject("Maths", null,4);
        org.javawavers.studybuddy.courses.Subject.Exam e1 = new org.javawavers.studybuddy.courses.Subject.Exam(400,20,LocalDate.of(2025, 2, 24),50);
        Maths.addExam(e1);
        org.javawavers.studybuddy.courses.Assignment.Assignment a1 = new org.javawavers.studybuddy.courses.Assignment.Assignment("Mathsασσ", LocalDate.of(2025, 2, 18), 13);

        Maths.addAssignment(a1);
        // Maths.addAssignment(a2);
        org.javawavers.studybuddy.courses.Subject.Subject History = new org.javawavers.studybuddy.courses.Subject.Subject("History", 4, null, null);
        org.javawavers.studybuddy.courses.Subject.Exam e2 = new org.javawavers.studybuddy.courses.Subject.Exam(100,30,LocalDate.of(2025, 2, 1),90);

        // assignment without any exam
        org.javawavers.studybuddy.courses.Assignment.Assignment a2 = new org.javawavers.studybuddy.courses.Assignment.Assignment("Ass1", LocalDate.of(2025, 1, 18), 13);
        org.javawavers.studybuddy.courses.Assignment.Assignment a3 = new Assignment.Assignment("Ass1", LocalDate.of(2025, 1, 18), 13);
        History.addExam(e2);
        ArrayList<org.javawavers.studybuddy.courses.Subject.Subject> subs = new ArrayList<>();
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

        for (Subject.Subject s : subs) {
            sAnnealing.addSubject(s);

        }

        sAnnealing.subAss2(a2.getName(), a2.getDeadline(), a2.getEstimateHours());
        sAnnealing.subAss2(a3.getName(), a3.getDeadline(), a3.getEstimateHours());
        SimulateAnnealing.scheduleResult();
    }
}
