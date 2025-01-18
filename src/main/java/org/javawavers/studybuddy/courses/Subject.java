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
      Theoretical,
      Practical,
      Combination,
      Unknown
  }


  private String courseName;
  private int difficultyLevel; // Difficulty level of the course
  private SubjectType subjectType; // Type of the course
  private List<Exam> exams = new ArrayList<>(); // List of exams
  private int subjectId;

  // Getters and Setters
  public int getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
  }

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
    this.subjectType = SubjectType.Unknown; // Default subject type

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
   *Methods to manage exams.
   */
  public void addExam(Exam exam) { // Add an exam to the course
    exams.add(exam);
  }

  public List<Exam> getExams() { // Retrieve the list of exams for the course
    return exams;
  }

  @Override
  public String toString() {
    return "Subject{" + "courseName='" + courseName + '\''
            + ", difficultyLevel=" + difficultyLevel
            + ", subjectType=" + subjectType
            + ", totalExams=" + exams.size()
            + '}';
  }

}
