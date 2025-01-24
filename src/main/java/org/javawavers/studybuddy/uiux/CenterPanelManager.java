package org.javawavers.studybuddy.uiux;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.StackPane;


/**
 * CenterPanelManager class changes the center panel of the main Page depending on the Menu Button
 * that is pressed.
 */
public class CenterPanelManager {
  private final StackPane centerPane;
  private static final Logger LOGGER = Logger.getLogger(CenterPanelManager.class.getName());

  /**
   * Constructor for CenterPanelManager.
   * Initializes the center panel with maximum width and height.
   */
  // class constructor
  public CenterPanelManager() {
    centerPane = new StackPane();
    centerPane.setMaxWidth(Double.MAX_VALUE);
    centerPane.setMaxHeight(Double.MAX_VALUE);
  }

  /**
   * This is the main method of the class It creates objects from all button Pages which are on the
   * Menu Using a switch it changes the page that should be presented on the center panel.
   */
  // Method that changes center Panel
  public void changeCenterPanel(String panelName) {
    try {
      centerPane.getChildren().clear();

      ExamPage examPage = new ExamPage();
      DashboardPage dashboard = new DashboardPage();
      Calendar calendar = new Calendar();
      AssignmentPage assignmentPage = new AssignmentPage();

      // Depending on which Menu Button is pressed it changes the center Panel
      switch (panelName) {
        case "Courses":
          break;
        case "Exam":
          if (examPage.createExamPanel() != null) {
            centerPane.getChildren().setAll(examPage.createExamPanel());
          } else {
            throw new NullPointerException("Η σελίδα Exam είναι null.");
          }
          break;
        case "Assignments":
          if (assignmentPage.assignmentPanel() != null) {
            centerPane.getChildren().setAll(assignmentPage.assignmentPanel());
          } else {
            throw new NullPointerException("Η σελίδα Assignment είναι null.");
          }
          break;
        case "Calendar":
          if (calendar.calendar() != null) {
            centerPane.getChildren().setAll(calendar.calendar());
          } else {
            throw new NullPointerException("Η σελίδα Calendar είναι null.");
          }
          break;
        case "Dashboard":
          if (dashboard.createDashboard() != null) {
            centerPane.getChildren().setAll(dashboard.createDashboard());
          } else {
            throw new NullPointerException("Η σελίδα Dashboard είναι null.");
          }
          break;
        default:
          throw new IllegalArgumentException("Δεν είναι σωστό το όνομα του panel " + panelName);
      }
    } catch (NullPointerException e) {
      LOGGER.log(Level.SEVERE, "Σφάλμα: " + e.getMessage(), e);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.WARNING, "Λάνθασμένη κλήση panel: " + e.getMessage());
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE,
          "Απροσδόκητο σφάλμα κατά την αλλαγή του panel: " + e.getMessage(), e);
    }
  }

  /**
   * * Returns the center pane that is displayed on the UI.
   *
   * @return The panel name that is selected it appears on the center panel
   */
  public StackPane getCenterPane() {
    return centerPane;
  }
}
