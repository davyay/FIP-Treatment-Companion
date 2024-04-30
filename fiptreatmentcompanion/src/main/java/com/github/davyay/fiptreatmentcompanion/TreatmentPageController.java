package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TreatmentPageController {
    @FXML
    private Label medicationNameLabel;
    @FXML
    private Label dosageRatioLabel;
    @FXML
    private Label catWeightLabel;
    @FXML
    private Label currentDoseLabel;
    @FXML
    private TextField newMedName;
    @FXML
    private TextField newDosageRatio;
    @FXML
    private TextField doseDateTime;
    @FXML
    private Label updateError;
    @FXML
    private Button homeButton;

    private Cat currentCat;
    private Stage primaryStage;

    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
        updateTreatmentDetails();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
    }

    private void updateTreatmentDetails() {
        medicationNameLabel.setText(currentCat.getTreatment().getCurrentMedicationName());
        dosageRatioLabel.setText(String.format("%.2f", currentCat.getTreatment().getDosageRatio()));
        catWeightLabel.setText(String.format("%.2f kg", currentCat.getWeightTracker().getMostRecentWeight()));
        double dosage = currentCat.getTreatment().calculateDosage(currentCat.getWeightTracker().getMostRecentWeight());
        currentDoseLabel.setText(String.format("%.2f ml", dosage));
    }

    @FXML
    private void handleUpdateTreatmentDetails() {
        try {
            String newMedicationName = newMedName.getText().trim();
            double newRatio = Double.parseDouble(newDosageRatio.getText().trim());
            currentCat.getTreatment().updateMedicationName(newMedicationName);
            currentCat.getTreatment().updateDosageRatio(newRatio);
            saveCatData();
            updateTreatmentDetails();
            updateError.setText("Treatment details updated successfully.");
            newMedName.setText(""); // Clearing the text field
            newDosageRatio.setText(""); // Clearing the text field
        } catch (NumberFormatException e) {
            updateError.setText("Error: Invalid dosage ratio. Please enter a valid number.");
        }
    }

    @FXML
    private void handleRecordDose() {
        try {
            LocalDateTime time = LocalDateTime.parse(doseDateTime.getText(),
                    DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            currentCat.getTreatment().addMedicationRecord(time);
            saveCatData();
            updateError.setText("Dose recorded successfully.");
            doseDateTime.setText(""); // Clearing the text field
        } catch (DateTimeParseException e) {
            updateError.setText("Error: Invalid date/time format. Please use MM/dd/yyyy HH:mm.");
        }
    }

    private void saveCatData() {
        try {
            CatManager catManager = new CatManager();
            catManager.saveCatProfile(currentCat); // Specify the file path
        } catch (IOException e) {
            updateError.setText("Failed to save data: " + e.getMessage());
            e.printStackTrace();
        }
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
