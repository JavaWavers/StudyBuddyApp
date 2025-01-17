package org.javawavers.studybuddy.courses;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a subject.
 * A subject includes details such as its name, difficulty level,
 * type, study goals, and related exams and assignments.
 */
public class Subject {
  /**
   * Enum representing the type of the course (e.g., theoretical, practical).
   */
  public enum SubjectType {
    Θεωρητικό, //Theoretical
    Θετικό, // Practical
    Συνδυασμός, // Combination
    Αγνωστο // Unknown
  }

  /**
   * Enum representing the study goal for the course (e.g., average, good, excellent).
   */
  public enum StudyGoal {
    AVERAGE,
    GOOD,
    EXCELLENT
  }

  private String courseName;
  private int difficultyLevel; // Difficulty level of the course
  private SubjectType subjectType; // Type of the course
  private StudyGoal studyGoal; // Study goal for the course
  private List<Exam> exams = new ArrayList<>(); // List of exams
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
  /**
   * Constructs a Subject with the given name.
   * Default values: difficulty level = 1, subject type = Αγνωστο, study goal = GOOD.
   *
   * @param courseName the name of the course
   */
  public Subject(String courseName) {
    this.courseName = courseName;
    this.difficultyLevel = 1; // Default difficulty level
    this.subjectType = SubjectType.Αγνωστο; // Default subject type
    this.studyGoal = StudyGoal.GOOD; // Default study goal
  }

  /**
  *constructor only for setting courseName, difficulty level and subject type.
   *
   */
  public Subject(String courseName, int difficultyLevel, SubjectType subjectType) {
    this.courseName = courseName;
    this.difficultyLevel = difficultyLevel;
    this.subjectType = subjectType;
  }

  /**
   *Constructor for setting course name,  difficulty Level, subject type, and study goal.
   */
  public Subject(
      String courseName, int difficultyLevel, SubjectType subjectType, StudyGoal studyGoal) {
    this.courseName = courseName;
    this.difficultyLevel = difficultyLevel;
    this.subjectType = subjectType;
    this.studyGoal = studyGoal;
  }

  /**
   *Methods to manage exams.
   */
  public void addExam(Exam exam) { // Add an exam to the course
    exams.add(exam);
  }

  public List<Exam> getExams() { // Retrieve the list of exams for the course
    return exams;
  }

  /**
   *Methods to manage assignments.
   */
  public void addAssignment(Assignment assignment) { // Add an assignment to the course
    assignments.add(assignment);
  }

  public List<Assignment> getAssignments() { // Retrieve the list of assignments for the course
    return assignments;
  }

  /**
   * Calculates the total estimated hours for all assignments in the course.
   *
   * @return the total estimated hours
   */
  public double getTotalAssHours() {
    double sum = 0.0;

    // Traverse through the assignments list
    for (Assignment assignment : assignments) {
      sum += assignment.getEstimateHours(); // Add the estimated hours of the current assignment
    }

    return sum;
  }

  @Override
  public String toString() {
    return "Subject{" + "courseName='" + courseName + '\''
            + ", difficultyLevel=" + difficultyLevel
            + ", subjectType=" + subjectType
            + ", studyGoal=" + studyGoal
            + ", totalExams=" + exams.size()
            + ", totalAssignments=" + assignments.size()
            + '}';
  }

}
