package org.javawavers.studybuddy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        okBtn.setOnMouseClicked(e -> okBtn.setStyle(btnMousePressed()));
        okBtn.setAlignment(Pos.CENTER_LEFT);

        HBox okBtnHBox = new HBox(10);
        okBtnHBox.setAlignment(Pos.CENTER_LEFT);
        okBtnHBox.getChildren().add(okBtn);

        //adds all the exam page parts to the panel
        examPanel.getChildren().addAll(coursePane, infoPane, evalPane, okBtnHBox);

        return examPanel;  //returns the page
    }

    // Section for Course name
    private VBox createCourseSection() {
        VBox box = new VBox(10);
        Label nameLabel = new Label("Όνομασία Μαθήματος:");
        nameLabel.setStyle(labelStyle());
        TextField nameField = new TextField();
        String courseName = nameField.getText();
        box.getChildren().addAll(nameLabel, nameField);
        return box;
    }

    // Section for course information
    private VBox createInfoSection() {
        VBox box = new VBox(10);

        Label typeLabel = new Label("Είδος: ");
        typeLabel.setStyle(labelStyle());
        ComboBox<String> typeCourseList = new ComboBox<>();
        typeCourseList.getItems().addAll("","Θεωρητικό", "Θετικό", "Συνδυασμός");
        typeCourseList.setValue("");
        HBox typeBox = new HBox(10, typeLabel, typeCourseList);
        String courseType = typeCourseList.getValue();

        HBox pageBox = createLabeledField("Σελίδες: ");
        HBox revisionBox = createLabeledField("Επανάληψη ανά (σελίδες): ");
        HBox deadlineBox = createLabeledField("Ημερομηνία Εξέτασης: ");


        box.getChildren().addAll(typeBox,pageBox,revisionBox,deadlineBox);
        return box;
    }

    // Section for course evaluation
    private VBox createEvalSection() {
        VBox box = new VBox(10);
        box.getChildren().addAll(
                createLabeledField("Δυσκολία:"),
                createLabeledField("Χρόνος ανά 20 διαφάνειες:")
        );
        return box;
    }

    // Label field
    private HBox createLabeledField(String labelText) {
        Label label = new Label(labelText);
        label.setStyle(labelStyle());
        TextField textField = new TextField();
        return new HBox(10, label, textField);
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
}
