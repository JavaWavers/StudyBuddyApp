package org.javawavers.studybuddy.ui_ux;

public class Styles {

  public static enum TaskType {
    TODAY("rgba(204, 77, 240, 1)"),
    WEEK("rgba(255, 215, 0, 0.93)"),
    OVERDUE("rgba(244, 78, 78, 1)"),
    COMPLETED("rgba(101, 225, 101, 0.9)");

    private final String color;

    TaskType(String color) {
      this.color = color;
    }

    public String getColor() {
      return color;
    }
  }

  public static final String LABEL_STYLE(String color) {
    return "-fx-font-size: 16px;"
        + " -fx-font-weight: bold; -fx-padding: 5;"
        + " -fx-background-color: "
        + color
        + ";"
        + " -fx-background-radius: 8,7,6;";
  }

  public static final String MENU_BTN_STYLE =
      "-fx-border-color: #F7B267; "
          + "-fx-background-color: #F7B267; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px; ";

  public static final String MENU_BTN_INSIDE_STYLE =
      "-fx-border-color: #F7B267; "
          + "-fx-background-color: #F7B267; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px 20px; ";

  public static final String MENU_BTN_SELECTED =
      "-fx-border-color: #F9C288; "
          + "-fx-background-color: #F9C288; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px; ";

  public static final String MENU_BTN_INSIDE_SELECTED =
      "-fx-border-color: #F9C288; "
          + "-fx-background-color: #F9C288; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px 20px; ";
}
