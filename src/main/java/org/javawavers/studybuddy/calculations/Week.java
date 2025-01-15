package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

/**
 * The Week class represents a week consisting of a list of days.
 * It holds and manages the days of the week and provides methods to access them.
 * The class allows for storing the days in a list and provides functionality to retrieve
 * either the entire list of days or a specific day based on an index.
 *
 */
public class Week {
  List<Day> daysOfWeek;

  /**
   * Default constructor.
   */
  public Week() {
    daysOfWeek = new ArrayList<>();
  }

  // returns the whole list
  public List<Day> getDaysOfWeek() {
    return daysOfWeek;
  }

  /**
   * Returns the specified day from the list of days in the week based on the given index.
   *
   * @param index The index of the day to be retrieved (0-based).
   * @return The `Day` object at the specified index in the list of days of the week.
   * @throws IndexOutOfBoundsException If the index is out of range
   *     (index < 0 || index >= daysOfWeek.size()).
   */
  public Day getTheDay(int index) {
    return daysOfWeek.get(index);
  }
}
