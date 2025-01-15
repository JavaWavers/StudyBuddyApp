package org.javawavers.studybuddy.courses;

import java.util.ArrayList;
import java.util.List;

public class Subject {
  // Enum for the course types
  public enum SubjectType {
    Θεωρητικό,
    Θετικό,
    Συνδυασμός,
    Αγνωστο
  }

  // Enum for the study goal
  public enum StudyGoal {
    AVERAGE,
    GOOD,
    EXCELLENT
  }

  private String courseName;
  private int difficultyLevel; // Difficulty level of the course
  private SubjectType subjectType; // Type of the course
  private StudyGoal studyGoal; // Study goal for the course
  private static List<Exam> exams = new ArrayList<>(); // List of exams
  private List<Assignment> assignments = new ArrayList<>(); // List of assignments

  // Getters and Setters
  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public int getDifficultyLevel() {
    return difficultyLevel;
  }

  public void setDifficultyLevel(int difficultyLevel) {
    this.difficultyLevel = difficultyLevel;
  }

  public SubjectType getSubjectType() {
    return subjectType;
  }

  public void setSubjectType(SubjectType subjectType) {
    this.subjectType = subjectType;
  }

  public StudyGoal getStudyGoal() {
    return studyGoal;
  }

  public void setStudyGoal(StudyGoal studyGoal) {
    this.studyGoal = studyGoal;
  }

  public void setExams(List<Exam> exams) {
    this.exams = exams;
  }

  // Constructors
  // Constructor with just the course name
  public Subject(String courseName) {
    this.courseName = courseName;
    this.difficultyLevel = 1; // Default difficulty level
    this.subjectType = SubjectType.Αγνωστο; // Default subject type
    this.studyGoal = StudyGoal.GOOD; // Default study goal
  }

  public Subject(String courseName, int difficultyLevel, SubjectType subjectType) {
    this.courseName = courseName;
    this.difficultyLevel = difficultyLevel;
    this.subjectType = subjectType;
  }

  // Constructor with all fields
  public Subject(
      String courseName, int difficultyLevel, SubjectType subjectType, StudyGoal studyGoal) {
    this.courseName = courseName;
    this.difficultyLevel = difficultyLevel;
    this.subjectType = subjectType;
    this.studyGoal = studyGoal;
  }

  public Subject(String courseName, SubjectType subjectType, int difficultyLevel) {
    this.courseName = courseName;
    this.difficultyLevel = difficultyLevel;
    this.subjectType = subjectType;
  }

  // Methods to manage exams
  public void addExam(Exam exam) { // Add an exam to the course
    exams.add(exam);
  }

  public static List<Exam> getExams() { // Retrieve the list of exams for the course
    return exams;
  }

  // Methods to manage assignments
  public void addAssignment(Assignment assignment) { // Add an assignment to the course
    assignments.add(assignment);
  }

  public List<Assignment> getAssignments() { // Retrieve the list of assignments for the course
    return assignments;
  }

  public double getTotalAssHours() {
    double sum = 0.0;

    // Traverse through the assignments list
    for (Assignment assignment : assignments) {
      sum += assignment.getEstimateHours(); // Add the estimated hours of the current assignment
    }

    return sum;
  }
}
