package org.javawavers.studybuddy;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Calendar extends Application {

    ListView<ImageView> listView = new ListView<>();
    

    ImageView imageView = new ImageView();
    Image image = null;
    Image image1 = new Image(getClass().getResource("/org/javawavers/studybuddy/eikona3.png").toExternalForm(), 35, 30, false, false);
    Image image2 = new Image(getClass().getResource("/org/javawavers/studybuddy/eikona2.png").toExternalForm(), 35, 30, false, false);
    Image image3 = new Image(getClass().getResource("/org/javawavers/studybuddy/eikona1.png").toExternalForm(), 35, 30, false, false);
    
    // Δημιουργία ImageView για κάθε εικόνα
    ImageView imageView1 = new ImageView(image1);
    ImageView imageView2 = new ImageView(image2);
    ImageView imageView3 = new ImageView(image3);






    

//StudyBuddyApp st = new StudyBuddyApp();
//περνουμε την σημερινη ημερα
    LocalDate today = LocalDate.now();

//αρχικοποιουμε την besttask,schedule,subject που τις εχουμε παρει απο την studybuddyapp
    public static List<Task> besttask = new ArrayList<>();
    public static int[][] schedule;
    ArrayList<SubjectTest> subject;

//αρχικοποιουμε τις λιστες και τα vbox τα οποια χρησιμευουν στην δυναμικη επεξεργασια και εμφανιση των task
    public static List<String> notStartedYet = new ArrayList<>();
    public static List<String> completed = new ArrayList<>();
    private VBox upcomingTasksBox = new VBox(10);
    private VBox completedTasksBox = new VBox(10);


    private LocalDate currentWeekStart;
//αρχικοποιουμε την μεταβλητη count
    int count = 0;






    @Override
    public void start(Stage primaryStage) {

//αρχικοποιουμε τα task
        initializeTaskLists(besttask);

// Αρχικοποιουμε ολα τα panel
        //VBox leftPanel = createLeftPanel();
        VBox centerPanel = createCenterPanel();
        VBox rightPanel = createRightPanel();

//  κυριο layout
        BorderPane root = new BorderPane();
       // root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

// Δημιουργια σκηνης
        Scene scene = new Scene(root, 1115, 747);

// εμφανιζουμε την σκηνη και ρυθμιζουμε το ονομα της σελιδας
        primaryStage.setScene(scene);
        primaryStage.setTitle("Study Buddy - Calendar");
        primaryStage.setResizable(true);
        primaryStage.show();

// Προσαρμοζομαι το παραθυρο αναλογα οταν ειναι maximize και minimize
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> adjustLayout(root, scene));
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> adjustLayout(root, scene));
    }

// μεθοδος για την προσαρμογη του παραθυρου
    private void adjustLayout(BorderPane root, Scene scene) {
        double widthFactor = scene.getWidth() / 1115;  // πλατος
        double heightFactor = scene.getHeight() / 747;  // υψος


        VBox leftPanel = (VBox) root.getLeft();
        VBox centerPanel = (VBox) root.getCenter();
        VBox rightPanel = (VBox) root.getRight();

        double sidePanelWidth = scene.getWidth() * 0.15;
        leftPanel.setPrefWidth(sidePanelWidth);
        rightPanel.setPrefWidth(sidePanelWidth);


        centerPanel.setPrefWidth(scene.getWidth() - 2 * sidePanelWidth);


        leftPanel.setPrefHeight(scene.getHeight());
        centerPanel.setPrefHeight(scene.getHeight());
        rightPanel.setPrefHeight(scene.getHeight());


        GridPane calendarGrid = (GridPane) centerPanel.getChildren().get(2);
        adjustCalendarGrid(calendarGrid, widthFactor, heightFactor);
    }

// Προσαρμογη του πινακα οταν αλλαζει το μεγεθος και αντοιστοιχοι προσαρμογη των κελιων
    private void adjustCalendarGrid(GridPane grid, double widthFactor, double heightFactor) {
        int numberOfCells = grid.getChildren().size();  // περνουμε τους αριθμους για τον πινακα

    
        for (int row = 1; row <= 5; row++) {
            for (int col = 0; col < 7; col++) {
                int index = row * 7 + col;
                if (index < numberOfCells) {
                    Label cell = (Label) grid.getChildren().get(index);
                    cell.setPrefWidth(124 * widthFactor);
                    cell.setPrefHeight(100 * heightFactor);
                }
            }
        }
    }


