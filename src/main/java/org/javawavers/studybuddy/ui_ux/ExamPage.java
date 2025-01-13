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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.javawavers.studybuddy.courses.Exam;
import org.javawavers.studybuddy.courses.Subject;

/*
TODO:
 * fix label and textFields alignment
 * fix title of titlePane colour
 * fix titlePane's background color
 * fix general style of okBtn when mouseEntered + MousePressed
 * ημερομηνία εξέτασης/παράδοσης need calendar
 * Add documentation code
*/
public class ExamPage {
  // create field for inputs
  private TextField nameField, pageField, revisionField, difficulty, timePer20Slides;
  // crete static variables for the use of inputs
  private static String courseName = "";
  private static int pages = 0;
  private static int revision = 0;
  private static LocalDate deadline;
  private static Subject.SubjectType courseType;
  private static int diffi = 0;
  private static double time = 0.0;

  // variables for storing inputs for calendar class use
  private DatePicker datePicker;
  private ComboBox<Subject.SubjectType> typeCourseList;

  // List with subjects
  private static List<Subject> subjects = new ArrayList<Subject>();

  public void add(Subject subject) {
    subjects.add(subject); // Προσθέτουμε το subject στην λίστα
    System.out.println(
        "Added subject: " + subject.getCourseName()); // Προαιρετική εκτύπωση για έλεγχο
  }

  // subject list getter
  public List<Subject> getSubjects() {
    return subjects;
  }

  // Exam Page as Node
  public Node createExamPanel() {
    VBox examPanel = new VBox(20);
    examPanel.setPadding(new Insets(20));

    // Create sections as titledPanes in order to be foldable
    TitledPane coursePane = new TitledPane("Μάθημα", createCourseSection());
    TitledPane infoPane = new TitledPane("Πληροφορίες", createInfoSection());
    TitledPane evalPane = new TitledPane("Αξιολόγηση", createEvalSection());

    titlePaneStyle(coursePane, 200, 400, 0, 300);
    titlePaneStyle(infoPane, 200, 400, 0, 300);
    titlePaneStyle(evalPane, 200, 400, 0, 300);

    // "ok" Button
    Button okBtn = new Button("OK");
    okBtn.setStyle(btnStyle());
    okBtn.setOnMouseEntered(e -> okBtn.setStyle(btnMouseEntered()));
    okBtn.setOnMouseClicked(
        e -> {
          courseName = nameField.getText();
          String value = pageField.getText();
          pages = Integer.parseInt(value);
          revision = Integer.parseInt(revisionField.getText());
          deadline =
              datePicker.getValue() != null
                  ? LocalDate.parse(datePicker.getValue().toString())
                  : null;
          courseType = typeCourseList.getValue();
          String value2 = difficulty.getText();
          diffi = Integer.parseInt(value2);
          time = Double.parseDouble(timePer20Slides.getText());

          // create a subject object
          Subject subject1 = new Subject(courseName, courseType, diffi);
          // create an exam object

          System.out.print("is subject null " + subject1.getCourseName());
          System.out.print("is subject null " + subject1.getSubjectType());
          System.out.print("is subject null " + subject1.getDifficultyLevel());
          Exam e1 = new Exam(pages, revision, deadline, time);
          subject1.addExam(e1);
          // static user add the subject
          staticUser.addSubject(subject1);

          System.out.print("object:" + subject1);
          System.out.println("Adding subject: " + subject1.getCourseName());
          // subjects.add(subject1);
          // add(subject1);
          System.out.println("Subjects in ExamPage after add: " + getSubjects().size());
          List<String> errors = new ArrayList<>();

          if (courseName.isEmpty()) {
            errors.add("• Εισήγαγε όνομα μαθήματος");
          }

          if (pages <= 0) {
            errors.add("• Η παράμετρος 'Σελίδες' πρέπει να είναι θετικός αριθμός.");
          }

          if (revision < 0) {
            errors.add(
                "• Η παράμετρος 'Επανάληψη ανά (σελίδες): ' πρέπει να είναι μη αρνητικός αριθμός.");
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
                .add(Objects.requireNonNull(getClass().getResource("alert.css")).toExternalForm());
            alert.showAndWait();
            return;
          }

          nameField.clear();
          pageField.clear();
          revisionField.clear();
          datePicker.setValue(null);
          typeCourseList.setValue(null);
          difficulty.clear();
          timePer20Slides.clear();
          okBtn.setStyle(btnMousePressed());
        });

    okBtn.setAlignment(Pos.CENTER_LEFT);

    HBox okBtnHBox = new HBox(10);
    okBtnHBox.setAlignment(Pos.CENTER_LEFT);
    okBtnHBox.getChildren().add(okBtn);

    examPanel.getChildren().addAll(coursePane, infoPane, evalPane, okBtnHBox);

    return examPanel;
  }

  // Section for Course name
  private VBox createCourseSection() {
    VBox box = new VBox(10);
    Label nameLabel = new Label("Όνομασία Μαθήματος:");
    nameLabel.setStyle(labelStyle());
    nameField = new TextField();
    box.getChildren().addAll(nameLabel, nameField);
    return box;
  }

