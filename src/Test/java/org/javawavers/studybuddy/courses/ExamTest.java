package org.javawavers.studybuddy.courses;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExamTest {

  @Test
  void testConstructorWithSubjectAndPages() {
    LocalDate examDate = LocalDate.now().plusDays(15);
    Exam exam = new Exam(new Subject("Math"), examDate, 100);

    assertEquals("Math", exam.getName());
    assertEquals(examDate, exam.getExamDate());
    assertEquals(100, exam.getPages());
  }

  @Test
  void testSetAndGetMethods() {
    LocalDate examDate = LocalDate.now().plusDays(10);
    Exam exam = new Exam(examDate, 50);

    exam.setPages(120);
    exam.setRevisionPerPages(5);
    exam.setTimePer20Slides(30);

    assertEquals(120, exam.getPages());
    assertEquals(5, exam.getRevisionPerPages());
    assertEquals(30, exam.getTimePer20Slides());
  }

  @Test
  void testIsDeadlineSoonTrue() {
    LocalDate examDate = LocalDate.now().plusDays(4);
    Exam exam = new Exam(examDate, 50);
    assertTrue(exam.isDeadLineSoon());
  }

  @Test
  void testIsDeadlineSoonFalse() {
    LocalDate examDate = LocalDate.now().plusDays(15);
    Exam exam = new Exam(examDate, 50);
    assertFalse(exam.isDeadLineSoon());
  }
}