/*
//δημιουργουμε το αριστερο panel
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
//οριζουμε το χρωμα
        leftPanel.setStyle("-fx-background-color: #CF7330;");

//οριζουμε το ονομα και το μεγεθος του label : user
        Label userNameLabel = new Label(Signup.storedUsername);
        userNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

//δημιουργουμε τα κουμπια για το αριστερο μερος
        Button dashboardButton = createSideButton1("Dashboard");
        Button calendarButton = createSideButton1("Calendar");

//φτιαχνουμε το vbox για το study και το assignments και προσθετουμε τα κουμπια
        VBox coursesSection = new VBox(5);
        coursesSection.setPadding(new Insets(10, 0, 0, 0));
        Label coursesLabel = new Label("Courses");
        coursesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");


        Button studyButton = createSideButton1("Study");
        Button assignmentsButton = createSideButton1("Assignments");
        coursesSection.getChildren().addAll(coursesLabel, studyButton, assignmentsButton);

//προσθετουμε ολα τα στοιχεια στο αριστερο μερος και το επιστρεφουμε
        leftPanel.getChildren().addAll(userNameLabel, dashboardButton, calendarButton, coursesSection);
        return leftPanel;
    }
*/


//μεθοδος για την δημιουργια του κεντρικου panel
    private VBox createCenterPanel() {
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(20));
        centerPanel.setStyle("-fx-background-color: white;");


//ΤΟ DO : οι αυξηση των εβδομαδων να ειναι δυναμικη
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
//μεταβλητη count η οποια μολις ο χρηστης παταει το κουμπι που παει τις εβδομαδες μπροστα αυξανεται αλλιως μειωνεται οταν count == 0 τοτε θα εεμφανιζετε το κουμπι today
        prevButton.setOnAction(event -> {
            currentWeekStart = currentWeekStart.minusWeeks(1);
            count = count + 1;
            weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));
            
        });
    
        nextButton.setOnAction(event -> {
            count--;
            currentWeekStart = currentWeekStart.plusWeeks(1);
            weekLabel.setText(formatWeekLabel(currentWeekStart, formatter));

        });


        weekSwitcher.setTranslateY(40);
        weekSwitcher.setAlignment(Pos.CENTER);
        weekSwitcher.getChildren().addAll(prevButton, weekLabel, nextButton);
        
//αρχικοποιηση του πινακα και δημιουργια του πινακα
        GridPane calendarGrid = new GridPane();
        calendarGrid.setStyle("-fx-border-color: black;");
        calendarGrid.setGridLinesVisible(true);
        createCalendarGrid(calendarGrid, besttask, schedule);

        Button todayButton = new Button("Today");
        todayButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
        todayButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        todayButton.setTextFill(Color.WHITE);

        if (count == 0) {
            todayButton.setVisible(true);
        } else {
            todayButton.setVisible(false);
        }

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
            schedule = SimulateAnnealing.SchedulResult();
            createCalendarGrid(calendarGrid, besttask, schedule);
        });
        
//βαζουμε ολα τα στοιχεια του κεντρου μαζι και τα επιστρεφουμε
        centerPanel.getChildren().addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);

        
        return centerPanel;
    }
    
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setStyle("-fx-background-color: white;");
        rightPanel.setPadding(new Insets(5));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPrefHeight(Double.MAX_VALUE);

//δημιουργια των κουμπιων για να βλεπει ο χρηστης τα upcoming task του καθως και τα completed task
        Button upcomingTasksButton = createCircularButton("Upcoming Tasks", "#96E2D6");
        Button completedTasksButton = createCircularButton("Completed Tasks", "#15B569");

//οταν ο χρηστης παταει το κουμπι ανοιγει το popup : showTasksPopup
        upcomingTasksButton.setOnAction(event -> showTasksPopup("Upcoming Tasks", notStartedYet));
        completedTasksButton.setOnAction(event -> showTasksPopup("Completed Tasks", completed));

        Button openPopupButton = new Button("Open Popup");
        openPopupButton.setOnAction(e -> showPopup());

        VBox root = new VBox(openPopupButton);
        Scene scene = new Scene(root, 20, 100);

    
//προσθετουμε ολα τα κουμπια στο δεξι panel
        rightPanel.getChildren().addAll(upcomingTasksButton, completedTasksButton, openPopupButton);
    
        return rightPanel;
    }


//μεθοδος χειρισμου των κουμπιων που βρισκονται στο αριστερο panel
    private Button createSideButton1(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        return button;
    }

