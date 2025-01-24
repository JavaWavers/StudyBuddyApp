package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;

/** contains the methods for deleting data. */
public class DeleteData {
  public static void deleteExam(Exam exam) {
    String sql = "DELETE FROM Exam WHERE examID = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, exam.getExamId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Η εξέταση διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε εξέταση με αυτό το ID στη βάση δεδομένων.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή εξέτασης στη βάση δεδομένων: " + e.getMessage());
    }
  }

  /**
   * Deletes a subject from the database.
   *
   * @param subject the subject to delete.
   */
  public static void deleteSubject(Subject subject) {
    String sql = "DELETE FROM Subject WHERE subjectID = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, subject.getSubjectId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Το μάθημα διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε μάθημα με αυτό το ID στη βάση δεδομένων.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή μαθήματος στη βάση δεδομένων: " + e.getMessage());
    }
  }

  /**
   * Deletes an assignment from the database.
   *
   * @param assignment the assignment to delete.
   */
  public static void deleteAssignment(Assignment assignment) {
    String sql = "DELETE FROM Assignment WHERE assignmentID = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, assignment.getAssignmentId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Η assignment διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε assignment με αυτό το ID στη βάση δεδομένων.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή assignment στη βάση δεδομένων: " + e.getMessage());
    }
  }

  /**
   * Deletes a scheduled task from the database.
   *
   * @param task the task to delete.
   */
  public static void deleteTask(ScheduledTask task) {
    String sql = "DELETE FROM Task WHERE taskID = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, task.getTaskId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("ScheduledTask διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε ScheduledTask με αυτό το ID στη βάση δεδομένων.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή ScheduledTask στη βάση δεδομένων: " + e.getMessage());
    }
  }

  /**
   * Deletes a non-available date from the database.
   *
   * @param date the date to delete.
   */
  public static void deleteNonAvDay(LocalDate date) {
    String sql = "DELETE FROM NonAvDates WHERE date = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, date.toString());
      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("NonAvDate διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε NonAvDate στη βάση δεδομένων");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή NonAvDate στη βάση δεδομένων: " + e.getMessage());
    }
  }

  /**
   * Deletes a completed task from the database.
   *
   * @param task the task to delete.
   */
  public static void deleteCompletedTask(ScheduledTask task) {
    String sql = "DELETE FROM CompletedTask WHERE taskID = ?";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, task.getTaskId());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("ScheduledTask διαγράφτηκε με επιτυχία στη βάση δεδομένων.");
      } else {
        System.out.println("Δεν βρέθηκε ScheduledTask με αυτό το ID στη βάση δεδομένων.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την διαγραφή ScheduledTask στη βάση δεδομένων: " + e.getMessage());
    }
  }
}
