package main.java.org.javawavers.studybuddy.ui_ux;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomePage {
    public Scene home(SceneManager sceneManager) {
        VBox home = new VBox();
        // Navigation Bar
        HBox navBar = new HBox(10);
        navBar.setPrefSize(901, 86);
        navBar.setStyle("-fx-background-color: #40E0D0;");
        navBar.setPadding(new Insets(10, 0, 0, 30));

        // Navigation items
        Label logo = new Label("StudyBuddy");
        logo.setFont(new Font("Agency FB Bold", 26));
        logo.setTextFill(javafx.scene.paint.Color.web("#f2eded"));
        logo.setPadding(new Insets(20, 0, 0, 20));

        Button btnSeeHow = new Button("Δες Πως");
        btnSeeHow.setFont(new Font(11));
        btnSeeHow.setStyle("-fx-font-weight: bold;");
        btnSeeHow.setPrefSize(167, 42);
        btnSeeHow.setPadding(new Insets(20, 0, 0, 80));

        Button btnNewsTips = new Button("Νέα και Συμβουλές");
        btnNewsTips.setStyle("-fx-font-weight: bold;");
        btnNewsTips.setPrefSize(218, 41);

        Button btnAboutUs = new Button("Ποιοι είμαστε");
        btnAboutUs.setStyle("-fx-font-weight: bold;");
        btnAboutUs.setPrefSize(195, 42);
        btnAboutUs.setPadding(new Insets(20, 50, 0, 0));

        Button btnLogin = new Button("Συνδέσου εδώ");
        btnLogin.setStyle("-fx-font-weight: bold; -fx-background-color: #d7ad6e;");
        btnLogin.setTextFill(javafx.scene.paint.Color.web("#fcfaf8"));
        btnLogin.setPrefSize(186, 41);

        btnLogin.setOnAction(event -> {
            LoginPage login = new LoginPage();
            //Stage loginStage = new Stage();
            sceneManager.switchScene(login.login(sceneManager));
            //loginStage.setMaximized(true);


//κλεινουμε το παραθυρο που ειναι ανοιχτο 
            //Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            //currentStage.close();
            
            //loginStage.show();
        });

        navBar.getChildren().addAll(logo, btnSeeHow, btnNewsTips, btnAboutUs, btnLogin);

        // Main content pane
        Pane mainPane = new Pane();
        mainPane.setLayoutY(86);
        mainPane.setPrefSize(902, 526);

        ImageView imageView = new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
        imageView.setFitHeight(340);
        imageView.setFitWidth(395);
        imageView.setLayoutX(451);
        imageView.setLayoutY(61);

        Label label1 = new Label("#1 εργαλείο οργάνωσης διαβάσματος");
        label1.setFont(new Font("Arial Narrow Bold", 14));
        label1.setPrefSize(237, 32);
        label1.setLayoutX(116);
        label1.setLayoutY(262);

        Button tryButton = new Button("Δοκίμασε το!");
        tryButton.setStyle("-fx-background-color: #dc52b0;");
        tryButton.setTextFill(javafx.scene.paint.Color.WHITE);
        tryButton.setLayoutX(154);
        tryButton.setLayoutY(308);

        tryButton.setOnAction(event -> {
            RegisterPage register = new RegisterPage();
            sceneManager.switchScene(register.register(sceneManager));
        });

        Label welcomeLabel = new Label("Γεια σου, \nΚαλώς όρισες στο \nStudy Buddy σου!");
        welcomeLabel.setFont(new Font("Arial Narrow Bold Italic", 28));
        welcomeLabel.setLayoutX(46);
        welcomeLabel.setLayoutY(141);
        welcomeLabel.setPrefSize(345, 137);
        welcomeLabel.setEffect(new SepiaTone());
        welcomeLabel.setPadding(new Insets(10));
        welcomeLabel.setStyle("-fx-text-fill: black;");
        welcomeLabel.setCursor(Cursor.TEXT);

        ImageView arrowImage = new ImageView(new Image(getClass().getResource("/arrowDown.png").toExternalForm()));
        arrowImage.setFitHeight(42);
        arrowImage.setFitWidth(33);
        arrowImage.setLayoutX(434);
        arrowImage.setLayoutY(476);

        mainPane.getChildren().addAll(imageView, label1, tryButton, welcomeLabel, arrowImage);

        // Add components to root
        home.getChildren().addAll(navBar,mainPane);
        return new Scene(home, 1024, 600);
    }
}
