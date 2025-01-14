package org.javawavers.studybuddy.calculations;

/*
 * This class distributes three kinds of tasks (studying -1, repetition -2,
 * assignment -3) into the available days randomly. The algorithm produces 50
 *  valid results,where the tasks are distributed into the available studying
 * hours per day differently (although there are chances for the same results).
 * Then, each result gains a score based on certain criteria, described in the
 * README file. The result with the higher score is considered the final result
 * and is given to the user as a recommended studying schedule.
 */

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Subject;

public class SimulateAnnealing {
  /*
   * The 12 rows represent the maximum tasks that can be assigned to a day,
   * which are 12 tasks of a duration of two hours each. The 8 columns represent
   * the 7 days of the week, starting with Monday. The first column of the table
   * is not used to prevent misinterpretation by the table pointer
   * with a value of zero.
   */

  private static List<Subject> subjects; // List for subjects
  private static List<Task> tasks; // List for each task that is connected with a subject
  private static List<Dates> exams; // List for each exam that is connected with a subject
  private static List<ScheduleResult> scheduleResults; // List for the valid schedule results
  private static List<Dates>
      assignments; // List for each assignment that is connected with a subject

  public SimulateAnnealing() {
    subjects = new ArrayList<>();
    tasks = new ArrayList<>();
    exams = new ArrayList<>();
    assignments = new ArrayList<>();
    scheduleResults = new ArrayList<>();
  }

  // Add the users Subjects
  public static void addSubject() {
    for (Subject s : staticUser.getSubjects()) {
      subjects.add(s);
      // Sets exams for the subject
      subExams(s);
      // Creates tasks for the subject
      subTasks(s);
    }
  }

  // Setting exams for each subject
  private static void subExams(Subject subject) {
    // Check if the subject or its exams list is null
    if (subject.getExams() == null || subject.getExams().isEmpty()) {
      throw new IllegalArgumentException(
          "Subject or exam list is invalid. Exams must not be empty.");
    }
    Dates examDate = new Dates(subject, subject.getExams().get(0).getExamDate());
    exams.add(examDate);
  }

  // Setting Assignments
  private static void subAssignment() {
    for (Assignment a : staticUser.getAssignments()) {
      Dates assDate = new Dates(a.getName(), a.getDate());
      assignments.add(assDate);
      subTask2(a.getName(), a.getEstimateHours());
    }
  }

  // Creating tasks for each subject
  private static void subTasks(Subject subject) {
    if (Subject.getExams() == null || Subject.getExams().isEmpty()) {
      throw new IllegalArgumentException("Cannot create tasks: No exams found for the subject.");
    }
    // setting the difficulty level
    CalculativeAlgorithm.setPagesPerMin(Subject.getExams().get(0).getTimePer20Slides());
    // studying tasks
    int taskType1 = CalculativeAlgorithm.studyingTasks(subject);
    // Task creation for each task type
    for (int i = 0; i < taskType1; i++) {
      tasks.add(new Task(subject, 1)); // studying
    }
  }

  // Creating tasks for assignments
  private static void subTask2(String name, int estimateHours) {
    int taskType3 = CalculativeAlgorithm.numberOfScheduledTask(estimateHours);
    // Task creation type
    for (int i = 0; i < taskType3; i++) {
      tasks.add(new Task(name, 3)); // Assignment
    }
  }

  // getter for the exam list
  public static List<Dates> getExams() {
    return exams;
  }

  // getter for the assignment list
  public static List<Dates> getAssignments() {
    return assignments;
  }

  private static double bestScoring;
  private static List<Task> bestTask = new ArrayList<>();
  private static int[][] schedule;

  // getters
  public static int[][] getSchedule() {
    return schedule;
  }

  public static List<Task> getBestTask() {
    return bestTask;
  }

  public static List<Subject> getSubjects() {
    return subjects;
  }

  // Κατανομή tasks στο πρόγραμμα
  public static void scheduleResult() {
    /*
     * each time the method is called in order to produce the best result
     * the best scoring is set as zero and the list with the best distribution,
     * shuffles an array so that the order of the table elements
     * different from the one that is given
     * The procedure is done 50 times in order to produce 10 possible results
     * Then each list gets a score. The list with the higher score is set as the
     * bestTask
     */
    scheduleResults.clear();
    bestTask.clear();
    Availability.setAvPerDay();
    addSubject();
    subAssignment();
    // sort exams
    Dates.sortList(exams);
    Dates.sortList(assignments);
    // The column size of the table is determined by the last examination date
    // The column size of the table is determined by the last examination date
    int colSize = Dates.lastIsDue(exams, assignments);
    List<Task> copyTask;
    for (int i = 0; i < 50; i++) {

      copyTask = new ArrayList<>(tasks);
      double valResultScoring;

      int[][] vSchedule = TaskAssignment.assignTask(copyTask, colSize);

      // list scoring
      // calls static method calculateScore
      valResultScoring = Scoring.calculateScore(TaskAssignment.getTasks(), vSchedule, colSize);
      if (i == 0) {
        bestScoring = valResultScoring;
      }
      // method to assign all the unassigned task type 3
      ScheduleResult result =
          new ScheduleResult(valResultScoring, TaskAssignment.getTasks(), vSchedule);
      scheduleResults.add(result);
    }
    for (ScheduleResult sr : scheduleResults) {
      bestSchedule(sr.getScore(), sr.getTasks(), sr.getSchedule());
    }
    schedule = Validate.validateSchedule(schedule, bestTask);
    PrintSchedule.printSchedule(schedule, bestTask, colSize);
    CreateWeekDay createWeekDay = new CreateWeekDay();
    createWeekDay.managerWeekDay(schedule, bestTask, colSize);

    // clear the lists
    subjects.clear();
    tasks.clear();
    exams.clear();
    assignments.clear();
    scheduleResults.clear();
  }

  private static void bestSchedule(double valResultScoring, List<Task> taskList, int[][] sch) {
    // Update best scoring and schedule only if the new score is equal or better
    if (valResultScoring >= bestScoring) {
      bestScoring = valResultScoring;
      // Create a new list to avoid reference issues
      bestTask = new ArrayList<>(taskList);
      schedule = sch;
    }
  }
}
