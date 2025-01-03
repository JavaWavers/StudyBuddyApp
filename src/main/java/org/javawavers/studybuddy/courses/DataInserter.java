package org.javawavers.studybuddy.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInserter {

    public static void insertUser(String username, String name, String password, String email) {
        String sql = "INSERT INTO User (username, name, password, email) VALUES (?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, email);
            ps.executeUpdate();
            System.out.println("Τα δεδομένα χρήστη εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή χρήστη.");
        }
    }

    public static void insertSubject(String subjectName, int difficultyLevel, String subjectType, String studyGoal, String username) {
        String sql = "INSERT INTO Subject (subjectName, difficultyLevel, subjectType, studyGoal, username) VALUES (?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, subjectName);
            ps.setInt(2, difficultyLevel);
            ps.setString(3, subjectType);
            ps.setString(4, studyGoal);
            ps.setString(5, username);
            ps.executeUpdate();
            System.out.println("Τα δεδομένα μαθήματος εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή μαθήματος.");
        }
    }

    public static void insertAssignment(String title, int remainingDays, String deadline, int estimateHours, String description, String completedDate, String subjectName) {
        String sql = "INSERT INTO Assignment (title, remainingDays, deadline, estimateHours, description, completedDate, subjectName) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, remainingDays);
            ps.setString(3, deadline);
            ps.setInt(4, estimateHours);
            ps.setString(5, description);
            ps.setString(6, completedDate);
            ps.setString(7, subjectName);
            ps.executeUpdate();
            System.out.println("Η εργασία εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
        }
    }

    public static void insertExam(String examName, String deadline, int pages, int revisionPerXPages, double minutesPer20Slides, String subjectName) {
        String sql = "INSERT INTO Exam (examName, deadline, pages, revisionPerXPages, minutesPer20Slides, subjectName) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, examName);
            ps.setString(2, deadline);
            ps.setInt(3, pages);
            ps.setInt(4, revisionPerXPages);
            ps.setDouble(5, minutesPer20Slides);
            ps.setString(6, subjectName);
            ps.executeUpdate();
            System.out.println("Η εξέταση εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εξέτασης.");
        }
    }

    public static void insertTask(int taskType, double taskHours, String subjectName) {
        String sql = "INSERT INTO Task (taskType, taskHours, subjectName) VALUES (?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, taskType);
            ps.setDouble(2, taskHours);
            ps.setString(3, subjectName);
            ps.executeUpdate();
            System.out.println("Η εργασία εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
        }
    }

    public static void insertDay(int availability, String username) {
        String sql = "INSERT INTO Day (availability, username) VALUES (?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, availability);
            ps.setString(2, username);
            ps.executeUpdate();
            System.out.println("Η διαθεσιμότητα ημέρας εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή διαθεσιμότητας ημέρας.");
        }
    }

    public static void insertAvailability(int mondayAv, int tuesdayAv, int wednesdayAv, int thursdayAv, int fridayAv, int saturdayAv, int sundayAv, String username) {
        String sql = "INSERT INTO Availability (mondayAv, tuesdayAv, wednesdayAv, thursdayAv, fridayAv, saturdayAv, sundayAv, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, mondayAv);
            ps.setInt(2, tuesdayAv);
            ps.setInt(3, wednesdayAv);
            ps.setInt(4, thursdayAv);
            ps.setInt(5, fridayAv);
            ps.setInt(6, saturdayAv);
            ps.setInt(7, sundayAv);
            ps.setString(8, username);
            ps.executeUpdate();
            System.out.println("Η διαθεσιμότητα εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή διαθεσιμότητας.");
        }
    }

    public static void insertNonAvDate(String date, String username) {
        String sql = "INSERT INTO NonAvDates (date, username) VALUES (?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, date);
            ps.setString(2, username);
            ps.executeUpdate();
            System.out.println("Η μη διαθέσιμη ημερομηνία εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή μη διαθέσιμης ημερομηνίας.");
        }
    }
}

