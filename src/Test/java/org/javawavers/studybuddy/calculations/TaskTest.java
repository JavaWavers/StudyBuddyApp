package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Subject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskConstructorForStudying() {
        Subject subject = new Subject("Science");
        Task task = new Task(subject, 1);

        assertEquals("Science", task.getSubject());
        assertEquals(1, task.getTaskType());
        assertEquals(2.0, task.getTaskHours());
    }

    @Test
    void testTaskConstructorForRevision() {
        Subject subject = new Subject("Science");
        Task task = new Task(subject, 2);

        assertEquals("Science", task.getSubject());
        assertEquals(2, task.getTaskType());
        assertEquals(1.0 / 3.0, task.getTaskHours());
    }

    @Test
    void testSetTaskHours() {
        Task task = new Task("Math", 2);
        task.setTaskHours(1.5);

        assertEquals(1.5, task.getTaskHours());
    }
}