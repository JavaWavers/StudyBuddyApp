package org.javawavers.studybuddy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.javawavers.studybuddy.database.DataBaseManager;
import org.javawavers.studybuddy.uiux.HomePage;
import org.javawavers.studybuddy.uiux.SceneManager;

import java.sql.Connection;

import static org.javawavers.studybuddy.database.DataBaseManager.connect;
import static org.javawavers.studybuddy.database.DataBaseManager.createTables;

/**
 * The StudyBuddyApp class serves as the entry point for the StudyBuddy application.
 * It initializes the primary stage, configures the main scene, and manages the connection
 * to the database. This class extends {@link javafx.application.Application}.
 */
public class StudyBuddyApp extends Application { // exam page

  private static Stage primaryStage;

  /**
   * Launches the application and sets up the initial stage and scene.
   *
   * @param stage The primary {@link javafx.stage.Stage} for this application.
   */
  @Override
  public void start(Stage stage) {
    try {
      primaryStage = stage;

      // Scene manager to handle scene transitions.
      SceneManager sceneManager = new SceneManager(stage);

      // Initialize and set up the home page.
      HomePage homePage = new HomePage();
      Scene homeScene = homePage.home(sceneManager);
      stage.setScene(homeScene);

      // Configure stage properties.
      stage.setTitle("StudyBuddy");
      stage.setX((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
      stage.setX((Screen.getPrimary().getVisualBounds().getHeight()) / 2);
      stage.setWidth(1024);
      stage.setHeight(768);
      stage.setMaximized(true);
      stage.setMinWidth(1024);
      stage.setMinHeight(768);

      // Initialize the database.
      try {
        DataBaseManager.createTables();
      } catch (Exception dbException) {
        System.err.println(
            "Δεν μπορεί να αρχικοποιηθεί η βάση δεδομένων: " + dbException.getMessage());
        dbException.printStackTrace();
      }

      // Display the stage.
      stage.show();
    } catch (Exception e) {
      System.err.println("Πρόβλημα κατά την εκκίνηση της εφαρμογής: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Provides access to the primary stage of the application.
   *
   * @return The primary {@link javafx.stage.Stage}.
   */
  public static Stage getStage() {
    return primaryStage;
  }

  /**
   * The main entry point of the application.
   * Launches the JavaFX application and establishes a connection to the database.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);

    // Establish a connection to the database and initialize tables.
    Connection connection = connect();
    createTables();
  }
}
