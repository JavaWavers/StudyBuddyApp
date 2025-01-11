package org.javawavers.studybuddy.ui_ux;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.time.LocalDate;
import java.util.*;

import org.javawavers.studybuddy.calculations.Availability;


public class AvailabilityPage {
    private final Map<String, Integer> availabilityMap = new HashMap<>();
    Integer[] avPerDay = new Integer[8];
    private DatePicker datePicker;

    private VBox leftPane = new VBox(10);
    private VBox rightPane = new VBox(10);
    private HBox btnsBox = new HBox(10);
    private TextField[] dayFields = new TextField[7];
    private String[] days = {"Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββατο", "Κυριακή"};
    private Stage popUpStage;
    private SceneManager sceneManager;

    public AvailabilityPage(Stage popUpStage) {
        this.popUpStage = popUpStage;
    }

    public AvailabilityPage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    public VBox availabilityPage() {

        VBox availPage = new VBox();
        leftFrame();
        rightFrame();

        HBox mainPanes = dayPickPanes();
        HBox btnPane = btnsFrame();

        HBox.setHgrow(mainPanes, Priority.ALWAYS);
        HBox.setHgrow(btnPane, Priority.ALWAYS);

        availPage.getChildren().addAll(mainPanes, btnPane);

        return availPage;
    }

    private HBox dayPickPanes() {
        HBox mainPane = new HBox(20);
        mainPane.setPadding(new Insets(30));

        leftPane.setAlignment(Pos.CENTER_LEFT);
        rightPane.setAlignment(Pos.TOP_RIGHT);

        VBox.setVgrow(leftPane, Priority.ALWAYS);
        VBox.setVgrow(rightPane, Priority.ALWAYS);

        leftPane.setFillWidth(true);
        rightPane.setFillWidth(true);
        leftPane.setMaxHeight(Double.MAX_VALUE);
        rightPane.setMaxHeight(Double.MAX_VALUE);

        mainPane.getChildren().addAll(leftPane, rightPane);
        return mainPane;
    }


    private VBox rightFrame() {

        Label dayLabel = new Label("Μη Διαθέσιμες Ημέρες");
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setStyle("-fx-background-color: #50D1C6;");
        dayLabel.setFont(new Font("System Bold", 14));

        datePicker = new DatePicker();
        datePicker.setPromptText("Eπιλεξτε μη-διαθεσιμη ημερομηνια");

        rightPane.getChildren().addAll(dayLabel, datePicker);
        return rightPane;
    }

    private VBox leftFrame() {

        Label avalLabel = new Label("Διαθεσιμότητα");
        avalLabel.setAlignment(Pos.CENTER);
        avalLabel.setStyle("-fx-background-color: #50D1C6;");
        avalLabel.setFont(new Font("System Bold", 14));

        GridPane daysPane = new GridPane();
        daysPane.setHgap(10);
        daysPane.setVgap(10);


        for (int i = 0; i < days.length; i++) {
            Label dayLabel = createDayLabel(days[i]);
            TextField hoursField = createHoursField();

            dayFields[i] = hoursField;
            daysPane.add(dayLabel, 0, i);
            daysPane.add(hoursField, 1, i);
        }

        leftPane.getChildren().addAll(avalLabel, daysPane);
        return leftPane;

    }

