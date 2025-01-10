package org.javawavers.studybuddy;




import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.javawavers.studybuddy.ui_ux.HomePage;
import org.javawavers.studybuddy.ui_ux.SceneManager;

/* 
 * TODO:
 *  fix courses menu (attention Home btn)
 *  scroll bar
 *  username btn alignment
 */ 
public class StudyBuddyApp extends Application { //exam page

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);

        HomePage homePage = new HomePage();
        Scene homeScene = homePage.home(sceneManager);
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