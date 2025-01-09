package main.java.org.javawavers.studybuddy.ui_ux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javawavers.studybuddy.calculations.SimulateAnnealing;
import org.javawavers.studybuddy.calculations.Task;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.Subject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Calendar {



  private LocalDate currentWeekStart;
  // αρχικοποιουμε την μεταβλητη count
  int count = 0;



  //αρχικοποιουμε τις λιστες και τα vbox τα οποια χρησιμευουν στην δυναμικη επεξεργασια και εμφανιση των task
  public static List<String> notStartedYet = new ArrayList<>();
  public static List<String> completed = new ArrayList<>();
  private VBox upcomingTasksBox = new VBox(10);
  private VBox completedTasksBox = new VBox(10);





  public Node calendar() {
    // Αρχικοποιουμε ολα τα panel
    VBox centerPanel = createCenterPanel();
    centerPanel.setPadding(new Insets(20));
    return centerPanel;

  }

  //μεθοδος για την δημιουργια του κεντρικου panel
  private VBox createCenterPanel() {
    VBox centerPanel = new VBox(10);
    centerPanel.setPadding(new Insets(20));
    centerPanel.setStyle("-fx-background-color: white;");


    /*Δημιουργουμε ενα hbox για την διαταξη τον στοιχειων σε οριζοντια θεση
     *οριζουμε την θεση του οριζουμε την σημερινη μερα καθως και την εβδομαδα την οποια διανυει ο χρηστης
     *με την datetimeformater οριζουμε τον τροπο με τον οποιο θα εμφανιζεται το weeklabel
     *δημιουργουμε δυο κουμπια τα οποια θα ειναι για την πλοηγηση του χρηστη στις εβδομαδες και να μπορει να δει το προγραμμα του
     */
    HBox weekSwitcher = new HBox(10);
    weekSwitcher.setTranslateY(40);
    weekSwitcher.setAlignment(Pos.CENTER);

    LocalDate today = LocalDate.now();
    currentWeekStart = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

    //αρχικοποιουμε την μεταβλητη weeklabel
    Label weekLabel = new Label(formatWeekLabel(currentWeekStart, formatter));
    weekLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
    weekLabel.setStyle("-fx-text-fill: black;");

    HBox.setHgrow(weekLabel, Priority.ALWAYS);
    //βαζουμε τα κουμπια για να πλοηγειτε ο χρηστης στις εβδομαδες
    Button prevButton = new Button("<");
    prevButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    prevButton.setPrefSize(30, 30);

    Button nextButton = new Button(">");
    nextButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    nextButton.setPrefSize(30, 30);


    GridPane calendarGrid = new GridPane();
    calendarGrid.setStyle("-fx-border-color: black;");
    calendarGrid.setGridLinesVisible(true);
    //μεταβλητη count η οποια μολις ο χρηστης παταει το κουμπι που παει τις εβδομαδες μπροστα αυξανεται αλλιως μειωνεται οταν count == 0 τοτε θα εεμφανιζετε το κουμπι today
    prevButton.setOnAction(event -> {
      count = count + 1;
      currentWeekStart = currentWeekStart.minusWeeks(1);
      weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));

    });

    nextButton.setOnAction(event -> {
      count = count - 1;
      currentWeekStart = currentWeekStart.plusWeeks(1);
      weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));

    });


    weekSwitcher.setTranslateY(40);
    weekSwitcher.setAlignment(Pos.CENTER);
    weekSwitcher.getChildren().addAll(prevButton, weekLabel, nextButton);


    Button todayButton = new Button("Today");
    todayButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
    todayButton.setFont(Font.font("System", FontWeight.BOLD, 14));
    todayButton.setTextFill(Color.WHITE);

    if (count == 0) {
      todayButton.setVisible(true);
    } else {
      todayButton.setVisible(false);
    }
    //createCalendarGrid(calendarGrid,null,null, 0, null, null);

    //κουμπι για να βαζει ο χρηστης την διαθεσημοτητα
    Button availabilityButton = new Button("Availiability");
    availabilityButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
    availabilityButton.setFont(Font.font("System", FontWeight.BOLD, 14));
    availabilityButton.setTextFill(Color.WHITE);
    availabilityButton.setPrefWidth(160);

    //οριζουμε οταν ο ζρηστης παταει πανω στο κουμπι να ανοιγει την σελιδα popupdia
    availabilityButton.setOnAction(event ->  {
      Popupdia popup1 = new Popupdia();
      Stage popup1Stage = new Stage();
      popup1.start(popup1Stage);


    });

    //οριζουμε την θεση του κουμπιου availiability
    StackPane availabilityPane = new StackPane(availabilityButton);
    availabilityPane.setPrefSize(150, 30);
    availabilityPane.setLayoutX(centerPanel.getWidth() - 300);
    availabilityPane.setLayoutY(200);

    //κουμπι για Refresh του προογραμματος
    Button refreshButton = new Button();
    refreshButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
    refreshButton.setPrefSize(30, 30);

    //προσθηκη εικονιδιου στο κουμπι για κυκλικα βελη
    SVGPath refreshIcon = new SVGPath();
    refreshIcon.setContent("M12 2V5C7.58 5 4 8.58 4 13C4 15.27 5.05 17.36 6.77 18.63L8.22 17.18C7.04 16.17 6.27 14.67 6.27 13C6.27 9.8 8.8 7.27 12 7.27V10L16 6L12 2ZM18.23 4.37L16.78 5.82C17.96 6.83 18.73 8.33 18.73 10C18.73 13.2 16.2 15.73 13 15.73V12L9 16L13 20V17C17.42 17 21 13.42 21 9C21 6.73 19.95 4.64 18.23 4.37Z");
    refreshIcon.setFill(Color.WHITE);
    refreshButton.setGraphic(refreshIcon);

    refreshButton.setOnAction(event -> {
      SimulateAnnealing sAnnealing = new SimulateAnnealing();
      System.out.println("s");
      ExamPage exPage=new ExamPage();
      if(!exPage.getSubjects().isEmpty()){
        System.out.print("Not empty subject list");
      } else {
        System.out.println("empty");
      }
      System.out.println("Subjects in ExamPage: " + exPage.getSubjects().size());
      for (Subject s : exPage.getSubjects()) {
        sAnnealing.addSubject(s);
        System.out.println("////////////");
      }
      List<Subject> subject = SimulateAnnealing.getSubjects();
      SimulateAnnealing.scheduleResult();
      int daysinWeek = 7;
      List<int[][]> weekschedule = splitSchedule(SimulateAnnealing.getSchedule(), daysinWeek);
      List<Task> besttask = SimulateAnnealing.getBestTask();
      createCalendarGrid(calendarGrid, SimulateAnnealing.getBestTask(), weekschedule, count, besttask, subject);

    });

    //βαζουμε ολα τα στοιχεια του κεντρου μαζι και τα επιστρεφουμε
    centerPanel.getChildren().addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);


    return centerPanel;
  }

  //δημιουργουμε τα checkbox
  private HBox createCheckBox(String taskName) {
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

      updateUpcomingTasks(upcomingTasksBox);
      updateCompletedTasks(completedTasksBox);


      //System.out.println("Completed tasks: " + completed);
    });

    checkBoxBox.getChildren().add(taskCheckBox);

    return checkBoxBox;
  }

  //δημιουργουμε το ημερολογιο
  //δεν υπαρχει schedule

  private void createCalendarGrid(GridPane grid, List<Task> bestTask, List<int[][]> weekSchedule, int count, List<Task> besttask, List<Subject> subject) {
    String[] days = {"Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σαββάτο", "Κυριακή"};
   // Exam exam = new Exam();
    int[][] selectedWeek = weekSchedule.get(count);
    int daysinWeek = 7;


    for (int i = 1; i < daysinWeek ; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0 / 7);
      grid.getColumnConstraints().add(column);
    }

