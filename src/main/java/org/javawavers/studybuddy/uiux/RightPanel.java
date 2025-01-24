package org.javawavers.studybuddy.uiux;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.Subject;



/**
 * RightPanel class responsible for managing the right-side panel of the UI.
 * It displays upcoming tasks, completed tasks, courses, assignments, and availability.
 */
public class RightPanel {

  private StackPane rightPanel;
  private VBox rightPane;


  /**
   * Constructor to initialize the RightPanel.
   */
  public RightPanel() {
    this.rightPane = rightPaneStyle();
  }

  /**
   * Creates and returns the right panel's scrollable pane.
   *
   * @return The scrollable right panel.
   */
  public ScrollPane rightPanel() {
    CenterPanelManager centerPanelManager = new CenterPanelManager();

    if (rightPane == null) {
      rightPane = rightPaneStyle();
    }
    rightPanel = new StackPane();
    rightPanel.setPrefWidth(280);
    rightPanel.setMinWidth(280); // or 88.33
    rightPanel.setMaxWidth(280);
    rightPanel.setMaxWidth(Double.MAX_VALUE);
    rightPanel.setMaxHeight(Double.MAX_VALUE);

    // Right Pane's ScrollPane
    ScrollPane scrollPane = new ScrollPane(rightPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefWidth(280);
    scrollPane.setMaxWidth(280);
    scrollPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");

    return scrollPane;
  }

  private VBox rightPaneStyle() {
    rightPane = new VBox(10);
    rightPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
    return rightPane;
  }

  /**
   * Updates the content of the right panel based on the selected tab.
   *
   * @param activePanel The currently active panel (Exam, Assignments, Calendar, Dashboard).
   */
  public void updateRightPaneContent(String activePanel) {
    if (rightPane == null) {
      rightPane = rightPaneStyle();
    }
    rightPane.getChildren().clear();

    switch (activePanel) {
      case "Exam", "Assignments":
        rightPane.getChildren().add(coursesPane());
        break;
      case "Calendar", "Dashboard":
        rightPane.getChildren().add(tasksPane());
        break;
    }
  }

  private String[] getSubjectsArray() {
    List<Subject> subjects = staticUser.getSubjects();
    if (subjects == null || subjects.isEmpty()) {
      return new String[]{"Δεν έχεις συμπληρώσει μαθήματα"};
    }
    subjects.forEach(subject -> System.out.println(subject.getCourseName()));
    return subjects.stream().map(Subject::getCourseName).toArray(String[]::new);
  }

  int[] avPerDay = staticUser.getAvPerDay();
  String[] avPerDayArray =
    (avPerDay == null || avPerDay.length == 0)
      ? new String[]{"Δεν έχεις συμπληρώσει διαθεσιμότητα"}
      : Arrays.stream(avPerDay).mapToObj(String::valueOf).toArray(String[]::new);

  List<LocalDate> nonAvailDays = staticUser.getNonAvailDays();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  String[] nonAvPerDay =
    (nonAvailDays == null || nonAvailDays.isEmpty())
      ? new String[]{"Δεν έχεις συμπληρώσει μη διαθεσιμότητα"}
      : nonAvailDays.stream().map(date -> date.format(formatter)).toArray(String[]::new);

  private String[] getAssignimentsArray() {
    List<Assignment> ass = staticUser.getAssignments();
    if (ass == null || ass.isEmpty()) {
      return new String[]{"Δεν έχεις συμπληρώσει εργασίες"};
    }
    ass.forEach(assignment -> System.out.println(assignment.getTitle()));
    return ass.stream().map(Assignment::getTitle).toArray(String[]::new);
  }

  private VBox tasksPane() {
    VBox tasksPane = new VBox(10);

    tasksPane
         .getChildren()
         .addAll(
        TasksVBox(
          "Σημερινά Tasks",
          new String[]{"Task 1", "Task 2", "Task 3", "4", "5", "6", "7", "8", "9,", "10"},
          Styles.TaskType.TODAY),
        TasksVBox(
          "Εβδομαδιαία Tasks",
          new String[]{"Task A", "Task B", "Task C", "Task D"},
          Styles.TaskType.WEEK),
        TasksVBox(
          "Εκκρεμότητες",
          new String[]{"Overdue Task 1", "Overdue Task 2"},
          Styles.TaskType.OVERDUE),
        TasksVBox(
          "Ολοκληρωμένα Tasks",
          new String[]{"Completed Task X", "Completed Task Y"},
          Styles.TaskType.COMPLETED));
    return tasksPane;
  }

  private VBox coursesPane() {
    VBox coursePane = new VBox(10);

    coursePane
          .getChildren()
        .addAll(
        TasksVBox(
          "Μαθήματα",
          getSubjectsArray(),
          Styles.TaskType
            .TODAY), // εδώ που είναι το new String βάζουμε μια μέθοδο που επιστρέφει τα
        // μαθήματα)
        TasksVBox("Εργασίες", getAssignimentsArray(), Styles.TaskType.WEEK),
        TasksVBox("Διαθεσιμότητα Ημερών", avPerDayArray, Styles.TaskType.OVERDUE),
        TasksVBox("Μη διαθεσιμότητα", nonAvPerDay, Styles.TaskType.COMPLETED));
    return coursePane;
  }

  // Method that creates the taskPane with a label and a listView
  private VBox TasksVBox(String title, String[] tasks, Styles.TaskType taskType) {
    Label titleLabel = new Label(title);
    titleLabel.setStyle(Styles.LABEL_STYLE(taskType.getColor()));

    ListView<String> listView = new ListView<>();
    listView.getItems().addAll(tasks);

    VBox taskPane = new VBox(5, titleLabel, listView);
    taskPane.setStyle("-fx-padding: 10; -fx-background-color: rgba(255, 255, 255, 0.2);");

    taskPane.setFillWidth(true);
    taskPane.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight() / 4);
    return taskPane;
  }
}