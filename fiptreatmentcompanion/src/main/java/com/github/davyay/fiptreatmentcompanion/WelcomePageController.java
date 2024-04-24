package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class WelcomePageController {
    private CatManager catManager = new CatManager();  // Initialize CatManager
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void handleLoadProfile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Cat Profile File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
    
        if (selectedFile != null) {
            try {
                Cat loadedCat = catManager.loadCatProfile(selectedFile);
                showHome(loadedCat);
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load the cat profile.", ButtonType.OK).show();
            }
        }
    }
    

    @FXML
    protected void handleCreateProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setPrimaryStage(primaryStage);  // Pass the stage to the next controller

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the profile creation page.", ButtonType.OK).show();
        }
    }

    private void showHome(Cat cat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the controller to HomeScreenController
            HomeScreenController controller = loader.getController();
            controller.setCatProfile(cat);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the home screen.", ButtonType.OK).show();
        }
    }
}
