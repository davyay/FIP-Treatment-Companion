package com.github.davyay.fiptreatmentcompanion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExportManager {

    // Method to write cat details to a text file
    public void writeCatDetailsToFile(Cat cat, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Name of the Cat: " + cat.getName());
            writer.newLine();
            writer.write("Date of Birth: " + cat.getDateOfBirth());
            writer.newLine();
            writer.write("Weight Records:");
            writer.newLine();

            // Iterate over weight records
            for (WeightTracker.WeightRecord record : cat.getWeightTracker().getWeightRecords()) {
                writer.write("Date: " + record.getDate() + ", Weight: " + record.getWeight() + " lbs");
                writer.newLine();
            }

            writer.write("Treatment Records:");
            writer.newLine();

            // Sort and iterate over treatment records
            List<Treatment.TreatmentRecord> sortedRecords = cat.getTreatment().getTreatmentRecords()
                .stream()
                .sorted(Comparator.comparing(Treatment.TreatmentRecord::getTime))
                .collect(Collectors.toList());

            for (Treatment.TreatmentRecord record : sortedRecords) {
                writer.write("Date/Time: " + record.getTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma")) +
                        ", Dose: " + record.getDosage() + " units" +
                        ", Medication: " + record.getMedicationName());
                writer.newLine();
            }
            System.out.println("Data exported successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write cat details to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}