package org.javawavers.studybuddy.uiux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.javawavers.studybuddy.courses.Exam;
import static org.javawavers.studybuddy.courses.StaticUser.staticUser;
import org.javawavers.studybuddy.courses.Subject;
import org.javawavers.studybuddy.database.ActiveUser;
import org.javawavers.studybuddy.database.DataInserter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Represents a page in the user interface where the user can input and save details about an exam.
 * The page includes fields for
 * the course name, type, pages, exam date, difficulty, and time per 20 slides.
 */
public class ExamPage {
  private TextField nameField;
  private TextField pageField;
  private TextField difficulty;
  private TextField timePer20Slides;
  private static int pages = 0;
  private static int diffi = 0;
  private static double time = 0.0;
  private DatePicker datePicker;
  private ComboBox<Subject.SubjectType> typeCourseList;
  private static List<Subject> subjects = new ArrayList<>();
  private Button okBtn;


  /**
   * Returns the list of subjects.
   *
   * @return a list of subjects
   */
  public List<Subject> getSubjects() {
    return subjects;
  }

  /**
   * Sets up and returns the scene for the exam starting page.
   *
   * @param sceneManager the scene manager used to switch scenes
   * @return the Scene object representing the exam page
   */
  public Scene examStartingPage(SceneManager sceneManager) {

    HBox nameLbl = new HBox(20);
    Label name = new Label("Εισαγωγή Μαθημάτων");
    name.setStyle(Styles.StyleType.TITLE.getStyle());
    nameLbl.getChildren().add(name);
    nameLbl.setPadding(new Insets(20));
    VBox examViewWithBtn = new VBox();
    examViewWithBtn.getChildren().add(nameLbl);

    VBox examView = (VBox) createExamPanel();
    examViewWithBtn.getChildren().add(examView);

    HBox btnBox = new HBox(20);
    btnBox.setPadding(new Insets(20));
    Button nextBtn = new Button("Επόμενο");
    nextBtn.setStyle(Styles.COURSES_BTN_STYLE);
    nextBtn.setOnAction(
        e -> {
          AssignmentPage assignPage = new AssignmentPage();
          sceneManager.switchScene(assignPage.assignmentStartingPage(sceneManager));
        });
    btnBox.getChildren().add(nextBtn);
    examViewWithBtn.getChildren().add(btnBox);

    return new Scene(
      examViewWithBtn,
      Screen.getPrimary().getVisualBounds().getWidth(),
      Screen.getPrimary().getVisualBounds().getHeight());
  }

  /**
   * Creates the exam panel that contains the input fields for the exam details.
   *
   * @return the created VBox that holds the exam form
   */
  public Node createExamPanel() {
    // Δημιουργία του GridPane για όλη τη φόρμα
    VBox examPanel = new VBox(20);
    examPanel.setPadding(new Insets(20));

    examPanel.getChildren().addAll(courseNameSection(), infoSection(), evalSection());

    // Κουμπί "OK"
    okBtn = new Button("OK");
    okBtn.setStyle(Styles.COURSES_BTN_STYLE);
    okBtn.setOnMouseEntered(e -> okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED));
    okBtn.setOnMouseClicked(
        e -> handleBtnOk());
    okBtn.setAlignment(Pos.CENTER_LEFT);

    // Τοποθέτηση του κουμπιού στο GridPane
    HBox okBtnHbox = new HBox(10, okBtn);
    okBtnHbox.setAlignment(Pos.CENTER_LEFT);
    examPanel.getChildren().add(okBtnHbox);

