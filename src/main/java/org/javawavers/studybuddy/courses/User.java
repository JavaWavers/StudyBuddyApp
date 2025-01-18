package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Task;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.database.DeleteData;

/**
 * Represents a user in the StudyBuddy app. The user can manage their subjects, assignments, exams,
 * availability, and schedules.
 */
public class User {
  private String username; // The username of the user
  private String email; // The email address of the user
  private String password; // The password of the user
  private int userId; // The unique identifier for the user.
  int[] avPerDay; // availability for each day of the week
  private Week currentWeek; // The current week being tracked by the user.
  List<LocalDate> nonAvailDays = new ArrayList<>();
  List<Subject> subjects = new ArrayList<>();
  List<Assignment> assignments = new ArrayList<>();
  List<Exam> exams = new ArrayList<>();

  List<Week> totalWeeks = new ArrayList<>(); // ////////////////////////////////

  List<Task> tasks;
  List<Day> days;
  List<ScheduledTask> scheduledTasks = new ArrayList<>();

  /** Default constructor. */
  public User() {}

  /**
   * Constructor for a user logging in without a generated schedule.
   *
   * @param username the username of the user
   * @param email the email address of the user
   * @param password the password of the user
   * @param avPerDay availability for each day of the week
   * @param nonAvailDays a list of dates when the user is unavailable
   * @param subjects a list of subjects associated with the user
   * @param assignments a list of assignments associated with the user
   * @param exams a list of exams associated with the user
   */
  public User(
      String username,
      String email,
      String password,
      int[] avPerDay,
      List<LocalDate> nonAvailDays,
      List<Subject> subjects,
      List<Assignment> assignments,
      List<Exam> exams) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.avPerDay = avPerDay;
    this.nonAvailDays = nonAvailDays;
    this.subjects = subjects;
    this.assignments = assignments;
    this.exams = exams;
  }

  /**
   * Constructor for user sign-in.
   *
   * @param username the username of the user
   * @param email the email address of the user
   * @param password the password of the user
   */
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  /**
   * Constructor for a user with a unique ID.
   *
   * @param userId the unique ID of the user
   * @param username the username of the user
   * @param email the email address of the user
   * @param password the password of the user
   */
  public User(int userId, String username, String email, String password) {
    this.username = username;
    this.userId = userId;
    this.email = email;
    this.password = password;
  }

  /**
   * Constructor for a user logging in with a generated program.
   *
   * @param userId the unique ID of the user
   * @param username the username of the user
   * @param email the email address of the user
   * @param password the password of the user
   * @param avPerDay availability for each day of the week
   * @param nonAvailDays a list of dates when the user is unavailable
   * @param subjects a list of subjects associated with the user
   * @param assignments a list of assignments associated with the user
   * @param exams a list of exams associated with the user
   * @param tasks a list of tasks assigned to the user
   * @param days a list of days with associated tasks for the user
   */
  public User(
      int userId,
      String username,
      String email,
      String password,
      int[] avPerDay,
      List<LocalDate> nonAvailDays,
      List<Subject> subjects,
      List<Assignment> assignments,
      List<Exam> exams,
      List<Task> tasks,
      List<Day> days) {
    this.email = email;
    this.password = password;
    this.avPerDay = avPerDay;
    this.nonAvailDays = nonAvailDays;
    this.subjects = subjects;
    this.assignments = assignments;
    this.exams = exams;
    this.tasks = tasks;
    this.days = days;
    this.userId = userId;
  }

  // getters and setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int[] getAvPerDay() {
    return avPerDay;
  }

  public void setAvPerDay(int[] avPerDay) {
    this.avPerDay = avPerDay;
  }

  public List<LocalDate> getNonAvailDays() {
    return nonAvailDays;
  }

  public void setNonAvailDays(List<LocalDate> nonAvailDays) {
    this.nonAvailDays = nonAvailDays;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }

  public List<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(List<Subject> subjects) {
    this.subjects = subjects;
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public void setAssignments(List<Assignment> assignments) {
    this.assignments = assignments;
  }

  public List<Exam> getExams() {
    return exams;
  }

  public void setExams(List<Exam> exams) {
    this.exams = exams;
  }

  public List<Week> getTotalWeeks() {
    return totalWeeks;
  }

  public void setTotalWeeks(List<Week> totalWeeks) {
    this.totalWeeks = totalWeeks;
  }

  public Week getCurrentWeek() {
    return currentWeek;
  }

  public void setCurrentWeek(Week currentWeek) {
    this.currentWeek = currentWeek;
  }

  public void setDays(List<Day> days) {
    this.days = days;
  }

  public void setTasks(List<ScheduledTask> tasks) {
    this.scheduledTasks = tasks;
  }

  /**
   * Adds a subject to the user's list of subjects.
   *
   * @param subj the subject to add
   */
  public void addSubject(Subject subj) {
    subjects.add(subj);
  }

  /**
   * Removes a subject from the user's list of subjects.
   *
   * @param subj the subject to remove
   */
  public void removeSubject(Subject subj) {
    subjects.remove(subj);
    DeleteData.deleteSubject(subj);
  }

  /**
   * Updates a subject in the user's list of subjects at the specified index.
   *
   * @param index the index of the subject to update
   * @param subj the updated subject
   */
  public void updateSubject(int index, Subject subj) {
    subjects.set(index, subj);
  }

  /** adds an assignment. */
  public void addAssignment(Assignment assign) {
    assignments.add(assign);
  }

  /** removes an assignment. */
  public void removeAssignment(Assignment assign) {
    assignments.remove(assign);
    DeleteData.deleteAssignment(assign);
  }

  /** Updates an assignment. */
  public void updateAssignment(int index, Assignment assign) {
    assignments.set(index, assign);
  }

  /** Adds an exam in the exam list. */
  public void addExam(Exam exam) {
    exams.add(exam);
  }

  /** Removes an Exam. */
  public void removeExam(Exam exam) {
    exams.remove(exam);
    DeleteData.deleteExam(exam);
  }

  /** Updates an exam. */
  public void updateExam(int index, Exam exam) {
    exams.set(index, exam);
  }

  /** Add days that the user is not available to study at all. */
  public void addNonAvailDays(LocalDate date) {
    nonAvailDays.add(date);
  }

  /** Remove days that the user is not available to study at all. */
  public void removeNonAvailDays(LocalDate date) {
    nonAvailDays.remove(date);
    DeleteData.deleteNonAvDay(date);
  }

  /** Update days that the user is not available to study at all. */
  public void updateNonAvailDays(int index, LocalDate date) {
    nonAvailDays.set(index, date);
  }

  /** Update the users availability (in hours) for a day. */
  public void updateAvPerDay(int index, int av) {
    avPerDay[index] = av;
  }

  /** Calculates and sets the current week based on today's date. */
  public void calculateCurrentWeek() {
    LocalDate today = LocalDate.now();
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        if (!d.getTodayTasks().isEmpty()
            && d.getTodayScheduledTask(0).getTaskDate().equals(today)) {
          this.currentWeek = w;
          return;
        }
      }
    }
  }
}
