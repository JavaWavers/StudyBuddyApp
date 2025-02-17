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
import javafx.scene.control.ScrollPane;
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







    

//StudyBuddyApp st = new StudyBuddyApp();
//╧Α╬╡╧Β╬╜╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╧Δ╬╖╬╝╬╡╧Β╬╣╬╜╬╖ ╬╖╬╝╬╡╧Β╬▒
    LocalDate today = LocalDate.now();

//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ besttask,schedule,subject ╧Α╬┐╧Ζ ╧Ε╬╣╧Γ ╬╡╧Θ╬┐╧Ζ╬╝╬╡ ╧Α╬▒╧Β╬╡╬╣ ╬▒╧Α╬┐ ╧Ε╬╖╬╜ studybuddyapp
    public static List<Task> besttask = new ArrayList<>();
    public static int[][] schedule;
    ArrayList<SubjectTest> subject;

//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╧Ε╬╣╧Γ ╬╗╬╣╧Δ╧Ε╬╡╧Γ ╬║╬▒╬╣ ╧Ε╬▒ vbox ╧Ε╬▒ ╬┐╧Α╬┐╬╣╬▒ ╧Θ╧Β╬╖╧Δ╬╣╬╝╬╡╧Ζ╬┐╧Ζ╬╜ ╧Δ╧Ε╬╖╬╜ ╬┤╧Ζ╬╜╬▒╬╝╬╣╬║╬╖ ╬╡╧Α╬╡╬╛╬╡╧Β╬│╬▒╧Δ╬╣╬▒ ╬║╬▒╬╣ ╬╡╬╝╧Η╬▒╬╜╬╣╧Δ╬╖ ╧Ε╧Κ╬╜ task
    public static List<String> notStartedYet = new ArrayList<>();
    public static List<String> completed = new ArrayList<>();
    private VBox upcomingTasksBox = new VBox(10);
    private VBox completedTasksBox = new VBox(10);


    private LocalDate currentWeekStart;
//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╬╝╬╡╧Ε╬▒╬▓╬╗╬╖╧Ε╬╖ count
    int count = 0;






    @Override
    public void start(Stage primaryStage) {

//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ task
        initializeTaskLists(besttask);

// ╬Σ╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╬┐╬╗╬▒ ╧Ε╬▒ panel
        //VBox leftPanel = createLeftPanel();
        VBox centerPanel = createCenterPanel();
        VBox rightPanel = createRightPanel();

//  ╬║╧Ζ╧Β╬╣╬┐ layout
        BorderPane root = new BorderPane();
       // root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

// ╬Φ╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╧Δ╬║╬╖╬╜╬╖╧Γ
        Scene scene = new Scene(root, 1115, 747);

// ╬╡╬╝╧Η╬▒╬╜╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╧Δ╬║╬╖╬╜╬╖ ╬║╬▒╬╣ ╧Β╧Ζ╬╕╬╝╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╬┐╬╜╬┐╬╝╬▒ ╧Ε╬╖╧Γ ╧Δ╬╡╬╗╬╣╬┤╬▒╧Γ
        primaryStage.setScene(scene);
        primaryStage.setTitle("Study Buddy - Calendar");
        primaryStage.setResizable(true);
        primaryStage.show();

// ╬ι╧Β╬┐╧Δ╬▒╧Β╬╝╬┐╬╢╬┐╬╝╬▒╬╣ ╧Ε╬┐ ╧Α╬▒╧Β╬▒╬╕╧Ζ╧Β╬┐ ╬▒╬╜╬▒╬╗╬┐╬│╬▒ ╬┐╧Ε╬▒╬╜ ╬╡╬╣╬╜╬▒╬╣ maximize ╬║╬▒╬╣ minimize
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> adjustLayout(root, scene));
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> adjustLayout(root, scene));
    }

