package org.javawavers.studybuddy.ui_ux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.ScheduledTask;
import static org.javawavers.studybuddy.courses.StaticUser.staticUser;
import org.javawavers.studybuddy.courses.Subject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.javawavers.studybuddy.database.ActiveUser;

public class Calendar {
  private LocalDate currentWeekStart;
  // initialize the variable count
  int count = 0;
  private ArrayList<Week> totalWeeks;

  // initialize the lists and VBoxes, which are used for dynamic processing
  // and displaying the tasks
  public static List<String> notStartedYet = new ArrayList<>();
  public static List<String> completed = new ArrayList<>();
  private static final GridPane calendarGrid = new GridPane();
  List<Subject> subject = new ArrayList<>();
  private VBox upcomingTasksBox = new VBox(10);
  private VBox completedTasksBox = new VBox(10);

  public Node calendar() {
    // initialize all panels
    VBox centerPanel = createCenterPanel();
    centerPanel.setPadding(new Insets(20));
    return centerPanel;
  }

  // method for the creation of hte central panel
  private VBox createCenterPanel() {
    VBox centerPanel = new VBox(10);
    centerPanel.setPadding(new Insets(20));
    centerPanel.setStyle("-fx-background-color: white;");

    /* create an HBox for arranging the elements in a horizontal position
     * define the position, set the current day, as well as the week the user is currently in
     * with the DateTimeFormatter, we define the format in which the week label will be displayed
     * create two buttons for navigating through the weeks and for the user to view their schedule
     */
    HBox weekSwitcher = new HBox(10);
    weekSwitcher.setTranslateY(40);
    weekSwitcher.setAlignment(Pos.CENTER);

    LocalDate today = LocalDate.now();
    currentWeekStart = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

    // initialize variable weeklabel
    Label weekLabel = new Label(formatWeekLabel(currentWeekStart, formatter));
    weekLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
    weekLabel.setStyle("-fx-text-fill: black;");

    // HBox.setHgrow(weekLabel, Priority.ALWAYS);
    // place the buttons for the user to navigate through the weeks
    Button prevButton = new Button("<");
    prevButton.setStyle(
        "-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    prevButton.setPrefSize(30, 30);

    Button nextButton = new Button(">");
    nextButton.setStyle(
        "-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    nextButton.setPrefSize(30, 30);

   // GridPane calendarGrid = new GridPane();
    calendarGrid.setStyle("-fx-border-color: black;");
    calendarGrid.setGridLinesVisible(true);

    subject = staticUser.getSubjects();
    for (Subject sub : subject) {
        System.out.println("call subject in createCalandar");
        System.out.println(sub);
    }
    /*createCalendarGrid(calendarGrid, count, subject, totalWeeks);///////////////////////////////////////////
    System.out.println("test calendar" + staticUser.getTotalWeeks());
    if (staticUser.getTotalWeeks() !=  null) {
        PrintWeeks printWeek = new PrintWeeks();
        printWeek.printWeeks(staticUser.getTotalWeeks());
    }
*/

    // variable count, which increases when the user presses the button to move the weeks forward
    //  and decreases otherwise. When count == 0, the 'Today' button will be displayed
    prevButton.setOnAction(
        event -> {
          if (count > 0) {
            count = count - 1;
            if (count < totalWeeks.size()) {
              currentWeekStart = currentWeekStart.minusWeeks(1);
              weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));
              createCalendarGrid(calendarGrid, count, SimulateAnnealing.getSubjects(), totalWeeks);
            }
          } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Προς το παρόν, δεν υπάρχουν προηγούμενες εβδομάδες. Αλλά μην ανησυχείς, όλα ξεκινούν από εδώ!");
            alert
                .getDialogPane()
                .getStylesheets()
                .add(getClass().getResource("/alert.css").toExternalForm());
            alert.getDialogPane().setMinWidth(500);
            alert.getDialogPane().setMinHeight(300);
            alert.showAndWait();
          }
        });

