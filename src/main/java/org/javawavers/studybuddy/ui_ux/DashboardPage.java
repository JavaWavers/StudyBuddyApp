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
import org.javawavers.studybuddy.graphs.SubjDistributionCalc;
import org.javawavers.studybuddy.graphs.SummaryBoxCalc;

import java.util.HashMap;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

public class DashboardPage {
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
                createSummaryBox("Goals Completed", SummaryBoxCalc.percentageCalculatorGoals(), "#57C4E5"),
                createSummaryBox("Study Completed", SummaryBoxCalc.percentageCalculatorStudying(), "#D4915D"),
                createSummaryBox("Assignment Completed", SummaryBoxCalc.percentageCalculatorAssignments(), "#57C4E5"),
                createSummaryBox("Revision Completed", SummaryBoxCalc.percentageCalculatorRevision(), "#D4915D")
        );

        // Charts
        HBox chartsBox = new HBox(10, createLineChart(), createPieChart());
        HBox barChartsBox = new HBox(10, createBarChart("Study"), createBarChart("Assignments"));

        centerPanel.getChildren().addAll(overviewLabel, summaryBox, chartsBox, barChartsBox);
        return centerPanel;
    }


    // Summary box
    private VBox createSummaryBox(String title, double percentage, String color) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");
        Label titleLabel = new Label(title);
        String value = String.valueOf(percentage);
        Label percentageLabel = new Label(value);
        percentageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        box.getChildren().addAll(titleLabel, percentageLabel);
        return box;
    }

    // Line Chart
    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis("Μέρα",1,7,1);
        NumberAxis yAxis = new NumberAxis("Ώρες μελέτης",0,10,1);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Productivity");
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        int [] studying = staticUser.getAvPerDay();
        for(int i = 1; i < studying.length; i++) {
            series.getData().add(new XYChart.Data<>(i, studying[i]));
        }
        lineChart.getData().add(series);
        return lineChart;
    }

    // Pie Chart
    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        HashMap<String, Double> subjDistr = SubjDistributionCalc.subjectsDistribution();
        for (String s : subjDistr.keySet()){
            double percentage = subjDistr.get(s);
            pieChart.getData().add( new PieChart.Data(s,percentage));
        }
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
