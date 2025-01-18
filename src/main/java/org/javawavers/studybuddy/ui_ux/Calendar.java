package org.javawavers.studybuddy.ui_ux;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;

/**
 * Represents the Calendar view of the application, displaying weeks and tasks.
 * Allows navigation between weeks, viewing tasks, and updating the user's schedule.
 */
public class Calendar {
  private LocalDate currentWeekStart;
  // initialize the variable count
  int count = 0;
  private ArrayList<Week> totalWeeks;


  public static List<String> notStartedYet = new ArrayList<>();
  public static List<String> completed = new ArrayList<>();
  private static final GridPane calendarGrid = new GridPane();
  List<Subject> subject = new ArrayList<>();


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
    if (!staticUser.getTotalWeeks().isEmpty()) {
      totalWeeks = new ArrayList<>(staticUser.getTotalWeeks());
      createCalendarGrid(calendarGrid, 0, subject, totalWeeks);
    } else {
      totalWeeks = new ArrayList<>();
      createCalendarGrid(calendarGrid, 0, subject, totalWeeks);
    }

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
            alert.setContentText(
                "Προς το παρόν, δεν υπάρχουν προηγούμενες εβδομάδες."
                    + " Αλλά μην ανησυχείς, όλα ξεκινούν από εδώ!");
            alert
                .getDialogPane()
                .getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
            alert.getDialogPane().setMinWidth(500);
            alert.getDialogPane().setMinHeight(300);
            alert.showAndWait();
          }
        });
    // Button to navigate in the next week with the variable count
    nextButton.setOnAction(
        event -> {
          count++;
          if (count > totalWeeks.size() - 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(
                "Πάει κι αυτό! 🎉 Ώρα για λίγη ξεκούραση τώρα! Η εξεταστική σου σταματάει εδώ");
            alert
                .getDialogPane()
                .getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
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
          count = 0;
          currentWeekStart = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
          weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));
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

            createCalendarGrid(calendarGrid, 0, subject, totalWeeks);
          }
        });

    // group all the elements of the center together and return them
    centerPanel
        .getChildren()
        .addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);

    return centerPanel;
  }



  /**
   * Creates the grid for the calendar and populates it with tasks.
   *
   * @param grid The GridPane to hold the calendar.
   * @param weeknumber The current week number.
   * @param subject The list of subjects for the user.
   * @param weeks The list of all weeks.
   */
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
                // List<Exam> exams = s.getExams();

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
  }
  /**
   * Formats the week label displaying the range of the week.
   *
   * @param weekStart The start date of the week.
   * @param formatter The DateTimeFormatter for formatting the date.
   * @return String The formatted week label.
   */
  private String formatWeekLabel(LocalDate weekStart, DateTimeFormatter formatter) {
    LocalDate weekEnd = weekStart.plusDays(6);
    return String.format("%s - %s", formatter.format(weekStart), formatter.format(weekEnd));
  }
}
