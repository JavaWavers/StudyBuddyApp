package org.javawavers.studybuddy.ui_ux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// import static Calendar.completed;
// import static Calendar.completed;
// import static Calendar.notStartedYet;
// import static Calendar.notStartedYet;
// import static org.javawavers.studybuddy.ui_ux.Calendar.completed;
// import static org.javawavers.studybuddy.ui_ux.Calendar.notStartedYet;

public class RightPanel {

  private StackPane rightPanel;
  VBox upcomingTasksBox = new VBox(10);
  VBox completedTasksBox = new VBox(10);

  public RightPanel() {

    rightPanel = new StackPane();
    rightPanel.setPrefWidth(212);
    rightPanel.setMinWidth(212); // or 88.33
    rightPanel.setMaxWidth(212);
    rightPanel.setMaxWidth(Double.MAX_VALUE);
    rightPanel.setMaxHeight(Double.MAX_VALUE);
  }

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
    // οριζουμε τον τιτλο αναλογα με το κουμπι  που εχει πατηθει
    Label titleLabel = new Label(title);
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    // δημιουργουμε taskbox για τα task
    VBox tasksBox = new VBox(5);
    tasksBox.setAlignment(Pos.TOP_LEFT);
    tasksBox.setStyle("-fx-max-height: 300px;");

    // δημιουργουμε scrollpane για να μπορει ο χρηστης να κανει scroll και να δει ολα τα task τα
    // οποια εχει να κανει εκεινη την εβδομαδα
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(tasksBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(300);

    Map<CheckBox, String> taskCheckBoxMap = new HashMap<>();
  }
}

    // δημιουργουμε τα checkbox
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
