package org.javawavers.studybuddy.ui_ux;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class HomePage {
  private SceneManager sceneManager;
  private HBox navBar;

  public Scene home(SceneManager sceneManager) {
    VBox home = new VBox();

    navBar = navBar();

    // Main content pane
    HBox mainPane = new HBox();

    VBox leftVBox = new VBox();

    Label welcomeLabel = new Label("Γεια σου, \nΚαλώς όρισες στο \nStudy Buddy σου!");
    welcomeLabel.setFont(new Font("Arial Narrow Bold Italic", 28));

    welcomeLabel.setEffect(new SepiaTone());
    welcomeLabel.setPadding(new Insets(10));
    welcomeLabel.setStyle("-fx-text-fill: black;");
    welcomeLabel.setCursor(Cursor.TEXT);

    Label label1 = new Label("#1 εργαλείο οργάνωσης διαβάσματος");
    label1.setFont(new Font("Arial Narrow Bold", 14));

    Button tryButton = new Button("Δοκίμασε το!");
    tryButton.setStyle(
        "-fx-background-color: rgba(181, 99, 241, 0.81); -fx-background-radius: 30px; "
            + "-fx-border-radius: 30px; -fx-border-color: black;");
    tryButton.setTextFill(javafx.scene.paint.Color.WHITE);

    tryButton.setOnAction(
        event -> {
          RegisterPage register = new RegisterPage();
          sceneManager.switchScene(register.register(sceneManager));
        });

    VBox welcomeBox = new VBox(10);
    welcomeBox.setAlignment(Pos.CENTER);
    welcomeBox.getChildren().addAll(welcomeLabel, label1, tryButton);

    Region upSpacer = new Region();
    Region downSpacer = new Region();

    HBox.setHgrow(upSpacer, Priority.ALWAYS);
    HBox.setHgrow(downSpacer, Priority.ALWAYS);

    leftVBox.getChildren().addAll(upSpacer, welcomeBox, downSpacer);

    ImageView pcImg =
        new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
    pcImg.setFitWidth(300);
    pcImg.setPreserveRatio(true);

    VBox rightVBox = new VBox();
    rightVBox.getChildren().addAll(pcImg);

    HBox.setHgrow(leftVBox, Priority.ALWAYS);
    HBox.setHgrow(rightVBox, Priority.ALWAYS);
    // VBox.setVgrow(leftVBox, Priority.ALWAYS);

    leftVBox.setPrefWidth(mainPane.getWidth() / 2);
    rightVBox.setPrefWidth(mainPane.getWidth() / 2);

    leftVBox.setAlignment(Pos.CENTER);
    rightVBox.setAlignment(Pos.CENTER);

    mainPane.getChildren().addAll(leftVBox, rightVBox);

    // Add components to root
    home.getChildren().addAll(navBar, mainPane);
    return new Scene(
        home,
        Screen.getPrimary().getVisualBounds().getWidth(),
        Screen.getPrimary().getVisualBounds().getHeight());
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

  public HBox navBar() {
    if (navBar == null) {
      navBar = new HBox();
    } else {
      HBox.setHgrow(navBar, Priority.ALWAYS);
    }

    navBar.setStyle("-fx-background-color: rgba(255, 200, 140, 0.81);");
    navBar.setPadding(new Insets(20));

    ImageView logoImg =
        new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
    logoImg.setFitWidth(80);
    logoImg.setPreserveRatio(true);
    HBox imgBox = new HBox(15);
    HBox.setHgrow(imgBox, Priority.ALWAYS);
    // imgBox.setAlignment(Pos.CENTER_RIGHT);
    imgBox.getChildren().add(logoImg);

    // Navigation items
    Button btnSeeHow = new Button("Δες Πως");
    btnSeeHow.setStyle(btnStyle());

    Button btnNewsTips = new Button("Νέα και Συμβουλές");
    btnNewsTips.setStyle(btnStyle());

    Button btnAboutUs = new Button("Ποιοι είμαστε");
    btnAboutUs.setStyle(btnStyle());

    HBox mainBtns = new HBox(15);
    HBox.setHgrow(mainBtns, Priority.ALWAYS);
    mainBtns.setAlignment(Pos.CENTER);
    mainBtns.getChildren().addAll(btnSeeHow, btnAboutUs, btnNewsTips);

    Button btnLogin = new Button("Συνδέσου εδώ");
    btnLogin.setStyle(
        "-fx-font-family: 'System'; "
            + "-fx-font-size: 14 px; "
            + "-fx-font-weight: bold; "
            + "-fx-text-fill: white; "
            + "-fx-background-color: rgba(101, 225, 101, 0.9); "
            + "-fx-background-radius: 30px; "
            + "-fx-border-radius: 30px; "
            + "-fx-border-color: black;");

    btnLogin.setOnAction(
        event -> {
          LoginPage login = new LoginPage();
          sceneManager.switchScene(login.login(sceneManager));
        });

    Region loginBtnSpacer = new Region();
    HBox.setHgrow(loginBtnSpacer, Priority.ALWAYS);

    HBox loginBox = new HBox(40);
    HBox.setHgrow(loginBox, Priority.ALWAYS);
    loginBox.setAlignment(Pos.CENTER_LEFT);
    loginBox.setPadding(new Insets(20));
    loginBox.getChildren().addAll(loginBtnSpacer, btnLogin);

    navBar.getChildren().addAll(imgBox, mainBtns, loginBox);
    return navBar;
  }
}
