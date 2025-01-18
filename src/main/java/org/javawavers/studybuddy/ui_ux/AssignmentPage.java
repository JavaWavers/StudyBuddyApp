package org.javawavers.studybuddy.ui_ux;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.javawavers.studybuddy.courses.Assignment;
import org.javawavers.studybuddy.courses.StaticUser;
import org.javawavers.studybuddy.database.DataInserter;

public class AssignmentPage {
  public static ArrayList<Assignment> assignments = new ArrayList<>();
  private TextField namField, assignmentField, estimateHours, difficultyField;
  ComboBox<String> coursesList;
  private DatePicker datePicker;
  private static String title = "";
  private static String estimate = "";
  private static int difficulty = 0;
  private static String deadline = "";
  private static String courseType = "";
  private static LocalDate localDeadline;
  private static int estimateHour;
  private Button okBtn;

  // Assignment Page as Node
  public Node assignmentPanel() {
    VBox assignmentPanel = new VBox(20);
    assignmentPanel.setPadding(new Insets(20));

    assignmentPanel.getChildren().addAll(infoSection(), evalSection());

    // "ok" Button
    Button okBtn = new Button("OK");
    okBtn.setStyle(Styles.COURSES_BTN_STYLE);
    okBtn.setOnMouseEntered(e -> okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED));
    okBtn.setOnMouseClicked(
        e -> {
          handleOkBtn();
        });
    okBtn.setAlignment(Pos.CENTER_LEFT);

    HBox okBtnHBox = new HBox(10);
    okBtnHBox.setAlignment(Pos.CENTER_LEFT);
    okBtnHBox.getChildren().add(okBtn);

    // adds all the exam page parts to the panel
    assignmentPanel.getChildren().add(okBtnHBox);

