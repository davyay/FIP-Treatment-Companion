package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.time.LocalDate;

public class WeightPageController {
    @FXML
    private LineChart<String, Number> weightChart;
    @FXML
    private TextField weightInput;
    @FXML
    private Label weightError;

    private WeightTracker weightTracker; // The weight tracker instance

    // Initialize method called by FXML loader
    @FXML
    private void initialize() {
        loadWeightData();
    }

    // Event handler for updating weight
    @FXML
    private void handleUpdateWeight() {
        try {
            double weight = Double.parseDouble(weightInput.getText());
            LocalDate currentDate = LocalDate.now(); // Current date for the weight record
            weightTracker.addWeightRecord(weight, currentDate); // Add to weight tracker
            updateChart(currentDate.toString(), weight); // Update chart
            weightInput.clear(); // Clear the input field
            weightError.setText(""); // Clear error message if any
        } catch (NumberFormatException e) {
            weightError.setText("Invalid input! Please enter a valid number."); // Display error
        }
    }

    // Load initial weight data into the chart
    private void loadWeightData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weight Over Time");

        for (WeightTracker.WeightRecord record : weightTracker.getWeightRecords()) {
            series.getData().add(new XYChart.Data<>(record.getDate().toString(), record.getWeight()));
        }

        weightChart.getData().add(series);
    }

    // Update the chart dynamically with new data
    private void updateChart(String date, double weight) {
        XYChart.Series<String, Number> series = weightChart.getData().get(0);
        series.getData().add(new XYChart.Data<>(date, weight));
    }

    @FXML
    private void handleHome() {
        // Navigate back to Home screen
        System.out.println("Navigating back to home...");
    }
}
