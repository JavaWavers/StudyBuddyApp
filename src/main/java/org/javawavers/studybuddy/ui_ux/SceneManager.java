package org.javawavers.studybuddy.ui_ux;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

  private Stage stage;

  public SceneManager(Stage stage) {
    if (stage == null) {
      throw new IllegalArgumentException("To stage μάλλον είναι null");
    }
    this.stage = stage;
  }

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
