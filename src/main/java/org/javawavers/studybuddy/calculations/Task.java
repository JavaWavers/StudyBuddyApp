package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.Subject;

/**
 * The Task class represents a task associated with a subject, such as studying, revision, or assignments.
 * Each task has a type, associated subject, and duration in hours.
 * The task types are:
 * 1: Studying,
 * 2: Revision,
 * 3: Assignment.
 * This class provides methods to retrieve and modify task properties, as well as a string representation of the task.
 */
public class Task {
  private String subjName;
  private int taskType; // 1: Studying , 2: revision, 3: assignment

  /**
   * stores the time required for studying for the task.
   */
  private double taskHours;


  /**
   * Constructs a Task object with the given Subject and task type.
   * The task type determines the duration of the task:
   * - Studying and Assignments have a duration of 2 hours.
   * - Revision tasks have a duration of 1/3 hour (20 minutes).
   *
   * @param subj The Subject object associated with the task.
   * @param taskType The type of the task (1: Studying, 2: Revision, 3: Assignment).
   */
  public Task(Subject subj, int taskType) {
    subjName = subj.getCourseName();
    this.taskType = taskType;
    if (taskType == 1 || taskType == 3) {
      this.taskHours = 2.0;
    } else {
      this.taskHours = 1.0 / 3.0;
    }
  }

  /**
   * Constructs a Task object with the given subject name and task type.
   * The task type determines the duration of the task:
   * - Studying and Assignments have a duration of 2 hours.
   * - Revision tasks have a duration of 1/3 hour (20 minutes).
   *
   * @param subj The name of the subject associated with the task.
   * @param taskType The type of the task (1: Studying, 2: Revision, 3: Assignment).
   */
  public Task(String subj, int taskType) {
    subjName = subj;
    this.taskType = taskType;
    if (taskType == 1 || taskType == 3) {
      this.taskHours = 2.0;
    } else {
      this.taskHours = 1.0 / 3.0;
    }
  }

  // get subject's name
  public String getSubject() {
    return subjName;
  }

  // get task's type
  public int getTaskType() {
    return taskType;
  }

  // get task's hours
  public double getTaskHours() {
    return taskHours;
  }

  /*
   * set task's hours
   * The above method is only required in task Type 2
   * when the tasks merge is done
   */
  public void setTaskHours(double hours) {
    this.taskHours = hours;
  }

  @Override
  public String toString() {
    String type = switch (taskType) {
      case 1 -> "Διάβασμα"; // Studying
      case 2 -> "Επανάληψη"; // Revision
      case 3 -> "Εργασία"; // Assignment
      default -> "Άγνωστο"; // Unknown
    };

    /* Determine the task type */

    // Separate hours and minutes from task hours
    int hours = (int) taskHours; // Whole hours
    int min = (int) ((taskHours - hours) * 60); // Remaining minutes

    // Build the string representation based on hours and minutes
    if (hours > 0 && min > 0) {
      return subjName + " - " + type + " (" + hours + " ώρες και " + min + " λεπτά)";
    } else if (hours > 0) {
      return subjName + " - " + type + " (" + hours + " ώρες)";
    } else if (min > 0) {
      return subjName + " - " + type + " (" + min + " λεπτά)";
    } else {
      return subjName + " - " + type + " (0 λεπτά)"; // Fallback for zero duration
    }
  }
}