    nextButton.setOnAction(
        event -> {
          count++;
          if (count > totalWeeks.size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle(null);
              alert.setHeaderText(null);
              alert.setContentText("Πάει κι αυτό! 🎉 Ώρα για λίγη ξεκούραση τώρα! Η εξεταστική σου σταματάει εδώ");
              alert
                  .getDialogPane()
                  .getStylesheets()
                  .add(getClass().getResource("/alert.css").toExternalForm());
              alert.getDialogPane().setMinWidth(500);
              alert.getDialogPane().setMinHeight(300);
              alert.showAndWait();
          } else {
            currentWeekStart = currentWeekStart.plusWeeks(1);
            weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));
            createCalendarGrid(calendarGrid, count, SimulateAnnealing.getSubjects(), totalWeeks);
              
          }
        
        });

    weekSwitcher.setTranslateY(40);
    weekSwitcher.setAlignment(Pos.CENTER);
    weekSwitcher.getChildren().addAll(prevButton, weekLabel, nextButton);

    Button todayButton = new Button("Today");
    todayButton.setStyle(
        "-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
    todayButton.setFont(Font.font("System", FontWeight.BOLD, 14));
    todayButton.setTextFill(Color.WHITE);
    todayButton.setOnAction(
        event -> {
          createCalendarGrid(calendarGrid, 0, SimulateAnnealing.getSubjects(), totalWeeks);
        });

    // button for the user to insert availability
    Button availabilityButton = new Button("Availiability");
    availabilityButton.setStyle(
        "-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
    availabilityButton.setFont(Font.font("System", FontWeight.BOLD, 14));
    availabilityButton.setTextFill(Color.WHITE);
    availabilityButton.setPrefWidth(160);

    // define that when the user clicks the button, the popup page will open
    availabilityButton.setOnAction(
        event -> {
          Stage popUpStage = new Stage();
          AvailabilityPage availabilityPage = new AvailabilityPage(popUpStage);
          VBox availPageLayout = availabilityPage.availabilityPage();

          popUpStage.initStyle(StageStyle.UTILITY);
          popUpStage.initModality(Modality.APPLICATION_MODAL);
          Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

          double centerX = parentStage.getX() + parentStage.getWidth() / 2 - 300;
          double centerY = parentStage.getY() + parentStage.getHeight() / 2 - 200;

          popUpStage.setX(centerX);
          popUpStage.setY(centerY);

          Scene popUpScene = new Scene(availPageLayout, 600, 400);
          popUpStage.setScene(popUpScene);
          popUpStage.show();
        });

    // define the availabilty's button position
    StackPane availabilityPane = new StackPane(availabilityButton);
    availabilityPane.setPrefSize(150, 30);
    availabilityPane.setLayoutX(centerPanel.getWidth() - 300);
    availabilityPane.setLayoutY(200);

    // button for refreshing the program
    Button refreshButton = new Button();
    refreshButton.setStyle(
        "-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    refreshButton.setPrefSize(30, 30);

    // add an icon to the button for circular arrows
    SVGPath refreshIcon = new SVGPath();
    refreshIcon.setContent(
        "M12 2V5C7.58 5 4 8.58 4 13C4 15.27 5.05 17.36 6.77 18.63L8.22 17.18C7.04 16.17 6.27 14.67 6.27 13C6.27 9.8 8.8 7.27 12 7.27V10L16 6L12 2ZM18.23 4.37L16.78 5.82C17.96 6.83 18.73 8.33 18.73 10C18.73 13.2 16.2 15.73 13 15.73V12L9 16L13 20V17C17.42 17 21 13.42 21 9C21 6.73 19.95 4.64 18.23 4.37Z");
    refreshIcon.setFill(Color.WHITE);
    refreshButton.setGraphic(refreshIcon);

    refreshButton.setOnAction(
        event -> {
          Alert confirmRefresh = new Alert(Alert.AlertType.CONFIRMATION);
          confirmRefresh.setTitle("Επιβεβαίωση Refresh");
          confirmRefresh.setHeaderText(null);
          DialogPane dialogPane = confirmRefresh.getDialogPane();
          dialogPane.getStylesheets().add(getClass().getResource("/alert.css").toExternalForm());
          confirmRefresh.setContentText("σίγουρα θέλεις να αλλάξεις το τωρινό πρόγραμμα σου");
          Optional<ButtonType> answer = confirmRefresh.showAndWait();
          if (answer.isPresent() && answer.get() == ButtonType.OK) {
            SimulateAnnealing sAnnealing = new SimulateAnnealing();
            ExamPage exPage = new ExamPage();

            subject = staticUser.getSubjects();
            if (!exPage.getSubjects().isEmpty()) {
              System.out.print("Not empty subject list");
            } else {
              System.out.println("empty subject list");
            }

            SimulateAnnealing.scheduleResult();
            totalWeeks = new ArrayList<>(staticUser.getTotalWeeks());
              PrintWeeks printWeek = new PrintWeeks();
              printWeek.printWeeks(totalWeeks);
            /*
            int [][] schedule = SimulateAnnealing.getSchedule();
            int colSize = schedule[0].length;
            ArrayList<Task> bestTask = new ArrayList<>(SimulateAnnealing.getBestTask());
            LocalDate today2 = LocalDate.now(); // Today's date
            DayOfWeek currentDayOfWeek = today2.getDayOfWeek();
            int daysUntilMonday = currentDayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();

            // Initialize the first week
            Week currentWeek = new Week();

            // Fill days before today with empty tasks
            for (int i = 0; i < daysUntilMonday; i++) {
              Day emptyDay = new Day(); // Day with no tasks
              currentWeek.getDaysOfWeek().add(emptyDay);
            }

            List<ScheduledTask> scheduledTasksForDay = new ArrayList<>();

            for (int dayIndex = 0; dayIndex < colSize; dayIndex++) {
              LocalDate currentDate = today2.plusDays(dayIndex - daysUntilMonday); // Calculate current date

              // Clear the scheduled tasks for the day
              scheduledTasksForDay.clear();
              for (int taskIndex = 0; taskIndex < schedule.length; taskIndex++) {
                int taskId = schedule[taskIndex][dayIndex];
                String taskType = " ";
                if (taskId > 0) { // If there is a task for the specific slot
                  Task task = bestTask.get(taskId); // Retrieve the Task from the list
                  if (task.getTaskType() == 1) {
                    taskType = "Διάβασμα";
                  } else if (task.getTaskType() == 2) {
                    taskType = "Επανάληψη";
                  } else {
                    taskType = "Εργασία";
                  }

                  ScheduledTask scheduledTask = new ScheduledTask(
                          task.getSubject(), taskType,
                          (int) Math.ceil(task.getTaskHours()),
                          currentDate,
                          new Subject(task.getSubject()) // Create Subject from the Task
                  );
                  scheduledTasksForDay.add(scheduledTask);
                }
              }

              // Create a Day object for the current day
              Day currentDay = new Day();
              currentDay.todayTasks.addAll(scheduledTasksForDay);

              // Add the day to the week
              currentWeek.getDaysOfWeek().add(currentDay);

              // If the week is complete or it's the last day, save it
              if (currentWeek.getDaysOfWeek().size() == 7 || dayIndex == colSize - 1) {
                totalWeeks.add(currentWeek);
                currentWeek = new Week(); // Start a new week
              }
            }
            */
            createCalendarGrid(calendarGrid, 0, subject, totalWeeks);
          }
        });

    // group all the elements of the center together and return them
    centerPanel
        .getChildren()
        .addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);

    return centerPanel;
  }

  // create the checkboxes
  /* private HBox createCheckBox(String taskName) {
      HBox checkBoxBox = new HBox(10);
      checkBoxBox.setAlignment(Pos.CENTER_LEFT);


      CheckBox taskCheckBox = new CheckBox(taskName);
      taskCheckBox.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

      taskCheckBox.setOnAction(event -> {
        if (taskCheckBox.isSelected()) {
          if (notStartedYet.contains(taskName)) {
            notStartedYet.remove(taskName);
            completed.add(taskName);
          }
        } else {
          if (completed.contains(taskName)) {
            completed.remove(taskName);
            notStartedYet.add(taskName);
          }
        }

        //updateUpcomingTasks(upcomingTasksBox);
        //updateCompletedTasks(completedTasksBox);


        //System.out.println("Completed tasks: " + completed);
      });

      checkBoxBox.getChildren().add(taskCheckBox);

      return checkBoxBox;
    }
  */
  // create the calendar
  // there is no schedule

  private void createCalendarGrid(
      GridPane grid, int weeknumber, List<Subject> subject, List<Week> weeks) {
    grid.getChildren().removeIf(node -> node instanceof Label);
    grid.getColumnConstraints().clear();
    grid.getRowConstraints().clear();

    String[] days = {"Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββατο", "Κυριακή"};
    // Define grid dimensions
    int daysInWeek = 7;
    // Set column constraints (for 7 days)
    for (int i = 0; i < daysInWeek; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0 / 7); // Distribute columns equally
      grid.getColumnConstraints().add(column);
    }

    // Add day titles (Δευτέρα, Τρίτη, ...)
    for (int i = 0; i < daysInWeek; i++) {
      Label dayLabel = new Label(days[i]);
      dayLabel.setStyle(
          "-fx-font-weight: bold; -fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
      dayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
      dayLabel.setPrefHeight(60);
      dayLabel.setPrefWidth(140);
      GridPane.setConstraints(dayLabel, i, 0); // Place titles in the first row
      grid.getChildren().add(dayLabel);
    }

    // Set row constraints (for tasks in each day)
    int maxTasksPerDay = 13; // Adjust based on your needs
    for (int i = 1; i <= maxTasksPerDay; i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / (maxTasksPerDay)); // Distribute rows equally
      grid.getRowConstraints().add(row);
    }
    // Get the list of weeks
    CreateWeekDay createWeekDay = new CreateWeekDay();

    // Select the current week (adjust "weeknumber" based on your logic)
    if (weeks == null || weeks.isEmpty()) {
      System.out.println("Η λίστα εβδομάδων είναι null.");
      return;
    }
    Week thisWeek = weeks.get(weeknumber);

    // Start populating the grid with data
    int dayCount = 0;
    for (Day d : thisWeek.getDaysOfWeek()) {
      int rowCount = 1;
        System.out.println("Ημέρα: ελεγχος των ημερολογιου" + d);
      if (!d.getTodayTasks().isEmpty()) {
        for (ScheduledTask s : d.getTodayTasks()) {
            System.out.println(s);
          Label cell = new Label();
          cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
          cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
          cell.setPrefSize(140, 60);
          String taskText = s.getSubjectName() + ": " + s.getTaskName();
          cell.setText(taskText);

          // Add the task to the grid
          GridPane.setConstraints(cell, dayCount, rowCount);
          grid.getChildren().add(cell);
          cell.setOnMouseClicked(
              event -> {
                String taskDescription = "κενο";
                taskDescription = s.toString();
                LocalDate examDate = null;
                //List<Exam> exams = s.getExams();

                if (subject != null) {
                  for (Subject subj : subject) {
                    if (taskDescription.contains(subj.getCourseName())) {
                        examDate = subj.getExams().get(0).getExamDate();
                      break;
                    }
                  }
                }
                Popupdiathesimotita popup = new Popupdiathesimotita();
                popup.setTaskLists(notStartedYet, completed);
                popup.setTaskDescription(taskDescription, examDate);
                Stage popupStage = new Stage();
                popup.start(popupStage);
              });
          rowCount++;
        }
        dayCount++;
      } else {
        dayCount++;
      }
    }
    // int rowIndex = 1; // Start from the second row
    // for (int dayIndex = 0; dayIndex < thisWeek.daysOfWeek.size(); dayIndex++) {
    // Day currentDay = thisWeek.daysOfWeek.get(dayIndex);
    // List<ScheduledTask> scheduledTasks = currentDay.todayTasks;

    // Populate each cell with the tasks of the day
    // for (int taskIndex = 0; taskIndex < scheduledTasks.size(); taskIndex++) {
    // ScheduledTask task = scheduledTasks.get(taskIndex);

    // Create a label for each task
    // Label taskLabel = new Label(task.getSubject() + " - " + task.getTaskType() + " (" +
    // task.getHours() + "h)");
    // taskLabel.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-padding: 5;");
    // taskLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
    // taskLabel.setPrefHeight(50);
    // taskLabel.setPrefWidth(140);

    // Add the task to the grid
    // GridPane.setConstraints(taskLabel, dayIndex, rowIndex + taskIndex);
    // grid.getChildren().add(taskLabel);
  }

  private String formatWeekLabel(LocalDate weekStart, DateTimeFormatter formatter) {
    LocalDate weekEnd = weekStart.plusDays(6);
    return String.format("%s - %s", formatter.format(weekStart), formatter.format(weekEnd));
  }
}

