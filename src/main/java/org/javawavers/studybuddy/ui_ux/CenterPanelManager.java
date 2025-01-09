package org.javawavers.studybuddy.ui_ux;

import javafx.scene.layout.StackPane;

/**
 * CenterPanelManager class changes the center panel of the main Page
 * depending on the Menu Button that is pressed.
*/
public class CenterPanelManager {
    private final StackPane centerPane;

    //class constructor
    public CenterPanelManager() {
        centerPane = new StackPane();
        centerPane.setMaxWidth(Double.MAX_VALUE);
        centerPane.setMaxHeight(Double.MAX_VALUE);
    }

    /**
     *This is the main method of the class
     * It creates objects from all button Pages which are on the Menu
     * Using a switch it changes the page that should be presented on the center panel
     */
    //Method that changes center Panel
    public void changeCenterPanel(String panelName) {
        centerPane.getChildren().clear();

        ExamPage examPage = new ExamPage();
        Dashboard dashboard = new Dashboard();
        Calendar calendar = new Calendar();
        AssignmentPage assignmentPage = new AssignmentPage();
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();

        // Depending on which Menu Button is pressed it changes the center Panel
        switch (panelName) {
            //case "Home":
                //centerPane.getChildren().setAll(loginPage.login());
                //break;
            case "Courses":
                break;
            case "Exam":
                centerPane.getChildren().setAll(examPage.createExamPanel());
                break;
            case "Assignments":
                centerPane.getChildren().setAll(assignmentPage.assignmentPanel());
                break;
            case "Calendar":
                centerPane.getChildren().setAll(calendar.calendar());
                break;
            case "Dashboard":
                centerPane.getChildren().setAll(dashboard.createDashboard());
                break;
            default:
                break;
        }
    }

    /**
     * @return The center pane that is selected it appears on the center panel
     */
    public StackPane getCenterPane() {
        return centerPane;
    }
}
