package org.javawavers.studybuddy.ui_ux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Popupdiathesimotita extends Application {

  private List<String> notStartedYet;
  private List<String> completed;

  public void setTaskLists(List<String> notStartedYet, List<String> completed) {
    this.notStartedYet = notStartedYet;
    this.completed = completed;
  }

  public static boolean isFinishedChecked = false;
  private String taskDescription = "κενο";
  private LocalDate examDate;
  private long daysLeft = -1;
  private String exam;

  public void setTaskDescription(String description, LocalDate examDate) {
    this.taskDescription = description;
    this.examDate = examDate;
  }

  @Override
  public void start(Stage primaryStage) {
    if (examDate != null) {
      this.daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), examDate);
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
      this.exam = examDate.format(dateFormatter);
    } else {
      this.daysLeft = -1;
      this.exam = "never";
    }

    Pane root = new Pane();
    root.setStyle("-fx-background-radius: 20px; -fx-background-color: white;");
    root.setPrefSize(241, 280);

    Label lessonLabel = new Label("ΜΑΘΗΜΑ");
    lessonLabel.setFont(Font.font("System Bold", 18));
    lessonLabel.setLayoutX(14);
    lessonLabel.setLayoutY(14);
    root.getChildren().add(lessonLabel);

    Pane duePane = new Pane();
    duePane.setStyle("-fx-background-color: #D3D3D3;");
    duePane.setLayoutX(14);
    duePane.setLayoutY(52);
    duePane.setPrefSize(80, 23);

    Label dueLabel = new Label("Due:");
    dueLabel.setFont(Font.font(14));
    dueLabel.setLayoutX(2);
    dueLabel.setLayoutY(2);

    Label dateLabel = new Label(exam);
    dateLabel.setFont(Font.font(14));
    dateLabel.setLayoutX(34);
    dateLabel.setLayoutY(2);

    duePane.getChildren().addAll(dueLabel, dateLabel);
    root.getChildren().add(duePane);

    Pane remainingPane = new Pane();
    remainingPane.setStyle("-fx-background-color: #D3D3D3;");
    remainingPane.setLayoutX(105);
    remainingPane.setLayoutY(52);
    remainingPane.setPrefSize(98, 23);

    Label remainingLabel = new Label(daysLeft + " days left");
    remainingLabel.setFont(Font.font(14));
    remainingLabel.setLayoutX(10);
    remainingLabel.setLayoutY(1);

    remainingPane.getChildren().add(remainingLabel);
    root.getChildren().add(remainingPane);

    Label descriptionLabel = new Label(taskDescription);
    descriptionLabel.setStyle("-fx-border-color: black;");
    descriptionLabel.setLayoutX(26);
    descriptionLabel.setLayoutY(91);
    descriptionLabel.setPrefSize(189, 124);
    root.getChildren().add(descriptionLabel);

    CheckBox finishedCheckBox = new CheckBox("Finished");
    finishedCheckBox.setFont(Font.font("System Bold", 14));
    finishedCheckBox.setLayoutX(26);
    finishedCheckBox.setLayoutY(218);
    finishedCheckBox.setStyle("-fx-background-color: #15B569; -fx-background-radius: 20px;");
    root.getChildren().add(finishedCheckBox);

    Pane inPane = new Pane();
    inPane.setStyle("-fx-background-radius: 30px; -fx-background-color: #FFC23D;");
    inPane.setLayoutX(129);
    inPane.setLayoutY(218);
    inPane.setPrefSize(98, 30);

    Label inLabel = new Label("In : (minutes)");
    inLabel.setFont(Font.font("System Bold", 14));
    inLabel.setLayoutX(8);
    inLabel.setLayoutY(5);

    inPane.getChildren().add(inLabel);
    root.getChildren().add(inPane);

    // add a button (OK) to close the window
    Button okButton = new Button("OK");
    okButton.setStyle(
        "-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 14px;");
    okButton.setLayoutX(80);
    okButton.setLayoutY(248);
    okButton.setPrefSize(70, 25);
    root.getChildren().add(okButton);

    okButton.setOnAction(
        event -> {
          // When the user selects OK, we check if 'finished' is selected
          isFinishedChecked = finishedCheckBox.isSelected();

          // If it is, we add the specific task to the complete list and remove it
          // from the not started yet list
          if (isFinishedChecked) {
            if (notStartedYet != null && notStartedYet.contains(taskDescription)) {
              notStartedYet.remove(taskDescription);
              completed.add(taskDescription);
            }
          }

          primaryStage.close();
        });

    Scene scene = new Scene(root);
    primaryStage.setTitle("Task Layout");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
