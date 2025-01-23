package org.javawavers.studybuddy.uiux;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * The MainFrame class is responsible for constructing the primary layout of the application.
 * It serves as the main container for the user interface, integrating key components
 * such as the menu, center panel, and right panel into a cohesive structure.
 */
public class MainFrame {

  SceneManager sceneManager;

  /**
   * Creates and configures the main frame of the application.
   * This includes setting up the menu, center panel, and right panel, and returns
   * a fully constructed {@link Scene} containing the layout.
   *
   * @param sceneManager The {@link SceneManager} used to handle scene transitions. It must not be null.
   * @return The main application {@link Scene}, sized to the primary screen's dimensions.
   */
  public Scene mainFrame(SceneManager sceneManager) {
    this.sceneManager = sceneManager;

    // The main container for the application layout.
    BorderPane borderPane = new BorderPane();

    // Set up and display the center panel with default content (e.g., "Calendar").
    CenterPanelManager centerPanelManager = new CenterPanelManager();
    centerPanelManager.changeCenterPanel("Calendar");
    borderPane.setCenter(centerPanelManager.getCenterPane());

    // Set up and initialize the right panel with default content.
    RightPanel rightPanel = new RightPanel();
    rightPanel.updateRightPaneContent("Calendar");

    // Create the menu page and integrate it into the left side of the layout.
    MenuPage menuPage = new MenuPage(centerPanelManager, rightPanel);
    borderPane.setLeft(menuPage.getLeftBoxMenu());

    // Attach the right panel to the right side of the layout.
    borderPane.setRight(rightPanel.rightPanel());

    // Create the scene with dimensions matching the primary screen's visual bounds.
    Scene scene =
        new Scene(
            borderPane,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());

    return scene;
  }
}
