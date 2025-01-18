package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Week} class represents a week containing a list of {@code Day} objects. This class
 * allows for managing the days of the week, retrieving specific days, and setting a custom list of
 * days.
 */
public class Week {
  private List<Day> daysOfWeek;

  /** constructor. */
  public Week() {
    daysOfWeek = new ArrayList<>();
  }

  /** returns the whole list with the days for the week. */
  public List<Day> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(List<Day> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  /**
   * returns the specified day from the list. ex:* Day tuesday = week.getTheDay(2); // Retrieves
   * "Tuesday"
   */
  public Day getTheDay(int index) {
    try {
      if (index < 1 || index > 7) {
        throw new IndexOutOfBoundsException("Index must be greater than 0, add less than 11");
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    return daysOfWeek.get(index);
  }
}
