package org.javawavers.studybuddy.ui_ux;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.time.LocalDate;
import java.util.*;

import org.javawavers.studybuddy.calculations.Availability;

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

        
//δημιουργια labels για τις ημέρες
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

    public static class Dashboard {
        // Center panel
        public Node createDashboard() {
            VBox centerPanel = new VBox(10);
            centerPanel.setPadding(new Insets(20));
            centerPanel.setStyle("-fx-background-color: white;");

            Label overviewLabel = new Label("Overview");
            overviewLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
            overviewLabel.setStyle("-fx-text-fill: black;");

            // Summary Boxes
            HBox summaryBox = new HBox(10);
            summaryBox.getChildren().addAll(
                    createSummaryBox("Goals Completed", "57.5%", "#57C4E5"),
                    createSummaryBox("Study Completed", "50%", "#D4915D"),
                    createSummaryBox("Assignment Completed", "65%", "#57C4E5"),
                    createSummaryBox("Goals Completed", "65%", "#D4915D")
            );

            // Charts
            HBox chartsBox = new HBox(10, createLineChart(), createPieChart());
            HBox barChartsBox = new HBox(10, createBarChart("Study"), createBarChart("Assignments"));

            centerPanel.getChildren().addAll(overviewLabel, summaryBox, chartsBox, barChartsBox);
            return centerPanel;
        }


        // Summary box
        private VBox createSummaryBox(String title, String percentage, String color) {
            VBox box = new VBox(5);
            box.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");
            Label titleLabel = new Label(title);
            Label percentageLabel = new Label(percentage);
            percentageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            box.getChildren().addAll(titleLabel, percentageLabel);
            return box;
        }

        // Line Chart
        private LineChart<Number, Number> createLineChart() {
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Productivity");
            return lineChart;
        }

        // Pie Chart
        private PieChart createPieChart() {
            PieChart pieChart = new PieChart();
            pieChart.getData().addAll(new PieChart.Data("Maths", 60), new PieChart.Data("Physics", 40));
            pieChart.setTitle("Distribution");
            return pieChart;
        }

        // Bar Chart
        private BarChart<String, Number> createBarChart(String title) {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle(title);
            return barChart;
        }



        // Side Buttons
        private Button createSideButton(String text) {
            Button button = new Button(text);
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
            return button;
        }
    }

    public static class MainFrame {

      SceneManager sceneManager;

      public Scene mainFrame(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        BorderPane borderPane = new BorderPane();

        CenterPanelManager centerPanelManager = new CenterPanelManager();
        centerPanelManager.changeCenterPanel("Exam");
        borderPane.setCenter(centerPanelManager.getCenterPane());

        MenuPage menuPage = new MenuPage(centerPanelManager);
        borderPane.setLeft(menuPage.getLeftBoxMenu());

        HBox topPane = new HBox();
        topPane.setPadding(new Insets(0, 0, 50, 212));
        topPane.setStyle("-fx-background-color: #60f7b3; ");
        borderPane.setTop(topPane);

        RightPanel rightPanel = new RightPanel();
        rightPanel.CoursesList();
        borderPane.setRight(rightPanel.getRightPanel());

        Scene scene = new Scene(borderPane,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());

        return scene;
      }
    }
}
