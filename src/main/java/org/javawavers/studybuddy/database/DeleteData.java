package org.javawavers.studybuddy.database;

import org.javawavers.studybuddy.courses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeleteData {
    public static void deleteExam(Exam exam) {
        String sql = "DELETE FROM Exam WHERE examID = ?";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, exam.getExamID());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Η εξέταση διαγράφτηκε με επιτυχία.");
            } else {
                System.out.println("Δεν βρέθηκε εξέταση με αυτό το ID.");
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την διαγραφή εξέτασης: " + e.getMessage());
        }
    }

    public static void deleteSubject(Subject subject) {
        String sql = "DELETE FROM Subject WHERE subjectID = ?";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, subject.getSubjectId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Το μάθημα διαγράφτηκε με επιτυχία.");
            } else {
                System.out.println("Δεν βρέθηκε μάθημα με αυτό το ID.");
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την διαγραφή μαθήματος: " + e.getMessage());
        }
    }

    public static void deleteAssignment(Assignment assignment) {
        String sql = "DELETE FROM Assignment WHERE assignmentID = ?";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, assignment.getAssignmentId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Η assignment διαγράφτηκε με επιτυχία.");
            } else {
                System.out.println("Δεν βρέθηκε assignment με αυτό το ID.");
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την διαγραφή assignment: " + e.getMessage());
        }
    }

    public static void deleteTask(ScheduledTask task) {
        String sql = "DELETE FROM Task WHERE taskID = ?";
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

    public static void deleteNonAvDay(LocalDate date) {
        String sql = "DELETE FROM NonAvDates WHERE date = ? AND userID = ?";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, date.toString());
            ps.setInt(2, StaticUser.staticUser.getUserID());

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
}

