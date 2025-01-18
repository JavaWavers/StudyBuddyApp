package org.javawavers.studybuddy.calculations;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DatesTest {

  @Test
  void testSortList() {
    List<Dates> dates = new ArrayList<>();
    dates.add(new Dates("Math", LocalDate.now().plusDays(10)));
    dates.add(new Dates("Science", LocalDate.now().plusDays(5)));

    Dates.sortList(dates);

    assertEquals("Science", dates.get(0).getSubName());
    assertEquals("Math", dates.get(1).getSubName());
  }

  @Test
  void testCheckDateBeforeDeadline() {
    Task task = new Task("Math", 1);
    List<Dates> exams = new ArrayList<>();
    exams.add(new Dates("Math", LocalDate.now().plusDays(5)));

    assertTrue(Dates.checkDate(task, 3, exams));
  }

  @Test
  void testCheckDateAfterDeadline() {
    Task task = new Task("Math", 1);
    List<Dates> exams = new ArrayList<>();
    exams.add(new Dates("Math", LocalDate.now().plusDays(2)));

    assertFalse(Dates.checkDate(task, 5, exams));
  }
}
