package org.javawavers.studybuddy.ui_ux;

import java.net.URL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.javawavers.studybuddy.StudyBuddyApp;

public class MenuPage {
  private VBox leftBoxMenu = new VBox(15);
  private ToggleButton btnExam = new ToggleButton("Exam");
  private ToggleButton btnAssignment = new ToggleButton("Assignments");
  private ToggleButton btnCalendar = new ToggleButton("Calendar");
  private ToggleButton btnDashboard = new ToggleButton("Dashboard");
  private ToggleButton btnCourses = new ToggleButton("Courses");
  private Button disconnectBtn = new Button("Αποσύνδεση");

  private CenterPanelManager centerPanelManager;
  private RightPanel rightPanel;
  private ImageView arrowIconCourses;
  private Image arrowRight;
  private Image arrowDown;
  private ImageView arrowIconCalendar;
  private ImageView arrowIconDashboard;
  VBox optionVBox = new VBox(15);

  public MenuPage(CenterPanelManager centerPanelManager, RightPanel rightPanel) {
    this.centerPanelManager = centerPanelManager;
    this.rightPanel = rightPanel;
    initializeMenu();
  }

  private void initializeMenu() {
    configureLayout();
    configureUserSection();
    configureButtons();
    setButtonGraphs();
    configureLogo();
  }

  private void configureLayout() {
    leftBoxMenu.setPrefWidth(212);
    leftBoxMenu.setStyle("-fx-background-color: #F7B267;");
    leftBoxMenu.setPadding(new Insets(20));
    leftBoxMenu.setMaxHeight(Double.MAX_VALUE);
  }

  private void configureButtons() {
    initializeToggleGroup();
    configureButtonStyles();
    configureNavigationButtons();
    configureCourseDropdown();
  }

  private void initializeToggleGroup() {
    ToggleGroup btnGroup = new ToggleGroup();
    btnCourses.setToggleGroup(btnGroup);
    btnExam.setToggleGroup(btnGroup);
    btnAssignment.setToggleGroup(btnGroup);
    btnCalendar.setToggleGroup(btnGroup);
    btnDashboard.setToggleGroup(btnGroup);
  }

  private void configureButtonStyles() {
    btnExam.setStyle(Styles.MENU_BTN_INSIDE_STYLE);
    btnAssignment.setStyle(Styles.MENU_BTN_INSIDE_STYLE);
    btnCalendar.setStyle(Styles.MENU_BTN_STYLE);
    btnDashboard.setStyle(Styles.MENU_BTN_STYLE);
    btnCourses.setStyle(Styles.MENU_BTN_STYLE);

    configureButtonSelection();
  }

  private void configureButtonSelection() {
    configureSelectionListener(btnCourses, Styles.MENU_BTN_SELECTED, Styles.MENU_BTN_STYLE);
    configureSelectionListener(btnCalendar, Styles.MENU_BTN_SELECTED, Styles.MENU_BTN_STYLE);
    configureSelectionListener(btnDashboard, Styles.MENU_BTN_SELECTED, Styles.MENU_BTN_STYLE);
    configureSelectionListener(
        btnExam, Styles.MENU_BTN_INSIDE_SELECTED, Styles.MENU_BTN_INSIDE_STYLE);
    configureSelectionListener(
        btnAssignment, Styles.MENU_BTN_INSIDE_SELECTED, Styles.MENU_BTN_INSIDE_STYLE);
  }

  private void configureSelectionListener(
      ToggleButton button, String selectedStyle, String defaultStyle) {
    button
        .selectedProperty()
        .addListener(
            (obs, wasSelected, isSelected) -> {
              button.setStyle(isSelected ? selectedStyle : defaultStyle);
            });
  }

  private void configureUserSection() {
    try {
      URL userImgUrl = getClass().getResource("/user.png");
      if (userImgUrl != null) {
        Image userImg = new Image(userImgUrl.toExternalForm());
        ImageView userImgView = new ImageView(userImg);
        userImgView.setFitWidth(20);
        userImgView.setFitHeight(20);

        String userName = new RegisterPage().storedUsername;
        if (userName == null) {
          userName = "Guest";
        }
        Label userNameLbl = new Label(userName);
        userNameLbl.setStyle(Styles.MENU_BTN_STYLE);

        URL logoutImgUrl = getClass().getResource("/logout.png");
        if (logoutImgUrl != null) {
          Image logoutImg = new Image(logoutImgUrl.toExternalForm());
          ImageView logoutImgView = new ImageView(logoutImg);
          logoutImgView.setFitWidth(20);
          logoutImgView.setFitHeight(20);
          Button btnLogout = new Button();
          btnLogout.setGraphic(logoutImgView);
          btnLogout.setStyle("-fx-background-color: transparent;");
          btnLogout.setOnAction(e -> configLogOutBtn());

          Region spacer = new Region();
          HBox.setHgrow(spacer, Priority.ALWAYS);

          HBox userSection = new HBox(10, userImgView, userNameLbl, spacer, btnLogout);
          userSection.setAlignment(Pos.CENTER_LEFT);
          HBox.setHgrow(btnLogout, Priority.ALWAYS);

          leftBoxMenu.getChildren().add(userSection);
        } else {
          System.out.println("Δεν βρέθηκε η εικόνα αποσύνδεσης!");
        }
      } else {
        System.out.println("Δεν βρέθηκε η εικόνα χρήστη!");
      }
    } catch (Exception e) {
      System.out.println("Δεν μπορεί να φορτωθεί η εικόνα: " + e.getMessage());
    }
  }

