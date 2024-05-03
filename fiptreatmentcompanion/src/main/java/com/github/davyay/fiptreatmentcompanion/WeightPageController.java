package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeightPageController {
    @FXML
    private LineChart<String, Number> weightChart;
    @FXML
    private TextField weightInput;

    private Cat currentCat; // The Cat instance, containing the WeightTracker
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
        loadWeightData();
    }

    // Initialize method called by FXML loader
    @FXML
    private void initialize() {
    }

    // Event handler for updating weight
    @FXML
    private void handleUpdateWeight() {
        try {
            double weight = Double.parseDouble(weightInput.getText());
            if (weight <= 0) {
                new Alert(Alert.AlertType.ERROR, "Weight must be greater than 0.", ButtonType.OK).show();
                return;
            }
            LocalDate currentDate = LocalDate.now(); // Current date for the weight record
            currentCat.addWeightRecord(weight, currentDate); // Add to weight tracker through Cat
            updateChart(currentDate.toString(), weight); // Update chart
            saveCatData(); // Save the updated cat data
            weightInput.clear(); // Clear the input field
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid weight input.", ButtonType.OK).show();
        }
    }

    private void saveCatData() {
        try {
            CatManager catManager = new CatManager();
            catManager.saveCatProfile(currentCat); // Specify the file path
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save data: " + e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
    }
    
    // Load initial weight data into the chart
    private void loadWeightData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weight Over Time");

        for (WeightTracker.WeightRecord record : currentCat.getWeightTracker().getWeightRecords()) {
            series.getData().add(new XYChart.Data<>(record.getDate().toString(), record.getWeight()));
        }

        weightChart.getData().clear();
        weightChart.getData().add(series);
    }

    // Update the chart dynamically with new data
    private void updateChart(String date, double weight) {
        XYChart.Series<String, Number> series = weightChart.getData().get(0);
        series.getData().add(new XYChart.Data<>(date, weight));
    }

    @FXML
    private void handleHome() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the controller to HomeScreenController
            HomeScreenController controller = loader.getController();
            controller.setCatProfile(currentCat);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the home screen.", ButtonType.OK).show();
        }
    }
    
}
