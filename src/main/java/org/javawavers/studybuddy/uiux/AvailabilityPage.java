package org.javawavers.studybuddy.uiux;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.javawavers.studybuddy.calculations.Availability;
import org.javawavers.studybuddy.courses.StaticUser;
import org.javawavers.studybuddy.database.ActiveUser;
import org.javawavers.studybuddy.database.DataInserter;

/**
 * The AvailabilityPage class handles the display and functionality of the availability page.
 * It allows the user to set their availability
 * for each day of the week and select a non-availability date.
 */
public class AvailabilityPage {

  private DatePicker datePicker;

  private VBox leftPane = new VBox(10);
  private VBox rightPane = new VBox(10);
  HBox btnsBox = new HBox(20);
  private TextField[] dayFields = new TextField[7];
  int[] avPerDay = new int[8];
  private String[] days = {
    "Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββατο", "Κυριακή"
  };
  private Stage popUpStage;
  private SceneManager sceneManager;

  /**
   * Constructor for initializing the AvailabilityPage with a pop-up stage.
   *
   * @param popUpStage The stage that will be used for the pop-up.
   */
  public AvailabilityPage(Stage popUpStage) {
    this.popUpStage = popUpStage;
  }

  /**
   * Constructor for initializing the AvailabilityPage with a SceneManager.
   *
   * @param sceneManager The SceneManager for switching scenes.
   */
  public AvailabilityPage(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  /**
   * Creates and returns the layout for the availability page.
   * It combines left and right frames, along with buttons at the bottom.
   *
   * @return A VBox containing the complete layout for the availability page.
   */
  public VBox availabilityPage() {

    VBox availPage = new VBox();
    leftFrame();
    rightFrame();

    HBox mainPanes = dayPickPanes();
    HBox btnPane = btnsFrame();

    HBox.setHgrow(mainPanes, Priority.ALWAYS);
    HBox.setHgrow(btnPane, Priority.ALWAYS);

    availPage.getChildren().addAll(mainPanes, btnPane);

    return availPage;
  }

  /**
   * Creates the HBox for displaying the left and right panels.
   *
   * @return An HBox that holds the left and right panes.
   */
  private HBox dayPickPanes() {
    HBox mainPane = new HBox(20);
    mainPane.setPadding(new Insets(20));

    leftPane.setAlignment(Pos.CENTER_LEFT);
    rightPane.setAlignment(Pos.TOP_RIGHT);

    VBox.setVgrow(leftPane, Priority.ALWAYS);
    VBox.setVgrow(rightPane, Priority.ALWAYS);

    leftPane.setFillWidth(true);
    rightPane.setFillWidth(true);
    leftPane.setMaxHeight(Double.MAX_VALUE);
    rightPane.setMaxHeight(Double.MAX_VALUE);

    mainPane.getChildren().addAll(leftPane, rightPane);
    return mainPane;
  }

  /**
   * Creates the right frame of the page, which contains the date picker for non-availability.
   *
   * @return The VBox containing the date picker for non-availability.
   */
  private VBox rightFrame() {

    Label dayLabel = new Label("Ημερομηνία Μη διαθεσιμότητας");
    dayLabel.setAlignment(Pos.CENTER);
    dayLabel.setStyle(Styles.StyleType.TITLE.getStyle());

    datePicker = new DatePicker();
    datePicker.setPromptText("Eπέλεξε μη-διαθεσιμη ημερομηνια");

    rightPane.getChildren().addAll(dayLabel, datePicker);
    return rightPane;
  }

  /**
   * Creates the left frame of the page, which contains the fields for each day's availability.
   *
   * @return The VBox containing the labels and text fields for each day's availability.
   */
  private VBox leftFrame() {

    Label avalLabel = new Label("Διαθεσιμότητα");
    avalLabel.setAlignment(Pos.CENTER);
    avalLabel.setStyle(Styles.StyleType.TITLE.getStyle());

    GridPane daysPane = new GridPane();
    daysPane.setHgap(10);
    daysPane.setVgap(10);

    for (int i = 0; i < days.length; i++) {
      Label dayLabel = createDayLabel(days[i]);
      TextField hoursField = createHoursField();

      dayFields[i] = hoursField;
      daysPane.add(dayLabel, 0, i);
      daysPane.add(hoursField, 1, i);
    }

    leftPane.getChildren().addAll(avalLabel, daysPane);
    return leftPane;
  }

  /**
   * Creates the button panel at the bottom of the page, with an "OK" button to submit the form.
   *
   * @return An HBox containing the "OK" button.
   */
  private HBox btnsFrame() {
    Button okBtn = new Button("OK");
    okBtn.setStyle(Styles.COURSES_BTN_STYLE);

    okBtn.setOnAction(
        event -> {
          List<String> errors = new ArrayList<>();
          boolean countFields = true;

          for (int i = 1; i < avPerDay.length; i++) {
            avPerDay[i] = parseTextFieldValue(dayFields[i - 1]);
            if (avPerDay[i] > 10) {
              errors.add("• Oι διαθέσιμες ώρες μέσα σε μια μέρα πρέπει να είναι λιγότερες απο 10");
            }
            if (avPerDay[i] > 0 && avPerDay[i] < 10) {
              countFields = false;
            }
          }
          if (countFields) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Επιβεβαίωση");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText(
                "Όλα τα πεδία είναι κενά. Είστε σίγουρος/η ότι θέλετε να συνεχίσετε;");

            DialogPane dialogPane = confirmAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alert.css").toExternalForm());

            confirmAlert
                .showAndWait()
                .ifPresent(
                    response -> {
                      if (response.getText().equalsIgnoreCase("Cancel")) {
                        return;
                      }
                    });
          }
          // test for registration or login
          boolean flag = true;
          if (staticUser.getAvPerDay() == null) {
            flag = false;
          }
          // static user for availability
          staticUser.setAvPerDay(avPerDay);

          LocalDate setNoAvailability = datePicker.getValue();
          if (setNoAvailability != null && setNoAvailability.isBefore(LocalDate.now())) {
            errors.add(
                "• Πρέπει να επιλέξεις ημερομηνία μη-διαθεσιμότητας μετά τη σημερινή ημερομηνία.");
          }
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
            return;
          } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Εισαγωγή Διαθεσιμότητας Επιτυχής");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Η Διαθεσιμότητα προστέθηκε!!");
            DialogPane dialogPane = successAlert.getDialogPane();
            dialogPane.getStyleClass().add("success-alert");
            dialogPane
                .getStylesheets()
                .add(
                    Objects.requireNonNull(getClass().getResource("/success.css"))
                        .toExternalForm());
            successAlert.showAndWait();

