package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class ProfileController {
    @FXML private TextField nameField;
    @FXML private TextField dobField;
    @FXML private Text dobError;
    @FXML private TextField initialWeight;
    @FXML private TextField medicationName;
    @FXML private TextField dosageRatio;

    private CatManager catManager = new CatManager(); // Instance of CatManager
    private Cat currentCat;
    private Stage primaryStage; // Stage injected from the Main controller

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // Method to set the existing Cat profile in the controller
    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
        updateCatProfileView();
    }

    // Updates the UI fields based on the current Cat object
    private void updateCatProfileView() {
        if (currentCat != null) {
            nameField.setText(currentCat.getName());
            dobField.setText(currentCat.getDateOfBirth());
            initialWeight.setText(String.format("%.2f", currentCat.getWeightTracker().getMostRecentWeight()));
            medicationName.setText(currentCat.getTreatment().getCurrentMedicationName());
            dosageRatio.setText(String.format("%.2f", currentCat.getTreatment().getDosageRatio()));
        }
    }

    @FXML
    protected void handleSaveProfile() {
        if (!validateDate(dobField.getText())) {
            dobError.setText("DOB not in mm/dd/yyyy format");
            return;
        } else {
            dobError.setText("");
        }
    
        try {
            if (nameField.getText().isEmpty() || dobField.getText().isEmpty() || initialWeight.getText().isEmpty() ||
                    medicationName.getText().isEmpty() || dosageRatio.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill in all fields.", ButtonType.OK).show();
                return;
            }
            if (Double.parseDouble(initialWeight.getText()) <= 0 || Double.parseDouble(dosageRatio.getText()) <= 0) {
                new Alert(Alert.AlertType.ERROR, "Please enter a positive number for weight and dosage.", ButtonType.OK).show();
                return;
            }
            if (currentCat == null) {
                currentCat = new Cat(nameField.getText(), dobField.getText());
            } else {
                currentCat.setName(nameField.getText());
                currentCat.setDateOfBirth(dobField.getText());
            }
            currentCat.getWeightTracker().addWeightRecord(Double.parseDouble(initialWeight.getText()), LocalDate.now());
            currentCat.getTreatment().updateMedicationName(medicationName.getText());
            currentCat.getTreatment().updateDosageRatio(Double.parseDouble(dosageRatio.getText()));
    
            catManager.saveCatProfile(currentCat);
            System.out.println("Profile Saved Successfully");
            showHome(currentCat); // Redirect to the home screen after successful save
        } catch (IOException e) {
            System.err.println("Failed to save cat profile: " + e.getMessage());
        } catch (NumberFormatException | DateTimeParseException e) {
            System.err.println("Invalid input format: " + e.getMessage());
            dobError.setText("Invalid input! Please check the data format.");
        }
    }

    private boolean validateDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
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
