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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TreatmentPageController {
    @FXML private Label medicationNameLabel;
    @FXML private Label dosageRatioLabel;
    @FXML private Label catWeightLabel;
    @FXML private Label currentDoseLabel;
    @FXML private TextField newMedName;
    @FXML private TextField newDosageRatio;
    @FXML private TextField doseDateTime;
    @FXML private Label updateError;
    @FXML private Button homeButton;

    private Cat cat;
    private Stage primaryStage; 

    public void setCatProfile(Cat cat) {
        this.cat = cat;
        updateTreatmentDetails();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TreatmentPageController() {
        // This constructor is intentionally empty
    }

    @FXML
    private void initialize() {
    }

    private void updateTreatmentDetails() {
        medicationNameLabel.setText(cat.getTreatment().getCurrentMedicationName());
        dosageRatioLabel.setText(String.format("%.2f", cat.getTreatment().getDosageRatio()));
        catWeightLabel.setText(String.format("%.2f kg", cat.getWeightTracker().getMostRecentWeight()));
        double dosage = cat.getTreatment().calculateDosage(cat.getWeightTracker().getMostRecentWeight());
        currentDoseLabel.setText(String.format("%.2f ml", dosage));
    }

    @FXML
    private void handleUpdateTreatmentDetails() {
        try {
            String newMedicationName = newMedName.getText().trim();
            double newRatio = Double.parseDouble(newDosageRatio.getText().trim());
            cat.getTreatment().updateMedicationName(newMedicationName);
            cat.getTreatment().updateDosageRatio(newRatio);
            updateTreatmentDetails();
            updateError.setText("Treatment details updated successfully.");
        } catch (NumberFormatException e) {
            updateError.setText("Error: Invalid dosage ratio. Please enter a valid number.");
        }
    }

    @FXML
    private void handleRecordDose() {
        try {
            LocalDateTime time = LocalDateTime.parse(doseDateTime.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            cat.getTreatment().addMedicationRecord(time);
            updateError.setText("Dose recorded successfully.");
        } catch (DateTimeParseException e) {
            updateError.setText("Error: Invalid date/time format. Please use MM/dd/yyyy HH:mm.");
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
