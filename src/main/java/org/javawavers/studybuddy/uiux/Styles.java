package org.javawavers.studybuddy.uiux;

/**
 * The Styles class contains constants and methods for managing CSS styles
 * used throughout the Study Buddy application. It provides predefined
 * styles for buttons, labels, and other UI elements.
 */
public class Styles {

  /**
   * Enum representing task types with their associated color styles.
   */
  public static enum TaskType {
    TODAY("rgba(204, 77, 240, 1)"),
    WEEK("rgba(255, 215, 0, 0.93)"),
    OVERDUE("rgba(244, 78, 78, 1)"),
    COMPLETED("rgba(101, 225, 101, 0.9)");

    private final String color;

    /**
     * Constructs a TaskType with a specific color.
     *
     * @param color The color associated with the task type.
     */
    TaskType(String color) {
      this.color = color;
    }

    /**
     * Returns the color associated with the task type.
     *
     * @return A {@link String} representing the color in CSS format.
     */
    public String getColor() {
      return color;
    }
  }

  /**
   * Generates a CSS style string for a label with the specified color.
   *
   * @param color The background color of the label in CSS format.
   * @return A {@link String} containing the CSS rules for the label.
   */
  public static final String LABEL_STYLE(String color) {
    return "-fx-font-size: 16px;"
        + " -fx-font-weight: bold; -fx-padding: 5;"
        + " -fx-background-color: "
        + color
        + ";"
        + " -fx-background-radius: 8,7,6;";
  }

  /**
   * Predefined CSS style for menu buttons.
   */
  public static final String MENU_BTN_STYLE =
      "-fx-border-color: #F7B267; "
          + "-fx-background-color: #F7B267; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px; ";

  /**
   * Predefined CSS style for inner menu buttons.
   */
  public static final String MENU_BTN_INSIDE_STYLE =
      "-fx-border-color: #F7B267; "
          + "-fx-background-color: #F7B267; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px 20px; ";

  /**
   * Predefined CSS style for selected menu buttons.
   */
  public static final String MENU_BTN_SELECTED =
      "-fx-border-color: #F9C288; "
          + "-fx-background-color: #F9C288; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px; ";

  /**
   * Predefined CSS style for selected inner menu buttons.
   */
  public static final String MENU_BTN_INSIDE_SELECTED =
      "-fx-border-color: #F9C288; "
          + "-fx-background-color: #F9C288; "
          + "-fx-border-width: 1px; "
          + "fx-text-fill: black; "
          + "fx-font-size: 14px; "
          + "fx-alignment: CENTER-LEFT; "
          + "-fx-border-radius: 5px; "
          + "-fx-padding: 10px 20px; ";

  /**
   * Predefined CSS style for course buttons.
   */
  public static final String COURSES_BTN_STYLE =
      "-fx-background-color: linear-gradient(#FAD7A0, #F7B267);"
          + "-fx-background-radius: 8,7,6;"
          + "-fx-background-insets: 0,1,2;"
          + "-fx-text-fill: #5A3D2B;"
          + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);"
          + "-fx-font-weight: bold;"
          + "-fx-padding: 10 20;"
          + "-fx-border-color: #D98A4B;"
          + "-fx-border-radius: 6;";

  /**
   * Predefined CSS style for course buttons when hovered.
   */
  public static final String COURSES_BTN_MOUSE_ENTERED =
      "-fx-background-color: linear-gradient(#FFE0B2, #F7B267);"
          + "-fx-background-radius: 8,7,6;"
          + "-fx-background-insets: 0,1,2;"
          + "-fx-text-fill: #5A3D2B;"
          + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);"
          + "-fx-font-weight: bold;"
          + "-fx-padding: 10 20;"
          + "-fx-border-color: #D98A4B;"
          + "-fx-border-radius: 6;";

  /**
   * Predefined CSS style for black-bordered elements.
   */
  public static final String BLACK_BORDER =
      "-fx-border-color: black;" + " -fx-border-width: 1;" + " -fx-padding: 10;";

  /**
   * Enum representing generic style types with associated color styles.
   */
  public enum StyleType {
    TITLE("rgba(101, 225, 101, 0.81)"),
    LABEL("rgba(181, 99, 241, 0.81)");

    private final String color;

    /**
     * Constructs a StyleType with a specific color.
     *
     * @param color The color associated with the style type.
     */
    StyleType(String color) {
      this.color = color;
    }

    /**
     * Returns the color associated with the style type.
     *
     * @return A {@link String} representing the color in CSS format.
     */
    public String getColor() {
      return color;
    }

    /**
     * Returns the CSS style string for the style type.
     *
     * @return A {@link String} containing the CSS rules for the style.
     */
    public String getStyle() {
      return "-fx-font-size: 14px;"
          + " -fx-padding: 5;"
          + " -fx-background-color: "
          + color
          + ";"
          + " -fx-background-radius: 6,5,4;";
    }
  }
}
