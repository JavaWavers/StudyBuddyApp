package org.javawavers.studybuddy.ui_ux;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Dashboard {
    // Center panel
    public Node createDashboard() {
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(20));
        centerPanel.setStyle("-fx-background-color: white;");

        Label overviewLabel = new Label("Overview");
        overviewLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        overviewLabel.setStyle("-fx-text-fill: black;");

        // Summary Boxes
        HBox summaryBox = new HBox(10);
        summaryBox.getChildren().addAll(
                createSummaryBox("Goals Completed", "57.5%", "#57C4E5"),
                createSummaryBox("Study Completed", "50%", "#D4915D"),
                createSummaryBox("Assignment Completed", "65%", "#57C4E5"),
                createSummaryBox("Goals Completed", "65%", "#D4915D")
        );

        // Charts
        HBox chartsBox = new HBox(10, createLineChart(), createPieChart());
        HBox barChartsBox = new HBox(10, createBarChart("Study"), createBarChart("Assignments"));

        centerPanel.getChildren().addAll(overviewLabel, summaryBox, chartsBox, barChartsBox);
        return centerPanel;
    }


    // Summary box
    private VBox createSummaryBox(String title, String percentage, String color) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");
        Label titleLabel = new Label(title);
        Label percentageLabel = new Label(percentage);
        percentageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        box.getChildren().addAll(titleLabel, percentageLabel);
        return box;
    }

    // Line Chart
    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Productivity");
        return lineChart;
    }

    // Pie Chart
    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(new PieChart.Data("Maths", 60), new PieChart.Data("Physics", 40));
        pieChart.setTitle("Distribution");
        return pieChart;
    }

    // Bar Chart
    private BarChart<String, Number> createBarChart(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        return barChart;
    }



    // Side Buttons
    private Button createSideButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        return button;
    }
}