    return examPanel;
  }

  /**
   * Creates the course name section of the exam form where the user inputs the course name.
   *
   * @return a VBox containing the course name input section
   */
  private VBox courseNameSection() {

    Label nameTitle = new Label("Μάθημα:");
    nameTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane nameSection = new GridPane();
    nameSection.setVgap(10);
    nameSection.setHgap(10);
    Label courseTitle = new Label("Oνομασία:");
    courseTitle.setStyle(Styles.StyleType.LABEL.getStyle());
    nameField = new TextField();
    nameSection.add(courseTitle, 0, 0);
    nameSection.add(nameField, 1, 0);

    nameSection.setStyle(Styles.BLACK_BORDER);

    VBox nameVbox = new VBox(10);
    nameVbox.getChildren().addAll(nameTitle, nameSection);

    nameVbox.setMaxWidth(400);
    nameVbox.setPrefWidth(400);

    return nameVbox;
  }

  /**
   * Creates the information section of the exam form where the user selects the course type,
   * enters the number of pages, and selects the exam date.
   *
   * @return a VBox containing the information input section
   */
  private VBox infoSection() {

    Label infoTitle = new Label("Πληροφορίες:");
    infoTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane info = new GridPane();
    info.setVgap(10);
    info.setHgap(10);
    Label typeLabel = new Label("Είδος:");
    typeLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    typeCourseList = new ComboBox<>();

    ObservableList<Subject.SubjectType> types =
        FXCollections.observableArrayList(Subject.SubjectType.values());

    typeCourseList.setItems(types);

    typeCourseList.setOnAction(
        e -> {
          Subject.SubjectType selectedType = typeCourseList.getValue();
          System.out.println("Επιλέχθηκε: " + selectedType);
        });
    info.add(typeLabel, 0, 1);
    info.add(typeCourseList, 1, 1);

    // Τμήμα "Σελίδες" (Pages)
    Label pageLabel = new Label("Σελίδες:");
    pageLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    pageField = new TextField();
    info.add(pageLabel, 0, 2);
    info.add(pageField, 1, 2);

    // Τμήμα "Ημερομηνία Εξέτασης" (Exam Date)
    Label dateLabel = new Label("Ημερομηνία Εξέτασης:");
    dateLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    datePicker = new DatePicker();
    info.add(dateLabel, 0, 3);
    info.add(datePicker, 1, 3);

    info.setStyle(Styles.BLACK_BORDER);

    VBox infoVbox = new VBox(10);
    infoVbox.getChildren().addAll(infoTitle, info);

    infoVbox.setMaxWidth(400);
    infoVbox.setPrefWidth(400);
    return infoVbox;
  }

  private VBox evalSection() {

    Label evalTitle = new Label("Αξιολόγηση:");
    evalTitle.setStyle(Styles.StyleType.TITLE.getStyle());
    GridPane eval = new GridPane();
    eval.setVgap(10);
    eval.setHgap(10);
    Label difficultyLabel = new Label("Δυσκολία:");
    difficultyLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    difficulty = new TextField();
    eval.add(difficultyLabel, 0, 1);
    eval.add(difficulty, 1, 1);

    // Τμήμα "Χρόνος διαβάσματος ανά 20 διαφάνειες" (Time per 20 Slides)
    Label timeLabel = new Label("Χρόνος ανά 20 Διαφάνειες:");
    timeLabel.setStyle(Styles.StyleType.LABEL.getStyle());
    timePer20Slides = new TextField();
    timePer20Slides.setPromptText("Λεπτά");
    eval.add(timeLabel, 0, 2);
    eval.add(timePer20Slides, 1, 2);

    eval.setStyle(Styles.BLACK_BORDER);

    VBox evalVbox = new VBox(10);
    evalVbox.getChildren().addAll(evalTitle, eval);

    evalVbox.setMaxWidth(400);
    evalVbox.setPrefWidth(400);

    return evalVbox;
  }

  private void handleBtnOk() {
    String courseName;
    courseName = nameField.getText();
    String value = pageField.getText();
    List<String> errors = new ArrayList<>();
    if (!String.valueOf(value).matches("\\d+")) {
      errors.add("• Η παράμετρος 'Σελίδες': μπόρει να περιέχει μόνο αριθμούς");
    } else {
      pages = Integer.parseInt(value);
    }

    LocalDate deadline;
    Subject.SubjectType courseType;
    deadline =
        datePicker.getValue() != null ? LocalDate.parse(datePicker.getValue().toString()) : null;
    courseType = typeCourseList.getValue();
    String value2 = difficulty.getText();
    if (!String.valueOf(value2).matches("\\d+")) {
      errors.add("• Η δυσκολία μπόρει να περιέχει μόνο αριθμούς");
    } else {
      diffi = Integer.parseInt(value2);
    }
    String value4 = timePer20Slides.getText();
    if (!String.valueOf(value4).matches("\\d+")) {
      errors.add("• Ο χρόνος ανά 20 διαφάνειες μπόρει να περιέχει μόνο αριθμούς");
    } else {
      time = Integer.parseInt(value4);
    }

    if (courseName.isEmpty()) {
      errors.add("• Εισήγαγε όνομα μαθήματος");
    } else if (!courseName.matches("[a-zA-Zα-ωΑ-ΩάέήίΰϊϋόύώΆΈΉΊΪΫΌΎΏ0-9 ]+")) {
      errors.add("• Η Ονομασία του μαθήματος μπορεί να περιέχει μόνο γράμματα, αριθμούς και κενά");
    }

    if (pages <= 0) {
      errors.add("• Η παράμετρος 'Σελίδες' πρέπει να είναι θετικός αριθμός.");
    }

    if (deadline == null || deadline.isBefore(LocalDate.now())) {
      errors.add("• Πρέπει να επιλέξεις ημερομηνία εξέτασης μετά τη σημερινή ημερομηνία.");
    }

    if (courseType == null) {
      errors.add("• Πρέπει να επιλέξεις το είδος του μαθήματος.");
    }

    if (diffi < 1 || diffi > 10) {
      errors.add("• Η δυσκολία πρέπει να είναι αριθμός μεταξύ 1 και 10.");
    }

    if (time <= 0) {
      errors.add("• Ο χρόνος ανά 20 διαφάνειες πρέπει να είναι θετικός αριθμός.");
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
      successAlert.setTitle("Εισαγωγή Μαθήματος Επιτυχής");
      successAlert.setHeaderText(null);
      successAlert.setContentText("Το Μάθημα προστέθηκε!!");
      DialogPane dialogPane = successAlert.getDialogPane();
      dialogPane.getStyleClass().add("success-alert");
      dialogPane
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/success.css")).toExternalForm());
      successAlert.showAndWait();

      int revision = 0;
      DataInserter.insertSubject(
          courseName, diffi, courseType.toString(), staticUser.getUserId());
      int subjectId = ActiveUser.getSubjectId(staticUser.getUserId(), courseName);
      DataInserter.insertExam(deadline, pages, revision, time, subjectId);

      // create an exam object
      Exam e1 = new Exam(pages, revision, deadline, time);
      // create a subject object
      Subject subject1 = new Subject(courseName, diffi, courseType);
      subject1.addExam(e1);
      staticUser.addSubject(subject1);
      staticUser.addExam(e1);
    }

    clearFields();
    okBtn.setStyle(Styles.COURSES_BTN_MOUSE_ENTERED);
  }

  private void clearFields() {
    nameField.clear();
    pageField.clear();
    datePicker.setValue(null);
    typeCourseList.setValue(null);
    difficulty.clear();
    timePer20Slides.clear();
  }
}
