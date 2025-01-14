package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static final String DATABASE_URL =
            "jdbc:sqlite:src/main/java/org/javawavers/studybuddy/DataBase.db";


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

    public static void CreateTables() {
        CreateUser();
        CreateAvailability();
        CreateNonAvDates();
        CreateSubject();
        CreateAssignment();
        CreateExam();
        CreateWeek();
        CreateDay();
        CreateScheduledTask();
    }

    public static void CreateUser() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS User (
                    userID INTEGER PRIMARY KEY AUTOINCREMENT,
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

    public static void CreateSubject() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Subject (
                    subjectID INTEGER PRIMARY KEY AUTOINCREMENT,
                    subjectName TEXT NOT NULL,
                    difficultyLevel INTEGER NOT NULL,
                    subjectType TEXT NOT NULL,
                    userID INTEGER NOT NULL,
                    FOREIGN KEY (userID) REFERENCES User (userID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateAssignment() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Assignment (
                    assignmentID INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    deadline TEXT NOT NULL,
                    estimateHours INTEGER NOT NULL,
                    completedDate TEXT,
                    userID INTEGER NOT NULL,
                    FOREIGN KEY (userID) REFERENCES User (userID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateExam() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Exam (
                    examID INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    deadline TEXT NOT NULL,
                    pages INT NOT NULL,
                    revisionPerXPages INT NOT NULL,
                    minutesPer20Slides REAL NOT NULL,
                    subjectID INTEGER NOT NULL,
                     FOREIGN KEY (subjectID) REFERENCES Subject (subjectID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void CreateScheduledTask() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS Task (
                taskID INTEGER PRIMARY KEY AUTOINCREMENT,
                taskName TEXT,
                hoursAllocated INTEGER,
                timeStarted TEXT,
                timeCompleted TEXT,
                taskStatus TEXT,
                taskDate TEXT,
                subjectName TEXT,
                taskType TEXT,
                userID INTEGER NOT NULL,
                dayID INTEGER NOT NULL,
                FOREIGN KEY (dayID) REFERENCES Day (dayID),
                FOREIGN KEY (userID) REFERENCES User (userID)
                );
                """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void CreateDay() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Day (
                    dayID INTEGER PRIMARY KEY AUTOINCREMENT,
                    userID INTEGER NOT NULL,
                    weekID INTEGER NOT NULL,
                    FOREIGN KEY (userID) REFERENCES User (userID),
                    FOREIGN KEY (weekID) REFERENCES Week (weekID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateWeek() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Week (
                    weekID INTEGER PRIMARY KEY AUTOINCREMENT,
                    userID TEXT NOT NULL,
                    FOREIGN KEY (userID) REFERENCES User (userID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateAvailability() {
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
                    userID TEXT PRIMARY KEY,
                    FOREIGN KEY (userID) REFERENCES User (userID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateNonAvDates() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS NonAvDates (
                    nonAvDateID INTEGER PRIMARY KEY AUTOINCREMENT,
                    date TEXT NOT NULL,
                    userID TEXT NOT NULL,
                    FOREIGN KEY (userID) REFERENCES User (userID)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}