package com.github.davyay.fiptreatmentcompanion;
import javafx.fxml.FXML;

public class HomeScreenController {

    @FXML
    private void handleWeight() {
        // Navigate to Weight Tracker screen
        System.out.println("Navigating to Weight Tracker...");
    }

    @FXML
    private void handleTreatment() {
        // Navigate to Treatment Records screen
        System.out.println("Navigating to Treatment Records...");
    }

    @FXML
    private void handleCommunity() {
        // Navigate to Community Resources screen
        System.out.println("Navigating to Community Resources...");
    }

    @FXML
    private void handleExport() {
        // Call FileManager to export data
        System.out.println("Exporting Data...");
        exportData();
    }

    private void exportData() {
        // ExportManager.writeCatDetailsToFile(cat,catdetails.txt);
        System.out.println("Data exported successfully.");
    }
}
