package org.javawavers.studybuddy.uiux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import org.javawavers.studybuddy.courses.StaticUser;
import org.javawavers.studybuddy.courses.User;
import org.javawavers.studybuddy.database.ActiveUser;
import org.javawavers.studybuddy.database.DataInserter;

/**
 * RegisterPage class is responsible for handling the user interface
 * and logic of the registration page.
 */
public class RegisterPage {

  public static String storedEmail = "";
  public static String storedPassword = "";
  public static String storedUsername = "Guest";

  private HBox rightPane;
  private HBox leftPane;
  private TextField emailField;
  private PasswordField passwordField;
  private TextField textField;
  private Button loginButton;
  private Button registerButton;
  private TextField nameField;
  private TextField confirmPasswordTextField;
  private PasswordField confirmPasswordField;
  private SceneManager sceneManager;

  /**
   * Constructs and returns the registration scene.
   *
   * @param sceneManager The SceneManager to handle scene switching.
   * @return The scene for the registration page.
   */
  public Scene register(SceneManager sceneManager) {
    this.sceneManager = sceneManager;

    initRightPane();
    initLeftPane();
    setupEventHandlers();

    HBox registerPage = new HBox();
    registerPage.getChildren().addAll(leftPane, rightPane);
    HBox.setHgrow(leftPane, Priority.ALWAYS);
    HBox.setHgrow(rightPane, Priority.ALWAYS);
    leftPane.setMinWidth(300);
    rightPane.setMinWidth(300);
    leftPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);
    rightPane.setPrefWidth((Screen.getPrimary().getVisualBounds().getWidth()) / 2);

