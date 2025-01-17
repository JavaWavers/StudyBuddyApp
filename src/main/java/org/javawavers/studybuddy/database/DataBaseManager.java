package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * this class contains all the methods for creating tables.
 */
public class DataBaseManager {
  private static final String DATABASE_URL =
      "jdbc:sqlite:src/main/java/org/javawavers/studybuddy/DataBase.db";

  /** creates the connction with the database. */
  public static Connection connect() {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection(DATABASE_URL);
      return connection;
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Trouble connecting:" + e);
      return null;
    }
  }

  /** creation of tables. */
  public static void createTables() {
    createUser();
    createAvailability();
    createNonAvDates();
    createSubject();
    createAssignment();
    createExam();
    createWeek();
    createDay();
    createScheduledTask();
    createCompletedScheduledTask();
  }

  /** create table User.*/
  public static void createUser() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                  CREATE TABLE IF NOT EXISTS User (
                  userId INTEGER PRIMARY KEY AUTOINCREMENT,
                  name TEXT NOT NULL UNIQUE,
                  password TEXT NOT NULL,
                  email TEXT NOT NULL
                  );
                                     """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table Subject.*/
  public static void createSubject() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                  CREATE TABLE IF NOT EXISTS Subject (
                  subjectId INTEGER PRIMARY KEY AUTOINCREMENT,
                  subjectName TEXT NOT NULL,
                  difficultyLevel INTEGER NOT NULL,
                  subjectType TEXT NOT NULL,
                  userId INTEGER NOT NULL,
                  FOREIGN KEY (userId) REFERENCES User (userId)
                  );
                  """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table assignment. */
  public static void createAssignment() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                  CREATE TABLE IF NOT EXISTS Assignment (
                  assignmentId INTEGER PRIMARY KEY AUTOINCREMENT,
                  title TEXT NOT NULL,
                  deadline TEXT NOT NULL,
                  estimateHours INTEGER NOT NULL,
                  completedDate TEXT,
                  userId INTEGER NOT NULL,
                  difficulty INTEGER NOT NULL,
                  FOREIGN KEY (userId) REFERENCES User (userId)
                  );                    """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** creates table exam. */
  public static void createExam() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                   CREATE TABLE IF NOT EXISTS Exam (
                   examId INTEGER PRIMARY KEY AUTOINCREMENT,
                   name TEXT,
                   deadline TEXT NOT NULL,
                   pages INT NOT NULL,
                   revisionPerxPages INT NOT NULL,
                   minutesPer20Slides REAL NOT NULL,
                   subjectId INTEGER NOT NULL,
                    FOREIGN KEY (subjectId) REFERENCES Subject (subjectId)
                   );
                   """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table scheduledtask. */
  public static void createScheduledTask() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
               CREATE TABLE IF NOT EXISTS Task (
               taskId INTEGER PRIMARY KEY AUTOINCREMENT,
               taskName TEXT,
               hoursAllocated INTEGER,
               timeStarted TEXT,
               timeCompleted TEXT,
               taskStatus TEXT,
               taskDate TEXT,
               subjectName TEXT,
               taskType TEXT,
               userId INTEGER NOT NULL,
               dayId INTEGER NOT NULL,
               FOREIGN KEY (dayId) REFERENCES Day (dayId),
               FOREIGN KEY (userId) REFERENCES User (userId)
               );
               """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table day. */
  public static void createDay() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                   CREATE TABLE IF NOT EXISTS Day (
                   dayId INTEGER PRIMARY KEY,
                    userId INTEGER NOT NULL,
                   weekId INTEGER NOT NULL,
                   FOREIGN KEY (userId) REFERENCES User (userId),
                   FOREIGN KEY (weekId) REFERENCES Week (weekId)
                   );
                   """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**create table week. */
  public static void createWeek() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                   CREATE TABLE IF NOT EXISTS Week (
                   weekId INTEGER PRIMARY KEY,
                   userId INNTEGER NOT NULL,
                   FOREIGN KEY (userId) REFERENCES User (userId)
                   );
                   """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table Availability. */
  public static void createAvailability() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                   CREATE TABLE IF NOT EXISTS Availability (
                   mondayAv INTEGER NOT NULL,
                   tuesdayAv INTEGER NOT NULL,
                   wednesdayAv INTEGER NOT NULL,
                   thursdayAv INTEGER NOT NULL,
                   fridayAv INTEGER NOT NULL,
                   saturdayAv INTEGER NOT NULL,
                   sundayAv INTEGER NOT NULL,
                   userId TEXT PRIMARY KEY,
                   FOREIGN KEY (userId) REFERENCES User (userId)
                   );
                   """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** create table NonAvDates. */
  public static void createNonAvDates() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
                   CREATE TABLE IF NOT EXISTS NonAvDates (
                   nonAvDateID INTEGER PRIMARY KEY AUTOINCREMENT,
                   date TEXT NOT NULL,
                   userId TEXT NOT NULL,
                   FOREIGN KEY (userId) REFERENCES User (userId)
                   );
                   """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /** Create table CompletedScheduledTask. */
  public static void createCompletedScheduledTask() {
    try (Connection c = DataBaseManager.connect();
         Statement s = c.createStatement()) {
      String sql = """
               CREATE TABLE IF NOT EXISTS CompletedTask (
               taskId INTEGER PRIMARY KEY AUTOINCREMENT,
               taskName TEXT,
               hoursAllocated INTEGER,
               timeStarted TEXT,
               timeCompleted TEXT,
               taskStatus TEXT,
               taskDate TEXT,
               subjectName TEXT,
               taskType TEXT,
               userId INTEGER NOT NULL,
               dayId INTEGER NOT NULL,
               FOREIGN KEY (dayId) REFERENCES Day (dayId),
               FOREIGN KEY (userId) REFERENCES User (userId)
               );
               """;
      s.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}