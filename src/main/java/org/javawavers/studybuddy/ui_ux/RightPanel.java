package org.javawavers.studybuddy.ui_ux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Subject;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

// import static Calendar.completed;
// import static Calendar.completed;
// import static Calendar.notStartedYet;
// import static Calendar.notStartedYet;
// import static org.javawavers.studybuddy.ui_ux.Calendar.completed;
// import static org.javawavers.studybuddy.ui_ux.Calendar.notStartedYet;

public class RightPanel {

  private StackPane rightPanel;
  private VBox rightPane;
  private VBox upcomingTasksBox = new VBox(10);
  private VBox completedTasksBox = new VBox(10);

  public RightPanel() {
    this.rightPane = rightPaneStyle();
  }

  public ScrollPane rightPanel() {
    CenterPanelManager centerPanelManager = new CenterPanelManager();

    if (rightPane == null) {
      rightPane = rightPaneStyle();
    }
    rightPanel = new StackPane();
    rightPanel.setPrefWidth(280);
    rightPanel.setMinWidth(280); // or 88.33
    rightPanel.setMaxWidth(280);
    rightPanel.setMaxWidth(Double.MAX_VALUE);
    rightPanel.setMaxHeight(Double.MAX_VALUE);

    // Right Pane's ScrollPane
    ScrollPane scrollPane = new ScrollPane(rightPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefWidth(280);
    scrollPane.setMaxWidth(280);
    scrollPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");

    return scrollPane;
  }

  private VBox rightPaneStyle() {
    rightPane = new VBox(10);
    rightPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
    return rightPane;
  }

  public void updateRightPaneContent(String activePanel) {
    if (rightPane == null) {
      rightPane = rightPaneStyle();
    }
    rightPane.getChildren().clear();

    switch (activePanel) {
      case "Exam":
        rightPane.getChildren().add(coursesPane());
        break;
      case "Assignments":
        rightPane.getChildren().add(coursesPane());
        break;
      case "Calendar":
        rightPane.getChildren().add(tasksPane());
        break;
      case "Dashboard":
        rightPane.getChildren().add(tasksPane());
        break;
    }
  }
  private String[] getSubjectsArray() {
    List<Subject> subjects = staticUser.getSubjects();
    subjects.forEach(subject -> System.out.println(subject.getCourseName()));
    return subjects.stream()
      .map(Subject::getCourseName)
      .toArray(String[]::new);
  }
  int[] avPerDay = staticUser.getAvPerDay();
  String[] avPerDayArray = Arrays.stream(avPerDay)
    .mapToObj(String::valueOf)
    .toArray(String[]::new);

  List<LocalDate> nonAvailDays = staticUser.getNonAvailDays();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  String[] nonAvPerDay = nonAvailDays.stream()
    .map(date -> date.format(formatter))
    .toArray(String[]::new);

  private String[] getAssignimentsArray() {
    List<Assignment> ass = staticUser.getAssignments();
    ass.forEach(Assigniment -> System.out.println(ass.get(0).getTitle()));
    return ass.stream()
      .map(Assignment::getTitle)
      .toArray(String[]::new);
  }


  private VBox tasksPane() {
    VBox tasksPane = new VBox(10);

    tasksPane
        .getChildren()
        .addAll(
            TasksVBox(
                "Σημερινά Tasks",
                new String[] {"Task 1", "Task 2", "Task 3", "4", "5", "6", "7", "8", "9,", "10"},
                Styles.TaskType.TODAY),
            TasksVBox(
                "Εβδομαδιαία Tasks",
                new String[] {"Task A", "Task B", "Task C", "Task D"},
                Styles.TaskType.WEEK),
            TasksVBox(
                "Εκκρεμότητες",
                new String[] {"Overdue Task 1", "Overdue Task 2"},
                Styles.TaskType.OVERDUE),
            TasksVBox(
                "Ολοκληρωμένα Tasks",
                new String[] {"Completed Task X", "Completed Task Y"},
                Styles.TaskType.COMPLETED));
    return tasksPane;
  }

  private VBox coursesPane() {
    VBox coursePane = new VBox(10);

    coursePane
        .getChildren()
        .addAll(
            TasksVBox(
                "Μαθήματα",
                getSubjectsArray(),
                Styles.TaskType
                    .TODAY), // εδώ που είναι το new String βάζουμε μια μέθοδο που επιστρέφει τα
            // μαθήματα)
            TasksVBox(
                "Εργασίες",
                getAssignimentsArray(),
                Styles.TaskType.WEEK),
            TasksVBox(
                "Διαθεσιμότητα Ημερών",
                avPerDayArray,
                Styles.TaskType.OVERDUE),
            TasksVBox(
                "Μη διαθεσιμότητα",
                 nonAvPerDay,
                Styles.TaskType.COMPLETED));
    return coursePane;
  }

  // Method that creates the taskPane with a label and a listView
  private VBox TasksVBox(String title, String[] tasks, Styles.TaskType taskType) {
    Label titleLabel = new Label(title);
    titleLabel.setStyle(Styles.LABEL_STYLE(taskType.getColor()));

    ListView<String> listView = new ListView<>();
    listView.getItems().addAll(tasks);

    VBox taskPane = new VBox(5, titleLabel, listView);
    taskPane.setStyle("-fx-padding: 10; -fx-background-color: rgba(255, 255, 255, 0.2);");

    taskPane.setFillWidth(true);
    taskPane.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight() / 4);
    return taskPane;
  }

  // Pelagia
  public void CoursesList() {
    VBox examList = new VBox(5);
    VBox assignmentLst = new VBox(5);

    examList.setStyle("-fx-background-color: #15B569;");
    assignmentLst.setStyle("-fx-background-color: #FFC23D;");

    rightPanel.getChildren().addAll(examList, assignmentLst, upcomingTasksBox, completedTasksBox);
  }

  public StackPane getRightPanel() {
    return rightPanel;
  }

  private void showTasksPopup(String title, List<String> taskList) {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle(title);

    VBox popupContent = new VBox(10);
    popupContent.setPadding(new Insets(10));
    popupContent.setAlignment(Pos.TOP_CENTER);
    // define the title according to the button that has been pressed
    Label titleLabel = new Label(title);
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    // create taskbox for the tasks
    VBox tasksBox = new VBox(5);
    tasksBox.setAlignment(Pos.TOP_LEFT);
    tasksBox.setStyle("-fx-max-height: 300px;");

    // We create a scrollpane so the user can scroll down and see all the tasks
    // that he has done that week
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(tasksBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(300);

    Map<CheckBox, String> taskCheckBoxMap = new HashMap<>();
  }
}

    // Create the checkbox
  /*  if (taskList != null && !taskList.isEmpty()) {
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
        //update the taskboxes
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


        System.out.println("Completed tasks: " + completed);
      });

      checkBoxBox.getChildren().add(taskCheckBox);

      return checkBoxBox;
    }

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
      for (String taskDescription : Calendar.completed) {
        HBox checkBoxBox = createCheckBox(taskDescription);
        //System.out.println("completed" + taskDescription);
        completedTasksBox.getChildren().add(checkBoxBox);
      }
    }
  }
  */
