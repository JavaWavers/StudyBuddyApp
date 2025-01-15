package org.javawavers.studybuddy.database;

import org.checkerframework.checker.units.qual.A;
import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.courses.*;
import org.javawavers.studybuddy.courses.StaticUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActiveUser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static int connectedID = -1;

    public static void loadData(String email, String password) {
        User connectedUser = authenticateUser(email, password);
        if (connectedUser != null) {
            StaticUser.staticUser = connectedUser;
            List<Subject> subjects = getSubjects(getUserID(email, password));
            List<LocalDate> nonAvDates = getNonAvDates(getUserID(email, password));
            int[] availability = getAvailability(getUserID(email, password));
            List<Day> days = getDays(getUserID(email, password));
            List<Assignment> assignments = getAssignments(getUserID(email, password));
            List<Exam> exams = getExam(getUserID(email, password));
            List<ScheduledTask> scheduledTasks = getTasks(getUserID(email, password));
            List<Week> weeks = getWeeks(getUserID(email, password));

            StaticUser.staticUser.setUserID(getUserID(email, password));
            StaticUser.staticUser.setEmail(email);
            StaticUser.staticUser.setPassword(password);
            StaticUser.staticUser.setAvPerDay(availability);
            StaticUser.staticUser.setTotalWeeks(weeks);
            StaticUser.staticUser.setAssignments(assignments);
            StaticUser.staticUser.setExams(exams);
            StaticUser.staticUser.setNonAvailDays(nonAvDates);
            StaticUser.staticUser.setSubjects(subjects);
            StaticUser.staticUser.setDays(days);
            StaticUser.staticUser.setTasks(scheduledTasks);

            for (Subject subject : subjects) {
                System.out.println(subject.toString());
            }
            for (Assignment assignment : assignments) {
                System.out.println(assignment);
            }
            for (Exam exam : exams) {
                System.out.println(exam);
            }
            for (LocalDate nonAvDate : nonAvDates) {
                System.out.println(nonAvDate);
            }
            for (Week week : weeks) {
                System.out.println(week);
            }
            for (int i = 1; i < 8; i++) {
                System.out.println(availability[i]);
            }
            for (ScheduledTask scheduledTask : scheduledTasks) {
                System.out.println(scheduledTask);
            }


        } else {
            System.out.println("Username or password is incorrect");
        }

    }

    public static int getUserID(String email, String password) {
        String query = "SELECT userID FROM User WHERE email = ? AND password = ?";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int userID = resultSet.getInt("userID");
                    return userID;
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την αναζήτηση κωδικου χρήστη: " + e.getMessage());
        }
        return -1;
    }

    public static User authenticateUser(String email, String password) {
        String query = "SELECT userID, email, name FROM User WHERE email = ? AND password = ?";

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int userID = resultSet.getInt("userID");
                    String userEmail = resultSet.getString("email");
                    String name = resultSet.getString("name");

                    // Δημιουργία του User αντικειμένου
                    return new User(userID, name ,userEmail, password);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την αναζήτηση χρήστη: " + e.getMessage());
        }
        return null; // Αν αποτύχει η αυθεντικοποίηση
    }


    public static int getSubjectID(int userID, String courseName) {
        System.out.println("userID: " + userID + ", courseName: " + courseName);
        String sql = "SELECT subjectID FROM Subject WHERE userID = ? AND subjectName = ?";
        int subjectID = -1;

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setString(2, courseName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    subjectID = rs.getInt("subjectID");
                    System.out.println("Βρέθηκε subjectID: " + subjectID);
                } else {
                    System.err.println("Δεν βρέθηκε subjectID για τον χρήστη: " + userID + ", μάθημα: " + courseName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση του κωδικου μαθήματος: " + e.getMessage());
        }
        return subjectID;
    }

    public static List<Integer> getSubjectID(int userID) {
        String sql = "SELECT subjectID FROM Subject WHERE userID = ?";
        List<Integer> subjectID = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subjectID.add(rs.getInt("subjectID"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση των μαθημάτων: " + e.getMessage());
        }
        return subjectID;
    }

    public static List<Subject> getSubjects(int userID) {
        String sql = "SELECT subjectName, difficultyLevel, subjectType, FROM Subject WHERE userID = ?;";
        List<Subject> subjects = new ArrayList<>();
        List<Exam> ex = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String subjectName = rs.getString("subjectName");
                    int difficultyLevel = rs.getInt("difficultyLevel");
                    Subject.SubjectType subjectType = Subject.SubjectType.valueOf(rs.getString("subjectType"));
                    Subject subject = new Subject(subjectName, difficultyLevel, subjectType);
                    subjects.add(subject);
                    int subID = getSubjectID(userID, subjectName);
                    ex = getExamForSubject(subID);
                    subject.setExams(ex);
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση των μαθημάτων: " + e.getMessage());
        }
        return subjects;
    }

    public static List<Exam> getExamForSubject(int subjectID) {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT e.deadline, e.pages, e.revisionPerXPages, e.minutesPer20Slides, s.subjectName " +
                "FROM Exam e Subject s WHERE e.subjectID = s.subjectID AND e.subjectID = ?;";
        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, subjectID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate deadline = rs.getDate("deadline").toLocalDate();
                    int pages = rs.getInt("pages");
                    int revisionPerXPages = rs.getInt("revisionPerXPages");
                    double minutesPer20Slides = rs.getDouble("minutesPer20Slides");
                    String subjectName = rs.getString("subjectName");
                    Exam exam = new Exam(pages, revisionPerXPages, deadline, minutesPer20Slides);
                    exams.add(exam);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση exams: " + e.getMessage());
        }
        return exams;
    }


    public static List<LocalDate> getNonAvDates(int userID) {
        String sql = "SELECT date FROM NonAvDates WHERE userID = ?;";
        List<LocalDate> nonAvDates = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String dateString = rs.getString("date");
                    LocalDate date = LocalDate.parse(dateString, FORMATTER);
                    nonAvDates.add(date);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση μη διαθέσιμων ημερομηνιών: " + e.getMessage());
        }
        return nonAvDates;
    }

    public static int[] getAvailability(int userID) {
        String sql = """
            SELECT mondayAv, tuesdayAv, wednesdayAv, thursdayAv, fridayAv, saturdayAv, sundayAv
            FROM Availability
            WHERE userID = ?;
            """;
        int[] availability = new int[8];

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    for (int i = 1; i <= 7; i++) {
                        availability[i] = rs.getInt(i);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση της διαθεσιμότητας: " + e.getMessage());
        }
        return availability;
    }

    public static List<Day> getDays(int userID) {
        String sql = "SELECT dayID FROM Day WHERE userID = ?;";
        List<Day> days = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int dayID = rs.getInt("dayID");
                    List<ScheduledTask> tasksForDay = getTasksForDay(dayID);
                    days.add(new Day(tasksForDay));
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση ημερών " + e.getMessage());
        }
        return days;
    }

    public static List<Exam> getExam(int userID) {
        List<Exam> exams = new ArrayList<>();
        List<Integer> subjectID = getSubjectID(userID);

        if (subjectID.isEmpty()) {
            return exams; // Επιστρέφει κενή λίστα αν δεν υπάρχουν subjectID
        }

        String sql = "SELECT e.deadline, e.pages, e.revisionPerXPages, e.minutesPer20Slides, s.subjectName " +
                "FROM Exam e JOIN Subject s ON e.subjectID = s.subjectID WHERE e.subjectID IN (" +
                subjectID.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int index = 1;
            for (int id : subjectID) {
                ps.setInt(index++, id);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate deadline = rs.getDate("deadline").toLocalDate();
                    int pages = rs.getInt("pages");
                    int revisionPerXPages = rs.getInt("revisionPerXPages");
                    double minutesPer20Slides = rs.getDouble("minutesPer20Slides");
                    String subjectName = rs.getString("subjectName");

                    Exam exam = new Exam(pages, revisionPerXPages, deadline, minutesPer20Slides);
                    exams.add(exam);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση exams: " + e.getMessage());
        }

        return exams;
    }


 /*   public static List<Exam> getExam(int userID) {
        List<Exam> exams = new ArrayList<>();
        List<Integer> subjectID = getSubjectID(userID);
        String sql = "SELECT e.deadline, e.pages, e.revisionPerXPages, e.minutesPer20Slides, s.subjectName " +
                "FROM Exam e JOIN Subject s ON e.subjectID = s.subjectID WHERE e.subjectID = ?;";

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (int id : subjectID) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String dateString = rs.getString("deadline");
                        LocalDate deadline = LocalDate.parse(dateString, FORMATTER);
                        int pages = rs.getInt("pages");
                        int revisionPerXPages = rs.getInt("revisionPerXPages");
                        double minutesPer20Slides = rs.getDouble("minutesPer20Slides");
                        String subjectName = rs.getString("subjectName");
                        Exam exam = new Exam(pages, revisionPerXPages, deadline, minutesPer20Slides);
                        exams.add(exam);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση exams: " + e.getMessage());
        }
        return exams;
    }*/

    public static List<ScheduledTask> getTasks(int userID) {
        String sql = "SELECT taskName, hoursAllocated, taskStatus, taskDate, subjectName, taskType, timeStarted, timeCompleted FROM Task WHERE userID = ?;";
        List<ScheduledTask> scheduledTasks = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String taskName = rs.getString("taskName");
                    int hoursAllocated = rs.getInt("hoursAllocated");
                    String taskTypeString = rs.getString("taskStatus");
                    ScheduledTask.TaskStatus taskStatus = ScheduledTask.TaskStatus.valueOf(taskTypeString.toUpperCase());
                    String dString = rs.getString("taskDate");
                    LocalDate taskDate = LocalDate.parse(dString, FORMATTER);
                    String subjectName = rs.getString("subjectName");
                    String taskType = rs.getString("taskType");
                    String  timeStString = rs.getString("timeStarted");
                    LocalTime timeStarted = LocalTime.parse(timeStString);
                    String timeComString = rs.getString("timeCompleted");
                    LocalTime timeCompleted = LocalTime.parse(timeComString);
                    ScheduledTask t = new ScheduledTask(taskName, taskType, hoursAllocated,
                            taskStatus, timeStarted, timeCompleted, taskDate, subjectName);
                    scheduledTasks.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση Task : " + e.getMessage());
        }
        return scheduledTasks;
    }

    public static List<Assignment> getAssignments(int userID) {
        List<Assignment> assignments = new ArrayList<>();

        String sql = "SELECT a.title, a.deadline, a.estimateHours, a.completedDate, a.diffilclty " +
                "FROM Assignment a WHERE userID = ?";

        try (Connection connection = DataBaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    LocalDate deadline = LocalDate.parse(rs.getString("deadline"));
                    int estimateHours = rs.getInt("estimateHours");
                    String completedDateString = rs.getString("completedDate");
                    int difficulty = rs.getInt("difficulty");
                    LocalDate completedDate = (completedDateString != null && !completedDateString.isEmpty())
                            ? LocalDate.parse(completedDateString)
                            : null;
                    Assignment assignment = new Assignment(title, deadline, estimateHours, difficulty);
                    assignments.add(assignment);
                }

            } catch (SQLException e) {
                System.err.println("Σφάλμα κατά την ανάκτηση εργασιών :" + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Σφάλμα κατά την ανάκτηση εργασιών :" + e.getMessage());
        }
        return assignments;
    }

    public static List<Week> getWeeks(int userID) {
        String sql = "SELECT weekID FROM Week WHERE userID = ?;";
        List<Week> weeks = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int weekID = rs.getInt("weekID");
                    Week week = new Week();
                    List<Day> daysOfWeek = getDays(weekID);
                    week.setDaysOfWeek(daysOfWeek);
                    weeks.add(week);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση εβδομάδων: " + e.getMessage());
        }
        return weeks;
    }

    public static List<ScheduledTask> getTasksForDay(int dayID) {
        String sql = "SELECT taskName, hoursAllocated, timeStarted, timeCompleted, taskStatus, taskDate, subjectName, taskType FROM ScheduledTask WHERE dayID = ?";
        List<ScheduledTask> scheduledTasks = new ArrayList<>();

        try (Connection c = DataBaseManager.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, dayID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String taskName = rs.getString("taskName");
                    int hoursAllocated = rs.getInt("hoursAllocated");
                    String timeStartedString = rs.getString("timeStarted");
                    LocalTime timeStarted = LocalTime.parse(timeStartedString, DateTimeFormatter.ofPattern("HH:mm"));
                    String timeCompletedString = rs.getString("timeCompleted");
                    LocalTime timeCompleted = LocalTime.parse(timeCompletedString, DateTimeFormatter.ofPattern("HH:mm"));
                    String taskTypeString = rs.getString("taskStatus");
                    ScheduledTask.TaskStatus taskStatus = ScheduledTask.TaskStatus.valueOf(taskTypeString.toUpperCase());
                    String dString = rs.getString("taskDate");
                    LocalDate taskDate = LocalDate.parse(dString, FORMATTER);
                    String subjectName = rs.getString("subjectName");
                    String taskType = rs.getString("taskType");
                    ScheduledTask t = new ScheduledTask(taskName, taskType, hoursAllocated,
                            taskStatus, timeStarted, timeCompleted, taskDate, subjectName);
                    scheduledTasks.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση των tasks για την ημέρα: " + e.getMessage());
        }
        return scheduledTasks;
    }
}