// ╬╝╬╡╬╕╬┐╬┤╬┐╧Γ ╬│╬╣╬▒ ╧Ε╬╖╬╜ ╧Α╧Β╬┐╧Δ╬▒╧Β╬╝╬┐╬│╬╖ ╧Ε╬┐╧Ζ ╧Α╬▒╧Β╬▒╬╕╧Ζ╧Β╬┐╧Ζ
    private void adjustLayout(BorderPane root, Scene scene) {
        double widthFactor = scene.getWidth() / 1115;  // ╧Α╬╗╬▒╧Ε╬┐╧Γ
        double heightFactor = scene.getHeight() / 747;  // ╧Ζ╧Ι╬┐╧Γ


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

// ╬ι╧Β╬┐╧Δ╬▒╧Β╬╝╬┐╬│╬╖ ╧Ε╬┐╧Ζ ╧Α╬╣╬╜╬▒╬║╬▒ ╬┐╧Ε╬▒╬╜ ╬▒╬╗╬╗╬▒╬╢╬╡╬╣ ╧Ε╬┐ ╬╝╬╡╬│╬╡╬╕╬┐╧Γ ╬║╬▒╬╣ ╬▒╬╜╧Ε╬┐╬╣╧Δ╧Ε╬┐╬╣╧Θ╬┐╬╣ ╧Α╧Β╬┐╧Δ╬▒╧Β╬╝╬┐╬│╬╖ ╧Ε╧Κ╬╜ ╬║╬╡╬╗╬╣╧Κ╬╜
    private void adjustCalendarGrid(GridPane grid, double widthFactor, double heightFactor) {
        int numberOfCells = grid.getChildren().size();  // ╧Α╬╡╧Β╬╜╬┐╧Ζ╬╝╬╡ ╧Ε╬┐╧Ζ╧Γ ╬▒╧Β╬╣╬╕╬╝╬┐╧Ζ╧Γ ╬│╬╣╬▒ ╧Ε╬┐╬╜ ╧Α╬╣╬╜╬▒╬║╬▒

    
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
//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╬▒╧Β╬╣╧Δ╧Ε╬╡╧Β╬┐ panel
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
//╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╧Θ╧Β╧Κ╬╝╬▒
        leftPanel.setStyle("-fx-background-color: #CF7330;");

//╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╬┐╬╜╬┐╬╝╬▒ ╬║╬▒╬╣ ╧Ε╬┐ ╬╝╬╡╬│╬╡╬╕╬┐╧Γ ╧Ε╬┐╧Ζ label : user
        Label userNameLabel = new Label(Signup.storedUsername);
        userNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒ ╬│╬╣╬▒ ╧Ε╬┐ ╬▒╧Β╬╣╧Δ╧Ε╬╡╧Β╬┐ ╬╝╬╡╧Β╬┐╧Γ
        Button dashboardButton = createSideButton1("Dashboard");
        Button calendarButton = createSideButton1("Calendar");

//╧Η╧Ε╬╣╬▒╧Θ╬╜╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ vbox ╬│╬╣╬▒ ╧Ε╬┐ study ╬║╬▒╬╣ ╧Ε╬┐ assignments ╬║╬▒╬╣ ╧Α╧Β╬┐╧Δ╬╕╬╡╧Ε╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒
        VBox coursesSection = new VBox(5);
        coursesSection.setPadding(new Insets(10, 0, 0, 0));
        Label coursesLabel = new Label("Courses");
        coursesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");


        Button studyButton = createSideButton1("Study");
        Button assignmentsButton = createSideButton1("Assignments");
        coursesSection.getChildren().addAll(coursesLabel, studyButton, assignmentsButton);

//╧Α╧Β╬┐╧Δ╬╕╬╡╧Ε╬┐╧Ζ╬╝╬╡ ╬┐╬╗╬▒ ╧Ε╬▒ ╧Δ╧Ε╬┐╬╣╧Θ╬╡╬╣╬▒ ╧Δ╧Ε╬┐ ╬▒╧Β╬╣╧Δ╧Ε╬╡╧Β╬┐ ╬╝╬╡╧Β╬┐╧Γ ╬║╬▒╬╣ ╧Ε╬┐ ╬╡╧Α╬╣╧Δ╧Ε╧Β╬╡╧Η╬┐╧Ζ╬╝╬╡
        leftPanel.getChildren().addAll(userNameLabel, dashboardButton, calendarButton, coursesSection);
        return leftPanel;
    }
*/


//╬╝╬╡╬╕╬┐╬┤╬┐╧Γ ╬│╬╣╬▒ ╧Ε╬╖╬╜ ╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╧Ε╬┐╧Ζ ╬║╬╡╬╜╧Ε╧Β╬╣╬║╬┐╧Ζ panel
    private VBox createCenterPanel() {
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(20));
        centerPanel.setStyle("-fx-background-color: white;");


//╬ν╬θ DO : ╬┐╬╣ ╬▒╧Ζ╬╛╬╖╧Δ╬╖ ╧Ε╧Κ╬╜ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╧Κ╬╜ ╬╜╬▒ ╬╡╬╣╬╜╬▒╬╣ ╬┤╧Ζ╬╜╬▒╬╝╬╣╬║╬╖
/*╬Φ╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╬╡╬╜╬▒ hbox ╬│╬╣╬▒ ╧Ε╬╖╬╜ ╬┤╬╣╬▒╧Ε╬▒╬╛╬╖ ╧Ε╬┐╬╜ ╧Δ╧Ε╬┐╬╣╧Θ╬╡╬╣╧Κ╬╜ ╧Δ╬╡ ╬┐╧Β╬╣╬╢╬┐╬╜╧Ε╬╣╬▒ ╬╕╬╡╧Δ╬╖
 *╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╬╕╬╡╧Δ╬╖ ╧Ε╬┐╧Ζ ╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╧Δ╬╖╬╝╬╡╧Β╬╣╬╜╬╖ ╬╝╬╡╧Β╬▒ ╬║╬▒╬╕╧Κ╧Γ ╬║╬▒╬╣ ╧Ε╬╖╬╜ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╬▒ ╧Ε╬╖╬╜ ╬┐╧Α╬┐╬╣╬▒ ╬┤╬╣╬▒╬╜╧Ζ╬╡╬╣ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ
 *╬╝╬╡ ╧Ε╬╖╬╜ datetimeformater ╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐╬╜ ╧Ε╧Β╬┐╧Α╬┐ ╬╝╬╡ ╧Ε╬┐╬╜ ╬┐╧Α╬┐╬╣╬┐ ╬╕╬▒ ╬╡╬╝╧Η╬▒╬╜╬╣╬╢╬╡╧Ε╬▒╬╣ ╧Ε╬┐ weeklabel
 *╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╬┤╧Ζ╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒ ╧Ε╬▒ ╬┐╧Α╬┐╬╣╬▒ ╬╕╬▒ ╬╡╬╣╬╜╬▒╬╣ ╬│╬╣╬▒ ╧Ε╬╖╬╜ ╧Α╬╗╬┐╬╖╬│╬╖╧Δ╬╖ ╧Ε╬┐╧Ζ ╧Θ╧Β╬╖╧Δ╧Ε╬╖ ╧Δ╧Ε╬╣╧Γ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╬╡╧Γ ╬║╬▒╬╣ ╬╜╬▒ ╬╝╧Α╬┐╧Β╬╡╬╣ ╬╜╬▒ ╬┤╬╡╬╣ ╧Ε╬┐ ╧Α╧Β╬┐╬│╧Β╬▒╬╝╬╝╬▒ ╧Ε╬┐╧Ζ
 */
        HBox weekSwitcher = new HBox(10);
        weekSwitcher.setTranslateY(40);
        weekSwitcher.setAlignment(Pos.CENTER);

        LocalDate today = LocalDate.now();
        currentWeekStart = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        
//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╬╝╬╡╧Ε╬▒╬▓╬╗╬╖╧Ε╬╖ weeklabel
        Label weekLabel = new Label(formatWeekLabel(currentWeekStart, formatter));
        weekLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        weekLabel.setStyle("-fx-text-fill: black;");

        HBox.setHgrow(weekLabel, Priority.ALWAYS);
//╬▓╬▒╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒ ╬│╬╣╬▒ ╬╜╬▒ ╧Α╬╗╬┐╬╖╬│╬╡╬╣╧Ε╬╡ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Δ╧Ε╬╣╧Γ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╬╡╧Γ
        Button prevButton = new Button("<");
        prevButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
        prevButton.setPrefSize(30, 30);
        
        Button nextButton = new Button(">");
        nextButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
        nextButton.setPrefSize(30, 30);
//╬╝╬╡╧Ε╬▒╬▓╬╗╬╖╧Ε╬╖ count ╬╖ ╬┐╧Α╬┐╬╣╬▒ ╬╝╬┐╬╗╬╣╧Γ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Α╬▒╧Ε╬▒╬╡╬╣ ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣ ╧Α╬┐╧Ζ ╧Α╬▒╬╡╬╣ ╧Ε╬╣╧Γ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╬╡╧Γ ╬╝╧Α╧Β╬┐╧Δ╧Ε╬▒ ╬▒╧Ζ╬╛╬▒╬╜╬╡╧Ε╬▒╬╣ ╬▒╬╗╬╗╬╣╧Κ╧Γ ╬╝╬╡╬╣╧Κ╬╜╬╡╧Ε╬▒╬╣ ╬┐╧Ε╬▒╬╜ count == 0 ╧Ε╬┐╧Ε╬╡ ╬╕╬▒ ╬╡╬╡╬╝╧Η╬▒╬╜╬╣╬╢╬╡╧Ε╬╡ ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣ today
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
        
//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬╖╧Δ╬╖ ╧Ε╬┐╧Ζ ╧Α╬╣╬╜╬▒╬║╬▒ ╬║╬▒╬╣ ╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╧Ε╬┐╧Ζ ╧Α╬╣╬╜╬▒╬║╬▒
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

//╬║╬┐╧Ζ╬╝╧Α╬╣ ╬│╬╣╬▒ ╬╜╬▒ ╬▓╬▒╬╢╬╡╬╣ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Ε╬╖╬╜ ╬┤╬╣╬▒╬╕╬╡╧Δ╬╖╬╝╬┐╧Ε╬╖╧Ε╬▒
        Button availabilityButton = new Button("Availiability");
        availabilityButton.setStyle("-fx-background-color: #CF308C; -fx-background-radius: 30px; -fx-border-color: black; -fx-border-radius: 30px;");
        availabilityButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        availabilityButton.setTextFill(Color.WHITE);
        availabilityButton.setPrefWidth(160);

//╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╬┐╧Ε╬▒╬╜ ╬┐ ╬╢╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Α╬▒╧Ε╬▒╬╡╬╣ ╧Α╬▒╬╜╧Κ ╧Δ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣ ╬╜╬▒ ╬▒╬╜╬┐╬╣╬│╬╡╬╣ ╧Ε╬╖╬╜ ╧Δ╬╡╬╗╬╣╬┤╬▒ popupdia
        availabilityButton.setOnAction(event ->  {
            Popupdia popup1 = new Popupdia();
            Stage popup1Stage = new Stage();
            popup1.start(popup1Stage);

        
        });

//╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬╖╬╜ ╬╕╬╡╧Δ╬╖ ╧Ε╬┐╧Ζ ╬║╬┐╧Ζ╬╝╧Α╬╣╬┐╧Ζ availiability
        StackPane availabilityPane = new StackPane(availabilityButton);
        availabilityPane.setPrefSize(150, 30);
        availabilityPane.setLayoutX(centerPanel.getWidth() - 300);
        availabilityPane.setLayoutY(200);

 //╬║╬┐╧Ζ╬╝╧Α╬╣ ╬│╬╣╬▒ Refresh ╧Ε╬┐╧Ζ ╧Α╧Β╬┐╬┐╬│╧Β╬▒╬╝╬╝╬▒╧Ε╬┐╧Γ
        Button refreshButton = new Button();
        refreshButton.setStyle("-fx-background-color: #CF308C; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 30px;");
        refreshButton.setPrefSize(30, 30);

//╧Α╧Β╬┐╧Δ╬╕╬╖╬║╬╖ ╬╡╬╣╬║╬┐╬╜╬╣╬┤╬╣╬┐╧Ζ ╧Δ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣ ╬│╬╣╬▒ ╬║╧Ζ╬║╬╗╬╣╬║╬▒ ╬▓╬╡╬╗╬╖
        SVGPath refreshIcon = new SVGPath();
        refreshIcon.setContent("M12 2V5C7.58 5 4 8.58 4 13C4 15.27 5.05 17.36 6.77 18.63L8.22 17.18C7.04 16.17 6.27 14.67 6.27 13C6.27 9.8 8.8 7.27 12 7.27V10L16 6L12 2ZM18.23 4.37L16.78 5.82C17.96 6.83 18.73 8.33 18.73 10C18.73 13.2 16.2 15.73 13 15.73V12L9 16L13 20V17C17.42 17 21 13.42 21 9C21 6.73 19.95 4.64 18.23 4.37Z");
        refreshIcon.setFill(Color.WHITE);
        refreshButton.setGraphic(refreshIcon);

        refreshButton.setOnAction(event -> {
            schedule = SimulateAnnealing.SchedulResult();
            createCalendarGrid(calendarGrid, besttask, schedule);
        });
        
//╬▓╬▒╬╢╬┐╧Ζ╬╝╬╡ ╬┐╬╗╬▒ ╧Ε╬▒ ╧Δ╧Ε╬┐╬╣╧Θ╬╡╬╣╬▒ ╧Ε╬┐╧Ζ ╬║╬╡╬╜╧Ε╧Β╬┐╧Ζ ╬╝╬▒╬╢╬╣ ╬║╬▒╬╣ ╧Ε╬▒ ╬╡╧Α╬╣╧Δ╧Ε╧Β╬╡╧Η╬┐╧Ζ╬╝╬╡
        centerPanel.getChildren().addAll(weekSwitcher, todayButton, calendarGrid, availabilityPane, refreshButton);

        
        return centerPanel;
    }
    
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setStyle("-fx-background-color: white;");
        rightPanel.setPadding(new Insets(5));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPrefHeight(Double.MAX_VALUE);

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╧Ε╧Κ╬╜ ╬║╬┐╧Ζ╬╝╧Α╬╣╧Κ╬╜ ╬│╬╣╬▒ ╬╜╬▒ ╬▓╬╗╬╡╧Α╬╡╬╣ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Ε╬▒ upcoming task ╧Ε╬┐╧Ζ ╬║╬▒╬╕╧Κ╧Γ ╬║╬▒╬╣ ╧Ε╬▒ completed task
        Button upcomingTasksButton = createCircularButton("Upcoming Tasks", "#96E2D6");
        Button completedTasksButton = createCircularButton("Completed Tasks", "#15B569");

