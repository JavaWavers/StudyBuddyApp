package org.javawavers.studybuddy.uiux;

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
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

/**
 * The HomePage class is responsible for creating and displaying the main page of the application.
 * It includes the layout of the main screen, graphical components, and the navigation management
 * between different scenes.
 */
public class HomePage {
  private SceneManager sceneManager;
  private HBox navBar;
  private VBox rightPanel;
  private VBox leftPanel;

  /**
   * Creates and returns the home page scene.
   *
   * @param sceneManager An instance of {@link SceneManager} that handles scene transitions.
   * @return A {@link Scene} object representing the home page, or null in case of an error.
   */
  public Scene home(SceneManager sceneManager) {
    try {
      this.sceneManager = sceneManager;

      VBox home = new VBox();

      navBar = navBar();
      if (navBar == null) {
        throw new IllegalStateException("Το navBar δεν δημιουργήθηκε σωστά!");
      }

      HBox mainPane = new HBox();
      mainPane.setFillHeight(true);
      HBox.setHgrow(mainPane, Priority.ALWAYS);

      mainPanel();
      mainPane = mainPanel();

      home.getChildren().addAll(navBar, mainPane);
      return new Scene(
          home,
          Screen.getPrimary().getVisualBounds().getWidth(),
          Screen.getPrimary().getVisualBounds().getHeight());
    } catch (IllegalStateException e) {
      System.err.println("Δεν μπορεί να δημιουργηθεί η αρχική: " + e.getMessage());
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      System.err.println("Απροσδόκητο σφάλμα: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates the main panel of the home page, which contains the left and right panels.
   *
   * @return An {@link HBox} containing the main content of the page.
   */
  private HBox mainPanel() {
    HBox centralHBox = new HBox(20);
    centralHBox.setFillHeight(true);
    HBox.setHgrow(centralHBox, Priority.ALWAYS);

    leftPanel = leftVBox();
    rightPanel = rightVBox();

    try {
      if (leftPanel == null || rightPanel == null) {
        throw new IllegalStateException("Ένα από τα VBoxes στο main pane είναι άδειο!");
      }
      centralHBox.getChildren().addAll(leftPanel, rightPanel);
    } catch (IllegalStateException e) {
      System.err.println("Σφάλμα κατά την προσθήκη των παιδιών στο mainPane: " + e.getMessage());
      e.printStackTrace();
    }

    Region upSpacer = new Region();
    Region downSpacer = new Region();
    VBox.setVgrow(upSpacer, Priority.ALWAYS);
    VBox.setVgrow(downSpacer, Priority.ALWAYS);

    return centralHBox;
  }

  /**
   * Creates the left panel, which includes the welcome message,
   * a brief description, and a button for users to proceed.
   *
   * @return A {@link VBox} containing the content of the left panel.
   */
  private VBox leftVBox() {

    Label welcomeLabel = new Label("Γεια σου, \nΚαλώς όρισες στο \nStudy Buddy σου!");
    welcomeLabel.setFont(new Font("Arial Narrow Bold Italic", 44));

    welcomeLabel.setEffect(new SepiaTone());
    welcomeLabel.setPadding(new Insets(10));
    welcomeLabel.setStyle("-fx-text-fill: black;");
    welcomeLabel.setCursor(Cursor.TEXT);

    Label label1 = new Label("#1 εργαλείο οργάνωσης διαβάσματος");
    label1.setFont(new Font("Arial Narrow Bold", 24));

    Button tryButton = new Button("Δοκίμασε το!");
    tryButton.setPrefWidth(150);
    tryButton.setPrefHeight(40);
    tryButton.setStyle(tryBtnStyle());

    tryButton.setTextFill(javafx.scene.paint.Color.WHITE);

    tryButton.setOnAction(
        event -> {
          RegisterPage register = new RegisterPage();
          sceneManager.switchScene(register.register(sceneManager));
        });

    VBox welcomeBox = new VBox(10);
    welcomeBox.setPadding(new Insets(30));
    welcomeBox.setAlignment(Pos.CENTER_LEFT);
    welcomeBox.setFillWidth(true);
    VBox.setVgrow(welcomeBox, Priority.ALWAYS);
    welcomeBox.getChildren().addAll(welcomeLabel, label1, tryButton);

    VBox leftVBox = new VBox(10);
    leftVBox.getChildren().addAll(welcomeBox);
    leftVBox.setFillWidth(true);
    VBox.setVgrow(leftVBox, Priority.ALWAYS);
    leftVBox.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

    leftVBox.setAlignment(Pos.CENTER_LEFT);
    return leftVBox;
  }

  /**
   * Creates the right panel, which displays an image.
   *
   * @return A {@link VBox} containing the content of the right panel.
   */
  private VBox rightVBox() {
    HBox imgVBox = new HBox(10);

    ImageView pcImg;
    try {
      Image logoWithPC = new Image(getClass().getResource("/logoWithPC.png").toExternalForm());
      pcImg = new ImageView(logoWithPC);
      pcImg.setFitWidth(500);
      pcImg.setPreserveRatio(true);
      pcImg.setRotationAxis(Rotate.Y_AXIS);
      pcImg.setRotate(-30);
    } catch (NullPointerException e) {
      System.err.println("Δεν βρέθηκε η εικόνα: /logoWithPC.png");
      pcImg = new ImageView();
    }

    imgVBox.setFillHeight(true);
    HBox.setHgrow(imgVBox, Priority.ALWAYS);
    imgVBox.setPadding(new Insets(30));
    imgVBox.setAlignment(Pos.BASELINE_CENTER);
    imgVBox.getChildren().add(pcImg);

    VBox rightVBox = new VBox(10);
    rightVBox.setFillWidth(true);
    VBox.setVgrow(rightVBox, Priority.ALWAYS);
    rightVBox.setAlignment(Pos.BASELINE_CENTER);
    rightVBox.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
    rightVBox.getChildren().addAll(imgVBox);
    return rightVBox;
  }

  /**
   * Creates the navigation bar of the home page.
   *
   * @return An {@link HBox} containing the navigation bar elements.
   */
  private HBox navBar() {

    navBar = new HBox();

    navBar.setStyle("-fx-background-color: rgba(255, 200, 140, 0.81);");
    navBar.setPadding(new Insets(20));

    ImageView logoImgView;
    try {
      Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
      logoImgView = new ImageView(logoImg);
      logoImgView.setFitWidth(100);
      logoImgView.setPreserveRatio(true);
    } catch (NullPointerException e) {
      System.err.println("Δεν βρέθηκε η εικόνα: /logo.png");
      logoImgView = new ImageView();
    }

    HBox imgBox = new HBox(15);
    HBox.setHgrow(imgBox, Priority.ALWAYS);
    imgBox.getChildren().add(logoImgView);

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

    Button logInBtn = new Button("Συνδέσου εδώ");
    logInBtn.setStyle(loginBtnStyle());

    logInBtn.setOnAction(
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
    loginBox.getChildren().addAll(loginBtnSpacer, logInBtn);

    navBar.getChildren().addAll(imgBox, mainBtns, loginBox);
    return navBar;
  }

  /**
   * Returns the CSS style for the "Log In" button.
   *
   * @return A {@link String} containing CSS rules for the button style.
   */
  private String loginBtnStyle() {
    return "-fx-font-family: 'System'; "
        + "-fx-font-size: 14 px; "
        + "-fx-font-weight: bold; "
        + "-fx-text-fill: white; "
        + "-fx-background-color: rgba(101, 225, 101, 0.9); "
        + "-fx-background-radius: 30px; "
        + "-fx-border-radius: 30px; "
        + "-fx-border-color: black;";
  }

  /**
   * Returns the CSS style for the navigation bar buttons.
   *
   * @return A {@link String} containing CSS rules for the button style.
   */
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

  /**
   * Returns the CSS style for the "Try it!" button in the left panel.
   *
   * @return A {@link String} containing CSS rules for the button style.
   */
  private String tryBtnStyle() {
    return "-fx-background-color: rgba(181, 99, 241, 0.81);"
        + " -fx-background-radius: 30px; "
        + "-fx-border-radius: 30px;"
        + " -fx-border-color: black;"
        + " -fx-font-size: 18;"
        + "-fx-font-weight: bold; ";
  }
}
