package org.javawavers.studybuddy.database;

/* This class contains all the methods to get the elements from the database*/
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
import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.StaticUser;
import org.javawavers.studybuddy.courses.Subject;
import org.javawavers.studybuddy.courses.User;

/**
 * the class containing all the methods for loading data from the database.
 */
public class ActiveUser {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static int connectedId = -1;

  /** loading all data after authenticating user. */
  public static void loadData(String email, String password) {
    User connectedUser = authenticateUser(email, password);
    if (connectedUser != null) {
      StaticUser.staticUser = connectedUser;
      List<Subject> subjects = getSubjects(getUserId(email, password));
      List<LocalDate> nonAvDates = getNonAvDates(getUserId(email, password));
      int[] availability = getAvailability(getUserId(email, password));
      List<Day> days = getDays(getUserId(email, password));
      List<Assignment> assignments = getAssignments(getUserId(email, password));
      List<Exam> exams = getExam(getUserId(email, password));
      List<ScheduledTask> scheduledTasks = getTasks(getUserId(email, password));
      List<Week> weeks = getWeeks(getUserId(email, password));
      List<ScheduledTask> completedTasks = getCompletedTasks(getUserId(email,password));

      StaticUser.staticUser.setUserId(getUserId(email, password));
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
      StaticUser.staticUser.setCompletedTasks(completedTasks);
      for (Subject subject : subjects) {
        System.out.println("subject:");
        System.out.println(subject.toString());
      }
      for (Assignment assignment : assignments) {
        System.out.println("assignment:");
        System.out.println(assignment);
      }
      for (Exam exam : exams) {
        System.out.println("exam:");
        System.out.println(exam);
      }
      for (LocalDate nonAvDate : nonAvDates) {
        System.out.println("nonAvDate:");
        System.out.println(nonAvDate);
      }
      for (Week week : weeks) {
        System.out.println("week:");
        System.out.println(week);
      }
      for (int i = 1; i < 8; i++) {
        System.out.println("availability:");
        System.out.println(availability[i]);
      }
      for (ScheduledTask scheduledTask : scheduledTasks) {
        System.out.println("scheduledTask:");
        System.out.println(scheduledTask);
      }
    } else {
      System.out.println("Username or password is incorrect");
    }

  }

  /** returns userId from their email land password. */
  public static int getUserId(String email, String password) {
    String query = "SELECT userId FROM User WHERE email = ? AND password = ?";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(query)) {
      ps.setString(1, email);
      ps.setString(2, password);
      try (ResultSet resultSet = ps.executeQuery()) {
        if (resultSet.next()) {
          int userId = resultSet.getInt("userID");
          return userId;
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την αναζήτηση κωδικου χρήστη: " + e.getMessage());
    }
    return -1;
  }

  /** authenticates user and creating a User object. */
  public static User authenticateUser(String email, String password) {
    String query = "SELECT userId, email, name FROM User WHERE email = ? AND password = ?";

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(query)) {
      ps.setString(1, email);
      ps.setString(2, password);

      try (ResultSet resultSet = ps.executeQuery()) {
        if (resultSet.next()) {
          int userId = resultSet.getInt("userId");
          String userEmail = resultSet.getString("email");
          String name = resultSet.getString("name");

          return new User(userId, name, userEmail, password); // creation of object User
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την αναζήτηση χρήστη: " + e.getMessage());
    }
    return null; // If the authentication fails
  }

  /** returns a specific SubjectId. */
  public static int getSubjectId(int userId, String courseName) {
    System.out.println("userId: " + userId + ", courseName: " + courseName);
    String sql = "SELECT subjectId FROM Subject WHERE userId = ? AND subjectName = ?";
    int subjectId = -1;

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ps.setString(2, courseName);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          subjectId = rs.getInt("subjectId");
          System.out.println("Βρέθηκε subjectId: " + subjectId);
        } else {
          System.err.println("Δεν βρέθηκε subjectId για τον χρήστη: "
              + userId + ", μάθημα: " + courseName);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση του κωδικου μαθήματος: " + e.getMessage());
    }
    return subjectId;
  }

  /** returns a list of SubjectId's for a specific user. */
  public static List<Integer> getSubjectId(int userId) {
    String sql = "SELECT subjectId FROM Subject WHERE userId = ?";
    List<Integer> subjectId = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          subjectId.add(rs.getInt("subjectId"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση των μαθημάτων: " + e.getMessage());
    }
    return subjectId;
  }

  /** returns the lit of Subjects for a specific user. */
  public static List<Subject> getSubjects(int userId) {
    String sql = "SELECT subjectName, difficultyLevel, "
        + "subjectType, subjectID FROM Subject WHERE userID = ?;";
    List<Subject> subjects = new ArrayList<>();
    List<Exam> ex = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int subjectId = rs.getInt("subjectId");
          String subjectName = rs.getString("subjectName");
          int difficultyLevel = rs.getInt("difficultyLevel");
          Subject.SubjectType subjectType =
              Subject.SubjectType.valueOf(rs.getString("subjectType"));
          Subject subject = new Subject(subjectName, difficultyLevel, subjectType);
          subject.setSubjectId(subjectId);
          subjects.add(subject);
          int subId = getSubjectId(userId, subjectName);
          ex = getExamForSubject(subId);
          subject.setExams(ex);
          subjects.add(subject);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση των μαθημάτων: " + e.getMessage());
    }
    return subjects;
  }

  /** returns the list of exams for a specific user. */
  public static List<Exam> getExamForSubject(int subjectId) {
    List<Exam> exams = new ArrayList<>();
    String sql = "SELECT e.deadline, e.pages, e.revisionPerxPages, e.minutesPer20Slides,"
        + " s.subjectName FROM Exam e, Subject s "
        + "WHERE e.subjectId = s.subjectId AND e.subjectId = ?;";
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, subjectId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String deadlineSt = rs.getString("deadline");
          LocalDate deadline = LocalDate.parse(deadlineSt);
          int pages = rs.getInt("pages");
          int revisionPerxPages = rs.getInt("revisionPerxPages");
          double minutesPer20Slides = rs.getDouble("minutesPer20Slides");
          String subjectName = rs.getString("subjectName");
          Exam exam = new Exam(pages, revisionPerxPages, deadline, minutesPer20Slides);
          exams.add(exam);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση exams: " + e.getMessage());
    }
    return exams;
  }

