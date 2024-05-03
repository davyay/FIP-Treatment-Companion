package com.github.davyay.fiptreatmentcompanion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class TreatmentPageController {
    @FXML
    private Label medicationNameLabel, dosageRatioLabel, catWeightLabel, currentDoseLabel, updateError;
    @FXML
    private TextField newMedName, newDosageRatio, doseDateTime;
    @FXML
    private Button homeButton;
    @FXML
    private TableView<Treatment.TreatmentRecord> treatmentRecordsTable;
    @FXML
    private TableColumn<Treatment.TreatmentRecord, String> dateColumn;
    @FXML
    private TableColumn<Treatment.TreatmentRecord, String> doseColumn;
    @FXML
    private TableColumn<Treatment.TreatmentRecord, String> medicationColumn;

    private Cat currentCat;
    private Stage primaryStage;

    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String> data) {
                        return new SimpleStringProperty(
                                data.getValue().getTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma")));
                    }
                });

        doseColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String> data) {
                        return new SimpleStringProperty(String.format("%.2f units", data.getValue().getDosage()));
                    }
                });

        medicationColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Treatment.TreatmentRecord, String> data) {
                        return new SimpleStringProperty(data.getValue().getMedicationName());
                    }
                });
    }

    public void setCatProfile(Cat cat) {
        this.currentCat = cat;
        updateTreatmentDetails();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void updateTreatmentDetails() {
        medicationNameLabel.setText(currentCat.getTreatment().getCurrentMedicationName());
        dosageRatioLabel.setText(String.format("%.2f", currentCat.getTreatment().getDosageRatio()));
        catWeightLabel.setText(String.format("%.2f lb", currentCat.getWeightTracker().getMostRecentWeight()));
        double dosage = currentCat.getTreatment().calculateDosage(currentCat.getWeightTracker().getMostRecentWeight());
        currentDoseLabel.setText(String.format("%.2f units", dosage));

        // Set the items for the TableView from the currentCat's treatment records
        treatmentRecordsTable
                .setItems(FXCollections.observableArrayList(currentCat.getTreatment().getTreatmentRecords()));
    }

    @FXML
    private void handleUpdateTreatmentDetails() {
        try {
            if (newMedName.getText().isEmpty() || newDosageRatio.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please enter a medication name and dosage ratio.", ButtonType.OK)
                        .show();
                return;
            }
            if (Double.parseDouble(newDosageRatio.getText()) <= 0) {
                new Alert(Alert.AlertType.ERROR, "Please enter a positive number for the dosage ratio.", ButtonType.OK)
                        .show();
                return;
            }
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
            new Alert(Alert.AlertType.ERROR, "Invalid input! \n Please enter a valid number for the dosage ratio.",
                    ButtonType.OK).show();
        }
    }

    @FXML
    private void handleRecordDose() {
        try {
            if (doseDateTime.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please enter a date and time.", ButtonType.OK).show();
                return;
            }
            LocalDateTime time = LocalDateTime.parse(doseDateTime.getText().trim(),
                    DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            if (LocalDate.parse(currentCat.getDateOfBirth(), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .isAfter(time.toLocalDate())) {
                new Alert(Alert.AlertType.ERROR, "Cannot record a date and time before the cat's birth.", ButtonType.OK)
                        .show();
                return;
            }
            currentCat.getTreatment().addMedicationRecord(time);
            saveCatData();
            updateTreatmentDetails();
            doseDateTime.setText(""); // Clearing the text field
            updateError.setText("Dose recorded successfully.");
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid date and time format. \nPlease enter in MM/dd/yyyy HH:mm format. \n(24-hour clock)",
                    ButtonType.OK).show();
        }
    }

    private void saveCatData() {
        try {
            CatManager catManager = new CatManager();
            catManager.saveCatProfile(currentCat); // Specify the file path
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save data: " + e.getMessage(), ButtonType.OK).show();
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