// for (int col = 1; col <= daysinWeek; col++) {
// for (int row = 1; row < 14; row++) {
// 1  7
// Label cell = new Label();
// System.out.println("scheduuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
//  cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
// cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
//  cell.setPrefSize(140, 60);
// Label cell = new Label();

// cell.setGraphic(new ImageView(image));
// cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;
// -fx-pref-width: 140; -fx-pref-height: 60;");
// cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
// cell.setPrefSize(140, 60);
// cell.setText(SimulateAnnealing.printSchedule(row, col));
// MainTestAlgorithm algorithm = new MainTestAlgorithm();
// cell.setText(algorithm.run(row, col));
// cell.setText(besttask.get(row).toString());
// ειναι λιστα το weekschedule οχι δισδιαστατος πινακασ

// if (selectedWeek != null && besttask != null &&
//    row < selectedWeek.length && col < selectedWeek[row].length &&
//        selectedWeek[row][col] > 0 && selectedWeek[row][col] <= besttask.size()) {
//  System.out.println("scheduleweek" + selectedWeek[row][col]);

//  int taskIndex = selectedWeek[row][col] - 1;// - 1

//  if (taskIndex >= 0 && taskIndex <= besttask.size()) {
// cell.setText(besttask.get(taskIndex).toString());
//    String taskText = besttask.get(taskIndex).toString();
// cell.setText(taskText);
//    String firstWord = taskText.split(" ")[0];
//    for (Subject subje : subject) {
//      if (subje.getCourseName().equalsIgnoreCase(firstWord)) {

