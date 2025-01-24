package org.javawavers.studybuddy.uiux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.javawavers.studybuddy.courses.Assignment;
import static org.javawavers.studybuddy.courses.StaticUser.staticUser;
import org.javawavers.studybuddy.database.DataInserter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * This class represents the Assignment Page where users can input information about assignments.
 * It handles the creation and validation of assignment forms.
 */
public class AssignmentPage {
  public static ArrayList<Assignment> assignments = new ArrayList<>();
  private TextField nameField;
  private TextField estimateHours;
  private TextField difficultyField;
  private DatePicker datePicker;
  private static String title = "";

  private static LocalDate localDeadline;

  Button okBtn = new Button("OK");


  /**
   * Creates the assignment panel as a VBox with form fields and a submit button.
   *
   * @return the assignment panel as a Node
   */
  public Node assignmentPanel() {
    VBox assignmentPanel = new VBox(20);
    assignmentPanel.setPadding(new Insets(20));

    assignmentPanel.getChildren().addAll(infoSection(), evalSection());

    // "ok" Button
    okBtn = new Button("OK");
    okBtn.setStyle(Styles.COURSES_BTN_STYLE);
    okBtn.setOnMouseEntered(e -> okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED));
    okBtn.setOnMouseClicked(
        e -> handleOkBtn());
    okBtn.setAlignment(Pos.CENTER_LEFT);

    HBox okBtnHbox = new HBox(10);
    okBtnHbox.setAlignment(Pos.CENTER_LEFT);
    okBtnHbox.getChildren().add(okBtn);

    // adds all the exam page parts to the panel
    assignmentPanel.getChildren().add(okBtnHbox);

    return assignmentPanel; // returns the page
  }

  /**
   * Handles the logic when the "OK" button is clicked.
   * This method validates the input fields and adds the assignment if valid.
   */
  private void handleOkBtn() {

    String estimate;
    String deadline;
    title = nameField.getText();
    estimate = estimateHours.getText();
    String value = difficultyField.getText();
    deadline = datePicker.getValue() != null ? datePicker.getValue().toString() : null;

    int difficulty = 0;
    List<String> errors = new ArrayList<>();
    if (title.isEmpty()) {
      errors.add("• Εισήγαγε όνομα εργασίας");
    } else if (!title.matches("[a-zA-Zα-ωΑ-ΩάέήίΰϊϋόύώΆΈΉΊΪΫΌΎΏ0-9 ]+")) {
      errors.add("• Η Ονομασία της εργασίας μπορεί να περιέχει μόνο γράμματα, αριθμούς και κενά");
    }

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
          .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
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
    int estimateHour;
    localDeadline = LocalDate.parse(deadline);
    estimateHour = Integer.parseInt(estimate);

    Assignment assignment1 = new Assignment(title, localDeadline, estimateHour, difficulty);
    // add the assignment to the static user
    staticUser.addAssignment(assignment1);

    DataInserter.insertAssignment(
        title, localDeadline, estimateHour, difficulty, null, staticUser.getUserId());


    System.out.println(title);
    System.out.println(estimate);
    System.out.println(difficulty);
    System.out.println(deadline);

    clearFields();
    okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED);
  }

  /**
   * Clears all input fields on the form.
   */
  private void clearFields() {
    nameField.clear();
    estimateHours.clear();
    difficultyField.clear();
    datePicker.setValue(null);
  }

  /**
   * Creates the section of the form where the user inputs information about the assignment.
   *
   * @return a VBox containing the information section
   */
  private VBox infoSection() {
    Label infoTitle = new Label("Πληροφορίες:");
    infoTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane info = new GridPane();
    info.setVgap(10);
    info.setHgap(10);

    Label nameLabel = new Label("Tίτλος Εργασιας:");
    nameLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    nameField = new TextField();
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
    VBox infoVbox = new VBox(10);
    infoVbox.getChildren().addAll(infoTitle, info);

    infoVbox.setMaxWidth(500);
    infoVbox.setPrefWidth(500);
    return infoVbox;
  }

  /**
   * Creates the section of the form where the user evaluates the difficulty of the assignment.
   *
   * @return a VBox containing the evaluation section
   */
  private VBox evalSection() {
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
    VBox evalVbox = new VBox(10);
    evalVbox.getChildren().addAll(evalTitle, eval);

    evalVbox.setMaxWidth(500);
    evalVbox.setPrefWidth(500);

    return evalVbox;
  }

  /**
   * Creates the starting page for the assignment view.
   *
   * @param sceneManager the scene manager to handle scene transitions
   * @return the assignment starting page scene
   */
  public Scene assignmentStartingPage(SceneManager sceneManager) {

    HBox nameLbl = new HBox(20);
    Label name = new Label("Εισαγωγή Εργασιών");
    name.setStyle(Styles.StyleType.TITLE.getStyle());
    nameLbl.getChildren().add(name);
    nameLbl.setPadding(new Insets(20));
    VBox assignViewWithBtn = new VBox();
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
          AvailabilityPage availabilityPage = new AvailabilityPage(sceneManager);
          sceneManager.switchScene(availabilityPage.availStartingPage(sceneManager));
        });
    btns.getChildren().addAll(prevBtn, nextBtn);
    assignViewWithBtn.getChildren().add(btns);

    return new Scene(
      assignViewWithBtn,
      Screen.getPrimary().getVisualBounds().getWidth(),
      Screen.getPrimary().getVisualBounds().getHeight());
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

}
