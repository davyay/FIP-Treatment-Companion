package com.github.davyay.fiptreatmentcompanion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

public class CommunityLinksController {

    private Cat currentCat;    
    private Stage primaryStage;

    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleFIPWarriors() {
        openWebpage("http://fipwarriors.com");
    }

    @FXML
    private void handleFIPWarriorsFB() {
        openWebpage("https://www.facebook.com/groups/fipwarriorsoriginal/");
    }

    @FXML
    private void handleFIPVets() {
        openWebpage("https://fiptreatment.com/fip-veterinarians/");
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

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