//╬┐╧Ε╬▒╬╜ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Α╬▒╧Ε╬▒╬╡╬╣ ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣ ╬▒╬╜╬┐╬╣╬│╬╡╬╣ ╧Ε╬┐ popup : showTasksPopup
        upcomingTasksButton.setOnAction(event -> showTasksPopup("Upcoming Tasks", notStartedYet));
        completedTasksButton.setOnAction(event -> showTasksPopup("Completed Tasks", completed));



    
//╧Α╧Β╬┐╧Δ╬╕╬╡╧Ε╬┐╧Ζ╬╝╬╡ ╬┐╬╗╬▒ ╧Ε╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒ ╧Δ╧Ε╬┐ ╬┤╬╡╬╛╬╣ panel
        rightPanel.getChildren().addAll(upcomingTasksButton, completedTasksButton);
    
        return rightPanel;
    }


//╬╝╬╡╬╕╬┐╬┤╬┐╧Γ ╧Θ╬╡╬╣╧Β╬╣╧Δ╬╝╬┐╧Ζ ╧Ε╧Κ╬╜ ╬║╬┐╧Ζ╬╝╧Α╬╣╧Κ╬╜ ╧Α╬┐╧Ζ ╬▓╧Β╬╣╧Δ╬║╬┐╬╜╧Ε╬▒╬╣ ╧Δ╧Ε╬┐ ╬▒╧Β╬╣╧Δ╧Ε╬╡╧Β╬┐ panel
    private Button createSideButton1(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        return button;
    }

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ checkbox
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

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╬╖╬╝╬╡╧Β╬┐╬╗╬┐╬│╬╣╬┐
    private void createCalendarGrid(GridPane grid, List<Task> besttask, int[][] schedule) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};



        //╧Β╧Ζ╬╕╬╝╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐ ╧Α╬╗╬▒╧Ε╬┐╧Γ ╬│╬╣╬▒ ╧Ε╬╣╧Γ 7 ╧Δ╧Ε╬╖╬╗╬╡╧Γ
        for (int i = 1; i < days.length + 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 7);
            grid.getColumnConstraints().add(column);
        }