// cell.setGraphic(new ImageView(image));
//        cell.setText(firstWord);
//        cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
//        break;
//      }
//    }
//  } else {
//  cell.setText("");
// }
// } else {
//  cell.setText("");
// }

          /*final int rowFinal = row;
                    final int colFinal = col;

          //οταν ο χρηστης παταει πανω σε οποιδηποτε κελη τοτε του εμφανιζεται η σελιδα popupdiathesimotita
                    cell.setOnMouseClicked(event -> {

                      String taskDescription = "κενο";
                      LocalDate examDate = null;
                      List<Exam> exams = Subject.getExams();

                      if (selectedWeek != null && besttask != null &&
                              rowFinal < selectedWeek.length && colFinal < selectedWeek[rowFinal].length &&
                              selectedWeek[rowFinal][colFinal] > 0 && selectedWeek[rowFinal][colFinal] <= besttask.size()) {

                        int taskIndex = selectedWeek[rowFinal][colFinal] - 1;
                        if (taskIndex >= 0 && taskIndex < besttask.size()) {
                          taskDescription = besttask.get(taskIndex).toString();
                        }
                      }
                      if (subject != null) {
                        for (Subject subj : subject) {
                          if (taskDescription.contains(subj.getCourseName())) {
                            for (Exam exam : exams) {
                              examDate = exam.getExamDate();
                            }
                            break;
                          }
                        }
                      }


                      Popupdiathesimotita popup = new Popupdiathesimotita();
                      popup.setTaskLists(notStartedYet, completed);
                      popup.setTaskDescription(taskDescription, examDate);
                      Stage popupStage = new Stage();
                      popup.start(popupStage);
                    });

                    GridPane.setConstraints(cell, col, row);
                    grid.getChildren().add(cell);
                  }

                     */

