package org.javawavers.studybuddy.ui_ux;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.javawavers.studybuddy.graphs.AssDistributionCalc;
import org.javawavers.studybuddy.graphs.BarChartCalc;
import org.javawavers.studybuddy.graphs.SubjDistributionCalc;
import org.javawavers.studybuddy.graphs.SummaryBoxCalc;

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
    summaryBox
        .getChildren()
        .addAll(
            createSummaryBox(
                "Goals Completed", SummaryBoxCalc.percentageCalculatorGoals(), "#57C4E5"),
            createSummaryBox(
                "Study Completed", SummaryBoxCalc.percentageCalculatorStudying(), "#D4915D"),
            createSummaryBox(
                "Assignment Completed",
                SummaryBoxCalc.percentageCalculatorAssignments(),
                "#57C4E5"),
            createSummaryBox(
                "Revision Completed", SummaryBoxCalc.percentageCalculatorRevision(), "#D4915D"));

    // Charts
    HBox chartsBox = new HBox(10, createLineChart(), createSubjectsPieChart());
    HBox barChartsBox = new HBox(10, createAssignmentPieChart(), createBarChart());

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
    NumberAxis x = new NumberAxis("Μέρα", 1, 7, 1);
    NumberAxis y = new NumberAxis("Ώρες μελέτης", 0, 10, 1);
    LineChart<Number, Number> lineChart = new LineChart<>(x, y);
    lineChart.setTitle("Productivity");
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    HashMap<Integer, Integer> productivity = new HashMap<>();
    int[] studying = staticUser.getAvPerDay();
    for (int i = 1; i < studying.length; i++) {
      series.getData().add(new XYChart.Data<>(i, studying[i]));
    }
    lineChart.getData().add(series);
    return lineChart;
  }

  // Pie Chart for subject distribution
  private PieChart createSubjectsPieChart() {
    PieChart pieChart = new PieChart();
    pieChart. setTitle("Subjects Distribution");
    HashMap<String, Double> subjDist = SubjDistributionCalc.subjectsDistribution();
    for (String s : subjDist.keySet()) {
      double percentage = subjDist.get(s);
      pieChart.getData().add(new PieChart.Data(s, percentage));
    }
    return pieChart;
  }

  // Pie Chart for subject distribution
  private PieChart createAssignmentPieChart() {
    PieChart pieChart = new PieChart();
    pieChart. setTitle("Assignment Distribution");
    HashMap<String, Double> assDist = AssDistributionCalc.assignmentsDistribution();
    for (String a : assDist.keySet()) {
      double percentage = assDist.get(a);
      pieChart.getData().add(new PieChart.Data(a, percentage));
    }
    return pieChart;
  }


  // Bar Chart
  private BarChart<String, Number> createBarChart() {
    //Axis creation
    CategoryAxis x = new CategoryAxis();
    NumberAxis y = new NumberAxis();
    x.setLabel("Κατηγορίες διαβάσματος");
    y.setStyle("Ώρες μελέτης");
    // stable size
    y.setAutoRanging(false);
    y.setLowerBound(0);
    y.setUpperBound(100);
    y.setTickUnit(10);

    BarChart<String, Number> barChart = new BarChart<>(x, y);
    barChart.setTitle("Weekly Study Distribution");

    //Data import
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("");

    //Data calculation
    int totalHours = BarChartCalc.calcTotalHours();
    int studyHours = BarChartCalc.calcStudyHours();
    int assignmentHours = BarChartCalc.calcAssignmentHours();
    int revisionHours = BarChartCalc.calcRevisionHours();

    //Data addition in the series
    series.getData().add(new XYChart.Data<>("Συνολική μελέτη", totalHours));
    series.getData().add(new XYChart.Data<>("Διάβασμα", studyHours));
    series.getData().add(new XYChart.Data<>("Εργασίες", assignmentHours));
    series.getData().add(new XYChart.Data<>("Επανάληψη", revisionHours));

    barChart.getData().add(series);

    return barChart;
  }

  // Side Buttons
  /*private Button createSideButton(String text) {
    Button button = new Button(text);
    button.setStyle(
        "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
    return button;
  }*/
}