  // Section for course information
  private VBox createInfoSection() {
    VBox box = new VBox(10);

    Label typeLabel = new Label("Είδος: ");
    typeLabel.setStyle(labelStyle());
    typeCourseList = new ComboBox<>();
    Subject s = new Subject("Βοηθητικό");
    typeCourseList.getItems().addAll(s.getSubjectType());
    typeCourseList.setValue(null);
    typeCourseList.setStyle("-fx-font-family: 'Arial';");
    HBox typeBox = new HBox(10, typeLabel, typeCourseList);
    pageField = new TextField();
    revisionField = new TextField();
    // calendar for the exam date
    datePicker = new DatePicker();
    HBox deadlineBox = createLabeledField("Ημερομηνία Εξέτασης: ", datePicker);

    HBox pageBox = createLabeledField("Σελίδες: ", pageField);
    HBox revisionBox = createLabeledField("Επανάληψη ανά (σελίδες): ", revisionField);

    box.getChildren().addAll(typeBox, pageBox, revisionBox, deadlineBox);
    return box;
  }

  // Section for course evaluation
  private VBox createEvalSection() {
    VBox box = new VBox(10);
    difficulty = new TextField();
    timePer20Slides = new TextField();
    box.getChildren()
        .addAll(
            createLabeledField("Δυσκολία:", difficulty),
            createLabeledField("Χρόνος ανά 20 διαφάνειες:", timePer20Slides));
    return box;
  }

  // Label field
  private HBox createLabeledField(String labelText, Node node) {
    Label label = new Label(labelText);
    label.setStyle(labelStyle());
    return new HBox(10, label, node);
  }

  // label style
  private String labelStyle() {
    return "-fx-background-color: rgba(181, 99, 241, 0.81);"
        + " -fx-padding: 5;"
        + " -fx-border-width: 1px;"
        + " -fx-border-radius: 4px;"
        + " -fx-background-radius: 4px;"
        + "-fx-font-family: Arial;";
  }

  // titlePane style
  private void titlePaneStyle(
      TitledPane titledPane, double minWidth, double maxWidth, double minHeight, double maxHeight) {

    titledPane.setMinWidth(minWidth);
    titledPane.setMaxWidth(maxWidth);
    titledPane.setMinHeight(minHeight);
    titledPane.setMaxHeight(maxHeight);

    titledPane.setStyle(
        "-fx-background-color: rgba(101, 225, 101, 0.81);"
            + " -fx-border-color: #000000;"
            + " -fx-border-width: 1px;"
            + " -fx-border-radius: 4px;"
            + " -fx-background-radius: 4px;");
  }

  // Button Styles
  private String btnStyle() {
    return "-fx-background-color: linear-gradient(#FAD7A0, #F7B267);"
        + "-fx-background-radius: 8,7,6;"
        + "-fx-background-insets: 0,1,2;"
        + "-fx-text-fill: #5A3D2B;"
        + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);"
        + "-fx-font-weight: bold;"
        + "-fx-padding: 10 20;"
        + "-fx-border-color: #D98A4B;"
        + "-fx-border-radius: 6;";
  }

  private String btnMouseEntered() {
    return "-fx-background-color: linear-gradient(#FFE0B2, #F7B267);"
        + "-fx-background-radius: 8,7,6;"
        + "-fx-background-insets: 0,1,2;"
        + "-fx-text-fill: #5A3D2B;"
        + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);"
        + "-fx-font-weight: bold;"
        + "-fx-padding: 10 20;"
        + "-fx-border-color: #D98A4B;"
        + "-fx-border-radius: 6;";
  }

  private String btnMousePressed() {
    return "-fx-background-color: linear-gradient(#F7B267, #D98A4B);"
        + "-fx-background-radius: 8,7,6;"
        + "-fx-background-insets: 0,1,2;"
        + "-fx-text-fill: #5A3D2B;"
        + "-fx-font-weight: bold;"
        + "-fx-padding: 10 20;"
        + "-fx-border-color: #D98A4B;"
        + "-fx-border-radius: 6;"
        + "-fx-effect: none;";
  }

  public Scene examStartingPage(SceneManager sceneManager) {
    VBox examViewWithBtn = new VBox();

    HBox nameLbl = new HBox(20);
    Label name = new Label("Εισαγωγή Μαθημάτων");
    name.setStyle(labelStyle());
    nameLbl.getChildren().add(name);
    nameLbl.setPadding(new Insets(20));
    examViewWithBtn.getChildren().add(nameLbl);

    VBox examView = (VBox) createExamPanel();
    examViewWithBtn.getChildren().add(examView);

    HBox btnBox = new HBox(20);
    btnBox.setPadding(new Insets(20));
    Button nextBtn = new Button("Επόμενο");
    nextBtn.setStyle(btnStyle());
    nextBtn.setOnAction(
        e -> {
          AssignmentPage assignPage = new AssignmentPage();
          sceneManager.switchScene(assignPage.assignmentStartingPage(sceneManager));
        });
    btnBox.getChildren().add(nextBtn);
    examViewWithBtn.getChildren().add(btnBox);

    Scene scene =
        new Scene(
            examViewWithBtn,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());
    return scene;
  }
}
