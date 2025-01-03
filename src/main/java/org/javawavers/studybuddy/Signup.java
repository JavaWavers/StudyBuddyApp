package org.javawavers.studybuddy;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Signup extends Application {

    public static String storedEmail = "";
    public static String storedPassword = "";
    public static String storedUsername = "admin";
    HBox hBox = new HBox();

    @Override
    public void start(Stage primaryStage) {

        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));

//οριζουμε το χρωμα για το αριστερο pane 
        AnchorPane leftPane = new AnchorPane();
        leftPane.setStyle("-fx-background-color: #30CFC2;");
        leftPane.setPrefWidth(295);
//στοιχεια του αριστερου pane
        Text welcomeText = new Text("Καλώς ήρθες ξανά!");
        welcomeText.setFont(Font.font(28));
        welcomeText.setLayoutX(18);
        welcomeText.setLayoutY(103);

        Text messageText1 = new Text("Ας οργανώσουμε ξανά μαζί");
        messageText1.setFont(new Font(14));
        messageText1.setLayoutX(18);
        messageText1.setLayoutY(127);

        Text messageText2 = new Text("το χρόνο σου.");
        messageText2.setFont(new Font(14));
        messageText2.setLayoutX(18);
        messageText2.setLayoutY(144);
//κουμπι logon
        Button loginButton = new Button("Συνδέσου εδώ");
        loginButton.setFont(new Font("System Bold", 14));
        loginButton.setLayoutX(63);
        loginButton.setLayoutY(215);
        loginButton.setPrefSize(137, 50);
        loginButton.setStyle("-fx-background-color: #CF7330; -fx-background-radius: 30px; "
                + "-fx-border-radius: 30px; -fx-border-color: black;");

        leftPane.getChildren().addAll(welcomeText, messageText1, messageText2, loginButton);
//οταν ο χρηστης παταει το κουμπι ανοιγει την σελισα συνδεσης 
        loginButton.setOnAction(event ->  {

            Signin signin = new Signin();
            Stage signinStage = new Stage();
            signin.start(signinStage);
            
//ελενχουμε αν το παραθυρο ειναι maximized 
            if (primaryStage.isMaximized()) {
                signinStage.setMaximized(true);
            }
        
            signinStage.show();
        });
//αρχικοποιουμε το δεξι pane και οριζουμε το χρωμα
        AnchorPane rightPane = new AnchorPane();
        rightPane.setStyle("-fx-background-color: #CF7330;");
        rightPane.setPrefWidth(305);
//βαζουμε τα στοιχεια που χρειαζομαστε για την σελιδα
        Text joinText = new Text("Έλα στην παρέα μας");
        joinText.setFont(Font.font(28));
        joinText.setLayoutX(13);
        joinText.setLayoutY(59);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font("System Bold", 14));
        nameLabel.setLayoutX(34);
        nameLabel.setLayoutY(74);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setLayoutX(34);
        nameField.setLayoutY(94);
        nameField.setPrefSize(227, 37);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("System Bold", 14));
        emailLabel.setLayoutX(34);
        emailLabel.setLayoutY(134);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setLayoutX(34);
        emailField.setLayoutY(156);
        emailField.setPrefSize(227, 37);
//δημιουργουμε δυο field για των κωδικο ετσι ωστε να μπορουμε να δημιουργησουμε το εφε οτι ο κωδικος δεν φαινεται μονο αμα πατησει ο χρηστης το αναλογο κουμπι
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("System Bold", 14));
        passwordLabel.setLayoutX(34);
        passwordLabel.setLayoutY(196);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setLayoutX(34);
        passwordField.setLayoutY(216);
        passwordField.setPrefSize(227, 37);


        TextField textField = new TextField();
        textField.setPromptText("Enter your password");
        textField.setLayoutX(34);
        textField.setLayoutY(216);
        textField.setPrefSize(227, 37);
        textField.setManaged(false);//να ξεκινησει και να μην φαινεται ο κωδικος 
        textField.setVisible(false);

//βαζουμε το κουμπι για να το παταει ο χρηστης και να μπορε να δει αυτα που γραφει
        Button toggleButton = new Button("👁");
        toggleButton.setStyle("-fx-font-size: 14px;");

