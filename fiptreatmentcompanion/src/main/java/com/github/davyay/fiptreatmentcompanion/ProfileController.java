package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProfileController {
    @FXML private TextField nameField;
    @FXML private TextField dobField;
    @FXML private Text dobError;
    @FXML private TextField initialWeight;
    @FXML private TextField medicationName;
    @FXML private TextField dosageRatio;

    @FXML
    protected void handleSaveProfile() {
        if (!validateDate(dobField.getText())) {
            dobError.setText("DOB not in mm/dd/yyyy format");
            return;
        } else {
            dobError.setText("");
        }

        // Save profile here if all data is valid
        // Code to create and save the Cat, WeightTracker, and Treatment information

        System.out.println("Profile Saved Successfully");
        // Transition to the next view
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
