package com.github.davyay.fiptreatmentcompanion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Treatment {
    private Cat cat;
    private String currentMedicationName; // Stores the current medication name
    private List<TreatmentRecord> treatmentRecords;
    private double dosageRatio; // Ratio of medication (ml per lb)

    public Treatment(Cat cat, String initialMedicationName, double initialDosageRatio) {
        this.cat = cat;
        this.currentMedicationName = initialMedicationName;
        this.dosageRatio = initialDosageRatio;
        this.treatmentRecords = new ArrayList<>();
    }

    public void updateMedicationName(String newMedicationName) {
        this.currentMedicationName = newMedicationName;
    }

    public void updateDosageRatio(double newDosageRatio) {
        this.dosageRatio = newDosageRatio;
    }

    public void addMedicationRecord(LocalDateTime time) {
        double dosage = calculateDosage();
        TreatmentRecord record = new TreatmentRecord(time, currentMedicationName, dosage, cat.getWeight());
        treatmentRecords.add(record);
    }

    private double calculateDosage() {
        return cat.getWeight() * dosageRatio;
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
    private class TreatmentRecord {
        private LocalDateTime time;
        private String medicationName;
        private double dosage;
        private double weight;

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
