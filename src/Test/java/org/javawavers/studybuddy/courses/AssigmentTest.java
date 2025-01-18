package org.javawavers.studybuddy.courses;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    void testDefaultConstructor() {
        Assignment assignment = new Assignment();
        assertNull(assignment.getTitle());
        assertNull(assignment.getDeadline());
    }

    @Test
    void testParameterizedConstructorValidHours() {
        LocalDate deadline = LocalDate.now().plusDays(10);
        Assignment assignment = new Assignment("Test Assignment", deadline, 20, 3);

        assertEquals("Test Assignment", assignment.getTitle());
        assertEquals(deadline, assignment.getDeadline());
        assertEquals(20, assignment.getEstimateHours());
    }

    @Test
    void testParameterizedConstructorInvalidHours() {
        LocalDate deadline = LocalDate.now().plusDays(2); // 2 days = maxHours = 28
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("Invalid Assignment", deadline, 50, 3);
        });
        assertTrue(exception.getMessage().contains("Invalid estimate hours"));
    }

    @Test
    void testIsDeadlineSoonTrue() {
        LocalDate deadline = LocalDate.now().plusDays(3);
        Assignment assignment = new Assignment("Test Assignment", deadline, 10, 3);
        assertTrue(assignment.isDeadLineSoon());
    }

    @Test
    void testIsDeadlineSoonFalse() {
        LocalDate deadline = LocalDate.now().plusDays(20);
        Assignment assignment = new Assignment("Test Assignment", deadline, 10, 3);
        assertFalse(assignment.isDeadLineSoon());
    }
}