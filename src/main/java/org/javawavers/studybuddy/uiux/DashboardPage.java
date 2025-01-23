package org.javawavers.studybuddy.uiux;

import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.javawavers.studybuddy.graphs.AssDistributionCalc;
import org.javawavers.studybuddy.graphs.BarChartCalc;
import org.javawavers.studybuddy.graphs.LineChartCalc;
import org.javawavers.studybuddy.graphs.SubjDistributionCalc;
import org.javawavers.studybuddy.graphs.SummaryBoxCalc;

/**
 * Represents the Dashboard page of the StudyBuddy application. This class is responsible for
 * creating and displaying the dashboard UI with summary boxes and charts that visualize study
 * progress.
 */
public class DashboardPage {

  /**
   * Creates and returns the main dashboard layout.
   *
   * @return A {@link Node} containing the dashboard UI.
   */
  public Node createDashboard() {
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20));
    gridPane.setHgap(20);
    gridPane.setVgap(20);
    gridPane.setStyle("-fx-background-color: white;");

    Label overviewLabel = new Label("Overview");
    overviewLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
    overviewLabel.setStyle("-fx-text-fill: black; -fx-background-color: rgba(181, 99, 241, 0.81);");

    // Summary Boxes
    VBox summaryBox =
      createSummaryBox(
        "Goals Completed",
        SummaryBoxCalc.percentageCalculatorGoals(),
        "rgba(101, 225, 101, 0.9)");
    VBox summaryBox1 =
      createSummaryBox(
        "Study Completed",
        SummaryBoxCalc.percentageCalculatorStudying(),
        "rgba(247, 178, 103, 0.9)");
    VBox summaryBox2 =
      createSummaryBox(
        "Assignment Completed",
        SummaryBoxCalc.percentageCalculatorAssignments(),
        "rgba(101, 225, 101, 0.9)");
    VBox summaryBox3 =
      createSummaryBox(
        "Revision Completed",
        SummaryBoxCalc.percentageCalculatorRevision(),
        "rgba(247, 178, 103, 0.9)");

    // Summary Boxes GridPane
    GridPane summaryPane = new GridPane();
    summaryPane.setHgap(30);
    summaryPane.setVgap(10);
    summaryPane.add(summaryBox, 0, 0);
    summaryPane.add(summaryBox1, 1, 0);
    summaryPane.add(summaryBox2, 2, 0);
    summaryPane.add(summaryBox3, 3, 0);

    // Charts
    LineChart<Number, Number> lineChart = createLineChart();
    PieChart subjectsPieChart = createSubjectsPieChart();
    PieChart assignmentPieChart = createAssignmentPieChart();
    BarChart<String, Number> barChart = createBarChart();

    // Layout Configuration in GridPane
    gridPane.add(overviewLabel, 0, 0, 2, 1); // Title spans 2 columns
    gridPane.add(summaryPane, 0, 1, 2, 1); // Summary boxes span 2 columns
    gridPane.add(lineChart, 0, 2);
    gridPane.add(subjectsPieChart, 1, 2);
    gridPane.add(assignmentPieChart, 0, 3);
    gridPane.add(barChart, 1, 3);

    // Ensuring even distribution
    GridPane.setHgrow(summaryPane, Priority.ALWAYS);
    GridPane.setHgrow(lineChart, Priority.ALWAYS);
    GridPane.setHgrow(subjectsPieChart, Priority.ALWAYS);
    GridPane.setHgrow(assignmentPieChart, Priority.ALWAYS);
    GridPane.setHgrow(barChart, Priority.ALWAYS);

    return gridPane;
  }

  /**
   * Creates a summary box with a title, percentage value, and background color.
   *
   * @param title The title of the summary box.
   * @param percentage The percentage value to be displayed.
   * @param color The background color of the box.
   * @return A {@link VBox} representing the summary box.
   */
  private VBox createSummaryBox(String title, double percentage, String color) {
    VBox box = new VBox(5);
    box.setFillWidth(true);
    box.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");
    Label titleLabel = new Label(title);
    String value = String.valueOf(percentage);
    Label percentageLabel = new Label(value);
    percentageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    box.getChildren().addAll(titleLabel, percentageLabel);
    return box;
  }

  /**
   * Creates a line chart representing weekly study productivity.
   *
   * @return A {@link LineChart} displaying study hours over the week.
   */
  private LineChart<Number, Number> createLineChart() {
    NumberAxis x = new NumberAxis("Μέρα", 1, 7, 1);
    NumberAxis y = new NumberAxis("Ώρες μελέτης", 0, 10, 1);
    LineChart<Number, Number> lineChart = new LineChart<>(x, y);
    lineChart.setTitle("Productivity");
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    HashMap<Integer, Integer> productivity = new HashMap<>(LineChartCalc.weekProgress());
    for (Integer day : productivity.keySet()) {
      series.getData().add(new XYChart.Data<>(day, productivity.get(day)));
    }
    lineChart.getData().add(series);
    return lineChart;
  }

  /**
   * Creates a pie chart representing subject distribution.
   *
   * @return A {@link PieChart} displaying the percentage of time spent per subject.
   */
  private PieChart createSubjectsPieChart() {
    PieChart pieChart = new PieChart();
    pieChart.setTitle("Subjects Distribution");
    HashMap<String, Double> subjDist = new HashMap<>(SubjDistributionCalc.subjectsDistribution());
    for (String s : subjDist.keySet()) {
      double percentage = subjDist.get(s);
      pieChart.getData().add(new PieChart.Data(s, percentage));
    }
    return pieChart;
  }

  /**
   * Creates a pie chart representing assignment distribution.
   *
   * @return A {@link PieChart} displaying the percentage of time spent on assignments.
   */
  private PieChart createAssignmentPieChart() {
    PieChart pieChart = new PieChart();
    pieChart.setTitle("Assignment Distribution");
    HashMap<String, Double> assDist = new HashMap<>(AssDistributionCalc.assignmentsDistribution());
    for (String a : assDist.keySet()) {
      double percentage = assDist.get(a);
      pieChart.getData().add(new PieChart.Data(a, percentage));
    }
    return pieChart;
  }

  /**
   * Creates a bar chart representing the weekly study distribution across different categories.
   *
   * @return A {@link BarChart} displaying total study hours for different categories.
   */
  private BarChart<String, Number> createBarChart() {
    // Axis creation
    CategoryAxis x = new CategoryAxis();
    NumberAxis y = new NumberAxis();
    x.setLabel("Κατηγορίες διαβάσματος");
    y.setStyle("Ώρες μελέτης");
    // Stable size
    y.setAutoRanging(false);
    y.setLowerBound(0);
    y.setUpperBound(100);
    y.setTickUnit(10);

    BarChart<String, Number> barChart = new BarChart<>(x, y);
    barChart.setTitle("Weekly Study Distribution");

    // Data import
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("");

    // Data calculation
    int totalHours = BarChartCalc.calcTotalHours();
    int studyHours = BarChartCalc.calcStudyHours();
    int assignmentHours = BarChartCalc.calcAssignmentHours();
    int revisionHours = BarChartCalc.calcRevisionHours();

    // Data addition in the series
    series.getData().add(new XYChart.Data<>("Συνολική μελέτη", totalHours));
    series.getData().add(new XYChart.Data<>("Διάβασμα", studyHours));
    series.getData().add(new XYChart.Data<>("Εργασίες", assignmentHours));
    series.getData().add(new XYChart.Data<>("Επανάληψη", revisionHours));

    barChart.getData().add(series);

    return barChart;
  }
}
