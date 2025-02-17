package org.javawavers.studybuddy.uiux;

import java.util.Objects;

import org.javawavers.studybuddy.database.ActiveUser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;



/**
 * This class represents the login page of the application.
 * It contains the layout for the login form and a welcome message.
 */
public class LoginPage {

  private HBox rightPane;
  private HBox leftPane;
  private TextField emailField;
  private PasswordField passwordField;
  private TextField textField;
  private Button loginButton;
  private Button signupButton;
  private SceneManager sceneManager;

  /**
   * Initializes and returns the login page scene.
   * This method sets up the left and right panes, as well as event handlers.
   *
   * @param sceneManager the scene manager used for scene switching
   * @return the login page scene
   */
  public Scene login(SceneManager sceneManager) {
    this.sceneManager = sceneManager;

    initRightPane();
    initLeftPane();
    setupEventHandlers();

    HBox loginPage = new HBox();
    loginPage.getChildren().addAll(leftPane, rightPane);
    HBox.setHgrow(leftPane, Priority.ALWAYS);
    HBox.setHgrow(rightPane, Priority.ALWAYS);
    leftPane.setMinWidth(300);
    rightPane.setMinWidth(300);
    leftPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
    rightPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);


    return new Scene(
      loginPage,
      Screen.getPrimary().getVisualBounds().getWidth(),
      Screen.getPrimary().getVisualBounds().getHeight());
  }

  private void initRightPane() {
    rightPane = new HBox();
    rightPane.setStyle("-fx-background-color: #65E165CF;");
    rightPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

    VBox rightVbox = new VBox(15);
    rightVbox.setAlignment(Pos.CENTER);
    rightVbox.setPadding(new Insets(30));
    rightVbox.setFillWidth(true);
    rightVbox.setMaxHeight(Double.MAX_VALUE);

    Text welcomeText = new Text("Καλώς ήρθες!");
    welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 60));
    welcomeText.setStyle("-fx-fill: black;");

    Text messageLine1 = new Text("Έτοιμος να κάνεις το διάβασμα σου πιο ");
    messageLine1.setFont(Font.font(14));

    Text messageLine2 = new Text("έξυπνο και αποτελεσματικό;");
    messageLine2.setFont(Font.font("Arial", 14));

    signupButton = new Button("Εγγραφή εδώ");
    signupButton.setStyle(
        "-fx-font-family: 'System'; "
            + "-fx-font-size: 14px; "
            + "-fx-font-weight: bold; "
            + "-fx-text-fill: white; "
            + "-fx-background-color: #801EC8E6; "
            + "-fx-background-radius: 30px; "
            + "-fx-border-radius: 30px; "
            + "-fx-border-color: black;");

    rightPane
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              messageLine2.setFont(Font.font("Arial", newSize));
              messageLine1.setFont(Font.font("Arial", newSize));
              welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              signupButton.setStyle(
                  "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-text-fill: white; "
                      + "-fx-background-color: #801EC8E6; "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-border-color: black;");
            });

    rightPane
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              messageLine2.setFont(Font.font("Arial", newSize));
              messageLine1.setFont(Font.font("Arial", newSize));
              welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              signupButton.setStyle(
                  "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-text-fill: white; "
                      + "-fx-background-color: #801EC8E6; "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-border-color: black;");
            });

    Region upSpacer = new Region();
    Region downSpacer = new Region();
    VBox.setVgrow(upSpacer, Priority.ALWAYS);
    VBox.setVgrow(downSpacer, Priority.ALWAYS);
    rightVbox
        .getChildren()
        .addAll(upSpacer, welcomeText, messageLine1, messageLine2, signupButton, downSpacer);

    Region leftSpacer = new Region();
    Region rightSpacer = new Region();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);
    VBox.setVgrow(rightVbox, Priority.ALWAYS);

    rightPane.getChildren().addAll(leftSpacer, rightVbox, rightSpacer);
  }

  private void initLeftPane() {
    leftPane = new HBox();
    leftPane.setStyle("-fx-background-color: #B563F1;");
    leftPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

    VBox leftVbox = new VBox(15);
    leftVbox.setAlignment(Pos.CENTER);
    leftVbox.setPadding(new Insets(30));
    leftVbox.setFillWidth(true);
    leftVbox.setMaxHeight(Double.MAX_VALUE);

    Text joinText = new Text("Συνδέσου ξανά!");
    joinText.setFont(Font.font("Arial", FontWeight.BOLD, 60));

    Label emailLabel = new Label("Email:");
    emailLabel.setFont(new Font(14));

    emailField = new TextField();
    emailField.setPromptText("Enter your email");

    Label passwordLabel = new Label("Password:");
    passwordLabel.setFont(new Font(14));

    passwordField = new PasswordField();
    passwordField.setPromptText("Enter your password");

    textField = new TextField();
    textField.setManaged(false);
    textField.setVisible(false);

    Button toggleButton = new Button();
    Image seeImage1 = new Image(getClass().getResource("/seePassword.png").toExternalForm());

    ImageView userImgView = new ImageView(seeImage1);
    userImgView.setFitWidth(30);
    userImgView.setFitHeight(30);

    Image notseeImage2 = new Image(getClass().getResource("/notseePassword.png").toExternalForm());
    toggleButton.setGraphic(userImgView);
    toggleButton.setStyle("-fx-font-size: 12px;");
    toggleButton.setOnAction(
        e -> {
          if (userImgView.getImage().equals(seeImage1)) {
            userImgView.setImage(notseeImage2);
          } else {
            userImgView.setImage(seeImage1);
          }
          PasswordVisibility();
        });

    loginButton = new Button("Συνδέσου");
    loginButton.setStyle(
        "-fx-font-family: 'System'; "
            + "-fx-font-size: 14px; "
            + "-fx-font-weight: bold; "
            + "-fx-text-fill: white; "
            + "-fx-background-color:  rgba(14, 164, 43, 0.81); "
            + "-fx-background-radius: 30px; "
            + "-fx-border-radius: 30px; "
            + "-fx-border-color: black;");

    leftPane
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              emailLabel.setFont(Font.font("Arial", newSize));
              emailField.setStyle("-fx-font-size: " + newSize + "px;");
              passwordLabel.setFont(Font.font("Arial", newSize));
              passwordField.setStyle("-fx-font-size: " + newSize + "px;");
              passwordField.setFont(Font.font("Arial", newSize));
              textField.setFont(Font.font("Arial", newSize));
              toggleButton.setStyle("-fx-font-size: " + newSize + "px;");
              loginButton.setStyle(
                  "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-background-color:  rgba(14, 164, 43, 0.81); "
                      + "-fx-text-fill: white; "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-border-color: black;");
            });

    leftPane
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              emailLabel.setFont(Font.font("Arial", newSize));
              emailField.setStyle("-fx-font-size: " + newSize + "px;");
              passwordLabel.setFont(Font.font("Arial", newSize));
              passwordField.setStyle("-fx-font-size: " + newSize + "px;");
              passwordField.setFont(Font.font("Arial", newSize));
              textField.setFont(Font.font("Arial", newSize));
              toggleButton.setStyle("-fx-font-size: " + newSize + "px;");
              loginButton.setStyle(
                  "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-text-fill: white; "
                      + "-fx-background-color:  rgba(14, 164, 43, 0.81); "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-border-color: black;");
            });

    Region upSpacer = new Region();
    Region downSpacer = new Region();
    VBox.setVgrow(upSpacer, Priority.ALWAYS);
    VBox.setVgrow(downSpacer, Priority.ALWAYS);

    HBox passwordBox = new HBox(10, passwordField, textField, toggleButton);
    leftVbox
        .getChildren()
        .addAll(
            upSpacer,
            joinText,
            emailLabel,
            emailField,
            passwordLabel,
            passwordBox,
            loginButton,
            downSpacer);

    Region leftSpacer = new Region();
    Region rightSpacer = new Region();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);

    leftPane.getChildren().addAll(leftSpacer, leftVbox, rightSpacer);
  }

  /**
   * Sets up the event handlers for the login and sign-up buttons.
   * Redirects to the registration page on sign-up button click.
   * Validates login credentials and switches to the main page on successful login.
   */
  private void setupEventHandlers() {
    signupButton.setOnAction(
        event -> {
          RegisterPage registerPage = new RegisterPage();
          sceneManager.switchScene(registerPage.register(sceneManager));
        });

    loginButton.setOnAction(
        event -> {
          //validateLogin();
          if (validateLogin()) {
            MainFrame mainFrame = new MainFrame();
            sceneManager.switchScene(mainFrame.mainFrame(sceneManager));
          }
          clearFields();
        });
  }

  private boolean validateLogin() {
    String email = emailField.getText();
    String password = passwordField.getText();
    System.out.println("activeUser: " + ActiveUser.authenticateUser(email, password));
    if (email.isEmpty() || password.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
      alert.setHeaderText(null);
      alert.setContentText(
          "Ωπ, κάτι ξέχασες! Ρίξε μια ματιά και συμπλήρωσε όλα τα απαραίτητα πεδία.");
      alert
          .getDialogPane()
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
      alert.getDialogPane().setMinWidth(500);
      alert.getDialogPane().setMinHeight(300);
      alert.showAndWait();

      return false;
    } else if (ActiveUser.authenticateUser(email, password) != null) {
      System.out.println(ActiveUser.authenticateUser(email, password));
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Σύνδεση Επιτυχής");
      alert.setHeaderText(null);
      alert.setContentText("Καλώς ήρθες!");
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStyleClass().add("success-alert");
      dialogPane.getStylesheets().add(getClass().getResource("/success.css").toExternalForm());
      ActiveUser.loadData(email, password);
      return true;
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Σφάλμα σύνδεσης");
      alert.setHeaderText(null);
      alert.setContentText("Λάθος email ή κωδικός πρόσβασης.");
      alert
          .getDialogPane()
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
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

  /**
   * Toggles the visibility of the password field.
   * Changes the password field between hidden and visible.
   */
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