//╬▓╬▒╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐╧Ζ╧Γ ╧Ε╬╣╧Ε╬╗╬┐╧Ζ╧Γ ╬│╬╣╬▒ ╧Ε╬╖╬╜ ╬║╬▒╬╕╬╡ ╬╝╬╡╧Β╬▒ 
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
            dayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            dayLabel.setPrefHeight(60);
            dayLabel.setPrefWidth(140);
            GridPane.setConstraints(dayLabel, i, 0);
            grid.getChildren().add(dayLabel);
        }

//╬╜╬▒ ╬╡╧Θ╬┐╧Ζ╬╜ ╬┐╬╗╬╡╧Γ ╬│╧Β╬▒╬╝╬╝╬╡╧Γ ╧Ε╬┐ ╬╣╬┤╬╣╬┐ ╧Ζ╧Ι╬┐╧Γ 
        for (int i = 0; i < 11; i++) {  // 11 ╬│╧Β╬▒╬╝╬╝╬╡╧Γ ╬│╬╣╬▒ ╧Ε╬┐╬╜ ╧Α╬╣╬╜╬▒╬║╬▒
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 11);
            grid.getRowConstraints().add(row);
        }

        
        for (int row = 1; row < 11; row++) {
            for (int col = 0; col <= days.length -1; col++) {
                Label cell = new Label();
              //  cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
               // cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
              //  cell.setPrefSize(140, 60);
               // Label cell = new Label();
 
                //cell.setGraphic(new ImageView(image));
                cell.setStyle("-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center; -fx-pref-width: 140; -fx-pref-height: 60;");
                cell.setFont(Font.font("System", FontWeight.NORMAL, 14));
                cell.setPrefSize(140, 60);
                //cell.setText(SimulateAnnealing.printSchedule(row, col));
                //MainTestAlgorithm algorithm = new MainTestAlgorithm();
                //cell.setText(algorithm.run(row, col));
               // cell.setText(besttask.get(row).toString());
                if (schedule != null && besttask != null &&
                    row < schedule.length && col < schedule[row].length &&
                    schedule[row][col] > 0 && schedule[row][col] <= besttask.size()) {

                    int taskIndex = schedule[row][col] - 1;
                    if (taskIndex >= 0 && taskIndex < besttask.size()) {
                        //cell.setText(besttask.get(taskIndex).toString());
                        String taskText = besttask.get(taskIndex).toString();
                       // cell.setText(taskText);
                        String firstWord = taskText.split(" ")[0];
                        for (SubjectTest subject : subject) {
                            if (subject.getName().equalsIgnoreCase(firstWord)) {

                                //cell.setGraphic(new ImageView(image));
                               cell.setStyle("-fx-background-color: " + subject.getColor() + "; " +
                                    "-fx-border-color: gray; -fx-border-width: 0; -fx-alignment: center;");
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

//╬┐╧Ε╬▒╬╜ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Α╬▒╧Ε╬▒╬╡╬╣ ╧Α╬▒╬╜╧Κ ╧Δ╬╡ ╬┐╧Α╬┐╬╣╬┤╬╖╧Α╬┐╧Ε╬╡ ╬║╬╡╬╗╬╖ ╧Ε╬┐╧Ε╬╡ ╧Ε╬┐╧Ζ ╬╡╬╝╧Η╬▒╬╜╬╣╬╢╬╡╧Ε╬▒╬╣ ╬╖ ╧Δ╬╡╬╗╬╣╬┤╬▒ popupdiathesimotita
                cell.setOnMouseClicked(event -> {
                    
                    String taskDescription = "╬║╬╡╬╜╬┐";
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
//╬┐╧Α╬┐╧Ε╬╡ ╬║╬▒╬╗╬╡╬╣╧Ε╬╡ ╬▒╬╜╬▒╬╜╬╡╧Κ╬╜╬┐╬╜╧Ε╬▒╬╣ ╬▒╬╜╬▒╬╗╬┐╬│╬▒ ╬╝╬╡ ╬╡╬║╬╡╬╣╬╜╬▒ ╧Ε╬▒ ╬┤╬╡╬┤╬┐╬╝╬╡╬╜╬▒ ╧Ε╬┐ popup ╧Α╬┐╧Ζ ╬╡╬╝╧Η╬▒╬╜╬╣╬╢╬╡╧Ε╬╡
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
//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╧Ε╬┐╧Ζ pop up ╧Ε╬┐ ╬┐╧Α╬┐╬╣╬┐ ╬▒╬╜╬┐╬╣╬│╬╡╬╣ ╬┐╧Ε╬▒╬╜ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╧Α╬▒╧Ε╬╖╧Δ╬╡╬╣ ╬┐╧Α╬┐╬╣╬┐╬┤╬╖╧Α╬┐╧Ε╬╡ ╬▒╧Α╬┐ ╧Ε╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬▒ ypcomingtasks/completedtasks
    private void showTasksPopup(String title, List<String> taskList) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setAlignment(Pos.TOP_CENTER);
//╬┐╧Β╬╣╬╢╬┐╧Ζ╬╝╬╡ ╧Ε╬┐╬╜ ╧Ε╬╣╧Ε╬╗╬┐ ╬▒╬╜╬▒╬╗╬┐╬│╬▒ ╬╝╬╡ ╧Ε╬┐ ╬║╬┐╧Ζ╬╝╧Α╬╣  ╧Α╬┐╧Ζ ╬╡╧Θ╬╡╬╣ ╧Α╬▒╧Ε╬╖╬╕╬╡╬╣
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ taskbox ╬│╬╣╬▒ ╧Ε╬▒ task
        VBox tasksBox = new VBox(5);
        tasksBox.setAlignment(Pos.TOP_LEFT);
        tasksBox.setStyle("-fx-max-height: 300px;");

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ scrollpane ╬│╬╣╬▒ ╬╜╬▒ ╬╝╧Α╬┐╧Β╬╡╬╣ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╬╜╬▒ ╬║╬▒╬╜╬╡╬╣ scroll ╬║╬▒╬╣ ╬╜╬▒ ╬┤╬╡╬╣ ╬┐╬╗╬▒ ╧Ε╬▒ task ╧Ε╬▒ ╬┐╧Α╬┐╬╣╬▒ ╬╡╧Θ╬╡╬╣ ╬╜╬▒ ╬║╬▒╬╜╬╡╬╣ ╬╡╬║╬╡╬╣╬╜╬╖ ╧Ε╬╖╬╜ ╬╡╬▓╬┤╬┐╬╝╬▒╬┤╬▒
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tasksBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        Map<CheckBox, String> taskCheckBoxMap = new HashMap<>();

//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ checkbox
        if (taskList != null && !taskList.isEmpty()) {
            for (String task : taskList) {
                CheckBox checkBox = new CheckBox(task);
                checkBox.setStyle("-fx-font-size: 14px;");

//╬▒╬╜ ╧Ε╬┐ task ╬╡╬╣╬╜╬▒╬╣ ╧Δ╧Ε╬╖╬╜ ╬╗╬╣╧Δ╧Ε╬▒ notstartedyet ╬╡╬╣╬╜╬▒╬╣ unselected ╬▒╬╗╬╗╬╣╧Κ╧Γ ╧Δ╧Ε╬╖╬╜ completed ╧Ε╬▒ task ╬╡╬╣╬╜╬▒╬╣ selected
                if (taskList == notStartedYet && !completed.contains(task)) {
                    checkBox.setSelected(false);
                } else if (taskList == completed && notStartedYet.contains(task)) {
                    checkBox.setSelected(true);
                }

                tasksBox.getChildren().add(checkBox);
                taskCheckBoxMap.put(checkBox, task);//map ╬│╬╣╬▒ ╬╜╬▒ ╬╡╬╗╬╡╬╜╬│╧Θ╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ task ╬╝╬╡ ╧Ε╬┐ checkbox
            }
        } else {
            Label noTasksLabel = new Label("No tasks available");//╧Δ╧Ε╬╖╬╜ ╧Α╬╡╧Β╬╣╧Α╧Ε╧Κ╧Δ╬╖ ╧Α╬┐╧Ζ ╬┤╬╡╬╜ ╧Ζ╧Α╬▒╧Β╧Θ╬┐╧Ζ╬╜ task
            tasksBox.getChildren().add(noTasksLabel);
        }
//╬┤╬╖╬╝╬╣╬┐╧Ζ╧Β╬│╬╣╬▒ ╬║╬┐╧Ζ╬╝╧Α╬╣╬┐╧Ζ ╬┐╬║ ╧Α╬┐╧Ζ ╬┐╧Ε╬▒╬╜ ╧Α╬▒╧Ε╬╖╬╕╬╡╬╣ ╬▒╬╜╬▒╬╗╬┐╬│╬▒ ╬╝╬╡ ╧Ε╬┐ ╧Ε╬╣ ╬╡╧Θ╬╡╬╣ ╧Α╬▒╧Ε╬╖╧Δ╬╡╬╣ ╬┐ ╧Θ╧Β╬╖╧Δ╧Ε╬╖╧Γ ╬╡╬╜╬╖╬╝╬╡╧Β╧Κ╬╜╬╡╬╣ ╧Ε╬╣╧Γ ╬┤╧Ζ╬┐ ╬╗╬╣╧Δ╧Ε╬╡╧Γ
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
//╬╡╬╜╬╖╬╝╬╡╧Β╧Κ╬╜╬┐╧Ζ╬╝╬╡ ╧Ε╬▒ taskboxes
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
//╬▒╧Β╧Θ╬╣╬║╬┐╧Α╬┐╬╣╬╖╧Δ╬╡╬╣ ╧Ε╧Κ╬╜ ╬╗╬╣╧Δ╧Ε╧Κ╬╜
    private void initializeTaskLists(List<Task> besttask) {
        for (Task task : besttask) {
            notStartedYet.add(task.toString());
        }

        completed = new ArrayList<>();

        updateUpcomingTasks(upcomingTasksBox);
        updateCompletedTasks(completedTasksBox);
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