//δημιουργουμε τα checkbox
    private HBox createCheckBox(String taskName) {
        HBox checkBoxBox = new HBox(10);
        checkBoxBox.setAlignment(Pos.CENTER_LEFT);
        

        javafx.scene.control.CheckBox taskCheckBox = new javafx.scene.control.CheckBox(taskName);
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
    private void createCalendarGrid(GridPane grid, List<Task> besttask, int[][] schedule) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        listView.getItems().add(imageView1);
        listView.getItems().add(imageView2);
        listView.getItems().add(imageView3);


        //ρυθμιζουμε το πλατος για τις 7 στηλες
        for (int i = 1; i < days.length + 1; i++) {
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

        
        for (int row = 1; row < 11; row++) {
            for (int col = 0; col <= days.length -1; col++) {
                //Label cell = new Label();
                /*  cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
                cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
                cell.setPrefSize(140, 60);*/
                image = new Image(getClass().getResource("/org/javawavers/studybuddy/eikona.png").toExternalForm(), 35, 30, false, false);
                Label cell = new Label();
                imageView.setFitWidth(60);  // Το πλάτος της εικόνας
                imageView.setFitHeight(60); // Το ύψος της εικόνας
                imageView.setPreserveRatio(true); 
                //cell.setGraphic(new ImageView(image));
                cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center; -fx-pref-width: 140; -fx-pref-height: 60;");
                cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
                cell.setPrefSize(140, 60);
                //cell.setText(SimulateAnnealing.printSchedule(row, col));
                //MainTestAlgorithm algorithm = new MainTestAlgorithm();
                //cell.setText(algorithm.run(row, col));
                //cell.setText(besttask.get(row).toString());
                if (schedule != null && besttask != null &&
                    row < schedule.length && col < schedule[row].length &&
                    schedule[row][col] > 0 && schedule[row][col] <= besttask.size()) {

                    int taskIndex = schedule[row][col] - 1;
                    if (taskIndex >= 0 && taskIndex < besttask.size()) {
                        //cell.setText(besttask.get(taskIndex).toString());
                        String taskText = besttask.get(taskIndex).toString();
                        
                        //cell.setText(taskText);
                        String firstWord = taskText.split(" ")[0];
                        for (SubjectTest subject : subject) {
                            if (subject.getName().equalsIgnoreCase(firstWord)) {

                                cell.setGraphic(new ImageView(image));
                               // cell.setStyle("-fx-background-color: " + subject.getColor() + "; " +
                                    //"-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
                                break;
                            }
                        }
                    }else {
                        cell.setText(""); 
                    }
                } else {
                    cell.setText("");
                }

                final int rowFinal = row;
                final int colFinal = col;

//οταν ο χρηστης παταει πανω σε οποιδηποτε κελη τοτε του εμφανιζεται η σελιδα popupdiathesimotita
                cell.setOnMouseClicked(event -> {
                    
                    String taskDescription = "κενο";
                    LocalDate examDate = null;

                    if (schedule != null && besttask != null &&
                        rowFinal < schedule.length && colFinal < schedule[rowFinal].length &&
                        schedule[rowFinal][colFinal] > 0 && schedule[rowFinal][colFinal] <= besttask.size()) {

                        int taskIndex = schedule[rowFinal][colFinal] - 1;
                    if (taskIndex >= 0 && taskIndex < besttask.size()) {
                        taskDescription = besttask.get(taskIndex).toString();
                    }
                }
                for (SubjectTest subject : subject) {
                    if (taskDescription.contains(subject.getName())) {
                        examDate = subject.getExamDate();
                        break;
                    }
                }


                    Popupdiathesimotita popup = new Popupdiathesimotita();
                    popup.setTaskLists(notStartedYet, completed);
                    popup.setTaskDescription(taskDescription, examDate);
                    Stage popupStage = new Stage();
                    popup.start(popupStage);
                });
            
                GridPane.setConstraints(cell, col, row );
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

    private void showPopup() {
        // Create the pop-up stage
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make the popup modal
        popupStage.setTitle("Select Item");

        // Create a ListView to hold the items
       // ListView<HBox> listView = new ListView<>();
        
        // Add some items to the ListView with ImageViews
        for (int i = 1; i <= 5; i++) {
            //Image image = new Image("file:path/to/your/icon" + i + ".png");
            //mageView imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.BLACK);
            imageView.setEffect(dropShadow);
            
            // Create an HBox to hold the image and additional content (if needed)
            //HBox listItem = new HBox(10);
            //listItem.getChildren().add(imageView);
            // Add the item to the ListView
            listView.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");

            listView.getItems().add(imageView);
        }

        // Create the OK button at the bottom of the popup
        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #50D1C6; -fx-background-radius: 30px; -fx-text-fill: white; -fx-font-size: 16px;");
        okButton.setAlignment(Pos.CENTER);
        okButton.setOnAction(e -> {
            //imageView selectedItem = listView.getSelectionModel().getSelectedItem();
            /*if (selectedItem != null) {
                // Handle the selection (for example, print something)
                System.out.println("Selected item: " + selectedItem);
            }
            popupStage.close();  // Close the popup*/
        });

        // Create the layout for the popup
        VBox popupLayout = new VBox(10);
        popupLayout.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");
        popupLayout.getChildren().addAll(listView, okButton);
        
        // Add a ScrollPane to allow scrolling
        ScrollPane scrollPane = new ScrollPane(popupLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300); // Set the preferred height of the scrollable area
        
        // Set the scene for the popup
        Scene popupScene = new Scene(scrollPane, 10, 300);
        popupStage.setScene(popupScene);

        // Show the popup
        popupStage.show();
    }

    public Node calendar() {
        HBox calendarPage = new HBox();
        calendarPage.getChildren().addAll(createCenterPanel(),createRightPanel());
        return calendarPage;

    }



    public static void main(String[] args) {
        launch(args);
    }
}