    return new Scene(
      registerPage,
      Screen.getPrimary().getVisualBounds().getWidth(),
      Screen.getPrimary().getVisualBounds().getHeight());
  }

  private void initRightPane() {
    rightPane = new HBox();
    rightPane.setStyle("-fx-background-color: #B563F1;");
    rightPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

    VBox rightVbox = new VBox(15);
    rightVbox.setAlignment(Pos.CENTER);
    rightVbox.setPadding(new Insets(30));
    rightVbox.setFillWidth(true);
    rightVbox.setMaxHeight(Double.MAX_VALUE);

    Text joinText = new Text("Έλα στην παρέα μας");
    joinText.setFont(new Font("System Bold", 28));

    Label nameLabel = new Label("Name:");
    nameLabel.setFont(new Font("System Bold", 14));

    nameField = new TextField();
    nameField.setPromptText("Enter your name");

    Label emailLabel = new Label("Email:");
    emailLabel.setFont(new Font("System Bold", 14));

    emailField = new TextField();
    emailField.setPromptText("Enter your email");

    Label passwordLabel = new Label("Password:");
    passwordLabel.setFont(new Font("System Bold", 14));

    passwordField = new PasswordField();
    passwordField.setPromptText("Enter your password");

    textField = new TextField();
    textField.setPromptText("Enter your password");

    textField.setManaged(false); // to start without the code being visible
    textField.setVisible(false);
    Image seeImage1 = new Image(getClass().getResource("/seePassword.png").toExternalForm());
    ImageView userImgView = new ImageView(seeImage1);
    userImgView.setFitWidth(32.5);
    userImgView.setFitHeight(32.5);


    Button toggleConfirmPasswordButton = new Button();

    Button toggleButton = new Button();
    toggleButton.setGraphic(userImgView);
    toggleButton.setStyle("-fx-font-size: 14px;");

    Image notseeImage2 = new Image(getClass().getResource("/notseePassword.png").toExternalForm());
    toggleButton.setOnAction(
        e -> {
          if (userImgView.getImage().equals(seeImage1)) {
            userImgView.setImage(notseeImage2);
          } else {
            userImgView.setImage(seeImage1);
          }
          PasswordVisibility();
        });

    // do the same for the confirmpassword
    Label confirmpasswordLabel = new Label("Confirm Password:");
    confirmpasswordLabel.setFont(new Font("System Bold", 14));

    confirmPasswordTextField = new TextField();
    confirmPasswordTextField.setPromptText("Confirm your password");

    confirmPasswordTextField.setManaged(false);
    confirmPasswordTextField.setVisible(false);

    confirmPasswordField = new PasswordField();
    confirmPasswordField.setPromptText("Confirm your password");

    toggleConfirmPasswordButton.setStyle("-fx-font-size: 14px;");
    Image seeImage = new Image(getClass().getResource("/seePassword.png").toExternalForm());
    ImageView userImgView1 = new ImageView(seeImage);
    userImgView1.setFitWidth(32.5);
    userImgView1.setFitHeight(32.5);
    toggleConfirmPasswordButton.setGraphic(userImgView1);

    Image notseeImage = new Image(getClass().getResource("/notseePassword.png").toExternalForm());
    toggleConfirmPasswordButton.setOnAction(
        e -> {
          if (userImgView1.getImage().equals(seeImage)) {
            userImgView1.setImage(notseeImage);
          } else {
            userImgView1.setImage(seeImage);
          }
          ConfPasswordVisibility();
        });

    // button for the registration
    registerButton = new Button("Εγγραφή");
    registerButton.setFont(new Font("System Bold", 14));

    rightPane
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              nameField.setFont(Font.font("Arial", newSize));
              nameLabel.setFont(Font.font("Arial", newSize));
              emailField.setFont(Font.font("Arial", newSize));
              emailLabel.setFont(Font.font("Arial", newSize));
              passwordField.setFont(Font.font("Arial", newSize));
              passwordLabel.setFont(Font.font("Arial", newSize));
              textField.setFont(Font.font("Arial", newSize));
              toggleButton.setFont(Font.font("Arial", newSize));
              toggleConfirmPasswordButton.setFont(Font.font("Arial", newSize));
              confirmPasswordField.setFont(Font.font("Arial", newSize));
              confirmpasswordLabel.setFont(Font.font("Arial", newSize));
              confirmPasswordTextField.setFont(Font.font("Arial", newSize));
              registerButton.setStyle(
                  "-fx-font-family: 'System';  "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-text-fill: white; "
                      + "-fx-background-color: rgba(14, 164, 43, 0.81); "
                      + "-fx-background-radius: 30px; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-border-radius: 30px;"
                      + " -fx-border-color: black;");
            });

    rightPane
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              joinText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              nameField.setFont(Font.font("Arial", newSize));
              nameLabel.setFont(Font.font("Arial", newSize));
              emailField.setFont(Font.font("Arial", newSize));
              emailLabel.setFont(Font.font("Arial", newSize));
              passwordField.setFont(Font.font("Arial", newSize));
              passwordLabel.setFont(Font.font("Arial", newSize));
              textField.setFont(Font.font("Arial", newSize));
              toggleButton.setFont(Font.font("Arial", newSize));
              toggleConfirmPasswordButton.setFont(Font.font("Arial", newSize));
              confirmPasswordField.setFont(Font.font("Arial", newSize));
              confirmPasswordTextField.setFont(Font.font("Arial", newSize));
              confirmpasswordLabel.setFont(Font.font("Arial", newSize));
              registerButton.setStyle(
                  "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; "
                      + "-fx-text-fill: white; "
                      + "-fx-background-color: rgba(14, 164, 43, 0.81); "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-border-color: black;");
            });

    Region upSpacer = new Region();
    Region downSpacer = new Region();
    VBox.setVgrow(upSpacer, Priority.ALWAYS);
    VBox.setVgrow(downSpacer, Priority.ALWAYS);
    // place all three of them in a HBox
    HBox passwordBox = new HBox(5, passwordField, textField, toggleButton);
    HBox confirmPasswordBox =
        new HBox(5, confirmPasswordField, confirmPasswordTextField, toggleConfirmPasswordButton);
    rightVbox
        .getChildren()
        .addAll(
            upSpacer,
            joinText,
            nameLabel,
            nameField,
            emailLabel,
            emailField,
            passwordLabel,
            passwordBox,
            confirmpasswordLabel,
            confirmPasswordBox,
            registerButton,
            downSpacer);

    Region leftSpacer = new Region();
    Region rightSpacer = new Region();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);
    VBox.setVgrow(rightVbox, Priority.ALWAYS);

    rightPane.getChildren().addAll(leftSpacer, rightVbox, rightSpacer);
  }

  private void initLeftPane() {
    leftPane = new HBox();
    leftPane.setStyle("-fx-background-color: #65E165CF;");
    leftPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

    VBox leftVbox = new VBox(15);
    leftVbox.setAlignment(Pos.CENTER);
    leftVbox.setPadding(new Insets(30));
    leftVbox.setFillWidth(true);
    leftVbox.setMaxHeight(Double.MAX_VALUE);

    Text welcomeText = new Text("Καλώς ήρθες ξανά!");
    welcomeText.setFont(new Font("System Bold", 28));

    Text messageText1 = new Text("Ας οργανώσουμε ξανά μαζί");
    messageText1.setFont(new Font(14));

    Text messageText2 = new Text("το χρόνο σου.");
    messageText2.setFont(new Font(14));

    // button for the login
    loginButton = new Button("Συνδέσου εδώ");
    loginButton.setFont(new Font("System Bold", 14));
    loginButton.setStyle(
        "-fx-background-color: #801EC8E6; -fx-background-radius: 30px; "
            + "-fx-border-radius: 30px; -fx-border-color: black;");

    leftPane
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              messageText1.setFont(Font.font("Arial", newSize));
              messageText2.setFont(Font.font("Arial", newSize));
              loginButton.setStyle(
                  "-fx-background-color: #801EC8E6; "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-text-fill: white; "
                      + "-fx-border-color: black;"
                      + "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; ");
            });

    leftPane
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              double newSize = newVal.doubleValue() / 30;
              welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, newSize));
              messageText1.setFont(Font.font("Arial", newSize));
              messageText2.setFont(Font.font("Arial", newSize));
              loginButton.setStyle(
                  "-fx-background-color: #801EC8E6; "
                      + "-fx-background-radius: 30px; "
                      + "-fx-border-radius: 30px; "
                      + "-fx-text-fill: white; "
                      + "-fx-border-color: black;"
                      + "-fx-font-family: 'System'; "
                      + "-fx-font-size: "
                      + newSize
                      + "px; "
                      + "-fx-font-weight: bold; ");
            });

    Region upSpacer = new Region();
    Region downSpacer = new Region();
    VBox.setVgrow(upSpacer, Priority.ALWAYS);
    VBox.setVgrow(downSpacer, Priority.ALWAYS);
    leftVbox
        .getChildren()
        .addAll(upSpacer, welcomeText, messageText1, messageText2, loginButton, downSpacer);

    Region leftSpacer = new Region();
    Region rightSpacer = new Region();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);

    leftPane.getChildren().addAll(leftSpacer, leftVbox, rightSpacer);
  }

  private void setupEventHandlers() {
    loginButton.setOnAction(
        event -> {
          LoginPage loginPage = new LoginPage();
          sceneManager.switchScene(loginPage.login(sceneManager));
        });

    registerButton.setOnAction(
        event -> {
          if (validateLogin()) {
            ExamPage examPage = new ExamPage();
            sceneManager.switchScene(examPage.examStartingPage(sceneManager));
            DataInserter.insertUser(storedUsername, storedPassword, storedEmail);
            StaticUser.staticUser.setUsername(storedUsername);
            StaticUser.staticUser.setPassword(storedPassword);
            StaticUser.staticUser.setEmail(storedEmail);
            StaticUser.staticUser.setUserId(ActiveUser.getUserId(storedEmail, storedPassword));
          }
        });
  }

  private boolean validateLogin() {
    storedUsername = nameField.getText();
    storedEmail = emailField.getText();
    storedPassword = passwordField.getText();

    // define the current user upon logging into the application
    User user = new User(storedUsername, storedEmail, storedPassword);

    StaticUser.staticUser = user;

    List<String> errors = new ArrayList<>();
    // error if the name is less than 4 characters
    if (storedUsername.isEmpty() || storedUsername.length() < 2) {
      System.out.println("storedUsername");
      errors.add("• Το όνομα πρέπει να έχει πάνω από έναν χαρακτήρα");
    } else if (!storedUsername.matches("[a-zA-Z0-9_α-ωΑ-ΩάέήίΰϊϋόύώΆΈΉΊΪΫΌΎΏ-]+")) {
      errors.add("• Το όνομα μπορεί να περιέχει μόνο γράμματα,αριθμούς,παύλες και κάτω παύλες");
    }
    // error if the email doesn't contain @
    if (storedEmail.isEmpty()
        || !storedEmail.matches("^[a-zA-Z0-9._%+-]{2,}@[a-zA-Z0-9.-]{2,}\\.[a-zA-Z]{2,}$")) {
      System.out.println("storedEmail");
      errors.add("• Εισήγαγε ένα έγκυρο email");
    }
    // error if the password is less than 6 characters
    if (storedPassword.isEmpty() || storedPassword.length() < 8) {
      System.out.println("storedPassword empty or length");
      errors.add("• Ο κωδικός πρόσβασης πρέπει να έχει πάνω από 8 χαρακτήρες");
    } else if (!storedPassword.matches(".*[A-Z].*")) {
      errors.add("• Ο Κωδικός πρόσβασης πρέπει να έχει τουλάχιστον ένα κεφαλαιό γράμμα");
    } else if (!storedPassword.matches(".*[a-z].*")) {
      errors.add("• Ο Κωδικός πρόσβασης πρέπει να έχει τουλάχιστον ένα πέζο γράμμα");
    } else if (!storedPassword.matches(".*\\d.*")) {
      errors.add("• Ο Κωδικός πρόσβασης πρέπει να έχει τουλάχιστον έναν αριθμό");
    } else if (!storedPassword.matches(".*[!@#$%^&+=].*")) {
      errors.add("• Ο Κωδικός πρόσβασης πρέπει να έχει τουλάχιστον έναν ειδικό χαρακτήρα");
    }
    String confirmPassword = confirmPasswordField.getText();
    // error if the confirmation code is not the same
    if (!storedPassword.equals(confirmPassword)) {
      System.out.println("storedpassword confirm");
      errors.add("• Οι κωδικοί που έβαλες δεν είναι ίδιοι");
    }
    // If there are errors, it displays the list to the user
    if (!errors.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
      alert.setHeaderText(null);
      String errorMessage = String.join("\n", errors);
      alert.setContentText(errorMessage);
      alert
          .getDialogPane()
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/alert.css")).toExternalForm());
      alert.showAndWait();
      return false;
    }
    // success message if there are no errors
    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
    successAlert.setTitle("Εγγραφή Επιτυχής");
    successAlert.setHeaderText(null);
    successAlert.setContentText("Η εγγραφή ολοκληρώθηκε με επιτυχία!");
    DialogPane dialogPane = successAlert.getDialogPane();
    dialogPane.getStyleClass().add("success-alert");
    dialogPane
        .getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource("/success.css")).toExternalForm());
    successAlert.showAndWait();
    return true;
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

  private void ConfPasswordVisibility() {
    if (confirmPasswordField.isVisible()) {
      confirmPasswordTextField.setText(confirmPasswordField.getText());
      confirmPasswordField.setVisible(false);
      confirmPasswordField.setManaged(false);
      confirmPasswordTextField.setVisible(true);
      confirmPasswordTextField.setManaged(true);
    } else {
      confirmPasswordField.setText(confirmPasswordTextField.getText());
      confirmPasswordTextField.setVisible(false);
      confirmPasswordTextField.setManaged(false);
      confirmPasswordField.setVisible(true);
      confirmPasswordField.setManaged(true);
    }
  }
}
