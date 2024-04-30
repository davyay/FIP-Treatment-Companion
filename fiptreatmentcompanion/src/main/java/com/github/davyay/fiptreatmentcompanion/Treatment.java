package com.github.davyay.fiptreatmentcompanion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Treatment {
    private Cat cat;
    private String currentMedicationName; // Stores the current medication name
    private List<TreatmentRecord> treatmentRecords;
    private double dosageRatio; // Ratio of medication (unit per lb)

    // Constructor 

    public Treatment() {
    }

    public Treatment(Cat cat) {
        this.cat = cat;
        this.currentMedicationName = "";
        this.dosageRatio = 0.0;
        this.treatmentRecords = new ArrayList<>();
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public String getCurrentMedicationName() {
        return currentMedicationName;
    }

    public double getDosageRatio() {
        return dosageRatio;
    }

    public void updateMedicationName(String newMedicationName) {
        this.currentMedicationName = newMedicationName;
    }

    public void updateDosageRatio(double newDosageRatio) {
        this.dosageRatio = newDosageRatio;
    }

    public void addMedicationRecord(LocalDateTime time) {
        if (this.cat == null) {
            throw new IllegalStateException("Cat object is null.");
        }
        
        WeightTracker weightTracker = cat.getWeightTracker();
        if (weightTracker == null) {
            throw new IllegalStateException("WeightTracker is null.");
        }
    
        Double currentWeight = weightTracker.getMostRecentWeight();
        if (currentWeight == null || currentWeight <= 0) {
            throw new IllegalArgumentException("Current weight is invalid.");
        }
    
        double dosage = calculateDosage(currentWeight);
        TreatmentRecord record = new TreatmentRecord(time, currentMedicationName, dosage, currentWeight);
        treatmentRecords.add(record);
    }
    

    public double calculateDosage(double weight) {
        return weight * dosageRatio;
    }

    public List<TreatmentRecord> getTreatmentRecords() {
        return new ArrayList<>(treatmentRecords);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
        for (TreatmentRecord record : treatmentRecords) {
            sb.append(record.getTime().format(formatter))
              .append(" ")
              .append(record.getMedicationName())
              .append(" ")
              .append(String.format("%.2fml", record.getDosage()))
              .append(" ")
              .append(String.format("%.2flb", record.getWeight()))
              .append("\n");
        }
        return sb.toString();
    }

    // Inner class to store individual treatment records
    class TreatmentRecord {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mma")
        private LocalDateTime time;

        private String medicationName;
        private double dosage;
        private double weight;

        public TreatmentRecord() {
        }

        public TreatmentRecord(LocalDateTime time, String medicationName, double dosage, double weight) {
            this.time = time;
            this.medicationName = medicationName;
            this.dosage = dosage;
            this.weight = weight;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public String getMedicationName() {
            return medicationName;
        }

        public double getDosage() {
            return dosage;
        }

        public double getWeight() {
            return weight;
        }
    }
}
