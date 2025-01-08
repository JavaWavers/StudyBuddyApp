package org.javawavers.studybuddy;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RightPanel {

  private StackPane rightPanel;

  public RightPanel() {
    rightPanel = new StackPane();
    rightPanel.setPrefWidth(212);
    rightPanel.setMinWidth(212); //or 88.33
    rightPanel.setMaxWidth(212);
    rightPanel.setMaxWidth(Double.MAX_VALUE);
    rightPanel.setMaxHeight(Double.MAX_VALUE);
  }

  public void CoursesList() {
    VBox examList = new VBox(5);
    VBox assignmentLst = new VBox(5);

    examList.setStyle("-fx-background-color: #15B569;");
    assignmentLst.setStyle("-fx-background-color: #FFC23D;");

    rightPanel.getChildren().addAll(examList, assignmentLst);
  }

  public StackPane getRightPanel(){
    return rightPanel;
  }
}
