package org.javawavers.studybuddy.ui_ux;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public static class Popupdia extends Application {
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

    /*
        TODO:
         * fix label and textFields alignment
         * fix title of titlePane colour
         * fix titlePane's background color
         * fix general style of okBtn when mouseEntered + MousePressed
         * ημερομηνία εξέτασης/παράδοσης need calendar
         * Add documentation code
        */
    public static class ExamPage {
        //SimulateAnnealing simulateAnnealing = new SimulateAnnealing();
       // Subject subjectGeneral = new Subject(null);
        ArrayList<Subject> subject = new ArrayList<>();
       // ArrayList<Exam> exam = new ArrayList<>();
        private TextField nameField, pageField, revisionField, difficulty, timePer20Slides;
        public static String courseName = "";
        public static int  pages = 0;
        public static String revision = "";
        public static String deadline = "";
        public static String courseType = "";
        public static int diffi = 0;
        public static String time = "";

       // Subject subject1 = new Subject(courseName, difficul);

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
                String value = pageField.getText();
                pages = Integer.parseInt(value);
                revision = revisionField.getText();
                deadline = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
                courseType = typeCourseList.getValue();
               // diffi = difficulty.getText();
                String value2 = difficulty.getText();
                int difficul = Integer.parseInt(value2);
                Subject subject1 = new Subject(courseName, difficul);

                time = timePer20Slides.getText();

                List<String> errors = new ArrayList<>();

                if (courseName.isEmpty()) {
                    errors.add("• Εισήγαγε Μάθημα");
                }

               // if (pages.isEmpty() || !pages.matches("\\d+")) {
                  //  errors.add("• Η παράμετρος 'Σελίδες' πρέπει να είναι ακέραιος αριθμός.");
                //}

                if (revision.isEmpty() || !revision.matches("\\d+")) {
                    errors.add("• Η παράμετρος 'Επανάληψη' πρέπει να είναι ακέραιος αριθμός.");
                }

                if (deadline == null || LocalDate.parse(deadline).isBefore(LocalDate.now())) {
                    errors.add("• Πρέπει να επιλέξεις ημερομηνία εξέτασης μετά τη σημερινή ημερομηνία.");
                }

                if (courseType == null || courseType.isEmpty()) {
                    errors.add("• Πρέπει να επιλέξεις το είδος του μαθήματος.");
                }

               // if (diffi.isEmpty() || !diffi.matches("\\d+") || Integer.parseInt(diffi) < 1 || Integer.parseInt(diffi) > 10) {
                    //errors.add("• Η δυσκολία πρέπει να είναι αριθμός μεταξύ 1 και 10.");
               // }

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
               // difficul = Integer.parseInt(diffi);
                int page = Integer.parseInt(revision);
                //double minutes = Double.parseDouble(pages);
                //Subject subject1 = new Subject(courseName, difficul);
               // subject.add(coursename);

                LocalDate localDeadline = LocalDate.parse(deadline);
                //Dates exam = new Dates(coursename, localDeadline);
                //simulateAnnealing.getExams().add(exam);
                SimulateAnnealing simulateAnnealing = new SimulateAnnealing();

                Exam exam1 = new Exam(localDeadline, page);
                //subjectGeneral.addExam(exam1);
                subject1.addExam(exam1);
                simulateAnnealing.addSubject(subject1);

               /*  simulateAnnealing.scheduleResult();
                schedule = simulateAnnealing.getSchedule();
                System.out.println(schedule);*/
                if (schedule == null) {
                    System.out.println("Schedule is null!");
                } else {
                    System.out.println("Schedule contents: " + Arrays.deepToString(schedule));
                }
                //System.out.println(schedule);
                //System.out.println(schedule);
               // subjectGeneral.getExams(exam1);
               // exam.add(exam1);
                nameField.clear();
                pageField.clear();
                revisionField.clear();
                datePicker.setValue(null);
                typeCourseList.setValue("");
                difficulty.clear();
                timePer20Slides.clear();
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
               // simulateAnnealing.scheduleResult();

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
            timePer20Slides = new TextField();
            box.getChildren().addAll(
                    createLabeledField("Δυσκολία:", difficulty),
                    createLabeledField("Χρόνος ανά 20 διαφάνειες:", timePer20Slides)
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

       // public List<Exam> getExams() {
           // return exam;
       // }
        public int[][] getSchedule() {
            //System.out.println(schedule);
            return schedule;
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
            nextBtn.setOnAction(e -> {
                AssignmentPage assignPage = new AssignmentPage();
                sceneManager.switchScene(assignPage.assignmentStartingPage(sceneManager));
            });
            btnBox.getChildren().add(nextBtn);
            examViewWithBtn.getChildren().add(btnBox);

            Scene scene = new Scene(examViewWithBtn,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());
            return scene;
        }
    }

    public static class Calendar {



        private LocalDate currentWeekStart;
        //αρχικοποιουμε την μεταβλητη count
        int count = 0;


        //αρχικοποιουμε τις λιστες και τα vbox τα οποια χρησιμευουν στην δυναμικη επεξεργασια και εμφανιση των task
        public static List<String> notStartedYet = new ArrayList<>();
        public static List<String> completed = new ArrayList<>();
        private VBox upcomingTasksBox = new VBox(10);
        private VBox completedTasksBox = new VBox(10);





        public Node calendar() {
    // Αρχικοποιουμε ολα τα panel
            VBox centerPanel = createCenterPanel();
            centerPanel.setPadding(new Insets(20));
            return centerPanel;

        }

    //μεθοδος για την δημιουργια του κεντρικου panel
        private VBox createCenterPanel() {
            VBox centerPanel = new VBox(10);
            centerPanel.setPadding(new Insets(20));
            centerPanel.setStyle("-fx-background-color: white;");


    /*Δημιουργουμε ενα hbox για την διαταξη τον στοιχειων σε οριζοντια θεση
     *οριζουμε την θεση του οριζουμε την σημερινη μερα καθως και την εβδομαδα την οποια διανυει ο χρηστης
     *με την datetimeformater οριζουμε τον τροπο με τον οποιο θα εμφανιζεται το weeklabel
     *δημιουργουμε δυο κουμπια τα οποια θα ειναι για την πλοηγηση του χρηστη στις εβδομαδες και να μπορει να δει το προγραμμα του
     */
            HBox weekSwitcher = new HBox(10);
            weekSwitcher.setTranslateY(40);
            weekSwitcher.setAlignment(Pos.CENTER);

            LocalDate today = LocalDate.now();
            currentWeekStart = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

    //αρχικοποιουμε την μεταβλητη weeklabel
            Label weekLabel = new Label(formatWeekLabel(currentWeekStart, formatter));
            weekLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
            weekLabel.setStyle("-fx-text-fill: black;");

            HBox.setHgrow(weekLabel, Priority.ALWAYS);
    //βαζουμε τα κουμπια για να πλοηγειτε ο χρηστης στις εβδομαδες
            Button prevButton = new Button("<");
            prevButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
            prevButton.setPrefSize(30, 30);

            Button nextButton = new Button(">");
            nextButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
            nextButton.setPrefSize(30, 30);


            GridPane calendarGrid = new GridPane();
            calendarGrid.setStyle("-fx-border-color: black;");
            calendarGrid.setGridLinesVisible(true);
    //μεταβλητη count η οποια μολις ο χρηστης παταει το κουμπι που παει τις εβδομαδες μπροστα αυξανεται αλλιως μειωνεται οταν count == 0 τοτε θα εεμφανιζετε το κουμπι today
            prevButton.setOnAction(event -> {
                count = count + 1;
                currentWeekStart = currentWeekStart.minusWeeks(1);
                weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));

            });

            nextButton.setOnAction(event -> {
                count = count - 1;
                currentWeekStart = currentWeekStart.plusWeeks(1);
                weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));

            });


            weekSwitcher.setTranslateY(40);
            weekSwitcher.setAlignment(Pos.CENTER);
            weekSwitcher.getChildren().addAll(prevButton, weekLabel, nextButton);


            Button todayButton = new Button("Today");
            todayButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
            todayButton.setFont(Font.font("System", FontWeight.BOLD, 14));
            todayButton.setTextFill(Color.WHITE);

            if (count == 0) {
                todayButton.setVisible(true);
            } else {
                todayButton.setVisible(false);
            }
            createCalendarGrid(calendarGrid);

    //κουμπι για να βαζει ο χρηστης την διαθεσημοτητα
            Button availabilityButton = new Button("Availiability");
            availabilityButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
            availabilityButton.setFont(Font.font("System", FontWeight.BOLD, 14));
            availabilityButton.setTextFill(Color.WHITE);
            availabilityButton.setPrefWidth(160);

    //οριζουμε οταν ο ζρηστης παταει πανω στο κουμπι να ανοιγει την σελιδα popupdia
            availabilityButton.setOnAction(event ->  {
                Popupdia popup1 = new Popupdia();
                Stage popup1Stage = new Stage();
                popup1.start(popup1Stage);


            });

    //οριζουμε την θεση του κουμπιου availiability
            StackPane availabilityPane = new StackPane(availabilityButton);
            availabilityPane.setPrefSize(150, 30);
            availabilityPane.setLayoutX(centerPanel.getWidth() - 300);
            availabilityPane.setLayoutY(200);

     //κουμπι για Refresh του προογραμματος
            Button refreshButton = new Button();
            refreshButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
            refreshButton.setPrefSize(30, 30);

    //προσθηκη εικονιδιου στο κουμπι για κυκλικα βελη
            SVGPath refreshIcon = new SVGPath();
            refreshIcon.setContent("M12 2V5C7.58 5 4 8.58 4 13C4 15.27 5.05 17.36 6.77 18.63L8.22 17.18C7.04 16.17 6.27 14.67 6.27 13C6.27 9.8 8.8 7.27 12 7.27V10L16 6L12 2ZM18.23 4.37L16.78 5.82C17.96 6.83 18.73 8.33 18.73 10C18.73 13.2 16.2 15.73 13 15.73V12L9 16L13 20V17C17.42 17 21 13.42 21 9C21 6.73 19.95 4.64 18.23 4.37Z");
            refreshIcon.setFill(Color.WHITE);
            refreshButton.setGraphic(refreshIcon);

            refreshButton.setOnAction(event -> {
               // schedule = SimulateAnnealing.p();
                //createCalendarGrid(calendarGrid, besttask, schedule);
            });

    //βαζουμε ολα τα στοιχεια του κεντρου μαζι και τα επιστρεφουμε
            centerPanel.getChildren().addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);


            return centerPanel;
        }

        //δημιουργουμε τα checkbox
        private HBox createCheckBox(String taskName) {
            HBox checkBoxBox = new HBox(10);
            checkBoxBox.setAlignment(Pos.CENTER_LEFT);


            CheckBox taskCheckBox = new CheckBox(taskName);
            taskCheckBox.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

            taskCheckBox.setOnAction(event -> {
                if (taskCheckBox.isSelected()) {
                    if (notStartedYet.contains(taskName)) {
                        notStartedYet.remove(taskName);
                        completed.add(taskName);
                    }
                } else {
                    if (completed.contains(taskName)) {
                        completed.remove(taskName);
                        notStartedYet.add(taskName);
                    }
                }

                updateUpcomingTasks(upcomingTasksBox);
                updateCompletedTasks(completedTasksBox);


                //System.out.println("Completed tasks: " + completed);
            });

            checkBoxBox.getChildren().add(taskCheckBox);

            return checkBoxBox;
        }

    //δημιουργουμε το ημερολογιο
    //δεν υπαρχει schedule
    private void createCalendarGrid(GridPane grid) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    //ρυθμιζουμε το πλατος για τις 7 στηλες
        for (int i = 0; i < days.length; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 7);
            grid.getColumnConstraints().add(column);
        }

    //βαζουμε τους τιτλους για την καθε μερα
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
            dayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            dayLabel.setPrefHeight(60);
            dayLabel.setPrefWidth(140);
            GridPane.setConstraints(dayLabel, i, 0);
            grid.getChildren().add(dayLabel);
        }

    //να εχουν ολες γραμμες το ιδιο υψος
        for (int i = 0; i < 11; i++) {  // 11 γραμμες για τον πινακα
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 11);
            grid.getRowConstraints().add(row);
        }


        for (int row = 1; row <= 10; row++) {
            for (int col = 0; col < days.length; col++) {
                Label cell = new Label();
                cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
                cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
                cell.setPrefSize(140, 60);
                //cell.setText(SimulateAnnealing.printSchedule(row, col));
              //  MainTestAlgorithm algorithm = new MainTestAlgorithm();
                //cell.setText(algorithm.run(row, col));


    //οταν ο χρηστης παταει πανω σε οποιδηποτε κελη τοτε του εμφανιζεται η σελιδα popupdiathesimotita
                cell.setOnMouseClicked(event -> {

                    Popupdiathesimotita popup = new Popupdiathesimotita();
                    Stage popupStage = new Stage();
                    popup.start(popupStage);
                });

                GridPane.setConstraints(cell, col, row);
                grid.getChildren().add(cell);
            }
        }
    }
    //οποτε καλειτε ανανεωνονται αναλογα με εκεινα τα δεδομενα το popup που εμφανιζετε
        private void updateUpcomingTasks(VBox upcomingTasksBox) {
            upcomingTasksBox.getChildren().clear();
            for (String taskDescription : notStartedYet) {
                //System.out.println("Add" + taskDescription);
                HBox checkBoxBox = createCheckBox(taskDescription);
                upcomingTasksBox.getChildren().add(checkBoxBox);
            }
        }


        private void updateCompletedTasks(VBox completedTasksBox) {
            completedTasksBox.getChildren().clear();
            for (String taskDescription : completed) {
                HBox checkBoxBox = createCheckBox(taskDescription);
                //System.out.println("completed" + taskDescription);
                completedTasksBox.getChildren().add(checkBoxBox);
            }
        }

        private String formatWeekLabel(LocalDate weekStart, DateTimeFormatter formatter) {
            LocalDate weekEnd = weekStart.plusDays(6);
            return String.format("%s - %s", formatter.format(weekStart), formatter.format(weekEnd));
        }
    //δημιουργια του pop up το οποιο ανοιγει οταν ο χρηστης πατησει οποιοδηποτε απο τα κουμπια ypcomingtasks/completedtasks
        private void showTasksPopup(String title, List<String> taskList) {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle(title);

            VBox popupContent = new VBox(10);
            popupContent.setPadding(new Insets(10));
            popupContent.setAlignment(Pos.TOP_CENTER);
    //οριζουμε τον τιτλο αναλογα με το κουμπι  που εχει πατηθει
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    //δημιουργουμε taskbox για τα task
            VBox tasksBox = new VBox(5);
            tasksBox.setAlignment(Pos.TOP_LEFT);
            tasksBox.setStyle("-fx-max-height: 300px;");

    //δημιουργουμε scrollpane για να μπορει ο χρηστης να κανει scroll και να δει ολα τα task τα οποια εχει να κανει εκεινη την εβδομαδα
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(tasksBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(300);

            Map<CheckBox, String> taskCheckBoxMap = new HashMap<>();

    //δημιουργουμε τα checkbox
            if (taskList != null && !taskList.isEmpty()) {
                for (String task : taskList) {
                    CheckBox checkBox = new CheckBox(task);
                    checkBox.setStyle("-fx-font-size: 14px;");

    //αν το task ειναι στην λιστα notstartedyet ειναι unselected αλλιως στην completed τα task ειναι selected
                    if (taskList == notStartedYet && !completed.contains(task)) {
                        checkBox.setSelected(false);
                    } else if (taskList == completed && notStartedYet.contains(task)) {
                        checkBox.setSelected(true);
                    }

                    tasksBox.getChildren().add(checkBox);
                    taskCheckBoxMap.put(checkBox, task);//map για να ελενγχουμε τα task με το checkbox
                }
            } else {
                Label noTasksLabel = new Label("No tasks available");//στην περιπτωση που δεν υπαρχουν task
                tasksBox.getChildren().add(noTasksLabel);
            }
    //δημιουργια κουμπιου οκ που οταν πατηθει αναλογα με το τι εχει πατησει ο χρηστης ενημερωνει τις δυο λιστες
            Button okButton = new Button("OK");
            okButton.setStyle("-fx-background-color: #50D1C6; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 16px;");
            okButton.setOnAction(event -> {

                for (Map.Entry<CheckBox, String> entry : taskCheckBoxMap.entrySet()) {
                    CheckBox checkBox = entry.getKey();
                    String task = entry.getValue();

                    if (checkBox.isSelected() && taskList == notStartedYet) {
                        notStartedYet.remove(task);
                        completed.add(task);
                    } else if (!checkBox.isSelected() && taskList == completed) {
                        completed.remove(task);
                        notStartedYet.add(task);
                    }
                }
                popupStage.close();
    //ενημερωνουμε τα taskboxes
                updateUpcomingTasks(upcomingTasksBox);
                updateCompletedTasks(completedTasksBox);
            });

            popupContent.getChildren().addAll(titleLabel, scrollPane, okButton);

            Scene popupScene = new Scene(popupContent, 300, 400);
            popupStage.setScene(popupScene);
            popupStage.showAndWait();
        }


        private Button createCircularButton(String text, String color) {
            Button button = new Button(text);
            button.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: black; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 5px; " +
                "-fx-border-color: black; " +
                "-fx-border-radius: 5px; " +
                "-fx-min-width: 200px;"
            );
            return button;
        }
    //αρχικοποιησει των λιστων
        private void initializeTaskLists(List<Task> besttask) {
            for (Task task : besttask) {
                notStartedYet.add(task.toString());
            }

            completed = new ArrayList<>();

            updateUpcomingTasks(upcomingTasksBox);
            updateCompletedTasks(completedTasksBox);
        }

        public List<int[][]> splitSchedule(int[][] schedule, int daysinWeek) {
            List<int[][]> weekTasks = new ArrayList<>();
            int totalrow = schedule.length;
            int totalcol = schedule[0].length;
            int weeksCount = (int) Math.ceil((double) totalcol / totalrow);

            for (int week = 0; week < weeksCount; week++) {
                //δημιουργουμε πινακα για την εβδομαδα
                int[][] scheduleWeek = new int[totalrow][daysinWeek];
                for (int row = 0; row < totalrow; row++){
                    for (int col = 0; col < totalcol; col++) {
                        int indexcol = week * daysinWeek + col;
                        if (indexcol < totalcol) {
                            scheduleWeek[row][col] = schedule[row][indexcol];
                        } else {
                            scheduleWeek[row][col] = 0;
                        }
                    }
                }
                weekTasks.add(scheduleWeek);
            }
            return weekTasks;
        }
    }

    public static class RegisterPage {

        public static String storedEmail = "";
        public static String storedPassword = "";
        public static String storedUsername = "Guest";

        private HBox rightPane;
        private HBox leftPane;
        private TextField emailField;
        private PasswordField passwordField;
        private TextField textField;
        private Button loginButton;
        private Button registerButton;
        private TextField nameField;
        private TextField confirmPasswordTextField;
        private PasswordField confirmPasswordField;
        private SceneManager sceneManager;

        public Scene register(SceneManager sceneManager) {
            HBox registerPage = new HBox();
            this.sceneManager = sceneManager;

            initRightPane();
            initLeftPane();
            setupEventHandlers();

            registerPage.getChildren().addAll(leftPane, rightPane);
            HBox.setHgrow(leftPane, Priority.ALWAYS);
            HBox.setHgrow(rightPane, Priority.ALWAYS);
            leftPane.setMinWidth(300);
            rightPane.setMinWidth(300);
            leftPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
            rightPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);

            Scene scene = new Scene(registerPage,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());

            return scene;
        }

        private void initRightPane() {
            rightPane = new HBox();
            rightPane.setStyle("-fx-background-color: #B563F1;");
            rightPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

            VBox rightVBox = new VBox(15);
            rightVBox.setAlignment(Pos.CENTER);
            rightVBox.setPadding(new Insets(30));
            rightVBox.setFillWidth(true);
            rightVBox.setMaxHeight(Double.MAX_VALUE);

            Text joinText = new Text("Έλα στην παρέα μας");
            joinText.setFont(new Font("System Bold", 28));

            Label nameLabel = new Label("Name:");
            nameLabel.setFont(new Font("System Bold", 14));

            nameField = new TextField();
            nameField.setPromptText("Enter your name");

            Label emailLabel = new Label("Email:");
            emailLabel.setFont(new Font("System Bold", 14));

            emailField = new TextField();
            emailField.setPromptText("Enter your email");

            Label passwordLabel = new Label("Password:");
            passwordLabel.setFont(new Font("System Bold", 14));

            passwordField = new PasswordField();
            passwordField.setPromptText("Enter your password");

            textField = new TextField();
            textField.setPromptText("Enter your password");

            textField.setManaged(false);//να ξεκινησει και να μην φαινεται ο κωδικος
            textField.setVisible(false);

            Button toggleButton = new Button("👁");
            toggleButton.setStyle("-fx-font-size: 14px;");

            toggleButton.setOnAction(e -> { PasswordVisibility();
            });

    //τα τοποθετουμε και τα τρια σε εna hbox
            HBox passwordBox = new HBox(5, passwordField, textField, toggleButton);

    //κανουμε το ιδιο για το confirmpassword
            Label confirmpasswordLabel = new Label("Confirm Password:");
            confirmpasswordLabel.setFont(new Font("System Bold", 14));

            confirmPasswordTextField =new TextField();
            confirmPasswordTextField.setPromptText("Confirm your password");

            confirmPasswordTextField.setManaged(false);
            confirmPasswordTextField.setVisible(false);

            confirmPasswordField = new PasswordField();
            confirmPasswordField.setPromptText("Confirm your password");

            Button toggleConfirmPasswordButton = new Button("👁");
            toggleConfirmPasswordButton.setStyle("-fx-font-size: 14px;");

            toggleConfirmPasswordButton.setOnAction(e -> {
                ConfPasswordVisibility();
            });

            HBox confirmPasswordBox = new HBox(5, confirmPasswordField, confirmPasswordTextField, toggleConfirmPasswordButton);

    //κουμπι για την εγγραφη
            registerButton = new Button("Εγγραφή");
            registerButton.setFont(new Font("System Bold", 14));


            rightPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                joinText.setFont(Font.font("Arial",FontWeight.BOLD, newSize));
                nameField.setFont(Font.font("Arial", newSize));
                nameLabel.setFont(Font.font("Arial", newSize));
                emailField.setFont(Font.font("Arial", newSize));
                emailLabel.setFont(Font.font("Arial", newSize));
                passwordField.setFont(Font.font("Arial",  newSize));
                passwordLabel.setFont(Font.font("Arial", newSize));
                textField.setFont(Font.font("Arial", newSize));
                toggleButton.setFont(Font.font("Arial", newSize));
                confirmPasswordField.setFont(Font.font("Arial", newSize));
                confirmpasswordLabel.setFont(Font.font("Arial", newSize));
                registerButton.setStyle("-fx-font-family: 'System';  " +
                "-fx-font-size: " + newSize + "px; " +
                "-fx-font-weight: bold; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: rgba(14, 164, 43, 0.81); " +
                "-fx-background-radius: 30px; " +
                "-fx-font-size: " + newSize + "px; " +
                "-fx-border-radius: 30px;" +
                " -fx-border-color: black;");
            });

            rightPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                nameField.setFont(Font.font("Arial", newSize));
                nameLabel.setFont(Font.font("Arial", newSize));
                emailField.setFont(Font.font("Arial", newSize));
                emailLabel.setFont(Font.font("Arial", newSize));
                passwordField.setFont(Font.font("Arial", newSize));
                passwordLabel.setFont(Font.font("Arial", newSize));
                textField.setFont(Font.font("Arial", newSize));
                toggleButton.setFont(Font.font("Arial", newSize));
                confirmPasswordField.setFont(Font.font("Arial", newSize));
                confirmpasswordLabel.setFont(Font.font("Arial", newSize));
                registerButton.setStyle("-fx-font-family: 'System'; " +
                    "-fx-font-size: " + newSize + "px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: rgba(14, 164, 43, 0.81); " +
                    "-fx-background-radius: 30px; " +
                    "-fx-border-radius: 30px; " +
                    "-fx-border-color: black;");
            });

            Region upSpacer = new Region();
            Region downSpacer = new Region();
            VBox.setVgrow(upSpacer, Priority.ALWAYS);
            VBox.setVgrow(downSpacer, Priority.ALWAYS);
            rightVBox.getChildren().addAll(upSpacer, joinText, nameLabel, nameField, emailLabel, emailField, passwordLabel,
            passwordBox, confirmpasswordLabel, confirmPasswordBox, registerButton, downSpacer);

            Region leftSpacer = new Region();
            Region rightSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);
            HBox.setHgrow(rightSpacer, Priority.ALWAYS);
            VBox.setVgrow(rightVBox, Priority.ALWAYS);

            rightPane.getChildren().addAll(leftSpacer, rightVBox, rightSpacer);
        }

        private void initLeftPane() {
            leftPane = new HBox();
            leftPane.setStyle("-fx-background-color: #65E165CF;");
            leftPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

            VBox leftVBox = new VBox(15);
            leftVBox.setAlignment(Pos.CENTER);
            leftVBox.setPadding(new Insets(30));
            leftVBox.setFillWidth(true);
            leftVBox.setMaxHeight(Double.MAX_VALUE);

            Text welcomeText = new Text("Καλώς ήρθες ξανά!");
            welcomeText.setFont(new Font("System Bold", 28));

            Text messageText1 = new Text("Ας οργανώσουμε ξανά μαζί");
            messageText1.setFont(new Font(14));

            Text messageText2 = new Text("το χρόνο σου.");
            messageText2.setFont(new Font(14));

    //κουμπι logon
            loginButton = new Button("Συνδέσου εδώ");
            loginButton.setFont(new Font("System Bold", 14));
            loginButton.setStyle("-fx-background-color: #801EC8E6; -fx-background-radius: 30px; "
                    + "-fx-border-radius: 30px; -fx-border-color: black;");

            leftPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                messageText1.setFont(Font.font("Arial", newSize));
                messageText2.setFont(Font.font("Arial", newSize));
                loginButton.setStyle("-fx-background-color: #801EC8E6; " +
                "-fx-background-radius: 30px; " +
                "-fx-border-radius: 30px; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: black;" +
                "-fx-font-family: 'System'; " +
                "-fx-font-size: " + newSize + "px; " +
                "-fx-font-weight: bold; "
                );
            });

            leftPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                messageText1.setFont(Font.font("Arial", newSize));
                messageText2.setFont(Font.font("Arial", newSize));
                loginButton.setStyle("-fx-background-color: #801EC8E6; " +
                "-fx-background-radius: 30px; " +
                "-fx-border-radius: 30px; "+
                        "-fx-text-fill: white; " +
                        "-fx-border-color: black;" +
                "-fx-font-family: 'System'; " +
                "-fx-font-size: " + newSize + "px; " +
                "-fx-font-weight: bold; "
                );
            });

            Region upSpacer = new Region();
            Region downSpacer = new Region();
            VBox.setVgrow(upSpacer, Priority.ALWAYS);
            VBox.setVgrow(downSpacer, Priority.ALWAYS);
            leftVBox.getChildren().addAll(upSpacer, welcomeText, messageText1, messageText2, loginButton, downSpacer);

            Region leftSpacer = new Region();
            Region rightSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);
            HBox.setHgrow(rightSpacer, Priority.ALWAYS);

            leftPane.getChildren().addAll(leftSpacer, leftVBox, rightSpacer);
        }

        private void setupEventHandlers() {
            loginButton.setOnAction(event -> {
                LoginPage loginPage = new LoginPage();
                sceneManager.switchScene(loginPage.login(sceneManager));
            });

             registerButton.setOnAction(event -> {
                validateLogin();
                if(validateLogin()){
                    ExamPage examPage = new ExamPage();
                    sceneManager.switchScene(examPage.examStartingPage(sceneManager));
                }
            });
        }

        private boolean validateLogin() {
                storedUsername = nameField.getText();
                storedEmail = emailField.getText();
                storedPassword = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
            List<String> errors = new ArrayList<>();
    //error αν το ονομα ειναι λιγοτερο απο 4 χαρακτηρες
                if (storedUsername.isEmpty() || storedUsername.length() <= 4) {
                    errors.add("• Το όνομα πρέπει να έχει πάνω από 4 χαρακτήρες");
                    return false;
                }
    //error αν το email δεν περιεχει το @
                if (storedEmail.isEmpty() || !storedEmail.contains("@")) {
                    errors.add("• Εισήγαγε ένα έγκυρο email");
                    return false;
                }
    //error αν ο κωδικος ειναι μικροτερος απο 6 χαρακτηρες
                if (storedPassword.isEmpty() || storedPassword.length() < 6) {
                    errors.add("• Ο κωδικός πρόσβασης πρέπει να έχει πάνω από 6 χαρακτήρες");
                    return false;
                }
    //error αν ο κωδικος και ο κωδικος επιβεβαιωσης δεν ειναι ιδιος
                if (!storedPassword.equals(confirmPassword)) {
                    errors.add("• Οι κωδικοί που έβαλες δεν είναι ίδιοι");
                    return false;
                }
    //αν υπαρχουν error εμφανιζει την λισατ στον χρηστη
                if (!errors.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                    alert.setHeaderText(null);
                    String errorMessage = String.join("\n", errors);
                    alert.setContentText(errorMessage);
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                    alert.showAndWait();
                    return false;
                }
    //μηνυμα επιτυχιας αν δεν υπαρχουν errors
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Εγγραφή Επιτυχής");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Η εγγραφή ολοκληρώθηκε με επιτυχία!");
                DialogPane dialogPane = successAlert.getDialogPane();
                dialogPane.getStyleClass().add("success-alert");
                dialogPane.getStylesheets().add(getClass().getResource("success.css").toExternalForm());
                successAlert.showAndWait();
                return true;

        }

        private void clearFields() {
            nameField.clear();
            emailField.clear();
            passwordField.clear();
            textField.clear();
            confirmPasswordField.clear();
        }

        private void PasswordVisibility() {
            if (passwordField.isVisible()) {
                textField.setText(passwordField.getText());
                passwordField.setVisible(false);
                textField.setVisible(true);
                passwordField.setManaged(false);
                textField.setManaged(true);
            } else {
                passwordField.setText(textField.getText());
                textField.setVisible(false);
                passwordField.setVisible(true);
                textField.setManaged(false);
                passwordField.setManaged(true);
            }
        }
        private void ConfPasswordVisibility() {
            if (confirmPasswordField.isVisible()) {
                confirmPasswordTextField.setText(confirmPasswordField.getText());
                confirmPasswordField.setVisible(false);
                confirmPasswordField.setManaged(false);
                confirmPasswordTextField.setVisible(true);
                confirmPasswordTextField.setManaged(true);
            } else {
                confirmPasswordField.setText(confirmPasswordTextField.getText());
                confirmPasswordTextField.setVisible(false);
                confirmPasswordTextField.setManaged(false);
                confirmPasswordField.setVisible(true);
                confirmPasswordField.setManaged(true);
            }
        }
    }

    public static class LoginPage {


        private HBox rightPane;
        private HBox leftPane;
        private TextField emailField;
        private PasswordField passwordField;
        private TextField textField;
        private Button loginButton;
        private Button signupButton;
        private SceneManager sceneManager;

        public Scene login(SceneManager sceneManager) {
            HBox loginPage = new HBox();
            this.sceneManager = sceneManager;

            initRightPane();
            initLeftPane();
            setupEventHandlers();

            loginPage.getChildren().addAll(leftPane, rightPane);
            HBox.setHgrow(leftPane, Priority.ALWAYS);
            HBox.setHgrow(rightPane, Priority.ALWAYS);
            leftPane.setMinWidth(300);
            rightPane.setMinWidth(300);
            leftPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
            rightPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);

            Scene scene = new Scene(loginPage,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());

            return scene;
        }

        private void initRightPane() {
            rightPane = new HBox();
            rightPane.setStyle("-fx-background-color: #65E165CF;");
            rightPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

            VBox rightVBox = new VBox(15);
            rightVBox.setAlignment(Pos.CENTER);
            rightVBox.setPadding(new Insets(30));
            rightVBox.setFillWidth(true);
            rightVBox.setMaxHeight(Double.MAX_VALUE);

            Text welcomeText = new Text("Καλώς ήρθες!");
            welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 60));
            welcomeText.setStyle("-fx-fill: black;");

            Text messageLine1 = new Text("Έτοιμος να κάνεις το διάβασμα σου πιο ");
            messageLine1.setFont(Font.font(14));

            Text messageLine2 = new Text("έξυπνο και αποτελεσματικό;");
            messageLine2.setFont(Font.font("Arial", 14));

            signupButton = new Button("Εγγραφή εδώ");
            signupButton.setStyle("-fx-font-family: 'System'; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #801EC8E6; " +
                "-fx-background-radius: 30px; " +
                "-fx-border-radius: 30px; " +
                "-fx-border-color: black;");

            rightPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                messageLine2.setFont(Font.font("Arial", newSize));
                messageLine1.setFont(Font.font("Arial", newSize));
                welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                signupButton.setStyle("-fx-font-family: 'System'; " +
                    "-fx-font-size: " + newSize + "px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: #801EC8E6; " +
                    "-fx-background-radius: 30px; " +
                    "-fx-border-radius: 30px; " +
                    "-fx-border-color: black;");
            });

            rightPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                messageLine2.setFont(Font.font("Arial", newSize));
                messageLine1.setFont(Font.font("Arial", newSize));
                welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                signupButton.setStyle("-fx-font-family: 'System'; " +
                    "-fx-font-size: " + newSize + "px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: #801EC8E6; " +
                    "-fx-background-radius: 30px; " +
                    "-fx-border-radius: 30px; " +
                    "-fx-border-color: black;");
            });


            Region upSpacer = new Region();
            Region downSpacer = new Region();
            VBox.setVgrow(upSpacer, Priority.ALWAYS);
            VBox.setVgrow(downSpacer, Priority.ALWAYS);
            rightVBox.getChildren().addAll(upSpacer, welcomeText, messageLine1, messageLine2, signupButton, downSpacer);


            Region leftSpacer = new Region();
            Region rightSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);
            HBox.setHgrow(rightSpacer, Priority.ALWAYS);
            VBox.setVgrow(rightVBox, Priority.ALWAYS);


            rightPane.getChildren().addAll(leftSpacer, rightVBox, rightSpacer);
        }

        private void initLeftPane() {
            leftPane = new HBox();
            leftPane.setStyle("-fx-background-color: #B563F1;");
            leftPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

            VBox leftVBox = new VBox(15);
            leftVBox.setAlignment(Pos.CENTER);
            leftVBox.setPadding(new Insets(30));
            leftVBox.setFillWidth(true);
            leftVBox.setMaxHeight(Double.MAX_VALUE);

            Text joinText = new Text("Συνδέσου ξανά!");
            joinText.setFont(Font.font("Arial", FontWeight.BOLD, 60));

            Label emailLabel = new Label("Email:");
            emailLabel.setFont(new Font( 14));

            emailField = new TextField();
            emailField.setPromptText("Enter your email");

            Label passwordLabel = new Label("Password:");
            passwordLabel.setFont(new Font( 14));

            passwordField = new PasswordField();
            passwordField.setPromptText("Enter your password");

            textField = new TextField();
            textField.setManaged(false);
            textField.setVisible(false);

            Button toggleButton = new Button("👁");
            toggleButton.setStyle("-fx-font-size: 14px;");
            toggleButton.setOnAction(e -> PasswordVisibility());

            HBox passwordBox = new HBox(10, passwordField, textField, toggleButton);

            loginButton = new Button("Συνδέσου");
            loginButton.setStyle("-fx-font-family: 'System'; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color:  rgba(14, 164, 43, 0.81); " +
                "-fx-background-radius: 30px; " +
                "-fx-border-radius: 30px; " +
                "-fx-border-color: black;");

            leftPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                emailLabel.setFont(Font.font("Arial",  newSize));
                emailField.setStyle("-fx-font-size: " + newSize + "px;");
                passwordLabel.setFont(Font.font("Arial", newSize));
                passwordField.setStyle("-fx-font-size: " + newSize + "px;");
                passwordField.setFont(Font.font("Arial", newSize));
                textField.setFont(Font.font("Arial", newSize));
                toggleButton.setStyle("-fx-font-size: " + newSize + "px;");
                loginButton.setStyle("-fx-font-family: 'System'; " +
                    "-fx-font-size: " + newSize + "px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-color:  rgba(14, 164, 43, 0.81); " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 30px; " +
                    "-fx-border-radius: 30px; " +
                    "-fx-border-color: black;");
            });

            leftPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                double newSize = newVal.doubleValue() / 30;
                joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
                emailLabel.setFont(Font.font("Arial",  newSize));
                emailField.setStyle("-fx-font-size: " + newSize + "px;");
                passwordLabel.setFont(Font.font("Arial", newSize));
                passwordField.setStyle("-fx-font-size: " + newSize + "px;");
                passwordField.setFont(Font.font("Arial", newSize));
                textField.setFont(Font.font("Arial", newSize));
                toggleButton.setStyle("-fx-font-size: " + newSize + "px;");
                loginButton.setStyle("-fx-font-family: 'System'; " +
                    "-fx-font-size: " + newSize + "px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color:  rgba(14, 164, 43, 0.81); " +
                    "-fx-background-radius: 30px; " +
                    "-fx-border-radius: 30px; " +
                    "-fx-border-color: black;");
            });

            Region upSpacer = new Region();
            Region downSpacer = new Region();
            VBox.setVgrow(upSpacer, Priority.ALWAYS);
            VBox.setVgrow(downSpacer, Priority.ALWAYS);
            leftVBox.getChildren().addAll(upSpacer, joinText, emailLabel, emailField, passwordLabel, passwordBox, loginButton, downSpacer);


            Region leftSpacer = new Region();
            Region rightSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);
            HBox.setHgrow(rightSpacer, Priority.ALWAYS);

            leftPane.getChildren().addAll(leftSpacer, leftVBox, rightSpacer);
        }

        private void setupEventHandlers() {
            signupButton.setOnAction(event -> {
                RegisterPage registerPage = new RegisterPage();
                sceneManager.switchScene(registerPage.register(sceneManager));
            });

            loginButton.setOnAction(event -> {
                validateLogin();
                if(validateLogin()) {
                    MainFrame mainFrame = new MainFrame();
                    sceneManager.switchScene(mainFrame.mainFrame(sceneManager));
                }
            });
        }

        private boolean validateLogin() {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                alert.setHeaderText(null);
                alert.setContentText("Ωπ, κάτι ξέχασες! Ρίξε μια ματιά και συμπλήρωσε όλα τα απαραίτητα πεδία.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.getDialogPane().setMinWidth(500);
                alert.getDialogPane().setMinHeight(300);
                alert.showAndWait();

                return false;
            } else if (email.equals(RegisterPage.storedEmail) && password.equals(RegisterPage.storedPassword)) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Σύνδεση Επιτυχής");
                alert.setHeaderText(null);
                alert.setContentText("Καλώς ήρθες!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("success-alert");
                dialogPane.getStylesheets().add(getClass().getResource("success.css").toExternalForm());

                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Σφάλμα σύνδεσης");
                alert.setHeaderText(null);
                alert.setContentText("Λάθος email ή κωδικός πρόσβασης.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.getDialogPane().setMinWidth(500);
                alert.getDialogPane().setMinHeight(300);
                alert.showAndWait();
                return false;
            }

        }

        private void clearFields() {
            passwordField.clear();
            textField.clear();
            emailField.clear();
        }

        private void PasswordVisibility() {
            if (passwordField.isVisible()) {
                textField.setText(passwordField.getText());
                passwordField.setVisible(false);
                textField.setVisible(true);
                passwordField.setManaged(false);
                textField.setManaged(true);
            } else {
                passwordField.setText(textField.getText());
                textField.setVisible(false);
                passwordField.setVisible(true);
                textField.setManaged(false);
                passwordField.setManaged(true);
            }
        }
    }

    public static class Popupdiathesimotita extends Application {

        private List<String> notStartedYet;
        private List<String> completed;

        public void setTaskLists(List<String> notStartedYet, List<String> completed) {
            this.notStartedYet = notStartedYet;
            this.completed = completed;
        }

        public static boolean isFinishedChecked = false;
        private String taskDescription = "κενο";
        private LocalDate examDate;
        private long daysLeft = -1;
        private String exam;

        public void setTaskDescription(String description, LocalDate examDate) {
            this.taskDescription = description;
            this.examDate = examDate;
        }

        @Override
        public void start(Stage primaryStage) {
            if (examDate != null) {
                this.daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), examDate);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
                this.exam = examDate.format(dateFormatter);
            } else {
                this.daysLeft = -1;
                this.exam = "never";
            }

            Pane root = new Pane();
            root.setStyle("-fx-background-radius: 20px; -fx-background-color: white;");
            root.setPrefSize(241, 280);

            Label lessonLabel = new Label("ΜΑΘΗΜΑ");
            lessonLabel.setFont(Font.font("System Bold", 18));
            lessonLabel.setLayoutX(14);
            lessonLabel.setLayoutY(14);
            root.getChildren().add(lessonLabel);

            Pane duePane = new Pane();
            duePane.setStyle("-fx-background-color: #D3D3D3;");
            duePane.setLayoutX(14);
            duePane.setLayoutY(52);
            duePane.setPrefSize(80, 23);

            Label dueLabel = new Label("Due:");
            dueLabel.setFont(Font.font(14));
            dueLabel.setLayoutX(2);
            dueLabel.setLayoutY(2);

            Label dateLabel = new Label(exam);
            dateLabel.setFont(Font.font(14));
            dateLabel.setLayoutX(34);
            dateLabel.setLayoutY(2);

            duePane.getChildren().addAll(dueLabel, dateLabel);
            root.getChildren().add(duePane);

            Pane remainingPane = new Pane();
            remainingPane.setStyle("-fx-background-color: #D3D3D3;");
            remainingPane.setLayoutX(105);
            remainingPane.setLayoutY(52);
            remainingPane.setPrefSize(98, 23);


            Label remainingLabel = new Label(daysLeft + " days left");
            remainingLabel.setFont(Font.font(14));
            remainingLabel.setLayoutX(10);
            remainingLabel.setLayoutY(1);

            remainingPane.getChildren().add(remainingLabel);
            root.getChildren().add(remainingPane);

            Label descriptionLabel = new Label(taskDescription);
            descriptionLabel.setStyle("-fx-border-color: black;");
            descriptionLabel.setLayoutX(26);
            descriptionLabel.setLayoutY(91);
            descriptionLabel.setPrefSize(189, 124);
            root.getChildren().add(descriptionLabel);

            CheckBox finishedCheckBox = new CheckBox("Finished");
            finishedCheckBox.setFont(Font.font("System Bold", 14));
            finishedCheckBox.setLayoutX(26);
            finishedCheckBox.setLayoutY(218);
            finishedCheckBox.setStyle("-fx-background-color: #15B569; -fx-background-radius: 20px;");
            root.getChildren().add(finishedCheckBox);

            Pane inPane = new Pane();
            inPane.setStyle("-fx-background-radius: 30px; -fx-background-color: #FFC23D;");
            inPane.setLayoutX(129);
            inPane.setLayoutY(218);
            inPane.setPrefSize(98, 30);

            Label inLabel = new Label("In : (minutes)");
            inLabel.setFont(Font.font("System Bold", 14));
            inLabel.setLayoutX(8);
            inLabel.setLayoutY(5);

            inPane.getChildren().add(inLabel);
            root.getChildren().add(inPane);

    //προσθηκη κουμπιου (οκ) για να κλεινει το παραθυρο
            Button okButton = new Button("OK");
            okButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 14px;");
            okButton.setLayoutX(80);
            okButton.setLayoutY(248);
            okButton.setPrefSize(70, 25);
            root.getChildren().add(okButton);

            okButton.setOnAction(event -> {
                //οταν ο χρηστης επιλεγει το οκ ελενγουμε αν το finished ειναι επιλεγμενο
                isFinishedChecked = finishedCheckBox.isSelected();

                //αν ειναι βαζουμε το συγκεκριμενο task στην λιστα complete και το αφαιρουμε απο την λιστα notstartedyet
                if (isFinishedChecked) {
                    if (notStartedYet != null && notStartedYet.contains(taskDescription)) {
                        notStartedYet.remove(taskDescription);
                        completed.add(taskDescription);
                    }
                }

                primaryStage.close();
            });



            Scene scene = new Scene(root);
            primaryStage.setTitle("Task Layout");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

    public static class RightPanel {

      private StackPane rightPanel;

      public RightPanel() {
        rightPanel = new StackPane();
        rightPanel.setPrefWidth(212);
        rightPanel.setMinWidth(212); //or 88.33
        rightPanel.setMaxWidth(212);
        rightPanel.setMaxWidth(Double.MAX_VALUE);
        rightPanel.setMaxHeight(Double.MAX_VALUE);
      }

      public void CoursesList() {
        VBox examList = new VBox(5);
        VBox assignmentLst = new VBox(5);

        examList.setStyle("-fx-background-color: #15B569;");
        assignmentLst.setStyle("-fx-background-color: #FFC23D;");

        rightPanel.getChildren().addAll(examList, assignmentLst);
      }

      public StackPane getRightPanel(){
        return rightPanel;
      }
    }

    public static class SceneManager {

      private Stage stage;

      public SceneManager(Stage stage) {
        this.stage = stage;
      }

      public void switchScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
      }

    }

    public static class MenuPage {
        private VBox leftBoxMenu = new VBox(15);
        private ToggleButton btnHome =new ToggleButton("Home");
        private ToggleButton btnExam = new ToggleButton("Exam");
        private ToggleButton btnAssignment = new ToggleButton("Assignments");
        private ToggleButton btnCalendar = new ToggleButton("Calendar");
        private ToggleButton btnDashboard = new ToggleButton("Dashboard");
        private ToggleButton btnCourses = new ToggleButton("Courses");

        private CenterPanelManager centerPanelManager;

        private ImageView arrowIconCourses;
        private Image arrowRight;
        private Image arrowDown;

        private String btnStyle;
        private String btnInsideStyle;
        private String btnSelected;
        private String btnInsideSelected;
        VBox optionVBox = new VBox(15);

        public MenuPage(CenterPanelManager centerPanelManager) {
            this.centerPanelManager = centerPanelManager;
            initVariables();
        }
        private void initVariables() {

            configMenu();
            initToggleGroup();
            configBtnStyles();
            setButtonSelectedStyles();
            configUserImgBtn();
            configNavigationBtns();
            configCourses();
            configLogo();

        }
        private void configMenu() {
            leftBoxMenu.setPrefWidth(212);
            leftBoxMenu.setMinWidth(212); //or 88.33
            leftBoxMenu.setMaxWidth(212);
            leftBoxMenu.setStyle(/*"-fx-padding: 68 0 0 0;"
                    + */"-fx-background-color: #F7B267; ");

            leftBoxMenu.setMaxHeight(Double.MAX_VALUE);
        }

        private void initToggleGroup() {
            ToggleGroup btnGroup = new ToggleGroup();
            btnHome.setToggleGroup(btnGroup);
            btnCourses.setToggleGroup(btnGroup);
            btnExam.setToggleGroup(btnGroup);
            btnAssignment.setToggleGroup(btnGroup);
            btnCalendar.setToggleGroup(btnGroup);
            btnDashboard.setToggleGroup(btnGroup);
        }

        private void configBtnStyles() {
            setButtonSelectedStyles();

            btnStyle = "-fx-border-color: #F7B267; "
                + "-fx-background-color: #F7B267; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px; ";

            btnInsideStyle = "-fx-border-color: #F7B267; "
                + "-fx-background-color: #F7B267; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px 20px; ";

            btnHome.setStyle(btnStyle);
            btnExam.setStyle(btnInsideStyle);
            btnAssignment.setStyle(btnInsideStyle);
            btnCalendar.setStyle(btnStyle);
            btnDashboard.setStyle(btnStyle);
            btnCourses.setStyle(btnStyle);


        }

        private void setButtonSelectedStyles() {
            //Button Styles


            btnSelected = "-fx-border-color: #F9C288; "
                + "-fx-background-color: #F9C288; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px; ";

            btnInsideSelected = "-fx-border-color: #F9C288; "
                + "-fx-background-color: #F9C288; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px 20px; ";

            //Change Button Colors When Selected
            btnHome.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnHome.setStyle(isSelected ? btnSelected : btnStyle);
            });

            btnCourses.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnCourses.setStyle(isSelected ? btnSelected : btnStyle);
            });

            btnCalendar.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnCalendar.setStyle(isSelected ? btnSelected : btnStyle);
            });

            btnDashboard.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnDashboard.setStyle(isSelected ? btnSelected : btnStyle);
            });

            btnExam.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnExam.setStyle(isSelected ? btnInsideSelected : btnInsideStyle);
            });

            btnAssignment.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                btnAssignment.setStyle(isSelected ? btnInsideSelected : btnInsideStyle);
            });
        }

        private void configUserImgBtn() {
            //User Image
            try {
                Image userImg = new Image(getClass().getResource("/user.png").toExternalForm());
                ImageView userImgView = new ImageView(userImg);
                userImgView.setFitWidth(20);
                userImgView.setFitHeight(20);
                //User Img btn
                RegisterPage sg = new RegisterPage();
                String name = sg.storedUsername;
                Label userNameLbl = new Label(name); // dynamic name
                userNameLbl.setStyle(btnStyle);

                HBox userImgBtn = new HBox(10);
                userImgBtn.getChildren().addAll(userImgView, userNameLbl);
                userImgBtn.setAlignment(Pos.CENTER_LEFT);

                leftBoxMenu.getChildren().add(userImgBtn);
            } catch (NullPointerException e) {
                System.out.println("Image not Found!");
            }
        }


        private void configNavigationBtns() {
            setButtonGraphs();


            btnHome.setOnAction(e -> centerPanelManager.changeCenterPanel("Home"));
            btnExam.setOnAction(e -> centerPanelManager.changeCenterPanel("Exam"));
            btnAssignment.setOnAction(e -> centerPanelManager.changeCenterPanel("Assignments"));
            btnCalendar.setOnAction(e -> {
                closeCoursesOption(optionVBox);
                centerPanelManager.changeCenterPanel("Calendar");
            });
            btnDashboard.setOnAction(e -> {
                closeCoursesOption(optionVBox);
                centerPanelManager.changeCenterPanel("Dashboard");
            });

            leftBoxMenu.getChildren().addAll(
                btnHome,
                btnCourses,
                btnCalendar,
                btnDashboard
            );
        }

        private void configCourses() {

            optionVBox.setVisible(false);
            btnCourses.setOnAction(e -> {
                if (optionVBox.getChildren().isEmpty()) {
                    optionVBox.getChildren().addAll(btnExam, btnAssignment);
                    optionVBox.setVisible(true);
                    arrowIconCourses.setImage(arrowDown);

                    if (!leftBoxMenu.getChildren().contains(optionVBox)) {
                        leftBoxMenu.getChildren().add(leftBoxMenu.getChildren().size() - 4, optionVBox);
                        VBox.setMargin(btnExam, new Insets(0, 20, 0, 20));
                        VBox.setMargin(btnAssignment, new Insets(0, 20, 0, 20));
                    }
                } else {
                    optionVBox.getChildren().clear();
                    optionVBox.setVisible(false);
                    leftBoxMenu.getChildren().remove(optionVBox);
                    arrowIconCourses.setImage(arrowRight);
                }
            });


        }

        private void closeCoursesOption(VBox optionVBox) {
            if (optionVBox.isVisible()) {
                optionVBox.getChildren().clear();
                optionVBox.setVisible(false);
                leftBoxMenu.getChildren().remove(optionVBox);
                arrowIconCourses.setImage(arrowRight);
            }
        }

        private void configLogo() {
            //logo Image
            Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
            ImageView logoImgView = new ImageView(logoImg);

            logoImgView.setFitWidth(200);
            logoImgView.setPreserveRatio(true);

            Region logoSpacer = new Region();
            VBox.setVgrow(logoSpacer, Priority.ALWAYS);

            HBox logoBox = new HBox(logoImgView);
            logoBox.setAlignment(Pos.CENTER);

            leftBoxMenu.getChildren().addAll(logoSpacer, logoImgView);
        }

        private void setButtonGraphs() {
            //arrow Images
            arrowRight = new Image(getClass().getResource("/arrowRight.png").toExternalForm());
            arrowDown = new Image(getClass().getResource("/arrowDown.png").toExternalForm());

            ImageView arrowIconHome = new ImageView(arrowRight);
            arrowIconHome.setFitWidth(20);
            arrowIconHome.setFitHeight(20);

            arrowIconCourses = new ImageView(arrowRight);
            arrowIconCourses.setFitHeight(20);
            arrowIconCourses.setFitWidth(20);

            ImageView arrowIconCalendar = new ImageView(arrowRight);
            arrowIconCalendar.setFitHeight(20);
            arrowIconCalendar.setFitWidth(20);

            ImageView arrowIconDashboard = new ImageView(arrowRight);
            arrowIconDashboard.setFitWidth(20);
            arrowIconDashboard.setFitHeight(20);

            //Home Image
            Image homeImage = new Image(getClass().getResource("/icons8-homepage-32.png").toExternalForm());
            ImageView homeImageView = new ImageView(homeImage);

            homeImageView.setFitHeight(20);
            homeImageView.setFitWidth(20);

            //Courses Image
            Image coursesImage = new Image(getClass().getResource("/folder.png").toExternalForm());
            ImageView coursesImageView = new ImageView(coursesImage);

            coursesImageView.setFitWidth(20);
            coursesImageView.setFitHeight(20);

            //Calendar Image
            Image calendarImage = new Image(getClass().getResource("/calendar.png").toExternalForm());
            ImageView calendarImageView = new ImageView(calendarImage);

            calendarImageView.setFitHeight(20);
            calendarImageView.setFitWidth(20);

            //Dashboard Image
            Image dashboardImage = new Image(getClass().getResource("/dashboard.png").toExternalForm());
            ImageView dashboardImageView = new ImageView(dashboardImage);

            dashboardImageView.setFitWidth(20);
            dashboardImageView.setFitHeight(20);


            //Home Image btn
            HBox homeImg = new HBox(10);
            homeImg.getChildren().addAll(arrowIconHome, homeImageView);
            btnHome.setGraphic(homeImg);

            //Courses
            HBox coursesImg = new HBox(10);
            coursesImg.getChildren().addAll(arrowIconCourses, coursesImageView);
            btnCourses.setGraphic(coursesImg);

            //Calendar
            HBox calendarImg = new HBox(10);
            calendarImg.getChildren().addAll(arrowIconCalendar, calendarImageView);
            btnCalendar.setGraphic(calendarImg);

            //Dashboard
            HBox dashboardImg = new HBox(10);
            dashboardImg.getChildren().addAll(arrowIconDashboard, dashboardImageView);
            btnDashboard.setGraphic(dashboardImg);

        }

        public VBox getLeftBoxMenu() {
            return leftBoxMenu;
        }
    }
}
