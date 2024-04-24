package com.github.davyay.fiptreatmentcompanion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeScreenController {

    private Cat currentCat;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
    }

    @FXML
    private void handleWeight() {
        loadScene("/WeightTracker.fxml", "Weight Tracker");
    }

    @FXML
    private void handleTreatment() {
        loadScene("/TreatmentRecords.fxml", "Treatment Records");
    }

    @FXML
    private void handleCommunity() {
        loadScene("/CommunityResources.fxml", "Community Resources");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the scene for " + title + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport() {
        System.out.println("Exporting Data...");
        exportData();
    }

    private void exportData() {
        ExportManager exportManager = new ExportManager(); // Create an instance of ExportManager
        try {
            exportManager.writeCatDetailsToFile(currentCat, currentCat.getName()+"records.txt"); // Use the instance to call the method
            System.out.println("Data exported successfully.");
        } catch (Exception e) {
            System.err.println("Error exporting data: " + e.getMessage());
        }
    }
}
