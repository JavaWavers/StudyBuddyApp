package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.javawavers.studybuddy.courses.ScheduledTask;

/** this class contains the methods for alla the inserts. */
public class DataInserter {
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /** insert user. */
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

  /** insert in table Subject. */
  public static void insertSubject(
      String subjectName, int difficultyLevel, String subjectType, int userId) {
    String sql =
        "INSERT INTO Subject (subjectName, difficultyLevel, "
            + "subjectType, userId) VALUES (?, ?, ?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, subjectName);
      ps.setInt(2, difficultyLevel);
      ps.setString(3, subjectType);
      ps.setInt(4, userId);
      ps.executeUpdate();
      System.out.println("Τα δεδομένα μαθήματος εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή μαθήματος.");
    }
  }

  /** insert assignment. */
  public static void insertAssignment(
      String title,
      LocalDate deadline,
      int estimateHours,
      int difficulty,
      LocalDate completedDate,
      int userId) {
    String sql =
        "INSERT INTO Assignment (title, deadline, estimateHours,"
            + " difficulty, completedDate, userId) VALUES (?, ?, ?, ?, ?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, title);
      ps.setString(2, deadline.format(FORMATTER));
      ps.setInt(3, estimateHours);
      ps.setInt(4, difficulty);
      ps.setString(5, completedDate == null ? null : completedDate.format(FORMATTER));
      ps.setInt(6, userId);
      ps.executeUpdate();
      System.out.println("Η εργασία εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή εργασίας: ");
    }
  }

  /** insert exam. */
  public static void insertExam(
      LocalDate deadline,
      int pages,
      int revisionPerxPages,
      double minutesPer20Slides,
      int subjectId) {
    String sql =
        "INSERT INTO Exam (deadline, pages, revisionPerxPages, "
            + "minutesPer20Slides, subjectId) VALUES (?, ?, ?, ?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, deadline.format(FORMATTER));
      ps.setInt(2, pages);
      ps.setInt(3, revisionPerxPages);
      ps.setDouble(4, minutesPer20Slides);
      ps.setInt(5, subjectId);
      ps.executeUpdate();
      System.out.println("Η εξέταση εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή εξέτασης.");
    }
  }

  /** insert task. */
  public static void insertTask(
      String taskName,
      int hoursAllocated,
      LocalTime timeStarted,
      LocalTime timeCompleted,
      ScheduledTask.TaskStatus taskStatus,
      LocalDate taskDate,
      String subjectName,
      String taskType,
      int userId,
      int dayId) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String sql =
        "INSERT INTO Task (taskName, hoursAllocated, timeStarted,"
            + "timeCompleted, taskStatus, taskDate, subjectName, taskType,"
            + "userId, dayId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
      ps.setInt(9, userId);
      ps.setInt(10, dayId);
      ps.executeUpdate();
      System.out.println("Το task εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
    }
  }

  /** insert Day. */
  public static void insertDay(int dayId, int userId, int weekId) {
    String sql = "INSERT INTO Day (dayId, userId, weekId) VALUES (?, ?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, dayId);
      ps.setInt(2, userId);
      ps.setInt(3, weekId);
      ps.executeUpdate();
      System.out.println("Η ημέρα εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή ημέρας.");
    }
  }

  /** insert week. */
  public static void insertWeek(int weekId, int userId) {
    String sql = "INSERT INTO Week (weekId, userId) VALUES (?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, weekId);
      ps.setInt(2, userId);
      ps.executeUpdate();
      System.out.println("Η εβδομάδα εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή εβδομάδας.");
    }
  }

  /** insert availability. */
  public static void insertAvailability(
      int mondayAv,
      int tuesdayAv,
      int wednesdayAv,
      int thursdayAv,
      int fridayAv,
      int saturdayAv,
      int sundayAv,
      int userId) {
    String sql =
        "INSERT INTO Availability (mondayAv, tuesdayAv, wednesdayAv, "
            + "thursdayAv, fridayAv, saturdayAv, sundayAv, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, mondayAv);
      ps.setInt(2, tuesdayAv);
      ps.setInt(3, wednesdayAv);
      ps.setInt(4, thursdayAv);
      ps.setInt(5, fridayAv);
      ps.setInt(6, saturdayAv);
      ps.setInt(7, sundayAv);
      ps.setInt(8, userId);
      ps.executeUpdate();
      System.out.println("Η διαθεσιμότητα εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή διαθεσιμότητας.");
    }
  }

  /** insert non available day. */
  public static void insertNonAvDate(LocalDate date, int userId) {
    String sql = "INSERT INTO NonAvDates (date, userId) VALUES (?, ?);";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, date.format(FORMATTER));
      ps.setInt(2, userId);
      ps.executeUpdate();
      System.out.println("Η μη διαθέσιμη ημερομηνία εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή μη διαθέσιμης ημερομηνίας: ");
    }
  }

  /** update availability. */
  public static void updateAvailability(
      int mondayAv,
      int tuesdayAv,
      int wednesdayAv,
      int thursdayAv,
      int fridayAv,
      int saturdayAv,
      int sundayAv,
      int userId) {
    String sql =
        "UPDATE Availability "
            + "SET mondayAv = ?, tuesdayAv = ?, wednesdayAv = ?, thursdayAv = ?, "
            + "fridayAv = ?, saturdayAv = ?, sundayAv = ? WHERE userId = ?;";
    try (Connection c = DataBaseManager.connect();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, mondayAv);
      ps.setInt(2, tuesdayAv);
      ps.setInt(3, wednesdayAv);
      ps.setInt(4, thursdayAv);
      ps.setInt(5, fridayAv);
      ps.setInt(6, saturdayAv);
      ps.setInt(7, sundayAv);
      ps.setInt(8, userId);

      int rowsUpdated = ps.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("Η διαθεσιμότητα ενημερώθηκε με επιτυχία.");
      } else {
        System.out.println("Δεν βρέθηκε χρήστης με το συγκεκριμένο Id.");
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ενημέρωση διαθεσιμότητας: " + e.getMessage());
    }
  }

  /** insert completed task. */
  public static void insertCompletedTask(
      String taskName,
      int hoursAllocated,
      LocalTime timeStarted,
      LocalTime timeCompleted,
      ScheduledTask.TaskStatus taskStatus,
      LocalDate taskDate,
      String subjectName,
      String taskType,
      int userId,
      int dayId) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String sql =
        "INSERT INTO CompletedTask (taskName, hoursAllocated, timeStarted,"
            + "timeCompleted, taskStatus, taskDate, subjectName, taskType,"
            + "userId, dayId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
      ps.setInt(9, userId);
      ps.setInt(10, dayId);
      ps.executeUpdate();
      System.out.println("Το task εισάχθηκε με επιτυχία.");
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την εισαγωγή εργασίας.");
    }
  }
}
