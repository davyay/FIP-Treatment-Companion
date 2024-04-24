package com.github.davyay.fiptreatmentcompanion;
import javafx.fxml.FXML;
import java.awt.Desktop;
import java.net.URI;

public class CommunityLinksController {

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
        // Navigate back to Home screen
        System.out.println("Navigating back to home...");
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
