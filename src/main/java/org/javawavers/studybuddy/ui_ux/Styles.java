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

    public static String getLabelStyle(String color) {
        return "-fx-font-size: 16px;" +
                " -fx-font-weight: bold; -fx-padding: 5;" +
                " -fx-background-color: " + color + ";" +
                " -fx-background-radius: 8,7,6;";
    }

}
