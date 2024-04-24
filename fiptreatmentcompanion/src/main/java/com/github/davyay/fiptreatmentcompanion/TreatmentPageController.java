package com.github.davyay.fiptreatmentcompanion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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

    private Cat cat; // Assume the cat object is passed or set somewhere in your application flow

    public TreatmentPageController() {
        // This constructor is intentionally empty
    }

    @FXML
    private void initialize() {
        updateTreatmentDetails();
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
        // Add the logic to navigate back to the home screen
        System.out.println("Navigating back to home...");
    }
}
