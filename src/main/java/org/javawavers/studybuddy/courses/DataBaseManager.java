package org.javawavers.studybuddy.courses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/org/javawavers/studybuddy/DataBase.db";

    public static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            return connection;
        } catch (SQLException e) {
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
        CreateTask();
        CreateDay();

    }

    public static void CreateUser() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS User (
                    name TEXT PRIMARY KEY,
                    username TEXT NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT NOT NULL
                    ) WITHOUT ROWID ;
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
                    subjectName TEXT PRIMARY KEY,
                    difficultyLevel INTEGER NOT NULL,
                    subjectType TEXT NOT NULL,
                    studyGoal TEXT NOT NULL,
                    name TEXT NOT NULL,
                    FOREIGN KEY (name) REFERENCES User (name)
                    ) WITHOUT ROWID ;
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void CreateAssignment() {    // convert LocalDate deadline and completeddDate to String
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Assignment (
                    title TEXT PRIMARY KEY,
                    remainingDays INTEGER NOT NULL,
                    deadline TEXT NOT NULL,
                    estimateHours INTEGER NOT NULL,
                    description TEXT NOT NULL,
                    completedDate TEXT NOT NULL,
                    subjectName TEXT NOT NULL,
                    FOREIGN KEY (subjectName) REFERENCES Subject(subjectName)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateExam() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) { //

            String sql = """
                    CREATE TABLE IF NOT EXISTS Exam (
                    name TEXT PRIMARY KEY,
                    deadline TEXT NOT NULL,
                    pages INT NOT NULL,
                    revisionPerXPages INT NOT NULL,
                    minutesPer20Slides REAL NOT NULL,
                    subjectName TEXT NOT NULL,
                    FOREIGN KEY (subjectName) REFERENCES Subject(subjectName)
                    );
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateTask() {
        try (Connection c = DataBaseManager.connect();
             Statement s = c.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS Task (
                    taskType INTEGER NOT NULL,
                    taskhours REAL NOT NULL,
                    subjectName TEXT NOT NULL,
                        FOREIGN KEY (subjectName) REFERENCES Subject (subjectName)
                    name TEXT NOT NULL,
                        FOREIGN KEY (name) REFERENCES User (name),
                    date INTEGER NOT NULL
                        FOREIGN KEY (date) REFERENCES Day (date)
                    ) WITHOUT ROWID ;
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
                    date TEXT PRIMARY KEY,
                    availability INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    FOREIGN KEY (name) REFERENCES User (name)
                    ) WITHOUT ROWID ;
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
                    CREATE TABLE IF NOT EXISTS Αvailability (
                    mondayAv INTEGER NOT NULL,
                    tuesdayAv INTEGER NOT NULL,
                    wednesdayAv INTEGER NOT NULL,
                    thursdayAv INTEGER NOT NULL,
                    fridayAv INTEGER NOT NULL,
                    saturdayAv INTEGER NOT NULL,
                    sundayAv INTEGER NOT NULL,
                    name TEXT NOT NULL
                    FOREIGN KEY (name) REFERENCES User (name)
                    ) WITHOUT ROWID ;
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
                    date TEXT NOT NULL,
                    name TEXT NOT NULL
                    FOREIGN KEY (name) REFERENCES User (name)
                    ) WITHOUT ROWID ;
                    """;
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

