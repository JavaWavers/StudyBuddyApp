package org.javawavers.studybuddy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Popupdia extends Application {
    private final Map<String, Integer> availabilityMap = new HashMap<>();
    int[] avperday = new int[8];
    private  DatePicker datePicker;



    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPrefSize(428, 444);
        root.setStyle("-fx-border-color: black;");

        Label label1 = new Label("       Διαθεσημότητα");
        label1.setLayoutX(27);
        label1.setLayoutY(49);
        label1.setPrefSize(169, 30);
        label1.setStyle("-fx-background-color: #50D1C6;");
        label1.setFont(new Font("System Bold", 14));

        Label label2 = new Label("    Συγκεκριμένη Ημέρα");
        label2.setLayoutX(232);
        label2.setLayoutY(49);
        label2.setPrefSize(169, 30);
        label2.setStyle("-fx-background-color: #50D1C6;");
        label2.setFont(new Font("System Bold", 14));

        
//δημιουργια labels για τις ημερες
        Label monday = createDayLabel("Δευτέρα", 92);
        Label tuesday = createDayLabel("Τρίτη", 134);
        Label wednesday = createDayLabel("Τετάρτη", 176);
        Label thursday = createDayLabel("Πέπμτη", 221);
        Label friday = createDayLabel("Παρασκευή", 263);
        Label saturday = createDayLabel("Σάββατο", 305);
        Label sunday = createDayLabel("Κυριακή", 352);
//δημιοργια text fields για τις ημερες
        TextField mondayField = createTextField(92);
        TextField tuesdayField = createTextField(134);
        TextField wednesdayField = createTextField(176);
        TextField thursdayField = createTextField(221);
        TextField fridayField = createTextField(263);
        TextField saturdayField = createTextField(305);
        TextField sundayField = createTextField(352);

        datePicker = new DatePicker();
        //TextField specificDayField = new TextField();
        datePicker.setLayoutX(242);
        datePicker.setLayoutY(98);
        datePicker.setPromptText("Eπιλεξτε μη-διαθεσιμη ημερομηνια");
        
        //προσθηκη κουμπιου (οκ) για να κλεινει το παραθυρο
        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #50D1C6; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 14px;");
        okButton.setLayoutX(285);
        okButton.setLayoutY(400);
        okButton.setPrefSize(70, 25);
        root.getChildren().add(okButton);

        okButton.setOnAction(event -> {
//αποθηκευση τιμων που εισαγει ο χρηστης 
//κληση της μεθοδου parseTextFieldValue για να ελενξουμε την τιμη που εισαγει ο χρηστης
        avperday[1] = parseTextFieldValue(mondayField);
        avperday[2] = parseTextFieldValue(tuesdayField);
        avperday[3] = parseTextFieldValue(wednesdayField);
        avperday[4] = parseTextFieldValue(thursdayField);
        avperday[5] = parseTextFieldValue(fridayField);
        avperday[6] = parseTextFieldValue(saturdayField);
        avperday[7] = parseTextFieldValue(sundayField);

        List<String> errors = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Availability.setAvailability(i, avperday[i]);
            if (avperday[i] > 7 ) {
                errors.add("• Oι διαθέσιμες ώρες μέσα σε μια μέρα πρέπει να είναι λιγότερες απο 7");
            }
        }
        LocalDate setNoAvailicility = datePicker.getValue();
        if (setNoAvailicility != null) {
            Availability.setNonAvailability(setNoAvailicility);
        }

        availabilityMap.put("Δευτέρα", avperday[1]);
        availabilityMap.put("Τρίτη", avperday[2]);
        availabilityMap.put("Τετάρτη", avperday[3]);
        availabilityMap.put("Πέμπτη", avperday[4]);
        availabilityMap.put("Παρασκευή", avperday[5]);
        availabilityMap.put("Σάββατο", avperday[6]);
        availabilityMap.put("Κυριακή", avperday[7]);
        //availabilityMap.put("Συγκεκριμένη Ημέρα", setNoAvailicility);

//εκτυπωση των αποτελεσματων των ημερων(για το test)
        System.out.println(Arrays.toString(avperday));

        if(setNoAvailicility == null || setNoAvailicility.isBefore(LocalDate.now())) {
            errors.add("• Πρέπει να επιλέξεις ημερομηνία μη-διαθεσιμότητας μετά τη σημερινή ημερομηνία.");
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




// Κλεισιμο παραθυρου
            primaryStage.close();
        });

        root.getChildren().addAll(label1, label2, monday, tuesday, wednesday, thursday, friday, saturday, sunday,
                mondayField, tuesdayField, wednesdayField, thursdayField, fridayField, saturdayField, sundayField,
                datePicker);

        Scene scene = new Scene(root, 428, 444);
        primaryStage.setTitle("Calendar View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createDayLabel(String day, double layoutY) {
        Label label = new Label(day);
        label.setLayoutX(27);
        label.setLayoutY(layoutY);
        label.setPrefSize(94, 36);
        label.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 20px;");
        label.setTextFill(javafx.scene.paint.Color.web("#f8f4f4"));
        label.setFont(new Font("System Bold", 14));
        return label;
    }

    private TextField createTextField(double layoutY) {
        TextField textField = new TextField();
        textField.setLayoutX(126);
        textField.setLayoutY(layoutY);
        textField.setPrefSize(94, 36);
        textField.setStyle("-fx-background-radius: 20px;");
//περιορισμος εισαγωγης μονο αριθμων 
        textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        return textField;
    
    }
//ελνχος αν το κειμενο ειναι κενο η περιεχει μονο κενα τοτε επιστρεφουμε την τιμη 0 για καθε ημερα 
    private Integer parseTextFieldValue(TextField textField) {
        String text = textField.getText();
        if (text == null || text.trim().isEmpty() ) {
            return 0;
        } else {
            return  Integer.parseInt(text);
        }
    }
    
    public int[] getAvailability() {
        return avperday;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
