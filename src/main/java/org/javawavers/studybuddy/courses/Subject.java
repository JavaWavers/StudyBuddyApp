package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Subject {
    // Enum for the course types
    public enum SubjectType {
        THEORETICAL, PRACTICAL, BOTH, UNDEFINED
    }

    // Enum for the study goal
    public enum StudyGoal {
        AVERAGE, GOOD, EXCELLENT
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

    // Constructors
    // Constructor with just the course name
    public Subject(String courseName) {
        this.courseName = courseName;
        this.difficultyLevel = 1; // Default difficulty level
        this.subjectType = SubjectType.UNDEFINED; // Default subject type
        this.studyGoal = StudyGoal.GOOD; // Default study goal
    }

    // Constructor with all fields
    public Subject(String courseName, int difficultyLevel, SubjectType subjectType, StudyGoal studyGoal) {
        this.courseName = courseName;
        this.difficultyLevel = difficultyLevel;
        this.subjectType = subjectType;
        this.studyGoal = studyGoal;
    }

    // Methods to manage exams
    public void addExam(Exam exam) { // Add an exam to the course
        exams.add(exam);
    }

    public List<Exam> getExams() { // Retrieve the list of exams for the course
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

  public static class Exam extends SubjectElement {

      private int pages;
      private int revisionPerXPages;
      private double timePer20Slides;

      // Constructors for different versions of Exam class
      public Exam(LocalDate examDate, int pages) {
          super(examDate, null);
          this.pages = pages;
      }

      public Exam(Subject subject, LocalDate examDate, int pages) {
          super(examDate, subject.getCourseName());
          this.pages = pages;
      }

      public Exam(Subject subject, LocalDate examDate, int pages, String name) {
          super(examDate, subject.getCourseName());
          this.pages = pages;
          this.name = name;

      }

      public Exam(Subject subject, LocalDate examDate, int pages, String name, int revisionPerXPages,
                  int timePer20Slides) {
          super(examDate, subject.getCourseName());
          this.pages = pages;
          this.name = name;
          this.revisionPerXPages = revisionPerXPages;
          this.timePer20Slides = timePer20Slides;

      }

      public Exam(int pages, int revisionPerXPages, LocalDate examDate, int timePer20Slides) {
          super(examDate, null);
          this.pages = pages;
          this.revisionPerXPages = revisionPerXPages;
          this.timePer20Slides = timePer20Slides;
      }

      // getters & setters
      // getSubjectName does not need to be overridden
      public void setExamDate(LocalDate examDate) {
          super.setDate(examDate);
      }

      public LocalDate getExamDate() {
          return super.getDate();
      }

      public int getPages() {
          return pages;
      }

      public void setPages(int pages) {
          this.pages = pages;
      }

      public int getRevisionPerXPages() {
          return revisionPerXPages;
      }

      public void setRevisionPerXPages(int revisionPerXPages) {
          this.revisionPerXPages = revisionPerXPages;
      }

      public double getTimePer20Slides() {
          return timePer20Slides;
      }

      public void setTimePer20Slides(double timePer20Slides) {
          this.timePer20Slides = timePer20Slides;
      }

      // toString (we can use it with ui)
      @Override
      public String toString() {
          StringBuilder builder = new StringBuilder(); // StringBuilder provides efficiency and flexibility
          builder.append("Εξέταση{Όνομα Μαθήματος: '").append(super.getName()).append("'");

          if (getExamDate() != null) {
              builder.append("\nΗμερομηνία εξέτασης: '").append(getExamDate()).append("'");
          }

          if (pages != 0) {
              builder.append("\nΣελίδες: '")
                      .append(pages)
                      .append("'");
          }

          if (revisionPerXPages != 0) {
              builder.append("\nΕπανάληψη ανά: '")
                      .append(revisionPerXPages)
                      .append("σελίδες'");
          }

          if (timePer20Slides != 0.0) {
              builder.append("\nΑπαιτούμενα λεπτά για 20 διαφάνειες: '")
                      .append(timePer20Slides)
                      .append("'");
          }

          builder.append("}");
          return builder.toString();
      }

      // Other Methods
      /*
       * Returns the Total Time Required for studying
       * based on pages and minutes per 20 pages
       */
      @Override
      public void isDeadLineSoonMessage() {
          // Calculate the remaining days until the exam date
          long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getExamDate());

          if (remainingDays <= 10 && remainingDays > 0) {
              System.out.println("Απομένουν μόνο " + remainingDays + " ημέρες μέχρι την εξέταση!");
          } else if (remainingDays == 0) {
              System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
          } else if (remainingDays < 0) {
              System.out.println("Η εξέταση έχει ήδη περάσει.");
          }
      }

      @Override
      // returns true if the deadline is in less than 5 days
      public boolean isDeadLineSoon() {
          long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getExamDate());
          return remainingDays <= 5;
      }
  }

  public static class Subject {
      // Enum for the course types
      public enum SubjectType {
          THEORETICAL, PRACTICAL, BOTH, UNDEFINED
      }

      // Enum for the study goal
      public enum StudyGoal {
          AVERAGE, GOOD, EXCELLENT
      }

      private String courseName;
      private int difficultyLevel; // Difficulty level of the course
      private SubjectType subjectType; // Type of the course
      private StudyGoal studyGoal; // Study goal for the course
      private List<Exam> exams = new ArrayList<>(); // List of exams
      private List<Assignment.Assignment> assignments = new ArrayList<>(); // List of assignments

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

      // Constructors
      // Constructor with just the course name
      public Subject(String courseName) {
          this.courseName = courseName;
          this.difficultyLevel = 1; // Default difficulty level
          this.subjectType = SubjectType.UNDEFINED; // Default subject type
          this.studyGoal = StudyGoal.GOOD; // Default study goal
      }

      // Constructor with all fields
      public Subject(String courseName, int difficultyLevel, SubjectType subjectType, StudyGoal studyGoal) {
          this.courseName = courseName;
          this.difficultyLevel = difficultyLevel;
          this.subjectType = subjectType;
          this.studyGoal = studyGoal;
      }

      public Subject (String courseName, SubjectType subjectType, int difficultyLevel){
          this.courseName = courseName;
          this.difficultyLevel = difficultyLevel;
          this.subjectType = subjectType;
      }

      // Methods to manage exams
      public void addExam(Exam exam) { // Add an exam to the course
          exams.add(exam);
      }

      public List<Exam> getExams() { // Retrieve the list of exams for the course
          return exams;
      }

      // Methods to manage assignments
      public void addAssignment(Assignment.Assignment assignment) { // Add an assignment to the course
          assignments.add(assignment);
      }

      public List<Assignment.Assignment> getAssignments() { // Retrieve the list of assignments for the course
          return assignments;
      }

      public double getTotalAssHours() {
          double sum = 0.0;

          // Traverse through the assignments list
          for (Assignment.Assignment assignment : assignments) {
              sum += assignment.getEstimateHours(); // Add the estimated hours of the current assignment
          }

          return sum;
      }

  }
}
