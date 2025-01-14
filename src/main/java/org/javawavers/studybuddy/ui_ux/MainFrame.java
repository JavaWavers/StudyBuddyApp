package org.javawavers.studybuddy.ui_ux;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class MainFrame {

  SceneManager sceneManager;

  public Scene mainFrame(SceneManager sceneManager) {
    this.sceneManager = sceneManager;

    BorderPane borderPane = new BorderPane();

    CenterPanelManager centerPanelManager = new CenterPanelManager();
    centerPanelManager.changeCenterPanel("Calendar");
    borderPane.setCenter(centerPanelManager.getCenterPane());

    RightPanel rightPanel = new RightPanel();
    rightPanel.updateRightPaneContent("Calendar");

    MenuPage menuPage = new MenuPage(centerPanelManager,rightPanel);
    borderPane.setLeft(menuPage.getLeftBoxMenu());

    HBox topPane = new HBox();
    topPane.setPadding(new Insets(0, 0, 50, 212));
    topPane.setStyle("-fx-background-color: #60f7b3; ");
    borderPane.setTop(topPane);

    //rightPanel.rightPanel();
    borderPane.setRight(rightPanel.rightPanel());

    Scene scene =
        new Scene(
            borderPane,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());

    return scene;
  }
}