  /** returns the list of not available days for a user. */
  public static List<LocalDate> getNonAvDates(int userId) {
    String sql = "SELECT date FROM NonAvDates WHERE userId = ?;";
    List<LocalDate> nonAvDates = new ArrayList<>();
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
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

  /** returns the array containing the availability for each day of the week. */
  public static int[] getAvailability(int userId) {
    String sql = """
           SELECT mondayAv, tuesdayAv, wednesdayAv, thursdayAv, fridayAv, saturdayAv, sundayAv
           FROM Availability
           WHERE userId = ?;
           """;
    int[] availability = new int[8];
    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
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

  /** returns the list of days. */
  public static List<Day> getDays(int userId) {
    String sql = "SELECT dayId FROM Day WHERE userId = ?;";
    List<Day> days = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int dayId = rs.getInt("dayId");
          List<ScheduledTask> tasksForDay = getTasksForDay(dayId);
          days.add(new Day(tasksForDay));
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση ημερών " + e.getMessage());
    }
    return days;
  }

  /** returns the list of exams. */
  public static List<Exam> getExam(int userId) {
    List<Exam> exams = new ArrayList<>();
    List<Integer> subjectId = getSubjectId(userId);
    for (int id : subjectId) {
      System.out.println("subjectIds in getExam: ");
      System.out.println("subjectId: " + subjectId);
    }
    if (subjectId.isEmpty()) {
      return exams; // returns an empty list if there are no subjectId's
    }
    String sql = "SELECT e.deadline, e.pages, e.revisionPerxPages,"
        + " e.minutesPer20Slides, e.examId, s.subjectName "
        + "FROM Exam e JOIN Subject s ON e.subjectId = s.subjectId WHERE e.subjectId IN ("
        + subjectId.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      int index = 1;
      for (int id : subjectId) {
        ps.setInt(index++, id);
      }

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String deadlineSt = rs.getString("deadline");
          LocalDate deadline = LocalDate.parse(deadlineSt, FORMATTER);
          int pages = rs.getInt("pages");
          int revisionPerxPages = rs.getInt("revisionPerxPages");
          double minutesPer20Slides = rs.getDouble("minutesPer20Slides");
          String subjectName = rs.getString("subjectName");
          int examId = rs.getInt("examId");
          Exam exam = new Exam(pages, revisionPerxPages, deadline, minutesPer20Slides);
          exam.setExamId(examId);
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
        String sql = "SELECT e.deadline, e.pages, e.revisionPerXPages, e.minutesPer20Slides,
         + s.subjectName FROM Exam e JOIN Subject s ON e.subjectID = s.subjectID
         + WHERE e.subjectID = ?;";

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
                        Exam exam = new Exam(pages, revisionPerXPages,
                         + deadline, minutesPer20Slides);
                        exams.add(exam);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση exams: " + e.getMessage());
        }
        return exams;
    }*/

