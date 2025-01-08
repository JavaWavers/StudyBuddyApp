package org.javawavers.studybuddy;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    SimulateAnnealing simulateAnnealing = new SimulateAnnealing();
    Subject subjectGeneral = new Subject(null);
    ArrayList<Subject> subject = new ArrayList<>();
    ArrayList<Exam> exam = new ArrayList<>();
    private TextField nameField, pageField, revisionField, difficulty, timePer;
    public static String courseName = "";
    public static String pages = "";
    public static String revision = "";
    public static String deadline = "";
    public static String courseType = "";
    public static String diffi = "0";
    public static String time = "";
    int difficul = Integer.parseInt(diffi);
    Subject coursename = new Subject(courseName, difficul);

    private DatePicker datePicker;
    private ComboBox<String> typeCourseList;
    int[][] schedule;


    // Exam Page as Node
    public Node createExamPanel() {
        VBox examPanel = new VBox(20);
        examPanel.setPadding(new Insets(20));

        // Create sections as titledPanes in order to be foldable
        TitledPane coursePane = new TitledPane("Μάθημα", createCourseSection());
        TitledPane infoPane = new TitledPane("Πληροφορίες", createInfoSection());
        TitledPane evalPane = new TitledPane("Αξιολόγηση", createEvalSection());

        titlePaneStyle(coursePane,200, 400, 0, 300);
        titlePaneStyle(infoPane,200, 400, 0, 300);
        titlePaneStyle(evalPane,200, 400, 0, 300);

        //"ok" Button
        Button okBtn = new Button("OK");
        okBtn.setStyle(btnStyle());
        okBtn.setOnMouseEntered(e -> okBtn.setStyle(btnMouseEntered()));
        okBtn.setOnMouseClicked(e -> {
            courseName = nameField.getText();
            pages = pageField.getText();
            revision = revisionField.getText();
            deadline = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
            courseType = typeCourseList.getValue();
            diffi = difficulty.getText();
            time = timePer.getText();

            List<String> errors = new ArrayList<>();

            if (courseName.isEmpty()) {
                errors.add("• Εισήγαγε Μάθημα");
            }

            if (pages.isEmpty() || !pages.matches("\\d+")) {
                errors.add("• Η παράμετρος 'Σελίδες' πρέπει να είναι ακέραιος αριθμός.");
            }

            if (revision.isEmpty() || !revision.matches("\\d+")) {
                errors.add("• Η παράμετρος 'Επανάληψη' πρέπει να είναι ακέραιος αριθμός.");
            }

            if (deadline == null || LocalDate.parse(deadline).isBefore(LocalDate.now())) {
                errors.add("• Πρέπει να επιλέξεις ημερομηνία εξέτασης μετά τη σημερινή ημερομηνία.");
            }

            if (courseType == null || courseType.isEmpty()) {
                errors.add("• Πρέπει να επιλέξεις το είδος του μαθήματος.");
            }

            if (diffi.isEmpty() || !diffi.matches("\\d+") || Integer.parseInt(diffi) < 1 || Integer.parseInt(diffi) > 10) {
                errors.add("• Η δυσκολία πρέπει να είναι αριθμός μεταξύ 1 και 10.");
            }

            if (time.isEmpty() || !time.matches("\\d+")) {
                errors.add("• Ο χρόνος ανά 20 διαφάνειες πρέπει να είναι αριθμός.");
            }



            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                alert.setHeaderText(null);
                String errorMessage = String.join("\n", errors);
                alert.setContentText(errorMessage);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.showAndWait();
                return;
            }
            difficul = Integer.parseInt(diffi);
            int page = Integer.parseInt(revision);
            double minutes = Double.parseDouble(pages);
            Subject coursename = new Subject(courseName, difficul);
           // subject.add(coursename);

            LocalDate localDeadline = LocalDate.parse(deadline);
            //Dates exam = new Dates(coursename, localDeadline);
            //simulateAnnealing.getExams().add(exam);

             Exam exam1 = new Exam(coursename, localDeadline, page);
            //subjectGeneral.addExam(exam1);
             coursename.addExam(exam1);
             subject.add(coursename);
             for ( Subject s : subject) {
                simulateAnnealing.addSubject(s);
              //  System.out.println(PrintSchedule.printSchedule(schedule, besttask, count));
            }
            schedule = simulateAnnealing.scheduleResult();
            //System.out.println(schedule);
           // subjectGeneral.getExams(exam1);
           // exam.add(exam1);
            nameField.clear();
            pageField.clear();
            revisionField.clear();
            datePicker.setValue(null);
            typeCourseList.setValue("");
            difficulty.clear();
            timePer.clear();
            Availability.setAvailability(1, 6); // Monday: 6 available hours
            Availability.setAvailability(2, 4); // Tuesday: 4 available hours
        Availability.setAvailability(3, 7); // Wednesday: 7 available hours
        Availability.setAvailability(4, 4); // Thursday: 4 available hours
        Availability.setAvailability(5, 6); // Friday: 6 available hours
        Availability.setAvailability(6, 6); // Saturday: 6 available hours
        Availability.setAvailability(7, 6); // Sunday: 6 available hour
            //πρεπει αυτο simulateAnnealing.addSubject(coursename);  να δημιουργειτε στο ημερολογιο αφου εχουν εισαχθει ολα τα μαθηματα και οι εργασιιες
        //αυτο λειτουργει και πρεπει να το βσλω στο calendar 
        //simulateAnnealing.addSubject(coursename);
        //for ( Subject s : subject) {
           // simulateAnnealing.addSubject(s);
          //  System.out.println(s);
       // }
      //  simulateAnnealing.scheduleResult();
        //System.out.println(schedule);
        
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
        typeCourseList.getItems().addAll("","Θεωρητικό", "Θετικό", "Συνδυασμός");
        typeCourseList.setValue("");
        typeCourseList.setStyle("-fx-font-family: 'Arial';");
        HBox typeBox = new HBox(10, typeLabel, typeCourseList);
        pageField = new TextField();
        revisionField = new TextField();
        //ημερολογιο για την ημερομηνια τις εξετασης
        datePicker = new DatePicker();
        HBox deadlineBox = createLabeledField("Ημερομηνία Εξέτασης: ", datePicker);

        

        HBox pageBox = createLabeledField("Σελίδες: ", pageField);
        HBox revisionBox = createLabeledField("Επανάληψη ανά (σελίδες): ", revisionField);
        //HBox deadlineBox = createLabeledField("Ημερομηνία Εξέτασης: ", deadlineField);


        box.getChildren().addAll(typeBox, pageBox, revisionBox, deadlineBox);
        return box;
    }

    // Section for course evaluation
    private VBox createEvalSection() {
        VBox box = new VBox(10);
        difficulty = new TextField();
        timePer = new TextField();
        box.getChildren().addAll(
                createLabeledField("Δυσκολία:", difficulty),
                createLabeledField("Χρόνος ανά 20 διαφάνειες:", timePer)
        );
        return box;
    }
    // Label field
    private HBox createLabeledField(String labelText,  Node node) {
        Label label = new Label(labelText);
        label.setStyle(labelStyle());
        return new HBox(10, label, node);
    }

    //label style
    private String labelStyle() {
        return "-fx-background-color: rgba(181, 99, 241, 0.81);"
                + " -fx-padding: 5;"
                + " -fx-border-width: 1px;"
                + " -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;"
                +"-fx-font-family: Arial;";
    }

    //titlePane style
    private void titlePaneStyle(TitledPane titledPane,
                                double minWidth, double maxWidth,
                                double minHeight, double maxHeight) {

        titledPane.setMinWidth(minWidth);
        titledPane.setMaxWidth(maxWidth);
        titledPane.setMinHeight(minHeight);
        titledPane.setMaxHeight(maxHeight);


        titledPane.setStyle("-fx-background-color: rgba(101, 225, 101, 0.81);"
                +   " -fx-border-color: #000000;"
                +   " -fx-border-width: 1px;"
                +   " -fx-border-radius: 4px;"
                +   " -fx-background-radius: 4px;");

    }

    //Button Styles
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

    public List<Subject> getSubjects() {
        return subject;
    }

    public List<Exam> getExams() {
        return exam;
    }

    public Scene examStartingPage(SceneManager sceneManager) {
        VBox examViewWithBtn = new VBox(20);
        VBox examView = (VBox) createExamPanel();
        examViewWithBtn.getChildren().add(examView);

        Button nextBtn = new Button("Επόμενο");
        nextBtn.setStyle(btnStyle());
        nextBtn.setOnAction(e -> {
            System.out.println("Το κουμπί πατήθηκε!");
        });
        examViewWithBtn.getChildren().add(nextBtn);

        Scene scene = new Scene(examViewWithBtn, 1024, 768);
        return scene;
    }
}
