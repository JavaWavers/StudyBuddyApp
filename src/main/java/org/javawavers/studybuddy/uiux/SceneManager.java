package org.javawavers.studybuddy.uiux;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SceneManager class is responsible for managing and switching scenes
 * in a JavaFX application. It provides a streamlined way to update the
 * displayed content in the application's primary stage.
 */
public class SceneManager {

  private Stage stage;

  /**
   * Constructs a SceneManager with a specified {@link Stage}.
   *
   * @param stage The primary stage for the application. It cannot be null.
   * @throws IllegalArgumentException If the provided stage is null.
   */
  public SceneManager(Stage stage) {
    if (stage == null) {
      throw new IllegalArgumentException("To stage μάλλον είναι null");
    }
    this.stage = stage;
  }

  /**
   * Switches the current scene of the application's stage.
   *
   * @param scene The new {@link Scene} to display. It cannot be null.
   * @throws NullPointerException If the provided scene is null.
   */
  public void switchScene(Scene scene) {
    try {
      if (scene == null) {
        throw new NullPointerException("Δεν μπορεί να αλλάξει η σκηνή");
      }
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      System.err.println("Σφάλμα κατά την αλλαγή της σελίδας: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
