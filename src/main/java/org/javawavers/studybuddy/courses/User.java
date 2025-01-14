package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.javawavers.studybuddy.calculations.*;

public class User {
  private String username;
  private String email;
  private String password;
  private int userID;
  int[] avPerDay; // availability for each day of the week
  private Week currentWeek;
  List<LocalDate> nonAvailDays = new ArrayList<>();
  List<Subject> subjects = new ArrayList<>();
  List<Assignment> assignments = new ArrayList<>();
  List<Exam> exams = new ArrayList<>();

  List<Week> totalWeeks = new ArrayList<>(); // ////////////////////////////////

  List<Task> tasks;
  List<Day> days;
  int[][] schedule; // the array with the indexes from the tasks list

  public User() {}

  // for the login without having generated schedule
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

  // constructor for the sign-in
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(int userID, String username, String email, String password) {
    this.username = username;
    this.userID = userID;
    this.email = email;
    this.password = password;
  }

  // constructor for the login having generated a program
  public User(
      int userID,
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
    this.userID = userID;
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

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getUserID() {
    return userID;
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

  // add, remove and update objects from the lists
  public void addSubject(Subject subj) {
    subjects.add(subj);
  }

  public void removeSubject(Subject subj) {
    subjects.remove(subj);
  }

  public void updateSubject(int index, Subject subj) {
    subjects.set(index, subj);
  }

  public void addAssignment(Assignment assign) {
    assignments.add(assign);
  }

  public void removeAssignment(Assignment assign) {
    assignments.remove(assign);
  }

  public void updateAssignment(int index, Assignment assign) {
    assignments.set(index, assign);
  }

  public void addExam(Exam exam) {
    exams.add(exam);
  }

  public void removeExam(Exam exam) {
    exams.remove(exam);
  }

  public void updateExam(int index, Exam exam) {
    exams.set(index, exam);
  }

  public void addNonAvailDays(LocalDate date) {
    nonAvailDays.add(date);
  }

  public void removeNonAvailDays(LocalDate date) {
    nonAvailDays.remove(date);
  }

  public void updateNonAvailDays(int index, LocalDate date) {
    nonAvailDays.set(index, date);
  }

  public void updateAvPerDay(int index, int av) {
    avPerDay[index] = av;
  }

  public void calculateCurrentWeek() {
    LocalDate today = LocalDate.now();
    for (Week w : totalWeeks) {
      for (Day d : w.getDaysOfWeek()) {
        if (!d.getTodayTasks().isEmpty() && d.getTodayScheduledTask(0).getTaskDate().equals(today)) {
          this.currentWeek = w;
          return;
        }
      }
    }
    throw new IllegalArgumentException("No week found for the current date");
  }
  /*
   input: list tasks and list days
     output: creates the array schedule
  public void createSchedule() {
      if (!days.isEmpty()) {
          int column = days.size();
          int[][] sch = new int[12][column];
          for (int j = 0; j < column; j++) {
              org.javawavers.studybuddy.courses.Day obj = days.get(j);
              int i = 0;
              while (!obj.dayTasks.isEmpty()) {
                  for (int r = 0; r < tasks.size(); r++) {
                      if (obj.equals(tasks.get(r))) {
                          sch[i][j] = r;
                          break;
                      }
                  }
              }
              this.schedule = sch;
          }
      } else {
          throw new IllegalArgumentException("Η λίστα των days είναι άδεια");
      }
  }*/
}
