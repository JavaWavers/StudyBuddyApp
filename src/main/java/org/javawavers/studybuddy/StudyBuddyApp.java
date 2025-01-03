package org.javawavers.studybuddy;


/*
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class StudyBuddyApp extends Application {


    @Override
    public void start(Stage primaryStage) {
 */
/*
//ΤO DO: οταν ανοιγει μια σελιδα θελουμε η αλλη να κλεινει αυτο για ολες τις σελιδες εκτος απο τα popups 
//ανοιγουμε αρχικα την σελιδα εγγραφης
        Signup signup = new Signup();
        Stage signupStage = new Stage();
        signup.start(signupStage);
            
//αν ο χρηστης εχει maximize την οθονη τοτε του εμφανιζεται maximize και η νεα οθονη
        if (primaryStage.isMaximized()) {
            signupStage.setMaximized(true);
        }
        
        signupStage.show();
        
 */
 /*       ArrayList<SubjectTest> subject = new ArrayList<>();
        // Initialize Availability (example)
        Availability.setAvailability(1, 6); // Monday: 6 available hours
        Availability.setAvailability(2, 4); // Tuesday: 4 available hours
        Availability.setAvailability(3, 7); // Wednesday: 5 available hours
        Availability.setAvailability(4, 4); // Thursday: 3 available hours
        Availability.setAvailability(5, 6); // Friday: 6 available hours
        Availability.setAvailability(6, 6); // Saturday: 2 available hours
        Availability.setAvailability(7, 6); // Sunday: 1 available hour


        String color = "red";
        // Create subjects (example)
        SubjectTest math = new SubjectTest("Maths", 2.5, 600, LocalDate.now().plusDays(65),color);
        String color1 = "blue";
        SubjectTest history = new SubjectTest("History", 1.8, 680, LocalDate.now().plusDays(65), color1);

        // Add subjects to SimulateAnnealing
        SimulateAnnealing simulateAnnealing = new SimulateAnnealing();
        simulateAnnealing.addSubject(math);
        simulateAnnealing.addSubject(history);
        subject.add(math);
        subject.add(history);
        
        int[][] schedule = SimulateAnnealing.SchedulResult();
        List<Task> besttask = SimulateAnnealing.getBestTask();
        
        Calendar  calendar = new Calendar();
        calendar.subject = subject;
        calendar.besttask = besttask;
        calendar.schedule = schedule;
        Stage calendarStage = new Stage();
        calendar.start(calendarStage);
        

    }

    
    


    public static void main(String[] args) {
        launch(args);
    }
    
}
*/


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/* 
 * TODO:
 *  fix courses menu (attention Home btn)
 *  scroll bar
 *  username btn alignment
 */ 
public class StudyBuddyApp extends Application { //exam page
    ToggleButton btnHome =new ToggleButton("Home");
    ToggleButton btnExam = new ToggleButton("Exam");
    ToggleButton btnAssignment = new ToggleButton("Assignments");
    ToggleButton btnCalendar = new ToggleButton("Calendar");
    ToggleButton btnDashboard = new ToggleButton("Dashboard");
    ToggleButton btnCourses = new ToggleButton("Courses");

    BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage stage) {

        borderPane.setLeft(leftBoxMenu());


        borderPane.setTop(topPane());

        StackPane centerPane = new StackPane();
        //changeCenterPanel("Home");
        borderPane.setCenter(centerPane);
        /*CenterPage centerPage = new CenterPage();
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(centerPage.createCenterPanel());*/

        centerPane.setMaxWidth(Double.MAX_VALUE);
        centerPane.setMaxHeight(Double.MAX_VALUE);

        borderPane.setCenter(centerPane);

        Scene scene = new Scene(borderPane, 1024, 600);

        stage.setScene(scene);
        stage.setTitle("StudyBuddy");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth()-1024)/2);
        stage.setX((Screen.getPrimary().getVisualBounds().getHeight()-768)/2);
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stage.show();
    }

    //Method that changes center Panel
    private void changeCenterPanel(String panelName) {
        ExamPage examPage = new ExamPage();
        StackPane centerPane = new StackPane();

        Dashboard dashboard = new Dashboard();
        StackPane dashBoard = new StackPane();

        Calendar calendar = new Calendar();
        StackPane calendarPage = new StackPane();

        AssignmentPage assignmentPage = new AssignmentPage();
        StackPane assignPage = new StackPane();

        HomePage homePage = new HomePage();
        StackPane homePane = new StackPane();

        // Depending on which Menu Button is pressed it changes the center Panel
        switch (panelName) {
            case "Home":
                centerPane.getChildren().setAll(homePage.home());
                break;
            case "Courses":
                break;
            case "Exam":
                centerPane.getChildren().setAll(examPage.createExamPanel());
                break;
            case "Assignments":
                centerPane.getChildren().setAll(assignmentPage.AssignmentPanel());
                break;
            case "Calendar":
                centerPane.getChildren().setAll(calendar.calendar());
                break;
            case "Dashboard":
                centerPane.getChildren().setAll(dashboard.createDashboard());
                break;
            default:
                break;
        }
        borderPane.setCenter(centerPane);
    }

    private HBox topPane() {
        HBox topPane = new HBox();

        topPane.setPadding(new Insets(0, 0, 50, 212));
        topPane.setStyle("-fx-background-color: #60f7b3; ");


        return topPane;
    }

    private VBox leftBoxMenu() {

        ToggleGroup btnGroup = new ToggleGroup();
        btnHome.setToggleGroup(btnGroup);
        btnCourses.setToggleGroup(btnGroup);
        btnExam.setToggleGroup(btnGroup);
        btnAssignment.setToggleGroup(btnGroup);
        btnCalendar.setToggleGroup(btnGroup);
        btnDashboard.setToggleGroup(btnGroup);

        VBox leftVBox = new VBox(15);
        leftVBox.setPrefWidth(212);
        leftVBox.setMinWidth(212); //or 88.33
        leftVBox.setMaxWidth(212);
        leftVBox.setStyle(/*"-fx-padding: 68 0 0 0;"
                + */"-fx-background-color: #F7B267; ");


        leftVBox.setMaxHeight(Double.MAX_VALUE);

        //Button Styles
        String btnStyle = "-fx-border-color: #F7B267; "
                + "-fx-background-color: #F7B267; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px; ";

        String btnInsideStyle = "-fx-border-color: #F7B267; "
                + "-fx-background-color: #F7B267; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px 20px; ";

        String btnSelected = "-fx-border-color: #F9C288; "
                + "-fx-background-color: #F9C288; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px; ";

        String btnInsideSelected = "-fx-border-color: #F9C288; "
                + "-fx-background-color: #F9C288; "
                + "-fx-border-width: 1px; "
                + "fx-text-fill: black; "
                + "fx-font-size: 14px; "
                + "fx-alignment: CENTER-LEFT; "
                + " -fx-border-radius: 5px; "
                + "-fx-padding: 10px 20px; ";

        //User Image
        Image userImg = new Image(getClass().getResource("/user.png").toExternalForm());
        ImageView userImgView = new ImageView(userImg);

        userImgView.setFitWidth(20);
        userImgView.setFitHeight(20);

        //logo Image
        Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
        ImageView logoImgView = new ImageView(logoImg);

        logoImgView.setFitWidth(200);
        logoImgView.setPreserveRatio(true);

        //arrow Images
        Image arrowRight = new Image(getClass().getResource("/arrowRight.png").toExternalForm());
        Image arrowDown = new Image(getClass().getResource("/arrowDown.png").toExternalForm());

        ImageView arrowIconHome = new ImageView(arrowRight);
        arrowIconHome.setFitWidth(20);
        arrowIconHome.setFitHeight(20);

        ImageView arrowIconCourses = new ImageView(arrowRight);
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

        //User Img btn
        Label userNameLbl = new Label("UserName"); // dynamic name
        userNameLbl.setStyle(btnStyle);

        HBox userImgBtn = new HBox(10);
        userImgBtn.getChildren().addAll(userImgView, userNameLbl);
        userImgBtn.setAlignment(Pos.CENTER_LEFT);


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



        btnHome.setOnAction(e -> changeCenterPanel("Home"));
        btnExam.setOnAction(e -> changeCenterPanel("Exam"));
        btnAssignment.setOnAction(e -> changeCenterPanel("Assignments"));
        btnCalendar.setOnAction(e -> changeCenterPanel("Calendar"));
        btnDashboard.setOnAction(e -> changeCenterPanel("Dashboard"));



        btnHome.setStyle(btnStyle);
        btnExam.setStyle(btnInsideStyle);
        btnAssignment.setStyle(btnInsideStyle);
        btnCalendar.setStyle(btnStyle);
        btnDashboard.setStyle(btnStyle);
        btnCourses.setStyle(btnStyle);

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


        VBox optionVBox = new VBox(15);
        optionVBox.setVisible(false);
        btnCourses.setOnAction(e -> {
            if (optionVBox.getChildren().isEmpty()) {
                optionVBox.getChildren().addAll(btnExam, btnAssignment);
                optionVBox.setVisible(true);
                arrowIconCourses.setImage(arrowDown);

                if (!leftVBox.getChildren().contains(optionVBox)) {
                    leftVBox.getChildren().add(2, optionVBox);
                    VBox.setMargin(btnExam, new Insets(0, 20, 0, 20));
                    VBox.setMargin(btnAssignment, new Insets(0, 20, 0, 20));
                }
            } else {
                optionVBox.getChildren().clear();
                optionVBox.setVisible(false);
                leftVBox.getChildren().remove(optionVBox);
                arrowIconCourses.setImage(arrowRight);
            }
        });


        Region logoSpacer = new Region();
        VBox.setVgrow(logoSpacer, Priority.ALWAYS);

        HBox logoBox = new HBox(logoImgView);
        logoBox.setAlignment(Pos.CENTER);
        leftVBox.getChildren().addAll(
                userImgBtn,
                btnHome,
                btnCourses,
                btnCalendar,
                btnDashboard,
                logoSpacer,
                logoImgView
        );

        return leftVBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}