// οποτε καλειτε ανανεωνονται αναλογα με εκεινα τα δεδομενα το popup που εμφανιζετε
  /*private void updateUpcomingTasks(VBox upcomingTasksBox) {
    upcomingTasksBox.getChildren().clear();
    for (String taskDescription : notStartedYet) {
      //System.out.println("Add" + taskDescription);
      HBox checkBoxBox = createCheckBox(taskDescription);
      upcomingTasksBox.getChildren().add(checkBoxBox);
    }
  }


  private void updateCompletedTasks(VBox completedTasksBox) {
    completedTasksBox.getChildren().clear();
    for (String taskDescription : completed) {
      HBox checkBoxBox = createCheckBox(taskDescription);
      //System.out.println("completed" + taskDescription);
      completedTasksBox.getChildren().add(checkBoxBox);
    }
  }

  private String formatWeekLabel(LocalDate weekStart, DateTimeFormatter formatter) {
    LocalDate weekEnd = weekStart.plusDays(6);
    return String.format("%s - %s", formatter.format(weekStart), formatter.format(weekEnd));
  }
  //δημιουργια του pop up το οποιο ανοιγει οταν ο χρηστης πατησει οποιοδηποτε απο τα κουμπια ypcomingtasks/completedtasks
  private void showTasksPopup(String title, List<String> taskList) {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle(title);

    VBox popupContent = new VBox(10);
    popupContent.setPadding(new Insets(10));
    popupContent.setAlignment(Pos.TOP_CENTER);
    //οριζουμε τον τιτλο αναλογα με το κουμπι  που εχει πατηθει
    Label titleLabel = new Label(title);
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    //δημιουργουμε taskbox για τα task
    VBox tasksBox = new VBox(5);
    tasksBox.setAlignment(Pos.TOP_LEFT);
    tasksBox.setStyle("-fx-max-height: 300px;");

    //δημιουργουμε scrollpane για να μπορει ο χρηστης να κανει scroll και να δει ολα τα task τα οποια εχει να κανει εκεινη την εβδομαδα
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(tasksBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(300);

    Map<CheckBox, String> taskCheckBoxMap = new HashMap<>();

    //δημιουργουμε τα checkbox
    if (taskList != null && !taskList.isEmpty()) {
      for (String task : taskList) {
        CheckBox checkBox = new CheckBox(task);
        checkBox.setStyle("-fx-font-size: 14px;");

        //αν το task ειναι στην λιστα notstartedyet ειναι unselected αλλιως στην completed τα task ειναι selected
        if (taskList == notStartedYet && !completed.contains(task)) {
          checkBox.setSelected(false);
        } else if (taskList == completed && notStartedYet.contains(task)) {
          checkBox.setSelected(true);
        }

        tasksBox.getChildren().add(checkBox);
        taskCheckBoxMap.put(checkBox, task);//map για να ελενγχουμε τα task με το checkbox
      }
    } else {
      Label noTasksLabel = new Label("No tasks available");//στην περιπτωση που δεν υπαρχουν task
      tasksBox.getChildren().add(noTasksLabel);
    }
    //δημιουργια κουμπιου οκ που οταν πατηθει αναλογα με το τι εχει πατησει ο χρηστης ενημερωνει τις δυο λιστες
    Button okButton = new Button("OK");
    okButton.setStyle("-fx-background-color: #50D1C6; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 16px;");
    okButton.setOnAction(event -> {

      for (Map.Entry<CheckBox, String> entry : taskCheckBoxMap.entrySet()) {
        CheckBox checkBox = entry.getKey();
        String task = entry.getValue();

        if (checkBox.isSelected() && taskList == notStartedYet) {
          notStartedYet.remove(task);
          completed.add(task);
        } else if (!checkBox.isSelected() && taskList == completed) {
          completed.remove(task);
          notStartedYet.add(task);
        }
      }
      popupStage.close();
      //ενημερωνουμε τα taskboxes
      updateUpcomingTasks(upcomingTasksBox);
      updateCompletedTasks(completedTasksBox);
    });

    popupContent.getChildren().addAll(titleLabel, scrollPane, okButton);

    Scene popupScene = new Scene(popupContent, 300, 400);
    popupStage.setScene(popupScene);
    popupStage.showAndWait();
  }


  private Button createCircularButton(String text, String color) {
    Button button = new Button(text);
    button.setStyle(
        "-fx-background-color: " + color + ";" +
            "-fx-text-fill: black; " +
            "-fx-font-size: 18px; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px; " +
            "-fx-border-color: black; " +
            "-fx-border-radius: 5px; " +
            "-fx-min-width: 200px;"
    );
    return button;
  }



  //αρχικοποιησει των λιστων
  private void initializeTaskLists(List<Task> besttask) {
    for (Task task : besttask) {
      notStartedYet.add(task.toString());
    }

    completed = new ArrayList<>();

    updateUpcomingTasks(upcomingTasksBox);
    updateCompletedTasks(completedTasksBox);
  }

   */
