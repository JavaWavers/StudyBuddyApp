package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Subject;
import org.javawavers.studybuddy.courses.Exam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculativeAlgorithmTest {

    private Subject subject;

    @BeforeEach
    void setUp() {
        // Initialize a Subject object with an Exam object for testing
        subject = new Subject("Math"); // Subject name: Math
        subject.setDifficultyLevel(3); // Set difficulty level to 3

        // Create an Exam object with exam date and number of pages
        Exam exam = new Exam(LocalDate.now().plusDays(30), 120); // Exam in 30 days, 120 pages
        List<Exam> exams = new ArrayList<>();
        exams.add(exam); // Add the exam to the list
        subject.setExams(exams); // Associate the exam with the subject
    }

    @Test
    void testSetPagesPerMinValidInput() {
        // Set study speed: 10 minutes for 20 slides
        CalculativeAlgorithm.setPagesPerMin(10);
        double expectedPagesPerMin = 20 / 10.0; // Expected speed: 2 pages per minute

        // Assert that the calculated study speed is correct
        assertEquals(expectedPagesPerMin, getPagesPerMin(), 0.001);
    }

    @Test
    void testSetPagesPerMinZeroTime() {
        // Test the case where study time is zero (should throw an exception)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculativeAlgorithm.setPagesPerMin(0); // Input: 0 minutes
        });

        // Assert that the exception message contains "divide by zero"
        assertTrue(exception.getMessage().contains("greater than zero"));
    }

    @Test
    void testTotalStudyingTime() {
        // Set study speed: 10 minutes for 20 slides
        CalculativeAlgorithm.setPagesPerMin(10);

        // Calculate the total study time
        double totalStudyTime = CalculativeAlgorithm.totalStudyingTime(subject);

        // Calculation: (120 pages * difficulty 3) / (2 pages per minute * 60 minutes)
        double expectedStudyTime = (120 * 3) / (double)(2 * 60);

        // Assert that the calculated study time is correct
        assertEquals(expectedStudyTime, totalStudyTime, 0.001);
    }

    @Test
    void testStudyingTasks() {
        // Set study speed: 10 minutes for 20 slides
        CalculativeAlgorithm.setPagesPerMin(10);

        // Calculate the total number of tasks
        int totalTasks = CalculativeAlgorithm.studyingTasks(subject);

        // Calculation: (Total study time in hours) / 2 hours per task
        double totalStudyTime = CalculativeAlgorithm.totalStudyingTime(subject);
        int expectedTasks = (int) Math.round(totalStudyTime / 2);

        // Assert that the calculated number of tasks is correct
        assertEquals(expectedTasks, totalTasks);
    }

    @Test
    void testNumberOfScheduledTask() {
        double totalStudyTime = 8.5; // Total study time: 8.5 hours

        // Calculate the number of tasks
        int totalTasks = CalculativeAlgorithm.numberOfScheduledTask(totalStudyTime);

        // Calculation: 8.5 hours / 2 hours per task
        int expectedTasks = (int) Math.round(8.5 / 2);

        // Assert that the calculated number of tasks is correct
        assertEquals(expectedTasks, totalTasks);
    }

    // Temporary method to access pagesPerMin (since it is private)
    private double getPagesPerMin() {
        try {
            var field = CalculativeAlgorithm.class.getDeclaredField("pagesPerMin");
            field.setAccessible(true);
            return (double) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Could not access pagesPerMin", e);
        }
    }
}
