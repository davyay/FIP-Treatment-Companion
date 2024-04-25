package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WeightPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the controller to WeightPageController
            WeightPageController controller = loader.getController();
            controller.setCatProfile(currentCat);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the Weight screen.", ButtonType.OK).show();
        }
    }

    @FXML
    private void handleTreatment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TreatmentPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the controller to TreatmentPageController
            TreatmentPageController controller = loader.getController();
            controller.setCatProfile(currentCat);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the Treatment screen.", ButtonType.OK).show();
        }
    }

    @FXML
    private void handleCommunity() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommunityLinks.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the controller to CommunityLinksController
            CommunityLinksController controller = loader.getController();
            controller.setCatProfile(currentCat);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the Community Links screen.", ButtonType.OK).show();
        }
    }

    

    @FXML
    private void handleExport() {
        System.out.println("Exporting Data...");
        exportData();
    }

    private void exportData() {
        ExportManager exportManager = new ExportManager();
        try {
            exportManager.writeCatDetailsToFile(currentCat, currentCat.getName() + "Records.txt");
            System.out.println("Data exported successfully.");
        } catch (Exception e) {
            System.err.println("Error exporting data: " + e.getMessage());
        }
    }
}

