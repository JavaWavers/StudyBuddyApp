package org.javawavers.studybuddy.courses;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Task;
import org.javawavers.studybuddy.calculations.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  private User user;

  @BeforeEach
  void setUp() {
    // Initialize a user object before each test
    user = new User("testuser", "test@example.com", "password123");
  }

  @Test
  void testAddSubject() {
    Subject subject = new Subject("Math");
    user.addSubject(subject);

    assertEquals(1, user.getSubjects().size());
    assertEquals(subject, user.getSubjects().get(0));
  }

  @Test
  void testRemoveSubject() {
    Subject subject = new Subject("Math");
    user.addSubject(subject);

    user.removeSubject(subject);

    assertTrue(user.getSubjects().isEmpty());
  }

  @Test
  void testUpdateSubject() {
    Subject oldSubject = new Subject("Math");
    Subject newSubject = new Subject("Science");

    user.addSubject(oldSubject);
    user.updateSubject(0, newSubject);

    assertEquals(1, user.getSubjects().size());
    assertEquals(newSubject, user.getSubjects().get(0));
  }

  @Test
  void testAddAssignment() {
    Assignment assignment = new Assignment("MathsAss", LocalDate.of(2025, 2, 18), 13, 4);
    user.addAssignment(assignment);

    assertEquals(1, user.getAssignments().size());
    assertEquals(assignment, user.getAssignments().get(0));
  }

  @Test
  void testRemoveAssignment() {
    Assignment assignment = new Assignment("Mathsασσ", LocalDate.of(2025, 2, 18), 13, 4);
    user.addAssignment(assignment);

    user.removeAssignment(assignment);

    assertTrue(user.getAssignments().isEmpty());
  }

  @Test
  void testUpdateAssignment() {
    Assignment oldAssignment = new Assignment("Math Homework", LocalDate.now().plusDays(17), 13, 4);
    Assignment newAssignment =
        new Assignment("Science Project", LocalDate.now().plusDays(18), 13, 5);

    user.addAssignment(oldAssignment);
    user.updateAssignment(0, newAssignment);

    assertEquals(1, user.getAssignments().size());
    assertEquals(newAssignment, user.getAssignments().get(0));
  }

  @Test
  void testAddExam() {
    Exam exam =
        new Exam(new Subject("Maths", 5, Subject.SubjectType.Unknown), LocalDate.now(), 600);
    user.addExam(exam);

    assertEquals(1, user.getExams().size());
    assertEquals(exam, user.getExams().get(0));
  }

  @Test
  void testRemoveExam() {
    Exam exam =
        new Exam(new Subject("Maths", 5, Subject.SubjectType.Unknown), LocalDate.now(), 600);
    user.addExam(exam);

    user.removeExam(exam);

    assertTrue(user.getExams().isEmpty());
  }

  @Test
  void testUpdateExam() {
    Exam oldExam =
        new Exam(new Subject("Maths", 5, Subject.SubjectType.Unknown), LocalDate.now(), 600);
    Exam newExam =
        new Exam(
            new Subject("Maths", 5, Subject.SubjectType.Unknown), LocalDate.now().plusDays(1), 600);

    user.addExam(oldExam);
    user.updateExam(0, newExam);

    assertEquals(1, user.getExams().size());
    assertEquals(newExam, user.getExams().get(0));
  }

  @Test
  void testAddNonAvailDays() {
    LocalDate date = LocalDate.now();
    user.addNonAvailDays(date);

    assertEquals(1, user.getNonAvailDays().size());
    assertEquals(date, user.getNonAvailDays().get(0));
  }

  @Test
  void testRemoveNonAvailDays() {
    LocalDate date = LocalDate.now();
    user.addNonAvailDays(date);

    user.removeNonAvailDays(date);

    assertTrue(user.getNonAvailDays().isEmpty());
  }

  @Test
  void testUpdateNonAvailDays() {
    LocalDate oldDate = LocalDate.now();
    LocalDate newDate = LocalDate.now().plusDays(1);

    user.addNonAvailDays(oldDate);
    user.updateNonAvailDays(0, newDate);

    assertEquals(1, user.getNonAvailDays().size());
    assertEquals(newDate, user.getNonAvailDays().get(0));
  }

  @Test
  void testCalculateCurrentWeek() {
    // Prepare test data
    Day day = new Day();
    Task task = new Task("Test Task", 2);
    // day.todayTasks.add(task);

    Week week = new Week();
    // week.addDay(day);

    List<Week> weeks = new ArrayList<>();
    weeks.add(week);

    user.setTotalWeeks(weeks);
  }

  @Test
  void testUpdateAvPerDay() {
    int[] availability = {2, 2, 2, 2, 2, 2, 2}; // Availability for each day of the week
    user.setAvPerDay(availability);

    user.updateAvPerDay(0, 4); // Update availability for Monday

    assertEquals(4, user.getAvPerDay()[0]);
  }
}
