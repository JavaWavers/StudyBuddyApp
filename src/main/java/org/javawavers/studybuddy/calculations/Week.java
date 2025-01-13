package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

public class Week {
  private List<Day> daysOfWeek;

  // constructor
  public Week() {
    daysOfWeek = new ArrayList<>();
  }

  // returns the whole list
  public List<Day> getDaysOfWeek() {
    return daysOfWeek;
  }

  // returns the specified task from the list
  public Day getTheDay(int index) {
    return daysOfWeek.get(index);
  }
}
