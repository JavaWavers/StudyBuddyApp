package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;

/** this class contains the methods for deleting data. */
public class DeleteData {

  /** delete exam. */
  public static void deleteExam(Exam exam) {
    String sql = "DELETE FROM Exam WHERE examId = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, exam.getExamId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Η εξέταση διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε εξέταση με αυτό το Id.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή εξέτασης: " + e.getMessage());
    }
  }

  /** delete subject. */
  public static void deleteSubject(Subject subject) {
    String sql = "DELETE FROM Subject WHERE subjectId = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, subject.getSubjectId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Το μάθημα διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε μάθημα με αυτό το Id.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή μαθήματος: " + e.getMessage());
    }
  }

  /** delete assignment. */
  public static void deleteAssignment(Assignment assignment) {
    String sql = "DELETE FROM Assignment WHERE assignmentId = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, assignment.getAssignmentId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Η assignment διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε assignment με αυτό το Id.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή assignment: " + e.getMessage());
    }
  }

  /** delete task. */
  public static void deleteTask(ScheduledTask task) {
    String sql = "DELETE FROM Task WHERE taskId = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, task.getTaskId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("ScheduledTask διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε ScheduledTask με αυτό το Id.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή ScheduledTask: " + e.getMessage());
    }
  }

  /** delete NonAvDay. */
  public static void deleteNonAvDay(LocalDate date) {
    String sql = "DELETE FROM NonAvDates WHERE date = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, date.toString());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("NonAvDate διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε NonAvDate");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή NonAvDate: " + e.getMessage());
    }
  }

  /** delete completed task. */
  public static void deleteCompletedTask(ScheduledTask task) {
    String sql = "DELETE FROM CompletedTask WHERE taskId = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, task.getTaskId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("ScheduledTask διαγράφτηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε ScheduledTask με αυτό το ID.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή ScheduledTask: " + e.getMessage());
    }
  }
}

