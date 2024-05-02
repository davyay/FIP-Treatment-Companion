package com.github.davyay.fiptreatmentcompanion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

            // Iterate over treatment records
            for (Treatment.TreatmentRecord record : cat.getTreatment().getTreatmentRecords()) {
                writer.write("Date/Time: " + record.getTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                        + " " + record.getTime().format(DateTimeFormatter.ofPattern("hh:mma")) +
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