            // checks for empty availability
            int c = 0;
            for (int i = 1; i < avPerDay.length; i++) {
              if (avPerDay[i] != 0) {
                c++;
              }
            } // inserts for the first time or it update the already inserted one
            if (!flag) {
              DataInserter.insertAvailability(
                  avPerDay[1],
                  avPerDay[2],
                  avPerDay[3],
                  avPerDay[4],
                  avPerDay[5],
                  avPerDay[6],
                  avPerDay[7],
                  StaticUser.staticUser.getUserId());
              StaticUser.staticUser.setAvPerDay(avPerDay);
            } else {
              if (c > 1) {
                DataInserter.updateAvailability(
                    avPerDay[1],
                    avPerDay[2],
                    avPerDay[3],
                    avPerDay[4],
                    avPerDay[5],
                    avPerDay[6],
                    avPerDay[7],
                    StaticUser.staticUser.getUserId());
                StaticUser.staticUser.setAvPerDay(avPerDay);
              }
            }
          }

          if (setNoAvailability != null) {
            Availability.setNonAvailability(setNoAvailability);
            DataInserter.insertNonAvDate(setNoAvailability, StaticUser.staticUser.getUserId());
            StaticUser.staticUser.addNonAvailDays(setNoAvailability);
          }
          ActiveUser.loadData(staticUser.getEmail(), staticUser.getPassword());

          for (TextField dayField : dayFields) {
            dayField.clear();
          }
          datePicker.setValue(null);
          if (popUpStage != null) {
            popUpStage.close();
          }
        });
    btnsBox.setAlignment(Pos.CENTER_LEFT);
    btnsBox.setPadding(new Insets(20));
    btnsBox.getChildren().add(okBtn);
    return btnsBox;
  }

  private Label createDayLabel(String day) {
    Label label = new Label(day);
    label.setStyle(Styles.StyleType.LABEL.getStyle());
    label.setAlignment(Pos.CENTER_RIGHT);
    return label;
  }

  private TextField createHoursField() {
    TextField hoursField = new TextField();
    hoursField.setStyle("-fx-background-radius: 20px;");
    // restriction for input to accept only numbers
    hoursField.setTextFormatter(
        new TextFormatter<>(
            new IntegerStringConverter(),
            null,
            change -> {
              if (change.getControlNewText().matches("\\d*")) {
                return change;
              }
              return null;
            }));
    return hoursField;
  }

  // Check if the text is empty or contains only spaces, then return the value 0 for each day
  private Integer parseTextFieldValue(TextField textField) {
    if (textField != null) {
      String text = textField.getText();
      if (text == null || text.trim().isEmpty()) {
        return 0;
      } else {
        return Integer.parseInt(text);
      }
    }
    return 0;
  }

  /**
   * Creates and returns the starting page scene for the availability screen.
   *
   * @param sceneManager The SceneManager used for switching scenes.
   * @return The Scene containing the availability page layout with buttons.
   */
  public Scene availStartingPage(SceneManager sceneManager) {
    VBox availViewWithBtn = new VBox();

    HBox nameLbl = new HBox(20);
    Label name = new Label("Διαθεσιμότητα");
    name.setStyle(Styles.StyleType.TITLE.getStyle());
    nameLbl.getChildren().add(name);
    nameLbl.setPadding(new Insets(20));
    availViewWithBtn.getChildren().add(nameLbl);

    VBox availView = availabilityPage();
    availViewWithBtn.getChildren().add(availView);

    HBox btns = new HBox(15);
    btns.setPadding(new Insets(20));
    Button prevBtn = new Button("Προηγούμενο");
    prevBtn.setStyle(Styles.COURSES_BTN_STYLE);
    prevBtn.setOnAction(
        e -> {
          AssignmentPage assignPage = new AssignmentPage();
          sceneManager.switchScene(assignPage.assignmentStartingPage(sceneManager));
        });

    Button nextBtn = new Button("Επόμενο");
    nextBtn.setStyle(Styles.COURSES_BTN_STYLE);
    nextBtn.setOnAction(
        e -> {
          RegisterPage register = new RegisterPage();
          String storedUsername = register.storedUsername;
          Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
          successAlert.setTitle("Εισαγωγή Διαθεσιμότητας Επιτυχής");
          successAlert.setHeaderText(null);
          successAlert.setContentText(
              "🎉 Καλώς ήρθες, "
                  + storedUsername
                  + "🎉\n"
                  + //
                  "Νέα χρονιά, νέες ευκαιρίες και νέοι στόχοι! ✨\n"
                  + //
                  "Ετοιμάσου για μια εμπειρία γεμάτη έμπνευση, οργάνωση και πρόοδο! 🚀\n"
                  + //
                  "\n"
                  + //
                  "Μαζί θα κάνουμε αυτή τη χρονιά την καλύτερη!\n"
                  + //
                  "Πάτα το κουμπί και ξεκινάμε! 💪");
          DialogPane dialogPane = successAlert.getDialogPane();
          dialogPane.getStyleClass().add("success-alert");
          dialogPane
              .getStylesheets()
              .add(Objects.requireNonNull(getClass().getResource("/success.css")).toExternalForm());
          successAlert.showAndWait();
          MainFrame mainframe = new MainFrame();
          sceneManager.switchScene(mainframe.mainFrame(sceneManager));
        });
    btns.getChildren().addAll(prevBtn, nextBtn);
    availViewWithBtn.getChildren().add(btns);
    HBox btnBox = new HBox(20);
    btnBox.setPadding(new Insets(20));

    Scene scene =
        new Scene(
            availViewWithBtn,
            Screen.getPrimary().getVisualBounds().getWidth(),
            Screen.getPrimary().getVisualBounds().getHeight());
    return scene;
  }
}