    return assignmentPanel; // returns the page
  }

  private void handleOkBtn() {
    estimate = estimateHours.getText();
    String value = difficultyField.getText();
    deadline = datePicker.getValue() != null ? datePicker.getValue().toString() : null;

    List<String> errors = new ArrayList<>();
    if (!String.valueOf(value).matches("\\d+")) {
      errors.add("• Η δυσκολία μπόρει να περιέχει μόνο αριθμούς");
    } else {
      difficulty = Integer.parseInt(value);
    }

    if (estimate.isEmpty()) {
      errors.add("• Όρισε εκτιμώμενη ώρα για το μάθημα");
    } else if (!estimate.matches("\\d+")) {
      errors.add("• H εκτιμώμενη ώρα πρέπει να είναι αριθμός");
    }

    if (difficulty < 1 || difficulty > 10) {
      errors.add("• H δυσκολία πρέπει να είναι αριθμός μεταξύ 1 και 10.");
    }

    if (deadline == null || LocalDate.parse(deadline).isBefore(LocalDate.now())) {
      errors.add("• Πρέπει να επιλέξεις ημερομηνία εξέτασης μετά τη σημερινή ημερομηνία.");
    }

    if (!errors.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
      alert.setHeaderText(null);
      String errorMessage = String.join("\n", errors);
      alert.setContentText(errorMessage);
      alert
          .getDialogPane()
          .getStylesheets()
          .add(getClass().getResource("/alert.css").toExternalForm());
      alert.showAndWait();
      return;
    } else {
      Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
      successAlert.setTitle("Εισαγωγή Εργασίας Επιτυχής");
      successAlert.setHeaderText(null);
      successAlert.setContentText("Η εργασία προστέθηκε!!");
      DialogPane dialogPane = successAlert.getDialogPane();
      dialogPane.getStyleClass().add("success-alert");
      dialogPane
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/success.css")).toExternalForm());
      successAlert.showAndWait();
    }
    localDeadline = LocalDate.parse(deadline);
    estimateHour = Integer.parseInt(estimate);
    // SimulateAnnealing simulateAnnealing = new SimulateAnnealing();
    // Subject sub = new Subject();

    // simulateAnnealing.subAss2(title, localDeadline, estimateHour);
    Assignment assignment1 = new Assignment(title, localDeadline, estimateHour, difficulty);
    // add the assignment to the static user
    staticUser.addAssignment(assignment1);

    DataInserter.insertAssignment(
        title, localDeadline, estimateHour, difficulty, null, StaticUser.staticUser.getUserId());
    StaticUser.staticUser.addAssignment(assignment1);

    // ExamPage exampage = new ExamPage();
    // Subject course = exampage.coursename;
    // course.addAssignment(assignment1);

    namField.clear();
    assignmentField.clear();
    estimateHours.clear();
    difficultyField.clear();
    datePicker.setValue(null);
    coursesList.setValue("");

    // System.out.println(courseName);
    System.out.println(title);
    System.out.println(estimate);
    System.out.println(difficulty);
    System.out.println(deadline);
    System.out.println(courseType);

    okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED);
  }

  // Section for course information
  private VBox infoSection() {
    VBox infoVBox = new VBox(10);
    Label infoTitle = new Label("Πληροφορίες:");
    infoTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane info = new GridPane();
    info.setVgap(10);
    info.setHgap(10);

    Label nameLabel = new Label("Tίτλος Εργασιας:");
    nameLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    TextField nameField = new TextField();
    info.add(nameLabel, 0, 1);
    info.add(nameField, 1, 1);

    Label estimateHoursLabel = new Label("Εκτιμώμενες Απαιτούμενες Ώρες:");
    estimateHoursLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    estimateHours = new TextField();
    info.add(estimateHoursLabel, 0, 2);
    info.add(estimateHours, 1, 2);

    Label deadlineLabel = new Label("Ημερομηνία Παράδοσης:");
    deadlineLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    datePicker = new DatePicker();
    info.add(deadlineLabel, 0, 3);
    info.add(datePicker, 1, 3);

    info.setStyle(Styles.BLACK_BORDER);
    infoVBox.getChildren().addAll(infoTitle, info);

    infoVBox.setMaxWidth(400);
    infoVBox.setPrefWidth(400);
    return infoVBox;
  }

  // Section for course evaluation
  private VBox evalSection() {
    VBox evalVBox = new VBox(10);
    Label evalTitle = new Label("Αξιολόγηση:");
    evalTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane eval = new GridPane();
    eval.setVgap(10);
    eval.setHgap(10);

    Label difficultyLabel = new Label("Δυσκολία:");
    difficultyLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    difficultyField = new TextField();
    eval.add(difficultyLabel, 0, 1);
    eval.add(difficultyField, 1, 1);

    eval.setStyle(Styles.BLACK_BORDER);
    evalVBox.getChildren().addAll(evalTitle, eval);

    evalVBox.setMaxWidth(400);
    evalVBox.setPrefWidth(400);

    return evalVBox;
  }

  public Scene assignmentStartingPage(SceneManager sceneManager) {
    VBox assignViewWithBtn = new VBox();

    HBox nameLbl = new HBox(20);
    Label name = new Label("Εισαγωγή Εργασιών");
    name.setStyle(Styles.StyleType.TITLE.getStyle());
    nameLbl.getChildren().add(name);
    nameLbl.setPadding(new Insets(20));
    assignViewWithBtn.getChildren().add(nameLbl);

    VBox examView = (VBox) assignmentPanel();
    assignViewWithBtn.getChildren().add(examView);

    HBox btns = new HBox(15);
    btns.setPadding(new Insets(20));
    Button prevBtn = new Button("Προηγούμενο");
    prevBtn.setStyle(Styles.COURSES_BTN_STYLE);
    prevBtn.setOnAction(
        e -> {
          ExamPage examPage = new ExamPage();
          sceneManager.switchScene(examPage.examStartingPage(sceneManager));
        });

    Button nextBtn = new Button("Επόμενο");
    nextBtn.setStyle(Styles.COURSES_BTN_STYLE);
    nextBtn.setOnAction(
        e -> {
          // System.out.println("Το κουμπί πατήθηκε!");
          AvailabilityPage availabilityPage = new AvailabilityPage(sceneManager);
          sceneManager.switchScene(availabilityPage.availStartingPage(sceneManager));
        });
    btns.getChildren().addAll(prevBtn, nextBtn);
    assignViewWithBtn.getChildren().add(btns);

    Scene scene =
        new Scene(
            assignViewWithBtn,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());
    return scene;
  }

  public ArrayList<Assignment> getAssignments() {
    return assignments;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getDeadline() {
    return localDeadline;
  }

  public int getEstimateHours() {
    return estimateHour;
  }
}
