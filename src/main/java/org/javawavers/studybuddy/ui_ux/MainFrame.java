package org.javawavers.studybuddy.ui_ux;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

    MenuPage menuPage = new MenuPage(centerPanelManager, rightPanel);
    borderPane.setLeft(menuPage.getLeftBoxMenu());

    borderPane.setRight(rightPanel.rightPanel());

    Scene scene =
        new Scene(
            borderPane,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());

    return scene;
  }
}
