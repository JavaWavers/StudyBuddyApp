package org.javawavers.studybuddy;

import org.javawavers.studybuddy.courses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticUserTest {

    @BeforeEach
    public void setup() {
        // Δημιουργία ενός User αντικειμένου για χρήση στο StaticUser
        List<LocalDate> nonAvailDays = new ArrayList<>();
        nonAvailDays.add(LocalDate.of(2025, 1, 1));
        nonAvailDays.add(LocalDate.of(2025, 1, 2));

        List<Subject> subjects = new ArrayList<>();
        Subject s1 = new Subject("Maths", null,4);
        subjects.add(s1);
        Subject s2 = new Subject("History", null,4);
        subjects.add(s2);

        List<Assignment> assignments = new ArrayList<>();
        assignments.add(new Assignment("Assignment 1", LocalDate.of(2025, 1, 15),13,4));

        List<Exam> exams = new ArrayList<>();
        exams.add(new Exam(s2, LocalDate.of(2025, 2, 1),300));

        StaticUser.staticUser = new User("testUser", "test@example.com", "password123", new int[]{2, 3, 4, 2, 3, 5, 1}, nonAvailDays, subjects, assignments, exams);
    }

    @Test
    public void testStaticUserInitialization() {
        assertNotNull(StaticUser.staticUser, "StaticUser.staticUser should be initialized");
        assertEquals("testUser", StaticUser.staticUser.getUsername(), "Username should match the initialized value");
        assertEquals("test@example.com", StaticUser.staticUser.getEmail(), "Email should match the initialized value");
    }

    @Test
    public void testUpdateUserDetails() {
        StaticUser.staticUser.setUsername("newUsername");
        StaticUser.staticUser.setEmail("newEmail@example.com");

        assertEquals("newUsername", StaticUser.staticUser.getUsername(), "Username should update correctly");
        assertEquals("newEmail@example.com", StaticUser.staticUser.getEmail(), "Email should update correctly");
    }

    @Test
    public void testAddNonAvailDay() {
        StaticUser.staticUser.addNonAvailDays(LocalDate.of(2025, 1, 3));

        assertTrue(StaticUser.staticUser.getNonAvailDays().contains(LocalDate.of(2025, 1, 3)),
                "Non-available day should be added");
    }

    @Test
    public void testRemoveNonAvailDay() {
        StaticUser.staticUser.removeNonAvailDays(LocalDate.of(2025, 1, 1));

        assertFalse(StaticUser.staticUser.getNonAvailDays().contains(LocalDate.of(2025, 1, 1)),
                "Non-available day should be removed");
    }

    @Test
    public void testAddSubject() {
        Subject newSubject = new Subject("Biology", null,6);
        StaticUser.staticUser.addSubject(newSubject);

        assertTrue(StaticUser.staticUser.getSubjects().contains(newSubject), "Subject should be added to the list");
    }

    @Test
    public void testRemoveSubject() {
        Subject subjectToRemove = StaticUser.staticUser.getSubjects().get(0);
        StaticUser.staticUser.removeSubject(subjectToRemove);

        assertFalse(StaticUser.staticUser.getSubjects().contains(subjectToRemove), "Subject should be removed from the list");
    }
}
