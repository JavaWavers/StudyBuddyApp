package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.util.List;

public class SpacedRepetitionTest {
    public static void main(String[] args) {
        // Create a new SpacedRepetition system
        SpacedRepetition spacedRepetition = new SpacedRepetition();

        // Add multiple lessons with different learned dates
        spacedRepetition.addLesson("Mathematics", LocalDate.now().minusDays(1));  // Learned 1 day ago
        spacedRepetition.addLesson("Physics", LocalDate.now().minusDays(3));     // Learned 3 days ago
        spacedRepetition.addLesson("History", LocalDate.now());                 // Learned today
        spacedRepetition.addLesson("Biology", LocalDate.now().minusDays(5));     // Learned 5 days ago
        spacedRepetition.addLesson("Chemistry", LocalDate.now().minusDays(2));   // Learned 2 days ago
        spacedRepetition.addLesson("Programming", LocalDate.now().minusDays(7)); // Learned 7 days ago
        spacedRepetition.addLesson("Literature", LocalDate.now().minusDays(10)); // Learned 10 days ago

        // Display all lessons initially
        System.out.println("Initial Lessons:");
        for (SpacedRepetition.Lesson lesson : spacedRepetition.lessons) {
            System.out.println(lesson);
        }

        for (SpacedRepetition.Lesson lesson : spacedRepetition.lessons) {
            // Simulate forgetting Physics and Programming in this round
            boolean remembered = !(lesson.getTitle().equals("Physics") || lesson.getTitle().equals("Programming"));
            lesson.advanceStep(remembered);
        }

        for (SpacedRepetition.Lesson lesson : spacedRepetition.lessons) {
            // Simulate all lessons being remembered in this round
            lesson.advanceStep(true);
        }

        // Check lessons due after multiple advancements
        LocalDate date = LocalDate.of(2025, 1, 20);
        System.out.println("\nLessons Due " + date);
        List<SpacedRepetition.Lesson> dueLessons = spacedRepetition.getDueLessons(date);
        if (dueLessons.isEmpty()) {
            System.out.println("δεν υπάρχουν μαθήματα για επανάληψη την μέρα " + date);
        } else {
            for (SpacedRepetition.Lesson lesson : dueLessons) {
                System.out.println(lesson.getTitle());
            }
        }


        // Display final state of all lessons
        System.out.println("\nFinal Lessons State:");
        for (SpacedRepetition.Lesson lesson : spacedRepetition.lessons) {
            System.out.println(lesson);
        }
    }
}
