package org.javawavers.studybuddy.database;

import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.courses.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class DataInserter {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void insertUser(String username, String password, String email) {
        String sql = "INSERT INTO User (name, password, email) VALUES (?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
            System.out.println("Τα δεδομένα χρήστη εισάχθηκαν με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή χρήστη: ");
        }
    }


    public static void insertSubject(String subjectName, int difficultyLevel, String subjectType, int userID) {
        String sql = "INSERT INTO Subject (subjectName, difficultyLevel, subjectType, userID) VALUES (?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, subjectName);
            ps.setInt(2, difficultyLevel);
            ps.setString(3, subjectType);
            ps.setInt(4, userID);
            ps.executeUpdate();
            System.out.println("Τα δεδομένα μαθήματος εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή μαθήματος.");
        }
    }

    public static void insertAssignment(String title, LocalDate deadline, int estimateHours, int difficulty, LocalDate completedDate, int userID) {
        String sql = "INSERT INTO Assignment (title, deadline, estimateHours, difficulty, completedDate, userID) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, deadline.format(FORMATTER));
            ps.setInt(3, estimateHours);
            ps.setInt(4, difficulty);
            ps.setString(5, completedDate == null ? null : completedDate.format(FORMATTER));
            ps.setInt(6, userID);
            ps.executeUpdate();
            System.out.println("Η εργασία εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εργασίας: ");
        }
    }


    public static void insertExam(/*String examName*/ LocalDate deadline, int pages, int revisionPerXPages, double minutesPer20Slides, int subjectID) {
        String sql = "INSERT INTO Exam (deadline, pages, revisionPerXPages, minutesPer20Slides, subjectID) VALUES (?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            //   ps.setString(1, examName);
            ps.setString(1, deadline.format(FORMATTER));
            ps.setInt(2, pages);
            ps.setInt(3, revisionPerXPages);
            ps.setDouble(4, minutesPer20Slides);
            ps.setInt(5, subjectID);
            ps.executeUpdate();
            System.out.println("Η εξέταση εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εξέτασης.");
        }
    }

    public static void insertTask(String taskName,
                                  int hoursAllocated,
                                  LocalTime timeStarted,
                                  LocalTime timeCompleted,
                                  ScheduledTask.TaskStatus taskStatus,
                                  LocalDate taskDate,
                                  String subjectName,
                                  String taskType,
                                  int userID,
                                  int dayID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String sql = "INSERT INTO Task (taskName, hoursAllocated, timeStarted," +
                "timeCompleted, taskStatus, taskDate, subjectName, taskType," +
                "userID, dayID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, taskName);
            ps.setInt(2, hoursAllocated);
            if (timeStarted == null) {
                System.out.println("timeStarted είναι null. Χρησιμοποιώ προεπιλεγμένη ώρα 09:00");
                timeStarted = LocalTime.of(9, 0);
            }
            if (timeCompleted == null) {
                System.out.println("timeCompleted is null, set to 11:00");
                timeCompleted = LocalTime.of(11, 0);
            }
            ps.setString(3, timeStarted.format(formatter));
            ps.setString(4, timeCompleted.format(formatter));
            ps.setString(5, taskStatus.name());
            ps.setString(6, taskDate.format(FORMATTER));
            ps.setString(7, subjectName);
            ps.setString(8, taskType);
            ps.setInt(9, userID);
            ps.setInt(10, dayID);
            ps.executeUpdate();
            System.out.println("Το task εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
        }
    }

    public static void insertDay(int dayID, int userID, int weekID) {
        String sql = "INSERT INTO Day (dayID, userID, weekID) VALUES (?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, dayID);
            ps.setInt(2, userID);
            ps.setInt(3, weekID);
            ps.executeUpdate();
            System.out.println("Η ημέρα εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή ημέρας.");
        }
    }

    public static void insertWeek(int weekID, int userID) {
        String sql = "INSERT INTO Week (weekID, userID) VALUES (?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, weekID);
            ps.setInt(2, userID);
            ps.executeUpdate();
            System.out.println("Η εβδομάδα εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εβδομάδας.");
        }
    }

    public static void insertAvailability(int mondayAv, int tuesdayAv, int wednesdayAv, int thursdayAv, int fridayAv, int saturdayAv, int sundayAv, int userID) {
        String sql = "INSERT INTO Availability (mondayAv, tuesdayAv, wednesdayAv, thursdayAv, fridayAv, saturdayAv, sundayAv, userID) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, mondayAv);
            ps.setInt(2, tuesdayAv);
            ps.setInt(3, wednesdayAv);
            ps.setInt(4, thursdayAv);
            ps.setInt(5, fridayAv);
            ps.setInt(6, saturdayAv);
            ps.setInt(7, sundayAv);
            ps.setInt(8, userID);
            ps.executeUpdate();
            System.out.println("Η διαθεσιμότητα εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή διαθεσιμότητας.");
        }
    }

    public static void insertNonAvDate(LocalDate date, int userID) {
        String sql = "INSERT INTO NonAvDates (date, userID) VALUES (?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, date.format(FORMATTER));
            ps.setInt(2, userID);
            ps.executeUpdate();
            System.out.println("Η μη διαθέσιμη ημερομηνία εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή μη διαθέσιμης ημερομηνίας: ");
        }
    }

    // update availability
    public static void updateAvailability(int mondayAv, int tuesdayAv, int wednesdayAv, int thursdayAv, int fridayAv, int saturdayAv, int sundayAv, int userID) {
        String sql = "UPDATE Availability " +
                "SET mondayAv = ?, tuesdayAv = ?, wednesdayAv = ?, thursdayAv = ?, fridayAv = ?, saturdayAv = ?, sundayAv = ? " +
                "WHERE userID = ?;";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, mondayAv);
            ps.setInt(2, tuesdayAv);
            ps.setInt(3, wednesdayAv);
            ps.setInt(4, thursdayAv);
            ps.setInt(5, fridayAv);
            ps.setInt(6, saturdayAv);
            ps.setInt(7, sundayAv);
            ps.setInt(8, userID);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Η διαθεσιμότητα ενημερώθηκε με επιτυχία.");
            } else {
                System.out.println("Δεν βρέθηκε χρήστης με το συγκεκριμένο ID.");
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ενημέρωση διαθεσιμότητας: " + e.getMessage());
        }
    }

    public static void insertCompletedTask(String taskName,
                                  int hoursAllocated,
                                  LocalTime timeStarted,
                                  LocalTime timeCompleted,
                                  ScheduledTask.TaskStatus taskStatus,
                                  LocalDate taskDate,
                                  String subjectName,
                                  String taskType,
                                  int userID,
                                  int dayID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String sql = "INSERT INTO CompletedTask (taskName, hoursAllocated, timeStarted," +
                "timeCompleted, taskStatus, taskDate, subjectName, taskType," +
                "userID, dayID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, taskName);
            ps.setInt(2, hoursAllocated);
            if (timeStarted == null) {
                System.out.println("timeStarted είναι null. Χρησιμοποιώ προεπιλεγμένη ώρα 09:00");
                timeStarted = LocalTime.of(9, 0);
            }
            if (timeCompleted == null) {
                System.out.println("timeCompleted is null, set to 11:00");
                timeCompleted = LocalTime.of(11, 0);
            }
            ps.setString(3, timeStarted.format(formatter));
            ps.setString(4, timeCompleted.format(formatter));
            ps.setString(5, taskStatus.name());
            ps.setString(6, taskDate.format(FORMATTER));
            ps.setString(7, subjectName);
            ps.setString(8, taskType);
            ps.setInt(9, userID);
            ps.setInt(10, dayID);
            ps.executeUpdate();
            System.out.println("Το task εισάχθηκε με επιτυχία.");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
        }
    }
}