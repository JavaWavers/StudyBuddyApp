package org.javawavers.studybuddy.calculations;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepetitionTest {

  private List<Task> tasks;
  private LocalDate examDate;

  @BeforeEach
  void setUp() {
    // Initialize tasks and exam date for each test
    tasks = new ArrayList<>();
    examDate = LocalDate.now().plusDays(40); // Exam date 40 days from today
  }

  @Test
  void testGenerateRepetitionsNullTaskList() {
    Task studyTask = new Task("Math", 1);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              Repetition.generateRepetitions(null, studyTask, examDate, 0);
            });

    assertTrue(exception.getMessage().contains("The task list can't be null."));
  }

  @Test
  void testGenerateRepetitionsNullStudyTask() {
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              Repetition.generateRepetitions(tasks, null, examDate, 0);
            });

    assertTrue(exception.getMessage().contains("The study task can't be null."));
  }

  @Test
  void testGenerateRepetitionsNegativeDay() {
    Task studyTask = new Task("Math", 1);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              Repetition.generateRepetitions(tasks, studyTask, examDate, -1);
            });

    assertTrue(exception.getMessage().contains("The day can't be negative."));
  }

  @Test
  void testIntervalRepGeneratesCorrectDates() {
    List<Repetition.RepetitionTask> repetitionTasks = new ArrayList<>();
    LocalDate studyDate = LocalDate.now();
    String subject = "Math";

    Repetition.intervalRep(repetitionTasks, studyDate, examDate, subject);

    // Check that the fixed intervals (1, 7, 16, 35) were generated
    assertEquals(4, repetitionTasks.size());
    assertEquals(studyDate.plusDays(1), repetitionTasks.get(0).getDate());
    assertEquals(studyDate.plusDays(7), repetitionTasks.get(1).getDate());
    assertEquals(studyDate.plusDays(16), repetitionTasks.get(2).getDate());
    assertEquals(studyDate.plusDays(35), repetitionTasks.get(3).getDate());
  }

  @Test
  void testAssRepetitionsValidSchedule() {
    int[][] schedule = new int[12][7]; // 12 slots, 7 days
    TaskAssignment.setValSchedule(schedule);
    TaskAssignment.setRemainingHours(10.0);

    List<Repetition.RepetitionTask> repetitionTasks = new ArrayList<>();
    repetitionTasks.add(new Repetition.RepetitionTask("Math", LocalDate.now().plusDays(1)));

    tasks.add(new Task("Math", 1)); // Study task for "Math"

    List<Task> updatedTasks = Repetition.assRepetitions(repetitionTasks, tasks, "Math");

    // Check that a repetition task has been added
    assertEquals(2, updatedTasks.size());
    assertEquals(2, updatedTasks.get(1).getTaskType()); // Type 2: Repetition

    // Verify that the schedule has been updated
    assertNotEquals(0, TaskAssignment.getValSchedule()[0][1]); // Task added to day 1
  }

  @Test
  void testAssRepetitionsWithFullSchedule() {
    int[][] schedule = new int[12][7]; // 12 slots, 7 days
    for (int i = 0; i < 12; i++) {
      schedule[i][1] = 1; // Fill all slots for day 1
    }
    TaskAssignment.setValSchedule(schedule);
    TaskAssignment.setRemainingHours(10.0);

    List<Repetition.RepetitionTask> repetitionTasks = new ArrayList<>();
    repetitionTasks.add(new Repetition.RepetitionTask("Math", LocalDate.now().plusDays(1)));

    tasks.add(new Task("Math", 1)); // Study task for "Math"

    List<Task> updatedTasks = Repetition.assRepetitions(repetitionTasks, tasks, "Math");

    // Check that no new tasks were added to a full day
    assertEquals(1, updatedTasks.size()); // No new tasks added
    assertEquals(1, schedule[0][1]); // Original task remains
  }
}