//λειτουργια του κουμπιου για την ενναλαγγη των δυο πεδιων 
        toggleButton.setOnAction(e -> {
            if (passwordField.isVisible()) {
//αν passwordfiled ανοιχτο τοτε το κρυβουμε δειχνουμε το textfield 
                textField.setText(passwordField.getText());
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                textField.setVisible(true);
                textField.setManaged(true);
            } else {
//αν το textfield ειναι ορατο το κρυβουμε και δειχνουμε το passwordfield 
                passwordField.setText(textField.getText());
                textField.setVisible(false);
                textField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });

//τα τοποθετουμε και τα τρια σε εna hbox
        HBox passwordBox = new HBox(5, passwordField, textField, toggleButton);
        passwordBox.setSpacing(10);
        passwordBox.setLayoutX(34);
        passwordBox.setLayoutY(216);
//κανουμε το ιδιο για το confirmpassword
        Label confirmpasswordLabel = new Label("Confirm Password:");
        confirmpasswordLabel.setFont(new Font("System Bold", 14));
        confirmpasswordLabel.setLayoutX(34);
        confirmpasswordLabel.setLayoutY(256);

        TextField confirmPasswordTextField = new TextField();
        confirmPasswordTextField.setPromptText("Confirm your password");
        confirmPasswordTextField.setLayoutX(34);
        confirmPasswordTextField.setLayoutY(276);
        confirmPasswordTextField.setPrefSize(227, 37);
        confirmPasswordTextField.setManaged(false);
        confirmPasswordTextField.setVisible(false);   

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setLayoutX(34);
        confirmPasswordField.setLayoutY(276);
        confirmPasswordField.setPrefSize(227, 37);

        Button toggleConfirmPasswordButton = new Button("👁");
        toggleConfirmPasswordButton.setStyle("-fx-font-size: 14px;");

        toggleConfirmPasswordButton.setOnAction(e -> {
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
        });

        HBox confirmPasswordBox = new HBox(5, confirmPasswordField, confirmPasswordTextField, toggleConfirmPasswordButton);
        confirmPasswordBox.setSpacing(10);
        confirmPasswordBox.setLayoutX(34);
        confirmPasswordBox.setLayoutY(276);

//κουμπι για την εγγραφη
        Button registerButton = new Button("Εγγραφή");
        registerButton.setFont(new Font("System Bold", 14));
        registerButton.setLayoutX(88);
        registerButton.setLayoutY(340);
        registerButton.setPrefSize(120, 50);
        registerButton.setStyle("-fx-background-color: #30CFC2; -fx-background-radius: 30px; "
                + "-fx-border-radius: 30px; -fx-border-color: black;");


//οριζουμε την ενεργεια οταν ο χρηστης παταει το κουμπι εγγραφης 
        registerButton.setOnAction(event -> {
            storedUsername = nameField.getText();
            storedEmail = emailField.getText();
            storedPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
//εαν ο χρηστης εχει αφησει εστω και μια φορμα κενη τοτε εμφανιζει μυνημα λαθους 
            if (storedUsername.isEmpty() || storedEmail.isEmpty() || storedPassword.isEmpty() || confirmPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                alert.setHeaderText(null);
                alert.setContentText("Ωπ, κάτι ξέχασες! Ρίξε μια ματιά και συμπλήρωσε όλα τα απαραίτητα πεδία.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.getDialogPane().setMinWidth(500);
                alert.getDialogPane().setMinHeight(300);
                alert.showAndWait();

                return;
            }
//λιστα για να εισαγουμε τα errors που εμφανιζονται στον χρηστη 
            List<String> errors = new ArrayList<>();
//error αν το ονομα ειναι λιγοτερο απο 4 χαρακτηρες
            if (storedUsername.isEmpty() || storedUsername.length() <= 4) {
                errors.add("• Το όνομα πρέπει να έχει πάνω από 4 χαρακτήρες");
            }
//error αν το email δεν περιεχει το @
            if (storedEmail.isEmpty() || !storedEmail.contains("@")) {
                errors.add("• Εισήγαγε ένα έγκυρο email");
            }
//error αν ο κωδικος ειναι μικροτερος απο 6 χαρακτηρες
            if (storedPassword.isEmpty() || storedPassword.length() < 6) {
                errors.add("• Ο κωδικός πρόσβασης πρέπει να έχει πάνω από 6 χαρακτήρες");
            }
//error αν ο κωδικος και ο κωδικος επιβεβαιωσης δεν ειναι ιδιος
            if (!storedPassword.equals(confirmPassword)) {
                errors.add("• Οι κωδικοί που έβαλες δεν είναι ίδιοι");
            }
//αν υπαρχουν error εμφανιζει την λισατ στον χρηστη 
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Η φόρμα δεν έχει ολοκληρωθεί");
                alert.setHeaderText(null);
                String errorMessage = String.join("\n", errors);
                alert.setContentText(errorMessage);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.showAndWait();
                return;
            }
//μηνυμα επιτυχιας αν δεν υπαρχουν errors
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Εγγραφή Επιτυχής");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Η εγγραφή ολοκληρώθηκε με επιτυχία!");
            DialogPane dialogPane = successAlert.getDialogPane();
            dialogPane.getStyleClass().add("success-alert");
            dialogPane.getStylesheets().add(getClass().getResource("success.css").toExternalForm());
            successAlert.showAndWait();

//κθαριζουμε τα πεδια αφου ο χρηστης εχει εισαγει σωστα τα στοιχεια του
            nameField.clear();
            emailField.clear();
            passwordField.clear();
            textField.clear();
            confirmPasswordField.clear();

//βλεπουμ τα δεδομενα στο cmd
            System.out.println("Name: " + storedUsername);
            System.out.println("Email: " + storedEmail);
            System.out.println("Password: " + storedPassword);
            System.out.println("Confirm Password: " + confirmPassword);
        });

        rightPane.getChildren().addAll(joinText, nameLabel, nameField, emailLabel, emailField, passwordLabel,
                passwordBox, confirmpasswordLabel, confirmPasswordBox, registerButton);

//προσαρμογη της οθονης 
        hBox.getChildren().addAll(leftPane, rightPane);

        Scene scene = new Scene(hBox, 600, 400);
        Scale scale = new Scale(1, 1);
        hBox.getTransforms().add(scale);

        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double scaleFactor = newWidth.doubleValue() / 600;
            scale.setX(scaleFactor);
        });

        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double scaleFactor = newHeight.doubleValue() / 400;
            scale.setY(scaleFactor);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Study Buddy");
        primaryStage.show();

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