//βαζουμε τους τιτλους για την καθε μερα
    for (int i = 0; i < 7; i++) {
      Label dayLabel = new Label(days[i]);
      dayLabel.setStyle("-fx-font-weight: bold; -fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
      dayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
      dayLabel.setPrefHeight(60);
      dayLabel.setPrefWidth(140);
      GridPane.setConstraints(dayLabel, i, 0);
      grid.getChildren().add(dayLabel);
    }

//να εχουν ολες γραμμες το ιδιο υψος
    for (int i = 0; i < 12; i++) {  // 11 γραμμες για τον πινακα
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / 11);
      grid.getRowConstraints().add(row);
    }


    for (int row = 1; row < 12; row++) {
      for (int col = 0; col <= daysinWeek -1; col++) {
        Label cell = new Label();
        //  cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
        // cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
        //  cell.setPrefSize(140, 60);
        // Label cell = new Label();

        //cell.setGraphic(new ImageView(image));
        cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center; -fx-pref-width: 140; -fx-pref-height: 60;");
        cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
        cell.setPrefSize(140, 60);
        //cell.setText(SimulateAnnealing.printSchedule(row, col));
        //MainTestAlgorithm algorithm = new MainTestAlgorithm();
        //cell.setText(algorithm.run(row, col));
        // cell.setText(besttask.get(row).toString());
        //ειναι λιστα το weekschedule οχι δισδιαστατος πινακασ
        if (selectedWeek != null && besttask != null &&
            row < selectedWeek.length && col < selectedWeek[row].length &&
            selectedWeek[row][col] > 0 && selectedWeek[row][col] <= besttask.size()) {

          int taskIndex = selectedWeek[row][col] - 1;

          if (taskIndex >= 0 && taskIndex < besttask.size()) {
            //cell.setText(besttask.get(taskIndex).toString());
            String taskText = besttask.get(taskIndex).toString();
            // cell.setText(taskText);
            String firstWord = taskText.split(" ")[0];
            for (Subject subje : subject) {
              if (subje.getCourseName().equalsIgnoreCase(firstWord)) {

                //cell.setGraphic(new ImageView(image));
                cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
                break;
              }
            }
          }else {
            cell.setText("");
          }
        } else {
          cell.setText("");
        }

        final int rowFinal = row;
        final int colFinal = col;

//οταν ο χρηστης παταει πανω σε οποιδηποτε κελη τοτε του εμφανιζεται η σελιδα popupdiathesimotita
        cell.setOnMouseClicked(event -> {

          String taskDescription = "κενο";
          LocalDate examDate = null;
          List<Exam> exams = Subject.getExams();

          if (selectedWeek != null && besttask != null &&
              rowFinal < selectedWeek.length && colFinal < selectedWeek[rowFinal].length &&
              selectedWeek[rowFinal][colFinal] > 0 &&  selectedWeek[rowFinal][colFinal] <= besttask.size()) {

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

        GridPane.setConstraints(cell, col, row );
        grid.getChildren().add(cell);
      }
    }

  }
  //οποτε καλειτε ανανεωνονται αναλογα με εκεινα τα δεδομενα το popup που εμφανιζετε
  private void updateUpcomingTasks(VBox upcomingTasksBox) {
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

  public List<int[][]> splitSchedule(int[][] schedule, int daysInWeek) {
    List<int[][]> weekTasks = new ArrayList<>();
    int totalRows = schedule.length; // Πλήθος tasks
    int totalCols = schedule[0].length; // Πλήθος ημερών (στήλες)

    // Υπολογισμός της τρέχουσας ημέρας της εβδομάδας
    int currentDayOfWeek = java.time.LocalDate.now().getDayOfWeek().getValue() % daysInWeek;

    // Υπολογισμός εβδομάδων
    int weeksCount = (int) Math.ceil((double) (totalCols + currentDayOfWeek) / daysInWeek);

    for (int week = 0; week < weeksCount; week++) {
      // Δημιουργούμε τον πίνακα για την εβδομάδα
      int[][] scheduleWeek = new int[totalRows][daysInWeek];
      for (int row = 0; row < totalRows; row++) {
        for (int col = 0; col < daysInWeek; col++) {
          // Υπολογίζουμε τη στήλη στον αρχικό πίνακα
          int indexCol = week * daysInWeek + col - currentDayOfWeek;
          if (indexCol >= 0 && indexCol < totalCols) {
            scheduleWeek[row][col] = schedule[row][indexCol];
          } else {
            scheduleWeek[row][col] = 0; // Κενές μέρες
          }
        }
      }
      weekTasks.add(scheduleWeek);
    }
    return weekTasks;
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
}