  private void configLogOutBtn() {
    try {
      Stage stage = StudyBuddyApp.getStage();

      SceneManager sceneManager = new SceneManager(stage);
      HomePage homePage = new HomePage();
      Scene homeScene = homePage.home(sceneManager);
      sceneManager.switchScene(homeScene);
    } catch (Exception e) {
      System.out.println("Σφάλμα κάτά την αποσύνδεση: " + e.getMessage());
    }
  }

  private void configureNavigationButtons() {
    setButtonIcons();

    btnExam.setOnAction(e -> changePanel("Exam"));
    btnAssignment.setOnAction(e -> changePanel("Assignments"));
    btnCalendar.setOnAction(e -> changePanel("Calendar"));
    btnDashboard.setOnAction(e -> changePanel("Dashboard"));

    try {
      if (!leftBoxMenu.getChildren().contains(btnCourses)) {
        leftBoxMenu.getChildren().add(btnCourses);
      }
      if (!leftBoxMenu.getChildren().contains(btnCalendar)) {
        leftBoxMenu.getChildren().add(btnCalendar);
      }
      if (!leftBoxMenu.getChildren().contains(btnDashboard)) {
        leftBoxMenu.getChildren().add(btnDashboard);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Δεν μπορούν να προστεθούν τα κουμπιά του μενού: " + e.getMessage());
    }
  }

  private void changePanel(String panelName) {
    try {
      centerPanelManager.changeCenterPanel(panelName);
      rightPanel.updateRightPaneContent(panelName);
      System.out.println("Switching to: " + panelName);
    } catch (Exception e) {
      System.out.println(
          "Δεν μπορεί να αλλάξει το panel σε: " + panelName + " -> " + e.getMessage());
    }
  }

  private void setButtonGraphs() {
    // Load arrow images
    arrowRight = new Image(getClass().getResource("/arrowRight.png").toExternalForm());
    arrowDown = new Image(getClass().getResource("/arrowDown.png").toExternalForm());

    // Configure arrow icons for buttons
    arrowIconCourses = new ImageView(arrowRight);
    arrowIconCalendar = new ImageView(arrowRight);
    arrowIconDashboard = new ImageView(arrowRight);

    arrowIconCourses.setFitHeight(20);
    arrowIconCourses.setFitWidth(20);
    arrowIconCalendar.setFitHeight(20);
    arrowIconCalendar.setFitWidth(20);
    arrowIconDashboard.setFitHeight(20);
    arrowIconDashboard.setFitWidth(20);

    // Configure buttons with arrows and icons
    btnCourses.setGraphic(
        new HBox(
            10,
            arrowIconCourses,
            new ImageView(new Image(getClass().getResource("/folder.png").toExternalForm()))));
    btnCalendar.setGraphic(
        new HBox(
            10,
            arrowIconCalendar,
            new ImageView(new Image(getClass().getResource("/calendar.png").toExternalForm()))));
    btnDashboard.setGraphic(
        new HBox(
            10,
            arrowIconDashboard,
            new ImageView(new Image(getClass().getResource("/dashboard.png").toExternalForm()))));
  }

  private void configureCourseDropdown() {
    optionVBox.setVisible(false);

    btnCourses.setOnAction(
        e -> {
          if (optionVBox.getChildren().isEmpty()) {
            if (!optionVBox.getChildren().contains(btnExam)) {
              optionVBox.getChildren().add(btnExam);
            }
            if (!optionVBox.getChildren().contains(btnAssignment)) {
              optionVBox.getChildren().add(btnAssignment);
            }
            optionVBox.setVisible(true);
            arrowIconCourses.setImage(arrowDown);

            if (!leftBoxMenu.getChildren().contains(optionVBox)) {
              leftBoxMenu.getChildren().add(leftBoxMenu.getChildren().size() - 4, optionVBox);
              VBox.setMargin(btnExam, new Insets(0, 20, 0, 20));
              VBox.setMargin(btnAssignment, new Insets(0, 20, 0, 20));
            }
          } else {
            closeCoursesDropdown();
          }
        });
  }

  private void closeCoursesDropdown() {
    if (leftBoxMenu.getChildren().contains(optionVBox)) {
      optionVBox.getChildren().clear();
      optionVBox.setVisible(false);
      leftBoxMenu.getChildren().remove(optionVBox);
    }
    arrowIconCourses.setImage(arrowRight);
  }

  private void configureLogo() {
    Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
    ImageView logoView = new ImageView(logoImg);
    logoView.setFitWidth(200);
    logoView.setPreserveRatio(true);

    Region logoSpacer = new Region();
    VBox.setVgrow(logoSpacer, Priority.ALWAYS);
    leftBoxMenu.getChildren().addAll(logoSpacer, logoView);
  }

  private void setButtonIcons() {
    setIconForButton(btnCourses, "/folder.png");
    setIconForButton(btnCalendar, "/calendar.png");
    setIconForButton(btnDashboard, "/dashboard.png");
  }

  private void setIconForButton(ToggleButton button, String imagePath) {
    Image img = new Image(getClass().getResource(imagePath).toExternalForm());
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(20);
    imgView.setFitHeight(20);
    button.setGraphic(new HBox(10, imgView));
  }

  public VBox getLeftBoxMenu() {
    return leftBoxMenu;
  }
}
