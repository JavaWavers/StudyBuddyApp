package org.javawavers.studybuddy.ui_ux;
import  java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.javawavers.studybuddy.courses.Assignment;

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

/*
TODO :
 * same as ExamPage
 */
public class AssignmentPage {
    public static ArrayList<Assignment> assignments = new ArrayList<>();
    private TextField namField, assignmentField, estimateHours, difficultyField;
    ComboBox<String> coursesList;
    private DatePicker datePicker;
    public static String courseName = "";
    public static String title = "";
    public static String estimate = "";
    public static String difficulty = "";
    public static String deadline = "";
    public static String courseType = "";
    static LocalDate localDeadline;
    static int estimateHour;

    // Assignment Page as Node
    public Node assignmentPanel() {
        VBox assignmentPanel = new VBox(20);
        assignmentPanel.setPadding(new Insets(20));

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
            //courseName = namField.getText();
            title = assignmentField.getText();
            estimate = estimateHours.getText();
            difficulty = difficultyField.getText();
            deadline = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
            courseType = coursesList.getValue();

            List<String> errors = new ArrayList<>();
           /*  if (courseName.isEmpty()) {
                errors.add("• Εισήγαγε Μάθημα");
            }*/

            if (title.isEmpty()) {
                errors.add("• Όρισε τον Τίτλο της εργασίας");
            }

            if (estimate.isEmpty()) {
                errors.add("• Όρισε εκτιμώμενη ώρα για το μάθημα");
            }

            if (difficulty.isEmpty() || !difficulty.matches("\\d+") || Integer.parseInt(difficulty) < 1 || Integer.parseInt(difficulty) > 10) {
                errors.add("• H δυσκολία πρέπει να είναι αριθμός μεταξύ 1 και 10.");
            }

            if (deadline == null || LocalDate.parse(deadline).isBefore(LocalDate.now())) {
                errors.add("• Πρέπει να επιλέξεις ημερομηνία εξέτασης μετά τη σημερινή ημερομηνία.");
            }

            if (courseType == "") {
                errors.add("• Πρέπει να προσθέσεις ένα μάθημα");
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
            localDeadline = LocalDate.parse(deadline);
            estimateHour = Integer.parseInt(estimate);
           // SimulateAnnealing simulateAnnealing = new SimulateAnnealing();
           //Subject sub = new Subject();

            //simulateAnnealing.subAss2(title, localDeadline, estimateHour);
            Assignment assignment1 = new Assignment(title, localDeadline, estimateHour);
            assignments.add(assignment1);
            
            //ExamPage exampage = new ExamPage();
            //Subject course = exampage.coursename;
            //course.addAssignment(assignment1);

            namField.clear();
            assignmentField.clear();
            estimateHours.clear();
            difficultyField.clear();
            datePicker.setValue(null);
            coursesList.setValue("");

            //System.out.println(courseName);
            System.out.println(title);
            System.out.println(estimate);
            System.out.println(difficulty);
            System.out.println(deadline);
            System.out.println(courseType);


        
        
            okBtn.setStyle(btnMousePressed());
        });
        okBtn.setAlignment(Pos.CENTER_LEFT);

        HBox okBtnHBox = new HBox(10);
        okBtnHBox.setAlignment(Pos.CENTER_LEFT);
        okBtnHBox.getChildren().add(okBtn);

        //adds all the exam page parts to the panel
        assignmentPanel.getChildren().addAll(coursePane, infoPane, evalPane, okBtnHBox);

        return assignmentPanel;  //returns the page
    }

    // Section for Course name
    private VBox createCourseSection() {
        VBox box = new VBox(10);
        Label nameLabel = new Label("Μάθημα Εργασίας:");
        nameLabel.setStyle(labelStyle());
        namField = new TextField();

        coursesList = new ComboBox<>();
        coursesList.setPromptText(" ");
        coursesList.getItems().addAll("","Μαθηματικά", "Ιστορία", "Φυσική");
        coursesList.setValue("");


        box.getChildren().addAll(nameLabel, coursesList);
        return box;
    }

    // Section for course information
    private VBox createInfoSection() {
        datePicker = new DatePicker();
        HBox deadlineBox = createLabeledField("Ημερομηνία Παράδοσης:", datePicker);
        
        assignmentField = new TextField();
        estimateHours = new TextField();
        
        VBox box = new VBox(10);
        box.getChildren().addAll(
                createLabeledField("Tίτλος Εργασίας:", assignmentField),
                createLabeledField("Εκτιμώμενες Απαιτούμενες Ώρες:", estimateHours)
        );
        box.getChildren().addAll(deadlineBox);
        return box;
    }

    // Section for course evaluation
    private VBox createEvalSection() {
        difficultyField = new TextField();
        VBox box = new VBox(10);
        box.getChildren().addAll(
                createLabeledField("Δυσκολία:", difficultyField)
        );
        return box;
    }

    // Label field
    private HBox createLabeledField(String labelText, Node node) {
        Label label = new Label(labelText);
        label.setStyle(labelStyle());
        //TextField textField = new TextField();
        return new HBox(10, label, node);
    }

    //label style
    private String labelStyle() {
        return "-fx-background-color: rgba(181, 99, 241, 0.81);"
                + " -fx-padding: 5;"
                + " -fx-border-width: 1px;"
                + " -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;";
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

    public Scene assignmentStartingPage(SceneManager sceneManager) {
        VBox assignViewWithBtn = new VBox();

        HBox nameLbl = new HBox(20);
        Label name = new Label("Εισαγωγή Εργασιών");
        name.setStyle(labelStyle());
        nameLbl.getChildren().add(name);
        nameLbl.setPadding(new Insets(20));
        assignViewWithBtn.getChildren().add(nameLbl);

        VBox examView = (VBox) assignmentPanel();
        assignViewWithBtn.getChildren().add(examView);

        HBox btns = new HBox(15);
        btns.setPadding(new Insets(20));
        Button prevBtn = new Button("Προηγούμενο");
        prevBtn.setStyle(btnStyle());
        prevBtn.setOnAction(e -> {
            ExamPage examPage = new ExamPage();
            sceneManager.switchScene(examPage.examStartingPage(sceneManager));
        });

        Button nextBtn = new Button("Επόμενο");
        nextBtn.setStyle(btnStyle());
        nextBtn.setOnAction(e -> {
           // System.out.println("Το κουμπί πατήθηκε!");
           Popupdia.MainFrame mainframe = new Popupdia.MainFrame();
           sceneManager.switchScene(mainframe.mainFrame(sceneManager));
        });
        btns.getChildren().addAll(prevBtn, nextBtn);
        assignViewWithBtn.getChildren().add(btns);

        Scene scene = new Scene(assignViewWithBtn,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());
        return scene;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public String getTitle() {
        return  title;
    }
    public LocalDate getDeadline() {
        return localDeadline;   
    }
    public int getEstimateHours() {
        return estimateHour;
    }
}
