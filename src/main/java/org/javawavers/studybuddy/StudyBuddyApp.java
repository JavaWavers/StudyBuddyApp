package org.javawavers.studybuddy;

<<<<<<< HEAD

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

=======
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.javawavers.studybuddy.database.DatabaseManager;

public class StudyBuddyApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       // DatabaseManager.CreateTables();
        launch(args);
    }
>>>>>>> 89a5bd7f0f8c995119e068b6f432a6dbcf80352d
}
*/


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.javawavers.studybuddy.ui_ux.HomePage;
import org.javawavers.studybuddy.ui_ux.Popupdia;

/*
 * TODO:
 *  fix courses menu (attention Home btn)
 *  scroll bar
 *  username btn alignment
 */
public class StudyBuddyApp extends Application { //exam page




    @Override
    public void start(Stage stage) {
        Popupdia.SceneManager sceneManager = new Popupdia.SceneManager(stage);

        HomePage homePage = new HomePage();
        Scene homeScene = homePage.home(sceneManager);
        /*


        Scene scene = new Scene(borderPane, 1024, 600);

        stage.setScene(scene);*/
        stage.setScene(homeScene);
        stage.setTitle("StudyBuddy");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
        stage.setX((Screen.getPrimary().getVisualBounds().getHeight()) / 2);
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}