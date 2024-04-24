package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;

public class ProfileController {
    @FXML private TextField nameField;
    @FXML private TextField dobField;
    @FXML private Text dobError;
    @FXML private TextField initialWeight;
    @FXML private TextField medicationName;
    @FXML private TextField dosageRatio;

    private CatManager catManager = new CatManager(); // Instance of CatManager
    private Cat currentCat;

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
            if (currentCat == null) {
                // Create a new Cat object using the provided data
                currentCat = new Cat(nameField.getText(), dobField.getText());
            } else {
                // Update the existing Cat object with the new profile data
                currentCat.setName(nameField.getText());
                currentCat.setDateOfBirth(dobField.getText());
            }
            // Update other attributes of the Cat object
            currentCat.getWeightTracker().addWeightRecord(Double.parseDouble(initialWeight.getText()), LocalDate.now());
            currentCat.getTreatment().updateMedicationName(medicationName.getText());
            currentCat.getTreatment().updateDosageRatio(Double.parseDouble(dosageRatio.getText()));
    
            // Save the updated profile
            catManager.saveCatProfile(currentCat);
            System.out.println("Profile Saved Successfully");
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
}
