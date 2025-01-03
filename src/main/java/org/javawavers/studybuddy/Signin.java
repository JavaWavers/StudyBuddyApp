package org.javawavers.studybuddy;

import javafx.application.Application;
import javafx.scene.Node;
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

public class Signin extends Application {
    


    public HBox hBox = new HBox();

//αρχικοποιουμε αυτα που θα χρειαστουμε για την σελιδα
    public AnchorPane rightPane = new AnchorPane(); // Πρώην Left Pane
    public Text welcomeText = new Text("Καλώς ήρθες ξανά!");
    Text messageText1Line1 = new Text("Έτοιμος να κάνεις το διάβασμα σου πιο ");
    Text messageText1Line2 = new Text("έξυπνο και αποτελεσματικό;");
    public Button signinButton = new Button("Εγγραφή εδώ");
    public AnchorPane leftPane = new AnchorPane(); // Πρώην Right Pane
    public Text joinText = new Text("Συνδέσου ξανά!");
    public Label emailLabel = new Label("Email:");
    public TextField emailField = new TextField();
    public Label passwordLabel = new Label("Password:");
    public PasswordField passwordField = new PasswordField();
    public Button loginButton = new Button("Συνδέσου");

    @Override
    public void start(Stage primaryStage) {
//οριζουμε το χρωμα για το αριστερο μερος του pane
        rightPane.setStyle("-fx-background-color:   #30CFC2;");
        rightPane.setPrefWidth(295);
//οριζουμε καθε στοιχειο στο αριστερο μερος τις συντεταγμενες του καθως και το μεγεθος της γραμματοσειρας
        welcomeText.setFont(Font.font(28));
        welcomeText.setLayoutX(18);
        welcomeText.setLayoutY(103);

        messageText1Line1.setFont(Font.font(14));
        messageText1Line2.setFont(Font.font(14));

        messageText1Line1.setLayoutX(18);
        messageText1Line1.setLayoutY(127);

        messageText1Line2.setLayoutX(18);
        messageText1Line2.setLayoutY(145);
//δημιουργουμε του κουμπι συνδεσης
        signinButton.setFont(new Font("System Bold", 14));
        signinButton.setLayoutX(63);
        signinButton.setLayoutY(205);
        signinButton.setPrefSize(137, 50);
        signinButton.setStyle("-fx-background-color:  #CF308C; -fx-background-radius: 30px; "
                + "-fx-border-radius: 30px; -fx-border-color: black;");

        rightPane.getChildren().addAll(welcomeText, messageText1Line1, messageText1Line2, signinButton);
//αν πατηθει το κουμπι ανοιγει η σελιδα signup
        signinButton.setOnAction(event ->  {
            Signup signup = new Signup();
            Stage signupStage = new Stage();
            signup.start(signupStage);
            
//αν ειναι mazimize το παραθυρο απο πριν τοτε να ανοιξει και στον χρηστη maximize
            if (primaryStage.isMaximized()) {
                signupStage.setMaximized(true);
            }
        
            signupStage.show();
        });

//οριζουμε το χρωμα για το δεξι pane
        leftPane.setStyle("-fx-background-color: #CF308C;");
        leftPane.setPrefWidth(305);
//οριζουμε τα μεγεθοι και τις συντεταγμενες για τα στοιχεια του δεξιου pane
        joinText.setFont(Font.font(34));
        joinText.setLayoutX(34);
        joinText.setLayoutY(59);

        emailLabel.setFont(new Font("System Bold", 14));
        emailLabel.setLayoutX(34);
        emailLabel.setLayoutY(74);

        emailField.setPromptText("Enter your email");
        emailField.setLayoutX(34);
        emailField.setLayoutY(94);
        emailField.setPrefSize(227, 37);
//δημιουργουμε δυο field για των κωδικο ετσι ωστε να μπορουμε να δημιουργησουμε το εφε οτι ο κωδικος δεν φαινεται μονο αμα πατησει ο χρηστης το αναλογο κουμπι
        passwordLabel.setFont(new Font("System Bold", 14));
        passwordLabel.setLayoutX(34);
        passwordLabel.setLayoutY(134);

        passwordField.setPromptText("Enter your password");
        passwordField.setLayoutX(34);
        passwordField.setLayoutY(154);
        passwordField.setPrefSize(227, 37);

        TextField textField = new TextField();
        textField.setPromptText("Enter your password");
        textField.setLayoutX(34);
        textField.setLayoutY(154);
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

        HBox passwordBox = new HBox(5, passwordField, textField, toggleButton);
        passwordBox.setSpacing(10);
        passwordBox.setLayoutX(34);
        passwordBox.setLayoutY(154);

        loginButton.setFont(new Font("System Bold", 14));
        loginButton.setLayoutX(88);
        loginButton.setLayoutY(220);
        loginButton.setPrefSize(120, 50);
        loginButton.setStyle("-fx-background-color: #30CFC2; -fx-background-radius: 30px; "
                + "-fx-border-radius: 30px; -fx-border-color: black;");

//οριζουμε το κουμπι login
        loginButton.setOnAction(event -> {
//παιρνουμε αυτα που εισαγει ο χρηστης
            String email = emailField.getText();
            String password = passwordField.getText();
//εαν ο χρηστης δεν εχει γραψει τιποτα στο email και στον κωδικο τοτε εμφανιζει μηνυμα λαθους
            if (email.isEmpty() || password.isEmpty()) {
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

            
//περνουμε τα στοιχεια που εχει εισαγει ο χρηστης στις φορμες εγγραφης και αν ειναι ιδια τοε εμφανιζεται μηνυμα επιτυχιας και ανοιγει το ημερολογιο 
            if (email.equals(Signup.storedEmail) && password.equals(Signup.storedPassword)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Σύνδεση Επιτυχής");
                alert.setHeaderText(null);
                alert.setContentText("Καλώς ήρθες!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("success-alert");
                dialogPane.getStylesheets().add(getClass().getResource("success.css").toExternalForm());
                Calendar calendar = new Calendar();
                Stage calendarstage = new Stage();
                calendar.start(calendarstage);
//ανοιγει σε μεγαλο μεγεθος η οθονη αν ηταν εξερχης maximized μεγεθος
                if (primaryStage.isMaximized()) {
                    calendarstage.setMaximized(true);
                }
                calendarstage.show();
                alert.showAndWait();
//εμφανιζουμε μηνυμα σφαλματος αν ο κωδικος και το email δεν ειναι ιδιο με αυτα που εβαλε ο χρηστης 
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Σφάλμα σύνδεσης");
                alert.setHeaderText(null);
                alert.setContentText("Λάθος email ή κωδικός πρόσβασης.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
                alert.getDialogPane().setMinWidth(500);
                alert.getDialogPane().setMinHeight(300);
                alert.showAndWait();
                
            }
            passwordField.clear();
            textField.clear();
            emailField.clear();
            
        });

        leftPane.getChildren().addAll(joinText, emailLabel, emailField, passwordLabel, passwordBox, loginButton);

//προσαρμογη του μεγεθους του πινακα ωστε να ανοιγει σε ολο το ευρος 
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
        primaryStage.setTitle("Study Buddy - Σύνδεση");
        primaryStage.show();
    }

    public Node signin() {
        HBox signinPage = new HBox();
        signinPage.getChildren().addAll(leftPane, rightPane);
        return signinPage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