  /** returns the list of scheduled tasks. */
  public static List<ScheduledTask> getTasks(int userId) {
    String sql = "SELECT taskName, hoursAllocated, taskStatus, taskDate, subjectName,"
        + "taskType, timeStarted, timeCompleted, taskId FROM Task WHERE userId = ?;";
    List<ScheduledTask> scheduledTasks = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String taskName = rs.getString("taskName");
          int hoursAllocated = rs.getInt("hoursAllocated");
          String taskTypeString = rs.getString("taskStatus");
          ScheduledTask.TaskStatus taskStatus =
              ScheduledTask.TaskStatus.valueOf(taskTypeString.toUpperCase());
          String dstring = rs.getString("taskDate");
          LocalDate taskDate = LocalDate.parse(dstring, FORMATTER);
          String subjectName = rs.getString("subjectName");
          String taskType = rs.getString("taskType");
          String  timeStString = rs.getString("timeStarted");
          LocalTime timeStarted = LocalTime.parse(timeStString);
          String timeComString = rs.getString("timeCompleted");
          LocalTime timeCompleted = LocalTime.parse(timeComString);
          int taskId = rs.getInt("taskId");
          ScheduledTask t = new ScheduledTask(taskName, taskType, hoursAllocated,
              taskStatus, timeStarted, timeCompleted, taskDate, subjectName);
          t.setTaskId(taskId);
          scheduledTasks.add(t);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση Task : " + e.getMessage());
    }
    return scheduledTasks;
  }

  /** returns the list of assignments. */
  public static List<Assignment> getAssignments(int userId) {
    List<Assignment> assignments = new ArrayList<>();

    String sql = "SELECT a.title, a.deadline, a.estimateHours, a.completedDate, a.difficulty "
        + "FROM Assignment a WHERE userId = ?";

    try (Connection connection = DataBaseManager.connect();
         PreparedStatement ps = connection.prepareStatement(sql)) {

      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String title = rs.getString("title");
          LocalDate deadline = LocalDate.parse(rs.getString("deadline"));
          int estimateHours = rs.getInt("estimateHours");
          String completedDateString = rs.getString("completedDate");
          int difficulty = rs.getInt("difficulty");
          int assignmentId = rs.getInt("assignmentId");
          LocalDate completedDate = (completedDateString != null && !completedDateString.isEmpty())
              ? LocalDate.parse(completedDateString)
              : null;
          Assignment assignment = new Assignment(title, deadline, estimateHours, difficulty);
          assignment.setAssignmentId(assignmentId);
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

  /** returns a list of weeks. */
  public static List<Week> getWeeks(int userId) {
    String sql = "SELECT weekId FROM Week WHERE userId = ?;";
    List<Week> weeks = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int weekId = rs.getInt("weekId");
          Week week = new Week();
          List<Day> daysOfWeek = getDays(weekId);
          week.setDaysOfWeek(daysOfWeek);
          weeks.add(week);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση εβδομάδων: " + e.getMessage());
    }
    return weeks;
  }

  /** returns the scheduled tasks for a specific day. */
  public static List<ScheduledTask> getTasksForDay(int dayId) {
    String sql = "SELECT taskName, hoursAllocated, timeStarted, timeCompleted,"
        + " taskStatus, taskDate, subjectName, taskType FROM Task WHERE dayId = ?";
    List<ScheduledTask> scheduledTasks = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, dayId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String taskName = rs.getString("taskName");
          int hoursAllocated = rs.getInt("hoursAllocated");
          String timeStartedString =
              rs.getString("timeStarted");
          LocalTime timeStarted =
              LocalTime.parse(timeStartedString, DateTimeFormatter.ofPattern("HH:mm"));
          String timeCompletedString =
              rs.getString("timeCompleted");
          LocalTime timeCompleted =
              LocalTime.parse(timeCompletedString, DateTimeFormatter.ofPattern("HH:mm"));
          String taskTypeString =
              rs.getString("taskStatus");
          ScheduledTask.TaskStatus taskStatus =
              ScheduledTask.TaskStatus.valueOf(taskTypeString.toUpperCase());
          String dstring =
              rs.getString("taskDate");
          LocalDate taskDate = LocalDate.parse(dstring, FORMATTER);
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

  /** a list for all the completed tasks. */
  public static List<ScheduledTask> getCompletedTasks(int userId) {
    String sql = "SELECT taskName, hoursAllocated, taskStatus, taskDate, subjectName,"
        + "taskType, timeStarted, timeCompleted, taskId FROM CompletedTask WHERE userId = ?;";
    List<ScheduledTask> scheduledTasks = new ArrayList<>();

    try (Connection c = DataBaseManager.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String taskName = rs.getString("taskName");
          int hoursAllocated = rs.getInt("hoursAllocated");
          String taskTypeString = rs.getString("taskStatus");
          ScheduledTask.TaskStatus taskStatus =
              ScheduledTask.TaskStatus.valueOf(taskTypeString.toUpperCase());
          String dstring = rs.getString("taskDate");
          LocalDate taskDate = LocalDate.parse(dstring, FORMATTER);
          String subjectName = rs.getString("subjectName");
          String taskType = rs.getString("taskType");
          String  timeStString = rs.getString("timeStarted");
          LocalTime timeStarted = LocalTime.parse(timeStString);
          String timeComString = rs.getString("timeCompleted");
          LocalTime timeCompleted = LocalTime.parse(timeComString);
          int taskId = rs.getInt("taskId");
          ScheduledTask t = new ScheduledTask(taskName, taskType, hoursAllocated,
              taskStatus, timeStarted, timeCompleted, taskDate, subjectName);
          t.setTaskId(taskId);
          scheduledTasks.add(t);
        }
      }
    } catch (SQLException e) {
      System.err.println("Σφάλμα κατά την ανάκτηση CompletedTask : " + e.getMessage());
    }
    return scheduledTasks;
  }
}