    private HBox btnsFrame() {
        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #50D1C6; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 14px;");


        okBtn.setOnAction(event -> {
            Integer[] avPerDay = new Integer[7];
            List<String> errors = new ArrayList<>();

            for (int i = 0; i < dayFields.length; i++) {
                avPerDay[i] = parseTextFieldValue(dayFields[i]);
                if (avPerDay[i] > 10) {
                    errors.add("• Oι διαθέσιμες ώρες μέσα σε μια μέρα πρέπει να είναι λιγότερες απο 10");
                }
            }
            LocalDate setNoAvailability = datePicker.getValue();
            if (setNoAvailability != null && setNoAvailability.isBefore(LocalDate.now())) {
                errors.add("• Πρέπει να επιλέξεις ημερομηνία μη-διαθεσιμότητας μετά τη σημερινή ημερομηνία.");
            }
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                alert.setHeaderText(null);
                String errorMessage = String.join("\n", errors);
                alert.setContentText(errorMessage);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/alert.css").toExternalForm());
                alert.showAndWait();
            }

            for (int j = 0; j < days.length; j++) {
                availabilityMap.put(days[j], avPerDay[j]);
            }
            if (setNoAvailability != null) {
                Availability.setNonAvailability(setNoAvailability);
            }

            if (popUpStage.isShowing()) {
                popUpStage.close();
            }

        });
        okBtn.setAlignment(Pos.BASELINE_LEFT);
        btnsBox.getChildren().add(okBtn);
        btnsBox.setPadding(new Insets(30));
        return btnsBox;
    }

    private Label createDayLabel(String day) {
        Label label = new Label(day);
        label.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 20px;");
        label.setTextFill(javafx.scene.paint.Color.web("#f8f4f4"));
        label.setFont(new Font("System Bold", 14));
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }

    private TextField createHoursField() {
        TextField hoursField = new TextField();
        hoursField.setStyle("-fx-background-radius: 20px;");
//περιορισμος εισαγωγης μονο αριθμων 
        hoursField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        return hoursField;

    }

    //ελνχος αν το κειμενο ειναι κενο η περιεχει μονο κενα τοτε επιστρεφουμε την τιμη 0 για καθε ημερα
    private Integer parseTextFieldValue(TextField textField) {
        if (textField != null) {
            String text = textField.getText();
            if (text == null || text.trim().isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(text);
            }
        }
        return 0;
    }


    public Integer[] getAvailability() {
        return avPerDay;
    }

    public Scene availStartingPage(SceneManager sceneManager) {
        VBox availViewWithBtn = new VBox();

        HBox nameLbl = new HBox(20);
        Label name = new Label("Διαθεσιμότητα");
        name.setStyle(labelStyle());
        nameLbl.getChildren().add(name);
        nameLbl.setPadding(new Insets(20));
        availViewWithBtn.getChildren().add(nameLbl);

        VBox availView =  availabilityPage();
        availViewWithBtn.getChildren().add(availView);

        HBox btns = new HBox(15);
        btns.setPadding(new Insets(20));
        Button prevBtn = new Button("Προηγούμενο");
        prevBtn.setStyle(btnStyle());
        prevBtn.setOnAction(e -> {
            AssignmentPage assignPage = new AssignmentPage();
            sceneManager.switchScene(assignPage.assignmentStartingPage(sceneManager));
        });

        Button nextBtn = new Button("Επόμενο");
        nextBtn.setStyle(btnStyle());
        nextBtn.setOnAction(e -> {
            // System.out.println("Το κουμπί πατήθηκε!");
            MainFrame mainframe = new MainFrame();
            sceneManager.switchScene(mainframe.mainFrame(sceneManager));
        });
        btns.getChildren().addAll(prevBtn, nextBtn);
        availViewWithBtn.getChildren().add(btns);
        HBox btnBox = new HBox(20);
        btnBox.setPadding(new Insets(20));

        Scene scene = new Scene(availViewWithBtn,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());
        return scene;
    }

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
    private String labelStyle() {
        return "-fx-background-color: rgba(181, 99, 241, 0.81);"
                + " -fx-padding: 5;"
                + " -fx-border-width: 1px;"
                + " -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;"
                +"-fx-font-family: Arial;";
    }
}
//δημιουργια labels για τις ημέρες
    /*    Label monday = createDayLabel("Δευτέρα");
        Label tuesday = createDayLabel("Τρίτη");
        Label wednesday = createDayLabel("Τετάρτη");
        Label thursday = createDayLabel("Πέπμτη");
        Label friday = createDayLabel("Παρασκευή");
        Label saturday = createDayLabel("Σάββατο");
        Label sunday = createDayLabel("Κυριακή");
//δημιοργια text fields για τις ημερες
        TextField mondayField = createHoursField(92);
        TextField tuesdayField = createHoursField(134);
        TextField wednesdayField = createHoursField(176);
        TextField thursdayField = createHoursField(221);
        TextField fridayField = createHoursField(263);
        TextField saturdayField = createHoursField(305);
        TextField sundayField = createHoursField(352);


        // root.getChildren().addAll(label1, label2, monday, tuesday, wednesday, thursday, friday, saturday, sunday,
        //      mondayField, tuesdayField, wednesdayField, thursdayField, fridayField, saturdayField, sundayField,
        //    datePicker);*/


//αποθηκευση τιμων που εισαγει ο χρηστης
//κληση της μεθοδου parseTextFieldValue για να ελενξουμε την τιμη που εισαγει ο χρηστης
  /*      avperday[1] = parseTextFieldValue(mondayField);
        avperday[2] = parseTextFieldValue(tuesdayField);
        avperday[3] = parseTextFieldValue(wednesdayField);
        avperday[4] = parseTextFieldValue(thursdayField);
        avperday[5] = parseTextFieldValue(fridayField);
        avperday[6] = parseTextFieldValue(saturdayField);
        avperday[7] = parseTextFieldValue(sundayField);

        List<String> errors = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Availability.setAvailability(i, avperday[i]);
            if (avperday[i] > 7) {
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

        if (setNoAvailicility == null || setNoAvailicility.isBefore(LocalDate.now())) {
            errors.add("• Πρέπει να επιλέξεις ημερομηνία μη-διαθεσιμότητας μετά τη σημερινή ημερομηνία.");
        }*/