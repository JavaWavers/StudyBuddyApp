package org.javawavers.studybuddy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class MenuPage {
    private VBox leftBoxMenu = new VBox(15);
    private ToggleButton btnHome =new ToggleButton("Home");
    private ToggleButton btnExam = new ToggleButton("Exam");
    private ToggleButton btnAssignment = new ToggleButton("Assignments");
    private ToggleButton btnCalendar = new ToggleButton("Calendar");
    private ToggleButton btnDashboard = new ToggleButton("Dashboard");
    private ToggleButton btnCourses = new ToggleButton("Courses");

    private CenterPanelManager centerPanelManager;

    private ImageView arrowIconCourses;
    private Image arrowRight;
    private Image arrowDown;

    private String btnStyle;
    private String btnInsideStyle;
    private String btnSelected;
    private String btnInsideSelected;
    VBox optionVBox = new VBox(15);

    public MenuPage(CenterPanelManager centerPanelManager) {
        this.centerPanelManager = centerPanelManager;
        initVariables();
    }
    private void initVariables() {

        configMenu();
        initToggleGroup();
        configBtnStyles();
        setButtonSelectedStyles();
        configUserImgBtn();
        configNavigationBtns();
        configCourses();
        configLogo();

    }
    private void configMenu() {
        leftBoxMenu.setPrefWidth(212);
        leftBoxMenu.setMinWidth(212); //or 88.33
        leftBoxMenu.setMaxWidth(212);
        leftBoxMenu.setStyle(/*"-fx-padding: 68 0 0 0;"
                + */"-fx-background-color: #F7B267; ");

        leftBoxMenu.setMaxHeight(Double.MAX_VALUE);
    }

    private void initToggleGroup() {
        ToggleGroup btnGroup = new ToggleGroup();
        btnHome.setToggleGroup(btnGroup);
        btnCourses.setToggleGroup(btnGroup);
        btnExam.setToggleGroup(btnGroup);
        btnAssignment.setToggleGroup(btnGroup);
        btnCalendar.setToggleGroup(btnGroup);
        btnDashboard.setToggleGroup(btnGroup);
    }

    private void configBtnStyles() {
        setButtonSelectedStyles();

        btnStyle = "-fx-border-color: #F7B267; "
            + "-fx-background-color: #F7B267; "
            + "-fx-border-width: 1px; "
            + "fx-text-fill: black; "
            + "fx-font-size: 14px; "
            + "fx-alignment: CENTER-LEFT; "
            + " -fx-border-radius: 5px; "
            + "-fx-padding: 10px; ";

        btnInsideStyle = "-fx-border-color: #F7B267; "
            + "-fx-background-color: #F7B267; "
            + "-fx-border-width: 1px; "
            + "fx-text-fill: black; "
            + "fx-font-size: 14px; "
            + "fx-alignment: CENTER-LEFT; "
            + " -fx-border-radius: 5px; "
            + "-fx-padding: 10px 20px; ";

        btnHome.setStyle(btnStyle);
        btnExam.setStyle(btnInsideStyle);
        btnAssignment.setStyle(btnInsideStyle);
        btnCalendar.setStyle(btnStyle);
        btnDashboard.setStyle(btnStyle);
        btnCourses.setStyle(btnStyle);


    }

    private void setButtonSelectedStyles() {
        //Button Styles


        btnSelected = "-fx-border-color: #F9C288; "
            + "-fx-background-color: #F9C288; "
            + "-fx-border-width: 1px; "
            + "fx-text-fill: black; "
            + "fx-font-size: 14px; "
            + "fx-alignment: CENTER-LEFT; "
            + " -fx-border-radius: 5px; "
            + "-fx-padding: 10px; ";

        btnInsideSelected = "-fx-border-color: #F9C288; "
            + "-fx-background-color: #F9C288; "
            + "-fx-border-width: 1px; "
            + "fx-text-fill: black; "
            + "fx-font-size: 14px; "
            + "fx-alignment: CENTER-LEFT; "
            + " -fx-border-radius: 5px; "
            + "-fx-padding: 10px 20px; ";

        //Change Button Colors When Selected
        btnHome.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnHome.setStyle(isSelected ? btnSelected : btnStyle);
        });

        btnCourses.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnCourses.setStyle(isSelected ? btnSelected : btnStyle);
        });

        btnCalendar.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnCalendar.setStyle(isSelected ? btnSelected : btnStyle);
        });

        btnDashboard.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnDashboard.setStyle(isSelected ? btnSelected : btnStyle);
        });

        btnExam.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnExam.setStyle(isSelected ? btnInsideSelected : btnInsideStyle);
        });

        btnAssignment.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            btnAssignment.setStyle(isSelected ? btnInsideSelected : btnInsideStyle);
        });
    }

    private void configUserImgBtn() {
        //User Image
        try {
            Image userImg = new Image(getClass().getResource("/user.png").toExternalForm());
            ImageView userImgView = new ImageView(userImg);
            userImgView.setFitWidth(20);
            userImgView.setFitHeight(20);
            //User Img btn
            Label userNameLbl = new Label("UserName"); // dynamic name
            userNameLbl.setStyle(btnStyle);

            HBox userImgBtn = new HBox(10);
            userImgBtn.getChildren().addAll(userImgView, userNameLbl);
            userImgBtn.setAlignment(Pos.CENTER_LEFT);

            leftBoxMenu.getChildren().add(userImgBtn);
        } catch (NullPointerException e) {
            System.out.println("Image not Found!");
        }
    }


    private void configNavigationBtns() {
        setButtonGraphs();


        btnHome.setOnAction(e -> centerPanelManager.changeCenterPanel("Home"));
        btnExam.setOnAction(e -> centerPanelManager.changeCenterPanel("Exam"));
        btnAssignment.setOnAction(e -> centerPanelManager.changeCenterPanel("Assignments"));
        btnCalendar.setOnAction(e -> {
            closeCoursesOption(optionVBox);
            centerPanelManager.changeCenterPanel("Calendar");
        });
        btnDashboard.setOnAction(e -> {
            closeCoursesOption(optionVBox);
            centerPanelManager.changeCenterPanel("Dashboard");
        });

        leftBoxMenu.getChildren().addAll(
            btnHome,
            btnCourses,
            btnCalendar,
            btnDashboard
        );
    }

    private void configCourses() {

        optionVBox.setVisible(false);
        btnCourses.setOnAction(e -> {
            if (optionVBox.getChildren().isEmpty()) {
                optionVBox.getChildren().addAll(btnExam, btnAssignment);
                optionVBox.setVisible(true);
                arrowIconCourses.setImage(arrowDown);

                if (!leftBoxMenu.getChildren().contains(optionVBox)) {
                    leftBoxMenu.getChildren().add(leftBoxMenu.getChildren().size() - 4, optionVBox);
                    VBox.setMargin(btnExam, new Insets(0, 20, 0, 20));
                    VBox.setMargin(btnAssignment, new Insets(0, 20, 0, 20));
                }
            } else {
                optionVBox.getChildren().clear();
                optionVBox.setVisible(false);
                leftBoxMenu.getChildren().remove(optionVBox);
                arrowIconCourses.setImage(arrowRight);
            }
        });


    }

    private void closeCoursesOption(VBox optionVBox) {
        if (optionVBox.isVisible()) {
            optionVBox.getChildren().clear();
            optionVBox.setVisible(false);
            leftBoxMenu.getChildren().remove(optionVBox);
            arrowIconCourses.setImage(arrowRight);
        }
    }

    private void configLogo() {
        //logo Image
        Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
        ImageView logoImgView = new ImageView(logoImg);

        logoImgView.setFitWidth(200);
        logoImgView.setPreserveRatio(true);

        Region logoSpacer = new Region();
        VBox.setVgrow(logoSpacer, Priority.ALWAYS);

        HBox logoBox = new HBox(logoImgView);
        logoBox.setAlignment(Pos.CENTER);

        leftBoxMenu.getChildren().addAll(logoSpacer, logoImgView);
    }

    private void setButtonGraphs() {
        //arrow Images
        arrowRight = new Image(getClass().getResource("/arrowRight.png").toExternalForm());
        arrowDown = new Image(getClass().getResource("/arrowDown.png").toExternalForm());

        ImageView arrowIconHome = new ImageView(arrowRight);
        arrowIconHome.setFitWidth(20);
        arrowIconHome.setFitHeight(20);

        arrowIconCourses = new ImageView(arrowRight);
        arrowIconCourses.setFitHeight(20);
        arrowIconCourses.setFitWidth(20);

        ImageView arrowIconCalendar = new ImageView(arrowRight);
        arrowIconCalendar.setFitHeight(20);
        arrowIconCalendar.setFitWidth(20);

        ImageView arrowIconDashboard = new ImageView(arrowRight);
        arrowIconDashboard.setFitWidth(20);
        arrowIconDashboard.setFitHeight(20);

        //Home Image
        Image homeImage = new Image(getClass().getResource("/icons8-homepage-32.png").toExternalForm());
        ImageView homeImageView = new ImageView(homeImage);

        homeImageView.setFitHeight(20);
        homeImageView.setFitWidth(20);

        //Courses Image
        Image coursesImage = new Image(getClass().getResource("/folder.png").toExternalForm());
        ImageView coursesImageView = new ImageView(coursesImage);

        coursesImageView.setFitWidth(20);
        coursesImageView.setFitHeight(20);

        //Calendar Image
        Image calendarImage = new Image(getClass().getResource("/calendar.png").toExternalForm());
        ImageView calendarImageView = new ImageView(calendarImage);

        calendarImageView.setFitHeight(20);
        calendarImageView.setFitWidth(20);

        //Dashboard Image
        Image dashboardImage = new Image(getClass().getResource("/dashboard.png").toExternalForm());
        ImageView dashboardImageView = new ImageView(dashboardImage);

        dashboardImageView.setFitWidth(20);
        dashboardImageView.setFitHeight(20);


        //Home Image btn
        HBox homeImg = new HBox(10);
        homeImg.getChildren().addAll(arrowIconHome, homeImageView);
        btnHome.setGraphic(homeImg);

        //Courses
        HBox coursesImg = new HBox(10);
        coursesImg.getChildren().addAll(arrowIconCourses, coursesImageView);
        btnCourses.setGraphic(coursesImg);

        //Calendar
        HBox calendarImg = new HBox(10);
        calendarImg.getChildren().addAll(arrowIconCalendar, calendarImageView);
        btnCalendar.setGraphic(calendarImg);

        //Dashboard
        HBox dashboardImg = new HBox(10);
        dashboardImg.getChildren().addAll(arrowIconDashboard, dashboardImageView);
        btnDashboard.setGraphic(dashboardImg);

    }

    public VBox getLeftBoxMenu() {
        return leftBoxMenu;
    }
}
