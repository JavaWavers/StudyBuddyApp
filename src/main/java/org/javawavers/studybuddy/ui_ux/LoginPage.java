package org.javawavers.studybuddy.ui_ux;

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
import org.javawavers.studybuddy.database.ActiveUser;

public class LoginPage {

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

    Scene scene =
        new Scene(
            loginPage,
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
    rightVBox
        .getChildren()
        .addAll(upSpacer, welcomeText, messageLine1, messageLine2, signupButton, downSpacer);

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
    Image notseeImage2 = new Image(getClass().getResource("/notseePassword.png").toExternalForm());
    ImageView userImgView = new ImageView(seeImage1);
    userImgView.setFitWidth(30);
    userImgView.setFitHeight(30);

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

    HBox passwordBox = new HBox(10, passwordField, textField, toggleButton);

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
    leftVBox
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

    leftPane.getChildren().addAll(leftSpacer, leftVBox, rightSpacer);
  }

  private void setupEventHandlers() {
    signupButton.setOnAction(
        event -> {
          RegisterPage registerPage = new RegisterPage();
          sceneManager.switchScene(registerPage.register(sceneManager));
        });

    loginButton.setOnAction(
        event -> {
          validateLogin();
          clearFields();
          if (validateLogin()) {
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
      alert.setContentText(
          "Ωπ, κάτι ξέχασες! Ρίξε μια ματιά και συμπλήρωσε όλα τα απαραίτητα πεδία.");
      alert
          .getDialogPane()
          .getStylesheets()
          .add(getClass().getResource("/alert.css").toExternalForm());
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
          .add(getClass().getResource("/alert.css").toExternalForm());
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
